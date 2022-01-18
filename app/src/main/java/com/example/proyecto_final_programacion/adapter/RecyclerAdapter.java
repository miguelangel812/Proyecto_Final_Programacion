package com.example.proyecto_final_programacion.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_programacion.R;
import com.example.proyecto_final_programacion.controller.Meme_Activity;
import com.example.proyecto_final_programacion.model.Memes;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>{


    /* Creo un contexto para poder llamar a otra actividad */
    Context context;
    List<Memes> memelist;
    Activity activity;


    /*Constructor de la clase, con el activity, para poder llamarlo*/
    public RecyclerAdapter(List<Memes> memelist, Activity activity) {
        this.memelist = memelist;
        this.activity = activity;
    }


     /*Con el inflate unimos el reciclerview con el layout*/
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        return recyclerHolder;
    }

    /*Ya tenemos el layout unido a nuestro reciclerview, y aqui le pasamos la iformacion*/
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Memes jugador = memelist.get(position);

        /*Metodo para mostrar imagenes desde una url*/
        Glide.with(activity)
                .load(jugador.getUrl_img())
                .error(R.drawable.principal)
                .into(holder.imgViewMeme);

        holder.setOnClickListener();
    }

    /*Tamaño del arrray*/
    @Override
    public int getItemCount() {
        return memelist.size();
    }

    /*Cuando hagamos el click en una imagen, le pasaremos la imagen, que haciendo el get(pos), conseguimos todo */

    public void segundaPagina(int pos){
        Memes jugador = memelist.get(pos);

        Intent i = new Intent(context, Meme_Activity.class)
                /*Cada putExtrs es informacion que pasamos a la siguiente activity*/
                .putExtra("IMAGEN", jugador.getUrl_img())
                .putExtra("DESCRIPCION",jugador.getDescripcion())
                .putExtra("NUMERO",pos);
        context.startActivity(i);
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;

        ImageView imgViewMeme;
        //TextView  txtViewDesc;


        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            imgViewMeme = (ImageView)  itemView.findViewById(R.id.img_item_biblioteca);
            itemView.setOnClickListener(this);
        }

        /*Donde podemos clicar*/
        void setOnClickListener(){

            imgViewMeme.setOnClickListener(this);
//            imgViewMeme.setOnLongClickListener(this);
        }


        /*metodo para poder clicar una imagen, aqui le pasamoe la posicion, con esa posicion sabemos que imagen ha clicado y  podemos
        * sacar toda la informacion del array*/
        @Override
        public void onClick(View v) {
            segundaPagina(getLayoutPosition());
        }
    }





/*
    public ActionMode.Callback mActionCallback = new ActionMode.Callback() {
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

                    mode.finish();

                    break;
            }

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {mActionMode = null;}
    };*/

}
