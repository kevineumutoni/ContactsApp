package com.kevine.contactsapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevine.contactsapp.model.Contact
import com.kevine.contactsapp.viewmodel.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen( onClickAddContact: () -> Unit,
                    viewModel: ContactsViewModel = viewModel()) {
    val contacts = viewModel.contactsLiveData.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllContacts()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "My contacts") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onClickAddContact()},
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        val contactsList = contacts.value
        if (contactsList.isNullOrEmpty()) {
            Box(modifier = Modifier.padding(paddingValues)) {
                Text("No contacts found")
            }
        } else {

            LazyColumn(contentPadding = paddingValues) {
                items(contactsList) { contact ->
                    ContactCard(contact)
                }
            }
        }
    }
}
@Composable
fun ContactCard(contact: Contact){
    Card (modifier = Modifier.fillMaxWidth()){
        Row{
            Icon(imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(64.dp))
            Spacer(Modifier.width(16.dp))

            Column {
                Text(text = contact.name)
                Text(text = contact.phoneNumber)
                Text(text = contact.email)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactCardPreview(){
    ContactCard(contact = Contact(
        contactId = 1,
        name = "LoveLace",
        phoneNumber = "0791507934",
        email = "kev@gmail.com",
        imageUrl = ""
    ))
}