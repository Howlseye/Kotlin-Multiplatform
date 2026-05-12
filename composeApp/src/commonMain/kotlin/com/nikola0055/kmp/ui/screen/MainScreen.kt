package com.nikola0055.kmp.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.navigation.NavHostController
import com.nikola0055.kmp.navigation.Screen
import com.nikola0055.kmp.shareData
import kmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import kotlin.math.pow
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
                            contentDescription = stringResource(Res.string.tentang_aplikasi)
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
    var berat by rememberSaveable { mutableStateOf("") }
    var beratError by rememberSaveable { mutableStateOf(false) }

    var tinggi by rememberSaveable { mutableStateOf("") }
    var tinggiError by rememberSaveable { mutableStateOf(false) }

    val radioOptions = listOf(
        Res.string.pria,
        Res.string.wanita
    )
    var gender by rememberSaveable { mutableIntStateOf(0) }

    var bmi by rememberSaveable { mutableFloatStateOf(0f) }
    val kategoriList = listOf(
        Res.string.kurus,
        Res.string.ideal,
        Res.string.gemuk
    )
    var kategori by rememberSaveable { mutableIntStateOf(0) }


    Column(
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.bmi_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = berat,
            onValueChange = { beratValue ->
                if (beratValue.all { it.isDigit() || it == '.' } && beratValue != ".") {
                    berat = beratValue
                }
            },
            label = {
                Text(text = stringResource(Res.string.berat_badan))
            },
            trailingIcon = {
                IconPicker(
                    isError = beratError,
                    unit = "kg"
                )
            },
            isError = beratError,
            supportingText = {
                ErrorHint(
                    isError = beratError
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
            value = tinggi,
            onValueChange = { tinggiValue ->
                if (tinggiValue.all { it.isDigit() || it == '.' } && tinggiValue != ".") {
                    tinggi = tinggiValue
                }
            },
            label = {
                Text(text = stringResource(Res.string.tinggi_badan))
            },
            trailingIcon = {
                IconPicker(
                    isError = tinggiError,
                    unit = "cm"
                )
            },
            isError = tinggiError,
            supportingText = {
                ErrorHint(
                    isError = tinggiError
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
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEachIndexed { index, item ->
                GenderOption(
                    label = stringResource(radioOptions[index]),
                    isSelected = item == radioOptions[gender],
                    modifier = Modifier.weight(1f).padding(16.dp).selectable(
                        selected = item == radioOptions[gender],
                        onClick = { gender = index },
                        role = Role.RadioButton
                    )
                )
            }
        }

        Button(
            onClick = {
                beratError = berat.isBlank() || berat.toFloat() <= 0
                tinggiError = tinggi.isBlank() || tinggi.toFloat() <= 0
                if (beratError || tinggiError) return@Button


                bmi = hitungBMI(berat.toFloat(), tinggi.toFloat())
                kategori = getKategori(bmi, radioOptions[gender] == radioOptions[0])
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
        ) {
            Text(text = stringResource(Res.string.hitung))
        }

        if (bmi != 0f) {
            val message = stringResource(
                Res.string.bagikan_template,
                berat,
                tinggi,
                gender,
                ((bmi * 100).roundToInt() / 100f).toString(),
                kategoriList[kategori]
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
            )
            Text(
                text = stringResource(Res.string.bmi_x, ((bmi * 100).roundToInt() / 100f).toString()),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(kategoriList[kategori]),
                style = MaterialTheme.typography.headlineLarge
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

@Composable
fun GenderOption(
    label: String,
    isSelected: Boolean,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

private fun hitungBMI(berat: Float, tinggi: Float): Float {
    return berat / ((tinggi / 100).pow(2))
}

private fun getKategori(bmi: Float, isMale: Boolean): Int {
    return if (isMale) {
        when {
            bmi < 20.5 -> 0
            bmi >= 27.0 -> 2
            else -> 1
        }
    } else {
        when {
            bmi < 18.5 -> 0
            bmi >= 25.0 -> 2
            else -> 1
        }
    }
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