import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    id("com.android.library")

    alias(libs.plugins.ksp)

    // REMOVE THIS FOR NOW
    // alias(libs.plugins.room)
    // Kotlin serialization plugin for type safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.21"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {

        androidMain.dependencies {

            implementation(libs.compose.uiToolingPreview)

            implementation(libs.firebase.firestore)

            implementation(libs.hilt.android)

            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

            implementation(libs.glide)

            implementation(libs.androidx.lifecycle.viewmodel.ktx)

            implementation(libs.kotlinx.coroutines.android)
        }

        commonMain.dependencies {



            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)



            implementation(libs.compose.components.resources)

            implementation(libs.compose.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
//            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8")
            val nav_version = "2.9.8"

            // Jetpack Compose integration
            implementation("androidx.navigation:navigation-compose:$nav_version")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.example.project.shared"

    compileSdk =
        libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk =
            libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    add(
        "kspAndroid",
        libs.androidx.room.compiler
    )
}