package com.example.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Note implements Parcelable {
    private String noteName;
    private int indexDescription;
    private int pictureNumber;
    Date date;
    private String description;

    public Note(String noteName, int indexDescription, Date date) {
        this.noteName = noteName;
        this.indexDescription = indexDescription;
        this.date = date;
    }

    public Note(String noteName, Date date, String description) {
        this.noteName = noteName;
        this.date = date;
        this.description = description;
    }

    public Note(String noteName, int indexDescription, int pictureNumber, Date date) {
        this.noteName = noteName;
        this.indexDescription = indexDescription;
        this.pictureNumber = pictureNumber;
        this.date = date;
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

    public void setDescription(String description) {
        this.description = description;
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
        dest.writeInt(indexDescription);
        dest.writeInt(pictureNumber);
        dest.writeString(description);
        dest.writeLong(date.getTime());
    }
}
