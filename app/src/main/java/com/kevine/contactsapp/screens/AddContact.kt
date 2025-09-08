package com.kevine.contactsapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevine.contactsapp.model.Contact
import com.kevine.contactsapp.viewmodel.ContactsViewModel

@Composable
fun AddContactScreen(
    viewModel: ContactsViewModel= viewModel ()
){
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column (Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Add contact",
            style = MaterialTheme.typography.titleLarge

            )
        OutlinedTextField(
            value = name,
            onValueChange = {name= it},
            placeholder = {Text(text = "Name")}
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {phoneNumber= it},
            placeholder = {Text(text = "Phone number")}
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email= it},
            placeholder = {Text(text = "Email")}
        )
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val newContact = Contact(
                contactId = 0,
                name = name,
                phoneNumber = phoneNumber,
                email = email,
                imageUrl = ""
            )
            viewModel.saveContact(contact = newContact)
        }) {
            Text(text = "Save contact")

        }


    }

}