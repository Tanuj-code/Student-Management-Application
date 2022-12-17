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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    ImageView img1, img2, img3, img4, img5;
    RecyclerView recyclerView;
    Button b, b4, b5, b6, b7;
    int bb=0;
    EditText sta, stb, stc, srd, ste, stf, stg, sth, sti, stdel, updatee, schst;
    TextView t;
    LinearLayoutManager manager;
    ArrayList<mod> list;
    DatabaseReference db, att;
    newadap adapter;
    Handler handler;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toast.makeText(this, "Add students", Toast.LENGTH_SHORT).show();
        img1=findViewById(R.id.imageView);
        img2=findViewById(R.id.refresh);
        img3=findViewById(R.id.logout);
        img4=findViewById(R.id.delete);
        img5=findViewById(R.id.sch);
        handler=new Handler();
        recyclerView=findViewById(R.id.recyclerView);
        Dialog dialog = new Dialog(MainActivity3.this);
        dialog.setContentView(R.layout.ram);
        Dialog update=new Dialog(MainActivity3.this);
        update.setContentView(R.layout.newdialog);
        Dialog nd=new Dialog(MainActivity3.this);
        nd.setContentView(R.layout.delstd);
        stdel=nd.findViewById(R.id.studname);
        b5=nd.findViewById(R.id.button5);
        flag=0;
        sta=dialog.findViewById(R.id.sta);
        stb=dialog.findViewById(R.id.stb);
        stc=dialog.findViewById(R.id.stc);
        srd=dialog.findViewById(R.id.srd);
        ste=dialog.findViewById(R.id.ste);
        stf=dialog.findViewById(R.id.ca11);
        stg=dialog.findViewById(R.id.midsem1);
        sth=dialog.findViewById(R.id.ca22);
        Dialog scst=new Dialog(MainActivity3.this);
        scst.setContentView(R.layout.schs);
        b7=scst.findViewById(R.id.button13);
        schst=scst.findViewById(R.id.seas);
        sti=dialog.findViewById(R.id.endsem2);
        b4=dialog.findViewById(R.id.button4);
        b=findViewById(R.id.button3);
        manager=new LinearLayoutManager(MainActivity3.this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        t=findViewById(R.id.textView4);
        list=new ArrayList<>();
        Intent intent=getIntent();
        Intent newint=new Intent(MainActivity3.this, MainActivity2.class);
        String data=intent.getStringExtra("name");
        db= FirebaseDatabase.getInstance().getReference().child(data);
        att=FirebaseDatabase.getInstance().getReference().child(data.concat("attendance"));
        b6=update.findViewById(R.id.button6);
        updatee=update.findViewById(R.id.updtatt);
        adapter=new newadap(list, data);

        recyclerView.setAdapter(adapter);
        att.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(String.class)==null){

                    att.setValue("0");
                }
                else {

                     int ii=Integer.valueOf(snapshot.getValue(String.class));


                    t.setText(Integer.toString(ii));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int ii=Integer.parseInt(t.getText().toString())+1;
                att.setValue(Integer.toString(ii));


            }
        });
        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update.show();
                return true;
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                att.setValue(updatee.getText().toString().trim());
            }
        });
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap: snapshot.getChildren()){

                    String value = snap.getKey();


                    list.add(new mod(value, R.drawable.ic_baseline_person_24, R.drawable.ic_baseline_add_circle_outline_24));
                }
                    adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                scst.show();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search=schst.getText().toString().trim();
                for(int i=0; i<list.size(); i++){
                    if(list.get(i).getText().equals(search)){
                        Toast.makeText(MainActivity3.this, "Student found at position "+(i+1), Toast.LENGTH_SHORT).show();
                        recyclerView.scrollToPosition(i);
                        flag=1;
                        break;
                    }
                }
                if(flag==0){
                    Toast.makeText(MainActivity3.this, "Student not found", Toast.LENGTH_SHORT).show();
                }
                schst.setText("");
                scst.dismiss();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity3.this, "Logging Out.....", Toast.LENGTH_SHORT).show();
                SharedPreferences sp= getSharedPreferences("log", MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("flag", false);
                editor.putString("str", "0");
                editor.apply();
                startActivity(newint);
                finish();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nd.show();
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }

        });
    img2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot snap: snapshot.getChildren()){

                                String value = snap.getKey();


                                list.add(new mod(value, R.drawable.ic_baseline_person_24, R.drawable.ic_baseline_add_circle_outline_24));
                            }
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }, 1000);
            Toast.makeText(MainActivity3.this, "Refreshing.....", Toast.LENGTH_SHORT).show();
        }
    });
b5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String temp=stdel.getText().toString().trim();
        bb=0;
        if(temp.length()==0){
            Toast.makeText(MainActivity3.this, "Enter correct name", Toast.LENGTH_SHORT).show();
            return;
        }
        db.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild(temp)){
                    Toast.makeText(MainActivity3.this, "Student not present.", Toast.LENGTH_SHORT).show();
                    bb=1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        if(bb==1){
            stdel.setText("");
            nd.dismiss();

        }
        else {
            db.child(temp).removeValue();
            stdel.setText("");
            Toast.makeText(MainActivity3.this, "Removed Student", Toast.LENGTH_SHORT).show();
            nd.dismiss();
        }
    }
});
b4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String ta, tb, tc, td, te, tf, tg, th, ti;
        ta = sta.getText().toString().trim();
        tb = stb.getText().toString().trim();
        tc = stc.getText().toString().trim();
        td = srd.getText().toString().trim();
        te = ste.getText().toString().trim();
        tf = stf.getText().toString().trim();
        tg = stg.getText().toString().trim();
        th = sth.getText().toString().trim();
        ti = sti.getText().toString().trim();
        if (ta.length() == 0 || tb.length() == 0 || tc.length() == 0 || td.length() == 0 || te.length() == 0) {
            Toast.makeText(MainActivity3.this, "Enter correct details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!chck(te)) {
            Toast.makeText(MainActivity3.this, "Enter correct attendance in number", Toast.LENGTH_SHORT).show();
            return;
        }

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(ta)) {
                    Toast.makeText(MainActivity3.this, "Student record already present. Please update.", Toast.LENGTH_SHORT).show();
                    sta.setText("");
                    stb.setText("");
                    stc.setText("");
                    srd.setText("");
                    ste.setText("");
                    stf.setText("");
                    stg.setText("");
                    sth.setText("");
                    sti.setText("");
                    dialog.dismiss();
                }
                else{
                    db.child(ta).child("Name").setValue(ta);
                    db.child(ta).child("Roll No").setValue(tb);
                    db.child(ta).child("Email").setValue(tc);

                    db.child(ta).child("Phone").setValue(td);
                    db.child(ta).child("Attendance").setValue(te);
                    db.child(ta).child("Exam1").setValue(tf);
                    db.child(ta).child("Exam2").setValue(tg);
                    db.child(ta).child("Exam3").setValue(th);
                    db.child(ta).child("Exam4").setValue(ti);
                    sta.setText("");
                    stb.setText("");
                    stc.setText("");
                    srd.setText("");
                    ste.setText("");
                    stf.setText("");
                    stg.setText("");
                    sth.setText("");
                    sti.setText("");
                    Toast.makeText(MainActivity3.this, "Entered Student Record", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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