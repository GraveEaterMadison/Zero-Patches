package app.zeroyogurt.patches.wolframalpha

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.InstructionLocation.MatchAfterImmediately
import app.morphe.patcher.methodCall
import app.morphe.patcher.opcode
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.Opcode

/**
 * Finds the query-parameter builder by the two stable Wolfram Alpha mode constants.
 * The containing class and method name are intentionally not used as sole anchors.
 */
internal object ResultAppearanceFingerprint : Fingerprint(
    definingClass = "Lcom/wolfram/android/alphalibrary/",
    returnType = "V",
    parameters = emptyList(),
    filters = listOf(
        string(WolframAlphaConstants.DARK_MODE),
        string(WolframAlphaConstants.LIGHT_MODE),
    ),
)

/**
 * Finds the fragment method that consumes the app's existing result-visibility flags.
 */
internal object ResultOptionsFingerprint : Fingerprint(
    definingClass = WolframAlphaConstants.FRAGMENT_PACKAGE,
    returnType = "V",
    parameters = listOf("Landroid/content/Intent;"),
    filters = listOf(
        string(WolframAlphaConstants.HIDE_LOGO_KEY),
        string(WolframAlphaConstants.POD_SELECT_KEY),
        methodCall(
            definingClass = "Landroid/os/BaseBundle;",
            name = "getString",
            parameters = listOf("Ljava/lang/String;"),
            returnType = "Ljava/lang/String;",
        ),
    ),
)

/**
 * Finds the query-bar visibility check using the fragment's Intent field.
 *
 * In the inspected target, y0(Bundle) loads an Intent field and calls
 * Intent.getExtras(); it does not call Activity.getIntent().
 */
internal object QueryBarFingerprint : Fingerprint(
    definingClass = WolframAlphaConstants.FRAGMENT_PACKAGE,
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;"),
    filters = listOf(
        methodCall(
            definingClass = "Landroid/content/Intent;",
            name = "getExtras",
            returnType = "Landroid/os/Bundle;",
        ),
        opcode(Opcode.MOVE_RESULT_OBJECT, MatchAfterImmediately()),
        string(WolframAlphaConstants.HIDE_QUERY_BAR_KEY),
        methodCall(
            definingClass = "Landroid/os/BaseBundle;",
            name = "containsKey",
            parameters = listOf("Ljava/lang/String;"),
            returnType = "Z",
        ),
        methodCall(
            definingClass = "Landroid/os/BaseBundle;",
            name = "getBoolean",
            parameters = listOf("Ljava/lang/String;"),
            returnType = "Z",
        ),
    ),
)
