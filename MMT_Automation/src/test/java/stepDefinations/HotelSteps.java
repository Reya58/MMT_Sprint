////STEP DEFINITION FILE OF MMT HOTELS
//
//package stepDefinations;
//
//import java.time.Duration;
//import java.util.Map;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//
//import utils.Hotel_ExcelUtil;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.testng.Assert;   
//import pages.HotelListingsPage;
//import pages.HotelSearchPage;
//
//public class HotelSteps {
//	
////	Scenarios:
////		  1) Valid city search with dates returns hotel listing # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:22
////		  2) Landmark name search returns nearby hotels with location tag # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:32
////		  3) Non-existent city name shows no-results message # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:42
////		  4) Special characters in destination field are sanitised gracefully # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:49
////		  5) Valid future date range is accepted and search activates # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:60
////		  6) Applying 5-star filter shows only 5-star hotels # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:75
////		  7) Multiple star ratings filter returns combined results # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:86
////		  8) Valid price range filter shows only hotels within the range # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:103
////		  9) Unrealistically low maximum price shows no available hotels # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:114
////		  10) Price Low to High sort orders results in ascending price order # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:131
////		  11) Price High to Low sort orders results in descending price order # file:///C:/Users/KIIT/Desktop/MakeMyTrip_Capg/MMT_Automation/src/test/resources/features/Hotels.feature:142
//
//
//		WebDriver driver;
//		HotelSearchPage hs;
//		HotelListingsPage hl;
//		
//		@Given("the user is on the MakeMyTrip Hotels homepage")
//		public void the_user_is_on_the_make_my_trip_hotels_homepage() {
//		    // Write code here that turns the phrase above into concrete actions
//			driver=new FirefoxDriver();
//			driver.manage().window().maximize();
//			driver.get("https://www.makemytrip.com/?cc=in&redirectedBy=gl");
//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));	        
//			hs = new HotelSearchPage(driver);
//	        hl = new HotelListingsPage(driver);
//	        hs.preReq();
//		    
//		}
//
//		Map<String, String> data;
//		
//		@When("the user enters {string} in the destination field")
//		public void the_user_enters_in_the_destination_field(String tcId) throws Exception {
//			
//			data = Hotel_ExcelUtil.getRowData(tcId);
//			hs.enterDestination(data.get("City"));
//		    
//		}
//
//		@When("the user selects check-in date {string}")
//		public void the_user_selects_check_in_date(String tcId) throws Exception {
//		    
//			data = Hotel_ExcelUtil.getRowData(tcId);
//			hs.enterCheckInDate(data.get("Check-in"));
//		    
//		}
//
//		@When("the user selects check-out date {string}")
//		public void the_user_selects_check_out_date(String tcId) throws Exception {
//		   
//			data = Hotel_ExcelUtil.getRowData(tcId);
//			hs.enterCheckOutDate(data.get("Check-out"));
//		  
//		}
//
//		@When("the user sets rooms and guests to {string} room and {string} adults")
//		public void the_user_sets_rooms_and_guests_to_room_and_adults(String tcId) throws Exception {
//		    
//			data = Hotel_ExcelUtil.getRowData(tcId);
//			hs.enterRoomsAndGuests(data.get("Rooms"), data.get("Guests"));
//		    
//		}
//
//		@When("the user clicks the Search button")
//		public void the_user_clicks_the_search_button() {
//		    
//			hs.clickSearchBtn();
//		}
//
//		//tc_01_01
//		@Then("the hotel listing page is displayed")
//		public void the_hotel_listing_page_is_displayed() {
//		    
//		    //boolean isPageDisplayed=hl.isHotelListingsDisplayed();
//		    Assert.assertTrue(hl.isHotelListingsDisplayed());
//		}
//		
//
//		//tc_01_02
//		@Then("each hotel card displays the correct location tag for {string}")
//		public void each_hotel_card_displays_the_correct_location_tag_for(String landmark) throws Exception {
//		    
//			
//		   
//		}
//
//
//		@Then("clicking the search icon displays a no hotels found message")
//		public void clicking_the_search_icon_displays_a_no_hotels_found_message() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//
//		@Then("the application removes the special characters")
//		public void the_application_removes_the_special_characters() {
//		    // Write code here that turns the phrase above into concrete actions
//		    throw new io.cucumber.java.PendingException();
//		}
//
//		@Then("the destination field contains only {string}")
//		public void the_destination_field_contains_only(String string) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("the Search button is enabled")
//		public void the_search_button_is_enabled() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("the hotel listing page is displayed for {string}")
//		public void the_hotel_listing_page_is_displayed_for(String string) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@When("the user applies the star rating filter for {string} stars")
//		public void the_user_applies_the_star_rating_filter_for_stars(String string) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("all displayed hotel cards show a {string} star rating")
//		public void all_displayed_hotel_cards_show_a_star_rating(String string) {
//		    // Write code here that turns the phrase above into concrete actions
//		   
//		}
//
//		@Then("the result count updates to reflect only {int}-star properties")
//		public void the_result_count_updates_to_reflect_only_star_properties(Integer int1) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("all displayed hotel cards show either {string} or {string} star ratings")
//		public void all_displayed_hotel_cards_show_either_or_star_ratings(String string, String string2) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("the result count reflects the combined filtered set")
//		public void the_result_count_reflects_the_combined_filtered_set() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@When("the user sets the minimum price to {string}")
//		public void the_user_sets_the_minimum_price_to(String string) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@When("the user sets the maximum price to {string}")
//		public void the_user_sets_the_maximum_price_to(String string) {
//		    // Write code here that turns the phrase above into concrete actions
//		   
//		}
//
//		@Then("all displayed hotel cards show a per-night rate between {string} and {string}")
//		public void all_displayed_hotel_cards_show_a_per_night_rate_between_and(String string, String string2) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("no hotel cards are displayed")
//		public void no_hotel_cards_are_displayed() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("a sold out or no results message is shown")
//		public void a_sold_out_or_no_results_message_is_shown() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@When("the user sorts results by {string}")
//		public void the_user_sorts_results_by(String string) {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("hotel cards are ordered in ascending price sequence")
//		public void hotel_cards_are_ordered_in_ascending_price_sequence() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("the first card shows the lowest price among all results")
//		public void the_first_card_shows_the_lowest_price_among_all_results() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//		@Then("hotel cards are ordered in descending price sequence")
//		public void hotel_cards_are_ordered_in_descending_price_sequence() {
//		    // Write code here that turns the phrase above into concrete actions
//		   
//		}
//
//		@Then("the first card shows the highest price among all results")
//		public void the_first_card_shows_the_highest_price_among_all_results() {
//		    // Write code here that turns the phrase above into concrete actions
//		    
//		}
//
//
//	
//	
//
//}
