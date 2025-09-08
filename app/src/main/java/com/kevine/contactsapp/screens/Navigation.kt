package com.kevine.contactsapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screens(val route: String) {
    object Contacts : Screens("contacts")
    object AddContact : Screens("addContact")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Contacts.route
    ) {
        composable(Screens.Contacts.route) {
            ContactsScreen(onClickAddContact = {
                navController.navigate(Screens.AddContact.route)
            })
        }
        composable(Screens.AddContact.route) {
            AddContactScreen()
        }
    }
}