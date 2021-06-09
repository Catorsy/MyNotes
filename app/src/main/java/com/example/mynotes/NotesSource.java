package com.example.mynotes;

public interface NotesSource {
    Note getNote(int position);
    int size();
    void deleteNoteData (int position);
    void updateNoteData (int position, Note note);
    int addNoteData (Note note);
    void clearNoteData();
    boolean moveCard(int position);
}
