package com.example.mynotes;
//зависимость на интерфейсах

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CardSourseImp implements  CardSourse{

    private List<CardData> list;
    //прямо из ресурсов будем брать, это наш исочник данных
    private Resources resources;

    public CardSourseImp(Resources resources) {
        list = new ArrayList<>(7);
        this.resources = resources;
    }

    //тут из ресурсов будемизвлекать массивы
    public CardSourseImp init() {
        String [] titles = resources.getStringArray(R.array.titles);
        String [] descriptions = resources.getStringArray(R.array.descriptions);
        int [] images = getImagesArray();

        for (int i = 0; i < descriptions.length; i++) {
            list.add(new CardData(
                    titles[i],
                    descriptions[i],
                    images[i],
                    false
            ));
        }
        return this;
    }

    //поскольку айдишники тут, по ним картинки ищем, то надо тайпер аррей
    private int[] getImagesArray() {
        TypedArray array = resources.obtainTypedArray(R.array.pictures);
        int[] images = new int [array.length()];
        for (int i = 0; i < array.length(); i++) {
            images[i] = array.getResourceId(i, 0);
        }
        return images;
    }

    @Override
    public CardData getCard(int position) {
        return list.get(position);
    }

    @Override
    public int size() {
        return list.size();
    }
}
