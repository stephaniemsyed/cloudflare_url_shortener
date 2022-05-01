# Cloudflare API Interview Coding Project 
## Overview & Expectations
This project is part of the hiring process for the API team within Cloudflare to use for candidate evaluation and as a foundation for follow-up technical interviews. Read this document completely and create a git repo that is publicly accessible in order to contain your work. Once your project is done, push your work to the repo and send us the link to it, so we can check it out. 

Given the project proposal below, use your repo in any way you see fit to accomplish the goal stated. Please try to avoid pushing one massive initial commit so that discussion can be had around approaches or implementations that worked or didn't work, or other adjustments made along the way. 


Choose whichever languages and frameworks you would like for this exercise, with any folder/file hierarchy that makes sense to you. Please note that we are focussed on your code, not the boilerplate or setup work you've done for the project. Implementing in PHP or GO will get some brownie point.

NOTE: We expect a reply with a link to your project in seven calendar days from us sending this proposal to you. If you have any questions about the project reach out to us immediately, we're excited to help! We'll reach out in two to three days regardless to check in and see how you're getting along.
 
## Proposal

You've been asked to make an internal service for shortening URLs that anyone in the company can use. You can implement it in any way you see fit, including whatever backend functionality that you think makes sense. Please try to make it both end user and developer friendly. Please include a README with documentation on how to build, and run and test the system. Clearly state all assumptions and design decisions in the README. 

A short URL: 
* Has one long URL 
* This URL shortener should have a well-defined API for URLs created, including analytics of usage.
* No duplicate URLs are allowed to be created.
* Short links can expire at a future time or can live forever.

Your solution must support: 
* Generating a short url from a long url 
* Redirecting a short url to a long url. 
* List the number of times a short url has been accessed in the last 24 hours, past week, and all time. 
* Data persistence ( must survive computer restarts) 
* Metrics and/or logging: Implement metrics or logging for the purposes of troubleshooting and alerting. This is optional.
* Short links can be deleted

Project Requirements:
* This project should be able to be runnable locally with  some simple instructions
* This project's documentation should include build and deploy instruction
* Tests should be provided and able to be executed locally or within a test environment.
