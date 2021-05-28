package com.example.mynotes;

//если делаем фрагмент ручками: сначала делаем лайаут, потом джава-класс и наследуемся от фрагмента
//далее перепределяем он креейт вью, чтобы задать лайаут. создаем вью и инфлейтим ее

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true); //метод для меню. "Захватывает" управление меню, захват настроек переопределения кнопок.
        initPopMenu(view);

        return view;
    }

    private void initPopMenu(View view) {
        //он привязывается к конкретному элементу, напр, к текст вью
        TextView text = view.findViewById(R.id.textView);
        text.setOnClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v); //просто сами его создаем
            //хотим заинфлейтить хмл R.menu.popup в меню popupMenu.getMenu()
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu()); //проинициализировали
            //теперьь можем управлять им
            Menu menu = popupMenu.getMenu();
            menu.findItem(R.id.item2_popup).setVisible(false);
            //просто возьмем и добавим прямо в рантайме
            menu.add(0, 123123, 1242, R.string.new_menu_item_added);
            //обработаем нажатие на клавиши
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item1_popup:
                        Toast.makeText(getContext(), "Chosen popup item 1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2_popup:
                        Toast.makeText(getContext(), "Chosen popup item 2", Toast.LENGTH_SHORT).show();
                        return true;
                    case 123123:
                        Toast.makeText(getContext(), "Chosen new item added", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            });
            popupMenu.show(); //показываем попап
        });
    }

    //к меню, которое было в активити, добавляем свое меню, и оно обхединяеется
    //так мы управляем, что отображаетмся в каком фрагменте в меню
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            //надо какую-нибдь кнопку переопределить
            Toast.makeText(getContext(), "Chosen add", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

