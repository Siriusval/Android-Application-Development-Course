package fi.jamk.roomwordsample.room.components

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Word entity, to create table
 */
@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "word")
    @NotNull
    val word: String
)