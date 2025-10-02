package kzcse.jni;

public class NativeCalculator {
    static {
        System.loadLibrary("nativecode"); // omit "lib" prefix and ".dylib"
    }
    public native double add(double a, double b);
}
