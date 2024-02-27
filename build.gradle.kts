buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Navigation.navigationSafeArgsKtx)
        classpath(DaggerHilt.hiltGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
