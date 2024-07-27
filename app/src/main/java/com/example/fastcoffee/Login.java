package com.example.fastcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Login extends AppCompatActivity {
    private EditText txtusu, txtpas;
    private Button btnini;
    private Button btnregistrar;


    private void ocultarTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtusu = (EditText) findViewById(R.id.txtusu);
        txtpas = (EditText) findViewById(R.id.txtpas);
        btnini = (Button) findViewById(R.id.btnini);
        btnregistrar= (Button) findViewById(R.id.btnregistrar);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, RegistroUsuario.class);
                startActivity(i);

            }

        });

        btnini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtusu.getText().toString().trim().isEmpty() || txtpas.getText().toString().trim().isEmpty()) {
                    Toast.makeText(Login.this, "Complete los Campos para Continuar", Toast.LENGTH_SHORT).show();
                    ocultarTeclado();
                } else {

                    String nombre = txtusu.getText().toString();
                    String apellido = "";
                    String email = nombre + "@gmail.com";
                    String rut = "";
                    String password = txtpas.getText().toString();


                    if (nombre.equalsIgnoreCase("ADMIN") && password.equalsIgnoreCase("inacap")) {
                        rut = "0.000.000-0";
                        apellido = "Administrador";
                        Usuario u;
                        u = new Usuario(nombre, apellido, email, rut, password);
                        Intent i = new Intent(Login.this, Catalogo.class);
                        i.putExtra("u", u);
                        startActivity(i);
                        finish();
                    }else{
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference dbref = db.getReference(Perfiles.class.getSimpleName());
                        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean respuesta =false;
                                for (DataSnapshot x : snapshot.getChildren()) {
                                    if (x.child("nombre").getValue().toString().equalsIgnoreCase(nombre) && x.child("password").getValue().toString().equalsIgnoreCase(password)) {
                                        respuesta = true;
                                        String apellido = x.child("apellido").getValue().toString();
                                        String email = x.child("email").getValue().toString();
                                        String rut = x.child("rut").getValue().toString();

                                        Toast.makeText(Login.this, "Usuario Encontrado", Toast.LENGTH_SHORT).show();
                                        Usuario u = new Usuario(nombre, apellido, email, rut, password);
                                        Intent i = new Intent(Login.this, Catalogo.class);
                                        i.putExtra("u", u);
                                        startActivity(i);
                                        ocultarTeclado();
                                        break;

                                    }

                                }
                                if (respuesta == false){

                                    Toast.makeText(Login.this, "Usuario No Encontrado", Toast.LENGTH_SHORT).show();
                                    txtusu.setText("");
                                    txtpas.setText("");
                                    txtusu.requestFocus();
                                    ocultarTeclado();                                 }
                                    return;

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Login.this, "Error de Usuario y/o Contraseña", Toast.LENGTH_SHORT).show();
                                txtusu.setText("");
                                txtpas.setText("");
                                txtusu.requestFocus();
                                ocultarTeclado();

                            }

                        });


                    }
                }
            }

        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}