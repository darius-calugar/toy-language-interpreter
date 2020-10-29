package api.view;

import api.controller.Controller;
import api.model.exceptions.MyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class View {
    Controller controller;
    boolean running;
    boolean displayEachStep;
    boolean stepByStep;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        running = true;
        while (running) {
            printMenu();
            var input = getInput();
            try {
                switch (input) {
                    case "0" -> running = false;
                    case "1" -> runProgram(0);
                    case "2" -> runProgram(1);
                    case "3" -> runProgram(2);
                    case "4" -> stepByStep = !stepByStep;
                    case "5" -> displayEachStep = !displayEachStep;
                    default -> System.out.println("<<Invalid Command!>>");
                }
            } catch (MyException e) {
                System.out.println("Encountered Exception: \"" + e.getMessage() + "\"");
            }
            System.out.println("");
        }
    }

    private void runProgram(int index) {
        controller.setDisplayOnStepFlag(displayEachStep);
        controller.selectProgram(index);
        controller.display();
        System.out.print("");
        if (stepByStep) {
            boolean executing = true;
            while (!controller.isEmpty() && executing) {
                controller.oneStep();
                System.out.print("Continue? (Y/N)");
                switch (getInput()) {
                    case "y", "Y" -> {}
                    case "n", "N" -> executing = false;
                }
            }
        }
        else {
            controller.allStep();
        }
        if (!displayEachStep)
            controller.display();
    }

    private void printMenu() {
        System.out.println("==================== CONSOLE ====================");
        System.out.println("[StepByStep=" + (stepByStep ? "ENABLED" : "DISABLED") + "] " +
                           "[DisplayEachStep=" + (displayEachStep ? "ENABLED" : "DISABLED") + "] ");
        System.out.println("0. Exit");
        System.out.println("1. Run Example 1");
        System.out.println("2. Run Example 2");
        System.out.println("3. Run Example 3");
        System.out.println("4. Enable/Disable step by step execution");
        System.out.println("5. Enable/Disable displaying each step");
        System.out.print("> ");
    }

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