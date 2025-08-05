plugins {
    java
}

group = "mil.army.usace.hec"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://www.hec.usace.army.mil/nexus/repository/maven-public/") }
}

// Define platform-specific configurations
val windowsNatives by configurations.creating
val linuxNatives by configurations.creating

// Define Version Numbers
val hecDssVersion = "7-IV-1"
val nativeLibLoaderVersion = "2.5.0"
val junitVersion = "5.10.0"

// Define groups
val nativeLibrariesGroup = "native libraries"

dependencies {
    // HEC-DSS Binaries
    windowsNatives("mil.army.usace.hec:hecdss:$hecDssVersion-win-x86_64@zip")
    linuxNatives("mil.army.usace.hec:hecdss:$hecDssVersion-linux-x86_64@zip")
    // NativeLibLoader to load the native libraries seamlessly
    implementation("org.scijava:native-lib-loader:$nativeLibLoaderVersion")
    // JUnit Testing Framework
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Modern task registration for Windows natives
tasks.register<Copy>("extractWindowsNatives") {
    group = nativeLibrariesGroup
    description = "Extract Windows native libraries from the HEC-DSS zip file."
    from(provider { windowsNatives.files.map { zipTree(it) } })
    into(layout.buildDirectory.dir("resources/main/natives/windows_64"))

    // Only extract when the source files have changed
    inputs.files(windowsNatives)
    outputs.dir(layout.buildDirectory.dir("resources/main/natives/windows_64"))
}

// Modern task registration for Linux natives
tasks.register<Copy>("extractLinuxNatives") {
    group = nativeLibrariesGroup
    description = "Extract Linux native libraries from the HEC-DSS zip file."
    from(provider { linuxNatives.files.map { zipTree(it) } })
    into(layout.buildDirectory.dir("resources/main/natives/linux_64"))

    // Only extract when the source files have changed
    inputs.files(linuxNatives)
    outputs.dir(layout.buildDirectory.dir("resources/main/natives/linux_64"))
}

// Aggregate task using lazy configuration
val extractAllNatives by tasks.registering {
    group = nativeLibrariesGroup
    description = "Extract all supported OS native libraries from the HEC-DSS zip file."
    dependsOn(tasks.named("extractWindowsNatives"), tasks.named("extractLinuxNatives"))
}

// Configure processResources to depend on native extraction
tasks.named<ProcessResources>("processResources") {
    dependsOn(extractAllNatives)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}