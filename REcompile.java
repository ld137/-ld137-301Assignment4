// REcompile.java:



// Accepts a regular expression (regexp) pattern as a command-line argument.
// Generates the corresponding Finite State Machine (FSM) for the given pattern.
// Outputs a description of the FSM, with each line representing a state in the machine.
// Each line includes the state-number, the symbol to be matched or branch-state indicator, and two numbers indicating the two possible next states.
// State Zero is the start state and should branch to the actual start state of the FSM.
public class REcompile {
    public static void main(String[] args) {

        // String expression = args[0];
        String expression = "A(AA(ABZY))\\)C";
        valid v = new valid(expression);
        v.isValid();
        
        if(isValid(expression)){
            FSM fsm = new FSM(expression);
        }
        else
            System.err.println("Invalid Regex Expression");
    }

    public static boolean isValid(String input){
        //Implementation
        //return
        return true;
    }
}

