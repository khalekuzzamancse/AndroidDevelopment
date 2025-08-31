package kzcse.jni;

import kzcse.jni.register.RegisterNativeBasic;

public class Main {
    public static void main(String[] args) {
        RegisterNativeBasic.hello();
        RegisterNativeBasic.welcome();
        Calculator calc = new Calculator();
        double a = 10.0;
        double b = 5.0;
        System.out.println(a + " + " + b + " = " + calc.add(a, b));
        double difference = calc.sub(a, b);
        double product = calc.mult(a, b);
        double quotient = calc.div(a, b);

        System.out.println(a + " - " + b + " = " + difference);
        System.out.println(a + " * " + b + " = " + product);
        System.out.println(a + " / " + b + " = " + quotient);
    }
}