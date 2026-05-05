package com.nikola0055.kmp

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    val darkTheme = isSystemInDarkTheme()

    MaterialTheme(colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()) {
        MainScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen() {
    var isOn by remember { mutableStateOf(false) }
    val status = listOf(
        Lampu(
            status = "Mati",
            stat = false,
            gambar = Res.drawable.lamp_off
        ),
        Lampu(
            status = "Nyala",
            stat = true,
            gambar = Res.drawable.lamp_on
        )
    )

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
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding),
            status = status[if (isOn) 1 else 0]
        ) {
            isOn = !isOn
        }
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    status: Lampu,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(status.gambar),
            contentDescription = null,
            modifier = modifier.size(132.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = stringResource(Res.string.status, status.status),
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier.padding(8.dp)
        )

        Button(
            onClick = onClick,
            modifier = modifier.padding(16.dp).fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = stringResource(if (status.stat) Res.string.mati else Res.string.nyala))
        }
    }
}
