package kzcse.jni.register;

public class RegisterNativeBasic {
    static {
        NativeUtils.loadAndBind();
    }
    public static native void hello();
    public static native void welcome();
}
