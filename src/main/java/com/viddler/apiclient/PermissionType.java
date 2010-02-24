package com.viddler.apiclient;

/**
 * Viddler video permission types
 * 
 * @author Maciej Dragan
 * 
 */
public enum PermissionType {

  PUBLIC, SHARED_ALL, SHARED, PRIVATE;

  /**
   * Get text value passed in API calls
   * 
   * @return
   */
  public String getValue() {
    return name().toLowerCase();
  }

  /**
   * Get enum instance for given api text value
   * 
   * @param value
   * @return
   */
  public static PermissionType forValue(String value) {
    for (PermissionType type : PermissionType.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    return null;
  }

  /**
   * Check if text value is valid permission type
   * 
   * @param value
   * @return
   */
  public static boolean isValueValid(String value) {
    return forValue(value) != null;
  }

}
