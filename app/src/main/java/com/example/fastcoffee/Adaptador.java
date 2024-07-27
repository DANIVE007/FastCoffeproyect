package com.example.fastcoffee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context contexto;
    String[][] datos;
    int [] datosimg;
    private List<String[]> datosFiltrados;
    private List<Integer> indicesFiltrados;

    public Adaptador(Context contexto, String[][] datos, int[] datosimg) {
        this.contexto = contexto;
        this.datos = datos;
        this.datosimg = datosimg;
        this.datosFiltrados = new ArrayList<>(datos.length);
        this.indicesFiltrados = new ArrayList<>();

        for (String[] dato : datos) {
            datosFiltrados.add(dato.clone());
        }

        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.elemento_lista,null);
        TextView NombreProducto = (TextView) vista.findViewById(R.id.textViewTitulo);
        TextView DescripciónProducto = (TextView) vista.findViewById(R.id.textViewDescrip);
        TextView Precio = (TextView) vista.findViewById(R.id.textViewPrecio);
        RatingBar calificacion = (RatingBar) vista.findViewById(R.id.ratingBar);
        ImageView imagen = (ImageView) vista.findViewById(R.id.imageProduc);


        NombreProducto.setText(datos[i][0]);
        DescripciónProducto.setText(datos [i][1]);
        Precio.setText("Precio  $ :" + datos[i][2]);
        imagen.setImageResource(datosimg[i]);
        calificacion.setProgress(Integer.valueOf(datos[i][3]));


        return vista;
    }
    public void filtrar(String query) {
        datosFiltrados.clear();
        indicesFiltrados.clear();

        // Itera sobre todos los productos y filtra aquellos que coincidan con la consulta
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0].toLowerCase().contains(query.toLowerCase())) {
                datosFiltrados.add(datos[i].clone());
                indicesFiltrados.add(i);
            }
        }

        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }


    @Override
    public int getCount() {
        return datosimg.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
