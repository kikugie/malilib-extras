# Malilib Extras
Additional utilities for Masa's [Malilib](https://www.curseforge.com/minecraft/mc-mods/malilib)

## Installation
```groovy
repositories {
    maven {url "https://maven.kikugie.dev/releases"} // Malilib Extras
    maven {url "https://maven.terraformersmc.com/releases/"} // Mod Menu
    maven {url "https://masa.dy.fi/maven"} // Malilib
}

dependencies {
    /*...*/
    // Malilib Extras is built for 1.19.4, 1.20.1. 1.20.2, 1.20.4 and has the same API for each version.
    // You can try using dependency overrides for other versions on your own risk.
    modImplementation "dev.kikugie.malilib_extras:${project.malilib_extras_version}:$project.minecraft_version}"
}
```

## Config
Provides a cleaner config registration, as well as fixing some inconsistencies and hardcoded settings.
> [!IMPORTANT]
> Read the [WIKI](https://github.com/kikugie/malilib-extras/wiki)  
> *(Scientists discovered that an ability to read solves 99% problems in life)*

*(Yes I like this embed too much, no I will not stop using it)*