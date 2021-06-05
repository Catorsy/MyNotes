package com.example.mynotes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class NoteCardFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";
    private Note note;
    private Publisher publisher; //с его помощью обмениваемся даннными

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    public NoteCardFragment() {
        // Required empty public constructor
    }

    public static NoteCardFragment newInstance(Note note) {
        NoteCardFragment fragment = new NoteCardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, note);
        fragment.setArguments(args);
        return fragment;
    }

    //для добавления новых данных
    public static NoteCardFragment newInstance() {
        NoteCardFragment fragment = new NoteCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_card, container, false);
        initView(view);
        if (note != null) {
            populateView();
        }
        return view;
    }

    //здесь соберем данные из вью
    @Override
    public void onStop() {
        super.onStop();
        note = collectCardData();
    }

    private Note collectCardData() {
        String title = this.title.getText().toString();
        int description;
        Date date = getDateFromDatePicker();
        int picture;
        if (note != null) {
            picture = note.getPictureNumber();
            description = note.getIndexDescription();
        } else {
            picture = R.drawable.dragon;
            description = 0;
        }
        return new Note(title, description, picture, date);
    }

    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.datePicker.getYear());
        calendar.set(Calendar.MONTH, this.datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return calendar.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
    }

    public void populateView() {
        title.setText(note.getNoteName());
        description.setText(note.getDescription());
        initDatePicker(note.getDate());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    //тут передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(note);
    }
}
