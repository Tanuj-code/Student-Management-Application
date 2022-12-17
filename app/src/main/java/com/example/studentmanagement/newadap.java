package com.example.studentmanagement;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class newadap extends RecyclerView.Adapter<newadap.ViewHolder> {
    private ArrayList<mod>list;
    String aa, bb, cc, dd, ee, ff, gg, hh, ii;
    Button b4, b8;
    EditText sta, stb, stc, srd, ste;
    int i, j;
    private String name;
    public newadap(ArrayList<mod> list, String name) {
        this.list = list;
        this.name=name;
    }

    @NonNull
    @Override
    public newadap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.stditem, parent, false);
        return new newadap.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newadap.ViewHolder holder, int position) {

        String txt=list.get(position).getText();
        int im1=list.get(position).getIm1();
        int im2=list.get(position).getIm2();
        holder.setData(txt, im1, im2);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView t;
        private ImageView i1, i2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t=itemView.findViewById(R.id.stdname);
            i1=itemView.findViewById(R.id.std);
            i2=itemView.findViewById(R.id.stdatt);

        }


        public void setData(String txt, int im1, int im2) {


        t.setText(txt);
        i1.setImageResource(im1);
        i2.setImageResource(im2);
            DatabaseReference db= FirebaseDatabase.getInstance().getReference().child(name).child(txt);

i2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            int a;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    if(snap.getKey().equals("Attendance")){
                        a=Integer.parseInt(snap.getValue(String.class));
                        ++a;
                        db.child("Attendance").setValue(Integer.toString(a));
                        Toast.makeText(v.getContext(), "Attendance marked +1"+" for "+t.getText(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return;
    }

});







            t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap: snapshot.getChildren()){
                            if(snap.getKey().equals("Name")){
                                aa=snap.getValue(String.class);

                            }
                            else if(snap.getKey().equals("Roll No")){
                                bb=snap.getValue(String.class);
                            }
                            else if(snap.getKey().equals("Email")){
                                cc=snap.getValue(String.class);

                            }
                            else if(snap.getKey().equals("Phone")){
                                dd=snap.getValue(String.class);

                            }
                            else if(snap.getKey().equals("Attendance")){
                                ee=snap.getValue(String.class);

                            }
                            else if(snap.getKey().equals("Exam1")){
                                ff=snap.getValue(String.class);

                            }
                            else if(snap.getKey().equals("Exam2")){
                                gg=snap.getValue(String.class);

                            }
                            else if(snap.getKey().equals("Exam3")){
                                hh=snap.getValue(String.class);

                            }
                            else if(snap.getKey().equals("Exam4")){
                                ii=snap.getValue(String.class);

                            }
                        }

                        Intent ni=new Intent(v.getContext(), MainActivity5.class);
                        ni.putExtra("aa", aa);
                        ni.putExtra("bb", bb);
                        ni.putExtra("cc", cc);
                        ni.putExtra("dd", dd);
                        ni.putExtra("ee", ee);
                        ni.putExtra("ff", ff);
                        ni.putExtra("gg", gg);
                        ni.putExtra("hh", hh);
                        ni.putExtra("ii", ii);
                        ni.putExtra("name", name);
                        ni.putExtra("txt", txt);
                        v.getContext().startActivity(ni);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        }

    }
}
