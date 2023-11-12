package edu.ramapo.bbhatta.cmps366_pente_java.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Log {

    private final ArrayList<String> logEntries;

    public Log() {
        logEntries = new ArrayList<>();
    }

    public void add(String message) {
        logEntries.add(message);
    }

    public List<String> getLog() {
        return logEntries;
    }

    public void clear() {
        logEntries.clear();
    }

    @NonNull
    public String toString() {
        StringBuilder logString = new StringBuilder();
        for (String message : logEntries) {
            logString.append(message).append("\n\n");
        }

        return logString.toString();
    }

}
