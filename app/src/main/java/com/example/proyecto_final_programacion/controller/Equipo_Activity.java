package com.example.proyecto_final_programacion.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_programacion.R;
import com.example.proyecto_final_programacion.adapter.RecyclerAdapter;
import com.example.proyecto_final_programacion.io.HttpConnectJugadores;
import com.example.proyecto_final_programacion.model.Memes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Equipo_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    static RecyclerAdapter recAdapter;
    public static ArrayList<Memes> memelist = new ArrayList<Memes>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        /* Conexion final, le hacemos un get para coger informacion y se lo hacemos a url final que se elija
        * ,este trozo de url es la parte de donde vamos a coger la informacion */
        new taskConnections().execute("GET", "/get_memes");
        recyclerView = (RecyclerView) findViewById(R.id.recView);

        /*Aqui se esta pasando el array con la informacion ya cogida de nuestra api*/
        recAdapter = new RecyclerAdapter(memelist, Equipo_Activity.this);
        /*Conexion de nuestro layout con el reciclerView*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(layoutManager);

        /*Cargar Ajustes*/
        loadPreferences();


    }

    /*Pequeño menú para los ajustes en esta vista*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }
    /*Eleccion del menú*/
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.menu_ajustes:
                Intent i = new Intent(Equipo_Activity.this, Ajustes_Activity.class);
                startActivity(i);
                break;
        }

        return true;
    }

    /*Creamos un hilo para cargar la api*/
    class taskConnections extends AsyncTask<String, Void, String> {
        /* como solo vamos a coger datos solo importo el método Get*/
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            switch (strings[0]) {
                case "GET":
                    result = HttpConnectJugadores.getRequest(strings[1]);
                    break;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            /* se está cogiendo la informacion del json de la api, y se la vamos a pasar a un array */
            try {
                if (s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray memes = data.getJSONArray("memes");
                    String name = "";
                    String urlPrueba = "";

                    for (int i = 0; i < memes.length(); i++) {
                        name = memes.getJSONObject(i).getString("name");
                        urlPrueba = memes.getJSONObject(i).getString("url");

                        memelist.add(new Memes(name,urlPrueba));
                    }
                    recAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Equipo_Activity.this, "Problema al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /* Método para llamar en esta vista a los ajustes */
    public void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Equipo_Activity.this);
    }
}