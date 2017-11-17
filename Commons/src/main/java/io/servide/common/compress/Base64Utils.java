package io.servide.common.compress;

import java.util.Base64;

public enum Base64Utils {

  ;

  public static String bytesToBase64(byte[] input) {
    return Base64.getEncoder().encodeToString(CompressionUtils.compress(input));
  }

  public static byte[] base64Tobytes(String base64) {
    return CompressionUtils.decompress(Base64.getDecoder().decode(base64));
  }

}
