package com.example.sqlitedatabasecrud.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyHelper(context: Context
): SQLiteOpenHelper(context, "Database", null, 1)
{
    override fun onCreate(db: SQLiteDatabase?) {
       // create table and column
        db?.execSQL("CREATE TABLE User(_id INTEGER  PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)")
        // insert data to table
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh', 'hoanganh@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 2', 'hoanganh2@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 3', 'hoanganh3@@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 4', 'hoanganh4@@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 5', 'hoanganh5@@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 6', 'hoanganh6@@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 7', 'hoanganh7@@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 8', 'hoanganh8@@gmail.com')")
        db?.execSQL("INSERT INTO User(name, email) VALUES('Hoang Anh 9', 'hoanganh9@@gmail.com')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}