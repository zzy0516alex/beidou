package com.example.beidou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private Button mbtnregister;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mbtnregister=findViewById(R.id.re_confirm);
        username=findViewById(R.id.re_username);
        password=findViewById(R.id.re_password);
        final SharedPreferences register=getSharedPreferences("login",MODE_PRIVATE);
        mbtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameIn=username.getText().toString();
                String passwordIn=password.getText().toString();
                SharedPreferences.Editor editor=register.edit();
                editor.putString("username",usernameIn).apply();
                editor.putString("password",passwordIn).apply();
                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this,SearchActivity.class);
                startActivity(intent);
            }
        });


    }
}
