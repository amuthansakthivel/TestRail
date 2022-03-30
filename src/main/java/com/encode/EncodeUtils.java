package com.encode;

import java.util.Base64;

/**
 * @author amuthansakthivel
 * @version 1.0
 */
public final class EncodeUtils {

  private EncodeUtils() {}

  /**
   * @param userName
   * @param password
   * @return Base64 encode string
   */
  public static String getBase64EncodedString(String userName, String password) {
    return Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
  }
}
