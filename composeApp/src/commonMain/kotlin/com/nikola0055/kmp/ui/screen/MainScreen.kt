package com.nikola0055.kmp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.nikola0055.kmp.BuildKonfig
import com.nikola0055.kmp.createDataStore
import com.nikola0055.kmp.model.Hewan
import com.nikola0055.kmp.model.User
import com.nikola0055.kmp.network.ApiStatus
import com.nikola0055.kmp.network.HewanApi
import com.nikola0055.kmp.network.UserDataStore
import kmp.composeapp.generated.resources.*
import kotlinx.coroutines.launch
import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
import network.chaintech.cmpimagepickncrop.imagecropper.ImageAspectRatio
import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val dataStore = remember { UserDataStore(createDataStore()) }
    val user by dataStore.userFlow.collectAsState(User())
    val scope = rememberCoroutineScope()
    val googleAuthProvider = GoogleAuthProvider.create(
        credentials = GoogleAuthCredentials(serverId = BuildKonfig.API_KEY)
    )

    val viewModel: MainViewModel = viewModel{ MainViewModel() }
    val errorMessage by viewModel.errorMessage

    var showDialog by remember { mutableStateOf(false) }
    var showHewanDialog by remember { mutableStateOf(false) }

    var openImagePicker by remember { mutableStateOf(false) }
    val imageCropper = rememberImageCropper()
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    GoogleButtonUiContainer(
                        onGoogleSignInResult = { googleUser ->
                            if (googleUser != null) {
                                scope.launch {
                                    dataStore.saveData(
                                        User(
                                            name = googleUser.displayName,
                                            email = googleUser.email!!,
                                            photoUrl = googleUser.profilePicUrl!!
                                        )
                                    )
                                }
                            } else {
                                println("Authentication failed or was canceled.")
                            }
                        }
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (user.email.isNotEmpty()) {
                                        showDialog = true
                                    } else {
                                        this@GoogleButtonUiContainer.onClick()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.account_circle_24),
                                contentDescription = stringResource(Res.string.profil),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openImagePicker = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(Res.string.tambah_hewan)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { innerPadding ->
        ScreenContent(viewModel, user.email, Modifier.padding(innerPadding))

        if (showDialog) {
            ProfilDialog(
                user = user,
                onDismissRequest = {
                    showDialog = false
                }
            ) {
                scope.launch {
                    dataStore.saveData(User())
                    googleAuthProvider.signOut()
                }
                showDialog = false
            }
        }

        CMPImagePickNCropDialog(
            imageCropper = imageCropper,
            openImagePicker = openImagePicker,
            defaultAspectRatio = ImageAspectRatio(1, 1),
            aspects = listOf(ImageAspectRatio(1, 1)),
            shapes = emptyList(),
            imagePickerDialogHandler = {
                openImagePicker = it
            },
            selectedImageCallback = {
                val index = if (it.size >= 1) it.size - 1 else 0
                selectedImage = it[index]
                showHewanDialog = true
                openImagePicker = false
            },
            selectedImageFileCallback = { }
        )

        if (showHewanDialog) {
            HewanDialog(
                imageBitmap = selectedImage!!,
                onDismissRequest = {
                    showHewanDialog = false
                },
                onConfirmation = { nama, namaLatin ->
                    viewModel.saveData(user.email, nama, namaLatin, selectedImage!!)
                    showHewanDialog = false 
                }
            )
        }

        if (errorMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = errorMessage!!,
                    duration = SnackbarDuration.Short
                )
                viewModel.clearMessage()
            }
        }
    }
}

@Composable
fun ScreenContent(
    viewModel: MainViewModel,
    userId: String,
    modifier: Modifier
) {
    val data by viewModel.data
    val status by viewModel.status.collectAsState()
    var selectedHewan by remember { mutableStateOf<Hewan?>(null) }

    LaunchedEffect(userId) {
        viewModel.retrieveData(userId)
    }

    if (selectedHewan != null) {
        AlertDialog(
            onDismissRequest = { selectedHewan = null },
            title = { Text(stringResource(Res.string.hapus)) },
            text = { Text(stringResource(Res.string.konfirmasi_hapus)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteData(userId, selectedHewan!!.id)
                    selectedHewan = null
                }) {
                    Text(stringResource(Res.string.hapus))
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedHewan = null }) {
                    Text(stringResource(Res.string.batal))
                }
            }
        )
    }

    when (status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        ApiStatus.SUCCESS -> {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize().padding(4.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(data) {
                    ListItem(hewan = it) {
                        selectedHewan = it
                    }
                }
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(Res.string.error))
                Button(
                    onClick = { viewModel.retrieveData(userId) },
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal=32.dp, vertical=16.dp)
                ) {
                    Text(text = stringResource(Res.string.try_again))
                }
            }
        }
    }

}

@Composable
fun ListItem(hewan: Hewan, hapusAction: () -> Unit) {
    Box(
        modifier = Modifier.padding(4.dp).border(1.dp, Color.Gray),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(HewanApi.getHewanUrl(hewan.imageId))
                .crossfade(true)
                .build(),
            contentDescription = stringResource(Res.string.gambar, hewan.nama),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.loading_img),
            error = painterResource(Res.drawable.broken_img),
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp)
                .background(Color(red = 0f, green = 0f, blue = 0f, alpha = 0.5f))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = hewan.nama,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = hewan.namaLatin,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            if (hewan.mine == 1) {
                IconButton(
                    onClick = hapusAction,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(Res.string.hapus),
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreviewLight() {
    MaterialTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        MainScreen()
    }
}