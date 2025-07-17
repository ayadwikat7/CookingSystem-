package cooking_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

public class OrderMenu {

    static List<Ingredient> availableIngredients;
    static List<Ingredient> selectedIngredients;
    static boolean incompatibleDetected = false;
    static String customerDietPlan = "VEGETARIAN"; 
    static List<String> alternatives;
    dietary_preferences preferences = new dietary_preferences("Vegan Plan", dietary_preferences.DietType.VEGAN);
    
    @Given("the customer accesses the meal customization page")
    public void theCustomerAccessesTheMealCustomizationPage() {
        availableIngredients = Arrays.asList(
                new Ingredient("Lettuce", 50, 10, 5),
                new Ingredient("Tomato", 40, 15, 5),
                new Ingredient("Cheese", 30, 10, 3),
                new Ingredient("Chicken", 20, 5, 2)
        );
        selectedIngredients = new ArrayList<>();
        assertTrue("Available ingredients should not be empty.", !availableIngredients.isEmpty());
    }

    @When("they select ingredients from the available list")
    public void theySelectIngredientsFromTheAvailableList() {
        selectedIngredients.add(findIngredientByName("Lettuce"));
        selectedIngredients.add(findIngredientByName("Tomato"));
        selectedIngredients.add(findIngredientByName("Cheese"));

        assertEquals("Selected ingredients should be exactly 3.", 3, selectedIngredients.size());
    }

    @Then("they should be able to add, remove, or modify ingredients before placing the order")
    public void theyShouldBeAbleToAddRemoveOrModifyIngredientsBeforePlacingTheOrder() {
        selectedIngredients.add(findIngredientByName("Chicken"));
        assertTrue("Chicken should have been added.",
                selectedIngredients.stream().anyMatch(i -> i.getName().equals("Chicken")));

        selectedIngredients.removeIf(i -> i.getName().equals("Tomato"));
        assertTrue("Tomato should have been removed.",
                selectedIngredients.stream().noneMatch(i -> i.getName().equals("Tomato")));

        for (int i = 0; i < selectedIngredients.size(); i++) {
            if (selectedIngredients.get(i).getName().equals("Cheese")) {
                selectedIngredients.set(i, findIngredientByName("Lettuce"));
            }
        }
        assertTrue("Lettuce should exist after modification.",
                selectedIngredients.stream().anyMatch(i -> i.getName().equals("Lettuce")));

        assertTrue("Cheese should have been replaced.",
                selectedIngredients.stream().noneMatch(i -> i.getName().equals("Cheese")));
    }

    @Given("the customer selects ingredients for a custom meal")
    public void theCustomerSelectsIngredientsForACustomMeal() {
        availableIngredients = Arrays.asList(
                new Ingredient("Lettuce", 50, 10, 5),
                new Ingredient("Tomato", 40, 15, 5),
                new Ingredient("Cheese", 30, 10, 3),
                new Ingredient("Chicken", 0, 5, 2), 
                new Ingredient("Beef", 10, 5, 2)    
        );
 
        selectedIngredients = new ArrayList<>();
        selectedIngredients.add(findIngredientByName("Lettuce"));
        selectedIngredients.add(findIngredientByName("Beef")); 

        assertTrue("The customer should have selected at least one ingredient.", !selectedIngredients.isEmpty());
    }

    @When("the system detects an incompatible or unavailable ingredient combination")
    public void theSystemDetectsAnIncompatibleOrUnavailableIngredientCombination() {
        incompatibleDetected = false;

        for (Ingredient ing : selectedIngredients) {
            if (customerDietPlan.equals("VEGETARIAN") && 
                (ing.getName().equalsIgnoreCase("Beef") || ing.getName().equalsIgnoreCase("Chicken"))) {
                incompatibleDetected = true;
                break;
            }
            if (ing.getStockLevel() <= 0) {
                incompatibleDetected = true;
                break;
            }
        }

        assertTrue("An incompatible or unavailable ingredient should have been detected.", incompatibleDetected);
    }

    @Then("it should notify the customer and suggest alternative options")
    public void itShouldNotifyTheCustomerAndSuggestAlternativeOptions() {
        if (incompatibleDetected) {
            System.out.println("Notification: Your selected ingredients are incompatible or unavailable.");
            List<String> alternatives = suggestAlternatives(customerDietPlan);
            System.out.println(" Suggested Alternatives: " + alternatives);

            assertTrue("The system should suggest at least one alternative.", !alternatives.isEmpty());
        } else {
            System.out.println(" All selected ingredients are valid.");
        }
    }

    private Ingredient findIngredientByName(String name) {
        return availableIngredients.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found: " + name));
    }

    private List<String> suggestAlternatives(String dietPlan) {
        List<String> alternatives = new ArrayList<>();

        for (Ingredient ing : availableIngredients) {
            if (dietPlan.equals("VEGETARIAN")) {
                if (!ing.getName().equalsIgnoreCase("Beef") &&
                    !ing.getName().equalsIgnoreCase("Chicken") &&
                    ing.getStockLevel() > 0) {
                    alternatives.add(ing.getName());
                }
            } else {
                if (ing.getStockLevel() > 0) {
                    alternatives.add(ing.getName());
                }
            }
        }
        return alternatives;
    }
    @Given("the customer selects ingredients")
    public void theCustomerSelectsIngredients() {
        availableIngredients = Arrays.asList(
                new Ingredient("Lettuce", 50, 10, 5),
                new Ingredient("Tomato", 40, 15, 5),
                new Ingredient("Cheese", 30, 10, 3),
                new Ingredient("Chicken", 20, 5, 2),
                new Ingredient("Beef", 10, 5, 2)
        );

        selectedIngredients = new ArrayList<>();
        selectedIngredients.add(findIngredientByName("Lettuce"));
        selectedIngredients.add(findIngredientByName("Chicken")); 

        assertTrue("Customer should have selected at least one ingredient.", !selectedIngredients.isEmpty());
    }

    @When("an ingredient violates dietary restrictions \\(e.g., vegetarian meal with meat)")
    public void anIngredientViolatesDietaryRestrictionsEGVegetarianMealWithMeat() {
        incompatibleDetected = false;

        for (Ingredient ing : selectedIngredients) {
            if (customerDietPlan.equals("VEGETARIAN") && 
                (ing.getName().equalsIgnoreCase("Beef") || ing.getName().equalsIgnoreCase("Chicken"))) {
                incompatibleDetected = true;
                break;
            }
        }

        assertTrue("Dietary restriction violation should be detected.", incompatibleDetected);
    }

    @Then("the system should display a warning and prevent order placement until the issue is resolved")
    public void theSystemShouldDisplayAWarningAndPreventOrderPlacementUntilTheIssueIsResolved() {
        if (incompatibleDetected) {
            System.out.println("Warning: Your selected ingredients violate dietary restrictions.");
            System.out.println("Order cannot be placed until the issue is resolved.");

             alternatives = suggestAlternatives(customerDietPlan);
            System.out.println("Suggested Alternatives: " + alternatives);

            assertTrue("The system should suggest alternatives.", !alternatives.isEmpty());
            boolean canPlaceOrder = !incompatibleDetected;
            assertTrue("Order placement should be blocked due to dietary restriction violation.", !canPlaceOrder);
        } else {
            System.out.println("All ingredients meet dietary restrictions. Order can be placed.");
        }
    }
 
 

}
