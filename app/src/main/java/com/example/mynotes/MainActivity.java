package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //инфлейтить - это связать макет и код. Парсинг хмл-я и преобразование его в джава-код.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //создаем класс айтем, делаем лайаут
        //делаем адаптер майАдаптер
        //создаем там же MyViewHolder

        //передаем адаптер в ресайкл вью
        RecyclerView recyclerView = findViewById(R.id.recycler);
        //мето ниже увеличит производительность: все элементы одного размера, не надо пересчитывать, сколько влезет на экран
        recyclerView.setHasFixedSize(true); //лучше так делать, если все комплименты одинакового размера
        recyclerView.setAdapter(new MyAdapter(initList()));
        //что еще сделать, что все заработало? Менеджер надо! Сойдет самый простой, и дать ему контекст:
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //еще хотелось бы? чтобы не забыть менеджер, можно передать ему прямо в иксемеле, в активити_мейн
    }

    private List<String> initList(){
        //из пяти элементов. Лучше его, конечно, не здесь создавать
        List<String> list = new ArrayList<>(5);
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        //если их много, можем прокрутить на экране!
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        return list;
    }
}