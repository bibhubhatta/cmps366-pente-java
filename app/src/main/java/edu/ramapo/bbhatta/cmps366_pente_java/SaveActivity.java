package edu.ramapo.bbhatta.cmps366_pente_java;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.OutputStream;

public class SaveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTextToDownloadsDirectory("hello.txt");
            }
        });
    }

    private void saveTextToDownloadsDirectory(String fileName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");

        Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

        try {
            if (uri != null) {
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    outputStream.write("hello".getBytes());
                    outputStream.close();
                    Toast.makeText(this, "Text saved to Downloads", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save text", Toast.LENGTH_SHORT).show();
        }
    }
}
