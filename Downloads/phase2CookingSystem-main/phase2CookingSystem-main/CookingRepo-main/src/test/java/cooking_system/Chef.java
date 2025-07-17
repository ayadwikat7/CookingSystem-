package cooking_system;

import java.util.ArrayList;
import java.util.List;

public class Chef extends user {

    private String expertise;
    private boolean available;
    private List<Task> assignedTasks;


    public Chef(String email, String password, String username, String expertise) {
        super(email, password, username); 
        this.expertise = expertise;
        this.available = true;
        this.assignedTasks = new ArrayList<>();
    }

    public Chef(String name, String expertise) {
        super("", "", name);  
        this.expertise = expertise;
        this.available = true;
        this.assignedTasks = new ArrayList<>();
    }



    public String getExpertise() {
        return expertise;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

  

    public void assignTask(Task task) {
        assignedTasks.add(task);
    }

  
    
}
