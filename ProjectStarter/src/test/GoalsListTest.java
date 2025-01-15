import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Goal;
import model.GoalsList;

public class GoalsListTest {
    private GoalsList testGoalsList;
    private Goal testGoal1;
    private Goal testGoal2;

    @BeforeEach
    public void runBefore() {
        testGoalsList = new GoalsList();
        testGoal1 = new Goal("Squats", 100);
        testGoal2 = new Goal("Bench Press", 120);
    }

    @Test
    public void testAddGoalToList() {
        testGoalsList.addGoalToList(testGoal1);
        testGoalsList.addGoalToList(testGoal2);

        assertTrue(testGoalsList.getGoalsList().contains(testGoal1));
        assertTrue(testGoalsList.getGoalsList().contains(testGoal2));
        assertEquals(2, testGoalsList.getGoalsList().size());

    }

}
