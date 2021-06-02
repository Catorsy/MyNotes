package com.example.mynotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

//этот фрагмент создает список заметок
public class NotesFragment extends Fragment {
    private boolean isLandscape;
    //private int position = 0;
    private Note currentNote;
    public static final String CURRENT_NOTE = "CurrentNote";

    // указываем макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    // инициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаём список заметок
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);
// создаём вьюшки
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView textView = new TextView(getContext());
            textView.setText(note);
            textView.setTextSize(30);
            textView.setTypeface(null, Typeface.ITALIC);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(0, 20, 0, 20);
            layoutView.addView(textView);

            //обработка нажатий
            final int fi = i;
            textView.setOnClickListener(v -> {
                currentNote = new Note(getResources().getStringArray(R.array.notes)[fi], fi, new Date());
                showDetails(currentNote);
            });
        }
    }

    private void showDetails(Note currentNote) {
        if (isLandscape) {
            showLandDetails(currentNote);
        } else {
            showPortrDetails(currentNote);
        }
    }

    private void showLandDetails(Note currentNote) {
        //создаем новый фрагмент
        NotesDescriptionFragment detail = NotesDescriptionFragment.newInstance(currentNote);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.description_of_notes, detail).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(getResources().getStringArray(R.array.notes)[NotesDescriptionFragment.DEFAULT_INDEX],
                    NotesDescriptionFragment.DEFAULT_INDEX, new Date());
        }
        if (isLandscape) {
            showLandDetails(currentNote);
        }
    }

    private void showPortrDetails(Note currentNote) {
        // откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);
        intent.putExtra(NotesDescriptionFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }
}

