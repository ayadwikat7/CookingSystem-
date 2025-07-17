/*package cooking_system;

import java.util.*;

public class choiseUser {

    static Scanner scanner = new Scanner(System.in);
    private static List<String> allowedIngredientsList = new ArrayList<>();

    public static void showManagerDashboard(KitchenManager manager) {
        System.out.println("\nWelcome Kitchen Manager " + manager.getUsername() + "!");
        dataBase.kitchenManager = manager;
        Assigning_Tasks scenario = new Assigning_Tasks();

        while (true) {
            System.out.println("\n📋 Manager Menu:");
            System.out.println("1. Add sample chefs");
            System.out.println("2. Create a new task");
            System.out.println("3. Assign task to available chef");
            System.out.println("4. Reassign task (simulate unavailable chef)");
            System.out.println("5. Notify chef about current task");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    scenario.theKitchenManagerAccessesTheTaskAssignmentSystem();
                    break;
                case "2":
                    System.out.print("Enter task description: ");
                    String desc = scanner.nextLine();

                    System.out.print("Enter required expertise (e.g., Pastry): ");
                    String expertise = scanner.nextLine();

                    System.out.print("Enter expected completion time (minutes): ");
                    int time = Integer.parseInt(scanner.nextLine());

                    dataBase.task = new Task(desc, expertise, time);
                    System.out.println(" Task created: " + desc);
                    break;

                case "3":
                    scenario.theyAssignTasksBasedOnWorkloadAndExpertise();
                    scenario.theChefsShouldReceiveTaskAssignmentsWithDetails();
                    break;

                case "4":
                    scenario.aChefIsUnableToCompleteTheirAssignedTask();
                    scenario.theKitchenManagerReassignsTheTask();
                    scenario.anotherAvailableChefShouldReceiveTheNewAssignment();
                    break;

                case "5":
                    scenario.aChefHasBeenAssignedACookingTask();
                    scenario.theSystemNotifiesTheChef();
                    scenario.theyShouldReceiveDetailsAboutTheTaskAndExpectedCompletionTime();
                    break;

                case "6":
                    System.out.println(" Exiting manager menu...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void showChefDashboard(Chef chef) {
        System.out.println("\n Welcome Chef " + chef.getUsername() + "!");
        System.out.println("Expertise: " + chef.getExpertise());

        if (chef.isAvailable()) {
            System.out.println(" You are available for new tasks.");

            List<Task> matchedTasks = new ArrayList<>();
            for (Task task : dataBase.availableTasks) {
                if (task.getExpertise().equalsIgnoreCase(chef.getExpertise())) {
                    matchedTasks.add(task);
                }
            }

            if (matchedTasks.isEmpty()) {
                System.out.println(" No tasks currently available for your expertise.");
            } else {
                System.out.println(" Available tasks:");
                for (int i = 0; i < matchedTasks.size(); i++) {
                    Task task = matchedTasks.get(i);
                    System.out.println((i + 1) + ". " + task.getDescription() + " (" + task.getExpectedCompletionTime() + " mins)");
                }

                System.out.print("Choose a task to accept (or 0 to cancel): ");
                String input = scanner.nextLine();
                try {
                    int selected = Integer.parseInt(input);
                    if (selected == 0) {
                        System.out.println("No task assigned.");
                        return;
                    }

                    if (selected >= 1 && selected <= matchedTasks.size()) {
                        Task chosenTask = matchedTasks.get(selected - 1);
                        chef.assignTask(chosenTask);
                        chef.setAvailable(false);
                        dataBase.sharedTask = chosenTask;

                        System.out.println(" Task '" + chosenTask.getDescription() + "' has been assigned to you!");
                    } else {
                        System.out.println(" Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }

        } else {
            System.out.println("⏳ You are currently assigned tasks.");
        }

        if (chef.hasPendingReminders()) {
            System.out.println(" You have new reminders:");
            for (String reminder : chef.getReminders()) {
                System.out.println(" - " + reminder);
            }
        } else {
            System.out.println(" No reminders at the moment.");
        }
    }

    public static void showCustomerDashboard(user customer) {
        System.out.println("\n Welcome " + customer.getUsername() + "!");
        int option = 0;

        while (option != 4) {
            System.out.println("1️. Set dietary preferences");
            System.out.println("2️. Choose ingredients");
            System.out.println("3️. Get meal recommendation");
            System.out.println("4️. Exit");

            System.out.print(" Choose an option: ");
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number (1 to 4).");
                continue;
            }

            switch (option) {
                case 1:
                    handleSetDietaryPreferences(customer);
                    break;
                case 2:
                    handleChooseIngredients(customer);
                    break;
                case 3:
                    handleRecommendMeal(customer);
                    break;
                case 4:
                    System.out.println(" Goodbye " + customer.getUsername() + "!");
                    break;
                default:
                    System.out.println(" Invalid choice.");
            }
        }
    }

    static void handleSetDietaryPreferences(user customer) {
        dietary_preferences.DietType[] types = dietary_preferences.DietType.values();

        System.out.println(" Available diet types:");
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        System.out.print(" Enter the number of your dietary preference (e.g., 1 for VEGAN): ");
        String input = scanner.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > types.length) {
                System.out.println(" Invalid number. Please choose between 1 and " + types.length);
                return;
            }

            dietary_preferences.DietType selected = types[choice - 1];
            dietary_preferences pref = new dietary_preferences(selected.name(), selected);
            storDietary.setDietary(customer, pref);

            System.out.println("\n You chose: " + selected);
            System.out.println(" Allowed ingredients for your diet:");

            allowedIngredientsList.clear();
            dietary_preferences.ingredientRestrictions.forEach((ingredient, restrictions) -> {
                if (!restrictions.contains(selected)) {
                    allowedIngredientsList.add(ingredient);
                }
            });

            for (int i = 0; i < allowedIngredientsList.size(); i++) {
                System.out.println((i + 1) + ". " + allowedIngredientsList.get(i));
            }

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input. Please enter a number.");
        }
    }

    static void handleChooseIngredients(user customer) {
        if (customer.getDietary() == null || customer.getDietary().userRestrictions.isEmpty()) {
            System.out.println("❗ Please set your dietary preference first before choosing ingredients.");
            return;
        }

        System.out.print(" Enter ingredient numbers (comma-separated): ");
        String input = scanner.nextLine();
        String[] indices = input.split("\\s*,\\s*");

        if (dataBase.customerIngredients == null) {
            dataBase.customerIngredients = new ArrayList<>();
        }

        for (String indexStr : indices) {
            try {
                int index = Integer.parseInt(indexStr) - 1;
                if (index >= 0 && index < allowedIngredientsList.size()) {
                    String ingredient = allowedIngredientsList.get(index);
                    if (!dataBase.customerIngredients.contains(ingredient)) {
                        dataBase.customerIngredients.add(ingredient);
                    }
                } else {
                    System.out.println("Invalid ingredient number: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input: " + indexStr);
            }
        }

        System.out.println(" All ingredients saved so far: " + dataBase.customerIngredients);
    }

    static void handleRecommendMeal(user customer) {
        if (customer.getDietary() == null || customer.getDietary().userRestrictions.isEmpty()) {
            System.out.println("❗ Please set your dietary preference first before getting a meal recommendation.");
            return;
        }

        if (dataBase.customerIngredients == null || dataBase.customerIngredients.isEmpty()) {
            System.out.println("❗ You must choose ingredients first.");
            return;
        }

        dietary_preferences.DietType selectedDiet = customer.getDietary().userRestrictions.iterator().next();

        Ai ai = new Ai();
        ai.setInputs(
            selectedDiet != null ? Set.of(selectedDiet) : null,
            dataBase.customerIngredients,
            Integer.MAX_VALUE
        );

        ai.recommend();
    }

    private static void runAdminInvoiceReportScenario() {
        GenerateInvoices invoice = new GenerateInvoices();
        invoice.iAmLoggedInAsASystemAdministrator();
        invoice.iSelectTheOptionForASpecificTimePeriod("Financial Summary");
        invoice.theSystemShouldGenerateTheFinancialReport();
        invoice.theReportShouldIncludeRevenueDetailsAndPerformanceMetrics();
    }
}*/
