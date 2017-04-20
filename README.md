# Practice Java
[![CircleCI](https://circleci.com/gh/ags799/learn-java.svg?style=shield)](https://circleci.com/gh/ags799/learn-java)

A project for practicing Java development.

## Usage

TODO: describe how to use this library.

## Development

Build with `./gradlew build`.

### Releasing

Create an annotated git tag with

    git tag -a $VERSION -m "$MESSAGE"

where `$VERSION` follows [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

Push the tag with

    git push $VERSION

Create a [GitHub Release](https://github.com/ags799/learn-java/releases/new) for the
tag. The tag should already exist, and you should leave the title and description
fields blank.
