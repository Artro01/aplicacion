package unid.arturo.autos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Nuevo extends AppCompatActivity {

    private String URL = "";
    private EditText txt_marca;
    private EditText txt_modelo;
    private EditText txt_tipo;
    private EditText txt_inventario;
    private EditText txt_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_marca=(EditText) findViewById(R.id.txt_marca);
        txt_modelo=(EditText) findViewById(R.id.txt_modelo);
        txt_tipo=(EditText) findViewById(R.id.txt_tipo);
        txt_inventario=(EditText) findViewById(R.id.txt_inventario);
        txt_precio=(EditText) findViewById(R.id.txt_precio);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String marca = txt_marca.getText().toString();
                String modelo = txt_modelo.getText().toString();
                String tipo = txt_tipo.getText().toString();
                String inventario = txt_inventario.getText().toString();
                String precio = txt_precio.getText().toString();
                Uri.Builder builder = new Uri.Builder();
                URL = "https://unid2018arturo.herokuapp.com/api_autos?user_hash=12345&action=put&marca="+marca+"&modelo="+modelo+"&tipo="+tipo+"&inventario="+inventario+"&precio="+precio+"";

                webServiceRest(URL);
            }
        });
    }

    private void webServiceRest(String requestURL){
        try{
            java.net.URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            Intent inten= new Intent(Nuevo.this,Productos.class);
            startActivity(inten);
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
