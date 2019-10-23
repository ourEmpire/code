import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootWar


plugins {
	java
	war
	kotlin("jvm") version "1.2.71"
	kotlin("plugin.allopen") version "1.2.71"
	kotlin("plugin.jpa") version "1.2.71"
	kotlin("plugin.spring") version "1.2.71"
	id("org.springframework.boot") version "2.1.7.RELEASE" apply false
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:2.1.6.RELEASE")
	}
}

apply {
	plugin("war")
	plugin("java")
	plugin("idea")
	plugin("org.springframework.boot")
	plugin("io.spring.dependency-management")
}

the<DependencyManagementExtension>().apply {
	imports {
		mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
	}
}

repositories {
	jcenter()
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.alibaba:fastjson:1.2.59")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.auth0:java-jwt:3.3.0")
	compile("org.springframework.boot:spring-boot-starter-mail")
	compile("org.springframework.boot:spring-boot-starter-thymeleaf")
	runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
	runtimeOnly("org.springframework.boot:spring-boot-devtools")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.apache.httpcomponents:httpclient:4.5.9")
	implementation("org.apache.httpcomponents:httpcore:4.4.11")
	implementation("org.jsoup:jsoup:1.12.1")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

val war: War by tasks
war.archiveName = "api.rest.war"

tasks.getByName<War>("war") {
	enabled = true
}

tasks.getByName<BootWar>("bootWar") {
	isExcludeDevtools = false
}

tasks.getByName<BootWar>("bootWar") {
	classifier = "boot"
}

tasks.getByName<BootWar>("bootWar") {
	mainClassName = "server.Application"
}

tasks.getByName<BootWar>("bootWar") {
	manifest {
		attributes("Start-Class" to "server.Application")
	}
}

tasks.getByName<BootWar>("bootWar") {
	manifest {
		attributes("Main-Class" to "org.springframework.boot.loader.PropertiesLauncher")
	}
}