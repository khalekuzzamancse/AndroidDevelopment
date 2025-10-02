package com.uitest.feature._core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** Abstract factory*/
interface ColorFactory {
    val primary: Color
    val bottomBarItemSelection: Color
    val testPrimary: Color
    val primaryLight: Color
    val pageColor: Color
    val buttonBorderColor: Color
    val colorPrimaryTwo:Color
    val grey: Color
    val black: Color
    val nero: Color
    val white: Color
    val text: Color
    val searchBg: Color
    val greyWhite: Color
    val gray: Color
    val oxfordBlue: Color
    val gainsboro: Color
    val nightRider: Color
    val background: Color
    val greyBunker: Color
    val greyChateau: Color
    val border: Color
    val disable: Color
    val red: Color
    val amber:Color
    val green:Color

    companion object {
        val colors: ColorFactory = LightColorFactory()
    }
}

class LightColorFactory : ColorFactory {
    override val primary = Color(0xFF1F5DA0)
    override val bottomBarItemSelection=Color(0xFF084FCF)
    override val testPrimary = Color(0xFF0C1C2C)
    override val primaryLight = Color(0xFFDBEDFF)
    override val white = Color(0xFFFFFFFF)
    override val pageColor = white
    override val buttonBorderColor = Color(0xFFDCDCDC)
    override val colorPrimaryTwo=Color(red = 88, green = 168, blue = 248, alpha = 255)
    override val grey = Color(0xFFA0A4A8)
    override val black = Color(0xFF000000)
    override val nero = Color(0xFF1F1F1F)
    override val text = Color(0xFF52575C)
    override val searchBg = Color(0xFFF4F7FC)
    override val greyWhite = Color(0xFFCACCCF)
    override val gray = Color(0xFF6E6E6E)
    override val oxfordBlue = Color(0xFF282F39)
    override val gainsboro = Color(0x6EE8E8E8)
    override val nightRider = Color(0xFF303030)
    override val background = Color(0xFFF4F7FC)
    override val greyBunker = Color(0xFF25282B)
    override val greyChateau = Color(0xFFA0A4A8)
    override val border = Color(0xFFDCDCDC)
    override val disable = Color(0xFF979797)
    override val red = Color(0xFFFF0000)
    override val amber=Color(0xFFFFC107)
    override val green= Color(0xFF43A047)

}
/** Abstract factory*/
interface TextStyleFactory {
    val labelSmall: TextStyle
    val labelMedium:TextStyle
    val titleMedium:TextStyle
     val labelCaption: TextStyle
    val valueCaption: TextStyle

    companion object {
        val styles: TextStyleFactory = LightTextStyleFactory()
    }
}

class LightTextStyleFactory : TextStyleFactory {
    override val labelSmall: TextStyle = TextStyle(
        fontSize = 13.sp,
        color = ColorFactory.colors.gray,
        fontWeight = FontWeight.W400
    )
    override val labelMedium: TextStyle = TextStyle(
        fontSize = 15.sp,
        color = ColorFactory.colors.gray,
        fontWeight =  FontWeight.W500
    )
    override val titleMedium: TextStyle = TextStyle(
        fontSize = 20.sp,
        color = ColorFactory.colors.black,
        fontWeight =  FontWeight.W400
    )
    override val labelCaption: TextStyle = TextStyle(
        fontSize = 15.sp,
        color = ColorFactory.colors.disable,
        fontWeight =  FontWeight.W400
    )
    override val valueCaption: TextStyle = TextStyle(
        fontSize = 15.sp,
        color = ColorFactory.colors.black,
        fontWeight =  FontWeight.W400
    )

}