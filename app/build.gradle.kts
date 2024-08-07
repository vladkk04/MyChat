import java.util.Properties

plugins {
    alias(pluginLibs.plugins.android.application)
    alias(pluginLibs.plugins.android.kotlin)
    alias(pluginLibs.plugins.google.services)
    alias(pluginLibs.plugins.google.ksp)
    alias(pluginLibs.plugins.dagger.hilt)
    id("androidx.navigation.safeargs.kotlin")
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

    //Android Basic
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.android.material)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)

    //Navigation Components
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    //Firebase
    implementation(platform(libs.firebase.boom))
    implementation(libs.firebase.auth)

    //Google Auth Credentials
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.services.auth)
    implementation(libs.android.libraries.identity)

    //DataStore
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)

    //Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    //Color Picker
    implementation(libs.color.picker)

    //uCrop
    implementation(libs.yalantis.ucrop)

    //Emoji Picker
    implementation(libs.com.vanniktech)

    //Glide
    implementation(libs.glide)

    //Camera X
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.mlkit)
    implementation(libs.androidx.camera.extensions)

    //Tests
    implementation(testLibs.junit)
    implementation(testLibs.androidx.test.ext)
    implementation(testLibs.androidx.test.espresso)

}