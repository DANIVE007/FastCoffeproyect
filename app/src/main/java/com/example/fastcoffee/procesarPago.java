package com.example.fastcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class procesarPago extends AppCompatActivity {

    private Usuario u;
    private Button btnIniciar;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private TextView tiempoRestante,etprocpac;
    private boolean temporizadorEnProgreso = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesar_pago);

        tiempoRestante = findViewById(R.id.tiempoRestante);
        progressBar = findViewById(R.id.progressBar);
        btnIniciar = findViewById(R.id.btnIniciar);
        etprocpac=(TextView) findViewById(R.id.etprocpac);
        Intent i = getIntent();
        u = i.getParcelableExtra("u");
        etprocpac.setText("Cliente : " + u.getNombre().substring(0, 1).toUpperCase() + u.getNombre().substring(1).toLowerCase() + " " + u.getApellido().substring(0, 1).toUpperCase() + u.getApellido().substring(1).toLowerCase());


        // Configurar la ProgressBar para avanzar durante 5 segundos
        progressBar.setMax(5000);

        // Iniciar temporizador y ProgressBar
        iniciarTemporizador();

        // Mostrar el layout durante 5 segundos (ajustado al tiempo de la ProgressBar)
        int tiempoMostrandoLayout = 5000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cambiar automáticamente a otro layout o actividad aquí
                // Ejemplo: Cambiar a MainActivity después de 5 segundos
                Intent i = new Intent(procesarPago.this, CodigoQR.class);
                i.putExtra("u", u);
                startActivity(i);
                ocultarTeclado();

                // Cierra la actividad actual
                finish();
            }
        }, tiempoMostrandoLayout);
    }

    private void iniciarTemporizador() {
        temporizadorEnProgreso = true;

        countDownTimer = new CountDownTimer(5000, 1000) {
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

    private void actualizarProgressBar(long millisUntilFinished) {
        int progreso = (int) (5000 - millisUntilFinished);
        progressBar.setProgress(progreso);
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

