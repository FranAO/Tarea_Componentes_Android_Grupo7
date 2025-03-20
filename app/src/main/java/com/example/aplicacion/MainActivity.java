package com.example.aplicacion;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ImageButton btnLoad;
    private Spinner spinner;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        webView = findViewById(R.id.webView);
        btnLoad = findViewById(R.id.btnLoad);
        spinner = findViewById(R.id.spinner);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Configuración de Toolbar para el DrawerLayout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configuración de ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configuración de WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setGeolocationEnabled(true);
        webView.setInitialScale(1);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "Error al cargar la página", Toast.LENGTH_SHORT).show();
            }
        });

        // Cargar la animación de rotación
        final Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);

        // Configuración de ImageButton
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aplicar la animación de rotación al botón
                btnLoad.startAnimation(rotateAnimation);

                // Cambiar el icono del ImageButton al presionar
                btnLoad.setImageResource(android.R.drawable.ic_menu_rotate);
                webView.loadUrl("https://youtu.be/QB7ACr7pUuE?si=5-m-HP2bkczN53UM");

                // Volver al icono original después de un tiempo
                btnLoad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnLoad.setImageResource(android.R.drawable.ic_menu_search); // Icono original
                    }
                }, 1000);
            }
        });

        // Configuración de Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Selecciona una opción", "Opción 1", "Opción 2", "Opción 3", "Opción 4", "Opción 5",
                        "Opción 6", "Opción 7", "Opción 8", "Opción 9", "Opción 10"});

        // Establece el adaptador al Spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
                Log.d("SpinnerDebug", "Seleccionaste: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("SpinnerDebug", "Nada seleccionado");
            }
        });

        // Configuración de NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item1) {
                    Toast.makeText(MainActivity.this, "Opción 1 seleccionada", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.item2) {
                    Toast.makeText(MainActivity.this, "Opción 2 seleccionada", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers(); // Cerrar el menú después de la selección
                return true;
            }
        });
    }
}
