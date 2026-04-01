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

  @TC_01 @results @positive @ui @regression
  Scenario Outline: User searches and books a flight with valid inputs
    Given the user searches for flights from "<from>" to "<to>"
    And the user selects departure date "<date>"
    When the user executes the flight search
    Then available flights are displayed
    When the user selects a flight
    And enters valid traveller details "<name>", "<lname>", "<gender>", "<mobile>", "<email>", "<state>"
    And completes the booking
    Then the booking is successful

    Examples:
      | from    | to        | date            | name   | lname  | gender | mobile     | email            | state       |
      | Delhi   | Mumbai    | Sun May 24 2026 | Rahul  | Kumar  | Male   | 9876543210 | rahul@gmail.com  | Delhi       |
      | Kolkata | Bengaluru | Tue May 26 2026 | Ananya | Kumari | Female | 9123456780 | ananya@gmail.com | West Bengal |

  @TC_02
  Scenario Outline: User books a round-trip flight
    Given the user searches for round-trip flights from "<from>" to "<to>"
    And selects departure date "<dep_date>" and return date "<ret_date>"
    When the user executes the flight search
    Then onward and return flights are displayed
    When the user selects flights for both journeys
    And enters valid traveller details
    And completes the booking
    Then the booking is successful

    Examples:
      | from  | to  | dep_date        | ret_date        |
      | Delhi | Goa | Sun May 24 2026 | Tue May 26 2026 |

  @TC_03
  Scenario: User books flight with add-ons
    Given the user searches for flights
    When the user selects a flight
    And adds meals and extra baggage
    And enters valid traveller details "<name>", "<lname>", "<gender>", "<mobile>", "<email>", "<state>"
    And completes the booking
    Then the booking is successful

    Examples:
      | name   | lname  | gender | mobile     | email            | state       |
      | Rahul  | Kumar  | Male   | 9876543210 | rahul@gmail.com  | Delhi       |
      | Ananya | Kumari | Female | 9123456780 | ananya@gmail.com | West Bengal |

  @TC_04
  Scenario Outline: User books the cheapest flight
    Given the user searches for flights from "<from>" to "<to>"
    And the user selects departure date "<date>"
    When the user executes the flight search
    Then available flights are displayed
    And the user selects the cheapest available flight
    And enters valid traveller details
    And completes the booking
    Then the booking is successful

    Examples:
      | from    | to        | date            |
      | Delhi   | Mumbai    | Sun May 24 2026 |
      | Kolkata | Bengaluru | Tue May 26 2026 |

  @TC_05
  Scenario Outline: User books a flight of a specific airline
    Given the user searches for flights from "<from>" to "<to>"
    And the user selects departure date "<date>"
    When the user executes the flight search
    And available flights are displayed
    And the user filters flights by airline "<airline>"
    And selects a flight after filter
    Then the airline is "<airline>"
    When enters valid traveller details
    And completes the booking
    Then the booking is successful

    Examples:
      | from  | to     | airline | date            |
      | Delhi | Mumbai | IndiGo  | Sun May 24 2026 |

  @TC_06
  Scenario: User books flight from either of two airlines
    Given the user searches for flights
    When the user filters flights by airlines "<airline1>" and "<airline2>"
    And selects a flight after filter
    Then selected flight belongs to "<airline1>","<airline2>" one of the chosen airlines
    And enters valid traveller details
    And completes the booking
    Then the booking is successful

    Examples:
      | airline1  | airline2          |
      | IndiGo    | Air India         |
      | Air India | Air India Express |

  @TC_07
  Scenario Outline: User checks flight status by flight number
    Given the user navigates to flight status page
    And is on Flight Status By Flight tab
    When the user enters flight number "<flight_no>"
    And submits the request
    Then the current flight status is displayed

    Examples:
      | flight_no |
      | AI101     |
      | 6E202     |

  @TC_08
  Scenario Outline: User checks flight status by route
    Given the user navigates to flight status page
    And is on Flight Status By Route tab
    When the user enters source "<from>" and destination "<to>"
    And submits the request
    Then all flights for "<from>","<to>" the route are displayed with status

    Examples:
      | from    | to        |
      | Delhi   | Mumbai    |
      | Kolkata | Bengaluru |

  @TC_09
  Scenario Outline: User checks flight status by airport
    Given the user navigates to flight status page
    And is on Flight Status By Airport
    When the user selects airport "<airport>"
    And submits the request
    Then all arrivals and departures of "<airport>" are displayed with status

    Examples:
      | airport |
      | Delhi   |
      | Kolkata |
