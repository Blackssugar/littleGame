package stickman.config;

import org.json.simple.JSONObject;

/** Utility class for reading JSON Configurations. */
public class ConfigurationUtils {

  // validity checks

  /**
   * Determines whether a JSONObject contains a certain JSONObject.
   *
   * @param object the parent object.
   * @param key the child object's identifier.
   * @return the child if it exists, else null.
   */
  public static boolean containsValidJSONObject(JSONObject object, String key) {
    return object.get(key) instanceof JSONObject;
  }

  // complex type parsing

  /**
   * Converts an Object to JSONObject.
   *
   * @param object the Object to convert.
   * @return the JSONObject casting if the Object is an instance of JSONObject, else null.
   */
  public static JSONObject toJSONObject(Object object) {

    if (!(object instanceof JSONObject)) {
      return null;
    }

    return (JSONObject) object;
  }

  /**
   * Parses a Double from a JSONObject.
   *
   * @param object the parent object.
   * @param key the child Double's identifier.
   * @return the child as Double if valid, else null.
   */
  public static Double parseDoubleFromObject(JSONObject object, String key) {

    if (object == null || !object.containsKey(key)) {
      return null;
    }

    Object value = object.get(key);
    if (!isNumerical(value)) {
      return null;
    }

    return toDouble(value);
  }

  /**
   * Parses an Integer from a JSONObject.
   *
   * @param object the parent object.
   * @param key the child Integer's identifier.
   * @return the child as Integer if valid, else null.
   */
  public static Integer parseIntegerFromObject(JSONObject object, String key) {

    if (object == null || !object.containsKey(key)) {
      return null;
    }

    Object value = object.get(key);
    if (!isNumerical(value)) {
      return null;
    }

    return toInt(value);
  }

  /**
   * Parses an String from a JSONObject.
   *
   * @param object the parent object.
   * @param key the child String's identifier.
   * @return the child as String if valid, else null.
   */
  public static String parseStringFromObject(JSONObject object, String key) {

    if (object == null || !object.containsKey(key)) {
      return null;
    }

    Object value = object.get(key);
    if (!isString(value)) {
      return null;
    }

    return toString(value);
  }

  // type checking

  /**
   * Determines whether an Object is numerical.
   *
   * @param object the object to check.
   * @return true if the Object is numerical.
   */
  private static boolean isNumerical(Object object) {
    return object instanceof Number;
  }

  /**
   * Determines whether an Object is a String.
   *
   * @param object the object to check.
   * @return true if the Object is a String.
   */
  private static boolean isString(Object object) {
    return object instanceof String;
  }

  // simple type parsing

  /**
   * Converts an Object to double.
   *
   * @param object the Object to convert.
   * @return the double representation.
   */
  private static double toDouble(Object object) {
    return ((Number) object).doubleValue();
  }

  /**
   * Converts an Object to int.
   *
   * @param object the Object to convert.
   * @return the int representation.
   */
  private static int toInt(Object object) {
    return ((Number) object).intValue();
  }

  /**
   * Converts an Object to String.
   *
   * @param object the Object to convert.
   * @return the String representation.
   */
  private static String toString(Object object) {
    return String.valueOf(object);
  }
}
