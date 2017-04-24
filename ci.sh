set -xeuo pipefail
./gradlew assemble check
if [[ "$(git branch | grep '*' | cut -d' ' -f2)" = 'master' ]]; then
  npm install -g serverless
  serverless deploy
fi
