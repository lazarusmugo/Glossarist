package io.github.lazarusmugo.glossarist.localization.extenstion

import io.github.lazarusmugo.glossarist.localization.annotations.ExperimentalGlossaristApi

/**
 * A typealias for a function that takes an integer `count` and returns a string.
 * This is used for handling pluralization in a type-safe way.
 */
@ExperimentalGlossaristApi
typealias PluralString = (count: Int) -> String
