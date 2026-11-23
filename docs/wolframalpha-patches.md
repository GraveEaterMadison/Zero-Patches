# Wolfram|Alpha patches

## Target analysed

- Package: `com.wolfram.android.alphapro`
- Version: `1.0.8.20260601651`
- Version code: `117`
- Minimum SDK: `27`
- Input package: required split `.apks` archive with `base.apk` and `split_config.arm64_v8a.apk`
- Repository build configuration: Morphe patches plugin `2.1.1`, Morphe patcher API `2.5.0`
- Archive SHA-256: `a008326d4c3841e4258203af44ae314fbfc22b678cdc588b52e1ae9d8f8950e3`

Compatibility is deliberately limited to this version and ARM64 APKS target. No other Wolfram|Alpha release has been verified.

## Result appearance

The app's query-parameter builder contains the stable string constants `DARK_MODE` and `LIGHT_MODE`. The patch exposes three choices:

- Use app preference (no string replacement)
- Always dark (replace `LIGHT_MODE` with `DARK_MODE` in the matched method)
- Always light (replace `DARK_MODE` with `LIGHT_MODE` in the matched method)

This controls query-result rendering mode, not the entire app's Android theme.

## Result customization

The fragment reads existing `HIDE...KEY` flags into boolean fields on its shared application/engine object. In this target, those reads are gated behind `ISUSEDASSDKKEY`; the patch therefore sets the corresponding fields after that branch, rather than turning on SDK mode or modifying purchase logic.

The patch offers only flags for which the inspected DEX contains a corresponding boolean field write:

- Wolfram|Alpha logo
- Result fading
- Query bar
- Warnings
- Assumptions
- Formulas
- Weather banner
- Source information
- Computation time
- Create options
- Image-map links
- Powered-by footer

The `HIDEQUERYBARKEY` path is special: the app's fragment code hides the query bar when the extra is present and its Boolean value is `false`. The patch adds that exact value before the visibility check, then sets the matching internal query-bar field after the flag-reading branch.

Related-query, related-link, feedback/recommendation, and copyright keys are intentionally omitted. In the inspected `p0(Intent)` method their Boolean return values are read but not stored to a corresponding field, so their effect is not established.

## Safety boundary

No billing, subscription, purchase verification, account, entitlement, network authentication, or server-side behaviour is modified.

## Fingerprint rationale

- Result appearance: two stable, app-owned query-mode string constants in a no-argument method under the Wolfram Alpha library package.
- Result flags: stable full extra-key strings, including the terminal `PODSELECTKEY` read, in the fragment's `Intent` handler.
- Query bar: `Activity.getIntent()` followed immediately by `move-result-object`, then the exact query-bar key and `BaseBundle.containsKey/getBoolean` calls.

The implementation derives the obfuscated boolean field references from the matched method's own key-to-`iput-boolean` instruction sequences instead of hard-coding those field names.

## Validation status

APK reconnaissance and raw DEX instruction inspection completed. Full Smali decompilation, Morphe compilation, bundle inspection, patch application to the original APKS, and device/runtime testing remain pending.
