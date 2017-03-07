package me.treaba.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

  public static void main(String[] args) {
    try {
      Thread.sleep(5000);
      SpringApplication.run(AuthServiceApplication.class, args);
    } catch (Exception e) {
      //rerun on any issue
      main(args);
    }
  }
}
