package cooking_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class OrderAndMenuReminder {

	private Chef chef = new Chef("Chef_Ahmed", "Italian Cuisine");
    private Task task = dataBase.sharedTask;

    @Given("a customer has scheduled a meal delivery")
    public void aCustomerHasScheduledAMealDelivery() {
        user customer = new user("customer@example.com", "password123", "JohnDoe");
        dataBase.usersRegList.add(customer);

        System.out.println("Customer " + customer.getUsername() + " has scheduled a meal delivery.");
        assertTrue("Customer should be added to the system.",
                   dataBase.usersRegList.stream().anyMatch(u -> u.getEmail().equals("customer@example.com")));
    }

    @When("the delivery time is approaching")
    public void theDeliveryTimeIsApproaching() {
        for (user customer : dataBase.usersRegList) {
            customer.addReminder("Reminder: Your meal delivery is approaching soon!");
            assertTrue("Customer should have a pending reminder.", customer.hasPendingReminders());
        }
    }

    @Then("the system should send a reminder notification to the customer")
    public void theSystemShouldSendAReminderNotificationToTheCustomer() {
        for (user customer : dataBase.usersRegList) {
            if (customer.hasPendingReminders()) {
                System.out.println("Sending reminder to customer " + customer.getUsername() + ": "
                        + customer.getReminders().get(customer.getReminders().size() - 1));
                customer.acknowledgeReminder();
                assertTrue("Customer should have acknowledged the reminder.", customer.hasAcknowledgedReminder());
            } else {
                System.out.println("No pending reminders for " + customer.getUsername());
            }
        }
    }

    @Given("a chef has been assigned a meal preparation task")
    public void aChefHasBeenAssignedAMealPreparationTask() {
        chef.assignTask(task);
        System.out.println("Chef " + chef.getUsername() + " has been assigned task: " + task.getDescription());
        assertTrue("Chef and task should be initialized.", chef != null && task != null);
    }

    @When("the scheduled cooking time is near")
    public void theScheduledCookingTimeIsNear() {
        System.out.println("Scheduled cooking time is near for chef " + chef.getUsername());
        assertTrue("Chef should be available when cooking time is near.", chef.isAvailable());
    }

    @Then("the system should send a notification with task details")
    public void theSystemShouldSendANotificationWithTaskDetails() {
        System.out.println("Notification to Chef " + chef.getUsername() + ": Prepare " + task.getDescription()
                + ". Expected completion time: " + task.getExpectedCompletionTime() + " minutes.");
        assertTrue("Expected completion time should be positive.", task.getExpectedCompletionTime() > 0);
    }

    @Given("a customer did not acknowledge the first reminder")
    public void aCustomerDidNotAcknowledgeTheFirstReminder() {
        for (user customer : dataBase.usersRegList) {
            if (customer.hasPendingReminders() && !customer.hasAcknowledgedReminder()) {
                System.out.println("Customer " + customer.getUsername() + " did not acknowledge the first reminder.");
                assertTrue("Customer should not have acknowledged the reminder.", !customer.hasAcknowledgedReminder());
            }
        }
    }

    @When("the delivery time is very close")
    public void theDeliveryTimeIsVeryClose() {
        System.out.println("The delivery time is very close for all scheduled deliveries.");
    }

    @Then("the system should send a follow-up reminder to the custome")
    public void theSystemShouldSendAFollowUpReminderToTheCustome() {
        for (user customer : dataBase.usersRegList) {
            if (!customer.hasAcknowledgedReminder()) {
                customer.addReminder("URGENT: Your delivery is arriving VERY soon! Please be ready.");
                System.out.println("Follow-up reminder sent to " + customer.getUsername());
                assertTrue("Customer should have a follow-up reminder.", customer.hasPendingReminders());
            }
        }
    }
}
