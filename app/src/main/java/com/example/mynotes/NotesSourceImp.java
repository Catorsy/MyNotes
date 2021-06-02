package com.example.mynotes;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class NotesSourceImp implements NotesSource{
    private List<Note> list;
    private Resources resources;

    public NotesSourceImp(Resources resources) {
        list = new ArrayList<>(4);
        this.resources = resources;
    }

    public  NotesSourceImp init(){
        String [] titles = resources.getStringArray(R.array.notes);
        int [] description = getDescriptionArray();
        int [] image = getImageArray();

        for (int i = 0; i < titles.length; i++) {
            list.add(new Note(titles[i], description[i], image[i]));
        }
        return this;
    }

    private int[] getDescriptionArray() {
        TypedArray array = resources.obtainTypedArray(R.array.notes_description);
        int [] des = new int [array.length()];
        for (int i = 0; i < array.length(); i++) {
            des[i] = array.getResourceId(i, 0);
        }
        return des;
    }

    private int[] getImageArray() {
        TypedArray array = resources.obtainTypedArray(R.array.pictures);
        int [] images = new int [array.length()];
        for (int i = 0; i < array.length(); i++) {
            images[i] = array.getResourceId(i, 0);
        }
        return images;
    }

    @Override
    public Note getNote(int position) {
        return list.get(position);
    }

    @Override
    public int size() {
        return list.size();
    }
}
