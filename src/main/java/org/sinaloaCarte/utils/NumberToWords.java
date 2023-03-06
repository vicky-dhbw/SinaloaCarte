package org.sinaloaCarte.utils;

import org.apache.commons.lang3.StringUtils;

public class NumberToWords {

    private static final String[] UNITS = {"", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
    private static final String[] TEENS = {"", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"};
    private static final String[] TENS = {"", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"};
    private static final String[] THOUSANDS = {"", "THOUSAND", "MILLION", "BILLION"};

    public static String convert(int number) {
        if (number == 0) {
            return "ZERO";
        }

        StringBuilder builder = new StringBuilder();
        int thousandsCount = 0;

        while (number > 0) {
            int chunkValue = number % 1000;
            if (chunkValue != 0) {
                if (thousandsCount > 0) {
                    builder.insert(0, THOUSANDS[thousandsCount]);
                }
                builder.insert(0, convertChunk(chunkValue));
            }
            number /= 1000;
            thousandsCount++;
        }

        return builder.toString();
    }

    private static String convertChunk(int number) {
        StringBuilder builder = new StringBuilder();
        if (number >= 100) {
            builder.append(UNITS[number / 100]).append("hundred");
            number %= 100;
            if (number > 0) {
                builder.append("and");
            }
        }

        if (number >= 11 && number <= 19) {
            builder.append(TEENS[number - 10]);
        } else if (number == 10 || number >= 20) {
            builder.append(TENS[number / 10]);
            number %= 10;
        }

        if (number >= 1 && number <= 9) {
            builder.append(UNITS[number]);
        }

        return builder.toString().toUpperCase();
    }

}
