package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
EditText no, pass, olds, newp, an, ap, forg, number;
Button b1, b2, b3, cng, da, b10, b11;
    DatabaseReference dbj;
    ArrayList<loginmod> list;

    int booln=0;
    int boolp=0;
    Intent intent;
    DatabaseReference bj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        intent=new Intent(MainActivity2.this, MainActivity.class);
        SharedPreferences sp = getSharedPreferences("log", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        Boolean bool = sp.getBoolean("flag", false);
        String sss=sp.getString("str", "0");
        if(bool && sss!="0"){
            editor.putBoolean("flag", true);
            editor.putString("str", sss);
            editor.apply();
            intent.putExtra("classdb", sss.concat("data").toString());
            startActivity(intent);
            finish();
        }
        no=findViewById(R.id.phone);
        pass=findViewById(R.id.password);
        b1=findViewById(R.id.register);
        b2=findViewById(R.id.forgotbutton);
        b3=findViewById(R.id.changepass);
        list = new ArrayList<>();
        da=findViewById(R.id.button9);
        Dialog d = new Dialog(MainActivity2.this);
        d.setContentView(R.layout.changex);
        olds= d.findViewById(R.id.oldpass);
        newp= d.findViewById(R.id.newpass);
        cng= d.findViewById(R.id.change);
        number=d.findViewById(R.id.number);
        dbj= FirebaseDatabase.getInstance().getReference().child("credentials");
        Dialog acc=new Dialog(MainActivity2.this);
        acc.setContentView(R.layout.accdel);
        an=acc.findViewById(R.id.accnum);
        ap=acc.findViewById(R.id.accpass);
        b10=acc.findViewById(R.id.button10);
        Dialog forpass=new Dialog(MainActivity2.this);
        forpass.setContentView(R.layout.forgot);
        b11=forpass.findViewById(R.id.button11);
        forg=forpass.findViewById(R.id.forg);
        bj=FirebaseDatabase.getInstance().getReference();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1=no.getText().toString().trim();
                if(s1.length()!=10){
                    Toast.makeText(MainActivity2.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                }
                String s2=pass.getText().toString().trim();
                if(s2.length()==0){
                    Toast.makeText(MainActivity2.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
                if(s1.length()==10 && s2.length()>0){
                    check(s1, s2);
                }
                else{
                    Toast.makeText(MainActivity2.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }


            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forpass.show();
            }
        });
         b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=forg.getText().toString().trim();
                if(s1.length()!=10){
                    Toast.makeText(MainActivity2.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                    return;
                }
                int check=0;
                for(loginmod a: list){
                    if(s1.equals(a.getNum())){
                        check=1;
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                            if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                                sendsms(a.getPass(), a.getNum());
                                Toast.makeText(MainActivity2.this, "Password has been sent to your registered number.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else{
                                requestPermissions(new String[] {Manifest.permission.SEND_SMS}, 1);
                            }
                        }
                    }
                }
                if(check==0){
                    Toast.makeText(MainActivity2.this, "Number not registered", Toast.LENGTH_SHORT).show();
                }
                forg.setText("");
        forpass.dismiss();
            }
        });
        cng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(f(olds.getText().toString().trim(), number.getText().toString().trim()) && newp.getText().toString().trim().length()>0){
                    dbj.child(number.getText().toString().trim()).child("pass").setValue(newp.getText().toString().trim());
                    newp.setText("");
                    olds.setText("");
                    number.setText("");
                    Toast.makeText(MainActivity2.this, "Password Changed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity2.this, "Enter correct old password or check new password length", Toast.LENGTH_SHORT).show();

                }
                d.dismiss();

            }
        });


        dbj.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {

                    String n=snap.child("num").getValue(String.class);
                    String p=snap.child("pass").getValue(String.class);
                    list.add(new loginmod(n, p));

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    da.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            acc.show();
        }
    });
    b10.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String accnum=an.getText().toString().trim();
            String accpass=ap.getText().toString().trim();
            booln=0;
            boolp=0;
            dbj.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap: snapshot.getChildren()){
                        if(snap.getKey().equals(accnum)){
                            booln=1;
                            String ans=snap.child("pass").getValue(String.class);
                            if(ans.equals(accpass)){
                                boolp=1;
                                dbj.child(snap.getKey()).removeValue();
                                break;
                            }
                        }
                    }
                    if(booln==0){
                        Toast.makeText(MainActivity2.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                    else if(boolp==0){
                        Toast.makeText(MainActivity2.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity2.this, "Deleted Account Successfully", Toast.LENGTH_SHORT).show();
                        bj.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot snap: snapshot.getChildren()){
                                    String temp=snap.getKey();
                                    if(temp.contains(accnum)){
                                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(temp);
                                        ref.removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    an.setText("");
                    ap.setText("");

                    acc.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    });


    }
    private void sendsms(String s, String ph){

        try {
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(ph, null, s, null, null);
            Toast.makeText(MainActivity2.this, "Sent", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity2.this, "Failed due to error", Toast.LENGTH_SHORT).show();
        }
    }
    public void check(String s1, String s2){
        intent.putExtra("classdb", s1.concat("data").toString());
        for(loginmod a: list){
            if(s1.equals(a.getNum())){


                if(s2.equals(a.getPass())){
                    Toast.makeText(MainActivity2.this, "Logging in", Toast.LENGTH_SHORT).show();
                    no.setText("");
                    pass.setText("");
                    SharedPreferences sp = getSharedPreferences("log", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putBoolean("flag", true);
                    editor.putString("str", s1);
                    editor.apply();
                    startActivity(intent);
                    finish();
                    return;
                }
                else {
                    Toast.makeText(MainActivity2.this, "Already Registered, Enter correct credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        dbj.child(s1).child("num").setValue(s1);
        dbj.child(s1).child("pass").setValue(s2);
/*
        dbj.push().setValue(new loginmod(s1, s2));

 */
        no.setText("");
        pass.setText("");
        SharedPreferences sp = getSharedPreferences("log", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("flag", true);
        editor.putString("str", s1);
        editor.apply();
        startActivity(intent);
        Toast.makeText(MainActivity2.this, "Registering, Logging in", Toast.LENGTH_SHORT).show();
        finish();
    }


    public boolean f(String s1, String s2){
        for(loginmod a: list){
            if(s2.equals(a.getNum())){
                if(s1.equals(a.getPass())){
                    Toast.makeText(MainActivity2.this, "Matched", Toast.LENGTH_SHORT).show();
                    return true;
                }

            }
        }
        return false;
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }
}