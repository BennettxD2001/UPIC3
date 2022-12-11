package com.example.upic2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignIn extends AppCompatActivity {
    EditText etNombre,etApellido,etCorreo,etTelefono,etContra,etContraConfirm,etCilindraje,etMarca,etModelo,etTipoVehiculo,etAnio;
    Button btnRegistrar;
    FirebaseAuth mAuth;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        mAuth=FirebaseAuth.getInstance();
        etNombre=findViewById(R.id.etNombre);
        etApellido=findViewById(R.id.etApellido);
        etCorreo=findViewById(R.id.etCorreo);
        etTelefono=findViewById(R.id.etTelefono);
        etContra=findViewById(R.id.etContra);
        etContraConfirm=findViewById(R.id.etContraConfirm);
        etCilindraje=findViewById(R.id.etCilindraje);
        etMarca=findViewById(R.id.etMarca);
        etModelo=findViewById(R.id.etModelo);
        etTipoVehiculo=findViewById(R.id.etTipoVehiculo);
        etAnio=findViewById(R.id.etAnio);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCorreo.getText().toString().isEmpty()||etContra.getText().toString().isEmpty()||
                        etContraConfirm.getText().toString().isEmpty()||etNombre.getText().toString().isEmpty()||
                        etApellido.getText().toString().isEmpty()||etTelefono.getText().toString().isEmpty()||
                        etCilindraje.getText().toString().isEmpty()||etMarca.getText().toString().isEmpty()||
                        etModelo.getText().toString().isEmpty()||etTipoVehiculo.getText().toString().isEmpty()||
                        etAnio.getText().toString().isEmpty()){
                    Toast.makeText(SignIn.this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    if (etContra.getText().toString().equals(etContraConfirm.getText().toString())){
                        mAuth.createUserWithEmailAndPassword(etCorreo.getText().toString(),etContra.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    createDatabaseUser();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(SignIn.this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void createDatabaseUser(){
        String uid=mAuth.getCurrentUser().getUid();
        HashMap<String, Object> data=new HashMap<>();
        data.put("Nombre",etNombre.getText().toString());
        data.put("Apellido",etApellido.getText().toString());
        data.put("Correo",etCorreo.getText().toString());
        data.put("Teléfono",etTelefono.getText().toString());
        data.put("Cilindraje",etCilindraje.getText().toString());
        data.put("Marca",etMarca.getText().toString());
        data.put("Modelo",etModelo.getText().toString());
        data.put("TipoVehiculo",etTipoVehiculo.getText().toString());
        data.put("Anio",etAnio.getText().toString());
        reference.child("Usuarios/"+uid).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignIn.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(SignIn.this,MainActivity.class);
                    String uid=mAuth.getCurrentUser().getUid();
                    i.putExtra("UID",uid);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}