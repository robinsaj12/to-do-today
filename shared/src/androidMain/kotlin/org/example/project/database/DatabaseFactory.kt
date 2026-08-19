package org.example.project.database

import android.content.Context
import androidx.room.Room

object DatabaseFactory {

    fun create(context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }
}