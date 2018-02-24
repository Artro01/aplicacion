package unid.arturo.autos;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Productos extends AppCompatActivity {
    private ListView lv_productos_list;
    private ArrayAdapter adapter;
    private String getAllProductosURL = "https://unid2018arturo.herokuapp.com/api_autos?user_hash=12345&action=get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_productos_list = (ListView)findViewById(R.id.lv_contacts_list1);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        webServiceRest(getAllProductosURL);
        lv_productos_list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Productos.this,Nuevo.class);
                startActivity(intent);
            }
        });
    }


    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_auto;
        String modelo;
        String tipo;
        String marca;
        String precio;
        String inventario;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_auto = jsonObject.getString("id_auto");
                modelo = jsonObject.getString("modelo");
                tipo = jsonObject.getString("tipo");
                tipo = jsonObject.getString("tipo");
                marca = jsonObject.getString("marca");
                precio = jsonObject.getString("precio");
                inventario = jsonObject.getString("inventario");
                adapter.add(id_auto + ": " + modelo);
                Log.d("producto",id_auto);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
