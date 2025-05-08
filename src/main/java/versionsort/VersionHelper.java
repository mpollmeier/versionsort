package versionsort;

public class VersionHelper {
  
    /**
      * Compares two version strings. 
      * 
      * Use this instead of String.compareTo() for a non-lexicographical 
      * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
      * 
      * Notes:
      * - handles missing patch/minor (defaults to 0)
      * - orders SNAPSHOT and pre-releases correctly
      * - supports alphanumeric comparison for non-numeric suffixes
      * - follows semver precedence rules with extra flexibility
      * - "1.10" does not equal "1.10.0"
      * 
      * @return The result is a negative integer if version1 lt version 2
      *         The result is a positive integer if version1 gt version 2
      *         The result is zero if the strings are numerically equal.
      **/
    public static int compare(String version1, String version2) {
      return new SemanticVersion(version1).compareTo(new SemanticVersion(version2));
    }

}
