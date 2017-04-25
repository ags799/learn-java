# written for Travis CI

set -xeuo pipefail
./gradlew assemble check
node --version
npm -v
if [ "${TRAVIS_PULL_REQUEST_BRANCH:-$TRAVIS_BRANCH}" = 'master' ]; then
  if [[ $(npm -v) != 4* ]]; then npm i -g npm@4; fi
  npm install -g serverless
  serverless deploy --stage dev --region us-east-1 --verbose
fi
