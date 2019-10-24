package br.com.rms.atividade2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.rms.atividade2.database.NotesDataBaseHelper;
import br.com.rms.atividade2.pojo.Note;

import static br.com.rms.atividade2.MainActivity.NOTE;

public class UpdateNoteActivity extends AppCompatActivity {

    EditText inputTitle;
    EditText inputNote;
    Button buttonSaveNote;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        note = (Note) getIntent().getParcelableExtra(NOTE);

        if (note != null) {
            setupInputTitle(note.getTitle());
            setupInputNote(note.getText());
        }

        setupButtonSaveNote();

    }

    private void setupInputTitle(String title) {
        inputTitle = (EditText) findViewById(R.id.input_title);
        inputTitle.setText(title);
    }

    private void setupInputNote(String text) {
        inputNote = (EditText) findViewById(R.id.input_note);
        inputNote.setText(text);
    }

    private void setupButtonSaveNote() {
        buttonSaveNote = findViewById(R.id.bt_save_note);
        buttonSaveNote.setOnClickListener(v -> updateNoteOnDataBase());
    }

    private void updateNoteOnDataBase() {
        String title = inputTitle.getText().toString();
        String text = inputNote.getText().toString();
        if (validateData(title, text)) {

            note.setTitle(title);
            note.setText(text);

            boolean noteHasBeenInserted = new NotesDataBaseHelper(this).updateNote(note);

            if (noteHasBeenInserted) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private Boolean validateData(String title, String note) {
        boolean dataIsValid = true;
        if (title.isEmpty()) {
            inputTitle.setError(this.getString(R.string.error_title_required));
            dataIsValid = false;
        } else if (note.isEmpty()) {
            inputNote.setError(this.getString(R.string.error_note_required));
            dataIsValid = false;
        }
        return dataIsValid;
    }

}
