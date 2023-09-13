import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class KMP {
  void KMPSearch(String pat, String txt) {
    boolean flag = false;
    int M = pat.length();
    int N = txt.length();
    int count = 0;

    // create lps[] that will hold the longest
    // prefix suffix values for pattern
    int[] lps = new int[M];
    int j = 0; // index for pat[]

    // Preprocess the pattern (calculate lps[]
    // array)
    computeLPSArray(pat, M, lps);

    int i = 0; // index for txt[]
    while ((N - i) >= (M - j)) {
      count += 1;
      if (pat.charAt(j) == txt.charAt(i)) {
        j++;
        i++;
      }
      if (j == M) {
        flag = true;
        System.out.println("Job found with string: " + txt.substring(i - j) + " starting " +
                "at position: " + (i - j));
        j = lps[j - 1];
        break;
      }

      // mismatch after j matches
      else if (i < N
              && pat.charAt(j) != txt.charAt(i)) {
        // Do not match lps[0..lps[j-1]] characters,
        // they will match anyway
        if (j != 0)
          j = lps[j - 1];
        else
          i = i + 1;
      }
    }
    if (!flag)
      System.out.println("Job Not Found");
    System.out.println("Number of comparisons: " + count);
  }

  void computeLPSArray(String pat, int M, int[] lps) {
    // length of the previous longest prefix suffix
    int len = 0;
    int i = 1;
    lps[0] = 0; // lps[0] is always 0

    // the loop calculates lps[i] for i = 1 to M-1
    while (i < M) {
      if (pat.charAt(i) == pat.charAt(len)) {
        len++;
        lps[i] = len;
        i++;
      } else // (pat[i] != pat[len])
      {

        if (len != 0) {
          len = lps[len - 1];

          // Also, note that we do not increment
          // i here
        } else // if (len == 0)
        {
          lps[i] = len;
          i++;
        }
      }
    }
  }

  // Driver code
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
    new KMP().KMPSearch(pattern, text);
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
