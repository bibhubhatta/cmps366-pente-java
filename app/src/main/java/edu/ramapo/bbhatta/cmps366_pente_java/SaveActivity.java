package edu.ramapo.bbhatta.cmps366_pente_java;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStream;

public class SaveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        final EditText fileNameEditText = findViewById(R.id.fileNameEditText);
        Button saveButton = findViewById(R.id.saveButton);

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
            outputStream.write("hello".getBytes());
            Toast.makeText(this, "Text saved to Downloads", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save text", Toast.LENGTH_SHORT).show();
        }

    }
}

