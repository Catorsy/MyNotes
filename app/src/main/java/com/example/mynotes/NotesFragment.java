package com.example.mynotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//этот фрагмент создает список заметок
public class NotesFragment extends Fragment {
    private boolean isLandscape;
    private int position = 0;
    public static final String CURRENT_NOTE = "CorrentNote";

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
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // showPortrDetails(fi);
                    position = fi;
                    showDetails(position);
                }
            });
        }
    }

    private void showDetails(int index) {
        if (isLandscape) {
            showLandDetails(index);
        } else {
            showPortrDetails(index);
        }
    }

    private void showLandDetails(int index) {
        //создаем новый фрагмент
        NotesDescriptionFragment detail = NotesDescriptionFragment.newInstance(index);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.description_of_notes, detail).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if(savedInstanceState != null){
            position = savedInstanceState.getInt(CURRENT_NOTE, NotesDescriptionFragment.DEFAULT_INDEX);
        }
        if (isLandscape) {
            showLandDetails(position);
        }
    }

    private void showPortrDetails(int index) {
        // откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);
        intent.putExtra(NotesDescriptionFragment.ARG_INDEX, index);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, position);
        super.onSaveInstanceState(outState);
    }
}

