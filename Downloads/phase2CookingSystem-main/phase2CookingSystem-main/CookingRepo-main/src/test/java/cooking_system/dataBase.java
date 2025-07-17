package cooking_system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dataBase {
    public static user usersnew ; 
    public static List<user> usersRegList = new ArrayList<>();
    public static List<user> AllLogedUser = new ArrayList<>();
    public static user LogedIn=new user();

    public static Chef sharedChef;
    public static Task sharedTask=new Task("Prepare Margherita Pizza", "Italian Cuisine", 45);

    public static List<Chef> chefs = new ArrayList<>(); 
    public static Task task = new Task("Prepare Tiramisu", "Pastry", 60); 

    public static List<String> invoices = new ArrayList<>();
    public static boolean isAdminLoggedIn = false;
    public static List<String> suggestedAlternatives = new ArrayList<>();
    public static List<String> selectedIngredients = Arrays.asList("Milk", "Egg", "Flour", "Peanut Butter");
    public static List<String> dietaryRestrictedIngredients = Arrays.asList("Peanut Butter");
    public static KitchenManager kitchenManager = new KitchenManager("aya@we","147852369 ","aya "); 
    public static Ingredient ingredient;
    public static dietary_preferences activeDietaryPreference=new dietary_preferences("fish");
    public static List<String> customerIngredients = new ArrayList<>();
    static List<dietary_preferences> allUserDietary = new ArrayList<>();
    public static List<Task> availableTasks = new ArrayList<>(List.of( 
    	    new Task("Make Spaghetti with Tomato Sauce", "Vegetarian", 30),
    	    new Task("Prepare Tomato Basil Soup", "Vegetarian", 40),
    	    new Task("Cook Vegan Pesto Pasta", "Vegetarian", 20),
    	    
    	    new Task("Bake Mashed Peanuts", "Pastry", 15),
    	    new Task("Prepare Plain Peanut Spread", "Pastry", 5),
    	    new Task("Make Mashed Peanuts without Wheat", "Pastry", 12),

    	    new Task("Prepare Egg Salad", "Vegetarian", 10),
    	    new Task("Boil Egg", "Vegetarian", 8),

    	    new Task("Assemble Cheese Sandwich", "Pastry", 10),
    	    new Task("Make Grilled Cheese", "Pastry", 12),

    	    new Task("Toss Green Salad", "Vegetarian", 7),
    	    new Task("Serve Fruit Bowl", "Vegetarian", 5),

    	    new Task("Cook Milk Porridge", "Pastry", 15),
    	    new Task("Prepare Hot Chocolate", "Pastry", 7),

    	    new Task("Roast Garlic", "Vegetarian", 20),
    	    new Task("Slice Just Cheese", "Pastry", 5),
    	    new Task("Make Egg with Cheese", "Pastry", 8)
    	));
    public static final List<String> expertises = Arrays.asList("Pastry", "Vegetarian", "Italian Cuisine");


}
