plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.arista1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.arista1"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        }

    }





    dependencies {
        dependencies {
            implementation(libs.material)
            implementation(libs.appcompat)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.database)
            implementation(libs.osmdroid.android.v6110)
            implementation(libs.osmdroid.android)
            implementation(libs.play.services.location.v2101)
            implementation(libs.cardview)
            implementation(libs.constraintlayout)
            implementation(libs.firebase.firestore)
            testImplementation(libs.junit)
            androidTestImplementation(libs.ext.junit)
            androidTestImplementation(libs.espresso.core)
            implementation(libs.navigation.fragment.ktx)
            implementation(libs.navigation.ui.ktx)
            implementation(libs.play.services.location)
            implementation(libs.core)
        }

    }
