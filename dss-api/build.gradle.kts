import java.util.*

plugins {
    id("java")
}

group = "mil.army.usace.hec"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}


//https://www.hec.usace.army.mil/nexus/repository/maven-public/mil/army/usace/hec/hecdss/7-IS-6-win-x86_64/hecdss-7-IS-6-win-x86_64.zip

tasks.named<Jar>("jar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from("src/main/resources") {
        include("natives/**")
    }
}

tasks.withType<Test>().configureEach {
    println("Setting java.library.path to: ${getNativeLibraryPath()}")
    jvmArgs(
            "--enable-native-access=ALL-UNNAMED",
            "-Djava.library.path=${getNativeLibraryPath()}"
    )
}

/* Helpers */
fun getNativeLibraryPath(): String {
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
    val nativesDir = "${projectDir}/src/main/resources/natives/"
    return when {
        osName.contains("win") -> nativesDir + "windows"
        osName.contains("mac") -> nativesDir + "macos"
        else -> nativesDir + "linux"
    }
}