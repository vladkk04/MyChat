buildscript {
    dependencies {
        classpath(classPathsLibs.google.gms)
        classpath(classPathsLibs.androidx.navigation)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(pluginLibs.plugins.android.application) apply false
    alias(pluginLibs.plugins.android.kotlin) apply false
    alias(pluginLibs.plugins.google.services) apply false
    alias(pluginLibs.plugins.google.ksp) apply false
    alias(pluginLibs.plugins.dagger.hilt) apply false
}