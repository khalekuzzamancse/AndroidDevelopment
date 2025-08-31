package kzcse.jni;
import kzcse.jni.register.NativeUtils;

public class Calculator {
    static {
        NativeUtils.loadAndBind();
    }
    public native double add(double a, double b);
    public native double sub(double a, double b);
    public native double mult(double a, double b);
    public native double div(double a, double b);
}
