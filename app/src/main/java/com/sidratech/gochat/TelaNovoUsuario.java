package com.sidratech.gochat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

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
        String nome=name.getText().toString();
        String e_mail=email.getText().toString();
        String pass=password.getText().toString();

        if(nome==null || nome.isEmpty() || e_mail.isEmpty() || e_mail==null ||pass==null || pass.isEmpty()){
            Toast.makeText(this,"Nome, Senha e email nao podem estar vazios",Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(e_mail,pass)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.i("Teste",task.getResult().getUser().getUid());
                        saveUserInFireBase();
                    }
                })
                .addOnFailureListener(e -> Log.i("Teste",e.getMessage()));
    }

    private void saveUserInFireBase(){
        String filename= UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/"+filename);
        ref.putFile(foto_uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Teste",foto_uri.toString());
                            }
                        });
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste",e.getMessage(),e);
                    }
                });
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