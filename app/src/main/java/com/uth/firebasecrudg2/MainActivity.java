package com.uth.firebasecrudg2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uth.firebasecrudg2.model.Persona;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SearchView txtBuscar;
    ArrayList<Persona> listaArrayPersonas;
    FloatingActionButton fabNuevo;
    FloatingActionButton fabAbout;
    ListView ListaPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.txtBuscar);
        ListaPersonas = findViewById(R.id.listPersonas);
        fabNuevo = findViewById(R.id.favNuevo);
        fabAbout = findViewById(R.id.favAbout);

    }
}