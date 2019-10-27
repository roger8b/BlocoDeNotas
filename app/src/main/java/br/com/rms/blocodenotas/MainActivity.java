package br.com.rms.blocodenotas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.com.rms.blocodenotas.database.NotesDataBaseHelper;
import br.com.rms.blocodenotas.pojo.Note;
import br.com.rms.blocodenotas.servicelocator.ServiceLocator;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE = "text";
    private static final int NEW_NOTE = 1000;
    private static final int UPDATE_NOTE = 1001;
    Button buttonNewNote;
    ListView notesList;
    ArrayAdapter<Note> adapter;
    ArrayList<Note> list;
    NotesDataBaseHelper dataBaseHelper;
    ServiceLocator serviceLocator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceLocator = ((App) getApplication()).service;
        dataBaseHelper = serviceLocator.get(NotesDataBaseHelper.class);

        setupButtonNewNote();
        setupNotesList();
    }

    private void setupButtonNewNote() {
        buttonNewNote = findViewById(R.id.bt_new_note);
        buttonNewNote.setOnClickListener(v -> showNewNoteActivity());
    }

    private void setupNotesList() {
        list = dataBaseHelper.loadNotes();
        notesList = findViewById(R.id.notes_list);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        notesList.setAdapter(adapter);
        notesList.setOnItemClickListener((parent, view, position, id) -> showNoteDetailDialog(list.get(position)));
    }

    private void updateNoteList() {
        list.clear();
        list.addAll(dataBaseHelper.loadNotes());
        adapter.notifyDataSetChanged();
    }

    private void showNoteDetailDialog(Note note) {
        View inflate = getDialogCustomView(note);
        new AlertDialog.Builder(this).setView(inflate)
                .setPositiveButton(R.string.label_edit, (dialog, which) -> showUpdateActivity(note))
                .setNeutralButton(R.string.erase_label, (dialog, which) -> deleteNote(note))
                .setNegativeButton(R.string.label_exit, (dialog, which) -> showDebugLogMessage("Exit note dialog"))
                .show();
    }

    private void showDebugLogMessage(String message) {
        Log.d("Notes", message);
    }

    private View getDialogCustomView(Note note) {
        View inflate = this.getLayoutInflater().inflate(R.layout.dialog_note, null);
        ((TextView) inflate.findViewById(R.id.note_title)).setText(note.getTitle());
        ((TextView) inflate.findViewById(R.id.text)).setText(note.getText());
        ((TextView) inflate.findViewById(R.id.date)).setText(note.getDate());
        return inflate;
    }

    private void deleteNote(Note note) {
        Boolean noteHasBeenDeleted;
        noteHasBeenDeleted = dataBaseHelper.deleteNote(note, list);

        if (noteHasBeenDeleted) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                updateNoteList();
            }
        } else if (requestCode == UPDATE_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                updateNoteList();
            }
        }
    }

    private void showUpdateActivity(Note note) {
        Intent intent = new Intent(this, UpdateNoteActivity.class);
        intent.putExtra(NOTE, note);
        startActivityForResult(intent, UPDATE_NOTE);
    }

    private void showNewNoteActivity() {
        Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
        startActivityForResult(intent, NEW_NOTE);
    }
}
