package com.example.fastcoffee;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductoSeleccionado implements Parcelable {
    private Producto producto;
    private boolean selected;
    private int cantidadSeleccionada;

    public ProductoSeleccionado(Producto producto) {
        this.producto = producto;
        this.selected = false;
        this.cantidadSeleccionada = 1;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return producto.getNombre();
    }

    public int getImagen() {
        return producto.getImagen();
    }

    public int getPrecio() {
        return producto.getPrecio();
    }

    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(int cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.producto, flags);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeInt(this.cantidadSeleccionada);
    }

    protected ProductoSeleccionado(Parcel in) {
        this.producto = in.readParcelable(Producto.class.getClassLoader());
        this.selected = in.readByte() != 0;
        this.cantidadSeleccionada = in.readInt();
    }

    public static final Parcelable.Creator<ProductoSeleccionado> CREATOR = new Parcelable.Creator<ProductoSeleccionado>() {
        @Override
        public ProductoSeleccionado createFromParcel(Parcel source) {
            return new ProductoSeleccionado(source);
        }

        @Override
        public ProductoSeleccionado[] newArray(int size) {
            return new ProductoSeleccionado[size];
        }
    };
}
