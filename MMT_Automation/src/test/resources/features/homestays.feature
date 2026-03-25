# ============================================================
# Feature  : MakeMyTrip – Villas & Homestays Module
# Module   : Search, Filters, Sorting, Wishlist & Navigation
# Author   : Your Name
# Version  : 1.0
# Ref      : REQ_MMT_Villas_01 to REQ_MMT_Villas_06
# ============================================================

@Villas
Feature: MakeMyTrip Villas and Homestays Functionality

  Background:
    Given the user is on the MakeMyTrip homepage
    And the user navigates to Villas and Homestays section


  # ============================================================
  # TS_MMT_Villas_01 – Basic Villa Search
  # ============================================================

  @TS01 @Positive @TC_MMT_Villas_01_01
  Scenario: Valid destination search shows villa listings
    When the user enters "Goa" in the destination field
    And the user selects check-in date "01 Apr 2026"
    And the user selects check-out date "03 Apr 2026"
    And the user sets guests to "2" adults
    And the user clicks the Search button
    Then the villa listing page is displayed
    And each villa card shows name, price, rating and location

  @TS01 @Negative @TC_MMT_Villas_01_02
  Scenario: Invalid destination shows no results
    When the user enters "XyzInvalidPlace123" in the destination field
    And the user clicks the Search button
    Then a no results message is displayed
    And the page remains stable


  # ============================================================
  # TS_MMT_Villas_02 – Apply Filters
  # ============================================================

  @TS02 @Positive @TC_MMT_Villas_02_01
  Scenario: Apply property type and amenities filters
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets guests to "2" adults
    And the user clicks the Search button
    And the user applies property type filter "Villa"
    And the user applies amenities filter "WiFi"
    Then all displayed properties match selected filters


  # ============================================================
  # TS_MMT_Villas_03 – Sorting Results
  # ============================================================

  @TS03 @Positive @TC_MMT_Villas_03_01
  Scenario: Sort villas by price low to high
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets guests to "2" adults
    And the user clicks the Search button
    And the user sorts results by "Price Low to High"
    Then villa listings are sorted in ascending price order

  @TS03 @Positive @TC_MMT_Villas_03_02
  Scenario: Sort villas by price high to low
    When the user enters "Goa" in the destination field
    And the user selects check-in date "05 May 2026"
    And the user selects check-out date "08 May 2026"
    And the user sets guests to "2" adults
    And the user clicks the Search button
    And the user sorts results by "Price High to Low"
    Then villa listings are sorted in descending price order


  # ============================================================
  # TS_MMT_Villas_04 – Property Details & Reviews
  # ============================================================

  @TS04 @Positive @TC_MMT_Villas_04_01
  Scenario: View villa details page
    When the user searches villas for "Goa"
    And the user selects a villa from results
    Then the property details page is displayed
    And villa information is visible

  @TS04 @Positive @TC_MMT_Villas_04_02
  Scenario: View guest reviews on property page
    When the user searches villas for "Goa"
    And the user selects a villa from results
    And the user navigates to the reviews section
    Then guest reviews and ratings are displayed


  # ============================================================
  # TS_MMT_Villas_05 – Wishlist Functionality
  # ============================================================

  @TS05 @Positive @TC_MMT_Villas_05_01
  Scenario: Add villa to wishlist
    When the user searches villas for "Goa"
    And the user selects a villa from results
    And the user adds the villa to wishlist
    Then the villa is saved successfully in wishlist

  @TS05 @Positive @TC_MMT_Villas_05_02
  Scenario: Remove villa from wishlist
    When the user searches villas for "Goa"
    And the user selects a villa from results
    And the user adds the villa to wishlist
    And the user navigates to wishlist page
    And the user removes the villa from wishlist
    Then the villa is removed successfully


  # ============================================================
  # TS_MMT_Villas_06 – Navigation
  # ============================================================

  @TS06 @Positive @TC_MMT_Villas_06_01
  Scenario: Navigate back to homepage from property page
    When the user searches villas for "Goa"
    And the user selects a villa from results
    And the user clicks on home icon
    Then the user is redirected to homepage


  # ============================================================
  # TS_MMT_Villas_07 – Coupon Application
  # ============================================================

  @TS07 @Positive @TC_MMT_Villas_07_01
  Scenario: Apply coupon on booking page
    When the user searches villas for "Goa"
    And the user selects a villa from results
    And the user proceeds to booking page
    And the user enters coupon code "DISCOUNT20"
    And the user applies the coupon
    Then discount is applied successfully