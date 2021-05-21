package com.example.mynotes;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 public class ImageFragment extends Fragment {
    //будем передавать аргумент
    public static final int DEFAULT_INDEX = 0;
    public static final String ARG_INDEX = "index";
    private int index = DEFAULT_INDEX;

    private static int [] CITY_IMAGES = {
            R.drawable.mosc,
            R.drawable.pit,
            R.drawable.ekat,
            R.drawable.novos,
            R.drawable.sam
    };

    // Фабричный метод создания фрагмента
// Фрагменты рекомендуется создавать через фабричные методы. Получает на входе индекс массива с городами. По нему вытаскивает изображение герба.
    public static ImageFragment newInstance (int index) {
        ImageFragment fragment = new ImageFragment(); // создание
// Передача параметра
        Bundle args = new Bundle(); //сами себе создали бандл и прикрепили к нашему фрагменту
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    //тут нам надо аргумент получить. Проверяем, что аргументы были переданы. Гет аргументс принимает наш бандлс. Смотрим по значению индекса.
    //по умолчанию он ноль
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX, DEFAULT_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Таким способом можно получить головной элемент из макета. Хотим вью сразу картиночку создать.
        View view = inflater.inflate(R.layout.fragment_image, container, false);
// Прежде, чем вернуть, можно сним поработать. Найти в контейнере элемент-изображение
        AppCompatImageView imageCoatOfArms = view.findViewById(R.id.cityImage);
// Получить из ресурсов массив указателей на изображения гербов
        //TypedArray images =  getResources().obtainTypedArray(R.array.coat_of_arms_imgs);
// Выбрать по индексу подходящий. Имедж вью подставляем тут картинку. По-хорошему картинки кладут в ресурсы. Типа как в комменте?
        //imageCoatOfArms.setImageResource(images.getResourceId(index, -1));
        imageCoatOfArms.setImageResource(CITY_IMAGES[index]);
        return view;
        //далее - надо из ситис фрагменты открывать имейдж фрагмент. Делаем новую активити.
    }
}