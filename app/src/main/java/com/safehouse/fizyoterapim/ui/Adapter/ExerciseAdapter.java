package com.safehouse.fizyoterapim.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.safehouse.fizyoterapim.Firebase.Model.Exercise;
import com.safehouse.fizyoterapim.R;
import com.safehouse.fizyoterapim.ui.Activity.DetailActivity;
import com.safehouse.fizyoterapim.ui.Fragment.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.Viewholder> {

        private ArrayList<Exercise> data;
        private Exercise model;
        private Context context;

        public ExerciseAdapter(ArrayList<Exercise> data, Context context) {
            this.data = data;
            this.context = context;
        }

        public static class Viewholder extends RecyclerView.ViewHolder {

            // 1. Declare your Views here

            public ImageView exercise_image;
            public com.google.android.material.textview.MaterialTextView exercise_name;
            public com.google.android.material.textview.MaterialTextView exercise_description;
            public LinearLayout exerciseLinear;

            public Viewholder(View itemView) {
                super(itemView);

                // 2. Define your Views here
                exerciseLinear = itemView.findViewById(R.id.exerciseRowLinear);
                exercise_image = (ImageView)itemView.findViewById(R.id.exercise_image);
                exercise_name = (com.google.android.material.textview.MaterialTextView)itemView.findViewById(R.id.exercise_name);
                exercise_description = (com.google.android.material.textview.MaterialTextView)itemView.findViewById(R.id.exercise_description);

            }
        }

        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercise_rc_row, parent, false);

            return new Viewholder(itemView);
        }

        @Override
        public void onBindViewHolder(Viewholder holder, int position) {

            model = data.get(position);
            holder.exercise_name.setText(model.getName());
            holder.exercise_description.setText(model.getDescription());
            Picasso.get().load(model.getImage1()).fit().into(holder.exercise_image);

            holder.exerciseLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("exercise",model);
                    context.startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }



}
