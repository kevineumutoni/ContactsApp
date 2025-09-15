package com.kevine.contactsapp.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.kevine.contactsapp.viewmodel.ContactsViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactDetailsScreen(contactId: Int, viewModel: ContactsViewModel = viewModel()) {
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }
    val ctx = LocalContext.current
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPhotoUri != null) {
            photoUri = tempPhotoUri
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            tempPhotoUri = createImageUri(ctx)
            tempPhotoUri?.let { uri ->
                cameraLauncher.launch(uri)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getContactById(contactId)
    }
    val contact by viewModel.contactLiveData.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "",
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        contact?.let {
            Text(text = it.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text(text = "📞 ${it.phoneNumber}")
            if (it.email.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(text = "📧 ${it.email}")
            }
            if (!it.imageUrl.isNullOrBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(text = "🖼️ ${it.imageUrl}")
            }
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
                Icon(imageVector = Icons.Outlined.Send, contentDescription = "Message")
            }
        }
    }
}



private fun createImageUri(context: Context): Uri? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = File(context.getExternalFilesDir(null), "Pictures")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        val imageFile = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}