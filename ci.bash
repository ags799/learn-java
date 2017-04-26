# written for Travis CI

set -xeuo pipefail
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.2/install.sh | bash
# https://github.com/creationix/nvm/issues/1505
sed -i '2474,2476s/$REINSTALL_PACKAGES_FROM/${REINSTALL_PACKAGES_FROM:-}/' /home/username/.nvm/nvm.sh
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
nvm install 4
nvm use 4
node --version
npm -v
./gradlew assemble check
if [ "${TRAVIS_PULL_REQUEST_BRANCH:-$TRAVIS_BRANCH}" = 'master' ]; then
  npm install -g serverless
  serverless deploy --stage dev --region us-east-1 --verbose
fi
