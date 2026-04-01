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

  @TS01 @Positive @TC_MMT_Villas_01_01 @Excel
  Scenario: Valid destination search shows villa listings
    When the user performs villa search for TC_MMT_Villas_01_01
    Then the villa listing validation is done

  @TS01 @Negative @TC_MMT_Villas_01_02 @Excel
  Scenario: Invalid destination shows no results
    When the user performs villa search for TC_MMT_Villas_01_02
    Then no location suggestions should be displayed


  # ============================================================
  # TS_MMT_Villas_02 – Apply Filters
  # ============================================================

  @TS02 @Positive @TC_MMT_Villas_02_01 @Excel
  Scenario: Apply property type and amenities filters
  When the user performs villa search for TC_MMT_Villas_02_01
    Then the villa listing validation is done
    When the user applies filters for TC_MMT_Villas_02_01
    Then all displayed properties match selected filters


  # ============================================================
  # TS_MMT_Villas_03 – Sorting Results
  # ============================================================

  @TS03 @Positive @TC_MMT_Villas_03_01 @Excel
  Scenario: Sort villas by price low to high
  When the user performs villa search for TC_MMT_Villas_03_01
    Then the villa listing validation is done
    When the user sorts villas for TC_MMT_Villas_03_01
    Then villa listings are sorted correctly

  @TS03 @Positive @TC_MMT_Villas_03_02 @Excel
  Scenario: Sort villas by price high to low
  When the user performs villa search for TC_MMT_Villas_03_02
    Then the villa listing validation is done
    When the user sorts villas for TC_MMT_Villas_03_02
    Then villa listings are sorted correctly


  @TS04 @Positive @TC_MMT_Villas_04_01 @Excel
  Scenario: View villa details page
    When the user performs villa search for TC_MMT_Villas_04_01
    Then the villa listing validation is done
    When the user opens villa details for TC_MMT_Villas_04_01
    Then villa details are validated

  @TS04 @Positive @TC_MMT_Villas_04_02 @Excel
  Scenario: View guest reviews on property page
    When the user performs villa search for TC_MMT_Villas_04_02
    Then the villa listing validation is done
    When the user opens villa details for TC_MMT_Villas_04_02
    Then guest reviews are validated
    

