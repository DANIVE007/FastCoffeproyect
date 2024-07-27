package com.example.fastcoffee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;

import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Catalogo extends AppCompatActivity {
    private SearchView buscar;
    private TextView etcatalogo;
    private RecyclerView listaproductos;
    private RecyclerViewAdaptador adaptadorProducto;
    private ImageButton volver;
    private ImageButton next;
    private Usuario u;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        buscar = findViewById(R.id.buscar);
        etcatalogo = findViewById(R.id.etcatalogo);
        volver = findViewById(R.id.volver);
        next = findViewById(R.id.next);
        listaproductos = findViewById(R.id.listaproductos);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        // Obtener referencia al elemento del menú
        MenuItem cerrarSesionItem = navigationView.getMenu().findItem(R.id.nav_cerrar_sesion);

        // Configurar un OnClickListener para el elemento del menú
        cerrarSesionItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder a = new AlertDialog.Builder(Catalogo.this);
                a.setCancelable(false);
                a.setTitle("Pregunta");
                a.setMessage("¿Esta seguro de cerrar la sesion?");
                a.setIcon(R.drawable.exit);
                a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acciones al hacer clic en Cancelar
                    }
                });
                a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent x = new Intent(Catalogo.this, Login.class);
                        startActivity(x);
                    }
                });
                a.show();
                  return true;
            }
});


        // Configuración del botón del menú lateral
        ImageButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }

        });

        listaproductos.setLayoutManager(new LinearLayoutManager(this));
        buscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
                    ocultarTeclado();
                    return true; // Consumir el evento
                }
                return false;
            }
        });

        adaptadorProducto = new RecyclerViewAdaptador(obtenerProductos());
        listaproductos.setAdapter(adaptadorProducto);

        Intent i = getIntent();
        u = i.getParcelableExtra("u");
        etcatalogo.setText("Bienvenido(a): " + u.getNombre().substring(0, 1).toUpperCase() +
                u.getNombre().substring(1).toLowerCase() + " " + u.getApellido().substring(0, 1).toUpperCase() +
                u.getApellido().substring(1).toLowerCase());

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navNombre = headerView.findViewById(R.id.navNombre);
        TextView navApellido = headerView.findViewById(R.id.navApellido);
        TextView navRut = headerView.findViewById(R.id.navRut);
        TextView navEmail = headerView.findViewById(R.id.navEmail);



        // Establece los valores en los elementos del menú
        navNombre.setText(u.getNombre().substring(0, 1).toUpperCase() +u.getNombre().substring(1).toLowerCase());
        navApellido.setText(u.getApellido().substring(0, 1).toUpperCase() +u.getApellido().substring(1).toLowerCase());
        navRut.setText(u.getRut());
        navEmail.setText(u.getEmail());

        botonVolver();
        botonSiguiente();
        configurarBusqueda();
        ocultarTeclado();

    }


    public List<Producto> obtenerProductos() {
        List<Producto> Productoss = new ArrayList<>();
        Productoss.add(new Producto("Capuccino Vainilla Fast", "Bebida a base de leche, café arabico, leche descremada, esencia de vainilla y canela", 4500, 7,R.drawable.capuccinovainilla));
        Productoss.add(new Producto("Mocaccino Fast", "Bebida a base de leche y Chocolate Negro, café arabico, leche descremada", 4600, 8,R.drawable.mocaccino));
        Productoss.add(new Producto("Cortado Express Fast", "Bebida de café arabico concentrado, 150 ml", 3800, 9,R.drawable.cafecortadosimple));
        Productoss.add(new Producto("Late Fast", "Bebida a base de leche y evaporizada con crema y sabores, sin lactosa ", 4100, 6,R.drawable.cafeconlechesinlactosa));
        Productoss.add(new Producto("Crossant Fast", "Sandwich de Crossant, en capas finas de masa madre rellenas con jamón y queso", 2900, 10,R.drawable.crossant));
        Productoss.add(new Producto("Muffin Chocolate Fast", "Queque en base a Chocolate, Chips de Chocolate amargo, ", 4100, 6,R.drawable.muffinchocolate));
        Productoss.add(new Producto("Te Frio Sabores Fast", "Te Helados refescantes, sabores de Piña, Veredemanzana, e InfusiónesLima", 3600, 10,R.drawable.tefriosabores));
        Productoss.add(new Producto("Prosciutto Mozzarella Fast", "Sandwich italiano, pan especial, con queso Mozzarela, jamon serrano, lechuga y tomate", 4900, 9,R.drawable.prosciuttomozzarella));
        Productoss.add(new Producto("Sandwich Naturista Fast", "Sandiwch a base de jamon acaramelizado, lechuga corlizam y tomates frescos,con pan integral", 3800, 7,R.drawable.sandwichjamonnaturista));
        Productoss.add(new Producto("Sandwich Ketto Jamon Queso Fast", "Swandwich con masa keto de harina de coco, Jamon cocido, y queso y huevo", 4500, 8,R.drawable.sandwichjamonqueso));
        return Productoss;
    }

    public void botonVolver() {
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a = new AlertDialog.Builder(Catalogo.this);
                a.setCancelable(false);
                a.setTitle("Pregunta");
                a.setMessage("¿Esta seguro de cerrar la sesion?");
                a.setIcon(R.drawable.exit);
                a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acciones al hacer clic en Cancelar
                    }
                });
                a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent x = new Intent(Catalogo.this, Login.class);
                        startActivity(x);
                    }
                });
                a.show();
            }
        });
    }

    private void botonSiguiente() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Producto> productosSeleccionados = adaptadorProducto.getProductosSeleccionados();
                Intent i = new Intent(Catalogo.this, CarritoCompras.class);
                i.putExtra("u", u);
                i.putParcelableArrayListExtra("productosSeleccionados", new ArrayList<>(productosSeleccionados));
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            ocultarTeclado();
            return true; // Indica que la tecla ha sido manejada
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        // Acciones al presionar el botón Atrás
    }

    private void ocultarTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void configurarBusqueda() {
        buscar.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ocultarTeclado();
                    listaproductos.requestFocus();
                }
            }
        });

        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscar.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptadorProducto.getFilter().filter(newText);
                return true;
            }
        });
    }
}