package br.com.rms.blocodenotas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.rms.blocodenotas.pojo.Note;
import br.com.rms.blocodenotas.utils.UtilsDate;

import static br.com.rms.blocodenotas.database.NotesDataBaseContract.Notes.COLUMN_DATA;
import static br.com.rms.blocodenotas.database.NotesDataBaseContract.Notes.COLUMN_ID;
import static br.com.rms.blocodenotas.database.NotesDataBaseContract.Notes.COLUMN_NOTE;
import static br.com.rms.blocodenotas.database.NotesDataBaseContract.Notes.COLUMN_TITLE;
import static br.com.rms.blocodenotas.database.NotesDataBaseContract.Notes.TABLE_NAME;

public class NotesDataBaseHelper {

    private static final long ERROR_ID = -1;
    private SQLiteDatabase db;

    public NotesDataBaseHelper(Context context) {
        db = new DataBaseHelper(context).getReadableDatabase();
    }

    public boolean insertNote(String title, String note) {
        boolean noteHasBeenInserted = false;
        long id = ERROR_ID;
        try {

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_NOTE, note);
            values.put(COLUMN_DATA, UtilsDate.getCurrentDateTime());
            id = db.insert(TABLE_NAME, null, values);

        } catch (SQLException error) {
            Log.e("SQL ERROR", "Error on Insert Data", error);
        }

        if (id != ERROR_ID) {

            noteHasBeenInserted = true;
        }

        return noteHasBeenInserted;
    }

    public Boolean deleteNote(Note note, ArrayList<Note> list) {
        boolean noteHasBeenDeleted = false;
        String whereClause = COLUMN_ID + " = " + note.getId();
        int deleteResult = db.delete(TABLE_NAME, whereClause, null);
        if (deleteResult != 0) {
            list.remove(note);
            noteHasBeenDeleted = true;
        }
        return noteHasBeenDeleted;
    }

    public boolean updateNote(Note note) {
        boolean noteHasBeenUpdated = false;
        String whereClause = COLUMN_ID + " = " + note.getId();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_NOTE, note.getText());
        values.put(COLUMN_DATA, UtilsDate.getCurrentDateTime());
        int updateResult = db.update(
                TABLE_NAME,
                values,
                whereClause,
                null);

        if (updateResult != 0) {
            noteHasBeenUpdated = true;
        }
        return noteHasBeenUpdated;

    }

    public ArrayList<Note> loadNotes() {
        ArrayList<Note> list = new ArrayList<>();
        try (Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null)) {

            while (cursor.moveToNext()) {
                Note note = new Note(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA)));
                list.add(note);
            }
        }

        return list;
    }
}
