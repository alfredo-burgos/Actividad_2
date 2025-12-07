package com.fic.relaciones_y_consultas.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Category.class, Note.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private static volatile AppDatabase instance;

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "notes_db"
                            )
                            .build();
                }
            }
        }
        return instance;
    }
}
