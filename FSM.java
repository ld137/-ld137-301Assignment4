import java.util.List;

public class FSM {
     // Fields (variables)
    // private int[] stack;
    // private int[] queue;

    private State[] array;
    private State startingState;
    private State prevState;
    private int stateIndex = 1;
    private String[] specialDict = new String[] {"\\", "(", ")", "*", "+", "?", ".", "[", "]", "|"}; // ! not currently including not
    
    private int currIndex = 0;
    private String fullExpression;
    
    public FSM(String expression) {
        this.fullExpression = expression;
        // this.fullExpression = "A(AA(AB))C";

        startingState = new State(0, "★");
        array = new State[]{startingState};
        prevState = startingState;
        String newString = extractInnermostBracketText(fullExpression);

        convert(newString);
        print();
        // System.err.println(newString.substring(0,1));
    }

    public static String extractInnermostBracketText(String text) {
        int start = text.lastIndexOf("(");
        int end = text.indexOf(")", start);
        if (start != -1 && end != -1 && start < end) {
            return extractInnermostBracketText(text.substring(start + 1, end));
        } else {
            return text;
        }
    }

    //Converts the Expression into the three-array using the State class
    public void convert(String expression) {

        boolean ignoreNext = false;

        //Loops through every letter in expression
        for(int i = 0; i < expression.length(); i++){

            String currChar = expression.substring(i, i+1);
            boolean specialCheck = false;
            for (String specialStr : specialDict) { // Makes sure the letter doesn't have a special function
                if(specialStr.equals(currChar)){
                    specialCheck = true;
                }
            }

            if(specialCheck && !ignoreNext){
                //Code to deal with special Characters
                State newState = new State(stateIndex, "★");
                if(currChar.equals("\\")){
                    ignoreNext = true;
                    connectPathTo(array[prevState.getIndex()-1], stateIndex);
                }
                else if(currChar.equals(".")){
                    
                }


                else if(currChar.equals("*")){
                    connectPathTo(array[prevState.getIndex()-1], stateIndex);
                    connectPathTo(prevState, stateIndex);
                    connectPathTo(prevState, prevState.getIndex());
                }
                else if(currChar.equals("+")){
                    connectPathTo(prevState, prevState.getIndex());
                }
                else if(currChar.equals("?")){
                    //State newState = new State(stateIndex, currChar);   
                }
                prevState = newState;
                stateIndex++;

                addtoArray(newState);
            }
            //If the letter isn't special connect it to the previous state
            // using either the first or second path
            else{ 
                State newState = new State(stateIndex, expression.substring(i, i+1));
                if(prevState.getFirPath()==-1 && prevState.getIndex()!=0)
                    prevState.setFirPath(stateIndex);

                else if(prevState.getSecPath()==-1 && prevState.getIndex()!=0)
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
