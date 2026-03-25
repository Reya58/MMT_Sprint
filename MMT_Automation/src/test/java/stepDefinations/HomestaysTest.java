package stepDefinations;

import io.cucumber.java.en.*;

public class HomestaysTest {

    // ---- Homepage and Navigation ----
    @Given("the user is on the MakeMyTrip homepage")
    public void open_homepage() {
        // Selenium code to open MakeMyTrip homepage
    }

    @Given("the user navigates to Villas and Homestays section")
    public void navigate_to_villas_and_homestays() {
        // Selenium code to go to Villas/Homestays section
    }

    // ---- Search Steps ----
    @When("the user enters {string} in the destination field")
    public void enter_destination(String destination) {
        // Enter destination in search field
    }

    @When("the user selects check-in date {string}")
    public void select_check_in_date(String checkIn) {
        // Select check-in date
    }

    @When("the user selects check-out date {string}")
    public void select_check_out_date(String checkOut) {
        // Select check-out date
    }

    @When("the user sets guests to {string} adults")
    public void set_guests(String adults) {
        // Set number of adults
    }

    @When("the user clicks the Search button")
    public void click_search_button() {
        // Click the search button
    }

    @Then("the villa listing page is displayed")
    public void villa_listing_page_displayed() {
        // Verify villa listing page loaded
    }

    @Then("each villa card shows name, price, rating and location")
    public void verify_villa_cards() {
        // Verify villa cards have name, price, rating, location
    }

    @Then("a no results message is displayed")
    public void no_results_message() {
        // Verify no results message
    }

    @Then("the page remains stable")
    public void page_remains_stable() {
        // Verify page stability
    }

    // ---- Sorting ----
    @When("the user sorts results by {string}")
    public void sort_results(String order) {
        if(order.equalsIgnoreCase("ascending price")) {
            // Sort ascending
        } else if(order.equalsIgnoreCase("descending price")) {
            // Sort descending
        }
    }

    @Then("villa listings are sorted in {string} price order")
    public void verify_sorted_listings(String order) {
        // Verify sorting order
    }

    // ---- Property Selection ----
    @When("the user searches villas for {string}")
    public void search_villas(String query) {
        // Enter search query
    }

    @When("the user selects a villa from results")
    public void select_villa() {
        // Select villa from results
    }

    @Then("the property details page is displayed")
    public void property_details_page_displayed() {
        // Verify property details page
    }

    @Then("villa information is visible")
    public void villa_info_visible() {
        // Verify villa info visible
    }

    // ---- Wishlist ----
    @When("the user adds the villa to wishlist")
    public void add_to_wishlist() {
        // Add villa to wishlist
    }

    @When("the user navigates to wishlist page")
    public void go_to_wishlist() {
        // Navigate to wishlist page
    }

    @When("the user removes the villa from wishlist")
    public void remove_from_wishlist() {
        // Remove villa from wishlist
    }

    @Then("the villa is removed successfully")
    public void verify_removed_from_wishlist() {
        // Verify villa removed
    }

    @Then("the villa is saved successfully in wishlist")
    public void verify_saved_in_wishlist() {
        // Verify villa saved
    }

    // ---- Booking ----
    @When("the user proceeds to booking page")
    public void proceed_to_booking() {
        // Navigate to booking page
    }

    @When("the user enters coupon code {string}")
    public void enter_coupon(String code) {
        // Enter coupon code
    }

    @When("the user applies the coupon")
    public void apply_coupon() {
        // Apply coupon
    }

    @Then("discount is applied successfully")
    public void verify_discount_applied() {
        // Verify discount
    }

    // ---- Reviews ----
    @When("the user navigates to the reviews section")
    public void go_to_reviews() {
        // Navigate to reviews section
    }

    @Then("guest reviews and ratings are displayed")
    public void verify_reviews_displayed() {
        // Verify guest reviews and ratings
    }

    // ---- Homepage Navigation ----
    @When("the user clicks on home icon")
    public void click_home_icon() {
        // Click on home icon
    }

    @Then("the user is redirected to homepage")
    public void verify_redirect_homepage() {
        // Verify redirected to homepage
    }

    // ---- Filters ----
    @When("the user applies property type filter {string}")
    public void apply_property_type_filter(String type) {
        // Apply property type filter
    }

    @When("the user applies amenities filter {string}")
    public void apply_amenities_filter(String amenities) {
        // Apply amenities filter
    }

    @Then("all displayed properties match selected filters")
    public void verify_filters_applied() {
        // Verify filters applied correctly
    }
    @Then("villa listings are sorted in ascending price order")
    public void villa_listings_are_sorted_in_ascending_price_order() {
        
    }


    @Then("villa listings are sorted in descending price order")
    public void villa_listings_are_sorted_in_descending_price_order() {
            }

}