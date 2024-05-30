import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/*
 * this unit test class is adapted from a Java unit test tutorial
 *Author: Coding with John: https://www.youtube.com/@CodingWithJohn
 * Video: Java Unit Testinng with Junit - How to Create and Use Unit Tests: https://www.youtube.com/watch?v=vZm0lHciFsQ&t=184s
*/
public class TaskTest {
    
    @BeforeEach
    public void setup() {
        Task.resetTaskCountAndHours();
    }
    
    @Test
    public void testTaskDescriptionLength() {
        Task task1 = new Task("Login Feature", "create login to authenticate users", "Robyn Harrison", 8, "to do");
        assertTrue(task1.checkTaskDescription(), "Task successfully captured");

        Task task2 = new Task("Login Feature", "This description is definitely more than fifty characters long and should fail", "Robyn Harrison", 8, "to do");
        assertTrue(!task2.checkTaskDescription(), "Please enter a task description of less than 50 characters");
    }

    @Test
    public void testTaskIDGeneration() {
        Task task1 = new Task("Login Feature", "create login to authenticate users", "Robyn Harrison", 8, "to do");
        assertEquals("LO:0:SON", task1.createTaskID());

        Task task2 = new Task("Add Task Feature", "Create add task feature to add task users", "Mike Smith", 10, "doing");
        assertEquals("AD:1:ITH", task2.createTaskID());
    }

    @Test
    public void testTotalHoursAccumulated() {
        Task task1 = new Task("Login Feature", "create login to authenticate users", "Robyn Harrison", 8, "to do");
        Task task2 = new Task("Add Task Feature", "Create add task feature to add task users", "Mike Smith", 10, "doing");

        assertEquals(18, Task.returnTotalHours());

        Task.resetTaskCountAndHours();
        
        Task task3 = new Task("Feature1", "Desc1", "Dev1", 10, "done");
        Task task4 = new Task("Feature2", "Desc2", "Dev2", 12, "done");
        Task task5 = new Task("Feature3", "Desc3", "Dev3", 55, "done");
        Task task6 = new Task("Feature4", "Desc4", "Dev4", 11, "done");
        Task task7 = new Task("Feature5", "Desc5", "Dev5", 1, "done");
        
        assertEquals(89, Task.returnTotalHours());
    }
}
