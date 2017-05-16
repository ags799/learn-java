# written for Travis CI

set -xeuo pipefail

./gradlew assemble check
