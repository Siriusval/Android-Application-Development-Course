package fi.jamk.roomwordsample.room.components

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Database
 * Contains dao and entities
 */

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [(Word::class)], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    /**
     * DAO to request db
     */
    abstract fun wordDao(): WordDao

    /**
     * Static variable db instance
     * Singleton
     * if not null, return instance
     * else create it
     */
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            //synchronized method is protected, cannot be executed twice at a time
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    /**
     * Execute when database is open
     */
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        /**
         * When db is open, populate it with some data
         */
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }


        /**
         * Populate data using dao
         */
        suspend fun populateDatabase(wordDao: WordDao) {
            // Delete all content here.
            wordDao.deleteAll()
            // Add sample words.
            var word = Word(0, "Hello")
            wordDao.insert(word)
            word = Word(0, "World")
            wordDao.insert(word)

            // TODO: Add your own words!
            word = Word(0, "TODO!")
            wordDao.insert(word)
        }
    }

}