// 
// Decompiled by Procyon v0.5.36
// 

package string;

import java.util.ArrayList;

public class CharConvertHelper
{
    private static boolean a(final char c) {
        for (int i = 0; i < CharConvert.h.length; ++i) {
            if (CharConvert.h[i][0] == c) {
                return true;
            }
        }
        for (int j = 0; j < CharConvert.g.length; ++j) {
            if (CharConvert.g[j] == c) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isOld(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (a(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isNew(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (!a(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static String convert(final String s) {
        final String[] sArr = split(s);
        final StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < sArr.length; ++i) {
            if (isOld(sArr[i])) {
                if (isNew(sArr[i])) {
                    sb.append(new CharConvert(sArr[i]).getInternChars());
                }
                else {
                    final String[] e = e(sArr[i]);
                    for (int j = 0; j < e.length; ++j) {
                        sb.append(new CharConvert(e[j]).getInternChars());
                    }
                }
            }
            else {
                sb.append(sArr[i]);
            }
            sb.append(" ");
        }
        return sb.toString();
    }
    
    private static String[] split(final String s) {
        if (s != null) {
            return s.split("\\s");
        }
        return new String[0];
    }
    
    private static String[] e(final String s) {
        final ArrayList<String> list = new ArrayList<String>();
        String s2 = "";
        for (int i = 0; i < s.length(); ++i) {
            if (a(s.charAt(i))) {
                if (!s2.equals("") && !isNew(s2)) {
                    list.add(s2);
                    final StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(s.charAt(i));
                    s2 = sb.toString();
                }
                else {
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append(s2);
                    sb2.append(s.charAt(i));
                    s2 = sb2.toString();
                }
            }
            else if (!s2.equals("") && isNew(s2)) {
                list.add(s2);
                final StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(s.charAt(i));
                s2 = sb3.toString();
            }
            else {
                final StringBuilder sb4 = new StringBuilder();
                sb4.append(s2);
                sb4.append(s.charAt(i));
                s2 = sb4.toString();
            }
        }
        return list.toArray(new String[list.size()]);
    }
}
