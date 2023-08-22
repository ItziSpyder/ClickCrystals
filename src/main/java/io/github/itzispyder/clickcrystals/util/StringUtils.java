package io.github.itzispyder.clickcrystals.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class StringUtils {

    static {
        System.setProperty("java.awt.headless", "false");
    }

    public static String color(String s) {
        return nullable(s).replace('&', '§');
    }

    public static String nullable(String s) {
        return s == null ? "" : s;
    }

    public static String decolor(String msg) {
        String s = msg;
        while (s.length() >= 2 && s.contains("§")) {
            int index = s.indexOf("§");
            s = s.replaceAll(s.substring(index, index + 2), "");
        }
        return s;
    }

    public static String fromClipboard() {
        try {
            Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
            return (String)board.getData(DataFlavor.stringFlavor);
        }
        catch (Exception ex) {
            return "";
        }
    }

    public static String revered(String s) {
        StringBuilder b = new StringBuilder();
        char[] a = s.toCharArray();

        for (int i = a.length - 1; i >= 0; i--) {
            b.append(a[i]);
        }

        return b.toString();
    }

    public static String capitalize(String s) {
        if (s.length() <= 1) return s.toUpperCase();
        s = s.toLowerCase();
        return String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1);
    }

    public static String capitalizeWords(String s) {
        s = s.replaceAll("[_-]"," ");
        String[] sArray = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String str : sArray) sb.append(capitalize(str)).append(" ");
        return sb.toString().trim();
    }

    public static char nextChar(String s, int index) {
        index ++;
        if (index >= s.length()) return ' ';
        return s.charAt(index);
    }

    public static List<String> wrapLines(String s, int maxLen) {
        return wrapLines(s, maxLen, false);
    }

    public static List<String> wrapLines(String s, int maxLen, boolean wordWrap) {
        final List<String> lines = new ArrayList<>();

        if (s.length() <= maxLen) {
            splitAdd(lines, s);
            return lines;
        }

        while (s.length() >= maxLen) {
            if (wordWrap) {
                int wrapAt = maxLen - 1;
                while (nextChar(s, wrapAt) != ' ') {
                    wrapAt ++;
                }
                splitAdd(lines, s.substring(0, wrapAt + 1).trim());
                s = s.substring(wrapAt + 1);
            }
            else {
                splitAdd(lines, s.substring(0, maxLen).trim());
                s = s.substring(maxLen);
            }
        }
        if (s.length() > 0) {
            lines.add(s.trim());
        }

        return lines;
    }

    public static void splitAdd(Collection<String> c, String s) {
        if (s == null || c == null) return;

        if (s.contains("<n>")) {
            c.addAll(Arrays.asList(s.split("<n>")));
        }
        else {
            c.add(s);
        }
    }

    public static String keyPressWithShift(String s) {
        if (s.length() != 1) return s;
        return Character.toString(toUpperCase(s.charAt(0)));
    }

    public static char toUpperCase(char c) {
        char upper = Character.toUpperCase(c);
        if (upper != c) {
            return upper;
        }

        switch (c) {
            case '1' -> upper = '!';
            case '2' -> upper = '@';
            case '3' -> upper = '#';
            case '4' -> upper = '$';
            case '5' -> upper = '%';
            case '6' -> upper = '^';
            case '7' -> upper = '&';
            case '8' -> upper = '*';
            case '9' -> upper = '(';
            case '0' -> upper = ')';
            case '-' -> upper = '_';
            case '=' -> upper = '+';
            case '`' -> upper = '~';
            case '[' -> upper = '{';
            case ']' -> upper = '}';
            case '\\' -> upper = '|';
            case '\'' -> upper = '"';
            case ';' -> upper = ':';
            case '/' -> upper = '?';
            case '.' -> upper = '>';
            case ',' -> upper = '<';
        }

        return upper;
    }
}
