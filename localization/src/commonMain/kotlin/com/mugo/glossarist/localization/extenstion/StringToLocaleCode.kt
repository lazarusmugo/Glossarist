package com.mugo.glossarist.localization.extenstion

import com.mugo.glossarist.localization.locale.LocaleCode

/**
 * Converts a raw string into a [LocaleCode].
 *
 * This extension function first attempts to find a matching locale in the standard `LocaleCode.Standard` enum.
 * If no standard locale matches the string, it falls back to creating a `LocaleCode.Custom` instance.
 *
 * @return A [LocaleCode] that is either a `Standard` or a `Custom` locale.
 */
fun String.toLocaleCode(): LocaleCode = LocaleCode.Standard.entries.find { it.code == this } ?: LocaleCode.Custom(this)
