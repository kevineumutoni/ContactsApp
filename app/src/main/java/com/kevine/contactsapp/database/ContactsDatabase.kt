package com.kevine.contactsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kevine.contactsapp.model.Contact

@Database(entities = [Contact::class], version = 1)

abstract class ContactsDatabase: RoomDatabase() {

}