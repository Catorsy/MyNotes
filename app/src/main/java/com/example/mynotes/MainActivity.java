package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initeview();
    }

    private void initeview() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //назначили  бар
    }

    //назначим меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //для айдишников: при нажатии добавляем фрагмент
        int id = item.getItemId();
        switch (id){
            case R.id.choose_settings:
                addFragment(new AboutAppFragment());
                return true;
            case R.id.about_app:
                addFragment(new AboutAppFragment());
                return  true;
            case R.id.frases_app:
                addFragment(new FrasesFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //создадим верхнее меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //строка "поиск"
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView)search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //добавляем заданный фрагмент во фрагмент менеджер
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}


