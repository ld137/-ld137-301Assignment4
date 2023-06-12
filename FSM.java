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
        //Loops through every letter in expression
        for(int i = 0; i < expression.length(); i++){
            boolean specialCheck = false;
            for (String specialStr : specialDict) { // Makes sure the letter doesn't have a special function
                if(specialStr.equals(expression.substring(0, 1))){
                    specialCheck = true;
                }
            }

            if(specialCheck){
                //Code to deal with special Characters
            }
            //If the letter isn't special connect it to the previous state
            // using either the first or second path
            else{ 
                State newState = new State(stateIndex, expression.substring(i, i+1));
                if(prevState.getFirPath()==-1 && prevState.getIndex()!=0)
                    prevState.setFirPath(stateIndex);

                else if(prevState.getSecPath()==-1 && prevState.getIndex()!=0)
                    prevState.setFirPath(stateIndex);

                prevState = newState;
                stateIndex++;

                State[] newArray = new State[array.length+1];
                for (int j = 0; j < array.length; j++) {
                    newArray[j] = array[j];
                }
                newArray[array.length] = newState;
                array = newArray;
            }
        }
    }

    public void print() {
        for (State state : array) {
            System.err.println(state.getIndex()+": "+state.getExpression()+": " + state.getFirPath()+": "+state.getSecPath());
        }
    }

}
