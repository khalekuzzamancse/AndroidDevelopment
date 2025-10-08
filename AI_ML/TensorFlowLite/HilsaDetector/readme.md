
## To Run the Project

* Place a model under `app/src/main/assets`.

  * A default model is **not included** in GitHub due to size restrictions and copyright.
* The default model name is expected to be `model.tflite`. If you use a different name, update the path in the package `core.ml.ClassifierFactory`.
* To run the prebuilt code and classifier, the model is expected to follow the format below.

## Expected Model Shape

* Takes a image byte array(or equaivalent type)
* Returns a 1D float array of size = number of classes, where each index represents a class and the corresponding value represents the confidence (probability) for that class.

## Note

* If your model has a different shape, you will need to modify the code slightly to make it work.

