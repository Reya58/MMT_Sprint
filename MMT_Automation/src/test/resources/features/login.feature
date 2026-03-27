Feature: Login Feature

Scenario Outline: Successful login with valid credentials

  Given the user is on the login page
  When the user logs in with "<email>" and "<password>"
  Then the user should be redirected to the dashboard

Examples:
  | email                | password     |
  | bredmi250@gmail.com | Shadab@2116 |