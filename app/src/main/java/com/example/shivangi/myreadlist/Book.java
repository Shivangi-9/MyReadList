package com.example.shivangi.myreadlist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class Book {

    //Attributes
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String author;

    @ColumnInfo(name = "category")
    private String cat;

    private String status;

    //Constructor
    public Book(String title, String author, String cat, String status) {
        this.title = title;
        this.author = author;
        this.cat = cat;
        this.status = status;
    }

    //Setter methods
    public void setId(int id) {
        this.id = id;
    }

    //Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCat() {
        return cat;
    }

    public String getStatus() {
        return status;
    }
}
