
package com.amin.composeandmaps.buildsrc

object Versions {
    const val ktLint = "0.43.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.3"
    const val ktLint = "com.pinterest:ktlint:${Versions.ktLint}"
    const val secrets = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1"

    object GoogleMaps {
        const val maps = "com.google.android.libraries.maps:maps:3.1.0-beta"
        const val mapsKtx = "com.google.maps.android:maps-v3-ktx:2.2.0"
    }

    object Volley {
        const val volley = "com.android.volley:volley:1.2.0"
    }

    object Accompanist {
        const val version = "0.22.0-rc"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val permissions = "com.google.accompanist:accompanist-permissions:$version"
    }

    object Retrofit {
        const val version = "2.9.0"
        const val gson ="com.squareup.retrofit2:converter-gson:$version"
        const val retrofit ="com.squareup.retrofit2:converter-gson:$version"
        const val loggingInterceptor ="com.squareup.okhttp3:logging-interceptor:4.9.1"
    }

    object Kotlin {
        private const val version = "1.6.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"

        object Coroutines {
            private const val version = "1.5.2"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidX {
        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val fragment = "androidx.fragment:fragment:1.4.0"

        object Compose {
            const val snapshot = ""
            const val version = "1.1.0-rc01"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val material = "androidx.compose.material:material:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val navigation = "androidx.navigation:navigation-compose:2.4.0-rc01"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
        }

        object Lifecycle {
            private const val version = "2.4.0"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }
    object UnitTest {
        const val core = "androidx.test:core:1.4.0"
        const val junit = "junit:junit:4.13"
        const val hamcrest = "org.hamcrest:hamcrest-all:1.3"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val robolectric = "org.robolectric:robolectric:4.3.1"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1"
        const val truth = "com.google.truth:truth:1.0.1"
        const val mockito = "org.mockito:mockito-core:2.21.0"
    }

    object InstrumentTest {
        const val junit = "junit:junit:4.13"
        const val mockitoDexMaker = "com.linkedin.dexmaker:dexmaker-mockito:2.12.1"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val truth = "com.google.truth:truth:1.0.1"
        const val junitExt = "androidx.test.ext:junit:1.1.1"
        const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
        const val mockito = "org.mockito:mockito-core:2.21.0"
        const val rules = "androidx.test:rules:1.4.0"
    }

    object Orchestrator {
        const val version = "1.1.0"
        const val runner = "androidx.test:runner:$version"
        const val orchestrator = "androidx.test:orchestrator:$version"
    }

    object Hilt {
        private const val version = "2.39"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val androidxCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha02"
        const val testing = "com.google.dagger:hilt-android-testing:$version"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"

    }

    object JUnit {
        private const val version = "4.13"
    }


    object Timber {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

    object ImageLoading {
        const val landscapist = "com.github.skydoves:landscapist-glide:1.4.4"
    }
}

object Urls {
    const val mavenCentralSnapshotRepo = "https://oss.sonatype.org/content/repositories/snapshots/"
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
        "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
}
