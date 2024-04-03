package com.uth.firebasecrudg2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {

    TextView txtNombres, txtApellidos, txtEdad, txtCorreo, txtTelefono;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtNombres = findViewById(R.id.txtNombres);
        txtEdad = findViewById(R.id.txtEdad);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            txtNombres.setText(bundle.getString("Nombres")+" "+bundle.getString("Apellidos"));
            txtEdad.setText("Edad: "+bundle.getString("Edad")+" Años");
            txtCorreo.setText("Correo: "+bundle.getString("Correo"));
            txtTelefono.setText("Telefono: "+bundle.getString("Telefono"));
            key = bundle.getString("Key");
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");

                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("Nombres",  txtNombres.getText().toString())
                        .putExtra("Apellidos", txtApellidos.getText().toString())
                        .putExtra("Edad", txtEdad.getText().toString())
                        .putExtra("Correo", txtCorreo.getText().toString())
                        .putExtra("Telefono", txtTelefono.getText().toString())
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }
}