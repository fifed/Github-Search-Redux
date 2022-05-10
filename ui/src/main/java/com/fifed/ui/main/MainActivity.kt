package com.fifed.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fifed.presentation.connector.MainActivityConnector
import com.fifed.ui.ComposeApplication
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val connector: MainActivityConnector by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connector.onCreated(savedInstanceState != null)
        setContent {
            ComposeApplication()
        }
    }
}