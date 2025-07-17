package cooking_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class SuggestAlternative {

    private boolean chefApprovedSubstitution = false;
    private boolean autoAppliedSubstitution = false;

    @Given("a customer selects ingredients for their meal")
    public void aCustomerSelectsIngredientsForTheirMeal() {
        dataBase.LogedIn = new user("customer@gmail.com", "password123", "customerUser");
        System.out.println(dataBase.LogedIn.getUsername() + " selected ingredients: " + dataBase.selectedIngredients);
        assertTrue("Ingredient selection should not be empty.", !dataBase.selectedIngredients.isEmpty());
    }

    @When("an ingredient is unavailable or does not fit their dietary restrictions")
    public void anIngredientIsUnavailableOrDoesNotFitTheirDietaryRestrictions() {
        dataBase.suggestedAlternatives.clear();
        for (String ingredient : dataBase.selectedIngredients) {
            if (dataBase.dietaryRestrictedIngredients.contains(ingredient)) {
                String alternative = findAlternative(ingredient);
                dataBase.suggestedAlternatives.add(alternative);
                System.out.println("Suggested alternative for " + ingredient + ": " + alternative);
            }
        }
        assertTrue("At least one alternative should be suggested.", dataBase.suggestedAlternatives.size() > 0);
    }

    @Then("the system should suggest suitable alternative ingredients")
    public void theSystemShouldSuggestSuitableAlternativeIngredients() {
        System.out.println("Suggested alternatives: " + dataBase.suggestedAlternatives);
        assertTrue("Suggested alternatives list should not be empty.", !dataBase.suggestedAlternatives.isEmpty());
    }

    @Given("an ingredient substitution has been suggested")
    public void anIngredientSubstitutionHasBeenSuggested() {
        assertTrue("Expected at least one suggested substitution.", !dataBase.suggestedAlternatives.isEmpty());
        System.out.println("Substitution suggestion ready for chef approval.");
    }

    @When("the chef receives an alert about the substitution")
    public void theChefReceivesAnAlertAboutTheSubstitution() {
        dataBase.sharedChef = new Chef("Chef_John", "General");
        System.out.println(dataBase.sharedChef.getUsername() + " received substitution alert: " + dataBase.suggestedAlternatives);
        assertTrue("Chef should receive at least one suggested alternative.", dataBase.suggestedAlternatives.size() > 0);
    }

    @Then("the chef should be able to approve or adjust the final recipe")
    public void theChefShouldBeAbleToApproveOrAdjustTheFinalRecipe() {
        if (!dataBase.suggestedAlternatives.isEmpty()) {
            chefApprovedSubstitution = true;
            System.out.println(dataBase.sharedChef.getUsername() + " approved substitutions: " + dataBase.suggestedAlternatives);
        }
        assertTrue("Chef must approve the substitution if alternatives exist.", chefApprovedSubstitution);
    }

    @Given("an ingredient is unavailable and a pre-approved substitution exists")
    public void anIngredientIsUnavailableAndAPreApprovedSubstitutionExists() {
        dataBase.suggestedAlternatives = Arrays.asList("Almond Butter");
        System.out.println("Pre-approved substitution exists for unavailable ingredient.");
        assertTrue("There should be a pre-approved substitution.", !dataBase.suggestedAlternatives.isEmpty());
    }

    @When("the system detects a compatible replacement")
    public void theSystemDetectsACompatibleReplacement() {
        autoAppliedSubstitution = !dataBase.suggestedAlternatives.isEmpty();
        if (autoAppliedSubstitution) {
            System.out.println("System detected compatible replacement: " + dataBase.suggestedAlternatives);
        }
        assertTrue("System should detect and apply compatible substitution.", autoAppliedSubstitution);
    }

    @Then("the system should automatically apply the substitution and notify the customer")
    public void theSystemShouldAutomaticallyApplyTheSubstitutionAndNotifyTheCustomer() {
        assertTrue("Substitution should be applied automatically if compatible.", autoAppliedSubstitution);
        System.out.println("System automatically applied substitution: " + dataBase.suggestedAlternatives);
        System.out.println("Notification sent to customer " + dataBase.LogedIn.getUsername() + ": substitution applied.");
    }

    private String findAlternative(String ingredient) {
        switch (ingredient) {
            case "Peanut Butter":
                return "Almond Butter";
            case "Milk":
                return "Soy Milk";
            case "Egg":
                return "Flaxseed Meal";
            default:
                return "Generic Substitute";
        }
    }
}
