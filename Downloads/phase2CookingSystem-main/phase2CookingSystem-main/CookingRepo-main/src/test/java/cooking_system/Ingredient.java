package cooking_system;

public class Ingredient {

    private String name;
    private int stockLevel;
    private int minThreshold;
    private int criticalThreshold;

    public Ingredient(String name, int stockLevel, int minThreshold, int criticalThreshold) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.minThreshold = minThreshold;
        this.criticalThreshold = criticalThreshold;
    }

    public String getName() {
        return name;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public int getCriticalThreshold() {
        return criticalThreshold;
    }
}
