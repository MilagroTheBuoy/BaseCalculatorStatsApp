package com.example.basecalculatorstatsapp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

//class that converts an infix algebraic expression to a postfix algebraic expression
public class DecimalCalculator{

    public String giveAnswer(String equation, int base){
        if(base == 2){
            return calculateBinaryAnswer(infixToPostfix(equation));
        }
        else if(base == 16){
            return calculateHexAnswer(infixToPostfix(equation));
        }
        return String.valueOf(calculateAnswer(infixToPostfix(equation)));
    }

    private static double calculateAnswer(String equation){
        StringBuilder num = new StringBuilder(new StringBuilder());
        Stack<String> stackOfNums = new Stack<>();
        double result = 0;
        char[] eqnChars = equation.toCharArray();
        boolean isLastCharNum = false;
        for(int i = 0; i < equation.length(); i++){
            if(isPartOfNumber(eqnChars[i])){
                num.append(eqnChars[i]);
                isLastCharNum = true;
            }
            else if(eqnChars[i] == '$'){
                num.append("-");
            }
            else if(eqnChars[i] == ' '){
                stackOfNums.add(num.toString());
                num = new StringBuilder();
                isLastCharNum = true;
            }
            else{
                if(isLastCharNum){
                    stackOfNums.add(num.toString());
                    num = new StringBuilder();
                    isLastCharNum = false;
                }
                double secNum = Double.parseDouble(stackOfNums.pop());
                double firstNum = Double.parseDouble(stackOfNums.pop());
                switch (eqnChars[i]) {
                    case '+':
                        result = firstNum + secNum;
                        break;
                    case '-':
                        result = firstNum - secNum;
                        break;
                    case '*':
                        result = firstNum * secNum;
                        break;
                    case '/':
                        result = firstNum / secNum;
                        break;
                    case '^':
                        result = Math.pow(firstNum, secNum);
                        break;
                }
                stackOfNums.add(String.valueOf(BigDecimal.valueOf(result).setScale(9, RoundingMode.HALF_UP)));
            }
        }
        return Double.parseDouble(stackOfNums.peek());
    }

    public static int prec(char c) {
        if(c == '^') {
            return 3;
        }
        else if(c == '*' || c == '/') {
            return 2;
        }
        else if(c == '+' || c =='-') {
            return 1;
        }
        else {
            return -1;
        }
    }

    private static boolean isPartOfNumber(char c){
        return Character.isAlphabetic(c) || Character.isDigit(c) || c == '.';
    }

    private static String infixToPostfix(String infix){
        Stack<String> specialChars = new Stack<>();
        specialChars.add(">");
        boolean lastCharWasDigit = false;
        boolean lastCharWasLeftBrac = false;

        char[] infixChars = infix.toCharArray();

        StringBuilder postfix = new StringBuilder();
        for (char c : infixChars) {

            if (isPartOfNumber(c)) {
                postfix.append(c);
                lastCharWasDigit = true;
            } else if (c == '(') {
                specialChars.add("(");
                lastCharWasDigit = false;
                lastCharWasLeftBrac = true;
            } else if (c == '^' || c == '+' || c == '*' || c == '/') {
                if (lastCharWasDigit) {
                    postfix.append(" ");
                }
                // Pop all the operators from the stack that have higher or equal precedence
                while (!specialChars.isEmpty() && prec(c) <= prec(specialChars.peek().charAt(0))) {
                    postfix.append(specialChars.pop());
                }
                // Push the current operator onto the stack
                specialChars.add(String.valueOf(c));
                lastCharWasDigit = false;
            } else if (c == '-') {
                if (lastCharWasLeftBrac) {
                    postfix.append("$");
                    lastCharWasLeftBrac = false;
                } else {
                    if (lastCharWasDigit) {
                        postfix.append(" ");

                    }
                    // Pop all the operators from the stack that have higher or equal precedence
                    while (!specialChars.isEmpty() && prec(c) <= prec(specialChars.peek().charAt(0))) {
                        postfix.append(specialChars.pop());
                    }
                    // Push the current operator onto the stack
                    specialChars.add(String.valueOf(c));
                    lastCharWasDigit = false;
                }
            } else if (c == ')') {
                while (!specialChars.empty() && !specialChars.peek().equals("(")) {
                    postfix.append(specialChars.peek());
                    specialChars.pop();
                }
                if (!specialChars.empty()) {
                    specialChars.pop();
                }
            }
        }

        while(!(specialChars.size() == 1)){
            if(!specialChars.peek().equals(">")){
                postfix.append(specialChars.peek());
                specialChars.pop();
            }
        }

        return postfix.toString();
    }

    private static String calculateBinaryAnswer(String equation){
        StringBuilder num = new StringBuilder(new StringBuilder());
        Stack<String> stackOfNums = new Stack<>();
        long result = 0b0;
        char[] eqnChars = equation.toCharArray();
        boolean isLastCharNum = false;
        for(int i = 0; i < equation.length(); i++){
            if(isPartOfNumber(eqnChars[i])){
                num.append(eqnChars[i]);
                isLastCharNum = true;
            }
            else if(eqnChars[i] == '$'){
                num.append("-");
            }
            else if(eqnChars[i] == ' '){
                stackOfNums.add(num.toString());
                num = new StringBuilder();
                isLastCharNum = true;
            }
            else{
                if(isLastCharNum){
                    stackOfNums.add(num.toString());
                    num = new StringBuilder();
                    isLastCharNum = false;
                }
                long secNum = 0b0;
                secNum = Long.parseLong(stackOfNums.pop());
                long firstNum = 0b0;
                firstNum = Long.parseLong(stackOfNums.pop());
                switch (eqnChars[i]) {
                    case '+':
                        result = firstNum + secNum;
                        break;
                    case '-':
                        result = firstNum - secNum;
                        break;
                    case '*':
                        result = firstNum * secNum;
                        break;
                    case '/':
                        result = firstNum / secNum;
                        break;
                    case '^':
                        result = (int)Math.pow(firstNum, secNum);
                        break;
                }
                stackOfNums.add(String.valueOf(result));
            }
        }
        return Long.toBinaryString(Long.parseLong(stackOfNums.peek()));
    }

    private static String calculateHexAnswer(String equation){
        equation = equation.toLowerCase();
        StringBuilder num = new StringBuilder(new StringBuilder());
        Stack<String> stackOfNums = new Stack<>();
        long result = 0x0;
        char[] eqnChars = equation.toCharArray();
        boolean isLastCharNum = false;
        for(int i = 0; i < equation.length(); i++){
            if(isPartOfNumber(eqnChars[i])){
                num.append(eqnChars[i]);
                isLastCharNum = true;
            }
            else if(eqnChars[i] == '$'){
                num.append("-");
            }
            else if(eqnChars[i] == ' '){
                stackOfNums.add(num.toString());
                num = new StringBuilder();
                isLastCharNum = true;
            }
            else{
                if(isLastCharNum){
                    stackOfNums.add(num.toString());
                    num = new StringBuilder();
                    isLastCharNum = false;
                }
                long secNum = 0x0;
                secNum = Long.parseLong(stackOfNums.pop(), 16);
                long firstNum = 0x0;
                firstNum = Long.parseLong(stackOfNums.pop(), 16);
                switch (eqnChars[i]) {
                    case '+':
                        result = firstNum + secNum;
                        break;
                    case '-':
                        result = firstNum - secNum;
                        break;
                    case '*':
                        result = firstNum * secNum;
                        break;
                    case '/':
                        result = firstNum / secNum;
                        break;
                    case '^':
                        result = (int)Math.pow(firstNum, secNum);
                        break;
                }
                stackOfNums.add(String.valueOf(result));
            }
        }
        return Long.toHexString(Long.parseLong(stackOfNums.peek())).toUpperCase();
    }
}
