package cooking_system;




import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "cases",  // Corrected feature file path
    plugin = { "summary", "html:target/cucumber/wikipedia.html" },
    monochrome = true,
    snippets = CucumberOptions.SnippetType.CAMELCASE,
    glue = { "cooking_system" } // Ensure this package contains step definitions
)
public class test {
}
