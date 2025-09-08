package com.kevine.contactsapp.repository

import androidx.lifecycle.LiveData
import com.kevine.contactsapp.ContactsApp
import com.kevine.contactsapp.database.ContactsDatabase
import com.kevine.contactsapp.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsRepository {
    val database = ContactsDatabase.getDatabase(ContactsApp.appContext)
    suspend fun saveContact(contact: Contact){
        withContext(Dispatchers.IO){
            database.contactDao().insertContact(contact)
        }
    }
    fun getAllContacts(): LiveData<List<Contact>>{
        return database.contactDao().getAllContacts()
    }

    fun getContactById(contactId:Int): LiveData<Contact>{
        return database.contactDao().getContactById(contactId)
    }

}