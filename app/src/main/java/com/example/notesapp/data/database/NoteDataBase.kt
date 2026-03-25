package com.example.notesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.data.entity.Note
import com.example.notesapp.data.local.dao.NoteDao
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = false
)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDataBase? = null

        fun getDataBase(context: Context): NoteDataBase {
            return INSTANCE ?: synchronized(this) {


                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "notes_database"
                )

                val isDebug = true
                if (!isDebug) {
                    val passphrase: ByteArray =
                        SQLiteDatabase.getBytes("my_secure_password".toCharArray())

                    val factory = SupportFactory(passphrase)

                    builder.openHelperFactory(factory)
                }

                val instance = builder
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}