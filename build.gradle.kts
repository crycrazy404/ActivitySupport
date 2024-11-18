import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")

    id("org.jetbrains.compose")
}

group = "com.activityApp"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven ("https://releases.aspose.com/java/repo/" )
}

kotlin {
    jvm {
        compilations.all{
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.apache.poi:poi-ooxml:5.2.5")
                implementation ("androidx.compose.ui:ui:1.0.5")
                implementation("org.jetbrains.compose.ui:ui:<compose_version>")
                implementation("org.apache.xmlgraphics:batik-svggen:1.14")
                implementation("org.apache.xmlgraphics:batik-dom:1.14")
                implementation("org.jetbrains.skiko:skiko-awt:0.7.57")
                implementation("org.apache.poi:poi-ooxml:5.2.3")
                implementation("org.apache.xmlgraphics:batik-svggen:1.14")
                implementation("org.apache.xmlgraphics:batik-codec:1.14")
                implementation("org.apache.xmlgraphics:batik-dom:1.14")
                implementation("org.apache.xmlgraphics:batik-util:1.14")
                implementation("org.apache.xmlgraphics:batik-gvt:1.14")
                implementation("org.apache.xmlgraphics:batik-bridge:1.14")
                implementation("org.apache.xmlgraphics:batik-xml:1.14")
                implementation("org.apache.xmlgraphics:batik-ext:1.14")
                implementation("org.apache.xmlgraphics:batik-script:1.14")
                implementation("org.apache.xmlgraphics:batik-svgpp:1.14")
                implementation("org.apache.xmlgraphics:batik-anim:1.14")
                implementation("org.apache.xmlgraphics:batik-css:1.14")
                implementation("org.apache.xmlgraphics:batik-parser:1.14")
                implementation("org.apache.xmlgraphics:batik-awt-util:1.14")
                implementation("org.jetbrains.exposed:exposed-core:0.50.0")
                implementation("org.jetbrains.exposed:exposed-jdbc:0.50.0")
                implementation("org.jetbrains.exposed:exposed-dao:0.50.0")
                implementation("org.jetbrains.exposed:exposed-java-time:0.50.0") // Для работы с LocalDate и LocalDateTime
                implementation("com.h2database:h2:2.3.232")    // Для H2
                implementation("org.xerial:sqlite-jdbc:3.42.0.0")
                implementation("org.telegram:telegrambots:6.7.0")
                implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Activity"
            packageVersion = "1.0.0"
        }
    }
}
