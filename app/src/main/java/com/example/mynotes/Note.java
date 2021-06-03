package com.example.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Note implements Parcelable {
    private String noteName;
    private int indexDescription;
    private int pictureNumber;
    Date date;

    public Note(String noteName, int indexDescription, int pictureNumber) {
        this.noteName = noteName;
        this.indexDescription = indexDescription;
        this.pictureNumber = pictureNumber;
    }

    public Note(String noteName, int indexDescription, Date date) {
        this.noteName = noteName;
        this.indexDescription = indexDescription;
        this.date = date;
    }

    public String getNoteName() {
        return noteName;
    }

    public int getIndexDescription() {
        return indexDescription;
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
        indexDescription = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeInt(indexDescription);
    }
}
