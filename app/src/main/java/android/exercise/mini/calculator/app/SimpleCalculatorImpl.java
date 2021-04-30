package android.exercise.mini.calculator.app;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.LinkedList;

public class SimpleCalculatorImpl implements SimpleCalculator {

    // todo: add fields as needed
    private LinkedList<String> input = new LinkedList<>();
    private final LinkedList<String> supportedOps = new LinkedList<String>() {
        {
            add("+");
            add("-");
        }
    };
    private boolean lastIsOperation = false;

    @Override
    public String output() {
        // todo: return output based on the current state
        if (input.isEmpty()) {
            return "0";
        }
        return input.toString().replaceAll("\\[|]|, ", ""); //"implement me please";
    }

    @Override
    public void insertDigit(int digit) {
        // todo: insert a digit
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException();
        }
        if (lastIsOperation | input.size() == 0) {
            input.add(Integer.toString(digit));
        } else {
            input.set(input.size() - 1, input.getLast().concat(Integer.toString(digit)));
        }

        lastIsOperation = false;
    }

    @Override
    public void insertPlus() {
        // todo: insert a plus
        if (lastIsOperation) {
            return;
        }
        if (input.isEmpty()) {
            input.add("0");
        }
        input.add("+");
        lastIsOperation = true;
    }

    @Override
    public void insertMinus() {
        // todo: insert a minus
        if (lastIsOperation) {
            return;
        }
        if (input.isEmpty()) {
            input.add("0");
        }
        input.add("-");
        lastIsOperation = true;
    }

    @Override
    public void insertEquals() {
        // todo: calculate the equation. after calling `insertEquals()`, the output should be the result
        //  e.g. given input "14+3", calling `insertEquals()`, and calling `output()`, output should be "17"
        int result = 0;
        String operation = "+";
        for (int i = 0; i < input.size(); i++) {
            if (operation.equals("+")) {
                result += Integer.parseInt(input.get(i));
            } else if (operation.equals("-")) {
                result -= Integer.parseInt(input.get(i));
            } else {
                throw new InputMismatchException();
            }

            if (i >= input.size() - 1) {
                break;
            }
            operation = input.get(++i);

            if (!supportedOps.contains(operation)) {
                throw new InputMismatchException();
            }
        }
        input.clear();
        input.add(Integer.toString(result));
        lastIsOperation = false;
    }

    @Override
    public void deleteLast() {
        // todo: delete the last input (digit, plus or minus)
        //  e.g.
        //  if input was "12+3" and called `deleteLast()`, then delete the "3"
        //  if input was "12+" and called `deleteLast()`, then delete the "+"
        //  if no input was given, then there is nothing to do here
        if (input.size() == 0) {
            return;
        }
        String last = input.getLast();
        input.removeLast();
        if (last.length() != 1) {
            input.add(last.substring(0, last.length() - 1));
        }
    }

    @Override
    public void clear() {
        input.clear();
        lastIsOperation = false;
    }

    @Override
    public Serializable saveState() {
        CalculatorState state = new CalculatorState();
        state.setInput(input);
        state.setWasLastOperation(lastIsOperation);
        return state;
    }

    @Override
    public void loadState(Serializable prevState) {
        if (!(prevState instanceof CalculatorState)) {
            return; // ignore
        }
        CalculatorState casted = (CalculatorState) prevState;
        input = casted.getInput();
        lastIsOperation = casted.getWasLastOperation();
    }

    private static class CalculatorState implements Serializable {
        /*
        all fields must only be from the types:
        - primitives (e.g. int, boolean, etc)
        - String
        - ArrayList<> where the type is a primitive or a String
        - HashMap<> where the types are primitives or a String
         */
        private LinkedList<String> savedInput;
        private boolean lastIsOperation;

        public CalculatorState() {
            super();
        }

        public void setInput(LinkedList<String> input) {
            savedInput = (LinkedList<String>) input.clone();
        }

        public void setWasLastOperation(boolean wasLastOperation) {
            lastIsOperation = wasLastOperation;
        }

        public LinkedList<String> getInput() {
            return savedInput;
        }

        public boolean getWasLastOperation() {
            return lastIsOperation;
        }
    }
}
