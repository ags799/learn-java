# written for Travis CI

set -xeuo pipefail
if [[ $(npm -v) != 4* ]]; then npm i -g npm@4; fi
npm --version  # TODO: remove this line
./gradlew assemble check
if [ "${TRAVIS_PULL_REQUEST_BRANCH:-$TRAVIS_BRANCH}" = 'master' ]; then
  npm install -g serverless
  serverless deploy --stage dev --region us-east-1 --verbose
fi
