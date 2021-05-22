package com.example.mynotes;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

//этот фрагмент даёт подробную информацию о заметке
public class NotesDescriptionFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    public static final int DEFAULT_INDEX = 0;
    private int index = DEFAULT_INDEX;
    private Note note;

    public static NotesDescriptionFragment newInstance(Note note) {
        NotesDescriptionFragment fragment = new NotesDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_description, container, false);
// находим в контейнере вьюшку
        TextView descrView = view.findViewById(R.id.description_of_notes_description);
// получить из ресурсов имя/подробности/дату
        String[] descriptions = getResources().getStringArray(R.array.notes_description);
// вывести нужное
        TextView nameView = view.findViewById(R.id.description_of_notes_name);
        TextView dataView = view.findViewById(R.id.description_of_notes_date);
        nameView.setTextSize(25);
        nameView.setTypeface(null, Typeface.BOLD);
        nameView.setText(note.getNoteName());
        descrView.setText(descriptions[note.getIndexDescription()]);
        try{
            //в альбоме работает, а портрете выбрасывает исключение. Интересно!
            //FIXME
            dataView.setText(note.getDate().toString());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return view;
    }
}