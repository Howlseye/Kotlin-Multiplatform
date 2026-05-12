package com.nikola0055.kmp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.IconButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.nikola0055.kmp.navigation.Screen
import com.nikola0055.kmp.shareData
import kmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt
import kotlin.text.all

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController
) {
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
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(Res.string.tentang)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier
) {
    var panjang by rememberSaveable { mutableStateOf("") }
    var panjangError by rememberSaveable { mutableStateOf(false) }

    var lebar by rememberSaveable { mutableStateOf("") }
    var lebarError by rememberSaveable { mutableStateOf(false) }


    var luas by rememberSaveable { mutableFloatStateOf(0f) }
    var keliling by rememberSaveable { mutableFloatStateOf(0f) }


    Column(
        modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = panjang,
            onValueChange = { panjangValue ->
                if (panjangValue.all { it.isDigit() || it == '.' } && panjangValue != ".") {
                    panjang = panjangValue
                }
            },
            label = {
                Text(text = stringResource(Res.string.panjang))
            },
            trailingIcon = {
                IconPicker(
                    isError = panjangError,
                    unit = "cm"
                )
            },
            isError = panjangError,
            supportingText = {
                ErrorHint(
                    isError = panjangError
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lebar,
            onValueChange = { lebarValue ->
                if (lebarValue.all { it.isDigit() || it == '.' } && lebarValue != ".") {
                    lebar = lebarValue
                }
            },
            label = {
                Text(text = stringResource(Res.string.lebar))
            },
            trailingIcon = {
                IconPicker(
                    isError = lebarError,
                    unit = "cm"
                )
            },
            isError = lebarError,
            supportingText = {
                ErrorHint(
                    isError = lebarError
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    panjangError = panjang.isBlank() || panjang.toFloat() <= 0
                    lebarError = lebar.isBlank() || lebar.toFloat() <= 0
                    if (panjangError || lebarError) return@Button

                    luas = luas(panjang.toFloat(), lebar.toFloat())
                    keliling = keliling(panjang.toFloat(), lebar.toFloat())
                },
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(text = stringResource(Res.string.hitung))
            }

            Spacer(Modifier.width(16.dp))

            Button(
                onClick = {
                    panjang = ""
                    lebar = ""
                    panjangError = false
                    lebarError = false
                    luas = 0f
                    keliling = 0f
                },
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(text = stringResource(Res.string.reset))
            }
        }

        if (luas != 0f) {
            val message = stringResource(
                Res.string.bagikan_template,
                panjang,
                lebar,
                ((luas * 100).roundToInt() / 100f).toString(),
                ((keliling * 100).roundToInt() / 100f).toString()
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
            )
            Text(
                text = stringResource(Res.string.luas_x, ((luas * 100).roundToInt() / 100f).toString()),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(Res.string.keliling_x, ((keliling * 100).roundToInt() / 100f).toString()),
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = { shareData(message) },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(32.dp, 16.dp),
            ) {
                Text(text = stringResource(Res.string.bagikan))
            }
        }
    }
}

private fun luas(panjang: Float, lebar: Float): Float {
    return panjang * lebar
}

private fun keliling(panjang: Float, lebar: Float): Float {
    return 2 * (panjang + lebar)
}

@Composable
fun IconPicker(
    isError: Boolean,
    unit: String
) {
    if (isError) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
        )
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(
    isError: Boolean,
) {
    if (isError) {
        Text(stringResource(Res.string.input_invalid))
    }
}