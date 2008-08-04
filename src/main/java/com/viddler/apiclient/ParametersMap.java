package com.viddler.apiclient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Helper class for creating parameters array for HTTP requests. Basically it ignores null parameter
 * values and transforms custom type object into its string representation
 * 
 * @author Maciej Dragan
 * 
 * @param <K> Always String HTTP parameter name
 * @param <V> Custom serializable type HTTP parameter value
 */
public class ParametersMap<K, V> extends HashMap<K, V> {

  private static final long serialVersionUID = 3106933200073335350L;

  @Override
  public V put(K key, V value) {
    if (value != null && key != null) {
      return super.put(key, value);
    }
    return value;
  }

  /**
   * Serialize object to string
   * 
   * @param value Object
   * @return String value
   */
  private String toString(V value) {
    if (value == null) {
      return null;
    }
    if (value instanceof String) {
      return (String) value;
    }
    if (value instanceof Boolean) {
      return ((Boolean) value).booleanValue() ? "1" : "0";
    }
    if (value instanceof Date) {
      return new SimpleDateFormat("yyyy-MM-dd").format((Date) value);
    }
    if (value instanceof PermissionType) {
      return ((PermissionType) value).getValue();
    }
    if (value instanceof String[]) {
      return join((String[]) value);
    }
    return value.toString();
  }

  /**
   * Join array of Strings using coma separator
   * 
   * @param tab
   * @return
   */
  private String join(String[] tab) {
    return join(tab, ",");
  }

  /**
   * Join array of Strings using custom separator
   * 
   * @param tab
   * @param separator
   * @return
   */
  private String join(String[] tab, String separator) {
    StringBuffer buff = new StringBuffer();
    if (tab != null) {
      for (String elem : tab) {
        if (buff.length() > 0) {
          buff.append(separator);
        }
        buff.append(elem);
      }
    }
    return buff.toString();
  }

  /**
   * Serialize to String array
   * 
   * @return
   */
  public String[][] serialize() {
    String[][] arr = new String[size()][];
    Iterator<K> keys = keySet().iterator();
    int idx = 0;
    while (keys.hasNext()) {
      K key = keys.next();
      arr[idx++] = new String[] { key.toString(), toString(get(key)) };
    }
    return arr;
  }

}
