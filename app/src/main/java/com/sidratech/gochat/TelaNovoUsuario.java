package com.sidratech.gochat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TelaNovoUsuario extends AppCompatActivity {

    private EditText name,email,password;
    private Button reg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_novo_usuario);
        initComponents();
        action();
    }

    private void initComponents(){
        name=findViewById(R.id.edit_txtUser);
        email=findViewById(R.id.edi_email);
        password=findViewById(R.id.edit_password);
        reg_button=findViewById(R.id.btnReg);
    }

    private void action(){
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser();
            }
        });
    }

    private void newUser(){
        String e_mail=email.getText().toString();
        String pass=password.getText().toString();

        if(e_mail.isEmpty() || e_mail==null ||pass==null || pass.isEmpty()){
            Toast.makeText(this,"Senha e email nao podem estar vazios",Toast.LENGTH_SHORT).show();
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(e_mail,pass);
    }

}