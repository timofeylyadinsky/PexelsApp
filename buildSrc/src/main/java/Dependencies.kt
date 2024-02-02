object Deps {
    val coreKtx by lazy {"androidx.core:core-ktx:${Versions.core}"}
    val lifecycleRuntime by lazy {"androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"}
    val activityCompose by lazy {"androidx.activity:activity-compose:${Versions.activity}"}
    val composeBom by lazy {"androidx.compose:compose-bom:${Versions.bom}"}
    val composeUI by lazy {  "androidx.compose.ui:ui" }
    val composeGraphic by lazy {"androidx.compose.ui:ui-graphics"}
    val composePreview by lazy {"androidx.compose.ui:ui-tooling-preview"}
    val material3 by lazy {"androidx.compose.material3:material3"}


}