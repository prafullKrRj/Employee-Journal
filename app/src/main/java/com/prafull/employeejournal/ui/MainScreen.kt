package com.prafull.employeejournal.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.TextField
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.filled.Info
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ElevatedButton
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prafull.employeejournal.Resource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Employee Journal",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add employee")
            }
        }
    ) { innerPadding ->
        Column (
            Modifier.fillMaxSize()
        ){
            when (state) {
                is Resource.Error -> {
                    Button(
                        onClick = {
                            viewModel.getAllEmployees()
                        }
                    ) {
                        Log.d("error", state.message.toString())
                        Text(text = state.message.toString())
                    }
                }
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
                is Resource.Success -> {
                    LazyColumn(contentPadding = innerPadding) {
                        state.data?.forEach { employee ->
                            item {
                                Card (
                                    Modifier
                                        .fillParentMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ){
                                    Row(modifier = Modifier.fillParentMaxWidth()) {
                                        Column(
                                            modifier = Modifier
                                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                                .weight(.85f)
                                        ) {
                                            Text(text = employee.name)
                                            Text(text = employee.email)
                                            Text(text = employee.location)
                                        }
                                        IconButton(onClick = {
                                            viewModel.delete(employee);
                                        }, modifier = Modifier.weight(.15f)) {
                                            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    if (openDialog) {
        var name by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var location by remember {
            mutableStateOf("")
        }
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text(text = "Add")
            },
            text = {
               Column(
                   verticalArrangement = Arrangement.spacedBy(8.dp)
               ) {
                   TextField(
                       value = name,
                       onValueChange = { name = it },
                       label = {
                           Text("Name")
                       }
                   )
                   TextField(
                       value = email,
                       onValueChange = { email = it },
                       label = {
                           Text("Email")
                       },
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Email
                       )
                   )
                   TextField(
                       value = location,
                       onValueChange = { location = it },
                       label = {
                           Text("Location")
                       }
                   )
               }
            },
            confirmButton = {
                TextButton(
                    enabled = name.isNotBlank() && email.isNotBlank() && location.isNotBlank(),
                    onClick = {
                        openDialog = false
                        viewModel.saveEmployee(name, email, location)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}
