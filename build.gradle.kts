// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.spotless)
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    ratchetFrom("origin/main")

    format("misc") {
        target(".gitignore")

        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        ktlint()
    }

    kotlin {
        target("app/src/*/kotlin/**/*.kt", "app/src/*/java/**/*.kt")

        ktlint()
            .setEditorConfigPath("$projectDir/.editorconfig")
            .customRuleSets(
                listOf(
                    "io.nlopez.compose.rules:ktlint:0.4.27",
                ),
            )
    }
}
