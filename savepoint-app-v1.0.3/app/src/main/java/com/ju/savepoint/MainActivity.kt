package com.ju.savepoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ju.savepoint.ui.SavepointApp
import com.ju.savepoint.ui.theme.SavepointTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SavepointTheme {
                SavepointApp()
            }
        }
    }
}
