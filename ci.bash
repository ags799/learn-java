# written for Travis CI

set -xeuo pipefail
./gradlew assemble check
nvm install 4
nvm use 4
node --version
npm -v
if [ "${TRAVIS_PULL_REQUEST_BRANCH:-$TRAVIS_BRANCH}" = 'master' ]; then
  npm install -g serverless
  serverless deploy --stage dev --region us-east-1 --verbose
fi
