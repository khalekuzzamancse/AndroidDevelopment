package kzcse.ndkfundamental;

public class NativeLibraryLoader {
    private static  boolean isLoaded=false;
    static void load(){
        if(!isLoaded){
            System.loadLibrary("native-library");
            isLoaded=true;
        }

    }
}
