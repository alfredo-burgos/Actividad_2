package com.fic.relaciones_y_consultas.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCategory(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertNote(Note note);

    @Transaction
    @Query("SELECT * FROM categories")
    List<CategoryWithNotes> getAllCategoriesWithNotes();

    @Query("SELECT * FROM notes WHERE category_id = :categoryId ORDER BY created_at DESC")
    List<Note> getNotesByCategory(long categoryId);

    @Query("SELECT * FROM notes " +
            "WHERE note_title LIKE '%' || :query || '%' " +
            "   OR note_content LIKE '%' || :query || '%' " +
            "ORDER BY created_at DESC")
    List<Note> searchNotes(String query);

    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();

}
