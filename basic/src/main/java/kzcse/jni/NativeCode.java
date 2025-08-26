package kzcse.jni;

public class NativeCode {
    static {
        System.loadLibrary("nativecode"); // omit "lib" prefix and ".dylib"
    }
    static native void hello();
    public static void main(String[] args) {
        hello();
    }
}
