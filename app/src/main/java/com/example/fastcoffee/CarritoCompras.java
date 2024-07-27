// CarritoCompras.java
// CarritoCompras.java
package com.example.fastcoffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras extends AppCompatActivity implements ElementosSeleccionadosAdapter.OnCheckBoxCheckedChangeListener {

    private ImageButton volver1;
    private ImageButton next1;
    private ElementosSeleccionadosAdapter adaptadorElementosSeleccionados;
    private TextView etcarrocompras;
    private TextView tvTotal;
    private Usuario u;
    private int totalConSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);

        // Obtén la lista de productos seleccionados
        List<ProductoSeleccionado> productosSeleccionados = obtenerProductosSeleccionados();

        // Configurar RecyclerView
        RecyclerView rvCarrito = findViewById(R.id.rvCarrito);
        adaptadorElementosSeleccionados = new ElementosSeleccionadosAdapter(productosSeleccionados);
        adaptadorElementosSeleccionados.setOnCheckBoxCheckedChangeListener(this);
        rvCarrito.setLayoutManager(new LinearLayoutManager(this));
        rvCarrito.setAdapter(adaptadorElementosSeleccionados);

        // Configurar otros elementos de la interfaz
        etcarrocompras = findViewById(R.id.etcarrocompras);
        tvTotal = findViewById(R.id.tvTotal);
        volver1 = findViewById(R.id.volver1);
        next1 = findViewById(R.id.next1);

        Intent i = getIntent();
        u = i.getParcelableExtra("u");
        etcarrocompras.setText("Cliente: " + u.getNombre().substring(0, 1).toUpperCase() +
                u.getNombre().substring(1).toLowerCase() + " " + u.getApellido().substring(0, 1).toUpperCase() +
                u.getApellido().substring(1).toLowerCase());

        ocultarTeclado();
        botonVolver();
        botonSiguiente();

        // Calcular el total y mostrarlo con el Spinner
        totalConSpinner = calcularTotalConSpinner(productosSeleccionados);
        actualizarTotal(totalConSpinner);
    }

    private List<ProductoSeleccionado> obtenerProductosSeleccionados() {
        // Obtén la lista de productos seleccionados del intent
        List<Producto> productos = getIntent().getParcelableArrayListExtra("productosSeleccionados");

        // Convierte la lista de productos en una lista de ProductoSeleccionado
        List<ProductoSeleccionado> productosSeleccionados = new ArrayList<>();
        for (Producto producto : productos) {
            ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado(producto);
            productoSeleccionado.setSelected(true); // Establecer el CheckBox como activado
            productosSeleccionados.add(productoSeleccionado);
        }

        return productosSeleccionados;
    }

    private int calcularTotalConSpinner(List<ProductoSeleccionado> productosSeleccionados) {
        int total = 0;
        for (ProductoSeleccionado productoSeleccionado : productosSeleccionados) {
            if (productoSeleccionado.isSelected()) {
                total += productoSeleccionado.getProducto().getPrecio() * productoSeleccionado.getCantidadSeleccionada();
            }
        }
        return total;
    }

    private void botonVolver() {
        volver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CarritoCompras.this, Catalogo.class);
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

    @Override
    public void onCheckBoxCheckedChanged(int position, boolean isChecked) {
        totalConSpinner = calcularTotalConSpinner(adaptadorElementosSeleccionados.getProductosSeleccionados());
        actualizarTotal(totalConSpinner);
    }

    private void eliminarProducto(int position) {
        adaptadorElementosSeleccionados.getProductosSeleccionados().remove(position);
        adaptadorElementosSeleccionados.actualizarLista(adaptadorElementosSeleccionados.getProductosSeleccionados());
        totalConSpinner = calcularTotalConSpinner(adaptadorElementosSeleccionados.getProductosSeleccionados());
        actualizarTotal(totalConSpinner);
    }
    public void actualizarTotal(int total) {
        if (total == 0) {
            // Si el total es 0, muestra "Total: $0"
            tvTotal.setText("Total: $0");
            totalConSpinner = total;
        } else {
            // Si hay productos seleccionados, muestra el total normalmente
            tvTotal.setText("Total: $" + total);
        }
    }

    private void botonSiguiente() {
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalConSpinner == 0){
                    Toast.makeText(CarritoCompras.this, "Seleccione Algún Producto", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CarritoCompras.this, Catalogo.class);
                    i.putExtra("u", u);
                    startActivity(i);
                } else {
                    Intent i = new Intent(CarritoCompras.this, Pagos.class);
                    i.putExtra("u", u);
                    i.putExtra("totalConDecimales", totalConSpinner);
                    startActivity(i);
                    ocultarTeclado();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
