package com.mycompany.test;


import java.util.LinkedList;
import java.util.Stack;

public class MyClass {

    public static void main(String[] args) {
        String s = "abd(jnb)asdf";
        String s1 = "abdjnbasdf";
        String s2 = "dd(df)a(ghhh)";
        System.out.println(s);
        System.out.println(reverseString(s));
        System.out.println(s1);
        System.out.println(reverseString(s1));
        System.out.println(s2);
        System.out.println(reverseString(s2));
    }

    static public String reverseString(String word) {
        Stack<Character> characterStack = new Stack<>();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ')') {
                LinkedList<Character> linkedList = new LinkedList<>();
                while (characterStack.peek() != '(') {
                    linkedList.add(characterStack.pop());
                }
                characterStack.pop();
                while (linkedList.size() != 0) {
                    characterStack.push(linkedList.remove());
                }
            } else {
                characterStack.push(word.charAt(i));
            }
        }
        char[] charsArray = new char[characterStack.size()];
        int i = charsArray.length - 1;
        while (characterStack.size() > 0) {
            charsArray[i] = characterStack.pop();
            i--;
        }

        return new String(charsArray);
    }
}
