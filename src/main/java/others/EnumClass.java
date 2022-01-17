package others;

public class EnumClass {
//    EnumClass(String st, int t, boolean type) {
//
//    }
    public static enum ImageType {
        GIF(true),
        JPEG(false),
        RAW(false),
        PNG_A(true),
        PNG(false),
        WEBP_A(true),
        WEBP(false),
        UNKNOWN(false);

        private final boolean hasAlpha;

        private ImageType(boolean hasAlpha) {
            this.hasAlpha = hasAlpha;
        }

        public boolean hasAlpha() {
            return this.hasAlpha;
        }
    }
}
