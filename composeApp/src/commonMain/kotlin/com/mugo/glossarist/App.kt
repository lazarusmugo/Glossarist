package com.mugo.glossarist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.lazarusmugo.glossarist.localization.annotations.ExperimentalGlossaristApi
import io.github.lazarusmugo.glossarist.localization.manager.Strings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlossaristApi::class)
@Composable
fun App() {
    MaterialTheme {
        var showLanguageSheet by remember { mutableStateOf(false) }

        val strings by appStrings

        Column(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(strings.welcome, style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(24.dp))

            Button(onClick = { showLanguageSheet = true }) { Text("Choose Language") }
        }

        if (showLanguageSheet) {
            ModalBottomSheet(onDismissRequest = { showLanguageSheet = false }) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("Select Language", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))

                    val languages = listOf("en" to "English", "sw" to "Swahili")

                    languages.forEach { (code, label) ->
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Strings.setLocale(code)
                                        showLanguageSheet = false
                                    }.padding(12.dp),
                        )
                    }
                }
            }
        }
    }
}
