package org.example.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.project.database.dao.TaskDao
import org.example.project.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}