public class State {

    private int index;
    private String expression;
    private String fullExpression;
    private int firPath;
    private int secPath;
    
    State(int index, String fExp, int first, int second) {
        this.index = index;
        this.fullExpression = fExp;
        this.firPath = first;
        this.secPath = second;
    }


    // V Getters and Setters Below
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public int getFirPath() {
        return firPath;
    }
    public void setFirPath(int firPath) {
        this.firPath = firPath;
    }
    public int getSecPath() {
        return secPath;
    }
    public void setSecPath(int secPath) {
        this.secPath = secPath;
    }
    
}
