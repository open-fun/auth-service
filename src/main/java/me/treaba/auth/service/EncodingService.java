package me.treaba.auth.service;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Stanislav on 08.03.17.
 */
@Component
public class EncodingService {

  public static final String UTF_8 = "UTF-8";

  public String encode(String value) {
    try {
      return URLEncoder.encode(value, UTF_8).replace(".", "%2E");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public String decode(String value) {
    //Intentional double decoding
    return tryDecode(tryDecode(value));
  }

  private String tryDecode(String value) {
    try {
      return URLDecoder.decode(value, UTF_8);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
