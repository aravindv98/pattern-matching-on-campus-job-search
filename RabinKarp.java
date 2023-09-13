import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RabinKarp {
  private static final int PRIME = 101;

  public static int rabinKarpSearch(String text, String pattern) {
    int textLength = text.length();
    int patternLength = pattern.length();
    int patternHash = calculateHash(pattern, patternLength);
    int textHash = calculateHash(text, patternLength);

    int comparisons = 0;

    for (int i = 0; i <= textLength - patternLength; i++) {
      comparisons++;

      if (patternHash == textHash && checkEqual(text, i, i + patternLength - 1, pattern)) {
        System.out.println("Number of comparisons: " + comparisons);
        return i;
      }

      if (i < textLength - patternLength) {
        textHash = recalculateHash(text, i, i + patternLength, textHash, patternLength);
      }
    }

    System.out.println("Number of comparisons: " + comparisons);
    return -1;
  }

  private static int calculateHash(String str, int length) {
    int hash = 0;
    for (int i = 0; i < length; i++) {
      hash += str.charAt(i) * Math.pow(PRIME, i);
    }
    return hash;
  }

  private static int recalculateHash(String str, int oldIndex, int newIndex, int oldHash, int patternLength) {
    int newHash = oldHash - str.charAt(oldIndex);
    newHash /= PRIME;
    newHash += str.charAt(newIndex) * Math.pow(PRIME, patternLength - 1);
    return newHash;
  }

  private static boolean checkEqual(String text, int start1, int end1, String pattern) {
    int start2 = 0;
    while (start1 <= end1) {
      if (text.charAt(start1) != pattern.charAt(start2)) {
        return false;
      }
      start1++;
      start2++;
    }
    return true;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the file path containing the job description:");
    String filePath = sc.next();
    String text = "";
    try {
      String fileContent = readAndStoreWords(filePath);
      System.out.println("Words in the file: " + fileContent);
      text = fileContent;
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
    System.out.println("Enter the job search:");
    String pattern = sc.next();

    int index = rabinKarpSearch(text, pattern);
    if (index != -1) {
      System.out.println("Job found with string: " + text.substring(index)+ " starting a position " +index);
    } else {
      System.out.println("Job not found.");
    }
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
