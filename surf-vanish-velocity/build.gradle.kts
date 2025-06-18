plugins {
    id("dev.slne.surf.surfapi.gradle.velocity")
}

velocityPluginFile {
    main = "dev.slne.surf.vanish.velocity.VelocityMain"
    version = (findProperty("version") ?: "Unknown") as String?

    authors = listOf("red")
}