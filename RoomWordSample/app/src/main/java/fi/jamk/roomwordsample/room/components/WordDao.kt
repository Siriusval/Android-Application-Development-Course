package fi.jamk.roomwordsample.room.components

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Word Data Access Object
 * Used for manage words in database
 */
@Dao
interface WordDao {

    //Wrap in LiveData to chain update
    @Query("SELECT * from word_table ORDER BY word COLLATE NOCASE ASC") //Alphabetical order, lowercase or uppercase doesn't matter
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}