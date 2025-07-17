package cooking_system;

import java.util.*;

public class dietary_preferences {

    private String nameDietaryPreferences;

    public enum DietType {
        VEGAN,
        VEGETARIAN,
        ALLERGY_FREE
    }

    Set<DietType> userRestrictions;

    static final Map<String, List<DietType>> ingredientRestrictions;
     static final Map<String, String> ingredientSubstitutes;

    static {
        Map<String, List<DietType>> restrictions = new HashMap<>();
        restrictions.put("Milk", Arrays.asList(DietType.VEGAN));
        restrictions.put("Egg", Arrays.asList(DietType.VEGAN));
        restrictions.put("Cheese", Arrays.asList(DietType.VEGAN));
        restrictions.put("Peanut Butter", Arrays.asList(DietType.ALLERGY_FREE));
        restrictions.put("Wheat Flour", Arrays.asList(DietType.ALLERGY_FREE));
     

        ingredientRestrictions = Collections.unmodifiableMap(restrictions);

        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("Milk", "Soy Milk");
        substitutes.put("Egg", "Flaxseed Meal");
        substitutes.put("Cheese", "Vegan Cheese");
        substitutes.put("Peanut Butter", "Sunflower Seed Butter");
        substitutes.put("Wheat Flour", "Almond Flour");
  

        ingredientSubstitutes = Collections.unmodifiableMap(substitutes);
    }

    public dietary_preferences(String nameDietaryPreferences, DietType... restrictions) {
        this.nameDietaryPreferences = nameDietaryPreferences;
        this.userRestrictions = new HashSet<>(Arrays.asList(restrictions));
        dataBase.activeDietaryPreference = this;
    }

    public String getNameDietaryPreferences() {
        return nameDietaryPreferences;
    }
}
