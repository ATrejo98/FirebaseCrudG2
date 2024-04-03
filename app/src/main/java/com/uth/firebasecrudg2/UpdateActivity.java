package com.uth.firebasecrudg2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uth.firebasecrudg2.model.Persona;

public class UpdateActivity extends AppCompatActivity {

    Button updateButton;
    EditText txtNombres, txtApellidos, txtEdad, txtCorreo, txtTelefono;
    String key;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateButton = findViewById(R.id.updateButton);
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            txtNombres.setText(bundle.getString("Nombres"));
            txtApellidos.setText(bundle.getString("Apellidos"));
            txtEdad.setText(bundle.getString("Edad"));
            txtCorreo.setText(bundle.getString("Correo"));
            txtTelefono.setText(bundle.getString("Telefono"));
            key = bundle.getString("Key");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios").child(key);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateData(){
        String nombres = txtNombres.getText().toString().trim();
        String apellidos = txtApellidos.getText().toString().trim();
        String edad = txtEdad.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();

        Persona dataClass = new Persona(null, nombres, apellidos, edad, correo, telefono);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}