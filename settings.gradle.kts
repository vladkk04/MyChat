pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("pluginLibs") {
            val androidGradlePluginVersion = "8.1.1"
            val androidKotlinPluginVersion = "1.9.22"
            val googleKspPluginVersion = "1.9.22-1.0.17"
            val googleServiceVersion = "4.4.1"
            val daggerHiltVersion = "2.50"

            plugin("android-application", "com.android.application").version(androidGradlePluginVersion)
            plugin("android-kotlin", "org.jetbrains.kotlin.android").version(androidKotlinPluginVersion)
            plugin("google-services", "com.google.gms.google-services").version(googleServiceVersion)

            plugin("dagger-hilt", "com.google.dagger.hilt.android").version(daggerHiltVersion)
            
            plugin("google-ksp", "com.google.devtools.ksp").version(googleKspPluginVersion)
        }

        create("libs") {
            val androidxCoreVersion = "1.12.0"
            val androidxAppcompatVersion = "1.6.1"
            val googleAndroidMaterialVersion = "1.10.0"
            val androidxConstraintlayoutVersion = "2.1.4"
            val androidxWebkitVersion = "1.8.0"
            val fragmentKtxVersion = "1.6.2"
            val activityKtxVersion = "1.8.2"
            val daggerHiltVersion = "2.50"
            val firebaseBoomVersion = "32.7.2"
            val googleCredentialsVersion = "1.3.0-alpha01"
            val googleIdentityID = "1.1.0"
            val glideVersion = "4.16.0"
            val navigationComponentsVersion = "2.7.7"

            //Navigation Components
            library("androidx.navigation-fragment", "androidx.navigation", "navigation-fragment-ktx").version(navigationComponentsVersion)
            library("androidx.navigation-ui", "androidx.navigation", "navigation-ui-ktx").version(navigationComponentsVersion)

            //Glide
            library("glide", "com.github.bumptech.glide", "glide").version(glideVersion)

            //Google Auth Credentials
            library("androidx.credentials", "androidx.credentials", "credentials").version(googleCredentialsVersion)
            library("androidx.credentials.services.auth", "androidx.credentials", "credentials-play-services-auth").version(googleCredentialsVersion)
            library("android.libraries.identity", "com.google.android.libraries.identity.googleid", "googleid").version(googleIdentityID)

            //Firebase
            library("firebase-boom", "com.google.firebase", "firebase-bom").version(firebaseBoomVersion)
            library("firebase-auth", "com.google.firebase", "firebase-auth").withoutVersion()

            //Hilt
            library("dagger-hilt-android", "com.google.dagger", "hilt-android").version(daggerHiltVersion)
            library("dagger-hilt-compiler", "com.google.dagger", "hilt-compiler").version(daggerHiltVersion)

            //WebView
            library("androidx.webkit", "androidx.webkit", "webkit").version(androidxWebkitVersion)

            //Android Basic
            library("androidx-ktx", "androidx.core", "core-ktx").version(androidxCoreVersion)
            library("androidx-appcompat", "androidx.appcompat", "appcompat").version(androidxAppcompatVersion)
            library("androidx-constraintlayout", "androidx.constraintlayout", "constraintlayout").version(androidxConstraintlayoutVersion)
            library("android-material", "com.google.android.material", "material").version(googleAndroidMaterialVersion)
            library("androidx-fragment-ktx", "androidx.fragment", "fragment-ktx").version(fragmentKtxVersion)
            library("androidx-activity-ktx", "androidx.activity", "activity-ktx").version(activityKtxVersion)
        }

        create("testLibs") {
            val junitVersion = "4.13.2"
            val androidxTestExtVersion = "1.1.5"
            val androidxTestEspressoVersion = "3.5.1"

            library("junit", "junit", "junit").version(junitVersion)
            library("androidx-test-ext", "androidx.test.ext", "junit").version(androidxTestExtVersion)
            library("androidx-test-espresso", "androidx.test.espresso", "espresso-core").version(androidxTestEspressoVersion)
        }
    }
}

rootProject.name = "MyChat"
include(":app")
 