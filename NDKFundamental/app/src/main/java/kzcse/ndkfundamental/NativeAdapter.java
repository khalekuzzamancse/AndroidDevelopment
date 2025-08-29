package kzcse.ndkfundamental;

public class NativeAdapter {
    static {
        NativeLibraryLoader.load();
    }
    public static native  String greetings();
}
