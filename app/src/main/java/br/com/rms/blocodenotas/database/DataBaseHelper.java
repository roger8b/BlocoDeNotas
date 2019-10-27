package br.com.rms.blocodenotas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static br.com.rms.blocodenotas.database.NotesDataBaseContract.SQL_CREATE_NOTES_TABLE;
import static br.com.rms.blocodenotas.database.NotesDataBaseContract.SQL_DELETE_NOTES_TABLE;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "Notes.db";
    private static final int DATA_BASE_VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOTES_TABLE);

    }
}
