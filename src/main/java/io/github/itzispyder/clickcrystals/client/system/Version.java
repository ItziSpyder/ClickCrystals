package io.github.itzispyder.clickcrystals.client.system;

public class Version {

    private final String versionString;
    private final int[] digits;

    public Version(String versionString) {
        this.versionString = versionString;

        String[] i = versionString.replaceAll("[^0-9]", "").split("\\.");
        this.digits = new int[i.length];
        for (int j = 0; j < i.length; j++) {
            digits[j] = Integer.parseInt(i[j]);
        }
    }

    public static Version ofString(String s) {
        return new Version(s);
    }

    public static Version ofInt(int... i) {
        StringBuilder b = new StringBuilder();
        for (int x : i) {
            b.append(x).append(".");
        }
        return ofString(b.toString());
    }

    public static Version ofAnother(Version v) {
        return new Version(v.getVersionString());
    }

    public String getVersionString() {
        return versionString;
    }

    public int[] getDigits() {
        return digits;
    }

    public boolean isNewerThan(Version another) {
        int[] cur = getDigits();
        int[] oth = another.getDigits();

        for (int i = 0; i < cur.length; i++) {
            if (cur[i] > oth[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean isSameAs(Version another) {
        int[] cur = getDigits();
        int[] oth = another.getDigits();
        boolean bl = false;

        for (int i = 0; i < cur.length; i++) {
            if (cur[i] == oth[i]) {
                bl = true;
                break;
            }
        }

        return bl;
    }

    public boolean isOlderThan(Version another) {
        return !isNewerThan(another) && !isSameAs(another);
    }

    public boolean isUpToDate(Version latest) {
        return isSameAs(latest) || isNewerThan(latest);
    }

    @Override
    public String toString() {
        return versionString;
    }
}
