import dev.slne.surf.surfapi.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    api(project(":surf-vanish-core"))
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.vanish.paper.PaperMain")
    authors.add("red")
    generateLibraryLoader(false)

    serverDependencies {
        registerRequired("packetevents")
    }
}
