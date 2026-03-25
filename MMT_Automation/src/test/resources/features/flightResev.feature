Feature: Flight search

  Scenario Outline: User can search for flights with valid details
    Given the user is on the flight search page
    When the user searches for flights from "<from_city>" to "<to_city>" on "<travel_date>"
    Then a list of available flights is displayed

    Examples:
      | from_city | to_city   | travel_date     |
      | New Delhi | Bengaluru | Sun May 24 2026 |
      | New Delhi | Bengaluru | Mon May 25 2026 |
