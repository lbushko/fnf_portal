Selenium Web UI automated tests developed to test the basic F&F portal functionality. In order to run the tests, do the following:
1. Clone the repository.
2. Download the latest release of Chrome Driver compliant with your OS here: https://sites.google.com/a/chromium.org/chromedriver/downloads.
3. Place the Chrome Driver executable into the project folder (same level with pom file and /src).
4. Place config.properties file into the project folder (same level with pom file and /src) in the following format:
validUsername=*
validPassword=*
validCustomerId=*
replacing asterisks with valid data.
5. Import project into the IDE (Intellij IDEA recommended).
6. From there, you should be able to run the project.

*If not running with latest version of Chrome Driver, try switch to v. 2.31, as it is one which was used during tests creation.
**For now, tests are configured to run against https://admin-dev.flightfx.com/fnfportal environment. Further on, more environments will be added as well as Jenkins job will be configured.