package com.example.fastcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Boleta extends AppCompatActivity {
private ImageView imageBoleta;
private ImageButton volver3;
private TextView etbol;
private Usuario u;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boleta);

        imageBoleta=(ImageView) findViewById(R.id.imageBoleta);
        volver3=(ImageButton) findViewById(R.id.volver3);
        etbol=(TextView) findViewById(R.id.etbol);
        Intent i = getIntent();
        u = (Usuario) i.getParcelableExtra("u");
        etbol.setText("Cliente : " + u.getNombre().substring(0, 1).toUpperCase()+ u.getNombre().substring(1).toLowerCase()+ " "+ u.getApellido().substring(0, 1).toUpperCase()+ u.getApellido().substring(1).toLowerCase());


        ocultarTeclado();
        botonVolver();


    }

    private void botonVolver(){
        volver3.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent i = new Intent(Boleta.this, CodigoQR.class);
                i.putExtra("u", u);
                startActivity(i);
                ocultarTeclado();

            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void ocultarTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }



    }
}