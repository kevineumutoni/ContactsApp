package com.kevine.contactsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevine.contactsapp.model.Contact
import com.kevine.contactsapp.repository.ContactsRepository
import kotlinx.coroutines.launch

class ContactsViewModel: ViewModel() {
    val contactsRepository = ContactsRepository()
    val contactsLiveData = MutableLiveData<List<Contact>>()
    val contactLiveData= MutableLiveData<Contact>()

    fun saveContact(contact: Contact){
        viewModelScope.launch {
            contactsRepository.saveContact(contact)
        }
    }

    fun getAllContacts(){
        viewModelScope.launch {
            contactsRepository.getAllContacts().observeForever{
                contactsLiveData.value= it
            }

        }

    }
    fun getContactById(contactId:Int){
        viewModelScope.launch {
            contactsRepository.getContactById(contactId).observeForever{
                contactLiveData.value= it
            }
        }
    }

}