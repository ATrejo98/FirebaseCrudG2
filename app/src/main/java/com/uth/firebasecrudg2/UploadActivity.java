package com.uth.firebasecrudg2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uth.firebasecrudg2.model.Persona;
import com.uth.firebasecrudg2.model.PersonaBK;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadActivity extends AppCompatActivity {

    Button saveButton;
    EditText txtNombres, txtApellidos, txtEdad, txtCorreo, txtTelefono;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        saveButton = findViewById(R.id.saveButton);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("usuarios");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombres = txtNombres.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String edad = txtEdad.getText().toString();
                String correo = txtCorreo.getText().toString();
                String telefono = txtTelefono.getText().toString();

                if (nombres.isEmpty() || apellidos.isEmpty() || edad.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                    // Mostrar mensaje de error
                    Toast.makeText(UploadActivity.this, "Falta rellenar uno o más campos", Toast.LENGTH_SHORT).show();
                } else {


                    Persona persona = new Persona(null, nombres, apellidos, edad, correo, telefono);

                    String clave = databaseReference.push().getKey();

                    databaseReference.child(clave).setValue(persona).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(UploadActivity.this, "Datos guardados correctamente en Firebase", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            } else {

                                Toast.makeText(UploadActivity.this, "Error al guardar los datos en Firebase", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    public void uploadData(){

        String nombres = txtNombres.getText().toString().trim();
        String apellidos = txtApellidos.getText().toString().trim();
        String edad = txtEdad.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();

        Persona dataClass = new Persona(null, nombres, apellidos, edad, correo, telefono);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("usuarios").child(currentDate)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");

        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
        startActivity(intent);
    }
}