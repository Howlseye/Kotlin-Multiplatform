package com.nikola0055.kmp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikola0055.kmp.model.Catatan
import kmp.composeapp.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Pengganti Toast
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
                )
            )
        },
        // Pengganti Toast
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding), snackbarHostState)
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    val viewModel: MainViewModel = viewModel{ MainViewModel() }
    val data = viewModel.data

    // Pengganti Toast
    val scope = rememberCoroutineScope()

    if (data.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(Res.string.list_kosong))
        }
    }
    else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(data) {
                val pesan = stringResource(Res.string.x_diklik, it.judul)
                ListItem(catatan = it) {
                    // Pengganti Toast
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = pesan,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ListItem(catatan: Catatan, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = catatan.judul,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = catatan.catatan,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = catatan.tanggal)
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