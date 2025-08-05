plugins {
    java
}

group = "mil.army.usace.hec"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://www.hec.usace.army.mil/nexus/repository/maven-public/") }
}

configurations {
    create("windowsNatives")
    create("linuxNatives")
}

dependencies {
    // Add the zip artifacts to the custom configuration
    "windowsNatives"("mil.army.usace.hec:hecdss:7-IV-1-win-x86_64@zip")
    "linuxNatives"("mil.army.usace.hec:hecdss:7-IV-1-linux-x86_64@zip")

    implementation("org.scijava:native-lib-loader:2.5.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Task to extract Windows natives
tasks.register<Copy>("extractWindowsNatives") {
    from(configurations.getByName("windowsNatives").map { zipFile ->
        zipTree(zipFile)
    })
    into(layout.buildDirectory.dir("resources/main/natives/windows_64"))
}

// Task to extract Linux natives
tasks.register<Copy>("extractLinuxNatives") {
    from(configurations.getByName("linuxNatives").map { zipFile ->
        zipTree(zipFile)
    })
    into(layout.buildDirectory.dir("resources/main/natives/linux_64"))
}

// Task to extract all natives
tasks.register("extractAllNatives") {
    dependsOn("extractWindowsNatives", "extractLinuxNatives")
}

// Make the processResources task depend on extracting natives
tasks.named("processResources") {
    dependsOn("extractAllNatives")
}

tasks.test {
    useJUnitPlatform()
}