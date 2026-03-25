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
    And the user is logged in with valid credentials


  # ============================================================
  # TS_MMT_Hotels_01 – Basic Hotel Search
  # ============================================================

  @TS01 @Positive @TC_MMT_Hotels_01_01
  Scenario: Valid city search with dates returns hotel listing
    When the user enters "Mumbai" in the destination field
    And the user selects check-in date "01 Apr 2026"
    And the user selects check-out date "03 Apr 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    Then the hotel listing page is displayed
    And each hotel card shows name, price per night, rating and location

  @TS01 @Positive @TC_MMT_Hotels_01_02
  Scenario: Landmark name search returns nearby hotels with location tag
    When the user enters "Rabindra Sadan" in the destination field
    And the user selects check-in date "15 Apr 2026"
    And the user selects check-out date "17 Apr 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    Then the hotel listing page is displayed
    And each hotel card displays the correct location tag for "Rabindra Sadan"

  @TS01 @Negative @TC_MMT_Hotels_01_03
  Scenario: Non-existent city name shows no-results message
    When the user enters "XyzCityAbc123" in the destination field
    Then no matching suggestion appears in the auto-complete dropdown
    And clicking the search icon displays a no hotels found message
    And the page remains stable without crash or blank screen

  @TS01 @Negative @TC_MMT_Hotels_01_04
  Scenario: Special characters in destination field are sanitised gracefully
    When the user enters "Mumbai!!!" in the destination field
    Then the application removes the special characters
    And the destination field contains only "Mumbai"


  # ============================================================
  # TS_MMT_Hotels_02 – Valid Date Selection
  # ============================================================

  @TS02 @Positive @TC_MMT_Hotels_02_02
  Scenario: Valid future date range is accepted and search activates
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    Then the Search button is enabled
    When the user clicks the Search button
    Then the hotel listing page is displayed for "Goa"


  # ============================================================
  # TS_MMT_Hotels_03 – Star Rating Filter
  # ============================================================

  @TS03 @Positive @TC_MMT_Hotels_03_01
  Scenario: Applying 5-star filter shows only 5-star hotels
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    And the user applies the star rating filter for "5" stars
    Then all displayed hotel cards show a "5" star rating
    And the result count updates to reflect only 5-star properties

  @TS03 @Positive @TC_MMT_Hotels_03_02
  Scenario: Multiple star ratings filter returns combined results
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    And the user applies the star rating filter for "5" stars
    And the user applies the star rating filter for "4" stars
    Then all displayed hotel cards show either "4" or "5" star ratings
    And the result count reflects the combined filtered set


  # ============================================================
  # TS_MMT_Hotels_04 – Price Range Filter
  # ============================================================

  @TS04 @Positive @TC_MMT_Hotels_04_01
  Scenario: Valid price range filter shows only hotels within the range
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    And the user sets the minimum price to "1500"
    And the user sets the maximum price to "2500"
    Then all displayed hotel cards show a per-night rate between "1500" and "2500"

  @TS04 @Negative @TC_MMT_Hotels_04_02
  Scenario: Unrealistically low maximum price shows no available hotels
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    And the user sets the minimum price to "0"
    And the user sets the maximum price to "10"
    Then no hotel cards are displayed
    And a sold out or no results message is shown


  # ============================================================
  # TS_MMT_Hotels_05 – Sort Prices
  # ============================================================

  @TS05 @Positive @TC_MMT_Hotels_05_01
  Scenario: Price Low to High sort orders results in ascending price order
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    And the user sorts results by "Price: Low to High"
    Then hotel cards are ordered in ascending price sequence
    And the first card shows the lowest price among all results

  @TS05 @Positive @TC_MMT_Hotels_05_02
  Scenario: Price High to Low sort orders results in descending price order
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets rooms and guests to "1" room and "2" adults
    And the user clicks the Search button
    And the user sorts results by "Price: High to Low"
    Then hotel cards are ordered in descending price sequence
    And the first card shows the highest price among all results


  
