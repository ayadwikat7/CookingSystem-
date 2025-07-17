package cooking_system;

public class Task {

    private String description;
    private String expertise;
    private int expectedCompletionTime;

    public Task(String description, String expertise, int expectedCompletionTime) {
        this.description = description;
        this.expertise = expertise;
        this.expectedCompletionTime = expectedCompletionTime;
    }

    public String getDescription() {
        return description;
    }

    public String getExpertise() {
        return expertise;
    }

    public int getExpectedCompletionTime() {
        return expectedCompletionTime;
    }
}
