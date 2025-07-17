package cooking_system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class signup {

    String message = "";
   boolean issign=false;
    @Given("the system is running")
    public void theSystemIsRunning() {
        dataBase.usersRegList.clear(); 
        dataBase.usersnew = null;
        message = "";
    }

    @Given("an account already exists with email {string}")
    public void anAccountAlreadyExistsWithEmail(String email) {
        user existingUser = new user();
        existingUser.setEmail(email);
        existingUser.setPassword("default123");
        dataBase.usersRegList.add(existingUser);
    } 

    @When("I enter signup details with email {string} and password {string}")
    public void iEnterSignupDetailsWithEmailAndPassword(String email, String password) {
        if ((email == null || !email.contains("@")) ||(password == null || password.length() < 6)) {
        	

            if (password == null || password.length() < 6) {
                message = "Password must be at least 6 characters"; 
                System.out.println( message);
                return;
            }
            message = "Invalid email format";
            System.out.println( message);
            message="signup filed ddd";
            System.out.println( message);
            return;
        }
        issign=true;


        for (user u : dataBase.usersRegList) {
            if (u.getEmail().equals(email)) {
                message = "Email already registered";
                System.out.println( message);
                return;
            }
        }

        user newUser = new user(email, password,"aya");
        dataBase.usersnew = newUser;
        dataBase.usersRegList.add(newUser);

        message = "Signup successful";
        System.out.println( message);
    }

    @Then("the account should be created successfully")
    public void theAccountShouldBeCreatedSuccessfully() {
        assertNotNull("New user should not be null", dataBase.usersnew);
        assertTrue("User should be in registered list", dataBase.usersRegList.contains(dataBase.usersnew));
    }

    @Then("I should see a signup message {string}")
    public void iShouldSeeSignupMessage(String expectedMessage) {
        assertEquals(expectedMessage, message);
    }
}
