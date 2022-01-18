package com.example.proyecto_final_programacion.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_programacion.R;

public class Meme_Activity extends AppCompatActivity{

    ImageView imgMeme;
    TextView txtMeme;
    String des,url;
    private androidx.appcompat.view.ActionMode mActionMode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        imgMeme = (ImageView)  findViewById(R.id.imgMeme);
        txtMeme = (TextView)  findViewById(R.id.txtMeme);

        Intent i = getIntent();
        /*Cogiendo la informacion recibida de la otra actividad y cargandola en la bista*/
        if(i !=null){

            des = i.getStringExtra("DESCRIPCION");
            txtMeme.setText(des);

            url = i.getStringExtra("IMAGEN");
            Glide.with(this)
                    .load(url)
                    .error(R.drawable.principal)
                    .into(imgMeme);
        }
        imgMeme.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(mActionMode != null){
                    return false;
                }

                mActionMode = startSupportActionMode(mActionCallback);
                return true;
            }
        });
    }


    /*Menú normal*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guardar,menu);

        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.eliminar_meme:
                /*Llamo al metodo de createAlertDialog() ya que solo quiero que haga una cosa,
                * cuando lo llamo me pregunta si quiero eliminar, en caso de Si, eliminará*/
                AlertDialog alertDialog = createAlertDialog();
                alertDialog.show();


                break;
            case R.id.menu_ajustes:
                /*Opción del menú para ir a ajustes*/
                Intent i = new Intent(Meme_Activity.this, Ajustes_Activity.class);
                startActivity(i);
                break;
        }

        return true;
    }
    /*El método para que me pregunte si quiero eliminar una imagen*/
    public AlertDialog createAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Meme_Activity.this);

        builder.setMessage("¿Estás seguro de querer eliminar el meme?")
                .setTitle("Eliminar Meme");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int pos = -1;

                for (int j = 0; j < Equipo_Activity.memelist.size(); j++){

                    if (Equipo_Activity.memelist.get(j).getDescripcion().equals(des)){
                        pos = j;
                    }

                }
                if (pos != -1){
                    Equipo_Activity.memelist.remove(pos);
                    Equipo_Activity.recAdapter.notifyDataSetChanged();
                    finish();
                }
                Toast.makeText(Meme_Activity.this, "Eliminado", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Meme_Activity.this, "Has elegido no eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
/*
    @Override
    public boolean onLongClick(View v) {
        if (mActionMode != null){
            return false;
        }
        mActionMode = startSupportActionMode((androidx.appcompat.view.ActionMode.Callback) mActionModeCallback);


        return true;
    }*/
    private ActionMode.Callback mActionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_menu,menu);
            mode.setTitle("Eliminar");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();
            switch(itemId){
                case R.id.action_eliminar:
                    AlertDialog alertDialog = createAlertDialog();
                    alertDialog.show();
                    mode.finish();
                    break;
            }

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {mActionMode = null;}
    };
}

