### Run:

- Add VM options : `-Djava.library.path=src/main/cpp`

### Misc

- Every time changes the C++ source code recompile and regenerate the dynamic library
    - On macOS use the following command or use other tools:
        - `g++ -dynamiclib -o src/main/cpp/libnativecode.dylib \
    -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" \
    src/main/cpp/NativeCode.cpp`
  
