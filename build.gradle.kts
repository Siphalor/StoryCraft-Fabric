import net.fabricmc.loom.task.RemapJarTask
import net.fabricmc.loom.task.RemapSourcesJarTask

plugins {
	wrapper
	idea
	id("fabric-loom") version Fabric.Loom.version
	id("maven-publish")
	kotlin("jvm") version "1.3.21"
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

idea {
	module {
		excludeDirs.add(file("run"))
	}
}

base {
	archivesBaseName = Constants.name
}

version = "${Constants.version}+${Constants.minecraftVersionVer}"
group = "io.github.paradoxicalblock"

repositories {
	mavenCentral()
	mavenLocal()
	maven("https://maven.fabricmc.net")
	maven("https://minecraft.curseforge.com/api/maven")
    maven("http://maven.sargunv.s3-website-us-west-2.amazonaws.com/")
	maven("http://server.bbkr.space:8081/artifactory/libs-snapshot/")
	maven( "http://server.bbkr.space:8081/artifactory/libs-release/")
	maven("https://maven.jamieswhiteshirt.com/libs-release/")
	maven ("https://maven.abusedmaster.xyz")
}

dependencies {
	minecraft(group = "com.mojang", name = "minecraft", version = Minecraft.version)
	mappings(group = "net.fabricmc", name = "yarn", version = "${Minecraft.version}+${Fabric.Yarn.version}")
	modCompile(group = "net.fabricmc", name = "fabric-loader", version = Fabric.Loader.version)

	modCompile(group = "net.fabricmc.fabric-api", name = "fabric-api", version = Fabric.API.version)
	include(group = "net.fabricmc.fabric-api", name = "fabric-api", version = Fabric.API.version)

	modCompile(group = "cloth-config", name = "ClothConfig2", version = Dependencies.ClothConfig.version)
    include(group = "cloth-config", name = "ClothConfig2", version = Dependencies.ClothConfig.version)
    modCompile(group = "me.sargunvohra.mcmods", name = "auto-config", version = Dependencies.AutoConfig.version)
    include(group = "me.sargunvohra.mcmods", name = "auto-config", version = Dependencies.AutoConfig.version)

	// For dev env testing
	modCompile("com.jamieswhiteshirt:developer-mode:1.0.12")
	modCompile(group = "me.shedaniel", name = "RoughlyEnoughItems", version = Dependencies.REI.version)
	modCompile("informed-load-fabric:informedload:2.1.0:1.14")
	modCompile("the-biome-overhaul:the-biome:overhaul:1.2.0")
	modCompile(group = "io.github.prospector.modmenu", name = "ModMenu", version = Dependencies.ModMenu.version)
	modCompile(group = "shulkerboxtooltip", name = "shulkerboxtooltip", version = "1.3.0+1.14.2")
	modCompile("miners-horizon:miners:horizon:1.5.0")
	modCompile("leaf-decay:leaf:decay:1.0.3")

	compile(group="com.google.code.findbugs", name="jsr305", version="3.0.2")
}

tasks.getByName<ProcessResources>("processResources") {
	filesMatching("fabric.mod.json") {
		expand(
				mutableMapOf(
						"version" to version
				)
		)
	}
}

val javaCompile = tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val jar = tasks.getByName<Jar>("jar") {
    from("LICENSE")
}

val remapJar = tasks.getByName<RemapJarTask>("remapJar")

val remapSourcesJar = tasks.getByName<RemapSourcesJarTask>("remapSourcesJar")

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			artifactId = "StoryCraft"
			artifact(jar) {
				builtBy(remapJar)
			}
			pom {
				name.set("StoryCraft")
				description.set(Constants.description)
				url.set(Constants.url)
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("paradoxicalblock")
						name.set("ParadoxicalBlock")
					}
				}
			}
		}
	}
}
