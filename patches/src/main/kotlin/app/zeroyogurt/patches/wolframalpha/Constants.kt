package app.zeroyogurt.patches.wolframalpha

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.SupportedAbi

internal object WolframAlphaConstants {
    const val PACKAGE_NAME = "com.wolfram.android.alphapro"
    const val VERSION_NAME = "1.0.8.20260601651"
    const val VERSION_CODE = 117

    const val DARK_MODE = "DARK_MODE"
    const val LIGHT_MODE = "LIGHT_MODE"

    const val HIDE_LOGO_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEWOLFRAMALPHALOGOKEY"
    const val HIDE_RESULT_FADING_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEQUERYRESULTFADINGKEY"
    const val HIDE_QUERY_BAR_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEQUERYBARKEY"
    const val HIDE_WARNINGS_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEWARNINGSKEY"
    const val HIDE_ASSUMPTIONS_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEASSUMPTIONSKEY"
    const val HIDE_FORMULAS_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEFORMULASKEY"
    const val HIDE_WEATHER_BANNER_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEWEATHERBANNERKEY"
    const val HIDE_SOURCE_INFORMATION_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDESOURCEINFORMATIONKEY"
    const val HIDE_COMPUTATION_TIME_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDECOMPUTATIONTIMEDKEY"
    const val HIDE_CREATE_OPTIONS_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDECREATEOPTIONSKEY"
    const val HIDE_IMAGE_MAP_LINKS_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEIMAGEMAPLINKSKEY"
    const val HIDE_POWERED_BY_FOOTER_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.HIDEPOWEREDBYWOLFRAMALPHAFOOTERKEY"
    const val POD_SELECT_KEY =
        "com.wolfram.android.alpha.activity.WolframAlpha.PODSELECTKEY"

    const val FRAGMENT_PACKAGE = "Lcom/wolfram/android/alphalibrary/fragment/"
    const val ENGINE_CLASS = "Lcom/wolfram/android/alphalibrary/e;"

    val COMPATIBILITY = Compatibility(
        name = "Wolfram|Alpha",
        packageName = PACKAGE_NAME,
        apkFileType = ApkFileType.APKS_REQUIRED,
        appIconColor = 0xFA7159,
        targets = listOf(
            AppTarget(
                version = VERSION_NAME,
                versionCodes = mapOf(SupportedAbi.ARM64_V8A to VERSION_CODE),
                minSdk = 27,
                description = "Split APKS build inspected from the supplied versionCode 117 package.",
            ),
        ),
    )
}
