package com.example.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Note implements Parcelable {
    private String id;
    private String noteName;
    private int pictureNumber;
    private String description;
    Date date;


    public Note(String noteName, String description, int pictureNumber, Date date) {
        this.noteName = noteName;
        this.description = description;
        this.pictureNumber = pictureNumber;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public String getNoteName() {
        return noteName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPictureNumber() {
        return pictureNumber;
    }

    protected Note(Parcel in) {
        noteName = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
        pictureNumber = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeInt(pictureNumber);
        dest.writeString(description);
        dest.writeLong(date.getTime());
    }
}
