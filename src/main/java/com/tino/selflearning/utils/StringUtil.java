package com.tino.selflearning.utils;

import java.util.Map;
import org.apache.commons.text.StringSubstitutor;

public class StringUtil {

  private StringUtil() {}

  public static String replacePlaceholder(String template, Map<String, Object> values) {
    return StringSubstitutor.replace(template, values, "{", "}");

  }
}
