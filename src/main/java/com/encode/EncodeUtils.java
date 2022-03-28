package com.encode;

import java.util.Base64;

public final class EncodeUtils {

  private EncodeUtils() {}

  public static String getBase64EncodedString(String userName, String password) {
    return Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
  }
}
