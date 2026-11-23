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

The fragment's `p0(Intent)` method reads the supported `HIDE...KEY` extras and writes their values into boolean fields on the shared Wolfram Alpha engine object. The patch sets only the selected fields after this flag-processing block. It does not enable SDK mode or alter action routing.

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

The `HIDEQUERYBARKEY` path is special: the fragment's `y0(Bundle)` method hides the query bar when the extra is present and its Boolean value is `false`. The patch inserts `Intent.putExtra(HIDEQUERYBARKEY, false)` immediately before the matching `Intent.getExtras()` call, then sets the corresponding engine flag in `p0(Intent)`.

Related-query, related-link, feedback/recommendation, and copyright keys are intentionally omitted. In the inspected `p0(Intent)` method their Boolean return values are read but not stored to a corresponding engine boolean field, so their effect is not established.

## Safety boundary

No billing, subscription, purchase verification, account, entitlement, network authentication, or server-side behaviour is modified.

## Fingerprint rationale

- Result appearance: both stable app-owned query-mode string constants in the query-parameter builder.
- Result flags: stable full extra-key strings, including the terminal `PODSELECTKEY` read and the following `BaseBundle.getString(String)` call, in the fragment's `Intent` handler.
- Query bar: `Intent.getExtras()`, immediately followed by `move-result-object` and the exact `HIDEQUERYBARKEY` string, then `BaseBundle.containsKey/getBoolean`. The supplied APK does not use `Activity.getIntent()` in this path; the fingerprint was corrected to match the actual DEX sequence.

The implementation derives the obfuscated boolean field references from the matched method's own key-to-`iput-boolean` instruction sequences instead of hard-coding those field names.

## Validation status

- APK archive/package/version reconnaissance: completed.
- Raw DEX instruction inspection for the three target methods: completed using a local DEX parser; this is instruction-level DEX inspection, not a full Smali decompilation.
- Corrected Kotlin source and Morphe API compatibility: CI `buildAndroid` passed on commit `306d4b11445cd9904bcd2ff8eadd1c8d96864d90`.
- Generated `patches-1.0.0.mpp` bundle: inspected. It contains both Wolfram|Alpha patch classes, the compatibility constants, the appearance choices, and all 12 customization option keys. Bundle SHA-256: `e7351226f939566dee299dfba8461c309683f08bdd96650bc2e0b48754d6e112`.
- Bundle manifest reports Patcher-Version `2.5.0`; compatibility bytecode includes package `com.wolfram.android.alphapro`, version `1.0.8.20260601651`, version code `117`, ARM64 ABI, and required APKS input.
- Applying the generated bundle to the original APKS and inspecting the resulting APK/splits: pending.
- Runtime/device testing: pending.

Do not describe the patches as working until patch application and runtime validation have succeeded.
