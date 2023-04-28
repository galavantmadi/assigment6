package sbu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
    For this exercise, you must simulate a CPU with a single core.
    You will receive an arraylist of tasks as input. Each task has a processing
    time which is the time it needs to run in order to fully execute.

    The CPU must choose the task with the shortest processing time and create
    a new thread for it. The main thread should wait for the task to fully
    execute and then join with it, before starting the next task.

    Once a task is fully executed, add its ID to the executed tasks arraylist.
    Use the tests provided in the test folder to ensure your code works correctly.
 */

public class CPU_Simulator
{
    public static class Task implements Runnable {
        long processingTime;
        String ID;

        public Task(String ID, long processingTime) {
        // TODO
            this.processingTime = processingTime;
            this.ID = ID;
        }

    /*
        Simulate running a task by utilizing the sleep method for the duration of
        the task's processingTime. The processing time is given in milliseconds.
    */
        @Override
        public void run() {
        // TODO
            try {
                Thread.sleep(this.processingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        The startProcessing function should be called at the start of your program.
        Here the CPU selects the next shortest task to run (also known as the
        shortest task first scheduling algorithm) and creates a thread for it to run.
    */
    public static ArrayList<String> startSimulation(ArrayList<Task> tasks) {
        ArrayList<String> executedTasks = new ArrayList<>();

        // TODO

        ArrayList<Task> tasks2= (ArrayList<Task>) tasks.stream()
                .sorted(Comparator.comparingLong(value -> value.processingTime)).collect(Collectors.toList());

        List<Task> tasksList = new ArrayList<>();
        List<Thread> threadsList = new ArrayList<>();

        for(Task task:tasks2){
            Task newTask = new Task(task.ID,task.processingTime);
            Thread thread = new Thread(newTask);

            tasksList.add(task);
            threadsList.add(thread);
        }

        for (Thread thread:threadsList) {  // start all threads
            thread.start();
        }

        for (Thread thread:threadsList) { // wait for all threads to join
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());;
            }
        }
        for (Task task:tasksList) {  // calculate the final sum
            executedTasks.add(task.ID);
        }

        return executedTasks;
    }

    public static void main(String[] args) {
        Task[] taskArray = {new Task("A", 100),
                new Task("B", 90),
                new Task("C", 80),
                new Task("D", 70),
                new Task("E", 60),
                new Task("F", 50),
                new Task("G", 40),
                new Task("H", 30),
                new Task("I", 20),
                new Task("J", 10)};

        ArrayList<Task> taskList = new ArrayList<>(Arrays.asList(taskArray));

        startSimulation(taskList);
    }
}
