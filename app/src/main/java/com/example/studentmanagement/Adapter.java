package com.example.studentmanagement;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {
    private ArrayList<model>list;
    private String nm;
    public Adapter(ArrayList<model>list, String nm){
        this.list=list;
        this.nm=nm;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String s=list.get(position).getTextView();
        int res=list.get(position).getImageview();
        holder.set(s, res);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MainActivity3.class);
                intent.putExtra("name", nm.concat(s).toString());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgg;
        private TextView txtt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgg =itemView.findViewById(R.id.imageView4);
            txtt =itemView.findViewById(R.id.textView);
        }
        public void set(String s, int res){
            imgg.setImageResource(res);


            txtt.setText(s);


        }


    }

}
