package cooking_system;

import static org.junit.Assert.*;

import java.util.*;
import cooking_system.user;
import cooking_system.dataBase;
import io.cucumber.java.en.*;

public class Login {

    String loginMessage = "";


    @Given("an account already exists with email {string} and password {string}")
    public void anAccountAlreadyExistsWithEmailAndPassword(String email, String password) {
        user existingUser = new user();
        existingUser.setEmail(email);
        existingUser.setPassword(password);

        dataBase.usersRegList.add(existingUser);
    }

    @When("I enter login email {string} and password {string}")
    public void iEnterLoginEmailAndPassword(String email, String password) {
        if (email == null || !email.contains("@")) {
            loginMessage = "Invalid email format";
            System.out.println(loginMessage);
            if (password == null || password.length() < 6) {
                loginMessage = "Password is too short";
                System.out.println(loginMessage);
                return;
            }
            loginMessage="login filed";
            System.out.println(loginMessage);
            return;
        } 



        for (user u : dataBase.usersRegList) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                dataBase.LogedIn = u;

                if (!dataBase.AllLogedUser.contains(u)) {
                    dataBase.AllLogedUser.add(u);
                }

                loginMessage = "Login successful";
                return;
            }
        }

        loginMessage = "Invalid username or password";
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        assertNotNull(dataBase.LogedIn);
        assertTrue(dataBase.AllLogedUser.contains(dataBase.LogedIn));
        assertEquals("Login successful", loginMessage);
    }

}
