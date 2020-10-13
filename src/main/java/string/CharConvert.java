// 
// Decompiled by Procyon v0.5.36
// 

package string;

public class CharConvert
{
    public static char a = '\u0622';
    public static char b = '\u0623';
    public static char c = '\u0625';
    public static char d = '\u0627';
    public static char e = '\u0644';
    public static char[][] f;
    public static char[] g;
    public static char[][] h;
    private String internChars;
    
    static {
        CharConvert.f = new char[][] { { '\u3ba6', (char)(-266), (char)(-267) }, { '\u3ba7', (char)(-264), (char)(-265) }, { '\u0627', (char)(-260), (char)(-261) }, { '\u0625', (char)(-262), (char)(-263) } };
        CharConvert.g = new char[] { '\u0600', '\u0601', '\u0602', '\u0603', '\u0606', '\u0607', '\u0608', '\u0609', '\u060a', '\u060b', '\u060d', '\u060e', '\u0610', '\u0611', '\u0612', '\u0613', '\u0614', '\u0615', '\u0616', '\u0617', '\u0618', '\u0619', '\u061a', '\u061b', '\u061e', '\u061f', '\u0621', '\u063b', '\u063c', '\u063d', '\u063e', '\u063f', '\u0640', '\u064b', '\u064c', '\u064d', '\u064e', '\u064f', '\u0650', '\u0651', '\u0652', '\u0653', '\u0654', '\u0655', '\u0656', '\u0657', '\u0658', '\u0659', '\u065a', '\u065b', '\u065c', '\u065d', '\u065e', '\u0660', '\u066a', '\u066b', '\u066c', '\u066f', '\u0670', '\u0672', '\u06d4', '\u06d5', '\u06d6', '\u06d7', '\u06d8', '\u06d9', '\u06da', '\u06db', '\u06dc', '\u06df', '\u06e0', '\u06e1', '\u06e2', '\u06e3', '\u06e4', '\u06e5', '\u06e6', '\u06e7', '\u06e8', '\u06e9', '\u06ea', '\u06eb', '\u06ec', '\u06ed', '\u06ee', '\u06ef', '\u06d6', '\u06d7', '\u06d8', '\u06d9', '\u06da', '\u06db', '\u06dc', '\u06dd', '\u06de', '\u06df', '\u06f0', '\u06fd', (char)(-400), (char)(-399), (char)(-398), (char)(-397), (char)(-396), (char)(-395), (char)(-394), (char)(-393), (char)(-392), (char)(-391), (char)(-390), (char)(-389), (char)(-388), (char)(-387), (char)(-386), (char)(-385), (char)(-930), (char)(-929), (char)(-928), (char)(-927), (char)(-926), (char)(-925) };
        CharConvert.h = new char[][] { { '\u0622', (char)(-383), (char)(-383), (char)(-382), (char)(-382), '\u0002' }, { '\u0623', (char)(-382), (char)(-381), (char)(-380), (char)(-380), '\u0002' }, { '\u0624', (char)(-379), (char)(-379), (char)(-378), (char)(-378), '\u0002' }, { '\u0625', (char)(-377), (char)(-377), (char)(-376), (char)(-376), '\u0002' }, { '\u0626', (char)(-375), (char)(-373), (char)(-372), (char)(-374), '\u0004' }, { '\u0627', '\u0627', '\u0627', (char)(-370), (char)(-370), '\u0002' }, { '\u0628', (char)(-369), (char)(-367), (char)(-366), (char)(-368), '\u0004' }, { '\u0629', (char)(-365), (char)(-365), (char)(-364), (char)(-364), '\u0002' }, { '\u062a', (char)(-363), (char)(-361), (char)(-360), (char)(-362), '\u0004' }, { '\u062b', (char)(-359), (char)(-357), (char)(-356), (char)(-358), '\u0004' }, { '\u062c', (char)(-355), (char)(-353), (char)(-352), (char)(-354), '\u0004' }, { '\u062d', (char)(-351), (char)(-349), (char)(-348), (char)(-350), '\u0004' }, { '\u062e', (char)(-347), (char)(-345), (char)(-344), (char)(-346), '\u0004' }, { '\u062f', (char)(-343), (char)(-343), (char)(-342), (char)(-342), '\u0002' }, { '\u0630', (char)(-341), (char)(-341), (char)(-340), (char)(-340), '\u0002' }, { '\u0631', (char)(-339), (char)(-339), (char)(-338), (char)(-338), '\u0002' }, { '\u0632', (char)(-337), (char)(-337), (char)(-336), (char)(-336), '\u0002' }, { '\u0633', (char)(-335), (char)(-333), (char)(-332), (char)(-334), '\u0004' }, { '\u0634', (char)(-331), (char)(-329), (char)(-328), (char)(-330), '\u0004' }, { '\u0635', (char)(-327), (char)(-325), (char)(-324), (char)(-326), '\u0004' }, { '\u0636', (char)(-323), (char)(-321), (char)(-320), (char)(-322), '\u0004' }, { '\u0637', (char)(-319), (char)(-317), (char)(-318), (char)(-316), '\u0004' }, { '\u0638', (char)(-315), (char)(-313), (char)(-314), (char)(-314), '\u0004' }, { '\u0639', (char)(-311), (char)(-309), (char)(-308), (char)(-310), '\u0004' }, { '\u063a', (char)(-307), (char)(-305), (char)(-304), (char)(-306), '\u0004' }, { '\u0641', (char)(-303), (char)(-301), (char)(-300), (char)(-302), '\u0004' }, { '\u0642', (char)(-299), (char)(-297), (char)(-296), (char)(-298), '\u0004' }, { '\u0643', (char)(-295), (char)(-293), (char)(-292), (char)(-294), '\u0004' }, { '\u0644', (char)(-291), (char)(-289), (char)(-288), (char)(-290), '\u0004' }, { '\u0645', (char)(-287), (char)(-285), (char)(-284), (char)(-286), '\u0004' }, { '\u0646', (char)(-283), (char)(-281), (char)(-280), (char)(-282), '\u0004' }, { '\u0647', (char)(-279), (char)(-277), (char)(-276), (char)(-278), '\u0004' }, { '\u0648', (char)(-275), (char)(-275), (char)(-274), (char)(-274), '\u0002' }, { '\u0649', (char)(-273), (char)(-273), (char)(-272), (char)(-272), '\u0002' }, { '\u0671', '\u0671', '\u0671', (char)(-1199), (char)(-1199), '\u0002' }, { '\u064a', (char)(-271), (char)(-269), (char)(-268), (char)(-270), '\u0004' }, { '\u066e', (char)(-1052), (char)(-1048), (char)(-1047), (char)(-1051), '\u0004' }, { '\u0671', '\u0671', '\u0671', (char)(-1199), (char)(-1199), '\u0002' }, { '\u06aa', (char)(-1138), (char)(-1136), (char)(-1135), (char)(-1137), '\u0004' }, { '\u06c1', (char)(-1114), (char)(-1112), (char)(-1111), (char)(-1113), '\u0004' }, { '\u06e4', '\u06e4', '\u06e4', '\u06e4', (char)(-274), '\u0002' } };
    }
    
    public CharConvert(final String s) {
        this.internChars = "";
        final InternChar internChar = new InternChar(this.convertIntern2(s));
        if (internChar.c.length > 0) {
            this.internChars = this.convertIntern(new String(internChar.c));
        }
        this.internChars = internChar.convertIntern(this.internChars);
    }
    
    private char convertIntern(final char c, final char c2, final boolean b) {
        int n;
        if (b) {
            n = 2;
        }
        else {
            n = 1;
        }
        final char e = CharConvert.e;
        char c3 = '\0';
        final char c4 = '\0';
        if (e == c2) {
            c3 = c4;
            if (c == CharConvert.a) {
                c3 = CharConvert.f[0][n];
            }
            if (c == CharConvert.b) {
                c3 = CharConvert.f[1][n];
            }
            if (c == CharConvert.c) {
                c3 = CharConvert.f[3][n];
            }
            if (c == CharConvert.d) {
                c3 = CharConvert.f[2][n];
            }
        }
        return c3;
    }
    
    private char convertIntern(final char c, final int n) {
        for (int i = 0; i < CharConvert.h.length; ++i) {
            if (CharConvert.h[i][0] == c) {
                return CharConvert.h[i][n];
            }
        }
        return c;
    }

    private int containHeaderOfHMiddle(final char c) {
        for (int i = 0; i < CharConvert.h.length; ++i) {
            char[] candy = CharConvert.h[i];
            for (int j = 1; j < candy.length; j++) {
                if (CharConvert.h[i][j] == c) {
                    // 是否包含, 返回行数
                    return i;
                }
            }
        }
        return -1;
    }

    private int containHeaderOfHTable(final char c) {
        for (int i = 0; i < CharConvert.h.length; ++i) {
            if (CharConvert.h[i][0] == c) {
                // 是否包含
                return CharConvert.h[i][5];
            }
        }
        return 2;
    }
    
    private String convertIntern2(final String s) {
        final int length = s.length();
        final char[] array = new char[length];
        s.getChars(0, length, array, 0);
        int i = 0;
        char c = '\0';
        while (i < array.length - 1) {
            char c2 = c;
            if (!this.existG(array[i])) {
                c2 = c;
                if (CharConvert.e != array[i]) {
                    c2 = array[i];
                }
            }
            if (CharConvert.e == array[i]) {
                final char c3 = array[i];
                int n;
                for (n = i + 1; n < array.length && this.existG(array[n]); ++n) {}
                if (n < array.length) {
                    char c4;
                    if (i > 0 && this.containHeaderOfHTable(c2) > 2) {
                        c4 = this.convertIntern(array[n], c3, false);
                    }
                    else {
                        c4 = this.convertIntern(array[n], c3, true);
                    }
                    if (c4 != '\0') {
                        array[i] = c4;
                        array[n] = ' ';
                    }
                }
            }
            ++i;
            c = c2;
        }
        return new String(array).replaceAll(" ", "").trim();
    }
    
    private boolean existG(final char c) {
        for (int i = 0; i < CharConvert.g.length; ++i) {
            if (CharConvert.g[i] == c) {
                return true;
            }
        }
        return false;
    }
    
    public String getInternChars() {
        return this.internChars;
    }
    
    public String convertIntern(final String s) {
        final StringBuffer sb = new StringBuffer("");
        final int length = s.length();
        final char[] dst = new char[length];
        s.getChars(0, length, dst, 0);
        sb.append(this.convertIntern(dst[0], 2));
        int n = 1;
        int n2;
        while (true) {
            n2 = length - 1;
            if (n >= n2) {
                break;
            }
            int pivlot = 2;
            if (this.containHeaderOfHTable(dst[n - 1]) == pivlot) {
                sb.append(this.convertIntern(dst[n], pivlot));
            }
//            if ((pivlot = this.containHeaderOfHMiddle(dst[n - 1])) != -1) {
//                sb.append(CharConvert.h[pivlot][n]);
//            }
            else {
                sb.append(this.convertIntern(dst[n], 3));
            }
            ++n;
        }
        if (length >= 2) {
            if (this.containHeaderOfHTable(dst[length - 2]) == 2) {
                sb.append(this.convertIntern(dst[n2], 1));
            }
            else {
                sb.append(this.convertIntern(dst[n2], 4));
            }
        }
        return sb.toString();
    }
    
    class InternChar
    {
        char[] a;
        int[] b;
        char[] c;
        int[] d;
        
        InternChar(final String s) {
            final int length = s.length();
            final int n = 0;
            int i = 0;
            int n2 = 0;
            while (i < length) {
                int n3 = n2;
                if (CharConvert.this.existG(s.charAt(i))) {
                    n3 = n2 + 1;
                }
                ++i;
                n2 = n3;
            }
            this.b = new int[n2];
            this.a = new char[n2];
            final int n4 = length - n2;
            this.d = new int[n4];
            this.c = new char[n4];
            int n5 = 0;
            int n6 = 0;
            for (int j = n; j < s.length(); ++j) {
                if (CharConvert.this.existG(s.charAt(j))) {
                    this.b[n5] = j;
                    this.a[n5] = s.charAt(j);
                    ++n5;
                }
                else {
                    this.d[n6] = j;
                    this.c[n6] = s.charAt(j);
                    ++n6;
                }
            }
        }
        
        String convertIntern(final String s) {
            final char[] value = new char[s.length() + this.a.length];
            final int n = 0;
            int index = 0;
            int i;
            while (true) {
                i = n;
                if (index >= this.d.length) {
                    break;
                }
                value[this.d[index]] = s.charAt(index);
                ++index;
            }
            while (i < this.b.length) {
                value[this.b[i]] = this.a[i];
                ++i;
            }
            return new String(value);
        }
    }
}
