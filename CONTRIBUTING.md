# Contributing to Zero Patches

Thank you for your interest in contributing.

## Before you start

- Open an issue to discuss a substantial new patch or a major change before implementing it.
- Keep changes focused on the app and patch behaviour being modified.
- Do not claim support for an app version unless it has been tested against that exact version.
- Do not copy patches from other projects without checking their license and preserving required attribution.

## Development workflow

1. Fork the repository.
2. Create your working branch from `dev`.
3. Make and test your changes locally.
4. Run `./gradlew buildAndroid`.
5. Open a pull request targeting `dev`, not `main`.

## Commit messages

Use semantic commit messages:

- `feat:` for a new patch or user-facing feature.
- `fix:` for a bug fix.
- `perf:` for a performance improvement.
- `chore:` for maintenance and non-user-facing changes.
- `docs:` for documentation-only changes.

## Patch quality

- Use descriptive patch names and concise descriptions.
- Keep fingerprints as specific as practical and document why they are stable.
- Declare accurate app package names, APK file types, and supported versions.
- Avoid broad fingerprints that can match unrelated methods.
- Test every supported target before marking it as confirmed.
- Keep extension code minimal and use it only when patch-time bytecode changes are insufficient.

## Generated files

Do not manually edit or commit generated release artefacts, including:

- `CHANGELOG.md`
- `patches-list.json`
- `patches-bundle.json`
- The generated patch catalogue sections in `README.md` and `PATCHES.md`

The existing semantic-release workflow updates these files.

## Licensing

By contributing, you agree that your contributions are distributed under the repository's GPL-3.0 license. Preserve copyright notices and third-party attribution where applicable.
