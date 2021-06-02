package com.example.mynotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Date;

//этот фрагмент создает список заметок
public class NotesFragment extends Fragment {
    private boolean isLandscape;
    private Note currentNote;
    public static final String CURRENT_NOTE = "CurrentNote";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        //передаем адаптер в ресайкл вью
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        //экономия производительности
        recyclerView.setHasFixedSize(true);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter = new NotesAdapter(new NotesSourceImp(getResources()).init());
        recyclerView.setAdapter(adapter);
        return  view;
    }

    //это реликт, но внутри есть еще экспресс-способ работы со списком
    // создаём список заметок
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);

        //работаем с нотс и айтемс, доостаем элементы из айтема
        LayoutInflater layoutInflater = getLayoutInflater();

// помещаем заметки в список
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            View item = layoutInflater.inflate(R.layout.my_note_item, layoutView, false);
            TextView title = item.findViewById(R.id.item_title);
            title.setText(note);
            ImageView picture = item.findViewById(R.id.item_image);
            picture.setImageResource(R.drawable.dragon);
            layoutView.addView(item);

            //обработка нажатий
            final int fi = i;
            title.setOnClickListener(v -> {
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

