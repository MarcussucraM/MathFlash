package com.example.marcus.mathflash;

import org.junit.Test;
import com.example.marcus.mathflash.models.MathResources;
import com.example.marcus.mathflash.models.InputValidator;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void testCreateAdditionProblems(){
        ArrayList<String> problems = MathResources.createAdditionProblems(0,3);
        assertEquals(16, problems.size());
        assertEquals("0 + 0", problems.get(0));
        assertEquals("3 + 3", problems.get(problems.size()-1));
    }

    @Test
    public void testCreateSubtractionProblems(){
        ArrayList<String> problems = MathResources.createSubtractionProblems(0, 3);
        assertEquals(10, problems.size());
        assertEquals("3 - 0", problems.get(0));
        assertEquals("0 - 0", problems.get(problems.size()-1));
    }

    @Test
    public void testValuesOk(){
        boolean resultsA = InputValidator.isDialogInputOk("5", "6", "3");
        boolean resultsB = InputValidator.isDialogInputOk("", "5", "3");
        boolean resultsC = InputValidator.isDialogInputOk("6", "5", "3");
        boolean resultsD = InputValidator.isDialogInputOk("a", "4", "5");
        boolean resultsE = InputValidator.isDialogInputOk("4", "5", "11");
        assertEquals(true, resultsA);
        assertEquals(false, resultsB);
        assertEquals(false, resultsC);
        assertEquals(false, resultsD);
        assertEquals(false, resultsE);
    }

    @Test
    public void testCreateProblems(){
        ArrayList<String> problems = MathResources.createProblems(0, 5, "Addition");
        assertEquals(36, problems.size());
        assertEquals("0 + 0", problems.get(0));
    }


    @Test
    public void testProblemAnswer(){
        int answer = MathResources.getProblemAnswer("45 + 5");
        assertEquals(50, answer);

    }
}