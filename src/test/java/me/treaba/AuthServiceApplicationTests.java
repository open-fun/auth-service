package me.treaba;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// Do not use spring in tests. Use integration level (features) instead
// Otherwise if you need a lot of deps then is not a unit test anymore.
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AuthServiceApplicationTests {

  @Test
  public void contextLoads() {
  }

}
