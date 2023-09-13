import java.io.*;
import java.util.*;

public class Solution {
  public static void main(String[] args) throws Exception {
    InputStreamReader r = new InputStreamReader(System.in);
    BufferedReader in = new BufferedReader(r);

    String conversion_rate = in.readLine();
    String conversion[] = conversion_rate.split(";");

    String currency1 = in.readLine();
    String currency2 = in.readLine();

    if (currency2.equals(currency1)) {
      System.out.println(0);
      return;
    }

    Queue<String> queue = new LinkedList<>();
    ArrayList<String> visited = new ArrayList<>();

    for (String temp : conversion) {
      String conversion_one[] = temp.split(",");

      if (conversion_one[0].equals(currency1)) {
        queue.add(conversion_one[1]);
        queue.add(conversion_one[2]);
      } else if (conversion_one[1].equals(currency1)) {
        double d = Double.parseDouble(conversion_one[2]);
        d = 1 / d;
        d = Math.round(d * 100.0) / 100.0;
        queue.add(conversion_one[0]);
        queue.add(d + "");
      }
    }

    visited.add(currency1);
    double max = -1.0;

    while (!queue.isEmpty()) {
      String currency = queue.poll();
      double rate = Double.parseDouble(queue.poll());

      if (currency.equals(currency2)) {
        if (rate > max) {
          max = rate;
        }
      } else {
        for (String temp : conversion) {
          String conversion_one[] = temp.split(",");

          if (conversion_one[0].equals(currency) && !visited.contains(currency)) {
            double d = Double.parseDouble(conversion_one[2]);
            d = d * rate;
            d = Math.round(d * 100.0) / 100.0;
            queue.add(conversion_one[1]);
            queue.add(d + "");
          } else if (conversion_one[1].equals(currency) && !visited.contains(currency)) {
            double d = Double.parseDouble(conversion_one[2]);
            d = 1 / d;
            d = d * rate;
            d = Math.round(d * 100.0) / 100.0;
            queue.add(conversion_one[0]);
            queue.add(d + "");
          }
        }
        visited.add(currency);
      }
    }

    System.out.println(max);
  }
}
