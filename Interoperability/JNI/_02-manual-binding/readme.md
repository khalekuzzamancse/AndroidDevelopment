# Custom Convention
- cpp: contain the source code, default name for JVM to understand
- include: contain the header, name is just convention allowed to any name
- build: custom directory to save the artifacts binary such dynamic library or executable
- main.cpp : this entry point, though main function concept is not exit here
- **adapter.cpp: contain method that convert data from C++ to JNI or vice versa
### Run:
- Add VM options : `-Djava.library.path=_02-manual-binding/src/main/cpp/build`

- Build Project
- Generate the header for single file
  - `javac -h src/main/cpp src/main/java/kzcse/jni/NativeCalculator.java`
- Generate corresponding headers for multiple file as at a time
  - `javac -h src/main/cpp \
    src/main/java/kzcse/jni/NativeCalculator.java \
    src/main/java/kzcse/jni/NativeMath.java \
    src/main/java/kzcse/jni/NativeUtils.java`
- Generate corresponding headers for all native method belongs under a package
- javac -h ManualBinding/src/main/cpp ManualBinding/src/main/java/kzcse/jni/*.java

### Misc

- Every time changes the C++ source code recompile and regenerate the dynamic library
    - On macOS use the following command or use other tools:
        - `g++ -dynamiclib -o src/main/cpp/build/libnativecode.dylib \
    -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" \
    src/main/cpp/main.cpp`
  
# RunConfiguration Intellij IDEA or Android Studio
- class path(cp) : `JNI._02-manual-binding.main`
- VM option: `-Djava.library.path=_02-manual-binding/src/main/cpp/build`
- Main Class: `kzcse.jni.Main`