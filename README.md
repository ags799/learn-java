# Learn Java
[![Travis](https://travis-ci.org/ags799/learn-java.svg?branch=master)](https://travis-ci.org/ags799/learn-java)

We learn Java by implementing answers to common interview questions.

## Development

Prepare your environment by

- installing [serverless](http://serverless.com)
- setting AWS access key ID & secret access key values

Verify your code with

    ./gradlew check

Deploy your code with

    ./gradlew deploy

The output of the deploy command will indicate the URL to which the code was deployed.

To run integration tests, first deploy your code to some development stack

    ./gradlew deploy -Pstage=my-dev-stack

This command will print a URL for the new stack. Run the tests with the URL for that stack in the
`LEARN_JAVA_URL` environment variable.

    LEARN_JAVA_URL=http://something.com/api ./gradlew integrationTest

Remove your deployment with

    ./gradlew removeDeployment -Pstage=my-dev-stack
