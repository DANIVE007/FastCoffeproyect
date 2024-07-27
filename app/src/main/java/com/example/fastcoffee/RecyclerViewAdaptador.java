package com.example.fastcoffee;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> implements Filterable {
    private List<Producto> productoListaCompleta;
    private List<Producto> productosSeleccionados;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Nombre, Descripcion, Precio;
        RatingBar Ranquing;
        ImageView Imagen;
        private Switch switchSeleccion; // Nombre del Switch ubicado en elemento_lista.xml

        public ViewHolder(View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.textViewTitulo);
            Descripcion = itemView.findViewById(R.id.textViewDescrip);
            Precio = itemView.findViewById(R.id.textViewPrecio);
            Ranquing = itemView.findViewById(R.id.ratingBar);
            Imagen = itemView.findViewById(R.id.imageProduc);
            switchSeleccion = itemView.findViewById(R.id.Seleccion);
        }
    }

    public List<Producto> productoLista;

    public List<Producto> getProductosSeleccionados() {
        return productosSeleccionados;
    }

    public RecyclerViewAdaptador(List<Producto> productoLista) {
        this.productoLista = productoLista;
        this.productoListaCompleta = new ArrayList<>(productoLista);
        this.productosSeleccionados = new ArrayList<>(); // Inicializa la lista de productos seleccionados
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filtro = charSequence.toString().toLowerCase().trim();
                List<Producto> listaFiltrada = new ArrayList<>();

                if (productoListaCompleta == null) {
                    productoListaCompleta = new ArrayList<>(productoLista);
                }

                if (filtro.isEmpty()) {
                    listaFiltrada.addAll(productoListaCompleta);
                } else {
                    for (Producto producto : productoListaCompleta) {
                        if (producto.getNombre().toLowerCase().contains(filtro)) {
                            listaFiltrada.add(producto);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listaFiltrada;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productoLista.clear();
                productoLista.addAll((List<Producto>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Nombre.setText(productoLista.get(position).getNombre());
        holder.Descripcion.setText(productoLista.get(position).getDescripcion());

        // Agrega el signo "$" delante del precio
        String precioConSigno = "Precio $ " + String.valueOf(productoLista.get(position).getPrecio());

        // Establece el texto en negrita
        holder.Precio.setText(precioConSigno);
        holder.Precio.setTypeface(null, Typeface.BOLD);

        holder.Ranquing.setProgress((int) productoLista.get(position).getRanquing());
        holder.Imagen.setImageResource(productoLista.get(position).getImagen());
        holder.switchSeleccion.setOnCheckedChangeListener(null);
        holder.switchSeleccion.setChecked(productosSeleccionados.contains(productoLista.get(position)));

        holder.switchSeleccion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Producto producto = productoLista.get(position);

            if (isChecked) {
                // Agrega el producto a la lista de seleccionados
                if (!productosSeleccionados.contains(producto)) {
                    productosSeleccionados.add(producto);
                }
            } else {
                // Remueve el producto de la lista de seleccionados
                productosSeleccionados.remove(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productoLista.size();
    }
}


