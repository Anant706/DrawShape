plugins {
  java
  jacoco
  pmd
  application
  id("org.openjfx.javafxplugin") version "0.0.7"
}       

repositories {
	mavenCentral()
}

dependencies {
  testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
	testRuntime("org.junit.platform:junit-platform-console:1.2.0")
    implementation("org.reflections:reflections:0.9.11")

}
 
sourceSets {
  main {
    java.srcDirs("DiagramTool/src")
  }
  test {
    java.srcDirs("DiagramTool/test")
  }
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform {}
}

tasks {
  val treatWarningsAsError =
    listOf("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")

  getByName<JavaCompile>("compileJava") {
    options.compilerArgs = treatWarningsAsError      
  }

  getByName<JavaCompile>("compileTestJava") {
    options.compilerArgs = treatWarningsAsError
  }
}

tasks {
    getByName<JacocoReport>("jacocoTestReport") {
        afterEvaluate {
            setClassDirectories(files(classDirectories.files.map {
                fileTree(it) { exclude("**/ui/**") }
            }))
        }
    }
} 

application {
    mainClassName = "diagram.ui.CanvasUI"
} 

javafx {
    modules = listOf("javafx.controls", "javafx.fxml")
}      

pmd {
  ruleSets = listOf()
  ruleSetFiles = files("../conf/pmd/ruleset.xml")
}
 
defaultTasks("clean", "test", "jacocoTestReport", "pmdMain")
