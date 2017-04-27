# written for Travis CI

set -xeuo pipefail

installNvm() {
  curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.1/install.sh | bash
  # https://github.com/creationix/nvm/issues/1505
  sed -i '2474,2476s/$REINSTALL_PACKAGES_FROM/${REINSTALL_PACKAGES_FROM:-}/' /home/travis/.nvm/nvm.sh
  export NVM_DIR="$HOME/.nvm"
  [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
}

installServerless() {
  installNvm
  nvm install 4
  nvm use 4
  npm install -g serverless
}

./gradlew assemble check

# integration tests
installServerless
branch="${TRAVIS_PULL_REQUEST_BRANCH:-$TRAVIS_BRANCH}"
stage="ci-$branch"
./gradlew deploy "-Pstage=$stage"
url="$(serverless info --stage "$stage" | grep -C 1 '^endpoints:' | sed -n 3p | cut -d' ' -f5)"
LEARN_JAVA_URL="$url" ./gradlew integrationTest ||
    (./gradlew removeDeployment "-Pstage=$stage" && exit 1)
./gradlew removeDeployment "-Pstage=$stage"

# continuous deployment
if [[ "$branch" = 'master' ]]; then
  ./gradlew deploy -Pstage=prod
fi

