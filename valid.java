public class valid {
    String exp;
    int current = 0;
    boolean isSquare = false;
    private String[] specialDict = new String[] { "\\", "(", ")", "*", "+", "?", ".", "[", "]", "|" }; // ! not
                                                                                                       // currently
                                                                                                       // including not

    public valid(String expression) {
        this.exp = expression;

    }

    public boolean isValid() {
        expression();
        if (current < exp.length()) {
            return false;
        }
        return true;
    }

    public void expression() {
        if (current >= exp.length())
            return;
        term();

    }

    public void term() {
        factor();

    }

    public void processSquare() {
        if (exp.charAt(this.current) != ']') {
            this.current++;
            processSquare();
        } else {
            if (exp.charAt(this.current - 1) == '[') {
                this.current++;
            }
            return;
        }
    }

    public void factor() {
        char c = exp.charAt(this.current);
        if (this.current > 0) {
            char d = exp.charAt(this.current - 1);
            // System.err.println(d);
        }
        // System.err.println(c);
        if (isLiteral(c) || ((this.current > 0 && exp.charAt(this.current - 1) == '\\'))) {
            this.current++;
            // System.err.println("Consumed " + c);
            expression();
            return;
        }
        if (c == '(') {
            this.current++;
            expression();
            if (exp.charAt(this.current) != ')') {
                //System.out.println(exp.charAt(this.current) + " " + this.current);
                throw new RuntimeException("Expected )");
            }
            this.current++;
            // System.err.println("Consumed )");
            expression();
            return;
        } else if (c == ')') {
            return;
        }
        if (c == '[' && exp.charAt(this.current - 1) != '[') {
            isSquare = true;
            this.current++;
            processSquare();
            if (exp.charAt(this.current) != ']') {
                //System.out.println(exp.charAt(this.current) + " " + this.current);
                throw new RuntimeException("Expected ]");
            }
            isSquare = false;
            this.current++;
            // System.err.println("Consumed ]");
            expression();
            return;
        } else if (c == ']' && exp.charAt(this.current - 1) != '[') {
            return;
        }
        switch (c) {
            case '*':
                this.current++;
                // System.err.println("Consumed *");
                expression();
                return;
            case '+':
                this.current++;
                // System.err.println("Consumed +");
                expression();
                return;
            case '?':
                this.current++;
                // System.err.println("Consumed ?");
                expression();
                return;
            case '|':
                char next = exp.charAt(this.current + 1);
                if (!isLiteral(next)) {
                    //System.out.println(exp.charAt(this.current) + " " + this.current);
                    throw new RuntimeException("Expected literal after |");
                }
                this.current++;
                // System.err.println("Consumed |");
                expression();
                return;
            case '\\':
                if (isLiteral(exp.charAt(this.current + 1))) {
                    //System.out.println(exp.charAt(this.current) + " " + this.current);
                    throw new RuntimeException("A literal cannot be escaped");
                }
                this.current++;
                // System.err.println("Consumed \\");
                expression();
                return;
        }
        System.out.println(exp.charAt(this.current) + " " + this.current);
        throw new RuntimeException("Invalid character: " + c);
    }

    public boolean isLiteral(char c) {
        for (String s : specialDict) {
            if (s.equals(Character.toString(c))) {
                return false;
            }
        }
        return true;
    }
}
