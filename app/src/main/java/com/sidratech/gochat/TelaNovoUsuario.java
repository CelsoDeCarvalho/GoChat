package com.sidratech.gochat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class TelaNovoUsuario extends AppCompatActivity {

    private EditText name,email,password;
    private Button reg_button,photo_select_btn;
    private Uri foto_uri;
    private ImageView foto;

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
        photo_select_btn=findViewById(R.id.btn_foto);
        foto=findViewById(R.id.photo);
    }

    private void action(){
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser();
            }
        });

        photo_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
    }

    private void newUser(){
        String e_mail=email.getText().toString();
        String pass=password.getText().toString();

        if(e_mail.isEmpty() || e_mail==null ||pass==null || pass.isEmpty()){
            Toast.makeText(this,"Senha e email nao podem estar vazios",Toast.LENGTH_SHORT).show();
        }else
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(e_mail,pass);
    }

    private void selectPhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0){
            foto_uri=data.getData();
            Bitmap bitmap=null;
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),foto_uri);
                foto.setImageDrawable(new BitmapDrawable(bitmap));
                photo_select_btn.setAlpha(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}