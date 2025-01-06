import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Locale

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "nativeApp"
            isStatic = true
            binaryOption("bundleId", "com.muiltplatform.project.kmm")
        }
        iosTarget.compilations {
            val main by getting {
                cinterops {
                    create("nativeApp") {
                        header(file("src/nativeMain/cpp/main.hpp"))
                        defFile("src/nativeInterop/cinterop/native.def")
                    }
                }
            }
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
    }

    task("testClasses")
}

android {
    namespace = "com.muiltplatform.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.muiltplatform.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
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
    buildFeatures {
        compose = true
    }
    externalNativeBuild {
        cmake {
            path = file("src/nativeMain/cpp/CMakeLists.txt")
        }
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "8.7"
    distributionType = Wrapper.DistributionType.BIN
}

val isMacOs = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("mac")

if (isMacOs) {
    tasks.register<Exec>("buildNativeLib") {
        commandLine("sh", "-c", "cd src/nativeMain/cpp && ./build_nativeApp.sh")
    }

    tasks["cinteropNativeAppIosX64"].dependsOn("buildNativeLib")
    tasks["cinteropNativeAppIosArm64"].dependsOn("buildNativeLib")
    tasks["cinteropNativeAppIosSimulatorArm64"].dependsOn("buildNativeLib")
    tasks["compileKotlinIosArm64"].dependsOn("buildNativeLib")
    tasks["compileKotlinIosSimulatorArm64"].dependsOn("buildNativeLib")
    tasks["compileKotlinIosX64"].dependsOn("buildNativeLib")
}