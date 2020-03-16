import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;


public class playfair {
    final static int playfairLength = 5;
    final static char oddLetter = 'X';
    final static char replacedLetter = 'J';

    public static void main(String[] args) {
        char[][] table = getTable(args[3]);
        if (args.length < 5) {
            System.out.println("Nuk keni mjaftueshem argumente!");
            System.exit(-1);
        }
        String function = args[2].toLowerCase();
        if (function.contentEquals("encrypt")) {
            System.out.println(Encrypt(args[4], table));
        } else if (function.contentEquals("decrypt")) {
            System.out.println(Decrypt(args[4], table));

        } else {
            System.out.println("Funksion jovalid!");
            System.exit(-2);
        }
        PrintTable(table);
    }


    public static String Encrypt(String plaintext, char[][] table) {
        String result = "";
        plaintext = plaintext.toUpperCase();
        plaintext = removeSpaces(plaintext);
        if (plaintext.length() % 2 != 0)
            plaintext = plaintext + oddLetter;

        int m = (int) Math.round(plaintext.length() / 2.0);
        int p = 0;
        char pairs[][] = new char[m][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 2; j++) {
                char currentchar = plaintext.charAt(p);
                if (currentchar == replacedLetter)
                    currentchar = 'I';
                pairs[i][j] = currentchar;
                p++;
            }
            PlayfairUnit pu = getSameRowColumn(pairs[i][0], pairs[i][1], table);
            if (pu.type == PlayfairType.column) {
                if (pu.row1 < playfairLength - 1) {
                    result += table[pu.row1 + 1][pu.column1];
                } else {
                    result += table[0][pu.column1];
                }
                if (pu.row2 < playfairLength - 1) {
                    result += table[pu.row2 + 1][pu.column2];
                } else {
                    result += table[0][pu.column2];
                }
            } else if (pu.type == PlayfairType.row) {
                if (pu.column1 < playfairLength - 1) {
                    result += table[pu.row1][pu.column1 + 1];
                } else {
                    result += table[pu.row1][0];
                }
                if (pu.column2 < playfairLength - 1) {
                    result += table[pu.row2][pu.column2 + 1];
                } else {
                    result += table[pu.row2][0];
                }
            } else {

                result += table[pu.row1][pu.column2];
                result += table[pu.row2][pu.column1];
            }
            result += " ";
        }
        return result;
    }

    public static String Decrypt(String ciphertext, char[][] table) {
        String result = "";
        ciphertext = ciphertext.toUpperCase();
        ciphertext = removeSpaces(ciphertext);
        int m = (ciphertext.length() / 2);
        int p = 0;
        char pairs[][] = new char[m][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 2; j++) {
                char currentchar = ciphertext.charAt(p);
                pairs[i][j] = currentchar;
                p++;
            }
            PlayfairUnit pu = getSameRowColumn(pairs[i][0], pairs[i][1], table);
            if (pu.type == PlayfairType.column) {
                if (pu.row1 == 0) {
                    result += table[playfairLength - 1][pu.column1];
                } else {
                    result += table[pu.row1 - 1][pu.column1];
                }
                if (pu.row2 == 0) {
                    result += table[playfairLength - 1][pu.column2];
                } else {
                    result += table[pu.row2 - 1][pu.column2];
                }
            } else if (pu.type == PlayfairType.row) {
                if (pu.column1 == 0) {
                    result += table[pu.row1][playfairLength - 1];
                } else {
                    result += table[pu.row1][pu.column1 - 1];
                }
                if (pu.column2 == 0) {
                    result += table[pu.row2][playfairLength - 1];
                } else {
                    result += table[pu.row2][pu.column2 - 1];
                }
            } else {

                result += table[pu.row1][pu.column2];
                result += table[pu.row2][pu.column1];
            }
        }

        if (result.charAt(result.length() - 1) == oddLetter)
            result = result.substring(0, result.length() - 1);
        return result;
    }


    public static PlayfairUnit getSameRowColumn(char ch1, char ch2, char[][] table) {
        int i1 = -1, j1 = -2, i2 = -3, j2 = -4;//some random unused indexes
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (table[i][j] == ch1) {
                    i1 = i;
                    j1 = j;
                }
                if (table[i][j] == ch2) {
                    i2 = i;
                    j2 = j;
                }
            }
        }
        PlayfairType temp;
        if (i1 == i2) {
            temp = PlayfairType.row;
        } else if (j1 == j2) {
            temp = PlayfairType.column;
        } else {
            temp = PlayfairType.square;
        }
        PlayfairUnit result = new PlayfairUnit(i1, i2, j1, j2, temp);
        return result;
    }

    public static String removeSpaces(String text) {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') {
                result += text.charAt(i);
            }
        }
        return result;
    }

    public static char[][] getTable(String key) {
        HashMap hm = new HashMap();
        char result[][] = new char[5][5];
        key = key.trim();
        key = key.toUpperCase();
        int IndexKey = 0;
        char symbolA = 65;//A in ascii code is 65
        for (int i = 0; i < 5; i++) {
            boolean symbolPlaced = false;
            int j = 0;
            while (!symbolPlaced || (j < 5)) {
                if (IndexKey < key.length()) {
                    //until the key is placed in the table
                    char symbolK = key.charAt(IndexKey);
                    if (symbolK != replacedLetter) {
                        if (!hm.containsKey(symbolK)) {
                            result[i][j] = symbolK;
                            int value[] = {i, j};
                            hm.put(symbolK, value);
                            symbolPlaced = true;
                            j++;
                        }
                    }
                    IndexKey++;
                } else if (IndexKey >= key.length()) {
                    //place the remaining letter of the alphabet except J
                    if (symbolA == replacedLetter) symbolA++;
                    if (!hm.containsKey(symbolA)) {
                        result[i][j] = symbolA;
                        int value[] = {i, j};
                        hm.put(symbolA, value);
                        symbolPlaced = true;
                        j++;
                    }
                    symbolA++;
                }
            }
        }
        return result;
    }

    public static void PrintTable(char[][] table) {
        System.out.println("Encrypt table: ");
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
}

