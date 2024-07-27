package com.example.fastcoffee;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String nombre;
    private String apellido;
    private String email;
    private String rut;
    private String password;

    public Usuario(String nombre, String apellido, String email, String rut, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rut = rut;
        this.password = password;
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.email);
        dest.writeString(this.rut);
        dest.writeString(this.password);
    }

    public void readFromParcel(Parcel source) {
        this.nombre = source.readString();
        this.apellido = source.readString();
        this.email = source.readString();
        this.rut = source.readString();
        this.password = source.readString();
    }

    protected Usuario(Parcel in) {
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.email = in.readString();
        this.rut = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}