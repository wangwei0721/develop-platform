package com.wcc.platform.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static boolean isNotBlank(String str) {
        if (str == null) {
            return false;
        }
        return str.trim().length() > 0;
    }

    public static List<String> splitIgnoreQuota(String str, char delimiter) {
        List<String> tokensList = new ArrayList<>();
        boolean inQuotes = false;
        boolean inSingleQuotes = false;
        int bracketLeftNum = 0;
        StringBuilder b = new StringBuilder();
        char[] chars = str.toCharArray();
        int idx = 0;
        for (char c : chars) {
            char flag = 0;
            if (idx > 0) {
                flag = chars[idx - 1];
            }
            if (c == delimiter) {
                if (inQuotes) {
                    b.append(c);
                } else if (inSingleQuotes) {
                    b.append(c);
                } else if (bracketLeftNum > 0) {
                    b.append(c);
                } else {
                    tokensList.add(b.toString());
                    b = new StringBuilder();
                }
            } else if (c == '\"' && '\\' != flag && !inSingleQuotes) {
                inQuotes = !inQuotes;
                b.append(c);
            } else if (c == '\'' && '\\' != flag && !inQuotes) {
                inSingleQuotes = !inSingleQuotes;
                b.append(c);
            } else if (c == '(' && !inSingleQuotes && !inQuotes) {
                bracketLeftNum++;
                b.append(c);
            } else if (c == ')' && !inSingleQuotes && !inQuotes) {
                bracketLeftNum--;
                b.append(c);
            } else {
                b.append(c);
            }
            idx++;
        }

        tokensList.add(b.toString());

        return tokensList;
    }

    public static String replaceIgnoreQuota(String str, String oriStr, String replaceStr) {
        String splitPatternStr = oriStr + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)(?=(?:[^']*'[^']*')*[^']*$)";
        return str.replaceAll(splitPatternStr, replaceStr);
    }

}
