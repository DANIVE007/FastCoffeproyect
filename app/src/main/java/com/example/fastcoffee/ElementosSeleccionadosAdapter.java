// ElementosSeleccionadosAdapter.java
package com.example.fastcoffee;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ElementosSeleccionadosAdapter extends RecyclerView.Adapter<ElementosSeleccionadosAdapter.ViewHolder> {

    private List<ProductoSeleccionado> productosSeleccionados;
    private OnCheckBoxCheckedChangeListener checkBoxCheckedChangeListener;

    public interface OnCheckBoxCheckedChangeListener {
        void onCheckBoxCheckedChanged(int position, boolean isChecked);
    }

    public ElementosSeleccionadosAdapter(List<ProductoSeleccionado> productosSeleccionados) {
        this.productosSeleccionados = productosSeleccionados;
    }

    public List<ProductoSeleccionado> getProductosSeleccionados() {
        return productosSeleccionados;
    }

    public void setOnCheckBoxCheckedChangeListener(OnCheckBoxCheckedChangeListener listener) {
        this.checkBoxCheckedChangeListener = listener;
    }

    public boolean isEmpty() {
        return productosSeleccionados.isEmpty();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_seleccionado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoSeleccionado productoSeleccionado = productosSeleccionados.get(position);

        holder.textViewTitulo.setText(productoSeleccionado.getNombre());
        String precioConEtiqueta = "Precio $ " + String.valueOf(productoSeleccionado.getPrecio());
        holder.textViewPrecio.setText(precioConEtiqueta);
        holder.imageView.setImageResource(productoSeleccionado.getImagen());

        // Manejar la lógica del CheckBox
        holder.checkBox.setChecked(productoSeleccionado.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            productoSeleccionado.setSelected(isChecked);
            // Notificar al listener cuando se selecciona o deselecciona un elemento
            if (checkBoxCheckedChangeListener != null) {
                checkBoxCheckedChangeListener.onCheckBoxCheckedChanged(position, isChecked);
            }
        });

        // Manejar la lógica del ImageButton para eliminar un elemento
        holder.deleteButton.setOnClickListener(view -> {
            productosSeleccionados.remove(position);
            notifyDataSetChanged();

            // Notificar a CarritoCompras sobre la eliminación del producto
            if (checkBoxCheckedChangeListener != null) {
                checkBoxCheckedChangeListener.onCheckBoxCheckedChanged(position, false);
            }

            if (isEmpty()) {
                // Notificar a CarritoCompras si la lista está vacía
                ((CarritoCompras) view.getContext()).actualizarTotal(0);
            }
        });

        // Manejar la lógica del Button para disminuir la cantidad
        holder.btnDisminuir.setOnClickListener(view -> {
            int cantidadActual = productoSeleccionado.getCantidadSeleccionada();
            if (cantidadActual >= 0) {
                cantidadActual--;
                productoSeleccionado.setCantidadSeleccionada(cantidadActual);
                notifyDataSetChanged();
                if (checkBoxCheckedChangeListener != null) {
                    checkBoxCheckedChangeListener.onCheckBoxCheckedChanged(position, true);
                }
            }
        });

        // Manejar la lógica del Button para aumentar la cantidad
        holder.btnAumentar.setOnClickListener(view -> {
            int cantidadActual = productoSeleccionado.getCantidadSeleccionada();
            if (cantidadActual < 99) {
                cantidadActual++;
                productoSeleccionado.setCantidadSeleccionada(cantidadActual);
                notifyDataSetChanged();
                if (checkBoxCheckedChangeListener != null) {
                    checkBoxCheckedChangeListener.onCheckBoxCheckedChanged(position, true);
                }
            }
        });

        // Actualizar la cantidad en el TextView
        holder.textViewCantidadSeleccionada.setText(String.valueOf(productoSeleccionado.getCantidadSeleccionada()));
    }
    public void actualizarLista(List<ProductoSeleccionado> nuevosProductos) {
        this.productosSeleccionados = nuevosProductos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productosSeleccionados.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitulo;
        private TextView textViewPrecio;
        private ImageView imageView;
        private CheckBox checkBox;
        private ImageButton deleteButton;
        private Button btnDisminuir;
        private TextView textViewCantidadSeleccionada;
        private Button btnAumentar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.SeleccionTitulo);
            textViewPrecio = itemView.findViewById(R.id.SeleccionPrecio);
            imageView = itemView.findViewById(R.id.imageProducSeleccionado);
            checkBox = itemView.findViewById(R.id.eleccion);
            deleteButton = itemView.findViewById(R.id.deleteButtom);
            btnDisminuir = itemView.findViewById(R.id.btnDisminuir);
            textViewCantidadSeleccionada = itemView.findViewById(R.id.textViewCantidadSeleccionada);
            btnAumentar = itemView.findViewById(R.id.btnAumentar);

            textViewCantidadSeleccionada.setText("1");
        }
    }
}
