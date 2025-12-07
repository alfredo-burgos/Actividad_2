package com.fic.relaciones_y_consultas.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    public long category_id;

    @ColumnInfo(name = "category_name")
    public String category_name;


    public Category(@NonNull String category_name) {
        this.category_name = category_name;
    }
}
