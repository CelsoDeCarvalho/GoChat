package com.sidratech.gochat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TelaLogin extends AppCompatActivity {

    private EditText email_field,password_field;
    private Button login_button;
    private TextView new_user_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tela_login);
        initComponents();
        actions();
    }

    private void initComponents(){
        email_field=findViewById(R.id.txtEmail);
        password_field=findViewById(R.id.txtPassword);
        login_button=findViewById(R.id.btnLogin);
        new_user_text=findViewById(R.id.txtReg);
    }

    private void actions(){
        new_user_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TelaLogin.this,TelaNovoUsuario.class);
                startActivity(intent);
            }
        });
    }

}