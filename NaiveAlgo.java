import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class NaiveAlgo {

  static void naiveSearch(String T, String P) {
    boolean flag = false;
    int nTxtLen = T.length();
    int mPatLen = P.length();

    int compCount = 1;

    for (int i = 0; i <= (nTxtLen - mPatLen); i++) {
      int j = 0;

      while (j < mPatLen && T.charAt(i + j) == P.charAt(j)) {
        compCount++;
        j = j + 1;
      }
      if (j == mPatLen) {
        flag = true;
        System.out.println("Job found with string: " + T.substring(i)+ " starting a position " +i);
        break;
      }
      compCount++;
    }
    System.out.println("Number of comparisons: " + compCount);
    if(!flag){
      System.out.println("Job not found!");
    }
  }
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the file path containing the job description:");
    String filePath = sc.next();
    String T = "";
    try {
      String fileContent = readAndStoreWords(filePath);
      System.out.println("Words in the file: " + fileContent);
      T = fileContent;
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
    System.out.println("Enter the job search:");
    String P = sc.next();

    naiveSearch(T, P);

  }
  public static String readAndStoreWords(String filePath) throws IOException {
    StringBuilder fileContent = new StringBuilder();

    try (FileReader fileReader = new FileReader(filePath);
         BufferedReader bufferedReader = new BufferedReader(fileReader)) {

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] words = line.split("\\s+");
        for (String word : words) {
          fileContent.append(word).append(" ");
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return fileContent.toString();
  }
}