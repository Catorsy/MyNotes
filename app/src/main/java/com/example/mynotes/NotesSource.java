package com.example.mynotes;

public interface NotesSource {
    Note getNote(int position);
    NotesSource init (NoteSoursceResponse noteSoursceResponse); //обмен данными с базой
    int size();
    void deleteNoteData (int position);
    void updateNoteData (int position, Note note);
    void addNoteData (Note note);
    void clearNoteData();
    boolean moveCard(int position);
}
