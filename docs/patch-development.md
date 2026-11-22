# Patch development notes

This repository uses the official [Morphe patches template](https://github.com/MorpheApp/morphe-patches-template) and the [Morphe patcher](https://github.com/MorpheApp/morphe-patcher).

## Source layout

- `patches/src/main/kotlin/` contains patch definitions, fingerprints, compatibility metadata, and shared Kotlin code.
- `extensions/` contains optional Android extension modules packaged with a patch bundle.
- `patches/build.gradle.kts` defines patch bundle metadata and Android API checks.
- `.github/scripts/generate_patches_readme.py` generates the README index and detailed `PATCHES.md` catalogue from release metadata.

## Adding a patch

1. Identify the target app and record its package name and exact version.
2. Inspect the APK and find stable class, method, field, string, or instruction fingerprints.
3. Add a patch definition under a package named for the target app or feature.
4. Declare compatibility with the exact tested version(s).
5. Add extension code only when required, and reference its correct DEX class descriptor.
6. Build with `./gradlew buildAndroid`.
7. Test the resulting bundle against a clean copy of the target APK.
8. Document supported versions and meaningful options in the patch metadata.

## Compatibility metadata

Declare the app name, package name, APK file type, icon colour, and tested app targets in the Morphe patch DSL. Prefer exact, verified version targets. Use experimental targets only when the patch has not yet been fully validated, and label them accordingly.

## Local build

```bash
./gradlew buildAndroid
```

The resulting bundle is in `patches/build/libs/`. Apply it with [Morphe Desktop](https://github.com/MorpheApp/morphe-desktop).

## Releases

Make development changes on `dev`. Semantic release creates pre-releases from that branch and stable releases from `main`. Never create release assets or modify generated release metadata manually.

Refer to the upstream [fingerprinting guide](https://github.com/MorpheApp/morphe-patcher/blob/main/docs/2_2_1_fingerprinting.md) and [patcher documentation](https://github.com/MorpheApp/morphe-patcher/tree/main/docs) for API details.
