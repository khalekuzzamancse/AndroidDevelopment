package com.kzcse.tfliteconcept.feature.image_picker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kzcse.tfliteconcept.R
import com.kzcse.tfliteconcept.feature._core.presentation.ScreenStrategy
import com.uitest.feature._core.ui.TextHeading3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    bottomBar: @Composable () -> Unit,
    navRail:@Composable ()-> Unit,
    navigationIcon: @Composable () -> Unit,
    onProcessRequest: (Bitmap) -> Unit,
) {

    ScreenStrategy(
        bottomBar = bottomBar,
        navRail=navRail
    ) {
        GalleryScreen(
            modifier = Modifier,
            onImageClick = { bitmap, fromDataSet ->
                onProcessRequest(bitmap)
            },
            navigationIcon =navigationIcon,
        )
    }

}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
    onImageClick: (Bitmap, fromDataSet: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        val bitmap = uri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
        if (bitmap != null) {
            onImageClick(bitmap, false)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            onImageClick(bitmap, false)
        }
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gallery",
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = navigationIcon
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BottomSheetItem(
                    label = "From Gallery",
                    icon = Icons.Filled.Photo,
                    onClick = {
                        galleryLauncher.launch("image/*")
                    }
                )
                BottomSheetItem(
                    label = "From Camera",
                    icon = Icons.Filled.CameraAlt,
                    onClick = {
                        cameraLauncher.launch(null)
                    }
                )
            }
            Spacer(Modifier.height(4.dp))
            HorizontalDivider()
            Spacer(Modifier.height(8.dp))
            TextHeading3(
                text = "Pick from data set",
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
          //  Instead of padding use the content padding instead
            ImageGallery(
                images = SavedImageProvider.getImages(),
                onImageClick = onImageClick
            )
        }
    }

}

object SavedImageProvider {
    fun getImages(): List<Int> {
        return listOf(
            R.drawable.chondona_01,
            R.drawable.chonodna_02,
            R.drawable.gurta_01,
            R.drawable.gurta_02,
            R.drawable.healthy_02,
            R.drawable.healthy_02,
            R.drawable.jhatka_01,
            R.drawable.jhatka_02,
            R.drawable.other_01,
            R.drawable.other_02
        )
    }
}

@Composable
fun ImageGallery(
    images: List<Int>,
    onImageClick: (Bitmap, fromDataSet: Boolean) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {

        items(images) { imageRes ->
            ImageItem(imageRes = imageRes) {
                onImageClick(it, true)
            }
        }
    }
}

@Composable
fun ImageItem(imageRes: Int, onImageClick: (Bitmap) -> Unit) {
    val bitmap = BitmapFactory.decodeResource(
        LocalContext.current.resources, imageRes
    )

    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(12.dp)) //We are sure regardless of theme the image back are always white
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            .clickable { onImageClick(bitmap) },
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Gallery Image",
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        )
    }
}

@Composable
fun BottomSheetItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = label, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
