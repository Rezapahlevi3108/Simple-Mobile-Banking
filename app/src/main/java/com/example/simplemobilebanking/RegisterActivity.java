package com.example.simplemobilebanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private ImageView R_btn_back;
    private TextView link_login;
    EditText registerName, registerUsername, registerEmail, registerPassword;
    Button btn_register;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        R_btn_back = findViewById(R.id.R_btn_back);
        link_login = findViewById(R.id.link_login);
        registerName = findViewById(R.id.registerName);
        registerUsername = findViewById(R.id.registerUsername);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = registerName.getText().toString();
                String email = registerEmail.getText().toString();
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                float balance = 0.0f;

                if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (containsInvalidCharacters(username)) {
                    Toast.makeText(RegisterActivity.this, "Username tidak boleh mengandung karakter .#$[]", Toast.LENGTH_SHORT).show();
                } else {
                    reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(RegisterActivity.this, "Username sudah digunakan.", Toast.LENGTH_SHORT).show();
                            } else {
                                HelperClass helperClass = new HelperClass(name, email, username, password, balance);
                                reference.child(username).setValue(helperClass);

                                Toast.makeText(RegisterActivity.this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RegisterActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        R_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean containsInvalidCharacters(String username) {
        String invalidCharacters = ".#$[]";

        for (char c : invalidCharacters.toCharArray()) {
            if (username.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }
}