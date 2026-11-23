package app.zeroyogurt.patches.wolframalpha

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.booleanOption
import app.morphe.patcher.patch.patch
import app.morphe.patcher.util.getFreeRegisterProvider
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

@Suppress("unused")
val wolframAlphaResultCustomizationPatch = patch(
    name = "Wolfram|Alpha Result Customization",
    description = "Hide selected result elements using the app's existing result-rendering flags.",
    default = true,
) {
    compatibleWith(WolframAlphaConstants.COMPATIBILITY)

    val hideLogo = booleanOption("hide_logo", false, title = "Hide Wolfram|Alpha logo")
    val hideResultFading = booleanOption("hide_result_fading", false, title = "Disable result fading")
    val hideQueryBar = booleanOption(
        "hide_query_bar",
        false,
        title = "Hide query bar",
        description = "Hides the query input bar on the result screen.",
    )
    val hideWarnings = booleanOption("hide_warnings", false, title = "Hide warnings")
    val hideAssumptions = booleanOption("hide_assumptions", false, title = "Hide assumptions")
    val hideFormulas = booleanOption("hide_formulas", false, title = "Hide formulas")
    val hideWeatherBanner = booleanOption("hide_weather_banner", false, title = "Hide weather banner")
    val hideSourceInformation = booleanOption("hide_source_information", false, title = "Hide source information")
    val hideComputationTime = booleanOption("hide_computation_time", false, title = "Hide computation time")
    val hideCreateOptions = booleanOption("hide_create_options", false, title = "Hide create options")
    val hideImageMapLinks = booleanOption("hide_image_map_links", false, title = "Hide image-map links")
    val hidePoweredByFooter = booleanOption("hide_powered_by_footer", false, title = "Hide powered-by footer")

    execute {
        val selectedKeys = buildList {
            if (hideLogo.value == true) add(WolframAlphaConstants.HIDE_LOGO_KEY)
            if (hideResultFading.value == true) add(WolframAlphaConstants.HIDE_RESULT_FADING_KEY)
            if (hideQueryBar.value == true) add(WolframAlphaConstants.HIDE_QUERY_BAR_KEY)
            if (hideWarnings.value == true) add(WolframAlphaConstants.HIDE_WARNINGS_KEY)
            if (hideAssumptions.value == true) add(WolframAlphaConstants.HIDE_ASSUMPTIONS_KEY)
            if (hideFormulas.value == true) add(WolframAlphaConstants.HIDE_FORMULAS_KEY)
            if (hideWeatherBanner.value == true) add(WolframAlphaConstants.HIDE_WEATHER_BANNER_KEY)
            if (hideSourceInformation.value == true) add(WolframAlphaConstants.HIDE_SOURCE_INFORMATION_KEY)
            if (hideComputationTime.value == true) add(WolframAlphaConstants.HIDE_COMPUTATION_TIME_KEY)
            if (hideCreateOptions.value == true) add(WolframAlphaConstants.HIDE_CREATE_OPTIONS_KEY)
            if (hideImageMapLinks.value == true) add(WolframAlphaConstants.HIDE_IMAGE_MAP_LINKS_KEY)
            if (hidePoweredByFooter.value == true) add(WolframAlphaConstants.HIDE_POWERED_BY_FOOTER_KEY)
        }

        val queryBarMethod = QueryBarFingerprint.method
        val resultOptionsMethod = ResultOptionsFingerprint.method

        if (hideQueryBar.value == true) {
            val intentResult = QueryBarFingerprint.instructionMatches[1]
            val intentRegister = intentResult.getInstruction<OneRegisterInstruction>().registerA
            val insertIndex = intentResult.index + 1
            val registers = queryBarMethod.getFreeRegisterProvider(insertIndex, 2, intentRegister)
            val keyRegister = registers.getFreeRegister()
            val valueRegister = registers.getFreeRegister()
            check(intentRegister <= 15 && keyRegister <= 15 && valueRegister <= 15) {
                "Could not allocate low registers for Intent.putExtra"
            }

            // A present HIDEQUERYBARKEY with value false is the branch that hides this view.
            queryBarMethod.addInstructions(
                insertIndex,
                """
                    const-string v$keyRegister, "${WolframAlphaConstants.HIDE_QUERY_BAR_KEY}"
                    const/4 v$valueRegister, 0x0
                    invoke-virtual {v$intentRegister, v$keyRegister, v$valueRegister}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;
                """.trimIndent(),
            )
        }

        if (selectedKeys.isNotEmpty()) {
            val instructions = resultOptionsMethod.implementation?.instructions?.toList()
                ?: error("Result options fingerprint resolved to a method without implementation")

            val fieldByKey = mutableMapOf<String, FieldReference>()
            instructions.forEachIndexed { index, instruction ->
                if (instruction.opcode != Opcode.CONST_STRING && instruction.opcode != Opcode.CONST_STRING_JUMBO) {
                    return@forEachIndexed
                }

                val key = ((instruction as ReferenceInstruction).reference as StringReference).string
                if (key !in selectedKeys) return@forEachIndexed

                val nextString = (index + 1 until instructions.size).firstOrNull { nextIndex ->
                    instructions[nextIndex].opcode == Opcode.CONST_STRING ||
                        instructions[nextIndex].opcode == Opcode.CONST_STRING_JUMBO
                } ?: instructions.size

                val fieldWrite = (index + 1 until nextString).firstOrNull { nextIndex ->
                    instructions[nextIndex].opcode == Opcode.IPUT_BOOLEAN
                } ?: error("No boolean field write found for supported result flag: $key")

                val field = (instructions[fieldWrite] as ReferenceInstruction).reference as FieldReference
                check(field.type == "Z" && field.definingClass == WolframAlphaConstants.ENGINE_CLASS) {
                    "Expected a boolean field on the Wolfram Alpha engine object for result flag: $key"
                }
                fieldByKey[key] = field
            }

            val selectedFields = selectedKeys.map { key ->
                fieldByKey[key] ?: error("Could not resolve the app field for result flag: $key")
            }

            val podSelectCallIndex = ResultOptionsFingerprint.instructionMatches.last().index
            val engineLoadIndex = (podSelectCallIndex + 1 until instructions.size).firstOrNull { index ->
                val instruction = instructions[index]
                if (instruction.opcode != Opcode.IGET_OBJECT || instruction !is ReferenceInstruction) {
                    false
                } else {
                    val field = instruction.reference as? FieldReference
                    field?.type == WolframAlphaConstants.ENGINE_CLASS &&
                        field.definingClass.startsWith(WolframAlphaConstants.FRAGMENT_PACKAGE)
                }
            } ?: error("Could not find the app state object after PODSELECTKEY")

            val engineRegister = (instructions[engineLoadIndex] as TwoRegisterInstruction).registerA
            val insertIndex = engineLoadIndex + 1
            val valueRegister = resultOptionsMethod
                .getFreeRegisterProvider(insertIndex, 1, engineRegister)
                .getFreeRegister()
            check(engineRegister <= 15 && valueRegister <= 15) {
                "Could not allocate low registers for result flag writes"
            }

            val injectedCode = buildString {
                append("const/4 v$valueRegister, 0x1\n")
                selectedFields.forEach { field ->
                    append(
                        "iput-boolean v$valueRegister, v$engineRegister, " +
                            "${field.definingClass}->${field.name}:${field.type}\n",
                    )
                }
            }

            resultOptionsMethod.addInstructions(insertIndex, injectedCode)
        }
    }
}
