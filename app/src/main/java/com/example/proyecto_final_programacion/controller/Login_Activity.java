package com.example.proyecto_final_programacion.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.proyecto_final_programacion.R;
import com.example.proyecto_final_programacion.model.Usuarios_BaseDatos;

public class Login_Activity extends AppCompatActivity {

    Usuarios_BaseDatos db;
    EditText email_login;
    EditText pass_login;
    Button boton_login;
    Button boton_ir_registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        db = new Usuarios_BaseDatos(this);

        email_login = (EditText) findViewById(R.id.email_login);
        pass_login = (EditText) findViewById(R.id.pass_login);
        boton_login = (Button) findViewById(R.id.boton_login);
        boton_ir_registrar = (Button) findViewById(R.id.boton_ir_registrar);

        loadPreferences();

        boton_ir_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_Activity.this, Registro_Activity.class);
                startActivity(i);
            }
        });
        /* Tengo una base de datos local SQlite, cuando el usuario pulsa en el botón entrar, con un if comprueba que ese
        * usario coincide y que la contraseña tambien */
        boton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = email_login.getText().toString();
                String password = pass_login.getText().toString();

                if(usuario.equals("") || password.equals("")){
                    Toast("Inserte valores");
                }else{
                    Boolean comprobacionUsuario = db.comprobacionUsuario(usuario);
                    if (comprobacionUsuario == false){
                        Toast("Bienvenido");
                        //IR A MENÚ PRINCIPAL
                        Intent i = new Intent(Login_Activity.this, Equipo_Activity.class);
                        startActivity(i);
                    }else{
                        Toast("Usuario no encontrado");
                    }
                }

            }
        });
    }
    /*En los ajustes podemos cambiar el nombre del usuario y contraseña con la que se quiere iniciar*/
    public void loadPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login_Activity.this);

        String email = sharedPreferences.getString("edit_text_preference_1", "Email");
        String pass = sharedPreferences.getString("edit_text_preference_1", "Password");
        email_login.setText(email);
        pass_login.setText(pass);
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}