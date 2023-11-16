package edu.ramapo.bbhatta.cmps366_pente_java.views;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.Objects;

import edu.ramapo.bbhatta.cmps366_pente_java.R;

public class SaveActivity extends Activity {

    private Button saveButton;
    private EditText fileNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        fileNameEditText = findViewById(R.id.fileNameEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(view -> {
            String fileName = fileNameEditText.getText().toString();
            saveTextToDownloadsDirectory(fileName);
        });
    }

    /**
     * Save the text to the Downloads directory.
     * <p>
     * Assistance for this method from:
     * https://stackoverflow.com/questions/59511147/create-copy-file-in-android-q-using-mediastore
     * https://www.youtube.com/watch?v=idsUMiWjfnM
     * https://stackoverflow.com/questions/61984396/save-txt-file-on-android-q
     * https://www.google.com/search?q=media+store+save+txt+file+android&sca_esv=580414175&ei=qFZLZebpOabtptQP8-yukAU&ved=0ahUKEwjm3c2YjbSCAxWmtokEHXO2C1IQ4dUDCBA&uact=5&oq=media+store+save+txt+file+android&gs_lp=Egxnd3Mtd2l6LXNlcnAiIW1lZGlhIHN0b3JlIHNhdmUgdHh0IGZpbGUgYW5kcm9pZDIFEAAYogQyCBAAGIkFGKIEMgUQABiiBEjFD1DyBlj3DHABeAGQAQCYAWygAbICqgEDMy4xuAEDyAEA-AEBwgIKEAAYRxjWBBiwA8ICChAhGKABGMMEGAriAwQYACBBiAYBkAYI&sclient=gws-wiz-serp
     * https://stackoverflow.com/questions/61984396/save-txt-file-on-android-q
     *
     * @param fileName The name of the file to save.
     */
    private void saveTextToDownloadsDirectory(String fileName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");

        Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        if (uri == null) {
            Toast.makeText(this, "Failed to save file", Toast.LENGTH_SHORT).show();
            return;
        }


        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            String output = MainActivity.pente.getSerialString();
            Objects.requireNonNull(outputStream).write(output.getBytes());
            Toast.makeText(this, "Text saved to Downloads", Toast.LENGTH_SHORT).show();

            // Hide the text input
            fileNameEditText.setVisibility(View.GONE);

            // Rename the save button to exit
            saveButton.setText(R.string.exit);
            saveButton.setOnClickListener(view -> finish());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save text", Toast.LENGTH_SHORT).show();
        }

    }
}

