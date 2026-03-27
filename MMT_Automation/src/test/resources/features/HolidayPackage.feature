# ============================================================
# Feature  : MakeMyTrip – Holiday Packages Module
# Module   : Holiday Packages Module
# Author   : Biswayan Nath
# Version  : 1.0
# Ref      : REQ_MakeMyTrip_01 to REQ_MakeMyTrip_03
# ============================================================

@Hotels
Feature: MakeMyTrip Holiday Packages Functionality

  Background:
    Given the user is on the MakeMyTrip Holiday Packges homepage


  # ============================================================
  # TS_MakeMyTrip_HolidayPackages_01 – Search Functionality
  # ============================================================

  @TS01 @Positive @TC_MakeMyTrip_01
  Scenario: Valid search with correct cities returns results
    When the user selects from city "Kolkata", to city "Kerala", valid departure date and room and guest details
    And the user clicks the Search button
    And the user clicks the First result from Search
    Then the holiday package result locations should match "Kolkata" to "Kerala"

  @TS01 @Negative @TC_MakeMyTrip_02
  Scenario: Invalid destination shows no results
    When the user selects from city "Kolkata", to city "iaooxoias", valid departure date and room and guest details
    Then the data "iaooxoias" is not accepted

  @TS01 @Positive @TC_MakeMyTrip_03
  Scenario: Search with filters returns filtered results
    When the user selects from city "Kolkata", to city "Kerala", valid departure date and room and guest details
    And flight, price and star rating filters are added
    And the user clicks the Search button
    Then filtered holiday packages should be displayed


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
