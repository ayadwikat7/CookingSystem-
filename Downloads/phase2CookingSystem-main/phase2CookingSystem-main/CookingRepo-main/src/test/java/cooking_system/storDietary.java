package cooking_system;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.*;

public class storDietary {
	 public static void setDietary(user customer, dietary_preferences pref) {
	        customer.setDietary(pref);
	        System.out.println(" Dietary preference saved: " + pref.getNameDietaryPreferences());
	    }

    user newUser = dataBase.LogedIn;

    boolean isLogged(user inter) {
        assertNotNull("User is null", inter);

        for (user registeredUser : dataBase.usersRegList) {
            if (inter.getEmail().equals(registeredUser.getEmail()) &&
                inter.getPassword().equals(registeredUser.getPassword())) {

                if (!dataBase.AllLogedUser.contains(registeredUser)) {
                    dataBase.AllLogedUser.add(registeredUser);
                }
                return true; 
            }
        }
        return false;
    }

    @Given("a customer is logged into the system")
    public void aCustomerIsLoggedIntoTheSystem() {
        if (!dataBase.usersRegList.contains(newUser)) {
            dataBase.usersRegList.add(newUser);
        }

        assertTrue("You Don't have an account yet.", isLogged(newUser));
        System.out.println("Successfully logged in");
    }

    @Given("the customer navigates to the dietary preferences section")
    public void theCustomerNavigatesToTheDietaryPreferencesSection() {
        System.out.println("You are on the food preferences page");
    }

    @When("the customer enters their dietary preferences and allergies")
    public void theCustomerEntersTheirDietaryPreferencesAndAllergies() {
        assertTrue("User not logged in", isLogged(newUser));
        assertNotNull("Active dietary preference not set", dataBase.activeDietaryPreference);

        newUser.setDietary(dataBase.activeDietaryPreference);
    }

    @When("saves the preferences")
    public void savesThePreferences() {
        assertTrue("User not logged in", isLogged(newUser));
        assertNotNull("User dietary preference is null", newUser.getDietary());

        boolean isSaved = !dataBase.allUserDietary.contains(newUser.getDietary());
        if (isSaved) {
            dataBase.allUserDietary.add(newUser.getDietary());
        }

        assertTrue("This dietary preference is already saved.", isSaved);
    }

    @Then("the system stores the preferences successfully")
    public void theSystemStoresThePreferencesSuccessfully() {
        assertTrue("User not logged in", isLogged(newUser));
        assertNotNull("User dietary preference is null", newUser.getDietary());

        boolean isStored = dataBase.allUserDietary.contains(newUser.getDietary());
        assertTrue("Failed to store dietary preferences!", isStored);

        System.out.println("Dietary preferences stored successfully.");
    }

    @Then("the system confirms the preferences are saved")
    public void theSystemConfirmsThePreferencesAreSaved() {
        assertTrue("User not logged in", isLogged(newUser));
        assertNotNull("User dietary preference is null", newUser.getDietary());

        boolean isConfirmed = dataBase.allUserDietary.contains(newUser.getDietary());
        assertTrue("Dietary preferences were not saved!", isConfirmed);

        System.out.println("The system confirms that dietary preferences are saved successfully.");
    }

    @Given("a chef is logged into the system")
    public void aChefIsLoggedIntoTheSystem() {
        user ChefUser = new user("Chef_JohnDoe@example.com", "chefpassword", "JohnDoe");

        if (!dataBase.usersRegList.contains(ChefUser)) {
            dataBase.usersRegList.add(ChefUser);
        }

        boolean isChef = ChefUser.isChef() && isLogged(ChefUser);
        assertTrue("You Are Not a Chef", isChef);
        System.out.println("Welcome Chef " + ChefUser.getUsername());
    }

    @Given("the chef navigates to the customer preferences section")
    public void theChefNavigatesToTheCustomerPreferencesSection() {
        System.out.println("You are on the food preferences page");
    }

    @When("the chef selects a customer order")
    public void theChefSelectsACustomerOrder() {
        System.out.println("Chef has selected a customer order.");
    }

    @Then("the chef can view the customer's dietary preferences and allergies")
    public void theChefCanViewTheCustomerSDietaryPreferencesAndAllergies() {
        assertTrue("No logged-in customers available.", !dataBase.AllLogedUser.isEmpty());

        user selectedCustomer = dataBase.AllLogedUser.get(0);
        if (selectedCustomer.getDietary() != null) {
            System.out.println("Chef is viewing dietary preferences: " + selectedCustomer.getDietary().getNameDietaryPreferences());
        } else {
            System.out.println("Customer has no dietary preferences set.");
        }
    }

    @Given("a customer has dietary preferences and allergies stored in the system")
    public void aCustomerHasDietaryPreferencesAndAllergiesStoredInTheSystem() {
        if (newUser.getDietary() == null) {
            newUser.setDietary(new dietary_preferences("Vegetarian"));
        }

        assertNotNull("You do not have any dietary preferences", newUser.getDietary());
        System.out.println("Customer dietary preferences are stored: " + newUser.getDietary().getNameDietaryPreferences());
    }

    @When("the customer browses the meal recommendations")
    public void theCustomerBrowsesTheMealRecommendations() {
        System.out.println("Customer is browsing meal recommendations...");
    }

    @Then("the system suggests meals that match the customer's dietary preferences")
    public void theSystemSuggestsMealsThatMatchTheCustomerSDietaryPreferences() {
        assertNotNull("User dietary preference is missing", newUser.getDietary());

        List<String> mealDatabase = List.of("Vegetarian Salad", "Vegan Burger", "Grilled Chicken", "Fish Curry");

        String customerPreference = newUser.getDietary().getNameDietaryPreferences().toLowerCase().trim();
        System.out.println(" Looking for meals matching: " + customerPreference);

 
        String keyword = "";
        if (customerPreference.contains("vegan")) {
            keyword = "vegan";
        } else if (customerPreference.contains("vegetarian")) {
            keyword = "vegetarian";
        } else if (customerPreference.contains("fish")) {
            keyword = "fish";
        } else if (customerPreference.contains("gluten")) {
            keyword = "gluten";
        }

        List<String> recommendedMeals = new ArrayList<>();
        for (String meal : mealDatabase) {
            if (meal.toLowerCase().contains(keyword)) {
                recommendedMeals.add(meal);
            }
        }

        System.out.println(" Recommended meals for '" + keyword + "': " + recommendedMeals);
        assertFalse(" No suitable meal recommendations found for: " + keyword, recommendedMeals.isEmpty());
    }

    @Then("does not suggest meals containing allergens")
    public void doesNotSuggestMealsContainingAllergens() {
        assertNotNull("User dietary preference is missing", newUser.getDietary());

        List<String> allergenMeals = List.of("Peanut Butter Sandwich", "Shrimp Pasta", "Fish Curry");
        String customerPreference = newUser.getDietary().getNameDietaryPreferences();

        boolean containsAllergens = false;
        for (String meal : allergenMeals) {
            if (meal.toLowerCase().contains(customerPreference.toLowerCase())) {
                containsAllergens = true;
                break;
            }
        }

        assertFalse("Allergen-containing meals were suggested!", containsAllergens);
        System.out.println("No allergen meals were suggested for " + customerPreference);
    }
}
