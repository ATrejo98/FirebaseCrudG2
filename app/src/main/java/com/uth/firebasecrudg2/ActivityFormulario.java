package com.uth.firebasecrudg2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uth.firebasecrudg2.model.Persona;


public class ActivityFormulario extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtEdad, txtCorreo, txtTelefono;

    Button btnGuardar;
    FloatingActionButton fabEditar, fabEliminar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("usuarios");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombres = txtNombres.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String edad = txtEdad.getText().toString();
                String correo = txtCorreo.getText().toString();
                String telefono = txtTelefono.getText().toString();

                if (nombres.isEmpty() || apellidos.isEmpty() || edad.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                    // Mostrar mensaje de error
                    Toast.makeText(ActivityFormulario.this, "Falta rellenar uno o más campos", Toast.LENGTH_SHORT).show();
                } else {


                    Persona persona = new Persona(null, nombres, apellidos, edad, correo, telefono);

                    String clave = databaseReference.push().getKey();

                    databaseReference.child(clave).setValue(persona).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(ActivityFormulario.this, "Datos guardados correctamente en Firebase", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            } else {

                                Toast.makeText(ActivityFormulario.this, "Error al guardar los datos en Firebase", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }
}