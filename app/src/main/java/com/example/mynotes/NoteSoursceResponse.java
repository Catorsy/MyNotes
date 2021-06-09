package com.example.mynotes;

public interface NoteSoursceResponse {
    void initializated(NotesSource notesSource); //что данные проинициализированы. Данные-то теперь асинхронные.
    //Метод будет вызываться, когда данные проинициализируются и будут готовы.
}
