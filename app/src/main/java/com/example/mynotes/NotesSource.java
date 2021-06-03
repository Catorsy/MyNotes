package com.example.mynotes;

public interface NotesSource {
    Note getNote(int position);
    int size();
}
