package com.prafull.employeejournal.ui

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prafull.employeejournal.Resource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()
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
                scope.launch { sheetState.expand() }
            }) {
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


}
