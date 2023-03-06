package org.sinaloaCarte.utils;

import java.util.HashMap;
import java.util.Map;

public class WordToNumber {
    private static final Map<String, Integer> wordsToNumbers;

    static {
        wordsToNumbers = new HashMap<>();
        wordsToNumbers.put("ZERO", 0);
        wordsToNumbers.put("ONE", 1);
        wordsToNumbers.put("TWO", 2);
        wordsToNumbers.put("THREE", 3);
        wordsToNumbers.put("FOUR", 4);
        wordsToNumbers.put("FIVE", 5);
        wordsToNumbers.put("SIX", 6);
        wordsToNumbers.put("SEVEN", 7);
        wordsToNumbers.put("EIGHT", 8);
        wordsToNumbers.put("NINE", 9);
        wordsToNumbers.put("TEN", 10);
        wordsToNumbers.put("ELEVEN", 11);
        wordsToNumbers.put("TWELVE", 12);
        wordsToNumbers.put("THIRTEEN", 13);
        wordsToNumbers.put("FOURTEEN", 14);
        wordsToNumbers.put("FIFTEEN", 15);
        wordsToNumbers.put("SIXTEEN", 16);
        wordsToNumbers.put("SEVENTEEN", 17);
        wordsToNumbers.put("EIGHTEEN", 18);
        wordsToNumbers.put("NINETEEN", 19);
        wordsToNumbers.put("TWENTY", 20);
    }

    public static int convert(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Input word cannot be null or empty");
        }

        Integer n = wordsToNumbers.get(word.toUpperCase());
        if (n == null) {
            throw new IllegalArgumentException("Invalid input word: " + word);
        }

        return n;
    }
}


