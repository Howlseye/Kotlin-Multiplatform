import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    // Modul 12
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlinxSerialization)

    // Modul 14
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Modul 12
            implementation(libs.ktorfit.lib)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serialization.json)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)

            // Modul 14
            implementation(libs.kmpAuth.google)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.androidx.datastore.core)

            // Modul 15
            implementation(libs.imagePick.crop)
            implementation(libs.compose.material)
            implementation(libs.compose.icons.extended)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.nikola0055.kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.nikola0055.kmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.nikola0055.kmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.nikola0055.kmp"
            packageVersion = "1.0.0"
        }
    }
}

val localProperties = Properties().apply {
    val propertiesFile = project.rootProject.file("local.properties")
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { load(it) }
    }
}

val googleClientId = localProperties.getProperty("API_KEY") ?: ""

buildkonfig {
    packageName = "com.nikola0055.kmp"

    defaultConfigs {
        buildConfigField(STRING, "API_KEY", googleClientId)
    }
}

tasks.register("generateXcodeConfig") {
    val propertiesFile = project.rootProject.file("local.properties")
    val localProperties = Properties()
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { localProperties.load(it) }
    }

    val iosId = localProperties.getProperty("IOS_API_KEY") ?: ""
    val reversedIosId = iosId.split(".").reversed().joinToString(".")

    val outputFile = project.rootProject.file("iosApp/GoogleConfig.xcconfig")

    doLast {
        outputFile.writeText("""
            CLIENT_ID = $iosId
            URL_SCHEME = $reversedIosId
        """.trimIndent())
    }
}

tasks.matching { it.name.startsWith("compileKotlinIos") }.configureEach {
    dependsOn("generateXcodeConfig")
}