package br.com.rms.blocodenotas.database;

import android.provider.BaseColumns;


public final class NotesDataBaseContract {

    static final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " + Notes.TABLE_NAME +
            " (" +
            Notes.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Notes.COLUMN_TITLE + " VARCHAR NOT NULL, " +
            Notes.COLUMN_NOTE + " VARCHAR NOT NULL, " +
            Notes.COLUMN_DATA + " VARCHAR NOT NULL " + " ); ";

    static final String SQL_DELETE_NOTES_TABLE = "DROP TABLE IF EXISTS " + Notes.TABLE_NAME;

    public NotesDataBaseContract() {

    }

    static class Notes implements BaseColumns {
        static final String TABLE_NAME = "notes";
        static final String COLUMN_ID = "id";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_NOTE = "text";
        static final String COLUMN_DATA = "data";
    }
}
