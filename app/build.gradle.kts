import java.util.Properties

plugins {
    alias(pluginLibs.plugins.android.application)
    alias(pluginLibs.plugins.android.kotlin)
    alias(pluginLibs.plugins.google.services)
    alias(pluginLibs.plugins.google.ksp)
    alias(pluginLibs.plugins.dagger.hilt)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "com.arkul.mychat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arkul.mychat"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "AUTH_WEB_CLIENT_ID_GOOGLE", "\"${properties.getProperty("AUTH_WEB_CLIENT_ID_GOOGLE")}\"")
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

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    hilt {
        enableAggregatingTask = false
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.android.material)

    implementation(libs.androidx.webkit)

    implementation(platform(libs.firebase.boom))
    implementation(libs.firebase.auth)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.services.auth)
    implementation(libs.android.libraries.identity)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)

    implementation(testLibs.junit)
    implementation(testLibs.androidx.test.ext)
    implementation(testLibs.androidx.test.espresso)

}