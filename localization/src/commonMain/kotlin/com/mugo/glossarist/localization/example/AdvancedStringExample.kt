@file:GenerateStrings

package com.mugo.glossarist.localization.example

import com.mugo.glossarist.localization.annotations.GenerateStrings
import com.mugo.glossarist.localization.extenstion.PluralString
import com.mugo.glossarist.localization.extenstion.stringResource
import com.mugo.glossarist.localization.locale.LocaleCode
import kotlinx.serialization.Serializable

/**
 * This file provides an advanced example of using Glossarist.
 *
 * It demonstrates:
 * - Using a data class (`AdvancedExampleStrings`) to structure related strings.
 * - Implementing pluralization logic with the [PluralString] typealias.
 * - Defining translations for standard and custom locales (`en-slang`).
 */

/**
 * A data class representing a set of related strings for an advanced use case.
 *
 * Using a data class provides type safety and organization for your translations.
 * The `@Serializable` annotation is not required by Glossarist itself but can be useful
 * if you plan to serialize these objects.
 *
 * @property hello A simple greeting string.
 * @property welcome A welcome message.
 * @property subtitle A descriptive subtitle.
 * @property plural A [PluralString] function to handle pluralization for messages.
 */
@Serializable
data class AdvancedExampleStrings(
    val hello: String,
    val welcome: String,
    val subtitle: String,
    val plural: PluralString,
)

/** English translations for the advanced example. */
private val advancedExampleStringsEnglish =
    AdvancedExampleStrings(
        hello = "Hello there",
        welcome = "Welcome",
        subtitle = "Let's get you verified",
        plural = { count ->
            when (count) {
                0 -> "You have no new messages"
                1 -> "You have 1 new message"
                else -> "You have $count new messages"
            }
        },
    )

/** Swahili translations for the advanced example. */
private val advancedExampleStringsSwahili =
    AdvancedExampleStrings(
        hello = "Habari yako",
        welcome = "Karibu",
        subtitle = "Hebu tukuhakikishe",
        plural = { count ->
            when (count) {
                0 -> "Huna ujumbe mpya"
                1 -> "Una ujumbe 1 mpya"
                else -> "Una ujumbe $count mpya"
            }
        },
    )

/** Sheng (Kenyan slang) translations for the advanced example, using a custom locale. */
val advancedExampleStringsSwahiliSlang =
    AdvancedExampleStrings(
        hello = "Niaje",
        welcome = "Karibu",
        subtitle = "Wacha tukuverify",
        plural = { count ->
            when (count) {
                0 -> "Huna message mpya"
                1 -> "Uko na message 1 mpya"
                else -> "Uko na message $count mpya"
            }
        },
    )

/**
 * The string resource for the advanced example.
 *
 * This resource bundles all the translations (English, Swahili, and Sheng) under a single key.
 */
val advancedExampleStrings =
    stringResource(
        key = "advanced.string.example",
        translations =
            mapOf(
                LocaleCode.Standard.EN to advancedExampleStringsEnglish,
                LocaleCode.Standard.SW to advancedExampleStringsSwahili,
                LocaleCode.Custom("en-slang") to advancedExampleStringsSwahiliSlang,
            ),
    )
