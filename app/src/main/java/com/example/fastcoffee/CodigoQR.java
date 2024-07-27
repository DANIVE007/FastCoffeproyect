package com.example.fastcoffee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CodigoQR extends AppCompatActivity {
    private TextView tiempoRestante;
    private ImageButton volver2;
    private ImageButton boleta;
    private Button btnIniciar;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private TextView etQR;
    private boolean temporizadorEnProgreso = false;
    private Usuario u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr);

        tiempoRestante = findViewById(R.id.tiempoRestante);
        btnIniciar = findViewById(R.id.btnIniciar);
        progressBar = findViewById(R.id.progressBar);
        volver2= (ImageButton)  findViewById(R.id.volver2);
        boleta= (ImageButton)  findViewById(R.id.boleta);
        etQR = (TextView) findViewById(R.id.etQR);

        Intent i = getIntent();
        u = (Usuario) i.getParcelableExtra("u");
        etQR.setText("Cliente : " + u.getNombre().substring(0, 1).toUpperCase()+ u.getNombre().substring(1).toLowerCase()+ " "+ u.getApellido().substring(0, 1).toUpperCase()+ u.getApellido().substring(1).toLowerCase());
        ocultarTeclado();
        botonVolver();
        botonBoleta();
        iniciarTemporizador();

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!temporizadorEnProgreso) {
                    iniciarTemporizador();
                } else {
                    detenerTemporizador();
                }
            }
        });
    }

    private void iniciarTemporizador() {
        temporizadorEnProgreso = true;

        countDownTimer = new CountDownTimer(07 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                actualizarTiempoRestante(millisUntilFinished);
                actualizarProgressBar(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                temporizadorEnProgreso = false;
                tiempoRestante.setText("00:00:00");
                progressBar.setProgress(0);
            }
        }.start();

        btnIniciar.setText("Detener");
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
    private void detenerTemporizador() {
        temporizadorEnProgreso = false;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        btnIniciar.setText("Iniciar");
    }

    private void actualizarTiempoRestante(long millisUntilFinished) {
        int segundos = (int) (millisUntilFinished / 1000);
        int minutos = segundos / 60;
        int horas = minutos / 60;

        segundos = segundos % 60;
        minutos = minutos % 60;

        tiempoRestante.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
    }

    private void actualizarProgressBar(long millisUntilFinished) {
        int progreso = (int) ((07 * 60 * 1000 - millisUntilFinished) * 100 / (15 * 60 * 1000));
        progressBar.setProgress(progreso);
    }


    private void botonVolver(){
        volver2.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                AlertDialog.Builder a = new AlertDialog.Builder(CodigoQR.this);
                a.setCancelable(false);
                a.setTitle("Pregunta");
                a.setMessage("¿Esta Seguro de Querer Volver al Catalogo?");
                a.setIcon(R.drawable.exit);
                a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent x = new Intent(CodigoQR.this, Catalogo.class);
                        x.putExtra("u", u);
                        startActivity(x);
                    }
                });
                a.show();
            }


        });
    }
    private void botonBoleta(){
        boleta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Intent i = new Intent(CodigoQR.this, Boleta.class);
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



}

