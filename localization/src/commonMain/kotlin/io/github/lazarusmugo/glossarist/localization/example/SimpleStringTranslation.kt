@file:Suppress("ktlint:standard:filename")
@file:GenerateStrings

package io.github.lazarusmugo.glossarist.localization.example

import io.github.lazarusmugo.glossarist.localization.annotations.GenerateStrings
import io.github.lazarusmugo.glossarist.localization.extenstion.stringResource
import io.github.lazarusmugo.glossarist.localization.locale.LocaleCode

/**
 * This file provides a basic example of a simple string translation.
 *
 * It demonstrates how to define a localizable resource for a raw `String` type.
 */

private val simpleStringEnglish = "Welcome to Glossarist!"
private val simpleStringsSwahili = "Karibu Glossarist!"

/**
 * A simple string resource for a welcome message.
 *
 * This demonstrates the most basic usage of the `stringResource` delegate for a `String`.
 */
val simpleExampleStrings =
    stringResource(
        key = "simple.string.translation",
        translations =
            mapOf(
                LocaleCode.Standard.EN to simpleStringEnglish,
                LocaleCode.Standard.SW to simpleStringsSwahili,
            ),
    )
