package android.exercise.mini.calculator.app;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;

public class SimpleCalculatorImpl implements SimpleCalculator {

  // todo: add fields as needed
  private LinkedList<String> input = new LinkedList<String>();
  private LinkedList<String> supportedOps = new LinkedList<String>(){ {add("+"); add("-");}};
  private boolean lastIsOperation = false;

  @Override
  public String output() {
    // todo: return output based on the current state
    if (input.isEmpty()){
      return "0";
    }

    return input.toString().replaceAll("\\[|]", ""); //"implement me please";
  }

  @Override
  public void insertDigit(int digit) {
    // todo: insert a digit
    if (digit < 0 || digit > 9){
      throw new IllegalArgumentException();
    }
    if (lastIsOperation | input.size() == 0){
      input.add( Integer.toString(digit) );
    } else {
      input.set(input.size() - 1, input.getLast().concat(Integer.toString(digit)));
    }

    lastIsOperation = false;
  }

  @Override
  public void insertPlus() {
    // todo: insert a plus
    if (lastIsOperation){
      return;
    }
    if (input.isEmpty()){
      input.add( "0" );
    }
    input.add( "+" );
    lastIsOperation = true;
  }

  @Override
  public void insertMinus() {
    // todo: insert a minus
    if (lastIsOperation){
      return;
    }
    if (input.isEmpty()){
      input.add( "0" );
    }
    input.add( "-" );
    lastIsOperation = true;
  }

  @Override
  public void insertEquals() {
    // todo: calculate the equation. after calling `insertEquals()`, the output should be the result
    //  e.g. given input "14+3", calling `insertEquals()`, and calling `output()`, output should be "17"
    int result = 0;
    String operation = "+";
    for (int i = 0; i < input.size(); i++) {
      if (operation.equals("+")){
        result += Integer.parseInt(input.get(i));
      } else if (operation.equals("-")){
        result -= Integer.parseInt(input.get(i));
      } else {
        throw new InputMismatchException();
      }

      if (i >= input.size() - 1){
        break;
      }
      operation = input.get(++i);

      if (!supportedOps.contains(operation)){
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
  }

  @Override
  public void clear() {
    // todo: clear everything (same as no-input was never given)
    input.clear();
    lastIsOperation = false;
  }

  @Override
  public Serializable saveState() {
    CalculatorState state = new CalculatorState();
    // todo: insert all data to the state, so in the future we can load from this state
    return state;
  }

  @Override
  public void loadState(Serializable prevState) {
    if (!(prevState instanceof CalculatorState)) {
      return; // ignore
    }
    CalculatorState casted = (CalculatorState) prevState;
    // todo: use the CalculatorState to load
  }

  private static class CalculatorState implements Serializable {
    /*
    TODO: add fields to this class that will store the calculator state
    all fields must only be from the types:
    - primitives (e.g. int, boolean, etc)
    - String
    - ArrayList<> where the type is a primitive or a String
    - HashMap<> where the types are primitives or a String
     */
  }
}
