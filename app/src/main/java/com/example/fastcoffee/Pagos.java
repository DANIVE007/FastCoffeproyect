package com.example.fastcoffee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


public class Pagos extends AppCompatActivity {
    private Button btnPagar;
    private Usuario u;
    private TextView etpago;
    private RecyclerViewAdaptador productosSeleccionados;  // Aquí se añade la inicialización del adaptador
    private ImageButton volver5;
    private int totalConDecimales;
    private TextView tituloPago;
    private EditText numeroTarjeta, nombreTitular, fechaExpiracion, cvv;
    private Spinner tipoTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        // Inicialización del adaptador (Asegúrate de pasar los parámetros necesarios)

        etpago = findViewById(R.id.etpago);
        btnPagar = findViewById(R.id.btnPagar);
        volver5 = findViewById(R.id.volver5);
        tituloPago = findViewById(R.id.tituloPago);
        numeroTarjeta = findViewById(R.id.numeroTarjeta);
        nombreTitular = findViewById(R.id.nombreTitular);
        fechaExpiracion = findViewById(R.id.fechaExpiracion);
        cvv = findViewById(R.id.cvv);
        tipoTarjeta = findViewById(R.id.tipoTarjeta);

        Intent i = getIntent();
        u = i.getParcelableExtra("u");
        totalConDecimales = i.getIntExtra("totalConDecimales", 0);

        etpago.setText("Cliente : " + u.getNombre().substring(0, 1).toUpperCase() + u.getNombre().substring(1).toLowerCase() + " " + u.getApellido().substring(0, 1).toUpperCase() + u.getApellido().substring(1).toLowerCase());

        // Mostrar el total en el TextView tituloPago
        tituloPago.setText("Ingrese Los Datos de Su Tarjeta              Total a Pagar: $" + totalConDecimales);

        ocultarTeclado();
        botonPagar();
        botonVolver();
    }

    private void botonPagar() {
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numeroTarjeta.getText().toString().trim().isEmpty()
                        || nombreTitular.getText().toString().trim().isEmpty()
                        || fechaExpiracion.getText().toString().trim().isEmpty()
                        || cvv.getText().toString().trim().isEmpty()
                        || tipoTarjeta.getSelectedItemPosition() == 0) {

                    ocultarTeclado();
                    Toast.makeText(Pagos.this, "Complete Todos Los Campos", Toast.LENGTH_LONG).show();

                } else {
                    Intent i = new Intent(Pagos.this, procesarPago.class);
                    int totalConSpinner = 0;
                    i.putExtra("totalConDecimales", totalConSpinner);
                    i.putExtra("u", u);
                    startActivity(i);
                    ocultarTeclado();
                }
            }
        });
    }

    private void botonVolver() {
        volver5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Pagos.this, Catalogo.class);
                i.putExtra("u", u);
                startActivity(i);
                ocultarTeclado();
            }
        });
    }

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