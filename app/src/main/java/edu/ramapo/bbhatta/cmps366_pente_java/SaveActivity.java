package edu.ramapo.bbhatta.cmps366_pente_java;

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

