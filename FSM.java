public class FSM {
     // Fields (variables)
    private int[] stack;
    private int[] queue;

    //private State[] Array;
    private State startingState;
    private int stateIndex = 1;
    private String[] specialDict = new String[] {"\\", "(", ")", "*", "+", "?", ".", "[", "]", "|"}; // ! not currently including not
    
    private int currIndex = 0;
    private String fullExpression;
    
    public FSM(String expression) {
        this.fullExpression = expression;

        startingState = new State(0, "★");
        scan(currIndex);
    }

    public void scan(int index) {
        boolean firSpecial = false;
        boolean secSpecial = false;

        int highestPIndex;

        State stateA = new State(stateIndex, Character.toString(fullExpression.charAt(index)));
        stateIndex++;

        for (int i = index; i < fullExpression.length(); i++) {
            
            if(i + 1 < fullExpression.length()){
                State stateB = new State(stateIndex, Character.toString(fullExpression.charAt(i)));
            }
        }

    }
}
