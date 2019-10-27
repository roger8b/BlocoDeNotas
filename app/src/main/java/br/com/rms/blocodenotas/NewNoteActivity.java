package br.com.rms.blocodenotas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.rms.blocodenotas.database.NotesDataBaseHelper;
import br.com.rms.blocodenotas.servicelocator.ServiceLocator;

public class NewNoteActivity extends AppCompatActivity {

    EditText inputTitle;
    EditText inputNote;
    Button buttonSaveNote;
    NotesDataBaseHelper dataBaseHelper;
    ServiceLocator serviceLocator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        setTitle(R.string.label_create_note);

        serviceLocator = ((App) getApplication()).service;
        dataBaseHelper = serviceLocator.get(NotesDataBaseHelper.class);

        setupButtonSaveNote();
        setupInputTitle();
        setupInputNote();
    }

    private void setupInputTitle() {
        inputTitle = (EditText) findViewById(R.id.input_title);
    }

    private void setupInputNote() {
        inputNote = (EditText) findViewById(R.id.input_note);
    }

    private void setupButtonSaveNote() {
        buttonSaveNote = findViewById(R.id.bt_save_note);
        buttonSaveNote.setOnClickListener(v -> saveNoteOnDataBase());
    }

    private void saveNoteOnDataBase() {
        String title = inputTitle.getText().toString();
        String note = inputNote.getText().toString();
        if (validateData(title, note)) {
            boolean noteHasBeenInserted = dataBaseHelper.insertNote(title, note);

            if (noteHasBeenInserted) {
                cleanForm();
                setResult(RESULT_OK);
            }
        }
    }

    private void cleanForm() {
        inputTitle.setText("");
        inputNote.setText("");
    }

    private Boolean validateData(String title, String note) {
        boolean dataIsValid = true;

        if (title.isEmpty()) {
            inputTitle.setError(
                    this.getString(R.string.error_message_title_field_is_required));

            dataIsValid = false;

        } else if (note.isEmpty()) {
            inputNote.setError(
                    this.getString(R.string.error_message_note_field_is_required));

            dataIsValid = false;
        }

        return dataIsValid;
    }
}
