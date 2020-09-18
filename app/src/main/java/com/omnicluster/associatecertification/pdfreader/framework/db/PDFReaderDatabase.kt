package com.omnicluster.associatecertification.pdfreader.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookmarkEntity::class, DocumentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PDFReaderDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "pdf_reader.db"

        private var instance: PDFReaderDatabase? = null

        private fun create(context: Context): PDFReaderDatabase =
            Room.databaseBuilder(context, PDFReaderDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()


        fun getInstance(context: Context): PDFReaderDatabase =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun bookmarkDao(): BookmarkDao

    abstract fun documentDao(): DocumentDao

}