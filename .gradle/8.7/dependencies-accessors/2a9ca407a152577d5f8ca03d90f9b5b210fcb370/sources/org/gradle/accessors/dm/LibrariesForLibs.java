package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AndroidLibraryAccessors laccForAndroidLibraryAccessors = new AndroidLibraryAccessors(owner);
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final ColorLibraryAccessors laccForColorLibraryAccessors = new ColorLibraryAccessors(owner);
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final CropLibraryAccessors laccForCropLibraryAccessors = new CropLibraryAccessors(owner);
    private final DaggerLibraryAccessors laccForDaggerLibraryAccessors = new DaggerLibraryAccessors(owner);
    private final FirebaseLibraryAccessors laccForFirebaseLibraryAccessors = new FirebaseLibraryAccessors(owner);
    private final YalantisLibraryAccessors laccForYalantisLibraryAccessors = new YalantisLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>glide</b> with <b>com.github.bumptech.glide:glide</b> coordinates and
     * with version <b>4.16.0</b>
     * <p>
     * This dependency was declared in settings file 'settings.gradle.kts'
     */
    public Provider<MinimalExternalModuleDependency> getGlide() {
        return create("glide");
    }

    /**
     * Group of libraries at <b>android</b>
     */
    public AndroidLibraryAccessors getAndroid() {
        return laccForAndroidLibraryAccessors;
    }

    /**
     * Group of libraries at <b>androidx</b>
     */
    public AndroidxLibraryAccessors getAndroidx() {
        return laccForAndroidxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>color</b>
     */
    public ColorLibraryAccessors getColor() {
        return laccForColorLibraryAccessors;
    }

    /**
     * Group of libraries at <b>com</b>
     */
    public ComLibraryAccessors getCom() {
        return laccForComLibraryAccessors;
    }

    /**
     * Group of libraries at <b>crop</b>
     */
    public CropLibraryAccessors getCrop() {
        return laccForCropLibraryAccessors;
    }

    /**
     * Group of libraries at <b>dagger</b>
     */
    public DaggerLibraryAccessors getDagger() {
        return laccForDaggerLibraryAccessors;
    }

    /**
     * Group of libraries at <b>firebase</b>
     */
    public FirebaseLibraryAccessors getFirebase() {
        return laccForFirebaseLibraryAccessors;
    }

    /**
     * Group of libraries at <b>yalantis</b>
     */
    public YalantisLibraryAccessors getYalantis() {
        return laccForYalantisLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class AndroidLibraryAccessors extends SubDependencyFactory {
        private final AndroidLibrariesLibraryAccessors laccForAndroidLibrariesLibraryAccessors = new AndroidLibrariesLibraryAccessors(owner);

        public AndroidLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>material</b> with <b>com.google.android.material:material</b> coordinates and
         * with version <b>1.12.0</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getMaterial() {
            return create("android.material");
        }

        /**
         * Group of libraries at <b>android.libraries</b>
         */
        public AndroidLibrariesLibraryAccessors getLibraries() {
            return laccForAndroidLibrariesLibraryAccessors;
        }

    }

    public static class AndroidLibrariesLibraryAccessors extends SubDependencyFactory {

        public AndroidLibrariesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>identity</b> with <b>com.google.android.libraries.identity.googleid:googleid</b> coordinates and
         * with version <b>1.1.0</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getIdentity() {
            return create("android.libraries.identity");
        }

    }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxActivityLibraryAccessors laccForAndroidxActivityLibraryAccessors = new AndroidxActivityLibraryAccessors(owner);
        private final AndroidxCameraLibraryAccessors laccForAndroidxCameraLibraryAccessors = new AndroidxCameraLibraryAccessors(owner);
        private final AndroidxCredentialsLibraryAccessors laccForAndroidxCredentialsLibraryAccessors = new AndroidxCredentialsLibraryAccessors(owner);
        private final AndroidxDatastoreLibraryAccessors laccForAndroidxDatastoreLibraryAccessors = new AndroidxDatastoreLibraryAccessors(owner);
        private final AndroidxFragmentLibraryAccessors laccForAndroidxFragmentLibraryAccessors = new AndroidxFragmentLibraryAccessors(owner);
        private final AndroidxNavigationLibraryAccessors laccForAndroidxNavigationLibraryAccessors = new AndroidxNavigationLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>appcompat</b> with <b>androidx.appcompat:appcompat</b> coordinates and
         * with version <b>1.6.1</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getAppcompat() {
            return create("androidx.appcompat");
        }

        /**
         * Dependency provider for <b>constraintlayout</b> with <b>androidx.constraintlayout:constraintlayout</b> coordinates and
         * with version <b>2.1.4</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getConstraintlayout() {
            return create("androidx.constraintlayout");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.core:core-ktx</b> coordinates and
         * with version <b>1.12.0</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.ktx");
        }

        /**
         * Group of libraries at <b>androidx.activity</b>
         */
        public AndroidxActivityLibraryAccessors getActivity() {
            return laccForAndroidxActivityLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.camera</b>
         */
        public AndroidxCameraLibraryAccessors getCamera() {
            return laccForAndroidxCameraLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.credentials</b>
         */
        public AndroidxCredentialsLibraryAccessors getCredentials() {
            return laccForAndroidxCredentialsLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.datastore</b>
         */
        public AndroidxDatastoreLibraryAccessors getDatastore() {
            return laccForAndroidxDatastoreLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.fragment</b>
         */
        public AndroidxFragmentLibraryAccessors getFragment() {
            return laccForAndroidxFragmentLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.navigation</b>
         */
        public AndroidxNavigationLibraryAccessors getNavigation() {
            return laccForAndroidxNavigationLibraryAccessors;
        }

    }

    public static class AndroidxActivityLibraryAccessors extends SubDependencyFactory {

        public AndroidxActivityLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.activity:activity-ktx</b> coordinates and
         * with version <b>1.8.2</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.activity.ktx");
        }

    }

    public static class AndroidxCameraLibraryAccessors extends SubDependencyFactory {

        public AndroidxCameraLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>camera2</b> with <b>androidx.camera:camera-camera2</b> coordinates and
         * with version <b>1.4.0-beta02</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getCamera2() {
            return create("androidx.camera.camera2");
        }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.camera:camera-core</b> coordinates and
         * with version <b>1.4.0-beta02</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("androidx.camera.core");
        }

        /**
         * Dependency provider for <b>extensions</b> with <b>androidx.camera:camera-extensions</b> coordinates and
         * with version <b>1.4.0-beta02</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getExtensions() {
            return create("androidx.camera.extensions");
        }

        /**
         * Dependency provider for <b>lifecycle</b> with <b>androidx.camera:camera-lifecycle</b> coordinates and
         * with version <b>1.4.0-beta02</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getLifecycle() {
            return create("androidx.camera.lifecycle");
        }

        /**
         * Dependency provider for <b>mlkit</b> with <b>androidx.camera:camera-mlkit-vision</b> coordinates and
         * with version <b>1.4.0-beta02</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getMlkit() {
            return create("androidx.camera.mlkit");
        }

        /**
         * Dependency provider for <b>view</b> with <b>androidx.camera:camera-view</b> coordinates and
         * with version <b>1.4.0-beta02</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getView() {
            return create("androidx.camera.view");
        }

    }

    public static class AndroidxCredentialsLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final AndroidxCredentialsServicesLibraryAccessors laccForAndroidxCredentialsServicesLibraryAccessors = new AndroidxCredentialsServicesLibraryAccessors(owner);

        public AndroidxCredentialsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>credentials</b> with <b>androidx.credentials:credentials</b> coordinates and
         * with version <b>1.3.0-alpha01</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("androidx.credentials");
        }

        /**
         * Group of libraries at <b>androidx.credentials.services</b>
         */
        public AndroidxCredentialsServicesLibraryAccessors getServices() {
            return laccForAndroidxCredentialsServicesLibraryAccessors;
        }

    }

    public static class AndroidxCredentialsServicesLibraryAccessors extends SubDependencyFactory {

        public AndroidxCredentialsServicesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>auth</b> with <b>androidx.credentials:credentials-play-services-auth</b> coordinates and
         * with version <b>1.3.0-alpha01</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getAuth() {
            return create("androidx.credentials.services.auth");
        }

    }

    public static class AndroidxDatastoreLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxDatastoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>datastore</b> with <b>androidx.datastore:datastore</b> coordinates and
         * with version <b>1.0.0</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("androidx.datastore");
        }

        /**
         * Dependency provider for <b>preferences</b> with <b>androidx.datastore:datastore-preferences</b> coordinates and
         * with version <b>1.0.0</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getPreferences() {
            return create("androidx.datastore.preferences");
        }

    }

    public static class AndroidxFragmentLibraryAccessors extends SubDependencyFactory {

        public AndroidxFragmentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.fragment:fragment-ktx</b> coordinates and
         * with version <b>1.6.2</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.fragment.ktx");
        }

    }

    public static class AndroidxNavigationLibraryAccessors extends SubDependencyFactory {

        public AndroidxNavigationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>fragment</b> with <b>androidx.navigation:navigation-fragment-ktx</b> coordinates and
         * with version <b>2.7.7</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getFragment() {
            return create("androidx.navigation.fragment");
        }

        /**
         * Dependency provider for <b>ui</b> with <b>androidx.navigation:navigation-ui-ktx</b> coordinates and
         * with version <b>2.7.7</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getUi() {
            return create("androidx.navigation.ui");
        }

    }

    public static class ColorLibraryAccessors extends SubDependencyFactory {

        public ColorLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>picker</b> with <b>com.github.madrapps:pikolo</b> coordinates and
         * with version <b>2.0.2</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getPicker() {
            return create("color.picker");
        }

    }

    public static class ComLibraryAccessors extends SubDependencyFactory {

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>vanniktech</b> with <b>com.vanniktech:emoji-ios</b> coordinates and
         * with version <b>0.21.0</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getVanniktech() {
            return create("com.vanniktech");
        }

    }

    public static class CropLibraryAccessors extends SubDependencyFactory {

        public CropLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>image</b> with <b>com.github.yalantis:ucrop</b> coordinates and
         * with version <b>2.2.8</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getImage() {
            return create("crop.image");
        }

    }

    public static class DaggerLibraryAccessors extends SubDependencyFactory {
        private final DaggerHiltLibraryAccessors laccForDaggerHiltLibraryAccessors = new DaggerHiltLibraryAccessors(owner);

        public DaggerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>dagger.hilt</b>
         */
        public DaggerHiltLibraryAccessors getHilt() {
            return laccForDaggerHiltLibraryAccessors;
        }

    }

    public static class DaggerHiltLibraryAccessors extends SubDependencyFactory {

        public DaggerHiltLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>com.google.dagger:hilt-android</b> coordinates and
         * with version <b>2.50</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("dagger.hilt.android");
        }

        /**
         * Dependency provider for <b>compiler</b> with <b>com.google.dagger:hilt-compiler</b> coordinates and
         * with version <b>2.50</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("dagger.hilt.compiler");
        }

    }

    public static class FirebaseLibraryAccessors extends SubDependencyFactory {

        public FirebaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>auth</b> with <b>com.google.firebase:firebase-auth</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getAuth() {
            return create("firebase.auth");
        }

        /**
         * Dependency provider for <b>boom</b> with <b>com.google.firebase:firebase-bom</b> coordinates and
         * with version <b>32.7.2</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getBoom() {
            return create("firebase.boom");
        }

        /**
         * Dependency provider for <b>firestore</b> with <b>com.google.firebase:firebase-firestore</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getFirestore() {
            return create("firebase.firestore");
        }

    }

    public static class YalantisLibraryAccessors extends SubDependencyFactory {

        public YalantisLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ucrop</b> with <b>com.github.yalantis:ucrop</b> coordinates and
         * with version <b>2.2.8</b>
         * <p>
         * This dependency was declared in settings file 'settings.gradle.kts'
         */
        public Provider<MinimalExternalModuleDependency> getUcrop() {
            return create("yalantis.ucrop");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
