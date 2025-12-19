# Glossarist - A Compose Multiplatform I18n Library - Quick Start Guide

> [!WARNING]
> **This library is currently in an experimental phase.**
> The API is subject to change, and it is not recommended for production use at this time.

## Overview

A simple, type-safe internationalization library for Kotlin Multiplatform projects. No XML files, no
complex setup—just clean Kotlin code.

## Key Features

1. **Unique String Keys** - No more collisions, just unique string identifiers for your resources.
2. **ISO 639-1 standard locale codes** - With support for custom locales
3. **Automatic validation** - Catches missing translations at initialization
4. **Clean API** - Minimal boilerplate, maximum clarity
5. **KMP compatible** - Works on JVM, iOS, Android, JS, Native

## Installation

```kotlin
// In your shared module's build.gradle.kts
dependencies {
    implementation("com.mugo.glossarist:glossarist:x.y.z")
}
```

## Quick Start

### 1. Initialize the SDK - N.B THIS IS ONLY REQUIRED FOR ACCESSING STRINGS FROM NON COMPOSE CONTEXT (E.G VIEWMODELS)

```kotlin
// In your App.kt or main function
fun initializeApp() {
    Strings.initialize(
        I18nConfig(
            localeProvider = PlatformLocaleProvider(),
            defaultLocale = LocaleCode.Standard.EN,
            supportedLocales = setOf(
                LocaleCode.Standard.EN,  // English
                LocaleCode.Standard.SW,  // Swahili
                LocaleCode.Standard.FR   // French
            ),
            enforceTranslations = true // Fail fast if translations missing
        )
    )
}
```

### 2. Define Your Translation Data Classes

```kotlin
@TranslationRequired
data class AuthStrings(
    val welcomeTitle: String,
    val loginButton: String,
    val signupButton: String
)
```

### 3. Define Translations

```kotlin
val authEnglish = AuthStrings(
    welcomeTitle = "Welcome Back",
    loginButton = "Log In",
    signupButton = "Create Account"
)

val authSwahili = AuthStrings(
    welcomeTitle = "Karibu Tena",
    loginButton = "Ingia",
    signupButton = "Fungua Akaunti"
)

val authFrench = AuthStrings(
    welcomeTitle = "Bon Retour",
    loginButton = "Se Connecter",
    signupButton = "Créer un Compte"
)
```

### 4. Register Your Strings with a Unique Key

```kotlin
val authStrings = stringResource(
    key = "auth_strings", // Use a unique string key
    translations = mapOf(
        LocaleCode.Standard.EN to authEnglish,
        LocaleCode.Standard.SW to authSwahili,
        LocaleCode.Standard.FR to authFrench
    )
)
```

### 5. Use in Your Composables

```kotlin
@Composable
fun LoginScreen() {
    val strings by authStrings

    Column {
        Text(strings.welcomeTitle)
        Button(onClick = {}) {
            Text(strings.loginButton)
        }
        OutlinedButton(onClick = {}) {
            Text(strings.signupButton)
        }
    }
}
```

### For usage in non compose context

```kotlin

class LoginViewModel : ViewModel() {
    val strings: AuthStrings = Strings.getNonCompose("auth_strings")

    init {
        println(strings.welcomeTitle)
    }
}

```

### Handling Plurals

The library supports pluralization through the `PluralString` typealias, which is a function that
takes a `count` and returns a string. This gives you full control over the pluralization logic.

First, define a `PluralString` in your data class:

```kotlin
data class NotificationStrings(
    val unreadMessages: PluralString
)
```

Then, provide the pluralization logic in your translations:

```kotlin
val notificationsEnglish = NotificationStrings(
    unreadMessages = { count ->
        when (count) {
            0 -> "You have no new messages"
            1 -> "You have 1 new message"
            else -> "You have $count new messages"
        }
    }
)

val notificationsSwahili = NotificationStrings(
    unreadMessages = { count ->
        when (count) {
            0 -> "Huna ujumbe mpya"
            1 -> "Una ujumbe 1 mpya"
            else -> "Una ujumbe $count mpya"
        }
    }
)
```

Finally, use it in your composable:

```kotlin
@Composable
fun NotificationBadge(count: Int) {
    val strings by notificationStrings

    Text(strings.unreadMessages(count))
}
```

## Switching Languages

```kotlin
// Switch to a specific language
Strings.setLocale(LocaleCode.Standard.SW)

// Or use string code
Strings.setLocale("sw")

// Reset to system default
Strings.resetToSystemLocale()

// Get current locale
val current = Strings.getCurrentLocale()
```

## Language Selector Example

```kotlin
@Composable
fun LanguageSelector() {
    Row {
        Button(onClick = { Strings.setLocale(LocaleCode.Standard.EN) }) {
            Text("English")
        }
        Button(onClick = { Strings.setLocale(LocaleCode.Standard.SW) }) {
            Text("Swahili")
        }
        Button(onClick = { Strings.setLocale(LocaleCode.Standard.FR) }) {
            Text("Français")
        }
    }
}
```

## What Gets Validated

The library automatically validates:

**All required locales have translations**

```kotlin
// This FAILS - missing French translation
val strings = stringResource(
    key = "auth_strings",
    translations = mapOf(
        LocaleCode.Standard.EN to authEnglish,
        LocaleCode.Standard.SW to authSwahili
        // Missing FR - error at registration!
    )
)
```

**No null translations**

```kotlin
// This FAILS - null value
val strings = stringResource(
    key = "auth_strings",
    translations = mapOf(
        LocaleCode.Standard.EN to authEnglish,
        LocaleCode.Standard.SW to null  // Null!
    )
)
```

## Custom Locales (Slang, Dialects)

```kotlin
// Define custom locale for Kenyan English/Sheng
Strings.initialize(
    I18nConfig(
        supportedLocales = setOf(
            LocaleCode.Standard.EN,
            LocaleCode.Custom("en-ke")  // Custom locale!
        ),
        // ...
    )
)

val authKenyan = AuthStrings(
    welcomeTitle = "Karibu Nyumbani",
    loginButton = "Ingia",
    signupButton = "Jisajili"
)

val authStrings = stringResource(
    key = "auth_strings_kenyan",
    translations = mapOf(
        LocaleCode.Standard.EN to authEnglish,
        LocaleCode.Custom("en-ke") to authKenyan
    )
)
```

## Supported Languages (ISO 639-1) - To be Expanded

Common languages available in `LocaleCode.Standard`:

- `EN` - English
- `SW` - Swahili
- `FR` - French

For languages not in the standard set, use custom locales:

```kotlin
LocaleCode.Custom("my-language-code")
```
