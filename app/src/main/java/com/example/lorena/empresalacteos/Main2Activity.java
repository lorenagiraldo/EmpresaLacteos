package com.example.lorena.empresalacteos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class Main2Activity extends AppCompatActivity {

    private EditText editNombreUsuario;
    private EditText editDocumentoUsuario;
    private EditText editDireccionUsuario;
    private EditText editTelefonoUsuario;
    private TextView textSucesoUsuario;
    private Button botonRegistrarUsuario;
    public static Usuario usuarioG=null;
    public static Pedido pedidoG=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editNombreUsuario=(EditText) findViewById(R.id.editNombreUsuario);
        editDocumentoUsuario=(EditText) findViewById(R.id.editDocumentoUsuario);
        editDireccionUsuario=(EditText) findViewById(R.id.editDireccionUsuario);
        editTelefonoUsuario=(EditText) findViewById(R.id.editTelefonoUsuario);
        textSucesoUsuario=(TextView) findViewById(R.id.textSucesoUsuario);
        botonRegistrarUsuario=(Button)findViewById(R.id.botonRegistrarUsuario);
        botonRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre=editNombreUsuario.getText().toString();
                String documento=editDocumentoUsuario.getText().toString();
                String direccion=editDireccionUsuario.getText().toString();
                String telefono=editTelefonoUsuario.getText().toString();
                Usuario usuario=new Usuario(-1,documento,nombre, direccion, telefono);
                registrarUsuario(usuario);
            }
        });
    }

    private void registrarUsuario(Usuario usuario)
    {
        try {
            if (usuario.getDocumento().equals("") || usuario.getNombre().equals("")) {
                textSucesoUsuario.setText("Error campos vacios");
            } else {
                Log.d("Error",usuario.getDocumento());
                Log.d("Error",usuario.getNombre());
                Log.d("Error",usuario.getDireccion());
                Log.d("Error",usuario.getTelefono());
                String url = "http://"+MainActivity.ip+"/EmpresaLacteosServidor/rest/services/registrarUsuario/" + usuario.getDocumento() + "/" + usuario.getNombre()+"/"+usuario.getDireccion()+"/"+usuario.getTelefono();
                //String url = "http://"+MainActivity.ip+"/EmpresaLacteosServidor/rest/services/registrarUsuario/5412/JORGE/AVENIDANORTE/87452145";
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                String message=json.fromJson(response, String.class);
                if (message.equals("Success")) {
                    textSucesoUsuario.setText("registrado: "+usuario.getNombre());
                    usuarioG=usuario;
                    Intent i=new Intent(Main2Activity.this,EnterpriseActivity.class);
                    startActivity(i);
                } else {
                    textSucesoUsuario.setText("Error al registrar usuario");
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}
