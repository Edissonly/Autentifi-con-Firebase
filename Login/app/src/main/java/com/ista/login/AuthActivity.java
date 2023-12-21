package com.ista.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {
    EditText emaileditText,contraeditText;
    Button btnregis,btnlogin;
    FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("Mensaje","Integracion de Fire Base Completa");
        mFirebaseAnalytics.logEvent("InitScreen",bundle);
        mAuth=FirebaseAuth.getInstance();
        emaileditText= findViewById(R.id.emaileditText);
        contraeditText= findViewById(R.id.contraeditText);
        btnlogin= findViewById(R.id.btnlogin);
        btnregis= findViewById(R.id.btnregis);
        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo, contra;
                correo=String.valueOf(emaileditText.getText());
                contra=String.valueOf(contraeditText.getText());
                if (TextUtils.isEmpty(correo)){
                    Toast.makeText(AuthActivity.this,"Igrese su correo",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contra)){
                    Toast.makeText(AuthActivity.this,"Ingrese su contra",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(correo, contra)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AuthActivity.this, "Inicio de sesión exitoso.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    if (errorMessage.contains("INVALID_EMAIL")) {
                                        Toast.makeText(AuthActivity.this, "Correo electrónico inválido.",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (errorMessage.contains("USER_DISABLED")) {
                                        Toast.makeText(AuthActivity.this, "La cuenta está deshabilitada.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AuthActivity.this, "Autentificaion Fallida: " + errorMessage,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

            }
        });
    }
}