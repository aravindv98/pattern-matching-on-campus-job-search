import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BoyerMoore {

  public static int SIZE = 500;
  public static int[] table = new int[SIZE];

  public void tableShift(String pattern) {
    int i, j, m;
    char[] P = pattern.toCharArray();
    m = pattern.length();

    for (i = 0; i < SIZE; i++) {
      table[i] = m;
    }

    for (j = 0; j < m - 1; j++) {
      table[P[j]] = m - 1 - j;
    }

  }

  public int horspoolMatch(String pattern, String text) {
    int i, k, pos, m, n;
    char[] T = text.toCharArray();
    char[] P = pattern.toCharArray();
    m = pattern.length();
    n = text.length();

    int compCount = 0;

    for (i = m - 1; i < n; ) {

      k = 0;

      while ((k < m) && P[m - 1 - k] == T[i - k]) {
        k++;
        compCount++;
      }

      if (k == m) {
        pos = i - m + 2;
        System.out.println("Number of comparisons: "+compCount);
        return pos;
      } else {
        i += table[T[i]];
      }

      compCount++;
    }
    System.out.println("Number of comparisons: "+compCount);
    return -1;
  }

  public static void main(String[] args) {
    int pos;
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
    BoyerMoore sT = new BoyerMoore();

    sT.tableShift(pattern);
    pos = sT.horspoolMatch(pattern, text);

    if (pos == -1) {
      System.out.println("Job Not Found");
    } else {
      pos--;
      System.out.println("Job found with string: " + text.substring(pos) + " starting at position: " +pos + "\n");
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