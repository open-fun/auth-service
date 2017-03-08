package features.environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Stanislav on 04.03.17.
 */
public class CommandRunner {

  public static String exec(String... command) {
    try {
      ProcessBuilder builder = new ProcessBuilder(command);
      builder.redirectErrorStream(true);
      Process process = builder.start();
      try (InputStream is = process.getInputStream()) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

          String line = null;
          StringBuilder sb = new StringBuilder();
          while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
          }
          return sb.toString();
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
