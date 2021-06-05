package com.example.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;

//этот фрагмент создает список заметок
public class NotesFragment extends Fragment {
    private boolean isLandscape;
    private Note currentNote;
    public static final String CURRENT_NOTE = "CurrentNote";

    private RecyclerView recyclerView;
    private NotesSource data;
    private NotesAdapter adapter;

    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    //получим источник данных для списка
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new NotesSourceImp(getResources()).init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        //data = new NotesSourceImp(getResources()).init();
        setHasOptionsMenu(true);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new OvershootInRightAnimator());

        if (moveToLastPosition) {
            recyclerView.smoothScrollToPosition(data.size() - 1);
            moveToLastPosition = false;
        }

        adapter = new NotesAdapter(data, this);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                currentNote = data.getNote(position);
                showDetails(currentNote);
            }
        });
        return view;
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
                currentNote = new Note(getResources().getStringArray(R.array.notes)[fi], new Date(),
                        getResources().getStringArray(R.array.notes_description)[fi]);
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

    //прикрутим меню
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //переопределим клик
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                navigation.addFragment(NoteCardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        data.addNoteData(note);
                        adapter.notifyItemInserted(data.size() - 1);
                        moveToLastPosition = true;
                    }
                });
                return true;

            case R.id.action_clear:
                data.clearNoteData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
    }

    //обработка кликов
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                navigation.addFragment(NoteCardFragment.newInstance(data.getNote(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        data.updateNoteData(position, note);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;

            case R.id.action_delete:
                data.deleteNoteData(position);
                adapter.notifyItemRemoved(position);
                return true;

            case R.id.action_move:
                if (data.moveCard(position)) {
                    data.moveCard(position);
                    adapter.notifyItemMoved(position, position + 1);
                    return true;
                }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }
}

