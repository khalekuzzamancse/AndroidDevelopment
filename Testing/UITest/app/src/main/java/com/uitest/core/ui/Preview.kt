package paymentsave.terminalapp.core.ui

import androidx.compose.ui.tooling.preview.Preview

// Your custom annotation holding multiple previews
@Preview(name = "Default")
@Preview(
    name = "Small Phone",
    device = DevicePreviews.SMALL_PHONE_PORTRAIT,
    showSystemUi = true
)
@Preview(
    name = "Large Phone",
    device = DevicePreviews.LARGE_PHONE_PORTRAIT,
    showSystemUi = true
)
@Preview(
    name = "Tablet",
    device = DevicePreviews.TABLET_PORTRAIT,
    showSystemUi = true
)
annotation class DevicePreviewsGroup

object DevicePreviews {
    // --- Phones ---
    const val SMALL_PHONE_PORTRAIT = "spec:width=320dp,height=480dp,orientation=portrait"
    const val SMALL_PHONE_LANDSCAPE = "spec:width=320dp,height=480dp,orientation=landscape"

    const val NORMAL_PHONE_PORTRAIT = "spec:width=360dp,height=640dp,orientation=portrait"
    const val NORMAL_PHONE_LANDSCAPE = "spec:width=360dp,height=640dp,orientation=landscape"

    const val LARGE_PHONE_PORTRAIT = "spec:width=411dp,height=731dp,orientation=portrait" // Pixel 2/3
    const val LARGE_PHONE_LANDSCAPE = "spec:width=411dp,height=731dp,orientation=landscape"

    const val XLARGE_PHONE_PORTRAIT = "spec:width=412dp,height=915dp,orientation=portrait" // Pixel 6/7
    const val XLARGE_PHONE_LANDSCAPE = "spec:width=412dp,height=915dp,orientation=landscape"

    // --- Tablets ---
    const val TABLET_PORTRAIT = "spec:width=800dp,height=1280dp,orientation=portrait"
    const val TABLET_LANDSCAPE = "spec:width=800dp,height=1280dp,orientation=landscape"

    const val XLARGE_TABLET_PORTRAIT = "spec:width=1280dp,height=1920dp,orientation=portrait"
    const val XLARGE_TABLET_LANDSCAPE = "spec:width=1280dp,height=1920dp,orientation=landscape"

    // --- Foldables ---
    const val FOLDABLE_OUTER_PORTRAIT = "spec:width=412dp,height=892dp,orientation=portrait" // Galaxy Z Fold outer
    const val FOLDABLE_OUTER_LANDSCAPE = "spec:width=412dp,height=892dp,orientation=landscape"

    const val FOLDABLE_INNER_PORTRAIT = "spec:width=673dp,height=841dp,orientation=portrait" // Galaxy Z Fold inner
    const val FOLDABLE_INNER_LANDSCAPE = "spec:width=673dp,height=841dp,orientation=landscape"

}
