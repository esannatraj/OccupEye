package com.example.occupeye;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    Button block55;
    Button block57;
    Button block59;


    Button register;
    Button back;
    boolean block55_sel;
    boolean block57_sel;
    boolean block59_sel;
    FirebaseFirestore db;
    SwitchCompat hostel_toggle;
    EditText username;
    EditText email;
    EditText password;
    DatabaseReference myRef;
    private boolean block_checker(){
        if(block57_sel ||block59_sel ||block55_sel)return true;
        else if (!hostel_toggle.isChecked()) {return true;

        }
        return false;
    }

    private String getActiveBlock(){
        if (block55_sel){return "55";} else if (block59_sel) {return "59";}else if(block57_sel){return "57";}else {return null;}

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://occupeye-dedb8-default-rtdb.asia-southeast1.firebasedatabase.app");
        db=FirebaseFirestore.getInstance();

        block55_sel=false;
        block59_sel=false;
        block57_sel=false;

        block57=findViewById(R.id.button57);
        block55=findViewById(R.id.button55);
        block59=findViewById(R.id.button59);

        register=findViewById(R.id.register_submit);
        back=findViewById(R.id.back);


        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        hostel_toggle=findViewById(R.id.toggleHostel);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User(username.getText().toString(),password.getText().toString(),email.getText().toString());
                //username checker
                //password checker
                //email checker
                //block select
                System.out.println(user.validate());
                Toast.makeText(Register.this, "brudda stfu", Toast.LENGTH_SHORT).show();
                if(user.validate()&&block_checker()){
                    //TODO send data to firebase collection
                    System.out.println("Check2");
                    user.setBlock(getActiveBlock());
                    myRef = database.getReference("Users").child(username.getText().toString());
                    boolean user_presnt;
                    System.out.println(myRef);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(Register.this,"Username Already In Use",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                System.out.println("CHEKC2");
                                myRef.setValue(user.database_obj());
                                db.collection("Users").document(username.getText().toString()).set(user.database_obj());
                                //Log database stored

                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String value = String.valueOf(snapshot.getValue());
                                        Log.d("FirebaseElement", "Value is: " + value);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w("FirebaseElement", "Failed to read value.", error.toException());
                                    }
                                });
                                Intent intent=new Intent(Register.this,Home.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else {
                    if(!user.username_checker()){

                        Toast.makeText(Register.this,"Username Cannot Have Space",Toast.LENGTH_SHORT).show();
                    }if (!user.password_checker()) {
                        Toast.makeText(Register.this,"Password Cannot Have Space",Toast.LENGTH_SHORT).show();
                    }if(!user.email_checker()) {
                        Toast.makeText(Register.this,"Email Needs @",Toast.LENGTH_SHORT).show();
                    }
                    if(!block_checker()){
                        Toast.makeText(Register.this,"Block Not Entered",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
        //SLECTOR FOR BUTTONS
        block55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(block55_sel){
                    ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    block55.setBackgroundTintList(colorStateList);
                    block55.setTextColor(Color.parseColor("#E3655B"));
                    block55_sel=false;
                    return;
                }
                if(block59_sel || block57_sel){
                    return;
                }
                block55_sel=true;
                ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.iconhover));
                block55.setBackgroundTintList(colorStateList);
                block55.setTextColor(Color.parseColor("#FFFFFFFF"));
            }
        });
        block57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(block57_sel){
                    ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    block57.setBackgroundTintList(colorStateList);
                    block57.setTextColor(Color.parseColor("#E3655B"));
                    block57_sel=false;
                    return;
                }
                if(block59_sel || block55_sel){
                    return;
                }
                block57_sel=true;
                ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.iconhover));
                block57.setBackgroundTintList(colorStateList);
                block57.setTextColor(Color.parseColor("#FFFFFFFF"));
            }
        });
        block59.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(block59_sel){
                    ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    block59.setBackgroundTintList(colorStateList);
                    block59.setTextColor(Color.parseColor("#E3655B"));
                    block59_sel=false;
                    return;
                }
                if(block55_sel || block57_sel){
                    return;
                }
                block59_sel=true;
                ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.iconhover));
                block59.setBackgroundTintList(colorStateList);
                block59.setTextColor(Color.parseColor("#FFFFFFFF"));
            }
        });


        
    }
}