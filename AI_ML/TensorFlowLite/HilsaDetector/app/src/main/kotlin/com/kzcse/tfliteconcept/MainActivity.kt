package com.kzcse.tfliteconcept

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kzcse.tfliteconcept.feature._core.presentation.AppTheme
import com.kzcse.tfliteconcept.feature._core.presentation.GlobalMessenger
import com.kzcse.tfliteconcept.feature._navigation.NavigationRootX

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val hostState = remember { SnackbarHostState() }
            LaunchedEffect(Unit) {
                GlobalMessenger.messageToUI.collect { msg ->
                    if (msg != null) {
                        hostState.showSnackbar(msg)
                    }

                }

            }
            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = hostState)
                    }) { innerPadding ->
                    Box(
                        Modifier.fillMaxWidth().padding(innerPadding),
                        contentAlignment = Alignment.TopCenter
                    )
                    {
                        NavigationRootX()

                    }

                }
            }
        }
    }
}

