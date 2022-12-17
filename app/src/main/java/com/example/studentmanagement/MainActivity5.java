package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity5 extends AppCompatActivity {
String a, b, c, d, e, f, g, h, i, d1, d2;
EditText e1, e2, e3, e4, e5, e6, e7, e8, e9;
Button bb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        e1=findViewById(R.id.name);
        e2=findViewById(R.id.roll);
        e3=findViewById(R.id.email);
        e4=findViewById(R.id.phonee);
        e5=findViewById(R.id.attendance);
        e6=findViewById(R.id.ca1);
        e7=findViewById(R.id.midsem);
        e8=findViewById(R.id.ca2);
        e9=findViewById(R.id.endsem);
    bb=findViewById(R.id.button12);
        Intent intent=getIntent();
        a=intent.getStringExtra("aa");
        b=intent.getStringExtra("bb");
        c=intent.getStringExtra("cc");
        d=intent.getStringExtra("dd");
        e=intent.getStringExtra("ee");
        f=intent.getStringExtra("ff");
        g=intent.getStringExtra("gg");
        h=intent.getStringExtra("hh");
        i=intent.getStringExtra("ii");
        d1=intent.getStringExtra("name");
        d2=intent.getStringExtra("txt");
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child(d1).child(d2);
        e1.setText(a);
        e2.setText(b);
        e3.setText(c);
        e4.setText(d);
        e5.setText(e);
        e6.setText(f);
        e7.setText(g);
        e8.setText(h);
        e9.setText(i);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t1, t2, t3, t4, t5, t6, t7, t8, t9;
                t1=e1.getText().toString().trim();
                t2=e2.getText().toString().trim();
                t3=e3.getText().toString().trim();
                t4=e4.getText().toString().trim();
                t5=e5.getText().toString().trim();
                t6=e6.getText().toString().trim();
                t7=e7.getText().toString().trim();
                t8=e8.getText().toString().trim();
                t9=e9.getText().toString().trim();
                if(!chck(t5)){
                    Toast.makeText(MainActivity5.this, "Enter correct attendance in number", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.child("Name").setValue(t1);
                db.child("Roll No").setValue(t2);
                db.child("Email").setValue(t3);
                db.child("Phone").setValue(t4);
                db.child("Attendance").setValue(t5);
                db.child("Exam1").setValue(t6);
                db.child("Exam2").setValue(t7);
                db.child("Exam3").setValue(t8);
                db.child("Exam4").setValue(t9);
                Toast.makeText(MainActivity5.this, "Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public boolean chck(String ss){
        try {
            Integer.parseInt(ss);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
    }
}