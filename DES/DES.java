import java.util.HashMap;
/**
 * Data Encryption Standard Algorithm
 *
 * @author Luisely Doza
 * @version June 4, 2020
 */
public class DES {
    int[] IP = {58, 50, 42, 34, 26, 18, 10, 2, 
                60, 52, 44, 36, 28, 20, 12, 4, 
                62, 54, 46, 38, 30, 22, 14, 6, 
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17, 9, 1, 
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 33, 45, 37, 29, 21, 13, 5, 
                63, 55, 47, 39, 31, 23, 15, 7};
    
    int[] PC1 = {57, 49, 41, 33, 25, 17, 9,
                 1, 58, 50, 42, 34, 26, 18,
                 10, 2, 59, 51, 43, 35, 27,
                 19, 11, 3, 60, 52, 44, 36,
                 63, 55, 47, 39, 31, 23, 15,
                 7, 62, 54, 46, 38, 30, 22,
                 14, 6, 61, 53, 45, 37, 29,
                 21, 13, 5, 28, 20, 12, 4};
                 
    int[] bitShifts = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    
    int[] PC2 = {14, 17, 11, 24, 1, 5,
                 3, 28, 15, 6, 21, 10,
                 23, 19, 12, 4, 26, 8,
                 16, 7, 27, 20, 13, 2,
                 41, 52, 31, 37, 47, 55,
                 30, 40, 51, 45, 33, 48,
                 44, 49, 39, 56, 34, 53,
                 46, 42, 50, 36, 29, 32};
                 
    int[] eBit = {32, 1, 2, 3, 4, 5,
                  4, 5, 6, 7, 8, 9,
                  8, 9, 10, 11, 12, 13,
                  12, 13, 14, 15, 16, 17,
                  16, 17, 18, 19, 20, 21,
                  20, 21, 22, 23, 24, 25,
                  24, 25, 26, 27, 28, 29,
                  28, 29 , 30, 31, 32, 1};
                  
    int[][][] sBoxes = {
        {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
         {0,15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
         {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
         {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
         
        {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
         {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
         {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
         {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
         
        {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
         {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
         {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
         {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
         
        {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
         {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
         {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
         {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
         
        {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
         {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
         {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
         {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
         
        {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
         {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
         {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
         {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
         
        {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
         {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
         {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0 ,5, 9, 2},
         {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
         
        {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
         {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
         {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
         {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}


    };
    
    int[] P = {16, 7, 20, 21,
               29, 12, 28, 17,
               1, 15, 23, 26,
               5, 18, 31, 10,
               2, 8, 24, 14,
               32, 27, 3, 9,
               19, 13, 30, 6,
               22, 11, 4, 25};
               
    int[] IP1 = {40, 8, 48, 16, 56, 24, 64, 32,
                 39, 7, 47, 15, 55, 23, 63, 31,
                 38, 6, 46, 14, 54, 22, 62, 30,
                 37, 5, 45, 13, 53, 21, 61, 29,
                 36, 4, 44, 12, 52, 20, 60, 28,
                 35, 3, 43, 11, 51, 19, 59, 27,
                 34, 2, 42, 10, 50, 18, 58, 26,
                 33, 1, 41, 9, 49, 17, 57, 25};
                 
    public DES(String plainText, String key) {
        System.out.println("Plaintext provided: " + plainText);
        plainText = hexToBinary(plainText);
        String IP = initPerm(plainText);
        String leftIP = divideLeft(IP);
        String rightIP = divideRight(IP);
        
        System.out.println("\nKey provided: " + key);
        key = hexToBinary(key);
        String kPlus = keyPerm(key);
        String leftKey = divideLeft(kPlus);
        String rightKey = divideRight(kPlus);
        String[] keyBlocks = keyBlocks(leftKey, rightKey);
        
        System.out.println("\nencrypting message...");
        String e = encrypt(leftIP, rightIP, keyBlocks);
        
        String cipherText = binaryToHex(e);
        
        System.out.println("\nSecret Code: " + cipherText);
    }
                 
                
    public String stringToHex(String s) {
        String hex = "";
        for (int i = 0; i < s.length(); i++) {
            int ascii = (int) s.charAt(i);
            hex += Integer.toHexString(ascii).toUpperCase();
        }
        return hex;
    }
    
    public String binaryToHex(String s) {
        String hex = "";
        HashMap<String, Character> binToHex = new HashMap<String, Character>();
        binToHex.put("0000", '0');
        binToHex.put("0001", '1');
        binToHex.put("0010", '2');
        binToHex.put("0011", '3');
        binToHex.put("0100", '4');
        binToHex.put("0101", '5');
        binToHex.put("0110", '6');
        binToHex.put("0111", '7');
        binToHex.put("1000", '8');
        binToHex.put("1001", '9');
        binToHex.put("1010", 'A');
        binToHex.put("1011", 'B');
        binToHex.put("1100", 'C');
        binToHex.put("1101", 'D');
        binToHex.put("1110", 'E');
        binToHex.put("1111", 'F');
        
        String remaining = s;
        while (remaining.length() > 0) {
            String bits = remaining.substring(0,4);
            char c = binToHex.get(bits);
            hex += c;
            remaining = remaining.substring(4);
        }
        
        return hex;
    }
    
    public String hexToBinary(String s) {
        String bin = "";
        HashMap<Character, String> hexToBin = new HashMap<Character, String>();
        hexToBin.put('0', "0000");
        hexToBin.put('1', "0001");
        hexToBin.put('2', "0010");
        hexToBin.put('3', "0011");
        hexToBin.put('4', "0100");
        hexToBin.put('5', "0101");
        hexToBin.put('6', "0110");
        hexToBin.put('7', "0111");
        hexToBin.put('8', "1000");
        hexToBin.put('9', "1001");
        hexToBin.put('A', "1010");
        hexToBin.put('B', "1011");
        hexToBin.put('C', "1100");
        hexToBin.put('D', "1101");
        hexToBin.put('E', "1110");
        hexToBin.put('F', "1111");
        
        for (int i = 0; i < s.length(); i++) {
            char hex = s.charAt(i);
            bin += hexToBin.get(hex);
        }
        return bin;
    }
     
    public String keyPerm(String key) {
        String kPlus = "";
        for(int i = 0; i < PC1.length; i++) {
            int entry = PC1[i] - 1;
            kPlus += key.charAt(entry);
        }
        return kPlus;
    } 
    
    public String[] secondKeyPerm(String[] keys) {
        String[] keyBlocks = new String[16];
        for (int i = 0; i < keys.length; i++) {
            String key = "";
            for(int j = 0; j < PC2.length; j++) {
                int entry = PC2[j] - 1;
                key += keys[i].charAt(entry);
            }
            keyBlocks[i] = key;
        }
        return keyBlocks;
    }
    
    public String initPerm(String plainText) {
        String initP = "";
        for (int i = 0; i < IP.length; i++) {
            int entry = IP[i] - 1;
            initP += plainText.charAt(entry);
        }
        return initP;
    }
    
    public String inversePerm(String reversed) {
        String inverse = "";
        for (int i = 0; i < IP1.length; i++) {
            int entry = IP1[i] - 1;
            inverse += reversed.charAt(entry);
        }
        return inverse;
    }
    
    public String straightPerm(String sbox) {
        String finalPerm = "";
        for(int i = 0; i < P.length; i++) {
            int entry = P[i] - 1;
            finalPerm += sbox.charAt(entry);
        }
        return finalPerm;
    }
    
    public String divideLeft(String s) {
        int mid = s.length() / 2;
        return s.substring(0, mid);
    }
    
    public String divideRight(String s) {
        int mid = s.length() / 2;
        return s.substring(mid, s.length());
    }
    
    public String[] keyBlocks(String initLeft, String initRight) {
        String[] keyBlocks = new String[16];
        
        String[] leftBlocks = new String[16];
        leftBlocks[0] = leftShift(initLeft, bitShifts[0]);
        for (int i = 1; i < bitShifts.length; i++) {
            leftBlocks[i] = leftShift(leftBlocks[i-1], bitShifts[i]);
        }
        
        String[] rightBlocks = new String[16];
        rightBlocks[0] = leftShift(initRight, bitShifts[0]);
        for (int i = 1; i < bitShifts.length; i++) {
            rightBlocks[i] = leftShift(rightBlocks[i-1], bitShifts[i]);
        }
        
        for(int i = 0; i < keyBlocks.length; i++) {
            keyBlocks[i] = leftBlocks[i] + rightBlocks[i];
        }
        
        keyBlocks = secondKeyPerm(keyBlocks);
        
        return keyBlocks;
    }
    
    
    public String leftShift(String s, int shifts) {
        int i = 0;
        String shiftedString = s;
        while (i < shifts) {
            char bit = shiftedString.charAt(0);
            shiftedString = shiftedString.substring(1) + bit;
            i++;
        }
        return shiftedString;
    }
    
    public String expandFunc(String rightIP) {
        String expand = "";
        for (int i = 0; i < eBit.length; i++) {
            int entry = eBit[i] - 1;
            expand += rightIP.charAt(entry);
        }
        return expand;
    }
    
    public String XOR(String expanded, String key) {
        String XOR = "";
        for(int i = 0; i < expanded.length(); i++) {
            if (expanded.charAt(i) == key.charAt(i)) {
                XOR += "0";
            } else {
                XOR += "1";
            }
        }
        
        return XOR;
    }
    
    public String sBoxLookUp(String s) {
        String[] blocks = new String[8];
        
        int i = 0;
        while (s.length() > 0) {
            String block = s.substring(0,6);
            blocks[i] = block;
            s = s.substring(6);
            i++;
        }
        
        String finString = "";
        for(int j = 0; j < blocks.length; j++) {
            int row = Integer.parseInt(blocks[j].charAt(0) + "" + blocks[j].charAt(5), 2);
            int col = Integer.parseInt(blocks[j].substring(1, 5), 2);
            
            finString += Integer.toHexString(sBoxes[j][row][col]);
        }
        
        finString = hexToBinary(finString.toUpperCase());
        
        return finString;
    }
    
    public String fFunc(String right, String key) {
        String expanded = expandFunc(right);
        String xor = XOR(expanded, key);
        String sb = sBoxLookUp(xor);
        String fin = straightPerm(sb);
        return fin;
    }
    
    
    public String encrypt(String initLeft, String initRight, String[] keyBlocks) {
        String[] rightIP = new String[16];
        String[] leftIP = new String[16];
        leftIP[0] = initRight;
        rightIP[0] = XOR(initLeft, fFunc(initRight, keyBlocks[0]));
        for (int i = 1; i < leftIP.length; i++) {
            leftIP[i] = rightIP[i-1];
            rightIP[i] = XOR(leftIP[i-1], fFunc(rightIP[i-1], keyBlocks[i]));
        }
        
        String reversed = rightIP[15] + leftIP[15];
        
        String fin = inversePerm(reversed);
        
        return fin;
    }
    
                
    public static void main(String args[]) {
        String plainText = "0123456789ABCDEF";
        String key = "133457799BBCDFF1";
        DES d = new DES(plainText, key);
    }
    
}
