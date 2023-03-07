package org.sinaloaCarte.S04;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class EngineCracker implements IEngineCracker{
    private HashSet<String> dictionary;

    public EngineCracker() {
        readDictionary();
    }

    public void execute(String cipher, int keyLength, int firstWordLength) {
        ArrayList<String> possiblePlainMessageList = new ArrayList<>();
        char[] key = new char[keyLength];
        System.out.println("cipher | " + cipher);

        for (int i = 0; i < Math.pow(26, keyLength); i++) {
            generateKey(key, keyLength);

            Vigenere.setKey(String.valueOf(key));
            String plainMessage = Vigenere.execute(cipher.substring(0, firstWordLength), false);

            if (dictionary.contains(plainMessage)) {
                System.out.println("recognized first word | " + plainMessage);
                possiblePlainMessageList.add(Vigenere.execute(cipher, false));
            }
        }

        System.out.println("[possible plain messages]");
        possiblePlainMessageList.forEach(System.out::println);
    }

    private void generateKey(char[] key, int keyLength) {
        if (key[0] == 0) {
            for (int i = 0; i < keyLength; i++) {
                key[i] = 'a';
            }
        } else {
            int counter = 1;
            boolean isIncremented = false;
            while (!isIncremented) {
                if (key[keyLength - counter] == 'z') {
                    key[keyLength - counter] = 'a';
                } else {
                    int ascii = key[keyLength - counter];
                    ascii++;
                    key[keyLength - counter] = (char) ascii;
                    isIncremented = true;
                }
                counter++;
            }
        }
    }

    private void readDictionary() {
        try {
            String currentLine;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dictionary.txt"));

            int dictionarySize = countLines();
            dictionary = new HashSet<>();

            for (int i = 0; i < dictionarySize; i++) {
                currentLine = bufferedReader.readLine();
                dictionary.add(currentLine);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private int countLines() {
        try {
            byte[] byteArray = new byte[1024];
            int count = 0;
            int numberOfCharacters;
            boolean empty = true;

            InputStream inputStream = new BufferedInputStream(new FileInputStream("dictionary.txt"));

            while ((numberOfCharacters = inputStream.read(byteArray)) != -1) {
                empty = false;
                for (int i = 0; i < numberOfCharacters; ++i) {
                    if (byteArray[i] == '\n') {
                        ++count;
                    }
                }
            }

            return (count == 0 && !empty) ? 1 : count + 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }
}
