### Run:

- Add VM options : `-Djava.library.path=src/main/cpp`

- Build Project
- Generate the header for single file
  - `javac -h src/main/cpp src/main/java/kzcse/jni/NativeCalculator.java`
- Generate corresponding headers for multiple file as at a time
  - `javac -h src/main/cpp \
    src/main/java/kzcse/jni/NativeCalculator.java \
    src/main/java/kzcse/jni/NativeMath.java \
    src/main/java/kzcse/jni/NativeUtils.java`
- Generate corresponding headers for all native method belongs under a package 
  - `javac -h src/main/cpp src/main/java/kzcse/jni/*.java`
- Generate corresponding headers for all native method belongs under whole module(`java` directory)
  - `javac -h src/main/cpp $(find src/main/java -name "*.java")`
### Misc

- Every time changes the C++ source code recompile and regenerate the dynamic library
    - On macOS use the following command or use other tools:
        - `g++ -dynamiclib -o src/main/cpp/libnativecode.dylib \
    -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" \
    src/main/cpp/NativeCode.cpp`
  
