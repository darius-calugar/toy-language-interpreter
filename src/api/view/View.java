package api.view;

import api.controller.Controller;
import api.model.exceptions.MyException;
import api.model.exceptions.OutOfBoundsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 Console ui view of the interpreter.
 Keeps a reference to the controller.
 */
public class View {
    Controller controller;
    boolean    running;
    boolean    displayEachStep;
    boolean    stepByStep;

    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     Start the console ui loop.
     */
    public void start() {
        running = true;
        while (running) {
            printMenu();
            var input = getInput();
            try {
                switch (input) {
                    case "0" -> running = false;                       // quit
                    case "1" -> runProgram(0);                  // run example 1
                    case "2" -> runProgram(1);                  // run example 2
                    case "3" -> runProgram(2);                  // run example 3
                    case "4" -> stepByStep = !stepByStep;             // toggle step by step execution
                    case "5" -> displayEachStep = !displayEachStep;   // toggle display each step flag
                    default -> System.out.println("<<Invalid Command!>>");
                }
            } catch (MyException e) {
                System.out.println("Encountered Exception: \"" + e.getMessage() + "\"");
            }
            System.out.println();
        }
    }

    /**
     Select a program through the controller and run it.

     @param index Index of the target program
     @throws OutOfBoundsException No program could be found on the given index
     */
    private void runProgram(int index) throws OutOfBoundsException {
        controller.setDisplayOnStepFlag(displayEachStep);
        controller.selectProgram(index);
        controller.display();
        System.out.println();
        if (stepByStep) {
            boolean executing = true;
            while (!controller.isEmpty() && executing) {
                controller.oneStep();
                System.out.print("Continue? (Y/N)");
                switch (getInput()) {
                    case "y", "Y" -> {
                    }
                    case "n", "N" -> executing = false;
                }
            }
        } else {
            controller.allStep();
        }
        if (!displayEachStep)
            controller.display();
    }

    /**
     Print the user main menu.
     */
    private void printMenu() {
        System.out.println("==================== CONSOLE ====================");
        System.out.println("[StepByStep=" + (stepByStep ? "ENABLED" : "DISABLED") + "] " +
                           "[DisplayEachStep=" + (displayEachStep ? "ENABLED" : "DISABLED") + "] ");
        System.out.println("0. Exit");
        System.out.println("1. Run Example 1");
        System.out.println("2. Run Example 2");
        System.out.println("3. Run Example 3");
        System.out.println("4. Toggle step by step execution");
        System.out.println("5. Toggle displaying each step");
        System.out.print("> ");
    }

    /**
     @return User's input line
     */
    private String getInput() {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException ignored) {
            System.out.print("Error occurred, quitting application...");
            return "0";
        }
    }
}