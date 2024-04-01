package binay_pratice;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTheTxtFile {
    public static void main(String[] args) {
        // File path
        String filePath = "C:\\Users\\SC-229-USER\\Desktop\\logging (1).txt";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(filePath);

            // Wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            // Read lines from the file until the end of file is reached.
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            // Close the file.
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

