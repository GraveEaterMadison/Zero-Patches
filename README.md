# Zero Patches

**Independent, community-maintained patches for Morphe.**  
Maintained by [@GraveEaterMadison](https://github.com/GraveEaterMadison) (Zeroyogurt).

Zero Patches is a third-party patch bundle project. It is not affiliated with or endorsed by the Morphe project.

## Add Zero Patches to Morphe Manager

Add this repository as a patch source in Morphe Manager:

[**Add Zero Patches**](https://morphe.software/add-source?github=GraveEaterMadison/Zero-Patches)

Or add the repository URL manually:

`https://github.com/GraveEaterMadison/Zero-Patches`

## Available patches

<!-- PATCHES_START EXPANDED -->

The patch list will be generated automatically after the first release.

<!-- PATCHES_END -->

## Development

Development takes place on the `dev` branch. The `main` branch is reserved for stable releases.

### Build locally

1. Follow the [Morphe development setup guide](https://github.com/MorpheApp/morphe-documentation/blob/main/docs/morphe-development/README.md).
2. Build the Android patch bundle:

   ```bash
   ./gradlew buildAndroid
   ```

3. The generated `.mpp` file is located in `patches/build/libs/`.

Apply the bundle locally with [Morphe Desktop](https://github.com/MorpheApp/morphe-desktop).

### Contributing

- Create changes on `dev`, not `main`.
- Use [semantic commit messages](https://kapeli.com/cheat_sheets/Semantic_Commits.docset/Contents/Resources/Documents/index), such as `feat:`, `fix:`, and `chore:`.
- Open issues for bugs and feature suggestions using the repository templates.
- Stable releases are produced through the existing GitHub Actions release workflow. Do not create releases or edit generated release files manually.

For patch development examples, see the [Morphe patcher documentation](https://github.com/MorpheApp/morphe-patcher/blob/main/docs/1_patcher_intro.md).

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE).
