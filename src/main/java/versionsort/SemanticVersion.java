package versionsort;
  
import java.util.*;

/**
 * A class that parses and compares semantic versions, supporting both
 * standard semantic versioning (major.minor.patch) and extensions like:
 * <ul>
 *   <li>Pre-release versions (e.g. {@code 1.0.0-alpha}, {@code 1.0.0-SNAPSHOT})</li>
 *   <li>Post-release suffixes (e.g. {@code 1.0.0a})</li>
 *   <li>Mixed alphanumeric segments</li>
 * </ul>
 * <p>
 * Versions are compared based on core numeric parts first,
 * then pre-release identifiers (lower than the release),
 * and finally post-release suffixes (greater than the base release).
 */
public class SemanticVersion implements Comparable<SemanticVersion> {
    private final String original;
    private final List<String> coreParts;
    private final List<String> preReleaseParts;
    private final List<String> postReleaseParts;
    private final boolean isPreRelease;

    /**
     * Constructs a {@code SemanticVersion} by parsing the given version string.
     *
     * @param version The version string (e.g., "1.0.0", "1.0.0-alpha", "1.0.0a")
     */
    public SemanticVersion(String version) {
        this.original = version;

        String[] split = version.split("-", 2);
        String corePart = split[0];
        String extra = split.length > 1 ? split[1] : "";

        // Extract trailing non-digit as post-release if no dash
        String core = corePart.replaceAll("([a-zA-Z].*)", "");
        String post = corePart.length() > core.length() ? corePart.substring(core.length()) : "";

        this.coreParts = Arrays.asList(core.split("\\."));
        this.postReleaseParts = post.isEmpty() ? Collections.emptyList() : Arrays.asList(post.split("\\."));
        this.isPreRelease = !extra.isEmpty();
        this.preReleaseParts = isPreRelease ? Arrays.asList(extra.split("\\.")) : Collections.emptyList();
    }

    /**
     * Compares this version to another {@code SemanticVersion}.
     *
     * @param other The other version to compare against
     * @return a negative integer, zero, or a positive integer as this version
     *         is less than, equal to, or greater than the specified version
     */
    @Override
    public int compareTo(SemanticVersion other) {
        int maxCore = Math.max(this.coreParts.size(), other.coreParts.size());
        for (int i = 0; i < maxCore; i++) {
            int thisPart = i < this.coreParts.size() ? parsePart(this.coreParts.get(i)) : 0;
            int otherPart = i < other.coreParts.size() ? parsePart(other.coreParts.get(i)) : 0;

            int cmp = Integer.compare(thisPart, otherPart);
            if (cmp != 0) return cmp;
        }

        // Handle pre-release comparison
        if (this.isPreRelease && !other.isPreRelease) return -1;
        if (!this.isPreRelease && other.isPreRelease) return 1;
        if (this.isPreRelease && other.isPreRelease) {
            int cmp = compareParts(this.preReleaseParts, other.preReleaseParts);
            if (cmp != 0) return cmp;
        }

        // Handle post-release comparison
        return compareParts(this.postReleaseParts, other.postReleaseParts);
    }

    /**
     * Returns the original version string.
     *
     * @return the version as a string
     */
    @Override
    public String toString() {
        return original;
    }

    // Helper method to compare a list of alphanumeric version parts
    private int compareParts(List<String> a, List<String> b) {
        int max = Math.max(a.size(), b.size());
        for (int i = 0; i < max; i++) {
            if (i >= a.size()) return -1;
            if (i >= b.size()) return 1;

            String pa = a.get(i), pb = b.get(i);
            boolean aNum = pa.matches("\\d+"), bNum = pb.matches("\\d+");

            if (aNum && bNum) {
                int cmp = Integer.compare(Integer.parseInt(pa), Integer.parseInt(pb));
                if (cmp != 0) return cmp;
            } else if (aNum) {
                return -1;
            } else if (bNum) {
                return 1;
            } else {
                int cmp = pa.compareTo(pb);
                if (cmp != 0) return cmp;
            }
        }
        return 0;
    }

    // Helper method to parse a version component to integer
    private int parsePart(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
