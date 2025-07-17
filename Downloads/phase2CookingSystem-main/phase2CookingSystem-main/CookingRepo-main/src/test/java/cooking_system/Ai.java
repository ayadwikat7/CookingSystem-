package cooking_system;

import io.cucumber.java.en.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Ai {



	private final List<Recipe> recipeDatabase = Arrays.asList(
		    new Recipe("Spaghetti with Tomato Sauce", Arrays.asList("Tomatoes", "pasta", "basil", "olive oil"), 25, true),
		    new Recipe("Tomato Basil Soup", Arrays.asList("Tomatoes", "basil", "garlic"), 40, true),
		    new Recipe("Vegan Pesto Pasta", Arrays.asList("basil", "pasta", "olive oil", "garlic"), 20, true),

		    new Recipe("Mashed Peanuts", Arrays.asList("peanut butter", "wheat flour"), 15, true),
		    new Recipe("Plain Peanut Spread", Arrays.asList("peanut butter"), 5, true),
		    new Recipe("Mashed Peanuts without Wheat", Arrays.asList("peanut butter", "olive oil"), 10, true),

		    new Recipe("Egg Salad", Arrays.asList("egg", "mayonnaise", "lettuce"), 10, true),
		    new Recipe("Boiled Egg", Arrays.asList("egg"), 8, true),

		    new Recipe("Cheese Sandwich", Arrays.asList("cheese", "bread"), 10, true),
		    new Recipe("Grilled Cheese", Arrays.asList("cheese", "butter", "bread"), 12, true),

		    new Recipe("Green Salad", Arrays.asList("lettuce", "cucumber", "olive oil"), 7, true),
		    new Recipe("Fruit Bowl", Arrays.asList("apple", "banana", "orange"), 5, true),

		    new Recipe("Milk Porridge", Arrays.asList("milk", "oats", "sugar"), 15, true),
		    new Recipe("Hot Chocolate", Arrays.asList("milk", "cocoa", "sugar"), 7, true),

		    new Recipe("Roasted Garlic", Arrays.asList("garlic"), 20, true),
		    new Recipe("Just Cheese", Arrays.asList("cheese"), 5, true),
		    new Recipe("Egg with cheese", Arrays.asList("egg", "cheese"), 8, true)
		    


		);


    private List<String> userIngredients;
    private int userMaxTime; 
    private dietary_preferences.DietType userDiet = null;
    private String recommendedRecipe = "";
 
    @Given("the user has dietary restrictions: Vegan")
    public void theUserHasDietaryRestrictionsVegan() {
        userDiet = dietary_preferences.DietType.VEGAN;
        dataBase.activeDietaryPreference = new dietary_preferences("Vegan", userDiet);
    }

    @Given("the user has available ingredients: Tomatoes, basil, pasta")
    public void theUserHasAvailableIngredientsTomatoesBasilPasta() {
        userIngredients = Arrays.asList("Tomatoes", "basil", "pasta");
    }

    @Given("the user has only {int} minutes to cook")
    public void theUserHasOnlyMinutesToCook(Integer time) {
        userMaxTime = time;
    }

    @When("the AI assistant is asked to recommend a recipe")
    public void theAIAssistantIsAskedToRecommendARecipe() {
        for (Recipe recipe : recipeDatabase) {
            if (recipe.time > userMaxTime) continue;
            if (userDiet != null && !recipe.isVegan) continue;

            if (userIngredients.containsAll(recipe.ingredients)) {
                recommendedRecipe = recipe.name;
                return;
            }
        }

        for (Recipe recipe : recipeDatabase) {
            if (recipe.time <= userMaxTime && recipe.isVegan) {
                recommendedRecipe = recipe.name;
                return;
            }
        }

        recommendedRecipe = "No suitable recipe found.";
    }

    @Then("the AI should recommend {string}")
    public void theAIShouldRecommend(String expected) {
        assertEquals(expected, recommendedRecipe);
    }
    @When("the AI assistant evaluates all recipes")
    public void theAIAssistantEvaluatesAllRecipes() {
        for (Recipe recipe : recipeDatabase) {
            if (recipe.time <= userMaxTime &&
                recipe.isVegan &&
                userIngredients.containsAll(recipe.ingredients)) {
                recommendedRecipe = recipe.name;
                return;
            }
        }

        for (Recipe recipe : recipeDatabase) {
            if (recipe.time <= userMaxTime && recipe.isVegan) {
                recommendedRecipe = recipe.name;
                return;
            }
        }

        recommendedRecipe = "No suitable recipe found.";
    }

    @Then("the recipe {string} should not be recommended")
    public void theRecipeShouldNotBeRecommended(String recipeName) {
        assertNotEquals("AI should not recommend the recipe: " + recipeName, recipeName, recommendedRecipe);
    }
    @Given("the user has available ingredients: basil, pasta")
    public void theUserHasAvailableIngredientsBasilPasta() {
        userIngredients = Arrays.asList("basil", "pasta");
    }

    public void setInputs(Set<dietary_preferences.DietType> diet, List<String> ingredients, int time) {
        this.userDiet = (diet != null && !diet.isEmpty()) ? diet.iterator().next() : null;
        this.userIngredients = ingredients;
        this.userMaxTime = time;
    }

    public void recommend() {
        Set<String> userLowerSet = userIngredients.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

        Recipe bestMatch = null;
        int maxMatchCount = 0;

        for (Recipe recipe : recipeDatabase) {
            if (userDiet != null && !recipe.isVegan) continue;

            Set<String> recipeLowerSet = recipe.ingredients.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

            long matches = recipeLowerSet.stream()
                .filter(userLowerSet::contains)
                .count();

            if (matches == recipeLowerSet.size()) {
                System.out.println(" Recommended Recipe (full match): " + recipe.name);
                return;
            }

            if (matches > maxMatchCount) {
                maxMatchCount = (int) matches;
                bestMatch = recipe;
            }
        }

        if (bestMatch != null) {
            System.out.println(" Recommended (partial match): " + bestMatch.name);
        } else {
            System.out.println("No suitable recipe found.");
        }
    }




}
