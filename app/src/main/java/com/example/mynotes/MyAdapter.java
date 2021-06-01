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
    private static final int IMAGE_TYPE = 1;
    private static final int TEXT_TYPE = 0;
    private List<String> data;

    public MyAdapter(List<String> data) {
        this.data = data;
        Log.d(TAG, "MyAdapter create");
        //а если хотим картинку? Надо переопределить метлд гетайтемфьютайп
    }

    @Override
    //говорит, какого типа элемент на такой-то позиции
    public int getItemViewType(int position) {
        //return position == 0 ? IMAGE_TYPE : TEXT_TYPE; //если позиция равна тноль то ноль, иначе 1
        //по-хорошему надо вынести в константы. У нас два типа: ноль и один.
        return position % 5 == 0 ? IMAGE_TYPE : TEXT_TYPE;
        //вот вьютайпы, их может быть сколько угодно. Но если много втюттайпов- много втюхолдеров.
        //если этого много, ничего не переиспользуется. Еще вар: делать и текст, и картинку, и картинку
        //в каких-то случаяъ показывать, в каких-то скрывать. Ресурсы на бандинг! Нужен баланс.
    }

    //вызывается, когда надо создать вью холдер
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        //теперь мы знаем вьютайп, текст или картинка! можем добавить
       if(viewType == TEXT_TYPE) {
           itemView = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.item, parent, false);
       }else{
           itemView = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.item_image, parent, false);
           //теперь у нас два типа элементов, но теперь надо правильно их биндить
       }
        //залогируем
        Log.d(TAG, "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    //когда надо забиндить вью холдер
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //холдер уже много сам знает, нам надо прост забандить
if (getItemViewType(position) != IMAGE_TYPE) {
    holder.bind(data.get(position));
}else{
        //Ниже - актуально до введения картинок
//        holder.bind(data.get(position));
//        Log.d(TAG, "onCreateViewHolder position = " + position);
//        if (position % 5 == 0){ //остаток при делении ноль
//            holder.getTextView().setBackgroundColor(Color.RED);
//        } else if (position % 5 == 1){
    //        holder.getTextView().setBackgroundColor(Color.GREEN);
//а вот и печаль ресайлер вью! Куски переставляем, а цвет-то остается, не перекрашивается!
        //это мы не затерли данные во вьюхолдере. Часто бывает. Напр, бывает, что пишется что-то там, где не надо,
        //из предыдущего элемента списка. Фиксится очень легко! пишем иначе вайт.
//        }else {
//            holder.getTextView().setBackgroundColor(Color.WHITE);
       }
    }

    //говорит, сколько элементов у нас сейчас в списке.
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //сделаем вью холдер.. Там есть текст вью, объявим его.
        //private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //тут его просто получаем. Альтернатива: файнд вью бай айди
           //textView = (TextView) itemView;
            //textView = itemView.findViewById(R.id.textView);
            //давайте еще кроме текста делать бекграунд. Сделаем геттер getTextView()
        }

        //забиндим
        void bind(String text){
            //textView.setText(text);
            getTextView().setText(text);
        }

        public TextView getTextView() {
            //return textView;
            return  itemView.findViewById(R.id.textView);
        }
    }
}
