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

    private EditText editCorreoUsuario;
    private EditText editPasswordUsuario;
    private TextView textSucesoUsuario;
    private Button botonIniciarSesion;
    public static Usuario usuarioG=null;
    public static Pedido pedidoG=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editCorreoUsuario=(EditText) findViewById(R.id.editCorreoUsuario);
        editPasswordUsuario=(EditText) findViewById(R.id.editPasswordUsuario);
        textSucesoUsuario=(TextView) findViewById(R.id.textSucesoUsuario);
        botonIniciarSesion=(Button)findViewById(R.id.botonIniciarSesion);
        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo=editCorreoUsuario.getText().toString();
                String password=editPasswordUsuario.getText().toString();
                Usuario usuario=new Usuario(-1,correo,password);
                iniciarSesion(usuario);
            }
        });
    }

    private void iniciarSesion(Usuario usuario)
    {
        try {
            if (usuario.getCorreoUsuario().equals("") || usuario.getPasswordUsuario().equals("")) {
                textSucesoUsuario.setText("Error campos vacios");
            } else {
                String url = "http://"+MainActivity.ip+"iniciarSesion/" + usuario.getCorreoUsuario() + "/" + usuario.getPasswordUsuario();

                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                String message=json.fromJson(response, String.class);
                if (message.equals("Exito")) {
                    textSucesoUsuario.setText("registrado: "+usuario.getCorreoUsuario());
                    usuarioG=usuario;
                    Intent i=new Intent(Main2Activity.this,EnterpriseActivity.class);
                    startActivity(i);
                } else {
                    textSucesoUsuario.setText("Error al registrar usuario");
                }

            Intent i=new Intent(Main2Activity.this,EnterpriseActivity.class);
            startActivity(i);
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}
