# Learn Java
[![Travis](https://travis-ci.org/ags799/learn-java.svg?branch=master)](https://travis-ci.org/ags799/learn-java)

We learn Java by implementing answers to common interview questions.

## Development

Prepare your environment by

- installing [serverless](http://serverless.com)
- [setting AWS access key ID & secret access key values](https://serverless.com/framework/docs/providers/aws/guide/credentials#amazon-web-services)

Verify your code with

    ./gradlew check

Run integration tests, which are not included in the above `check` task, with

    make integrationTest

Deploy your code with

    serverless deploy

Remove your deployment with

    serverless remove

Note that all of these commands will deploy to the default `dev` stack and `us-east-1` AWS region.
This could produce conflicts for simultaneous deploying across users.
