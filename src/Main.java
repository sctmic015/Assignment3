import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.*;

public class Main {
    private static final int BUFFER_SIZE = 7;
    public static void main(String[] args) throws IOException {

        int[] pageTable = {2, 4, 1, 7, 3, 5, 6};
        float start = System.nanoTime();
        String inputFile = "OS1sequence";
        //String outputFile = "outBru";

        //byte[] bytes = getByte(inputFile);
        File file = new File(inputFile);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        ArrayList<Long> addresses = new ArrayList<Long>();

        FileWriter fw = new FileWriter("out.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        while (dataInputStream.available()>0){
            Long temp = dataInputStream.readLong();
            Long reversed = Long.reverseBytes(temp);
            String bitAddress = Long.toBinaryString(reversed);
            if (bitAddress.length() < 7){
                int num0 = 7 - bitAddress.length();
                String zeroString = "";
                for (int i = 0; i < num0; i ++){
                    zeroString += "0";
                }
                bitAddress = zeroString + bitAddress;
            }
            int virtualAddressNum = getInt(bitAddress);
            int physicalAddressNum = pageTable[virtualAddressNum];
            //System.out.println(virtualAddressNum + " : " + physicalAddressNum);
            String physicalAddressNumString = Integer.toBinaryString(physicalAddressNum);
            //System.out.println(physicalAddressNumString);
            try {
                String out = physicalAddressNumString + bitAddress.substring(bitAddress.length() - 7 ,bitAddress.length());
                Long decimal = Long.parseLong(out, 2);
                String hexStr = Long.toHexString(decimal);
                int num0 = 0;
                String zeroString = "";
                for (int i = 0; i < 16 - hexStr.length(); i++){
                    zeroString += "0";
                }
                hexStr = zeroString + hexStr;
                bw.write(hexStr + "\n");
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

    /**
    public static byte[] getByte(String inputFile) throws IOException, FileNotFoundException {
        byte[] buffer = new byte[1];
        InputStream inputStream = new FileInputStream(inputFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        while (inputStream.read(buffer) != -1){
            outputStream.write(buffer);
        }
        //System.out.print(outputStream);
        inputStream.close();
        outputStream.close();

        return outputStream.toByteArray();
    }

    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    public static String[] splitString(String string) {
        String[] out = new String[string.length() / 16];
        int splitCount = 0;
        out[0] = "";
        for (int i = 0; i < string.length(); i ++){
            if (i % 16 == 0 && i != 0) {
                splitCount ++;
                out[splitCount] = "";
            }
            out[splitCount] += string.charAt(i);
        }
        return out;
    }

    public static String reverse(String str) {
        StringBuilder out = new StringBuilder();
        out.append(str);
        out.reverse();
        String outString = out.toString();
        System.out.println(out);

        char[] thing = outString.toCharArray();
        for (int i = 0; i < thing.length; i ++) {
            char temp = thing[i+1];
            thing[i+1] = thing[i];
            thing[i] = temp;
            i ++;
        }
        return String.valueOf(thing);
    }

    public static String[] reverseArray(String[] array){
        for (int i = 0; i < array.length; i ++){
            array[i] = reverse(array[i]);
        }
        return array;
    }

    public static long[] longArray(String[] array) {
        long[] longOut = new long[array.length];

        for (int i = 0; i < array.length; i ++) {
            longOut[i] = Long.parseLong(array[i]);
        }
        return longOut;
    }

     String inputFile = "OS1sequence";
     String outputFile = "output.txt";

     try (
     InputStream inputStream = new FileInputStream(inputFile);
     OutputStream outputStream = new FileOutputStream(outputFile);
     ) {

     byte[] buffer = new byte[BUFFER_SIZE];

     while (inputStream.read(buffer) != -1) {
     outputStream.write(buffer);
     }

     } catch (IOException ex) {
     ex.printStackTrace();
     }
     **/
}
