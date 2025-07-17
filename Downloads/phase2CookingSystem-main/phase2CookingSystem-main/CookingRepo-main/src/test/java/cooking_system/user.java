package cooking_system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class user {

    private String email;
    private String password;
   
    private String username;
    private dietary_preferences dietary;

   
    private List<String> reminders;
    private boolean hasAcknowledgedReminder;

    
    private List<Order> orderHistory;

    
    public user(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.dietary = null;

        this.reminders = new ArrayList<>();
        this.hasAcknowledgedReminder = false;

        this.orderHistory = new ArrayList<>();
    }

   
    public user() {
		
	}


	

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public dietary_preferences getDietary() {
        return dietary;
    }


    public void setPassword(String password) {
        this.password = password;
    }

 

    public void setDietary(dietary_preferences dietary) {
        this.dietary = dietary;
    }

    public boolean isChef() {
        return email.startsWith("Chef_");
    }

    
    public void addReminder(String reminderMessage) {
        reminders.add(reminderMessage);
        hasAcknowledgedReminder = false;
        System.out.println("Reminder added for user " + username + ": " + reminderMessage);
    }

    public List<String> getReminders() {
        return reminders;
    }



    public void acknowledgeReminder() {
        if (!reminders.isEmpty()) {
            hasAcknowledgedReminder = true;
            System.out.println("User " + username + " acknowledged the reminder: " + reminders.get(reminders.size() - 1));
        } else {
            System.out.println("No reminders to acknowledge for user " + username);
        }
    }

    public boolean hasAcknowledgedReminder() {
        return hasAcknowledgedReminder;
    }

    public boolean hasPendingReminders() { 
        return !reminders.isEmpty();
    }





	public void setEmail(String email2) {
		email=email2;
		
		
	}
	public void setName(String na) {
		username=na;
	}
}
