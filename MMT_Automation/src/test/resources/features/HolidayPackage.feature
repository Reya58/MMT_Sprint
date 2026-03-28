# ============================================================
# Feature  : MakeMyTrip – Holiday Packages Module
# Module   : Holiday Packages Module
# Author   : Biswayan Nath
# Version  : 1.0
# Ref      : REQ_MakeMyTrip_01 to REQ_MakeMyTrip_03
# ============================================================

@HolidayPackage
Feature: MakeMyTrip Holiday Packages Functionality

  Background:
    Given the user is on the MakeMyTrip Holiday Packges homepage


  # ============================================================
  # TS_MakeMyTrip_HolidayPackages_01 – Search Functionality
  # ============================================================

  @TS01 @Positive
  Scenario Outline: Valid search with different cities
    When the user selects from city "<fromCity>", to city "<toCity>", valid departure date and room and guest details
    And the user clicks the Search button
    And the user clicks the First result from Search
    Then the holiday package result locations should match "<fromCity>" to "<toCity>"

    Examples:
      | fromCity | toCity  |
      | Kolkata  | Kerala  |


  @TS01 @Negative
  Scenario Outline: Invalid destination validation
    When the user selects valid from city "<fromCity>", invalid to city "<toCity>"
    Then the data "<toCity>" is not accepted

    Examples:
      | fromCity | toCity     |
      | Kolkata  | iaooxoias  |


  @TS01 @Positive
  Scenario Outline: Search with filters
    When the user selects from city "<fromCity>", to city "<toCity>", valid departure date and room and guest details
    And flight, price and star rating filters are added
    And the user clicks the Search button
    Then filtered holiday packages should be displayed

    Examples:
      | fromCity | toCity |
      | Kolkata  | Kerala |


  # ============================================================
  # TS_MakeMyTrip_HolidayPackages_02 – Visa Free Functionality
  # ============================================================

  @TS02 @Positive @TC_MakeMyTrip_04
  Scenario: Navigating Visa Free Packages opens correct page
    When the user clicks on Visa Free Packages
    Then the Visa Free Packages section should be displayed

  @TS02 @Positive @TC_MakeMyTrip_05
  Scenario: Selecting a visa free destination opens details
    When the user clicks on Visa Free Packages
    And the user selects a destination
    And the user chooses a package
    Then the correct holiday packages page should open

  @TS02 @Positive @TC_MakeMyTrip_06
  Scenario: Visa free packages section loads with proper UI
    When the user clicks on Visa Free Packages
    And the user selects a destination
    And the user chooses a package
    Then the holiday packages page should open with correct UI


  # ============================================================
  # TS_MakeMyTrip_HolidayPackages_03 – Disney Cruise Functionality
  # ============================================================

  @TS03 @Positive @TC_MakeMyTrip_07
  Scenario: Navigating Disney Cruise section works correctly
    When the user clicks on Disney Cruise
    Then the Disney Cruise section should be displayed

  @TS03 @Positive @TC_MakeMyTrip_08
  Scenario: View All in Disney Cruise opens full listing
    When the user clicks on Disney Cruise
    And the user clicks View All
    Then the holiday packages page should open in a new tab

  @TS03 @Positive @TC_MakeMyTrip_09
  Scenario: Disney cruise package opens correctly
    When the user clicks on Disney Cruise
    And the user clicks View All
    And the user chooses a cruise package
    Then the correct cruise packages page should open
