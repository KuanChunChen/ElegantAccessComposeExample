import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "elegant.access.compose.example"
    compileSdk = 34

    defaultConfig {
        applicationId = "elegant.access.compose.example"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("Boolean", "IS_USING_DEBUG_CONFIG", "true")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("Boolean", "IS_USING_DEBUG_CONFIG", "false")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    productFlavors.all {
        val channels = Json.decodeFromStream<Channels>(FileInputStream(file("build.config")))
        val appInfo = channels.channels["elegantAccessApp"]!!
        applicationId = appInfo.packageName
        versionCode = appInfo.versionCode
        versionName = appInfo.versionName
        buildConfigField("boolean", "IS_USING_DEBUG_CONFIG", "${appInfo.appConfig == "debug"}")
        buildOutputs.all {
            val oldName = (this as BaseVariantOutputImpl).outputFileName
            val newName = oldName.replace("app-", "ElegantAccessExample-")
            outputFileName = newName
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.navigation)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.compose)
    implementation(libs.squareup.retrofit.core)
    implementation(libs.squareup.retrofit.converter.kotlinx.serialization)
    implementation(libs.squareup.okhttp.logging)
    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.datastore.preferences)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlin.coroutines.test)
}