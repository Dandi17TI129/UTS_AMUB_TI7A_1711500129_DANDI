package com.example.uts_amub_ti7a_1711500129_dandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btnlog,btnregs ;
    EditText ed_usn, ed_pass;


    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_usn = findViewById(R.id.ed_usn);
        ed_pass = findViewById(R.id.ed_pass);

        btnlog = findViewById(R.id.btnlog);
        btnregs = findViewById(R.id.btnregs);

        //fungsi pindah ke activity lain ke acttvty Register
        btnlog.setOnClickListener (new View.OnClickListener(){
            public void onClick(View view){
                Intent gotonextregister = new Intent(MainActivity.this, RegisterOneActivity.class);
                startActivity(gotonextregister);
            }
        });

        //menyimpan daya pada localstorage (handphone)
        SharedPreferences sharedPreferences= getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key,ed_usn.getText().toString());//ngambil data inputan username ke variable username_key
        editor.apply();

        //Proses SIMPAN ke database
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(ed_usn.getText().toString());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("username").setValue(ed_usn.getText().toString());
                dataSnapshot.getRef().child("password").setValue(ed_pass.getText().toString());
                dataSnapshot.getRef().child("saldo").setValue(100000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnregs.setOnClickListener (new View.OnClickListener(){
            public void onClick(View view){
                Intent gotologin = new Intent(MainActivity.this, RegisterTwoActivity.class);
                startActivity(gotologin);
            }
        });
    }
}
