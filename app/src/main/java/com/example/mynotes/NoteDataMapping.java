package com.example.mynotes;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {
    //под такими именами будут храниться на сервере
    public static class Fields {
        public static final String NOTENAME = "noteName";
        public static final String PICTURENUMBER = "pictureName";
        public static final String DESCRIPTION = "description";
        public final static String DATE = "date";
    }

    public static Note toNoteData(String id, Map<String, Object> doc) {
        long indexPic = (long) doc.get(Fields.PICTURENUMBER);
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE); //Timestamp - время в миллисекундах от 1янв 1970го года

        int pictureNumber = (int) PictureIndexConverter.getPictureByIndex((int) indexPic);
        String description = (String) doc.get(Fields.DESCRIPTION);
        String noteName = (String) doc.get(Fields.NOTENAME);
        Date date = timeStamp.toDate();

        Note noteData = new Note(noteName, description, pictureNumber, date);
        noteData.setId(id);
        return noteData;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> doc = new HashMap<>();
        doc.put(Fields.NOTENAME, note.getNoteName());
        doc.put(Fields.DESCRIPTION, note.getDescription());
        doc.put(Fields.PICTURENUMBER, note.getPictureNumber());
        doc.put(Fields.DATE, note.getDate());
        return doc;
    }
}
