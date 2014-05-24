package com.main.java.calculator.Model;

import sun.util.locale.StringTokenIterator;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by E. Mozharovsky on 24.05.14.
 */
public class Body {
    private ArrayList<String> q_tokens; // quadratic_tokens
    private ArrayList<String> u_tokens; // unknown_tokens
    private ArrayList<String> k_tokens; // known_tokens
    private char common; // the common unknown char

    public Body(String str) {
        q_tokens = new ArrayList<String>();
        u_tokens = new ArrayList<String>();
        k_tokens = new ArrayList<String>();

        sortTokens(str);
        optimize();
    }

    public ArrayList<String> getQ_tokens() {
        return q_tokens;
    }

    public ArrayList<String> getU_tokens() {
        return u_tokens;
    }

    public ArrayList<String> getK_tokens() {
        return k_tokens;
    }

    /**
     * A global optimization.
     */
    private void optimize() {
        optimizeQuadraticTokens();
        optimizeUnknownTokens();
        optimizeKnownTokens();
    }

    private void optimizeQuadraticTokens() {
        if(q_tokens.size() > 1) {
            ArrayList<Integer> coefficients = new ArrayList<Integer>();

            for(String token : q_tokens) {
                for(int j = 0; j < token.length(); j++) {
                    if(token.charAt(j) == common && j != 0 && (j - 1) >= 0 && Character.isDigit(token.charAt(j - 1))) {
                        coefficients.add(Integer.parseInt(token.substring(0, j)));
                    } else if((j - 1) >= 0 && !Character.isDigit(token.charAt(j - 1)) && token.charAt(j) == common) {
                        coefficients.add(-1);
                    } else if((j - 1) < 0 && token.charAt(j) == common) {
                        coefficients.add(1);
                    }
                }
            }

            int result = 0;
            for(int i = 0; i < coefficients.size(); i++) {
                result += coefficients.get(i);
            }

            // clean the space in memory
            coefficients = null;
            q_tokens.clear();

            // add the final result to the q_tokens list
            q_tokens.add(Integer.toString(result) + Character.toString(common) + "^2");
        }
    }

    private void optimizeUnknownTokens() {
        if(u_tokens.size() > 1) {
            ArrayList<Integer> coefficients = new ArrayList<Integer>();

            for(String token : u_tokens) {
                for(int j = 0; j < token.length(); j++) {
                    if(token.charAt(j) == common && j != 0 && (j - 1) >= 0 && Character.isDigit(token.charAt(j - 1))) {
                        coefficients.add(Integer.parseInt(token.substring(0, j)));
                    } else if((j - 1) >= 0 && !Character.isDigit(token.charAt(j - 1)) && token.charAt(j) == common) {
                        coefficients.add(-1);
                    } else if((j - 1) < 0 && token.charAt(j) == common) {
                        coefficients.add(1);
                    }
                }
            }

            int result = 0;
            for(int i = 0; i < coefficients.size(); i++) {
                result += coefficients.get(i);
            }

            // clean the space in memory
            coefficients = null;
            u_tokens.clear();

            // add the final result to the q_tokens list
            u_tokens.add(Integer.toString(result) + Character.toString(common));
        }
    }

    private void optimizeKnownTokens() {
        if(k_tokens.size() > 1) {
            int result = 0;
            for(String token : k_tokens) {
                result += Integer.parseInt(token);
            }

            // clean the space in memory
            k_tokens.clear();

            // add the final result to the q_tokens list
            k_tokens.add(Integer.toString(result));
        }
    }

    /**
     * Analyzes the given string and finds all the tokens sorting them by:
     * quadratic (q_tokens) / unknown (u_tokens) / known (k_tokens).
     * @param str
     *        The source string line we sort tokens from.
     */
    private void sortTokens(String str) {
        ArrayList<String> tokens = new ArrayList<String>();

        if(!str.equals(null)) {
            StringTokenIterator tokenIterator = new StringTokenIterator(str, " ");
            boolean isAfterEqualMark = false;

            while(tokenIterator.hasNext() || tokenIterator.currentEnd() == (str.length() - 1)) {
                String currentToken = tokenIterator.current();

                if (!currentToken.equals("+") && !currentToken.equals("-") && !currentToken.equals("") && !currentToken.equals("=") && !isAfterEqualMark) {
                    // adds, basically, the first token which doesn't have a sign-mark
                    tokens.add(currentToken);
                } else if (currentToken.equals("+") && !isAfterEqualMark) {
                    // finds a positive sign-mark and adds the next token as a positive one
                    tokens.add(tokenIterator.next());
                } else if (currentToken.equals("-") && !isAfterEqualMark) {
                    // finds a negative sign-mark and adds the next token as a negative one
                    tokens.add("-" + tokenIterator.next());
                } else if (currentToken.equals("=")) {
                    // handles all the tokens after equal-mark ("=")
                    isAfterEqualMark = true; /** the status of being there, so these tokens won't be handled further */

                    StringTokenizer tokenizer = new StringTokenizer(str.substring(str.indexOf(currentToken) + 1));
                    String first = tokenizer.nextToken(); /** else if there is no sign-mark we should throw an exception */
                    tokenizer = new StringTokenizer(str.substring(str.indexOf(currentToken) + 1)); /** updates the current element */

                    // identify a sign-mark of the current token and adds this token depending on the sign-mark (except for the first token)
                    while (tokenizer.hasMoreTokens()) {
                        String current = tokenizer.nextToken();
                        if (current.equals(first) && !first.equals("+") && !first.equals("-") && !first.equals("")) {
                            tokens.add("-" + current);
                        } else if (current.equals("+")) {
                            tokens.add("-" + tokenizer.nextToken());
                        } else if (current.equals("-")) {
                            tokens.add(tokenizer.nextToken());
                        }
                    }
                }

                // if the current element is the last token
                if (tokenIterator.currentEnd() == str.length()) {
                    break;
                }

                // sets the next token's start position
                tokenIterator.setStart(tokenIterator.currentEnd() + 1);
            }

            // not necessary to keep this object in memory
            tokenIterator = null;

            // sorting process
            for(String token : tokens) {
                if(token.contains("^2")) {
                    q_tokens.add(token);
                } else if(hasLetter(token)) {
                    u_tokens.add(token);
                } else if(isNumber(token)) {
                    k_tokens.add(token);
                } else {

                }
            }

            // clean the space in memory
            tokens.removeAll(k_tokens);

            if(isCommonUnknownCharMatched(tokens)) {
                // that's processed only if all the unknown tokens have the same common unknown char
                tokens.removeAll(q_tokens);
                tokens.removeAll(u_tokens);
            } else {
                // TODO: Throw CommonUnknownCharIsNotMatched Exception
            }

            // should be empty in any way
            if(tokens.isEmpty())
                tokens = null;
        } else {
            // TODO: Throw NullPointerException
        }
    }

    /**
     * Determines if the given string contains any letter.
     * @param str
     *        The source string line we check tokens from.
     * @return
     *        true - contains,
     *        false - does not contain.
     */
    private boolean hasLetter(String str) {
        for(Character element : str.toCharArray()) {
            if(Character.isLetter(element)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines if the given string is a number.
     * @param str
     *        The source string line we check tokens from.
     * @return
     *        true - is a number,
     *        false - is not a number.
     */
    private boolean isNumber(String str) {
        for(Character element : str.toCharArray()) {
            if(Character.isAlphabetic(element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if there is the common unknown character in all tokens with a letter.
     * @param unknownTokens
     *        The list with all tokens containing a letter.
     * @return
     *        true - in all the tokens the common unknown character is the same.
     *        Identify the common unknown character. (E.g. X or Y)
     */
    private boolean isCommonUnknownCharMatched(ArrayList<String> unknownTokens) {
        if(unknownTokens.size() == 1) return true;

        char common = ' ';
        char previous = ' ';

        for(int i = 0; i < unknownTokens.size(); i++) {
            for(int j = 0; j < unknownTokens.get(i).length(); j++) {
                if(Character.isLetter(unknownTokens.get(i).charAt(j))) {
                    if(common == ' ') {
                        common = unknownTokens.get(i).charAt(j);
                        previous = common;
                    } else if(previous == common) {
                        common = unknownTokens.get(i).charAt(j);
                    } else if(common != ' ' && previous != common) {
                        return false;
                    }
                }
            }
        }

        this.common = common;

        return true;
    }
}
