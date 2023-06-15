import java.util.List;
import java.util.Stack;

public class FSM {
     // Fields (variables)
    // private int[] stack;
    // private int[] queue;

    private State[] array;
   // private State startingState;
    private State prevState;
    private int stateIndex = 1;
    private String[] specialDict = new String[] {"\\", "(", ")", "*", "+", "?", ".", "[", "]", "|"}; // ! not currently including not
    
    //private int currIndex = 0;
    //private String fullExpression;
    
    public FSM(String expression) {
        //this.fullExpression = expression;
        // this.fullExpression = "A(AA(AB))C";

        State startingState = new State(0, "★");
        array = new State[]{startingState};
        prevState = startingState;
        //String newString = extractInnermostBracketText(fullExpression);
        //String newString = extractInnermostBracketText(expression);

        //convert(newString);
        convert(expression);
        print();
        // System.err.println(newString.substring(0,1));
    }

    // Might actually be useless Should probably just go left to right until I hit a bracket
    // public static String extractInnermostBracketText(String text) {
    //     int start = text.lastIndexOf("(");
    //     int end = text.indexOf(")", start);
    //     if (start != -1 && end != -1 && start < end) {
    //         return extractInnermostBracketText(text.substring(start + 1, end));
    //     } else {
    //         return text;
    //     }
    // }

    //Converts the Expression into the three-array using the State class
    public void convert(String expression) {

        boolean ignoreNext = false;
        // boolean alternationCheck = false; Got an idea if it doesn't work uncomment this
        int alternationCheck = -1;

        //for brackets
        Stack<State> stateStack = new Stack<>();

        //Loops through every letter in expression
        for(int i = 0; i < expression.length(); i++){

            String currChar = expression.substring(i, i+1);
            boolean specialCheck = false;
            for (String specialStr : specialDict) { // Makes sure the letter doesn't have a special function
                if(specialStr.equals(currChar)){
                    specialCheck = true;
                    break;
                }
            }

            
            if(specialCheck && !ignoreNext){
                //Code to deal with special Characters
                State newState = new State(stateIndex, "☆");
                if(currChar.equals("\\")){
                    ignoreNext = true;
                    connectPathTo(array[prevState.getIndex()-1], stateIndex);
                }
                else if (currChar.equals("(")) {
                    // Handle opening bracket

                    State branch = new State(stateIndex, "☆");
                    newState.setIndex(++stateIndex);
                    newState.setExpression("☆");

                    connectPathTo(prevState, branch.getIndex());
                    connectPathTo(branch, newState.getIndex());
                    
                    addtoArray(branch);
                    stateStack.push(branch);
                } else if (currChar.equals(")")) {
                    // Handle closing bracket
                    newState.setExpression("☆");
                    State openingBracketState = stateStack.pop();
                    //connectPathTo(openingBracketState, stateIndex);
                    connectPathTo(prevState, stateIndex);
                    connectPathTo(newState, stateIndex+1); //seemingly works
                    prevState = openingBracketState;

                    stateIndex++;
                    addtoArray(newState);
                }
                else if(alternationCheck == 0){
                newState = new State(stateIndex, expression.substring(i, i+1));

                alternationCheck++;
                }
                else if(alternationCheck == 1){
                    newState = new State(stateIndex, expression.substring(i, i+1));

                    connectPathTo(prevState, stateIndex);
                    connectPathTo(array[stateIndex-3], stateIndex);

                    alternationCheck = -1;
                }
                else if(currChar.equals(".")){
                    //I think most of this implementation is done when checking if
                    //text matches
                    newState.setExpression(".");
                    connectPathTo(prevState, stateIndex);
                }
                else if(currChar.equals("*")){

                    connectPathTo(prevState, stateIndex); // Implements 0 times
                    //connectPathTo(newState, prevState.getIndex()-1); // Implements many times
                    connectPathTo(newState, prevState.getIndex());
                    connectPathTo(array[prevState.getIndex()-1], stateIndex); // Maybe useless?

                    // This works just want to implement it differently
                    // connectPathTo(array[prevState.getIndex()-1], stateIndex);
                    // connectPathTo(prevState, stateIndex);
                    // connectPathTo(prevState, prevState.getIndex());
                }
                else if(currChar.equals("+")){
                    connectPathTo(prevState, prevState.getIndex());
                }
                else if(currChar.equals("?")){

                    connectPathTo(prevState, stateIndex);
                    connectPathTo(array[stateIndex-2], stateIndex);
                }
                else if(currChar.equals("|")){ // pain

                    State twoBack = array[prevState.getIndex()-1];
                    if(twoBack.getFirPath() == prevState.getIndex())
                        twoBack.setFirPath(stateIndex);
                    else if(twoBack.getSecPath() == prevState.getIndex())
                        twoBack.setSecPath(stateIndex);

                    connectPathTo(newState, prevState.getIndex());
                    connectPathTo(newState, stateIndex + 1);
                    //connectPathTo(array[prevState.getIndex()-1], stateIndex); //maybe same as array[stateIndex - 2]

                    alternationCheck = 0;
                }
                if(!currChar.equals(")")){
                    prevState = newState;
                    stateIndex++;

                    addtoArray(newState);
                }
                
            }
            //If the letter isn't special connect it to the previous state
            // using either the first or second path
            else{ 
                State newState = new State(stateIndex, expression.substring(i, i+1));

                if(alternationCheck == 0){
                newState = new State(stateIndex, expression.substring(i, i+1));

                alternationCheck++;
                }
                else if(alternationCheck == 1){
                    newState = new State(stateIndex, expression.substring(i, i+1));

                    connectPathTo(prevState, stateIndex);
                    connectPathTo(array[stateIndex-3], stateIndex);


                    alternationCheck = -1;
                }

                //else if(prevState.getFirPath()==-1 && prevState.getIndex()!=0)
                else if(prevState.getFirPath()==-1)
                    prevState.setFirPath(stateIndex);

                //else if(prevState.getSecPath()==-1 && prevState.getIndex()!=0)
                else if(prevState.getSecPath()==-1)
                    prevState.setSecPath(stateIndex);

                prevState = newState;
                stateIndex++;

                addtoArray(newState);
            }
        }
    }

    public void connectPathTo(State originState, int setValue) {
        if(originState.getFirPath()==-1)
            originState.setFirPath(setValue);
        else if(prevState.getSecPath()==-1)
            originState.setSecPath(setValue);
    }
    
    public void addtoArray(State inputState) {
        State[] newArray = new State[array.length+1];
                for (int j = 0; j < array.length; j++) {
                    newArray[j] = array[j];
                }
                newArray[array.length] = inputState;
                array = newArray;
    }

    public void print() {
        for (State state : array) {
            System.err.println(state.getIndex()+": "+state.getExpression()+": " + state.getFirPath()+": "+state.getSecPath());
        }
    }

}
