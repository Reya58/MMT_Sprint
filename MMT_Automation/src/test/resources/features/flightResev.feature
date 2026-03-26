# =============================================================================
# Feature: Flight Search - MakeMyTrip
# Module   : One-Way Flight Search
# Author   : Shadab
# Version  : 1.0
# Created  : 2026-03-26
# Tags     : @flight @search @regression @smoke
# =============================================================================
@flight @search
Feature: Flight Search on MakeMyTrip
  As a user of MakeMyTrip
  I want to search for flights between cities
  So that I can view available flights and their details

  Background:
    Given the user is on the flight search page

  # ---------------------------------------------------------------------------
  # TC-01 : Source / Destination Dropdown Suggestions
  # ---------------------------------------------------------------------------
  @autocomplete @from @positive
  Scenario Outline: Show suggestions for valid inputs in From field
    When the user enters "<input>" in the From field
    Then matching city suggestions should be displayed
    And the suggestions list should not be empty

    Examples:
      | input |
      | Del   |
      | BOM   |
      | Kol   |

  @autocomplete @from @negative
  Scenario Outline: Show fallback UI for invalid inputs in From field
    When the user enters "<input>" in the From field
    Then no city suggestions should be displayed
    And the recent searches section should be visible

    Examples:
      | input     |
      | xyz123abc |
      | 123456    |
      | @@@###    |

  @autocomplete @to @positive
  Scenario Outline: Show suggestions for valid inputs in To field
    When the user enters "<input>" in the To field
    Then matching city suggestions should be displayed
    And the suggestions list should not be empty

    Examples:
      | input |
      | CCU   |
      | DEL   |
      | MUM   |

  @autocomplete @to @negative
  Scenario Outline: Show fallback UI for invalid inputs in To field
    When the user enters "<input>" in the To field
    Then no city suggestions should be displayed
    And the recent searches section should be visible

    Examples:
      | input     |
      | 000000    |
      | ab!@     |
      | invalidxx |

  # ---------------------------------------------------------------------------
  # TC-02 : Same Source and Destination Validation
  # ---------------------------------------------------------------------------
  @validation @negative
  Scenario Outline: Prevent search when origin and destination are identical
    When the user selects "<city>" as the From
    And the user selects "<city>" as the To
    And the user attempts to search for flights
    Then a validation error indicating identical origin and destination should be displayed
    And the flight search should not be executed

    Examples:
      | city      |
      | New Delhi |
      | Mumbai    |
      | Kolkata   |

  # ---------------------------------------------------------------------------
  # TC-03 : Departure Date Calendar — Past Dates Are Disabled
  # ---------------------------------------------------------------------------
  @calendar @datepicker @negative
  Scenario: Past dates cannot be selected in departure calendar
    When the user opens the departure date picker
    Then the user should not be able to select a past date

  @calendar @datepicker @positive
  Scenario Outline: User can select a future departure date
    When the user opens the departure date picker
    And the user selects a date "<days_from_today>" days from today
    Then the selected date should be displayed in the departure date field

    Examples:
      | days_from_today |
      | 30              |
      | 1               |
      | 5               |

  # ---------------------------------------------------------------------------
  # TC-04 : Flight Result Card Displays Required Information
  # ---------------------------------------------------------------------------
  @results @positive @ui @regression
  Scenario Outline: Flight results displays for valid routes
    Given the user searches flights from "<from>" to "<to>"
    And user set flight date to be "<Date>"
    When the flight search is executed
    Then avaliable flights are shown to user

    Examples:
      | from    | to        | Date            |
      | Delhi   | Mumbai    | Sun May 24 2026 |
      | Kolkata | Bengaluru | Tue May 26 2026 |
