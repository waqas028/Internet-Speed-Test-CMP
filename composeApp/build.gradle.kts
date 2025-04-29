import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinCocoapods)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    

    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            //ktor
            api(libs.ktor.client.core)
            implementation(libs.ktor.client.websockets)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.utils)

            implementation(libs.navigation.compose)
            implementation(libs.ksoup)
            implementation(libs.compottie)

            //coil
            implementation(libs.bundles.coil)

            //SqlDelight
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.extensions)

            //datetime
            implementation(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.android)

            //SqlDelight
            implementation(libs.sqldelight.android.driver)

            //Admob
            implementation(libs.play.services.ads)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

            //SqlDelight
            implementation(libs.sqldelight.native.driver)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    kotlin {

        cocoapods {
            version = "1.0"
            summary = "Compose Multiplatform Application"
            homepage = "https://your-homepage.com"
            ios.deploymentTarget = "14.1"
            podfile = project.file("../iosApp/Podfile")

            framework {
                baseName = "ComposeApp"
                isStatic = true
                // Add these:
                linkerOpts.add("-ObjC")
            }

            // Explicit AdMob pod configuration
            pod("Google-Mobile-Ads-SDK") {
                moduleName = "GoogleMobileAds"
                extraOpts += listOf("-compiler-option","-fmodules")
            }
        }
    }
}

android {
    namespace = "com.farimarwat.speedtest"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.farimarwat.speedtest"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sqldelight {
        databases{
            create("SpeedTestDatabase"){
                packageName.set("com.speedtest")
            }
        }
    }
}


dependencies {
    debugImplementation(compose.uiTooling)
}

