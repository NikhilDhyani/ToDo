package com.example.nikhil.tod0;

/**
 * Created by NIKHIL on 10-06-2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DatabaseInterface {

    @Query("SELECT * FROM todolistitem")
    List<TodoListItem> getAllItems();

    @Insert
    void insertAll(TodoListItem... todoListItems);
}
