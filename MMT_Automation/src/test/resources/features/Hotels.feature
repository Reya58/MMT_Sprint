# ============================================================
# Feature  : MakeMyTrip – Hotels Module
# Module   : Hotel Search, Date Validation, Filters & Detail Page
# Author   : Patatri Acharya
# Version  : 1.0
# Ref      : REQ_MMT_Hotels_01 to REQ_MMT_Hotels_06
# ============================================================

@Hotels
Feature: MakeMyTrip Hotel Search and Filtering

  Background:
    Given the user is on the MakeMyTrip Hotels homepage


  # ============================================================
  # TS_MMT_Hotels_01 – Basic Hotel Search
  # ============================================================

  @TS01 @Positive @TC_MMT_Hotels_01_01
  Scenario: Valid city search with dates returns hotel listing
    When the user enters "TC_MMT_Hotels_01_01" 
    Then the hotel listing page is displayed

  @TS01 @Positive @TC_MMT_Hotels_01_02
  Scenario: Landmark name search returns nearby hotels with location tag
    When the user enters "TC_MMT_Hotels_01_02" 
    Then the hotel listing page is displayed
    And at least one hotel card displays the correct location tag for "Rabindra Sarovar"

  @TS01 @Negative @TC_MMT_Hotels_01_03
  Scenario: Non-existent city name shows no-results message
    When the user enters "TC_MMT_Hotels_01_03"
    Then displays a no hotels found message
    

  @TS01 @Negative @TC_MMT_Hotels_01_04
  Scenario: Special characters in destination field are sanitised gracefully
    When the user enters "TC_MMT_Hotels_01_04"
    Then the destination field contains only "Mumbai"


  # ============================================================
  # TS_MMT_Hotels_03 – Star Rating Filter
  # ============================================================

  @TS03 @Positive @TC_MMT_Hotels_03_01
  Scenario: Applying 5-star filter shows only 5-star hotels
    When the user enters "TC_MMT_Hotels_03_01"
    Then at least one displayed hotel cards show a 5 star rating
    And the result count updates to reflect only 5 star properties



  # ============================================================
  # TS_MMT_Hotels_04 – Price Range Filter
  # ============================================================

  @TS04 @Positive @TC_MMT_Hotels_04_01
  Scenario: Valid price range filter shows only hotels within the range
    When the user enters "TC_MMT_Hotels_04_01" 
    And the user sets the minimum price to "1500"
    And the user sets the maximum price to "2500"
    Then all displayed hotel cards show a per-night rate between "1500" and "2500"

  @TS04 @Negative @TC_MMT_Hotels_04_02
  Scenario: Unrealistically low maximum price shows no available hotels with a proper message
    When the user enters "TC_MMT_Hotels_04_02" 
    And the user sets the minimum price to "0"
    And the user sets the maximum price to "10"
    Then no hotel cards are displayed
    And an 'Adjust price filter' message is shown


  
