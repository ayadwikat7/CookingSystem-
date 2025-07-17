package cooking_system;

import java.util.Date;

public class Order {
    private String mealName;
    private Date date;
    private double price;

    public Order(String mealName, Date date, double price) {
        this.mealName = mealName;
        this.date = date;
        this.price = price;
    }

    public String getMealName() { return mealName; }
    public Date getDate() { return date; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "Meal: " + mealName + ", Date: " + date + ", Price: $" + price;
    }
}
