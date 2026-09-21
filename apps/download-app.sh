#!/usr/bin/env bash
# Downloads the Android app under test (ApiDemos-debug.apk) into apps/.
#
# The app is not committed to the repository; it is fetched on demand, both
# for local runs and in CI, from its official release on GitHub:
#   https://github.com/appium/android-apidemos
#
# Usage: apps/download-app.sh [version]
#   version defaults to the latest tag below. Override with an explicit tag,
#   e.g. apps/download-app.sh v6.0.17

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
VERSION="${1:-v6.0.17}"
URL="https://github.com/appium/android-apidemos/releases/download/${VERSION}/ApiDemos-debug.apk"
DEST="${SCRIPT_DIR}/ApiDemos-debug.apk"

if [ -f "${DEST}" ]; then
    echo "App already present at ${DEST}, skipping download."
    exit 0
fi

echo "Downloading ApiDemos-debug.apk (${VERSION}) from ${URL} ..."
curl -fL --retry 3 --retry-delay 2 -o "${DEST}" "${URL}"
echo "Saved to ${DEST}"
