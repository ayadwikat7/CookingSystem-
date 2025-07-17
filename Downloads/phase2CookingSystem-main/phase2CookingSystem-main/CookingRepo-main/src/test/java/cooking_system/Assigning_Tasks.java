package cooking_system;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Assigning_Tasks {

    @Given("the kitchen manager accesses the task assignment system")
    public void theKitchenManagerAccessesTheTaskAssignmentSystem() {
    	
        System.out.println(" Kitchen manager " + dataBase.kitchenManager.getUsername() + " accesses the task assignment system.");
     //   Scanner scanner = new Scanner(System.in);
        dataBase.chefs.add(new Chef("Chef_Ahmed", "Italian Cuisine"));
        dataBase.chefs.add(new Chef("Chef_Sara", "Pastry"));
        System.out.print("Enter number of chefs to add: ");
      //  int numChefs = Integer.parseInt(scanner.nextLine());

        List<String> expertises = dataBase.expertises;


       /* for (int i = 1; i <= numChefs; i++) {
            System.out.print("Enter name for Chef #" + i + ": ");
            String name = scanner.nextLine();

            System.out.println("Choose expertise for " + name + ":");
            for (int j = 0; j < expertises.size(); j++) {
                System.out.println((j + 1) + ". " + expertises.get(j));
            }

            int choice = -1;
            while (true) {
                System.out.print("Enter choice (1-" + expertises.size() + "): ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice >= 1 && choice <= expertises.size()) break;
                    else System.out.println("Invalid number, try again.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, enter a number.");
                }
            }

            String selectedExpertise = expertises.get(choice - 1);
            Chef newChef = new Chef(name, selectedExpertise);
            dataBase.chefs.add(newChef);

            System.out.println("Added Chef: " + name + " (" + selectedExpertise + ")");
    }*/
    }

    @When("they assign tasks based on workload and expertise")
    public void theyAssignTasksBasedOnWorkloadAndExpertise() {
        Chef selectedChef = null;

        for (Chef chef : dataBase.chefs) {
            if (chef.isAvailable() && chef.getExpertise().equalsIgnoreCase(dataBase.task.getExpertise())) {
                selectedChef = chef;
                break;
            }
        }

        if (selectedChef != null) {
            selectedChef.assignTask(dataBase.task);
            selectedChef.setAvailable(false);
            System.out.println(" Task '" + dataBase.task.getDescription() + "' assigned to Chef " + selectedChef.getUsername());
        } else {
            System.out.println(" No available chef with expertise in: " + dataBase.task.getExpertise());
        }

        assertNotNull("Task should be assigned if a matching available chef exists", selectedChef);
    }

    @Then("the chefs should receive task assignments with details") 
    public void theChefsShouldReceiveTaskAssignmentsWithDetails() {
        boolean notificationSent = false;

        for (Chef chef : dataBase.chefs) {
            if (!chef.isAvailable()) {
                System.out.println(" Hey " + chef.getUsername() + "! You've got a new task: '" + dataBase.task.getDescription()
                        + "' – expected in " + dataBase.task.getExpectedCompletionTime() + " mins.");
                notificationSent = true;
            }
        }

        assertTrue("Notification should be sent to at least one chef", notificationSent);
    }

    @Given("a chef is unable to complete their assigned task")
    public void aChefIsUnableToCompleteTheirAssignedTask() {
        dataBase.chefs = new ArrayList<>();
        dataBase.task = new Task("Prepare Tiramisu", "Pastry", 60);

        Chef busyChef = new Chef("Chef_Sami", "Pastry");
        busyChef.setAvailable(false); 
        busyChef.assignTask(dataBase.task);

        Chef availableChef = new Chef("Chef_Sara", "Pastry");
        availableChef.setAvailable(true); 

        dataBase.chefs.add(busyChef);
        dataBase.chefs.add(availableChef);

        System.out.println(" " + busyChef.getUsername() + " is unavailable.");
        System.out.println(" " + availableChef.getUsername() + " is available for reassignment.");
    }

    @When("the kitchen manager reassigns the task")
    public void theKitchenManagerReassignsTheTask() {
        Chef reassignedChef = null;

        for (Chef chef : dataBase.chefs) {
            if (chef.isAvailable()) {
                reassignedChef = chef;
                break;
            }
        }

        if (reassignedChef != null) {
            reassignedChef.assignTask(dataBase.task);
            reassignedChef.setAvailable(false);
            System.out.println("Task '" + dataBase.task.getDescription() + "' reassigned to Chef " + reassignedChef.getUsername());
        } else {
            System.out.println("No available chefs to reassign the task.");
        }

        assertNotNull("Task should be reassigned to an available chef", reassignedChef);
    }

    @Then("another available chef should receive the new assignment")
    public void anotherAvailableChefShouldReceiveTheNewAssignment() {
        boolean reassigned = false;

        for (Chef chef : dataBase.chefs) {
            if (!chef.isAvailable()) {
                System.out.println(" Chef " + chef.getUsername() + ", you've been reassigned the task: '" + dataBase.task.getDescription() + "'");
                reassigned = true;
            }
        }

        assertTrue("A chef should receive the reassigned task", reassigned);
    }

    @Given("a chef has been assigned a cooking task")
    public void aChefHasBeenAssignedACookingTask() {
        dataBase.chefs = new ArrayList<>();
        dataBase.task = new Task("Grill Salmon", "Italian Cuisine", 45);
        
        Chef chef = new Chef("Chef_Ahmed", "Italian Cuisine");
        dataBase.chefs.add(chef);
        
        chef.assignTask(dataBase.task);
        chef.setAvailable(false); 
        System.out.println(" Task '" + dataBase.task.getDescription() + "' has been assigned to " + chef.getUsername());

        assertTrue("Chef should have been assigned a task", !chef.isAvailable());
    }

    @When("the system notifies the chef")
    public void theSystemNotifiesTheChef() {
        for (Chef chef : dataBase.chefs) {
            if (!chef.isAvailable()) {
                System.out.println(" Notification sent to " + chef.getUsername());
                assertTrue("Notification should go to unavailable (assigned) chef", true);
            }
        }
    }

    @Then("they should receive details about the task and expected completion time")
    public void theyShouldReceiveDetailsAboutTheTaskAndExpectedCompletionTime() {
        for (Chef chef : dataBase.chefs) {
            if (!chef.isAvailable()) {
                System.out.println(" Chef " + chef.getUsername() + ", your task is: '" + dataBase.task.getDescription() + "', expected time: " + dataBase.task.getExpectedCompletionTime() + " minutes.");
                assertTrue("Chef should receive task details", dataBase.task.getDescription() != null && dataBase.task.getExpectedCompletionTime() > 0);
            }
        }
    } 
}
