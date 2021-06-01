package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        //recyclerView.setHasFixedSize(true); //лучше так делать, если все комплименты одинакового размера
        //recyclerView.setAdapter(new MyAdapter(initList()));
        //что еще сделать, что все заработало? Менеджер надо! Сойдет самый простой, и дать ему контекст:
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //еще хотелось бы? чтобы не забыть менеджер, можно передать ему прямо в иксемеле, в активити_мейн

        //добавим декоратор! например, разделим элементы.
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration); //надо эдд, не сеи! потому что их может быть несколько



        //инициализируем новый адаптер
       // MyAdapter adapter = new MyAdapter(initList());
        MyAdapter adapter = new MyAdapter(new CardSourseImp(getResources()).init());
        recyclerView.setAdapter(adapter);

        //а в итем добавляем кликабл тру!
        adapter.setListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "position " + position, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "position " + position);
                //можно писать в лог, чтобы проверить работоспособность кода
            }
        });
    }
}