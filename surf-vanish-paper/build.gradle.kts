import dev.slne.surf.surfapi.gradle.util.registerRequired
import dev.slne.surf.surfapi.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

repositories {
    maven("https://repo.extendedclip.com/releases/")
}

dependencies {
    api(project(":surf-vanish-core"))
    compileOnly("me.clip:placeholderapi:2.11.6")
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.vanish.paper.PaperMain")
    authors.add("red")
    generateLibraryLoader(false)

    serverDependencies {
        registerRequired("packetevents")
        registerSoft("PlaceholderAPI")
    }
}
