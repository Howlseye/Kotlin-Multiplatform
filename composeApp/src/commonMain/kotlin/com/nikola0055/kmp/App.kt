package com.nikola0055.kmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikola0055.kmp.ui.theme.AppTheme
import kmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(Res.string.app_name))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { innerPadding ->
            ScreenContent(Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScreenContent(
    modifier: Modifier = Modifier
) {
    var number by remember { mutableIntStateOf(0) }

    Row(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { number++ },
            shape = CircleShape,
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = "+",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Text(
            text = number.toString(),
            modifier = Modifier.padding(32.dp),
            style = MaterialTheme.typography.displayLarge
        )

        Button(
            onClick = { if (number > 0) number-- },
            shape = CircleShape,
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = "-",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}
