# Weather API Automation

This repository was created to showcase the author's test automation skills.

This repo consist of two parts

- Postman Collection
- Java automation solution using.

## Requirements:

- Java(JDK 11 or Higher)

## System under test

The SUT is _Foreca_.

_Foreca Weather API_ combines accurate weather forecasts and easy-to-use tools for visualising the information.
Technical API documentation is available at http://developer.foreca.com/.
After successful registration the details regarding authorization will be sent to your registration email address.
These details should consist of a username and a password.

## Java Automation Framework

### Prerequisites

To be able to run the automation, the authorization details need to be provided to the solution.
This can be done by entering the username and password into the data.json file in the base of the repository.

### Running

The java framework uses gradle as the main building tool.

On a Windows operating system, navigate to the root of the project in your file system via command line.
Then run :
`gradlew clean test`

On Mac machine you can run:
`./gradlew clean test`

### Reporting

A test report can be generated of the last completed test run. Running command:
`gradlew allureReport` or `./gradlew allureReport` respectively, will create the report.

The report will be saved into a generated folder named _test-output_. The
index.html is the main entry-point to the report which is best viewed in Chrome browser.

## Postman

The Postman collection utilizes environment variables to keep track of all the information used in the collection.
It also requires the username and password received from Foreca to Authenticate all API calls.
The place of entry can be found in the environment variables section of postman after importing. The fields requiring
user data entry have been marked accordingly.

The two files required to use postman can be found in the postman folder at the base of this repository.