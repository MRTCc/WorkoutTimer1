package com.example.workouttimer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetExerciseTime(){
        Exercise exercise = new Exercise();
        int testValue = exercise.getExerciseTime();
        assertEquals(0, testValue);

        exercise.setPreparationTime(10);
        exercise.setWorkTime(5);
        exercise.setRestTime(5);
        exercise.setSetsToDo(2);
        testValue = exercise.getExerciseTime();
        assertEquals(25, testValue);
    }

    @Test
    public void testGetRoutineTime(){
        Routine routine = new Routine();
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        ArrayList<Exercise> listExercises = new ArrayList<>();
        listExercises.add(exercise1);
        listExercises.add(exercise2);
        routine.setListExercise(listExercises);
        int testValue = routine.getRoutineTime();
        assertEquals(0, testValue);

        listExercises.get(0).setPreparationTime(10);
        listExercises.get(0).setWorkTime(5);
        listExercises.get(0).setRestTime(5);
        listExercises.get(0).setSetsToDo(2);
        testValue = routine.getRoutineTime();
        assertEquals(25, testValue);

        listExercises.get(1).setPreparationTime(10);
        listExercises.get(1).setWorkTime(5);
        listExercises.get(1).setRestTime(5);
        listExercises.get(1).setSetsToDo(2);
        testValue = routine.getRoutineTime();
        assertEquals(50, testValue);
    }
}