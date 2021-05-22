package com.example.mynotes;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//этот фрагмент даёт подробную информацию о заметке
public class NotesDescriptionFragment extends Fragment {
    public static final String ARG_INDEX = "index";
    public static final int DEFAULT_INDEX = 0;
    private int index = DEFAULT_INDEX;

    //фабрика
    public static NotesDescriptionFragment newInstance(int index) {
        NotesDescriptionFragment fragment = new NotesDescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_description, container, false);
// находим в контейнере вьюшку
        TextView descrView =  view.findViewById(R.id.description_of_notes_description);
// получить из ресурсов имя/подробности/дату
        String [] notes = getResources().getStringArray(R.array.notes);
        String [] descriptions = getResources().getStringArray(R.array.notes_description);
// вывести нужное
        TextView nameView = view.findViewById(R.id.description_of_notes_name);
        nameView.setTextSize(25);
        nameView.setTypeface(null, Typeface.BOLD);
        nameView.setText(notes[index]);
        descrView.setText(descriptions[index]);
        return view;
    }
}