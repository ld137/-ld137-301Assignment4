// REsearch.java:

// The second program must accept, as standard input, the output
//  of the first program, then it must execute a search for matching
//   patterns within the text of a file whose name is given as a 
//   command-line argument. Each line of the text file that contains a 
//   substring that matches the regexp is output just once, regardless 
//   of the number of times the pattern might be satisfied in that line.
//    (Note also we are just interested in searching text files.) 
//    Again, the entire line is output just once if it contains a 
//    substring that matches the regular expression.

// Accepts the output of REcompile.java as standard input.
// Reads the text from a file specified as a command-line argument.
// Searches for substrings in the text that match the regular expression.
// Outputs each line of the text file that contains a matching substring, without duplication.
// The entire line is output just once if it contains a substring that matches the regular expression.

public class REsearch {
    public static void main(String[] args) {

        String expression = args[0];
    }
}