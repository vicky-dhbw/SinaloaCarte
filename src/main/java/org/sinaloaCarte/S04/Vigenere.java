package org.sinaloaCarte.S04;

import java.util.HashMap;
import java.util.Map;

public class Vigenere {
    public static final Map<Character, Integer> characterToIntegerMap;
    public static final Map<Integer, Character> integerToCharacterMap;
    private static String key;

    static {
        characterToIntegerMap = new HashMap<>();
        characterToIntegerMap.put('A', 0);
        characterToIntegerMap.put('a', 0);
        characterToIntegerMap.put('B', 1);
        characterToIntegerMap.put('b', 1);
        characterToIntegerMap.put('C', 2);
        characterToIntegerMap.put('c', 2);
        characterToIntegerMap.put('D', 3);
        characterToIntegerMap.put('d', 3);
        characterToIntegerMap.put('E', 4);
        characterToIntegerMap.put('e', 4);
        characterToIntegerMap.put('F', 5);
        characterToIntegerMap.put('f', 5);
        characterToIntegerMap.put('G', 6);
        characterToIntegerMap.put('g', 6);
        characterToIntegerMap.put('H', 7);
        characterToIntegerMap.put('h', 7);
        characterToIntegerMap.put('I', 8);
        characterToIntegerMap.put('i', 8);
        characterToIntegerMap.put('J', 9);
        characterToIntegerMap.put('j', 9);
        characterToIntegerMap.put('K', 10);
        characterToIntegerMap.put('k', 10);
        characterToIntegerMap.put('L', 11);
        characterToIntegerMap.put('l', 11);
        characterToIntegerMap.put('M', 12);
        characterToIntegerMap.put('m', 12);
        characterToIntegerMap.put('N', 13);
        characterToIntegerMap.put('n', 13);
        characterToIntegerMap.put('O', 14);
        characterToIntegerMap.put('o', 14);
        characterToIntegerMap.put('P', 15);
        characterToIntegerMap.put('p', 15);
        characterToIntegerMap.put('Q', 16);
        characterToIntegerMap.put('q', 16);
        characterToIntegerMap.put('R', 17);
        characterToIntegerMap.put('r', 17);
        characterToIntegerMap.put('S', 18);
        characterToIntegerMap.put('s', 18);
        characterToIntegerMap.put('T', 19);
        characterToIntegerMap.put('t', 19);
        characterToIntegerMap.put('U', 20);
        characterToIntegerMap.put('u', 20);
        characterToIntegerMap.put('V', 21);
        characterToIntegerMap.put('v', 21);
        characterToIntegerMap.put('W', 22);
        characterToIntegerMap.put('w', 22);
        characterToIntegerMap.put('X', 23);
        characterToIntegerMap.put('x', 23);
        characterToIntegerMap.put('Y', 24);
        characterToIntegerMap.put('y', 24);
        characterToIntegerMap.put('Z', 25);
        characterToIntegerMap.put('z', 25);
    }

    static {
        integerToCharacterMap = new HashMap<>();
        integerToCharacterMap.put(0, 'A');
        integerToCharacterMap.put(1, 'B');
        integerToCharacterMap.put(2, 'C');
        integerToCharacterMap.put(3, 'D');
        integerToCharacterMap.put(4, 'E');
        integerToCharacterMap.put(5, 'F');
        integerToCharacterMap.put(6, 'G');
        integerToCharacterMap.put(7, 'H');
        integerToCharacterMap.put(8, 'I');
        integerToCharacterMap.put(9, 'J');
        integerToCharacterMap.put(10, 'K');
        integerToCharacterMap.put(11, 'L');
        integerToCharacterMap.put(12, 'M');
        integerToCharacterMap.put(13, 'N');
        integerToCharacterMap.put(14, 'O');
        integerToCharacterMap.put(15, 'P');
        integerToCharacterMap.put(16, 'Q');
        integerToCharacterMap.put(17, 'R');
        integerToCharacterMap.put(18, 'S');
        integerToCharacterMap.put(19, 'T');
        integerToCharacterMap.put(20, 'U');
        integerToCharacterMap.put(21, 'V');
        integerToCharacterMap.put(22, 'W');
        integerToCharacterMap.put(23, 'X');
        integerToCharacterMap.put(24, 'Y');
        integerToCharacterMap.put(25, 'Z');
    }

    public static String execute(String plainMessage, boolean encrypt) {
        plainMessage = plainMessage.replace(" ", "");
        char[] characterArray = plainMessage.toCharArray();
        int[] integerArray = convertTextToIntegerArray(plainMessage.replace(" ", ""));
        int[] keyIntegerArray = convertTextToIntegerArray(key.replace(" ", ""));

        int keyIndex = 0;
        for (int i = 0; i < characterArray.length; i++) {
            int temp;
            if (encrypt) {
                temp = (integerArray[i] + keyIntegerArray[keyIndex]) % 26;
            } else {
                temp = (integerArray[i] - keyIntegerArray[keyIndex]) % 26;
            }

            if (temp < 0) {
                temp += 26;
            }

            char replacementChar = integerToCharacterMap.get(temp);
            characterArray[i] = replacementChar;

            keyIndex++;
            if (keyIndex >= key.length()) {
                keyIndex = 0;
            }
        }

        return String.valueOf(characterArray);
    }

    public static int[] convertTextToIntegerArray(String plaintext) {
        int[] result = new int[plaintext.length()];

        for (int i = 0; i < plaintext.length(); i++) {
            result[i] = characterToIntegerMap.get(plaintext.charAt(i));
        }

        return result;
    }

    public static void setKey(String key) {
        Vigenere.key = key.replace(" ", "");
    }
}
