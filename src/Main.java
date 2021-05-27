/** Program that translates virtual addresses to physical addresses in a tiny computing system
 *
 */

import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        int[] pageTable = {2, 4, 1, 7, 3, 5, 6}; // Virtual to Physical address mapping
        float start = System.nanoTime();
        String inputFile = args[0];

        // Attach File to dataInputStream
        File file = new File(inputFile);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        // Create ArrayList of Longs to hold the Virtual Addresses
        ArrayList<Long> addresses = new ArrayList<Long>();

        // Create Buffered Writer to write the translated addresses to a text file
        FileWriter fw = new FileWriter("output-OS1.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        // While loop that reads in virtual addresses and translates and outputs the corresponding physical addresses to a file
        while (dataInputStream.available()>0){
            Long temp = dataInputStream.readLong(); // Reads address
            Long reversed = Long.reverseBytes(temp); // Reverses bytes in address to get the correct format
            String bitAddress = Long.toBinaryString(reversed); // Converts the long  address to its String representation
            if (bitAddress.length() < 7){   // Ensures that zeros are not cut off in the Long to String conversion above
                int num0 = 7 - bitAddress.length();
                String zeroString = "";
                for (int i = 0; i < num0; i ++){
                    zeroString += "0";
                }
                bitAddress = zeroString + bitAddress;
            }
            int virtualAddressNum = getInt(bitAddress); // Gets the virtual address number
            if (virtualAddressNum > 6){   // If the virtual address cannot be mapped a message is written to the file for that address
                bw.write("Address " + bitAddress + " could not be translated" + "\n");
                continue;
            }
            int physicalAddressNum = pageTable[virtualAddressNum]; // Translates the virtual address number to the corresponding physical address number
            String physicalAddressNumString = Integer.toBinaryString(physicalAddressNum); // Translates the physical address number to its binary representation in String format

            try {
                String out = physicalAddressNumString + bitAddress.substring(bitAddress.length() - 7 ,bitAddress.length()); // The binary representation (in String format) of the out address by combing the physical address number with the offset
                Long decimal = Long.parseLong(out, 2);
                String hexStr = Long.toHexString(decimal); // Hexidecimal representation of physical address
                int num0 = 0;
                String zeroString = "";
                for (int i = 0; i < 16 - hexStr.length(); i++){  // Padding to get the output in the format specified in the announcement
                    zeroString += "0";
                }
                hexStr = zeroString + hexStr;
                bw.write(hexStr + "\n"); // Writing physical address to the output file
            }
            catch (Exception e){
                System.out.println(bitAddress);
            }
        }
        bw.close();
        fw.close();
        long end = System.nanoTime();
        System.out.println(end-start);
    }

    /**
     * Converts a binary String to an Integer
     * @param str
     * @return Int
     */
    public static int getInt(String str){
        int num=0;
        int count = 0;
        if (str.length() == 7)
            return 0;
        for (int i = str.length()-8; i >= 0; i --){
            if (str.charAt(i) == '1') {
                num += Math.pow(2, count);
            }
            count ++;
        }
        return num;
    }
}
