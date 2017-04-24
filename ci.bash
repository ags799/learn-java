set -xeuo pipefail
./gradlew assemble check
git branch
git branch | grep '*'
git branch | grep '*' | cut -d' ' -f2
if [ "$(git branch | grep '*' | cut -d' ' -f2)" = 'master' ]; then
  npm install -g serverless
  serverless deploy
fi
