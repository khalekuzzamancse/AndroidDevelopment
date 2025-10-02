package com.uitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.uitest.feature._navigation.NavigationRoot
import com.uitest.feature.home.SimpleCalculatorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UITestSamplesTheme {
               // SimpleCalculatorScreen { }
                NavigationRoot()

            }
        }
    }
}

