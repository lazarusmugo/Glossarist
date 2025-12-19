package com.mugo.glossarist.localization.example

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mugo.glossarist.localization.manager.Strings

/**
 * Demonstrates the basic usage of a simple `String` resource in a Composable.
 */
@Composable
fun SimpleExampleUsage() {
    val strings by simpleExampleStrings

    Column { Text(strings) }
}

/**
 * Demonstrates the usage of a data class-based resource in a Composable.
 */
@Composable
fun AdvancedExampleUsage() {
    val strings by advancedExampleStrings

    Column { Text(strings.hello) }
}

/**
 * Demonstrates the usage of the `PluralString` typealias for handling pluralization.
 */
@Composable
fun PluralExampleUsage() {
    val strings by advancedExampleStrings

    Column {
        Text(strings.plural(0))
        Text(strings.plural(1))
        Text(strings.plural(5))
    }
}

/**
 * Demonstrates how to retrieve a translation outside of a Composable context, such as in a ViewModel.
 */
fun AdvancedExampleUsageForNonComposeContext() {
    val strings: AdvancedExampleStrings = Strings.getNonCompose("advanced.string.example")

    println(strings.hello)
}
