package features;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeoutException;

/**
 * Created by stanislav on 04.03.17.
 */
@Slf4j
public class DockerHelper {

  public static final int WAIT_STEP = 1000;
  public static final int TIMEOUT = 60 * 1000;

  public static String inspectIp(String containerName) {
    log.info("Looking IP for " + containerName);
    String ip = CommandRunner.exec("/bin/sh", "-c", "docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' " + containerName).trim();
    log.info("Has been detected IP " + ip);
    return ip;
  }

  public static Boolean isHealthy(String containerName) {
    String healthStatus = CommandRunner.exec("/bin/sh", "-c", "docker inspect --format '{{.State.Health.Status}}' " + containerName).trim();
    return healthStatus.replace("'", "").equals("healthy") || considerHealthyOnError(healthStatus);
  }

  private static boolean considerHealthyOnError(String healthStatus) {
    return healthStatus.contains("Template parsing error");
  }

  public static void waitUntilIsHealthy(String containerName) throws TimeoutException {
    long startTime = System.currentTimeMillis();
    while (!isHealthy(containerName)) {
      tryWait();
      if (System.currentTimeMillis() - startTime > TIMEOUT)
        throw new TimeoutException("The container is unhealthy: " + containerName);
    }
  }

  private static void tryWait() {
    try {
      Thread.sleep(WAIT_STEP);
    } catch (InterruptedException e) {
      //just ignored
    }
  }

  public static String findIdByPartOfName(String partName) {
    log.info("Looking container with name that contains: " + partName);
    return CommandRunner.exec("docker", "ps", "-q", "-a", "--no-trunc", "--filter", "name=" + partName).substring(0, 8);
  }

  public static String findNameByPartOfText(String text) {
    return CommandRunner.exec("/bin/sh", "-c", "docker ps | grep \"" + text + "\" | awk '{print $NF}'");
  }

  public static String getIpWhenIsHealthy(String partialName) throws TimeoutException {
    String containerName = findNameByPartOfText(partialName);
    waitUntilIsHealthy(containerName);
    return inspectIp(containerName);
  }
}
