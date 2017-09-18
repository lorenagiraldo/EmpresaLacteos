package com.example.lorena.empresalacteos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editDocument;
    private EditText editPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editDocument=(EditText)findViewById(R.id.editDocument);
        editPassword=(EditText)findViewById(R.id.editPassword);
        login=(Button)findViewById(R.id.logIn);
        final Session session= new Session();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String document = editDocument.getText().toString();
                String password = editPassword.getText().toString();
                Userl userl=session.logIn(document, password, getApplicationContext());
                if (userl!=null)
                {
                    Intent i=new Intent(MainActivity.this,EnterpriseActivity.class);
                    i.putExtra("userl", userl);
                    startActivity(i);
                }
            }
        });
    }
}