package com.example.fastcoffee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistroUsuario extends AppCompatActivity {

    private EditText regnom, regape, regemail, regrut, regpass,repass;
    private ImageButton volver, grabar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);


        regnom = (EditText) findViewById(R.id.regnom);
        regape = (EditText) findViewById(R.id.regape);
        regemail = (EditText) findViewById(R.id.regemail);
        regrut = (EditText) findViewById(R.id.regrut);
        regpass = (EditText) findViewById(R.id.regpass);
        repass = (EditText) findViewById(R.id.repass);
        volver = (ImageButton) findViewById(R.id.volver);
        grabar = (ImageButton) findViewById(R.id.grabar);


        botonVolver();
        botonGrabar();

    }

    private void botonVolver(){
        volver.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        Intent i = new Intent(RegistroUsuario.this, Login.class);
        startActivity(i);

    }
    });
}
    private void botonGrabar() {
        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (regnom.getText().toString().trim().isEmpty()
                        || regape.getText().toString().trim().isEmpty()
                        || regemail.getText().toString().trim().isEmpty()
                        || regrut.getText().toString().trim().isEmpty()
                        || regpass.getText().toString().trim().isEmpty()
                        || repass.getText().toString().trim().isEmpty()
                ){
                    ocultarTeclado();
                    Toast.makeText(RegistroUsuario.this, "Complete Todos Los Campos", Toast.LENGTH_LONG).show();

                } else if (!regpass.getText().toString().equals(repass.getText().toString())) {
                    ocultarTeclado();
                    Toast.makeText(RegistroUsuario.this, "Las contraseñas deben coincidir", Toast.LENGTH_LONG).show();
                } else{
                    AlertDialog.Builder a = new AlertDialog.Builder(RegistroUsuario.this);
                    a.setCancelable(false);
                    a.setTitle("LEA TERMINOS Y CONDICIONES");
                    a.setMessage("Términos y Condiciones de Uso - Fast Coffee App\n" +
                            "Vigencia: 13 de diciembre de 2023\n" +
                            "\n" +
                            "1. **Aceptación:** Al usar la aplicación Fast Coffee, acepta expresamente los términos y condiciones. Si no está de acuerdo, le instamos a no utilizar la aplicación.\n" +
                            "\n" +
                            "2. **Objeto del Servicio:** Fast Coffee permite realizar pedidos de café a través de dispositivos móviles, conectando a usuarios y la empresa para transacciones de productos y servicios.\n" +
                            "\n" +
                            "3. **Registro y Responsabilidad:** Los usuarios deben registrar una cuenta y son responsables de mantener la confidencialidad de sus credenciales. Fast Coffee no asume responsabilidad por accesos no autorizados.\n" +
                            "\n" +
                            "4. **Uso Apropiado:** Los usuarios se comprometen a un uso ético y legal, prohibiéndose actividades fraudulentas, difamatorias o comerciales no autorizadas.\n" +
                            "\n" +
                            "5. **Privacidad y Seguridad:** Se reconoce y acepta la política de privacidad de Fast Coffee, respaldada por medidas de seguridad para proteger los datos del usuario.\n" +
                            "\n" +
                            "6. **Compras y Pagos:** Al comprar, el usuario acepta pagar mediante métodos seguros y se somete a los términos del proveedor de pagos. Fast Coffee no almacena detalles de tarjetas.\n" +
                            "\n" +
                            "7. **Disponibilidad y Mantenimiento:** Se esfuerza por mantener la disponibilidad, pero no garantiza acceso ininterrumpido. Se realizan mantenimientos programados con notificación previa.\n" +
                            "\n" +
                            "8. **Cambios en Términos:** Fast Coffee puede actualizar términos en cualquier momento. Los cambios son efectivos después de su publicación. Se recomienda revisar periódicamente.\n" +
                            "\n" +
                            "9. **Finalización del Servicio:** Fast Coffee puede dar por terminado el acceso en caso de violación. El usuario puede cerrar su cuenta en cualquier momento.\n" +
                            "\n" +
                            "10. **Ley y Disputas:** Sujeto a las leyes de la República de Chile. Disputas relacionadas con estos términos se someterán a los tribunales chilenos.\n" +
                            "\n" +
                            "Al aceptar, el usuario reconoce haber leído, comprendido y aceptado todas las disposiciones establecidas.");
                    a.setIcon(R.drawable.alerta);
                    a.setNegativeButton("No Acepto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String nombre = regnom.getText().toString();
                            String apellido = regape.getText().toString();
                            String email= regemail.getText().toString();
                            String rut = regrut.getText().toString();
                            String password = regpass.getText().toString();

                            FirebaseDatabase dbss= FirebaseDatabase.getInstance();
                            DatabaseReference dbrefer = dbss.getReference(Perfiles.class.getSimpleName());
                            dbrefer.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean rep = false;
                                    for (DataSnapshot x : snapshot.getChildren()) {
                                        if (x.child("rut").getValue().toString().equalsIgnoreCase(rut)) {
                                            rep = true;
                                            ocultarTeclado();
                                            Toast.makeText(RegistroUsuario.this, "Error, El Rut " + rut + " Ya Existe", Toast.LENGTH_SHORT).show();
                                            break;

                                        }
                                    }
                                    boolean rep2 = false;
                                    for (DataSnapshot x : snapshot.getChildren()) {
                                        if (x.child("email").getValue().toString().equalsIgnoreCase(email)) {
                                            rep2 = true;
                                            ocultarTeclado();
                                            Toast.makeText(RegistroUsuario.this, "Error, El email " + email + " Ya Existe", Toast.LENGTH_SHORT).show();
                                            break;

                                        }
                                    }
                                    if (rep == false && rep2 == false) {
                                        Perfiles per = new Perfiles(nombre, apellido, email, rut, password);
                                        dbrefer.push().setValue(per);
                                        ocultarTeclado();
                                        Toast.makeText(RegistroUsuario.this, "Perfil Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegistroUsuario.this, Login.class);
                                        startActivity(i);

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                    a.show();
                }}// cierra el if y else
        });

    }//cierre btn reg

    private void ocultarTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
}
}