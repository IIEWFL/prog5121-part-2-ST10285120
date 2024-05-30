import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/*
 * this unit test class is adapted from a Java unit test tutorial
 *Author: Coding with John: https://www.youtube.com/@CodingWithJohn
 * Video: Java Unit Testinng with Junit - How to Create and Use Unit Tests: https://www.youtube.com/watch?v=vZm0lHciFsQ&t=184s
*/
public class UserRegistrationLoginTest {

    @Test
    public void testLoginSuccessful() {
        UserRegistrationLogin login = new UserRegistrationLogin();
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        UserRegistrationLogin login = new UserRegistrationLogin();
        assertFalse(login.loginUser("invalid_username", "invalid_password"));
    }

    @Test
    public void testUsernameCorrectlyFormatted() {
        UserRegistrationLogin login = new UserRegistrationLogin();
        assertTrue(login.checkUsername("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        UserRegistrationLogin login = new UserRegistrationLogin();
        assertFalse(login.checkUsername("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        UserRegistrationLogin login = new UserRegistrationLogin();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        UserRegistrationLogin login = new UserRegistrationLogin();
        assertFalse(login.checkPasswordComplexity("password"));
    }
}
