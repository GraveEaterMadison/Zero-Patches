package app.zeroyogurt.patches.wolframalpha

import app.morphe.patcher.patch.patch
import app.morphe.patcher.patch.replaceStrings
import app.morphe.patcher.patch.stringOption
import app.morphe.patcher.string

@Suppress("unused")
val wolframAlphaResultAppearancePatch = patch(
    name = "Wolfram|Alpha Result Appearance",
    description = "Choose whether Wolfram|Alpha query results use the app's preference, dark mode, or light mode.",
    default = true,
) {
    compatibleWith(WolframAlphaConstants.COMPATIBILITY)

    val appearance = stringOption(
        key = "result_appearance",
        default = "app",
        values = linkedMapOf(
            "Use app preference" to "app",
            "Always dark" to "dark",
            "Always light" to "light",
        ),
        title = "Result appearance",
        description = "Controls the DARK_MODE/LIGHT_MODE parameter used for query results. It does not change the app's overall interface theme.",
    )

    execute {
        val targetMethod = ResultAppearanceFingerprint.method
        val mode = appearance.value ?: "app"

        when (mode) {
            "dark" -> replaceStrings(string(WolframAlphaConstants.LIGHT_MODE)) { method, _, _ ->
                if (method.definingClass == targetMethod.definingClass && method.name == targetMethod.name) {
                    WolframAlphaConstants.DARK_MODE
                } else {
                    null
                }
            }

            "light" -> replaceStrings(string(WolframAlphaConstants.DARK_MODE)) { method, _, _ ->
                if (method.definingClass == targetMethod.definingClass && method.name == targetMethod.name) {
                    WolframAlphaConstants.LIGHT_MODE
                } else {
                    null
                }
            }
        }
    }
}
