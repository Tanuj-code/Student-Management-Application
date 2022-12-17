package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button bt, bt2, bt3;
    EditText txt, ent, srcch;
    DatabaseReference db;
    ImageView img, img1, img2, img3, img4;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    ArrayList<model>list;
    Adapter adapter;
    String data;
    int cck=0;
    int falg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Add classes", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(MainActivity.this);
         img=findViewById(R.id.imageView);
        img1=findViewById(R.id.refresh);
        img2=findViewById(R.id.log);
        img4=findViewById(R.id.srcc);
        dialog.setContentView(R.layout.dialog);

        falg=0;
         bt=dialog.findViewById(R.id.button);
         txt=dialog.findViewById(R.id.editText);
         list=new ArrayList<>();
         img3=findViewById(R.id.del);
         Dialog src=new Dialog(MainActivity.this);
         src.setContentView(R.layout.src);
        srcch=src.findViewById(R.id.srcch);
        bt3=src.findViewById(R.id.button14);
         Handler handler= new Handler();
        recyclerView=findViewById(R.id.recyclerView);
        manager=new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        Intent i=getIntent();
        Dialog dte=new Dialog(MainActivity.this);
        dte.setContentView(R.layout.deleted);
         data=i.getStringExtra("classdb");

         ent=dte.findViewById(R.id.enter);
         bt2=dte.findViewById(R.id.button2);
        adapter=new Adapter(list, data);

        recyclerView.setAdapter(adapter);
        db= FirebaseDatabase.getInstance().getReference().child(data);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    String value = snap.getValue(String.class);

                    list.add(new model(R.drawable.ic_baseline_people_24, value));
                }
adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    img4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            src.show();
            falg=0;
        }
    });
    bt3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String search=srcch.getText().toString().trim();
            for(int i=0; i<list.size(); i++){
                if(list.get(i).getTextView().equals(search)){
                    Toast.makeText(MainActivity.this, "Class found at position "+(i+1), Toast.LENGTH_SHORT).show();
                    recyclerView.scrollToPosition(i);
                    falg=1;
                    break;
                }
            }
            if(falg==0){
                Toast.makeText(MainActivity.this, "Class not found", Toast.LENGTH_SHORT).show();
            }
            srcch.setText("");
            src.dismiss();
        }
    });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                for(DataSnapshot snap: snapshot.getChildren()){
                                    String value = snap.getValue(String.class);

                                    list.add(new model(R.drawable.ic_baseline_people_24, value));
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }, 1000);

                Toast.makeText(MainActivity.this, "Refreshing.....", Toast.LENGTH_SHORT).show();


            }

        });

        adapter=new Adapter(list, data);

        recyclerView.setAdapter(adapter);

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logging Out.....", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this, MainActivity2.class);
                SharedPreferences sp= getSharedPreferences("log", MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("flag", false);
                editor.putString("str", "0");
                editor.apply();
                startActivity(intent);
                finish();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dte.show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qw=ent.getText().toString().trim();

                if(qw.length()==0){
                    Toast.makeText(MainActivity.this, "Enter valid class name", Toast.LENGTH_SHORT).show();
                    return;
                }



                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(qw)){

                            db.child(qw).removeValue();

                            DatabaseReference ddd = FirebaseDatabase.getInstance().getReference().child(data.concat(qw));
                            ddd.removeValue();

                            String delt = data.concat(qw);
                            delt = delt.concat("attendance");
                            DatabaseReference ddbb = FirebaseDatabase.getInstance().getReference().child(delt);
                            ddbb.removeValue();
                            Toast.makeText(MainActivity.this, "Deleted Class", Toast.LENGTH_SHORT).show();
                            ent.setText("");
                            dte.dismiss();

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Class not present", Toast.LENGTH_SHORT).show();
                            ent.setText("");
                            dte.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
               

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertion();
                dialog.dismiss();
            }
        });


    }

    public void insertion(){

            String val = txt.getText().toString().trim();
            if(val.length()==0){
                Toast.makeText(this, "Enter valid class name", Toast.LENGTH_SHORT).show();
                return;
            }
            txt.setText("");
            for(model a: list){
                if(a.getTextView().equals(val)){
                    Toast.makeText(this, "Class already present", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            db.child(val).setValue(val);

        Toast.makeText(this, "Class Added", Toast.LENGTH_SHORT).show();
    }
}