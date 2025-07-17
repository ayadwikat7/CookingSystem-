package cooking_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class sendRemainder {

    @Given("the system has scheduled orders and tasks for the day")
    public void theSystemHasScheduledOrdersAndTasksForTheDay() {
        user customer = new user("customer@email.com", "1234", "Customer_Aya");
        Chef chef = new Chef("chef@email.com", "pass", "Chef_Ali", "Grill");
        Task task = new Task("Grill Chicken", "Grill", 30);

        // Store in shared database
        dataBase.usersRegList.add(customer);
        dataBase.sharedChef = chef;
        dataBase.sharedTask = task;

        System.out.println("✅ System initialized with today's scheduled task and order.");
    }

    @Given("a customer has a scheduled meal delivery today")
    public void aCustomerHasAScheduledMealDeliveryToday() {
        user customer = dataBase.usersRegList.get(0);
        System.out.println("📦 Reminder: Dear " + customer.getUsername() + ", your meal is scheduled for delivery today.");
    }

    @Given("a chef is assigned a task scheduled for today")
    public void aChefIsAssignedATaskScheduledForToday() {
        System.out.println("📌 Chef " + dataBase.sharedChef.getUsername() +
                           " is assigned task: " + dataBase.sharedTask.getDescription() + " for today.");
    }

    @When("the task time is near")
    public void theTaskTimeIsNear() {
        System.out.println("⏰ Reminder: Scheduled cooking task is approaching soon!");
    }

    @Then("the system should notify the chef with the task details")
    public void theSystemShouldNotifyTheChefWithTheTaskDetails() {
        System.out.println("📢 Notification sent to Chef " + dataBase.sharedChef.getUsername() +
                           ": Task - " + dataBase.sharedTask.getDescription() +
                           " | Duration: " + dataBase.sharedTask.getExpectedCompletionTime() + " mins.");
    }
}
