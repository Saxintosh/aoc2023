plugins {
    kotlin("jvm") version "1.9.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation("com.google.guava:guava:32.1.3-jre")
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
