package com.example.mynotes;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
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
   // private List<String> data;
    private  CardSourse cardSourse;
    private OnItemClickListener listener;

    public MyAdapter(CardSourse cardSourse) {
        this.cardSourse = cardSourse;
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
        //holder.bind(data.get(position));
        holder.bind(cardSourse.getCard(position));
        Log.d(TAG, "onCreateViewHolder position = " + position);
       }


    //говорит, сколько элементов у нас сейчас в списке.
    @Override
    public int getItemCount() {
        //return data.size();
        return cardSourse.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //private final TextView textView;

        private final TextView title;
        private final TextView description;
        private final ImageView image;
        private final CheckBox like;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //textView = itemView.findViewById(R.id.textView);

            title = itemView.findViewById(R.id.text);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            like = itemView.findViewById(R.id.likes);
        }

        //хотим забиндить новшества
//        void bind(String text){
//            textView.setText(text);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                       listener.onItemClick(getAdapterPosition());
//                    }
//                }
//            });
//        }


        void bind(CardData data){
            title.setText(data.getTitle());
            description.setText(data.getDescription());
            image.setImageResource(data.getPicture());
            like.setChecked(data.isLike());

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        //адаптер готов, осталось все это передть в мейн активити

//        void bind(CardData data){
//            textView.setText(text);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                        listener.onItemClick(getAdapterPosition());
//                    }
//                }
//            });
//        }

//        public TextView getTextView() {
//            return textView;
//            //return  itemView.findViewById(R.id.textView);
//        }
    }

    //для начала объявим интерфейс
    interface  OnItemClickListener {
        void onItemClick(int position);
    }
}
