package com.example.fastcoffee;

import android.os.Parcel;
import android.os.Parcelable;

public class Productos implements Parcelable {
    private String Nombre;
    private String Descripcion;
    private Integer Precio;
    private String Imagen;

    public Productos() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Integer getPrecio() {
        return Precio;
    }

    public void setPrecio(Integer precio) {
        Precio = precio;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    @Override
    public String toString() {
        return Nombre +
                "                 $    " + Precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Nombre);
        dest.writeString(this.Descripcion);
        dest.writeValue(this.Precio);
        dest.writeString(this.Imagen);
    }

    public void readFromParcel(Parcel source) {
        this.Nombre = source.readString();
        this.Descripcion = source.readString();
        this.Precio = (Integer) source.readValue(Integer.class.getClassLoader());
        this.Imagen = source.readString();
    }

    protected Productos(Parcel in) {
        this.Nombre = in.readString();
        this.Descripcion = in.readString();
        this.Precio = (Integer) in.readValue(Integer.class.getClassLoader());
        this.Imagen = in.readString();
    }

    public static final Parcelable.Creator<Productos> CREATOR = new Parcelable.Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel source) {
            return new Productos(source);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };
}