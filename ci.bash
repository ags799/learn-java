# written for Travis CI

set -xeuo pipefail
./gradlew assemble check
if [ "${TRAVIS_PULL_REQUEST_BRANCH:-$TRAVIS_BRANCH}" = 'master' ]; then
  npm install -g serverless
  serverless deploy
fi
