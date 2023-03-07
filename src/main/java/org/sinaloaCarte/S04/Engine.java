package org.sinaloaCarte.S04;

public class Engine implements IEngine{
    public String generateKey(String plainMessage, StringBuilder keyword) {
        int x = plainMessage.length();

        for (int i = 0; ; i++) {
            if (x == i) {
                i = 0;
            }

            if (keyword.length() == plainMessage.length()) {
                break;
            }

            keyword.append(keyword.charAt(i));
        }

        return keyword.toString();
    }

    public String encrypt(String plainMessage, String keyword) {
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < plainMessage.length(); i++) {
            int x = (plainMessage.charAt(i) + keyword.charAt(i)) % 26;
            x += 'A';
            cipher.append((char) (x));
        }

        return cipher.toString();
    }

    public String decrypt(String cipher, String keyword) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < cipher.length() && i < keyword.length(); i++) {
            int x = (cipher.charAt(i) - keyword.charAt(i) + 26) % 26;
            x += 'A';
            decryptedMessage.append((char) (x));
        }

        return decryptedMessage.toString();
    }
}
