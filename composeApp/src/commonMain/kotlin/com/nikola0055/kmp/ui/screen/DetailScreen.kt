package com.nikola0055.kmp.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nikola0055.kmp.database.MahasiswaDb
import com.nikola0055.kmp.getDatabaseBuilder
import kmp.composeapp.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    id: Long? = null
) {
    val builder = getDatabaseBuilder()
    val database = MahasiswaDb.getInstance(builder)
    val dao = database.mahasiswaDao
    val viewModel: DetailViewModel = viewModel{ DetailViewModel(dao) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val invalidString = stringResource(Res.string.invalid)

    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableLongStateOf(0) }
    val kelasOptions = listOf(
        "D3IF-01-48",
        "D3IF-02-48",
        "D3IF-03-48",
        "D3IF-04-48"
    )
    var kelas by remember { mutableStateOf(kelasOptions[0]) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getMahasiswa(id) ?: return@LaunchedEffect
        nim = data.nim
        nama = data.nama
        kelas = data.kelas
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(Res.string.tambah_mahasiswa))
                    else
                        Text(text = stringResource(Res.string.edit_mahasiswa))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || nim == 0L) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = invalidString,
                                    duration = SnackbarDuration.Short
                                )
                            }
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(nama, nim, kelas)
                        } else {
                            viewModel.update(id, nama, nim, kelas)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(Res.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { innerPadding ->
        FormMahasiswa(
            nim = nim,
            onTitleChange = { nama = it },
            nama = nama,
            onDescChange = { nim = it.toLongOrNull() ?: 0 },
            kelasOptions = kelasOptions,
            kelas = kelas,
            onKelasChange = { kelas = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FormMahasiswa(
    nim: Long?,
    onTitleChange: (String) -> Unit,
    nama: String,
    onDescChange: (String) -> Unit,
    kelasOptions: List<String>,
    kelas: String,
    onKelasChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = onTitleChange,
            label = { Text(text = stringResource(Res.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = if (nim == 0L) "" else nim.toString(),
            onValueChange = onDescChange,
            label = { Text(text = stringResource(Res.string.nim)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
        ) {
            kelasOptions.forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth().selectable(
                        selected = (option == kelas),
                        onClick = {
                            onKelasChange(option)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == kelas),
                        onClick = {
                            onKelasChange(option)
                        }
                    )
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(Res.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(Res.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreviewLight() {
    MaterialTheme {
        DetailScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        DetailScreen(rememberNavController())
    }
}