package cooking_system;

import java.util.List;
import java.util.stream.Collectors;

public class Recipe {
    String name;
    List<String> ingredients;
    int time;
    boolean isVegan;

    public Recipe(String name, List<String> ingredients, int time, boolean isVegan) {
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
        this.isVegan = isVegan;
    }
}
