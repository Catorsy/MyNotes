package com.example.mynotes;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class CitiesFragment extends Fragment {
    //надо написать, что должно открываться в зависимости от ориентации
    private boolean isLandscape;
    //будем сохранять, что мы натыкали, две строки ниже
    private int position = 0;
    public static final String CURRENT_CITY = "CURRENT_CITY";

    //для сохранения
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_CITY, position);
    }

    // При создании фрагмента укажем его макет
    //Это самый главный метод. Отвечает за то, чтобы заинфлейтить лайаут. Сюда приходит инфлейтер (будет происходить сопоставление лайаута с кодом)
    //ViewGroup container куда помещается наш фрагмент. Может не на всю активити, а на часть.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //здесь как раз наш фрагмент ситис используем
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    //этот метод говорит о том, что все готово для работы с вью
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view); //тут работаем со списком
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null){
            position = savedInstanceState.getInt(CURRENT_CITY, ImageFragment.DEFAULT_INDEX);
        }

        if (isLandscape){
            showImage(position);
        }
    }

    // создаём список городов на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view; //смело кастим. Мы же знаем, что там линеар лайаут. Можно через файнд нью бай айдию
        String[] cities = getResources().getStringArray(R.array.cities); //получаем ресурсы
// В этом цикле создаём элемент TextView, его значениями и добавляем на экран. Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            TextView tv = new TextView(getContext()); //текствью нельзя создать без контекста, поэтому гэт контекст
            tv.setText(city);
            tv.setTextSize(30);
            layoutView.addView(tv);

            final int currentindex  = i;
            //тут при нажатии на текст вью отображаем фрагмент
            tv.setOnClickListener(v -> {
                //надо передавать сюда файнл! поэтому закрепим выше переменную
                showImage(currentindex);
                position = currentindex; //сохраняем
            });
        }
    }

    private void showImage(int index) {
        if(isLandscape){
            showLandImage(index);
        }else {
            showPortImage(index);
        }
    }

    private void showLandImage(int index) {
        //ImageFragment fragment = ImageFragment.newInstance(index);
        //метод ниже никогда не возращает налл, если нет активити, онн бросает исключение, лучше пользоваться им
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.image_fragment_container, ImageFragment.newInstance(index)).commit();
    }



    void showPortImage(int index){
        Intent intent = new Intent();
        intent.setClass(getActivity(), CityImageActivity.class);
        intent.putExtra(ImageFragment.ARG_INDEX, index);
        startActivity(intent);
    }
}