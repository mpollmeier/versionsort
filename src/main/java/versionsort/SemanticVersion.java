package versionsort;
  
import java.util.*;

public class SemanticVersion implements Comparable<SemanticVersion> {
    private final String original;
    private final List<String> coreParts;
    private final List<String> preReleaseParts;
    private final List<String> postReleaseParts;
    private final boolean isPreRelease;

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

    @Override
    public int compareTo(SemanticVersion other) {
        int maxCore = Math.max(this.coreParts.size(), other.coreParts.size());
        for (int i = 0; i < maxCore; i++) {
            int thisPart = i < this.coreParts.size() ? parsePart(this.coreParts.get(i)) : 0;
            int otherPart = i < other.coreParts.size() ? parsePart(other.coreParts.get(i)) : 0;

            int cmp = Integer.compare(thisPart, otherPart);
            if (cmp != 0) return cmp;
        }

        // Handle pre-release vs normal version
        if (this.isPreRelease && !other.isPreRelease) return -1;
        if (!this.isPreRelease && other.isPreRelease) return 1;
        if (this.isPreRelease && other.isPreRelease) {
            int cmp = compareParts(this.preReleaseParts, other.preReleaseParts);
            if (cmp != 0) return cmp;
        }

        // Now check post-release (e.g. "1.0.0a")
        return compareParts(this.postReleaseParts, other.postReleaseParts);
    }

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

    private int parsePart(String part) {
        try {
            return Integer.parseInt(part);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return original;
    }

    // Example usage
    public static void main(String[] args) {
        List<SemanticVersion> versions = Arrays.asList(
            new SemanticVersion("1.0.0"),
            new SemanticVersion("1.0.0a"),
            new SemanticVersion("1.0.0-SNAPSHOT"),
            new SemanticVersion("1.0.0-alpha"),
            new SemanticVersion("1.0.0-beta"),
            new SemanticVersion("1.0.1"),
            new SemanticVersion("2.0.0"),
            new SemanticVersion("1.0.0-rc1"),
            new SemanticVersion("1.0.0-alpha.1")
        );

        Collections.sort(versions);

        for (SemanticVersion v : versions) {
            System.out.println(v);
        }
    }
}
