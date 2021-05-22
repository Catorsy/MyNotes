package com.example.mynotes;

import android.content.Intent;
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

//этот фрагмент создает список заметок
public class NotesFragment extends Fragment {

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
            textView.setOnClickListener(v -> showPortrDetails(fi));
        }
    }

    // герб в портрете
    private void showPortrDetails(int index) {
        // откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);
        intent.putExtra(NotesDescriptionFragment.ARG_INDEX, index);
        startActivity(intent);
    }
}

