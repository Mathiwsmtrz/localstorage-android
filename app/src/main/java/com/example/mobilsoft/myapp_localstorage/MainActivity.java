package com.example.mobilsoft.myapp_localstorage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText txt_text_info;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_text_info = (EditText) findViewById(R.id.txt_info);
        createResources();
    }

    public void createResources(){
        try {
            File nuevaCarpeta = new File(Environment.getExternalStorageDirectory(), "dataStorage");
            if (!nuevaCarpeta.exists()) {
                nuevaCarpeta.mkdir();
                Toast.makeText(getApplicationContext(),"Se creo la carpeta", Toast.LENGTH_SHORT).show();
            }
            try {
                File file = new File(nuevaCarpeta, "data" + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                    Toast.makeText(getApplicationContext(),"Se creo el txt", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),"Error " +" e: " + ex, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Error " +" e: " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void loadData(View view){
        try{
            FileInputStream fis = openFileInput("data.txt");
            InputStreamReader isr = new InputStreamReader(fis);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";

            int charRead;
            while((charRead = isr.read(inputBuffer)) > 0){
                // Convertimos los char a String
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;

                inputBuffer = new char[READ_BLOCK_SIZE];
            }

            // Establecemos en el EditText el texto que hemos leido
            txt_text_info.setText(s);

            // Mostramos un Toast con el proceso completado
            Toast.makeText(getBaseContext(), "Cargado", Toast.LENGTH_SHORT).show();

            isr.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void saveData(View view){
        String dataInfo =  txt_text_info.getText().toString();

        try{
            FileOutputStream fos = openFileOutput("data.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            // Escribimos el String en el archivo
            osw.write(dataInfo);
            osw.flush();
            osw.close();

            // Mostramos que se ha guardado
            Toast.makeText(getBaseContext(), "Guardado", Toast.LENGTH_SHORT).show();

            txt_text_info.setText("");
        }catch (IOException ex){
            Toast.makeText(getApplicationContext(),"Error " +" e: " + ex, Toast.LENGTH_LONG).show();
        }
    }
}
