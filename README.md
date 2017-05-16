# Learn Java
[![Travis](https://travis-ci.org/ags799/learn-java.svg?branch=master)](https://travis-ci.org/ags799/learn-java)

We learn Java by implementing answers to common interview questions.

## Development

Prepare your environment by

- installing [serverless](http://serverless.com)
- [setting AWS access key ID & secret access key values](https://serverless.com/framework/docs/providers/aws/guide/credentials#amazon-web-services)

Verify your code with

    ./gradlew check

Deploy your code with

    ./gradlew deploy -Pstage=my-dev-stack

`my-dev-stack` should be unique to you. Using your username is a good idea.
The output of the deploy command will indicate the URL to which the code was deployed.

Remove your deployment with

    ./gradlew removeDeployment -Pstage=my-dev-stack
