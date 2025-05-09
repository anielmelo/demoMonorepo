plugins {
	id("buildlogic.java-application-conventions")
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    // VERSÃO CENTRALIZADA
    // implementation(libs.spring.data.jpa)
    // implementation(libs.spring.starter.web)
    // implementation(libs.spring.boot.starter.validation)
    
    // developmentOnly(libs.spring.boot.devtools)
    
    // testImplementation(libs.spring.boot.starter.test)
    // testRuntimeOnly(libs.junit.platform.launcher)
    
    // runtimeOnly(libs.postgres.db)

    implementation("org.springframework.boot:spring-boot-starter-web:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.5")

    developmentOnly("org.springframework.boot:spring-boot-devtools:3.4.5")
    
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.4")

    runtimeOnly("org.postgresql:postgresql:42.7.5")
}

application {
    mainClass = "br.edu.ifpb.esp.demo.DemoApplication"
}
