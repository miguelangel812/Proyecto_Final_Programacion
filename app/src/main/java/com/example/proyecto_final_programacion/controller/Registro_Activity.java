package com.example.proyecto_final_programacion.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_programacion.R;
import com.example.proyecto_final_programacion.model.Usuarios_BaseDatos;


public class Registro_Activity extends AppCompatActivity {

    Usuarios_BaseDatos db;

    Button boton_registrarse;

    EditText email_registro;
    EditText contraseña_registro;
    EditText contraseña_registro_repetida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new Usuarios_BaseDatos(this);

        email_registro = (EditText) findViewById(R.id.email_registro);
        contraseña_registro = (EditText) findViewById(R.id.contraseña_registro);
        contraseña_registro_repetida = (EditText) findViewById(R.id.contraseña_registro_repetida);
        boton_registrarse = (Button) findViewById(R.id.boton_registrarse);

        /* Cuando se pulsa se coge aquello que se haya escrito y se guarda en nuestra base de datos local*/
        boton_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = email_registro.getText().toString();
                String password = contraseña_registro.getText().toString();
                String password_rep = contraseña_registro_repetida.getText().toString();

                if(usuario.equals("") || password.equals("")  || password_rep.equals("")){
                    Toast("Inserte valores");
                }else{
                    Boolean comprobacionUsuario = db.comprobacionUsuario(usuario);
                    if (comprobacionUsuario == true){
                        if(password.equals(password_rep)){
                            Boolean insert = db.insert(usuario,password);
                            if (insert == true){
                                Toast("Registro Completado");
                                finish();
                            }
                        }else{
                            Toast("La contraseña debe de ser igual en los dos campos");
                        }
                    }else{
                        Toast("Error, El email ya está registrado");
                    }
                }

            }
        });


    }
    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
