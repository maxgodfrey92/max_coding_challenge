# Max Godfrey Coding Challenge in Java

## Introduction

This is a test automation framework using JUnit, RestAssured, and Selenium.

The tests themselves deal with the automationpractice.com site, as well as the Github Gist API.

Firefox and Chrome are currently in use for the UI Tests, but further browsers could be added as necessary.

## Setup

The framework uses Maven for dependency management, and a simple properties file for config changes such as browser to use, github token, etc.

### Config

Please copy the config.properties.sample file and rename to config.properties for local testing.

There are also some config items in that file to change:
 - BROWSER: The browser you want to use, between "chrome" and "firefox" currently
 - CHROME_DRIVER_PATH: The path to a chromedriver.exe file stored locally. Only use if BROWSER set to "chrome"
 - FIREFOX_DRIVER_PATH: The path to a geckodriver.exe file stored locally. Only use if BROWSER set to "firefox"
 - UI_URL: The URL to use for UI testing, pre-filled to automationpratice.com
 - API_URL: The URL to use for API testing, pre-filled to Github Gist API
 - GITHUB_TOKEN: Personal Github token that can be used for API use
 - GITHUB_USERNAME: Username of the Github user linked with the Github token

### Maven

Maven is used for dependency management, to remove the need for manually installing JARs.

To install dependencies in command prompt:
- Open a command prompt
- Navigate to "max_coding_challenge" directory
- Run the following command: `maven clean install`

Or through an IDE:
- Open the project in IDE of your choosing
- Choose to build the project

### Running Tests

To run in command prompt:
- Open a command prompt
- Navigate to "max_coding_challenge" directory
- Run the following command: `maven clean test`

Or through an IDE:
- Open the project in IDE of your choosing
- Open one of the test files
- Choose to run as a JUnit test
