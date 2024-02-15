object Deps {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.core}" }
    val lifecycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activity}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.bom}" }
    val composeUI by lazy { "androidx.compose.ui:ui" }
    val composeGraphic by lazy { "androidx.compose.ui:ui-graphics" }
    val composePreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val material3 by lazy { "androidx.compose.material3:material3" }

    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompilerKapt by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
    val hiltNavigation by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNav}" }

    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }

    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigation}" }

    val glideCompose by lazy { "com.github.bumptech.glide:compose:${Versions.glide}" }
    val landscapist by lazy { "com.github.skydoves:landscapist-glide:${Versions.landscapist}" }

    val coil by lazy { "io.coil-kt:coil-compose:${Versions.coil}" }

    val room by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }

    val splash by lazy { "androidx.core:core-splashscreen:${Versions.splash}" }
}