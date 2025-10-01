package kzcse.ndkfundamental;

public class Hello {
    static {
        NativeLibraryLoader.load();
    }
    static native String hello(String name);
}
