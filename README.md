<div align="center">

<img src="assets/zero-patches.svg" alt="Zero Patches logo" width="128">

# 🧩 Zero Patches

**Independent, community-maintained patches for Morphe.**

Maintained by [Zeroyogurt](https://github.com/GraveEaterMadison).

[![Latest release](https://img.shields.io/github/v/release/GraveEaterMadison/Zero-Patches?style=for-the-badge&label=latest%20release)](https://github.com/GraveEaterMadison/Zero-Patches/releases)
[![Release workflow](https://img.shields.io/github/actions/workflow/status/GraveEaterMadison/Zero-Patches/release.yml?branch=main&style=for-the-badge&label=build)](https://github.com/GraveEaterMadison/Zero-Patches/actions/workflows/release.yml)
[![License](https://img.shields.io/github/license/GraveEaterMadison/Zero-Patches?style=for-the-badge)](LICENSE)
[![Issues](https://img.shields.io/github/issues/GraveEaterMadison/Zero-Patches?style=for-the-badge)](https://github.com/GraveEaterMadison/Zero-Patches/issues)

<a href="https://morphe.software/add-source?github=GraveEaterMadison/Zero-Patches">
  <img src=".github/assets/add-to-morphe.svg" alt="Add Zero Patches to Morphe Manager" height="48">
</a>

</div>

---

## About

**Zero Patches** is an independent third-party patch bundle project maintained by **Zeroyogurt**. It is intended to provide optional patches for Android apps supported by the [Morphe](https://github.com/MorpheApp) patching ecosystem.

This repository is community-maintained and is **not affiliated with, endorsed by, or an official project of Morphe**. The Morphe name is used only to describe compatibility.

## Add Zero Patches to Morphe Manager

1. Install [Morphe Manager](https://morphe.software/).
2. Use the **Add Zero Patches** button above, or add this source manually:
   `https://github.com/GraveEaterMadison/Zero-Patches`
3. Open the source in Morphe Manager and select a patch bundle when a release is available.

## Patch catalogue

The catalogue below is generated automatically from the released patch metadata. It will populate after the first release.

<!-- PATCHES_START -->

_No patches have been published yet. The repository is currently being prepared._

<!-- PATCHES_END -->

See **[PATCHES.md](PATCHES.md)** for the detailed patch reference. It is generated alongside the release catalogue.

## Compatibility

Each patch release will document its supported app package names and target versions. Do not assume that a patch supports the latest version of an app unless that version is explicitly listed in the release metadata.

Use the exact app version listed by the patch catalogue whenever possible. Back up app data before patching and keep an unmodified copy of the original APK.

## Development

Development happens on the `dev` branch. The `main` branch is reserved for stable releases.

### Requirements

- JDK 21
- Git
- Node.js LTS and npm (for the semantic-release workflow)
- Python 3 (for generating the patch catalogue)

### Build the patch bundle

Clone the repository and build from its root:

```bash
git clone https://github.com/GraveEaterMadison/Zero-Patches.git
cd Zero-Patches
git switch dev
./gradlew buildAndroid
```

The generated `.mpp` bundle is written to:

```
patches/build/libs/
```

Use [Morphe Desktop](https://github.com/MorpheApp/morphe-desktop) to apply a locally built bundle to a supported APK.

For fingerprinting, patch DSL examples, compatibility declarations, and extension development, see the [Morphe patcher documentation](https://github.com/MorpheApp/morphe-patcher/tree/main/docs) and [development documentation](docs/patch-development.md).

## Repository structure

```text
.
├── .github/
│   ├── ISSUE_TEMPLATE/       # Bug reports and feature requests
│   ├── assets/               # Morphe Manager source button and icons
│   ├── scripts/              # Automatic patch catalogue generator
│   └── workflows/            # Pull-request and release automation
├── docs/                     # Project-specific development notes
├── extensions/               # Optional Android extension module(s)
├── patches/
│   └── src/main/kotlin/      # Patch implementations and fingerprints
├── gradle/                   # Gradle wrapper and version catalogue
├── CHANGELOG.md              # Generated release notes
├── PATCHES.md                # Generated detailed patch reference
├── patches-list.json         # Generated patch metadata
└── patches-bundle.json       # Generated bundle metadata
```

Generated release files are maintained by the existing release workflow. Do not edit `CHANGELOG.md`, `patches-list.json`, or `patches-bundle.json` by hand.

## Releases

- **`dev`** produces pre-releases for testing.
- **`main`** is used for stable releases.
- Releases are generated automatically from semantic commit messages.
- Do not upload patch bundles or create releases manually.

## Contributing

Bug reports and patch suggestions are welcome through the [issue tracker](https://github.com/GraveEaterMadison/Zero-Patches/issues). Before opening an issue, search for an existing report and include the affected app version, package name, patch name, and relevant logs.

Read [CONTRIBUTING.md](CONTRIBUTING.md) before submitting changes.

## License

Zero Patches is distributed under the [GNU General Public License v3.0](LICENSE). See [NOTICE](NOTICE) for the required project-name and branding restrictions.
