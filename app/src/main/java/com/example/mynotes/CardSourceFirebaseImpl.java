package com.example.mynotes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImpl implements  NotesSource {

    public static final String NOTE_COLLECTION = "notes"; //надо разложить все по коллекциям, по "папочкам". Это ее имя.
    public static final String TAG = "CardSourceFirebaseImpl";

    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //это экземпляр базы данных

    final private List<Note> notesData = new ArrayList<>(); //здесь будут храниться уже загруженные элементы
    private CollectionReference collection = db.collection(NOTE_COLLECTION); //как список выше, только удаленный

    @Override
    public Note getNote(int position) {
        return notesData.get(position);
    }

    @Override
    public NotesSource init(NoteSoursceResponse noteSoursceResponse) {
        collection.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.ASCENDING) //сортируем. Последнее поле - сортировка от старого в новому
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    notesData.clear();
                    for (QueryDocumentSnapshot document: task.getResult()){ //Snapshot - часть коллекции
                        Map<String, Object> doc = document.getData(); //достали документ
                        Note note = NoteDataMapping.toNoteData(document.getId(), doc);
                        notesData.add(note);
                    }
                    Log.d(TAG, "isSucessful");
                    noteSoursceResponse.initializated(CardSourceFirebaseImpl.this); //оповещаем, что данные пришли с сервера
                } else {
                    Log.e(TAG, "Failed");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "get failed with ", e);
            }
        });
        return this;
    }

    @Override
    public int size() {
        if (notesData == null) {
            return 0;
        }
        return notesData.size();
    }

    @Override
    public void deleteNoteData(int position) {
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note note) {
        try {
            collection.document(note.getId()).set(NoteDataMapping.toDocument(note)); //по айдишнику назвначаем новые поля
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addNoteData(Note note) {
        collection.add(NoteDataMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override //проверим, успешно ли
            public void onSuccess(DocumentReference documentReference) {
                 note.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNoteData() {
        for (Note note: notesData) {
        collection.document(note.getId()).delete(); //все сразу удалить, к сожалению, нельзя
        }
        notesData.clear();
    }

    @Override //для БД move сложно реализуется
    public void updateFromBase(NoteSoursceResponse noteSoursceResponse) {
        init(noteSoursceResponse);
    }
}
