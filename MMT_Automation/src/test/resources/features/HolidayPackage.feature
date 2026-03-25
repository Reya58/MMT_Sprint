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
  Scenario:  Invalid destination shows no results
    When the user selects from city "Kolkata", to city "iaooxoias", valid departure date and room and guest details
    Then the data is not accepted

  @TS01 @Positive @TC_MakeMyTrip_03
  Scenario: Search with filters returns filtered results
    When the user selects from city "Kolkata", to city "Kerala", valid departure date and room and guest details
    And flight, price and star rating filters are added
    And the user clicks the Search button
    Then filtered holiday packages should be displayed


  