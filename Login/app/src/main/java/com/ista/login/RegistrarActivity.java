package com.ista.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.collect.Iterators;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarActivity extends AppCompatActivity {
EditText txtcorreo,txtcontra;
Button btnregis,btnlogin1;
FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        mAuth=FirebaseAuth.getInstance();
        txtcorreo= findViewById(R.id.txtcorreo);
        txtcontra= findViewById(R.id.txtcontra);
        btnregis= findViewById(R.id.btnregistrar);
        btnlogin1= findViewById(R.id.btnlogin1);
        btnlogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo, contra;
                correo=String.valueOf(txtcorreo.getText());
                contra=String.valueOf(txtcontra.getText());
                if (TextUtils.isEmpty(correo)){
                    Toast.makeText(RegistrarActivity.this,"Igrese su correo",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contra)){
                    Toast.makeText(RegistrarActivity.this,"Ingrese su contra",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(correo, contra)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrarActivity.this, "Cuenta creada exitosamente.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    if (errorMessage.contains("WEAK_PASSWORD")) {
                                        Toast.makeText(RegistrarActivity.this, "Contraseña débil. Debe tener al menos 6 caracteres.",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (errorMessage.contains("EMAIL_ALREADY_IN_USE")) {
                                        Toast.makeText(RegistrarActivity.this, "El correo electrónico ya está en uso.",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (errorMessage.contains("INVALID_EMAIL")) {
                                        Toast.makeText(RegistrarActivity.this, "Correo electrónico inválido.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegistrarActivity.this, "Error en la creación de la cuenta: " + errorMessage,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

    }


}