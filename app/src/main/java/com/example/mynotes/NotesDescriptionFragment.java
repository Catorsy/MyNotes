package com.example.mynotes;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;

//этот фрагмент даёт подробную информацию о заметке
public class NotesDescriptionFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    public static final int DEFAULT_INDEX = 0;
    private int index = DEFAULT_INDEX;
    private Note note;
    Calendar calendar = Calendar.getInstance();
    TextView dataView;

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
        dataView = view.findViewById(R.id.description_of_notes_date);
        nameView.setText(note.getNoteName());
        descrView.setText(descriptions[note.getIndexDescription()]);
        //descrView.setText(note.getDescription());
        dataView.setText(calendar.getTime().toString());
        Button button = view.findViewById(R.id.buttonSetTime);
        button.setOnClickListener(v -> {
            setDate();
        });
        return view;
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            note.setDate(calendar.getTime());
            //TODO сохранить время заметок?
            dataView.setText(note.getDate().toString());
        }
    };

    private void setDate() {
        new DatePickerDialog(getContext(), dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}