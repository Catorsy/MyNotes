package com.example.mynotes;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//один класс можно поместить в другой, если он нигде не используется, кроме как в родительском
//надо заполнить все три переопределенных метода
//когда адаптер готов, передаем его в ресайкл вью

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    //задали ему шаблонный тип вью холдер, а потом сами его реализуем. Вьюхолдер знает про все вьюхи внутри элемента.
    private static final String TAG = "MyAdapter";
    private List<String> data;

    public MyAdapter(List<String> data) {
        this.data = data;
        Log.d(TAG, "MyAdapter create");
    }

    //вызывается, когда надо создать вью холдер
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.item, parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    //когда надо забиндить вью холдер
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(data.get(position));
        Log.d(TAG, "onCreateViewHolder position = " + position);
       }


    //говорит, сколько элементов у нас сейчас в списке.
    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
        
        void bind(String text){
            //textView.setText(text);
            //getTextView().setText(text);
            textView.setText(text);
        }

        public TextView getTextView() {
            return textView;
            //return  itemView.findViewById(R.id.textView);
        }
    }
}
