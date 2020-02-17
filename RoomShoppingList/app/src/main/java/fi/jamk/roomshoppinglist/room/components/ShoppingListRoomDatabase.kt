package fi.jamk.roomshoppinglist.room.components

import androidx.room.Database
import androidx.room.RoomDatabase
import fi.jamk.roomshoppinglist.room.components.ShoppingListDao
import fi.jamk.roomshoppinglist.room.components.ShoppingListItem

@Database(entities = [ShoppingListItem::class], version = 1)
abstract class ShoppingListRoomDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
}