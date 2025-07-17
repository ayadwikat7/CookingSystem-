/*package cooking_system;

import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            enterToSystem(scanner);

            if (dataBase.LogedIn != null) {
                if (dataBase.LogedIn instanceof Chef) {
                    Chef chef = (Chef) dataBase.LogedIn;
                    dataBase.sharedChef = chef;

                    while (true) {
                        System.out.println("\nChef Menu:");
                        System.out.println("1. View Dashboard");
                        System.out.println("2. Run Full Task Assignment Scenario");
                        System.out.println("3. Run Meal Reminder Scenario");
                        System.out.println("4. Exit");

                        System.out.print("Choose option: ");
                        String chefChoice = scanner.nextLine().trim();

                        switch (chefChoice) {
                            case "1":
                                choiseUser.showChefDashboard(chef);
                                break;
                            case "2":
                                runFullTaskAssignmentScenario();
                                break;
                            case "3":
                                runOrderAndMenuReminderScenario();
                                break;
                            case "4":
                                System.out.println("Exiting chef menu...");
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }

                        if (chefChoice.equals("4")) break;
                    }

                } else if (dataBase.LogedIn instanceof KitchenManager) {
                    KitchenManager manager = (KitchenManager) dataBase.LogedIn;

                    while (true) {
                        System.out.println("\n Manager Menu:");
                        System.out.println("1. View Dashboard");
                        System.out.println("2. Run Task Assignment Scenario");
                        System.out.println("3. Run Invoice Report Scenario");
                        System.out.println("4. Exit");

                        System.out.print("Choose an option: ");
                        String input = scanner.nextLine().trim();

                        switch (input) {
                            case "1":
                                choiseUser.showManagerDashboard(manager);
                                break;
                            case "2":
                                runFullTaskAssignmentScenario();
                                break;
                            case "3":
                                runAdminInvoiceReportScenario();
                                break;
                            case "4":
                                System.out.println("Exiting manager menu...");
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }

                        if (input.equals("4")) break;
                    }

                } else {
                    while (true) {
                        System.out.println("\n Customer Menu:");
                        System.out.println("1. Set dietary preferences");
                        System.out.println("2. Choose ingredients");
                        System.out.println("3. Get meal recommendation");
                        System.out.println("4. Run Invoice Scenario");
                        System.out.println("5. Exit");

                        System.out.print("Choose an option: ");
                        String choice = scanner.nextLine();

                        switch (choice) {
                            case "1":
                                choiseUser.handleSetDietaryPreferences(dataBase.LogedIn);
                                break;
                            case "2":
                                choiseUser.handleChooseIngredients(dataBase.LogedIn);
                                break;
                            case "3":
                                choiseUser.handleRecommendMeal(dataBase.LogedIn);
                                break;
                            case "4":
                                if (dataBase.LogedIn.getDietary() == null || dataBase.LogedIn.getDietary().userRestrictions.isEmpty()) {
                                    System.out.println("❗ Please set your dietary preference before proceeding with an invoice.");
                                } else {
                                    runGenerateInvoiceScenario();
                                }
                                break;

                            case "5":
                                System.out.println("Goodbye " + dataBase.LogedIn.getUsername() + "!");
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }

                        if (choice.equals("5")) break;
                    }
                }
            }

            // 💬 بعد الخروج من قائمة المستخدم:
            System.out.print("\nDo you want to log in or sign up again? (yes/no): ");
            String again = scanner.nextLine().trim().toLowerCase();
            if (!again.equals("yes")) {
                System.out.println(" Exiting system. Goodbye!");
                break;
            }

            // تنظيف المستخدم الحالي
            dataBase.LogedIn = null;
        }
    }

    private static void enterToSystem(Scanner scanner) {
        while (true) {
            System.out.println(" Welcome to the Cooking System");
            System.out.println("1. Login");
            System.out.println("2. Signup");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 1) {
                    performLogin(scanner);
                    break;
                } else if (choice == 2) {
                    performSignup(scanner);
                    System.out.println("\nPlease login to continue:");
                    performLogin(scanner);
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
            }
        }
    }

    private static void performLogin(Scanner scanner) {
        System.out.print("Enter login email: ");
        String email = scanner.nextLine();

        System.out.print("Enter login password: ");
        String password = scanner.nextLine();

        Login loginObj = new Login();
        loginObj.iEnterLoginEmailAndPassword(email, password);

        if (dataBase.LogedIn != null) {
            System.out.println("Login successful. Welcome, " + dataBase.LogedIn.getUsername());
        } else {
            System.out.println("Login failed. You do not have an account yet. Please signup and try again.");
            enterToSystem(scanner);
        }
    }

    private static void performSignup(Scanner scanner) {
        System.out.print("Enter signup username: ");
        String username = scanner.nextLine();

        System.out.print("Enter signup email: ");
        String email = scanner.nextLine();

        System.out.print("Enter signup password: ");
        String password = scanner.nextLine();

        signup signupObj = new signup();
        signupObj.iEnterSignupDetailsWithEmailAndPassword(email, password);

        if (signupObj.issign == true) {
            System.out.println("Select user type:");
            System.out.println("1. Chef");
            System.out.println("2. Kitchen Manager");
            System.out.println("3. Regular Customer");
            System.out.print("Enter choice: ");
            int userType = scanner.nextInt();
            scanner.nextLine();

            if (dataBase.usersnew != null) {
                dataBase.usersnew.setName(username);

                user createdUser;
                switch (userType) {
                    case 1:
                        List<String> expertises = Arrays.asList("Pastry", "Vegetarian", "Italian Cuisine");
                        System.out.println("Select your expertise:");
                        for (int i = 0; i < expertises.size(); i++) {
                            System.out.println((i + 1) + ". " + expertises.get(i));
                        }
                        System.out.print("Enter choice: ");
                        int expChoice = scanner.nextInt();
                        scanner.nextLine();
                        String selectedExpertise = expertises.get(expChoice - 1);
                        createdUser = new Chef(email, password, username, selectedExpertise);
                        break;
                    case 2:
                        createdUser = new KitchenManager(email, password, username);
                        break;
                    case 3:
                    default:
                        createdUser = new user(email, password, username);
                        break;
                }

                dataBase.usersnew = createdUser;
                dataBase.usersRegList.removeIf(u -> u.getEmail().equals(email));
                dataBase.usersRegList.add(createdUser);

                System.out.println("Signup successful. Welcome, " + username + "!");
            } else {
                System.out.println("Signup failed. Please try again.");
                performSignup(scanner);
            }
        } else {
            System.out.println("Signup failed. Please try again.");
            enterToSystem(scanner);
        }
    }

    private static void runFullTaskAssignmentScenario() {
        System.out.println("\nRunning Task Assignment Scenarios...\n");
        Assigning_Tasks scenario = new Assigning_Tasks();

        scenario.theKitchenManagerAccessesTheTaskAssignmentSystem();
        scenario.theyAssignTasksBasedOnWorkloadAndExpertise();
        scenario.theChefsShouldReceiveTaskAssignmentsWithDetails();

        System.out.println("\nReassigning task due to unavailability...\n");
        scenario.aChefIsUnableToCompleteTheirAssignedTask();
        scenario.theKitchenManagerReassignsTheTask();
        scenario.anotherAvailableChefShouldReceiveTheNewAssignment();

        System.out.println("\nNotification and task details...\n");
        scenario.aChefHasBeenAssignedACookingTask();
        scenario.theSystemNotifiesTheChef();
        scenario.theyShouldReceiveDetailsAboutTheTaskAndExpectedCompletionTime();

        System.out.println("\nAll scenarios completed.\n");
    }

    private static void runOrderAndMenuReminderScenario() {
        System.out.println("\nRunning Order and Menu Reminder Scenario...\n");
        OrderAndMenuReminder reminder = new OrderAndMenuReminder();

        reminder.aCustomerHasScheduledAMealDelivery();
        reminder.theDeliveryTimeIsApproaching();
        reminder.theSystemShouldSendAReminderNotificationToTheCustomer();

        reminder.aChefHasBeenAssignedAMealPreparationTask();
        reminder.theScheduledCookingTimeIsNear();
        reminder.theSystemShouldSendANotificationWithTaskDetails();

        reminder.aCustomerDidNotAcknowledgeTheFirstReminder();
        reminder.theDeliveryTimeIsVeryClose();
        reminder.theSystemShouldSendAFollowUpReminderToTheCustome();

        System.out.println("\nAll reminder-related scenarios completed.\n");
    }

    private static void runGenerateInvoiceScenario() {
        System.out.println("\nRunning Invoice Scenario...\n");
        GenerateInvoices invoice = new GenerateInvoices();

        invoice.aCustomerIsLoggedIn();
        invoice.aCustomerHasCompletedAPurchase();
        invoice.theTransactionIsConfirmed();
        invoice.theSystemShouldGenerateAnInvoice();
        invoice.theInvoiceShouldBeSentToTheCustomerSEmail();
        System.out.println("\nInvoice scenario completed.\n");
    }

    private static void runAdminInvoiceReportScenario() {
        System.out.println("\nRunning Admin Financial Report Scenario...\n");
        GenerateInvoices invoice = new GenerateInvoices();

        invoice.iAmLoggedInAsASystemAdministrator();
        invoice.iSelectTheOptionForASpecificTimePeriod("Financial Summary");
        invoice.theSystemShouldGenerateTheFinancialReport();
        invoice.theReportShouldIncludeRevenueDetailsAndPerformanceMetrics();

        System.out.println("\nFinancial report scenario completed.\n");
    }
}
*/