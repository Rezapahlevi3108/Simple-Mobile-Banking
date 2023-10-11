package com.example.simplemobilebanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editEmail, editUsername, editPassword;
    Button btn_save;
    ImageView btn_back;
    String name, username, email, password;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.btn_back);

        showData();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isEmailChanged() || isPasswordChanged()) {

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(EditProfileActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        isNameChanged();
                        isEmailChanged();
                        isPasswordChanged();

                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("username", username);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivity(intent);

                        Toast.makeText(EditProfileActivity.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
    }

    private boolean isNameChanged() {
        if (!name.equals(editName.getText().toString())) {
            reference.child(username).child("name").setValue(editName.getText().toString());
            name = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if (!email.equals(editEmail.getText().toString())) {
            reference.child(username).child("email").setValue(editEmail.getText().toString());
            email = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if (!password.equals(editPassword.getText().toString())) {
            reference.child(username).child("password").setValue(editPassword.getText().toString());
            password = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void showData() {
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        editName.setText(name);
        editUsername.setText(username);
        editEmail.setText(email);
        editPassword.setText(password);
    }
}