package com.example.lorena.empresalacteos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.lorena.empresalacteos.R.id.textSucesoPedido;

public class EnterpriseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static LinkedList<Pedido> pedidosCola=new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadFirstFragment();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enterprise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_fragment1) {
            loadFirstFragment();
        } else if (id == R.id.nav_fragment2) {
            loadSecondFragment();
        } else if (id == R.id.nav_fragment3) {
            loadThirdFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFirstFragment() {
        FirstFragment firstFragment = new FirstFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, firstFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadSecondFragment() {
        SecondFragment secondFragment = new SecondFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, secondFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadThirdFragment() {
        ThirdFragment thirdFragment = new ThirdFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, thirdFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();
            onNetworkChange(ni);
        }
    };

    private void onNetworkChange(NetworkInfo networkInfo) {
        if (networkInfo != null)
        {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED)
            {
                try {
                Log.d("INTERNET", "CONNECTED");
                PedidoOperations operaciones=new PedidoOperations(this);
                operaciones.open();
                List<Pedido> pedidos=operaciones.getAllPedidos();
                operaciones.close();
                String url = "http://"+MainActivity.ip+"leerPedidos";
                String  response = new WSC().execute(url).get();
                Type type=new TypeToken<List<Pedido>>() {}.getType();
                Gson json=new Gson();
                List<Pedido> pedidosServer=json.fromJson(response,type);
                for (int i=0; i<pedidos.size(); i++)
                {
                    boolean bandera=false;
                    for (int j=0; j<pedidosServer.size(); j++){

                        if (pedidos.get(i).getCodigo().equals(pedidosServer.get(j).getCodigo()))
                        {
                            if (!pedidos.get(i).getNombre().equals(pedidosServer.get(j).getNombre())||pedidos.get(i).getCantidad()!=pedidosServer.get(j).getCantidad()){
                                url = "http://"+MainActivity.ip+"modificarPedido/" + pedidos.get(i).getCodigo() + "/" + pedidos.get(i).getNombre()+"/"+pedidos.get(i).getCantidad();
                                response =new WSC().execute(url).get();
                                json=new Gson();
                                String message=json.fromJson(response, String.class);
                                bandera=true;
                                break;
                            }
                            else{
                                bandera=true;
                                break;
                            }
                        }
                    }
                    if (!bandera){
                        url = "http://"+MainActivity.ip+"crearPedido/" + pedidos.get(i).getCodigo() + "/" + pedidos.get(i).getNombre()+"/"+pedidos.get(i).getCantidad();
                        response =new WSC().execute(url).get();
                        json=new Gson();
                        String message=json.fromJson(response, String.class);
                    }
                }
                for (int i=0; i<pedidosServer.size(); i++) {
                    boolean bandera = false;
                    for (int j = 0; j < pedidos.size(); j++) {
                        if (pedidosServer.get(i).getCodigo().equals(pedidos.get(j).getCodigo())){
                            bandera=true;
                            break;
                        }
                    }
                    if (!bandera)
                    {
                        url = "http://"+MainActivity.ip+"eliminarPedido/" + pedidosServer.get(i).getCodigo();
                        response =new WSC().execute(url).get();
                        json=new Gson();
                        String message=json.fromJson(response, String.class);
                    }
                }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.d("INTERNET", "DISCONNECTED");
            }
        }
    }

    @Override public void onResume()
    {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override public void onPause()
    {
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }
}
