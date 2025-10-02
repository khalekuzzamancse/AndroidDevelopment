package kzcse.jni.register;

public class NativeUtils {
    private static boolean isLoaded=false;
    public static void loadAndBind(){
        if(!isLoaded){
            System.loadLibrary("nativecode"); // omit "lib" prefix and ".dylib"
            isLoaded=true;
            registerNative();
        }
    }
    private static native void registerNative();

}
