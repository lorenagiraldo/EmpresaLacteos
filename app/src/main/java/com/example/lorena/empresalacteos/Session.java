package com.example.lorena.empresalacteos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by JORGE_ALEJANDRO on 18/09/2017.
 */

public class Session {

    public static String ip="192.168.1.57:8084";
    public Userl logIn(String document, String password, Context context)
    {
        try {
            Userl userl=new Userl(-1,document,null,null,null,null, password);
            if (userl.getUserlDocument().equals("") || userl.getUserlPassword().equals("")) {
                Toast toast1 = Toast.makeText(context.getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT);
                toast1.show();
                return null;
            } else {

                String url = "http://"+ip+"/EmpresaLacteosServidor/rest/services/userLogIn/" + userl.getUserlDocument() + "/" + userl.getUserlPassword();
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                userl=json.fromJson(response, Userl.class);
                if (userl != null) {
                    //writeFileSession(userl, context);
                    return userl;
                } else {
                    Toast toast1 = Toast.makeText(context, "No existe el usuario", Toast.LENGTH_SHORT);
                    toast1.show();
                    return null;
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
            return null;
        }
    }

    public void writeFileSession(Userl userl, Context context)
    {
        try
        {
            File directory = context.getExternalFilesDir(null);
            File file = new File(directory.getAbsolutePath(), "sessioninformation.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(userl.getUserlId()+"\n"+userl.getUserlDocument());
            outputStreamWriter.close();
        }
        catch (Exception ex)
        {
            Toast toast = Toast.makeText(context,"Error: "+ex.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
