package com.kevine.contactsapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screens(val route: String) {
    object Contacts : Screens("contacts")
    object AddContact : Screens("addContact")
    object ContactDetails: Screens("contactDetails")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Contacts.route
    ) {
        composable(Screens.Contacts.route) {
            ContactsScreen(
                onClickAddContact = {
                    navController.navigate(Screens.AddContact.route)
                },
                onClickContact = { contactId ->
                    navController.navigate("${Screens.ContactDetails.route}/$contactId")
                }
            )
        }
        composable(Screens.AddContact.route) {
            AddContactScreen()
        }
        composable("${Screens.ContactDetails.route}/{contactId}") { navBackStackEntry ->
            val contactId = navBackStackEntry.arguments?.getString("contactId")
            if (contactId != null) {
                ContactDetailsScreen(contactId = contactId.toInt())
            }
        }
    }
}