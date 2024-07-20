package chucknorris;
import java.util.Scanner;

public class Main {

    public static String chuckNorrisEncode(String message) {
        String binary = processStringToBinary(message);

        StringBuilder encoded = new StringBuilder();
        char currentBit = binary.charAt(0);
        int count = 1;

        for (int i = 1; i < binary.length(); i++) {
            if (binary.charAt(i) == currentBit) {
                count++;
            } else {
                encoded.append(currentBit == '1' ? "0 " : "00 ");
                encoded.append("0".repeat(count)).append(" ");
                currentBit = binary.charAt(i);
                count = 1;
            }
        }

        // Add the last series
        encoded.append(currentBit == '1' ? "0 " : "00 ");
        encoded.append("0".repeat(count));

        return encoded.toString().trim();
    }

    public static String chuckNorrisDecode(String encoded) {
        // Split the encoded string into blocks
        String[] blocks = encoded.split(" ");

        // Convert the blocks back to binary
        StringBuilder binary = new StringBuilder();

        for (int i = 0; i < blocks.length; i += 2) {
            char bit = blocks[i].equals("0") ? '1' : '0';
            int count = blocks[i + 1].length();
            binary.append(String.valueOf(bit).repeat(count));
        }

        // Convert binary back to ASCII characters
        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 7) {
            String charBinary = binary.substring(i, Math.min(i + 7, binary.length()));
            int asciiValue = Integer.parseInt(charBinary, 2);
            decoded.append((char) asciiValue);
        }

        return decoded.toString();
    }

    static String processStringToBinary(String string) {
        StringBuilder binaryForm = new StringBuilder();
        for (char ch : string.toCharArray()) {
            String binary = String.format("%7s", Integer.toBinaryString(ch)).replace(' ', '0');
            binaryForm.append(binary);
        }
        return binaryForm.toString();
    }

    static boolean checkIfCanDecode(String encoded) {
        if (!encoded.matches("^[0 ]+$")) {
            System.out.println("Encoded string is not valid.");
            return false;
        }
        String[] blocks = encoded.split(" ");
        if (blocks.length % 2 != 0) {
            System.out.println("Encoded string is not valid.");
            return false;
        }
        for (int i = 0; i < blocks.length; i += 2) {
            if (!blocks[i].equals("0") && !blocks[i].equals("00")) {
                System.out.println("Encoded string is not valid.");
                return false;
            }
        }
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < blocks.length; i += 2) {
            char bit = blocks[i].equals("0") ? '1' : '0';
            int count = blocks[i + 1].length();
            binary.append(String.valueOf(bit).repeat(count));
        }
        if (binary.length() % 7 != 0) {
            System.out.println("Encoded string is not valid.");
            return false;
        }
        return true;
    }

    static void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String input = scanner.nextLine();
            if (input.equals("encode")) {
                System.out.println("Input string:");
                String message = scanner.nextLine();
                System.out.println("Encoded string:");
                System.out.println(chuckNorrisEncode(message));
            } else if (input.equals("decode")) {
                System.out.println("Input encoded string:");
                String message = scanner.nextLine();
                if (!checkIfCanDecode(message)) {
                    continue;
                }
                System.out.println("Decoded string:");
                System.out.println(chuckNorrisDecode(message));
            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                System.exit(0);
            } else {
                System.out.println("There is no '" + input + "' operation");
            }
        }
    }

    public static void main(String[] args) {
        start();
    }
}
