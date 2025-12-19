package com.mugo.glossarist

import io.github.lazarusmugo.glossarist.localization.annotations.ExperimentalGlossaristApi
import io.github.lazarusmugo.glossarist.localization.extenstion.stringResource
import io.github.lazarusmugo.glossarist.localization.locale.LocaleCode

data class AppStrings(
    val welcome: String,
    val test: String,
)

val appStringsEn = AppStrings(welcome = "Welcome", test = "Try")

val appStringsSw = AppStrings(welcome = "Karibu", test = "Jaribu")

@OptIn(ExperimentalGlossaristApi::class)
val appStrings =
    stringResource(
        key = "app.strings",
        translations =
            mapOf(LocaleCode.Standard.EN to appStringsEn, LocaleCode.Standard.SW to appStringsSw),
    )
