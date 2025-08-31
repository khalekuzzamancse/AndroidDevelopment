# Setup
- Install CMake if not installed
- Add to ENV variable to make accessible from anywhere

# Code Writing Guide
- Do not include any `.cpp` file into other file, if a `.cpp` file has dependency to other then extract the 
  dependency as header and put it in the `cpp/include` directory
O Changed the source code
  - `cd _03_with_cmake/src/main/cpp/build`
  - `cmake ..`
  - `cmake --build .`

# RunConfiguration Intellij IDEA or Android Studio
- class path(cp) : `JNI._02-manual-binding.main`
- VM option: `-Djava.library.path=_03-with-cmake/src/main/cpp/build`
- Main Class: `kzcse.jni.Main`


# Custom Convention
- cpp: contain the source code, default name for JVM to understand
- include: contain the header, name is just convention allowed to any name
- build: custom directory to save the artifacts binary such dynamic library or executable
- main.cpp : this entry point, though main function concept is not exit here
- **adapter.cpp: contain method that convert data from C++ to JNI or vice versa