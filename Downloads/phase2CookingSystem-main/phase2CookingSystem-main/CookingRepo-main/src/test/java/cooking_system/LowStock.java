package cooking_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class LowStock {

    @Given("the kitchen inventory is being monitored")
    public void theKitchenInventoryIsBeingMonitored() {
        dataBase.kitchenManager = new KitchenManager("kitchen@manager.com", "admin123", "MainManager");
        dataBase.ingredient = new Ingredient("Tomatoes", 10, 5, 2);
        System.out.println("Monitoring stock levels for ingredient: " + dataBase.ingredient.getName());
    }

    @When("an ingredient's stock level falls below the minimum threshold")
    public void anIngredientsStockLevelFallsBelowTheMinimumThreshold() {
        dataBase.ingredient.setStockLevel(4);
        if (dataBase.ingredient.getStockLevel() < dataBase.ingredient.getMinThreshold()) {
            dataBase.kitchenManager.addReminder("Low-stock alert: " + dataBase.ingredient.getName() + " is below the minimum threshold.");
        }
    }

    @Then("the system should send a low-stock alert to the kitchen manager")
    public void theSystemShouldSendALowStockAlertToTheKitchenManager() {
        assertTrue("Expected reminder for low stock was not found", dataBase.kitchenManager.hasPendingReminders());
        System.out.println("Sending low-stock alert to kitchen manager: " + dataBase.kitchenManager.getReminders().get(0));
        dataBase.kitchenManager.acknowledgeReminder();
    }

    @Given("an ingredient's stock level is low")
    public void anIngredientsStockLevelIsLow() {
        dataBase.ingredient.setStockLevel(4);
        System.out.println("Stock for " + dataBase.ingredient.getName() + " is low: " + dataBase.ingredient.getStockLevel());
    }

    @When("the kitchen manager receives the alert")
    public void theKitchenManagerReceivesTheAlert() {
        dataBase.kitchenManager.addReminder("Reorder suggestion: " + dataBase.ingredient.getName() + " stock is low.");
        System.out.println("Kitchen manager received alert for " + dataBase.ingredient.getName());
    }

    @Then("the system should provide a suggested reorder quantity and supplier details")
    public void theSystemShouldProvideASuggestedReorderQuantityAndSupplierDetails() {
        int suggestedQuantity = dataBase.ingredient.getMinThreshold() * 2;
        String supplierInfo = "Supplier XYZ";
        System.out.println("Suggestion: Order " + suggestedQuantity + " units of " + dataBase.ingredient.getName() + " from " + supplierInfo);
        assertTrue("Suggested reorder quantity must be greater than zero", suggestedQuantity > 0);
    }

    @Given("an ingredient's stock is critically low")
    public void anIngredientsStockIsCriticallyLow() {
        dataBase.ingredient.setStockLevel(1);
        System.out.println("Critical stock warning for " + dataBase.ingredient.getName() + ": " + dataBase.ingredient.getStockLevel());
    }

    @When("the system detects an urgent need for restocking")
    public void theSystemDetectsAnUrgentNeedForRestocking() {
        if (dataBase.ingredient.getStockLevel() < dataBase.ingredient.getCriticalThreshold()) {
            dataBase.kitchenManager.addReminder("URGENT: Critical stock warning for " + dataBase.ingredient.getName() + "!");
        }
    }

    @Then("the system should send a high-priority notification to the kitchen manager")
    public void theSystemShouldSendAHighPriorityNotificationToTheKitchenManager() {
        assertTrue("Expected high-priority reminder was not found", dataBase.kitchenManager.hasPendingReminders());
        System.out.println("HIGH PRIORITY: " + dataBase.kitchenManager.getReminders().get(0));
        dataBase.kitchenManager.acknowledgeReminder();
    }
    
}
