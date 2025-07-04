plugins {
    id("dev.slne.surf.surfapi.gradle.velocity")
}

dependencies {
    api(project(":surf-vanish-core"))
}

velocityPluginFile {
    main = "dev.slne.surf.vanish.velocity.VelocityMain"
    version = (findProperty("version") ?: "Unknown") as String?

    authors = listOf("red")
}

tasks.shadowJar {
    archiveFileName = "surf-vanish-velocity-${project.version}.jar"
}