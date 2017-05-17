.PHONY: integrationTest

integrationTest:
	mkdir -p src/integrationTest/resources
	serverless deploy > src/integrationTest/resources/most-recent-deployment-output.txt
	./gradlew integrationTest
