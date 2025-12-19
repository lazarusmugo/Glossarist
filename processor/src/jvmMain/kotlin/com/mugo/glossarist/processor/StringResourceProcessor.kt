package com.mugo.glossarist.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier

/**
 * A KSP [SymbolProcessor] that discovers all string resources and generates a registry file.
 *
 * This processor searches for properties of type `StringsProperty` in files annotated with
 * `@GenerateStrings`. It then generates a `GeneratedStrings.kt` file containing an object
 * that provides a centralized `registerAll()` method to initialize all discovered string resources.
 *
 * @property logger A [KSPLogger] for logging processing events.
 * @property codeGenerator A [CodeGenerator] for creating the output file.
 */
class StringResourceProcessor(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    init {
        Napier.base(
            object : Antilog() {
                override fun performLog(
                    priority: LogLevel,
                    tag: String?,
                    throwable: Throwable?,
                    message: String?,
                ) {
                    val logMessage = "[${tag ?: "Processor"}] ${message.orEmpty()}"
                    val fullMessage =
                        if (throwable != null) "$logMessage${throwable.stackTraceToString()}" else logMessage

                    when (priority) {
                        LogLevel.VERBOSE, LogLevel.DEBUG, LogLevel.INFO -> logger.info(fullMessage)
                        LogLevel.WARNING -> logger.warn(fullMessage)
                        LogLevel.ERROR, LogLevel.ASSERT -> logger.error(fullMessage)
                    }
                }
            },
        )
    }

    /**
     * The main entry point for the symbol processor.
     *
     * This method is called by KSP to initiate the processing round. It finds all symbols annotated
     * with `@GenerateStrings`, extracts the `StringsProperty` declarations, and then triggers the
     * code generation process.
     *
     * @param resolver The [Resolver] instance provided by KSP, used to access symbols and types.
     * @return A list of symbols that could not be processed and should be deferred to the next round.
     *         This processor always returns an empty list as it processes all symbols in one go.
     */
    override fun process(resolver: Resolver): List<KSAnnotated> {
        Napier.i(tag = "StringResourceProcessor", message = "Starting processing...")

        val annotatedFiles =
            resolver.getSymbolsWithAnnotation("com.mugo.glossarist.localization.annotations.GenerateStrings")
        if (!annotatedFiles.iterator().hasNext()) {
            Napier.i(
                tag = "StringResourceProcessor",
                message = "No files annotated with @GenerateStrings. Skipping.",
            )
            return emptyList()
        }

        val stringResources = mutableListOf<ClassName>()

        annotatedFiles
            .filterIsInstance<KSFile>()
            .forEach { file ->
                Napier.i(
                    tag = "StringResourceProcessor",
                    message = "Processing file: ${file.filePath}",
                )
                file.declarations
                    .filterIsInstance<KSPropertyDeclaration>()
                    .forEach { prop ->
                        val resolvedType = prop.type.resolve()
                        if (resolvedType.declaration.qualifiedName?.asString() ==
                            "com.mugo.glossarist.localization.extension.StringsProperty"
                        ) {
                            val packageName = prop.packageName.asString()
                            val simpleName = prop.simpleName.asString()
                            Napier.i(
                                tag = "StringResourceProcessor",
                                message = "Found string resource: $packageName.$simpleName",
                            )
                            stringResources.add(ClassName(packageName, simpleName))
                        }
                    }
            }

        if (stringResources.isNotEmpty()) {
            generateStringsFile(stringResources)
        }

        return emptyList()
    }

    /**
     * Generates the `GeneratedStrings.kt` file using KotlinPoet.
     *
     * The generated file contains a `GeneratedStrings` object with a `registerAll` function.
     * This function references all the discovered string resource properties, which triggers
     * their registration and validation when called.
     *
     * @param resources A list of [ClassName]s representing the discovered string resource properties.
     */
    private fun generateStringsFile(resources: List<ClassName>) {
        val fileSpec =
            FileSpec
                .builder("com.mugo.glossarist.generated", "GeneratedStrings")
                .addType(
                    TypeSpec
                        .objectBuilder("GeneratedStrings")
                        .addFunction(
                            FunSpec
                                .builder("registerAll")
                                .apply {
                                    resources.forEach { resource ->
                                        addComment("Triggering registration for ${resource.simpleName}")
                                        addStatement("%T", resource)
                                    }
                                }.build(),
                        ).build(),
                ).build()

        try {
            fileSpec.writeTo(codeGenerator, Dependencies(aggregating = false))
            Napier.i(
                tag = "StringResourceProcessor",
                message = "Successfully generated GeneratedStrings.kt",
            )
        } catch (e: Exception) {
            Napier.e(
                tag = "StringResourceProcessor",
                message = "Error generating file",
                throwable = e,
            )
        }
    }
}

/**
 * A [SymbolProcessorProvider] for creating instances of [StringResourceProcessor].
 *
 * This provider is registered in `META-INF/services/com.google.devtools.ksp.processing.SymbolProcessorProvider`
 * and is used by KSP to instantiate the processor.
 */
class StringResourceProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        StringResourceProcessor(environment.logger, environment.codeGenerator)
}
