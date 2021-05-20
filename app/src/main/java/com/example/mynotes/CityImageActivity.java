package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.MediaStore;

public class CityImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_image);

        //будем запускать эту активити, только если приложение в портретной ориентации
        //проверяем ориентацию
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish(); //сразу закрываем, если не та ориентация
            //Если финиш прямо в онкреейт, то жизненный цикл прервется сразу, не будет ни резьюм, ни  старт... На экране ниче не будет
            return;
        }

        if(savedInstanceState == null) { //если активити запускается первый раз, то перенаправлем параметр фрагменту
            ImageFragment fragment = new ImageFragment();
            //добавим во фрагмент сет аргументс. Беме бандл нашего активити и кладем его во фрагмент. Все параметры, что там были, перейдут и во фрагмент
            fragment.setArguments(getIntent().getExtras());

            //как добавить фрагмент в нашу активити, т.е. сделать транзакцию? Вызываем метод гет саппорт фрагмент, просто фрагмент не опльзуемся
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    //бегин транзакшн - это открыть транзакцию. Берем реплейс (заменить), хотя можно адд
            //коммит - завершить транзакцию.. Схема: бегин - действие/действия - коммит
            //это второй споосб добавления фрагмента в активити. Во фрагмент менеджере.
            //осталось при нажатии на текст вью отобразить правильый фрагмент, т.е. вызвать активити. Идем в ситис фрагмент

            //для альбомной ориентации: надо создать новый лайаут и сделать ему классификаию ландскейп
        }
    }
}