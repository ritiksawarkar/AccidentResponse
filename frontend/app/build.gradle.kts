import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {

    namespace = "com.ritik.accidentresponse"

    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    // -----------------------------------------
    // Read local.properties
    // -----------------------------------------
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { input ->
            localProperties.load(input)
        }
    }

    defaultConfig {
        applicationId = "com.ritik.accidentresponse"

        minSdk = 24
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

        // -----------------------------------------
        // Supabase
        // -----------------------------------------
        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"${localProperties.getProperty("SUPABASE_URL", "")}\""
        )

        buildConfigField(
            "String",
            "SUPABASE_PUBLISHABLE_KEY",
            "\"${localProperties.getProperty("SUPABASE_PUBLISHABLE_KEY", "")}\""
        )
    }

    // -----------------------------------------
    // BuildConfig
    // -----------------------------------------
    buildFeatures {
        buildConfig = true
    }

    // -----------------------------------------
    // Release
    // -----------------------------------------
    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    // -----------------------------------------
    // Java
    // -----------------------------------------
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}