package com.safehouse.fizyoterapim.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.safehouse.fizyoterapim.Firebase.Model.Exercise;
import com.safehouse.fizyoterapim.Firebase.Model.Form;
import com.safehouse.fizyoterapim.R;
import com.safehouse.fizyoterapim.ui.Activity.DetailActivity;
import com.safehouse.fizyoterapim.ui.Activity.WebViewActivity;
import com.safehouse.fizyoterapim.ui.Fragment.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.Viewholder> {

    private ArrayList<Form> data;
    private Form model;
    private Context context;

    public FormAdapter(ArrayList<Form> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        // 1. Declare your Views here


        public com.google.android.material.textview.MaterialTextView formName;
        public com.google.android.material.card.MaterialCardView materialCardView;

        public Viewholder(View itemView) {
            super(itemView);

            // 2. Define your Views here
            formName = itemView.findViewById(R.id.formName);
            materialCardView = itemView.findViewById(R.id.materialCard);



        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.form_row, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        model = data.get(position);
        holder.formName.setText(model.getName());


        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url",model);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
