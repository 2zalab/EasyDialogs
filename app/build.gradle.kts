plugins {
    //alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("com.android.library")
    id ("maven-publish")
}

android {
    namespace = "com.touzalab.easydialogs"
    compileSdk = 35

    defaultConfig {
        //applicationId = "com.touzalab.easydialogs"
        minSdk = 24
        targetSdk = 35
        //versionCode = 1
        //versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Configuration de publication pour une bibliothèque
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.2zalab"  // Votre groupId
                artifactId = "easydialogs"     // Votre artifactId
                version = "1.0.1"              // Votre version
                from(components["release"])
            }
        }
    }
}