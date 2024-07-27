package com.example.fastcoffee;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {
    private String nombre;
    private String descripcion;
    private int precio;
    private int ranquing;
    private int imagen;

    public Producto(String nombre, String descripcion, int precio,int ranquing, int imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ranquing = ranquing;
        this.imagen = imagen;
    }

    protected Producto(Parcel in) {
        nombre = in.readString();
        descripcion = in.readString();
        precio = in.readInt();
        ranquing = in.readInt();
        imagen = in.readInt();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecio() {
        return precio;
    }
    public double getRanquing() {
        return ranquing;
    }


    public int getImagen() {
        return imagen;
    }


    // Resto de la implementación Parcelable...
    // ...

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeInt(precio);
        dest.writeInt(ranquing);
        dest.writeInt(imagen);
    }
}