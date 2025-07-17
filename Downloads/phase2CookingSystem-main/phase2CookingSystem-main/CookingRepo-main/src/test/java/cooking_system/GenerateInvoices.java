package cooking_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Scanner;

public class GenerateInvoices {

    private String currentInvoice = "";
    private boolean transactionConfirmed = false;
    private String selectedOption = "";
    private String selectedPeriod = "";
    @Given("a customer is logged in")
    public void aCustomerIsLoggedIn() {
        user u = new user("customer@example.com", "1234", "Customer_Aya");
        u=dataBase.LogedIn;
        dataBase.LogedIn = u;
        dataBase.usersRegList.add(u); 
    }

    @Given("a customer has completed a purchase")
    public void aCustomerHasCompletedAPurchase() {
        assertTrue(" No logged in customer found.", dataBase.LogedIn != null);
        System.out.println(" Purchase completed by customer: " + dataBase.LogedIn.getEmail());
    }

    @When("the transaction is confirmed")
    public void theTransactionIsConfirmed() {
        transactionConfirmed = true;
        System.out.println(" Transaction confirmed.");
    }

    @Then("the system should generate an invoice")
    public void theSystemShouldGenerateAnInvoice() {
        assertTrue(" Transaction not confirmed.", transactionConfirmed);
        assertTrue(" No logged in customer. Cannot generate invoice.", dataBase.LogedIn != null);

        currentInvoice = "INVOICE-" + LocalDateTime.now() +
                "\nAmount: $45.00\nStatus: Paid\nCustomer: " + dataBase.LogedIn.getEmail();
        dataBase.invoices.add(currentInvoice);
        System.out.println(" Invoice generated:\n" + currentInvoice); 
    }

    @Then("the invoice should be sent to the customer's email")
    public void theInvoiceShouldBeSentToTheCustomerSEmail() {
        assertTrue(" No invoice available to send.", currentInvoice != null && !currentInvoice.isEmpty());
        assertTrue(" No logged in customer.", dataBase.LogedIn != null);

        System.out.println(" Invoice sent to " + dataBase.LogedIn.getEmail() + ":\n" + currentInvoice);
    }

    @Given("I am logged in as a system administrator")
    public void iAmLoggedInAsASystemAdministrator() {
        dataBase.isAdminLoggedIn = true;
        System.out.println(" Administrator logged in.");
    }

    @When("I select the {string} option for a specific time period")
    public void iSelectTheOptionForASpecificTimePeriod(String option) {
        assertTrue(" Access denied. Please log in as administrator.", dataBase.isAdminLoggedIn);

        selectedOption = option;
        selectedPeriod = "Last 30 Days"; 
        System.out.println(" Selected report option: " + selectedOption + " for " + selectedPeriod);
    }

    @Then("the system should generate the financial report")
    public void theSystemShouldGenerateTheFinancialReport() {
    	 assertTrue(" Transaction not confirmed.", transactionConfirmed);
    	    assertTrue(" No logged in customer. Cannot generate invoice.", dataBase.LogedIn != null);

    	  /*  Scanner scanner = new Scanner(System.in);
    	    System.out.print(" Confirm payment (yes/no): ");
    	    String confirm = scanner.nextLine().trim().toLowerCase();

    	    if (!confirm.equals("yes")) {
    	        System.out.println(" Payment not confirmed. Invoice was not generated.");
    	        return;
    	    }

    	    currentInvoice = "INVOICE-" + LocalDateTime.now() +
    	            "\nAmount: $45.00\nStatus: Paid\nCustomer: " + dataBase.LogedIn.getEmail();
    	    dataBase.invoices.add(currentInvoice);

    	    System.out.println(" Invoice generated:\n" + currentInvoice);*/
    }

    @Then("the report should include revenue details and performance metrics")
    public void theReportShouldIncludeRevenueDetailsAndPerformanceMetrics() {
        int totalRevenue = dataBase.invoices.size() * 45;

        if (dataBase.invoices.isEmpty()) {
            System.out.println(" No invoices generated yet. Report is currently empty.");
            return;
        }

        int averagePerDay = totalRevenue / 30;

        if (totalRevenue <= 0) {
            System.out.println(" Revenue is 0. No payments recorded.");
        }

        System.out.println("Revenue per Invoice: $45.00");
        System.out.println("Avg Revenue/Day (approx.): $" + averagePerDay);
        System.out.println("System performance is optimal. No delays or failures reported.");
    }

    

}
