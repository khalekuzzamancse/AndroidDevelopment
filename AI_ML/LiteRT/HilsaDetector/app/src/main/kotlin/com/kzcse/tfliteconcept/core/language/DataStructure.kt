package com.kzcse.tfliteconcept.core.language

typealias VoidCallback=()-> Unit

/** dp=px/density */
fun Float.toDp(density: Float): Float{
    val px=this
    return px/density
}
/** dp=px*density */
fun Float.toPx(density: Float): Float{
    val dp=this
    return dp*density
}
