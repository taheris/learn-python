package com.taheris.learnpython.helpers;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class StringMethods {
    /** replace all instances of double quotes with single quotes */
    public static String replaceDoubleQuotes(String text) {
        return text.replaceAll("\"", "'");
    }

    /** replace all instances of single quotes with double quotes */
    public static String replaceSingleQuotes(String text) {
        return text.replaceAll("'", "\"");
    }

    /** replace all tabs with double spaces */
    public static String replaceTabs(String text) {
        return replaceTabs(text, 2);
    }

    /** replace all tabs with n spaces */
    public static String replaceTabs(String text, int spaces) {
        return text.replaceAll("\t", StringUtils.repeat(' ', spaces));
    }

    /** strips single quotes and tabs */
    public static String standardFormat(String text) {
        return replaceTabs(replaceSingleQuotes(stripNewline(text)));
    }

    /** joins String[] with newlines */
    public static String join(String[] text) {
        return StringUtils.join(text, "\n");
    }

    /** keep escape characters */
    public static String keepEscape(String text) {
        return StringEscapeUtils.escapeJava(text);
    }

    /** strip all first and last characters from string if they're newlines */
    public static String stripNewline(String text) {
        return text.replaceFirst("^\n+", "").replaceFirst("\n+$", "");
    }

    /** strip all square brackets from string */
    public static String stripSquareBrackets(String text) {
        return text.replace("[", "").replace("]", "");
    }
}
