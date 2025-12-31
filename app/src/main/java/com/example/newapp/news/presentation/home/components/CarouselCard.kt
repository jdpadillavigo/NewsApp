package com.example.newapp.news.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.util.lerp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.newapp.news.presentation.models.NewUi
import com.example.newapp.news.presentation.models.toTimeAgo
import com.example.newapp.news.presentation.new_list.NewListAction
import com.example.newapp.news.presentation.new_list.components.previewNew
import com.example.newapp.ui.theme.NewAppTheme
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun CarouselCard(
    news: List<NewUi>,
    onAction: (NewListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val sliderList = news.takeLast(5)
    val pagerState = rememberPagerState(initialPage = 2)
    val scope = rememberCoroutineScope()

    val textShadow = TextStyle(
        shadow = Shadow(
            color = MaterialTheme.colorScheme.onSurface,
            offset = IntOffset(3, 3).toOffset(),
            blurRadius = 10f
        )
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalPager(
            count = sliderList.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier.fillMaxHeight(0.85f)
        ) {
                page ->
            val backgroundGradient: Brush
            if(sliderList[page].urlToImage != "") {
                backgroundGradient = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.onSurface,
                        Color.Transparent,
                        Color.Transparent,
                        MaterialTheme.colorScheme.onSurface
                    )
                )
            } else {
                backgroundGradient = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent
                    )
                )
            }

            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.94f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                            .also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                    }
                    .clickable {
                        onAction(NewListAction.OnNewClick(sliderList[page]))
                    }
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(30.dp),
                        spotColor = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onSurface),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(sliderList[page].urlToImage)
                            .crossfade(true)
                            .scale(Scale.FILL)
                            .build(),
                        contentDescription = "News image",
                        contentScale = ContentScale.Crop,
                        loading = {
                            ImageIcon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "Loading news image"
                            )
                        },
                        error = {
                            ImageIcon(
                                imageVector = Icons.Default.ImageNotSupported,
                                contentDescription = "No news image available"
                            )
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundGradient)
                            .align(Alignment.Center)
                            .padding(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.BottomStart),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = sliderList[page].source.name +
                                        "  •  " +
                                        sliderList[page].publishedAt.toTimeAgo(),
                                color = MaterialTheme.colorScheme.surface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = textShadow
                            )
                            Text(
                                text = sliderList[page].title,
                                color = MaterialTheme.colorScheme.surface,
                                fontSize = 16.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.Black,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = textShadow
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(sliderList.size) { it ->
                val clipShape: Shape
                val width: Dp
                val height = 8.dp
                val color: Color
                if(pagerState.currentPage == it) {
                    clipShape = RoundedCornerShape(20.dp)
                    width = height * 3
                    color = MaterialTheme.colorScheme.primary
                } else {
                    clipShape = CircleShape
                    width = height
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(clipShape)
                        .size(width = width, height = height)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        }
                )
            }
        }
    }
}

@Composable
private fun ImageIcon(
    imageVector: ImageVector,
    contentDescription: String
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        tint = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
    )
}

@PreviewLightDark
@Composable
private fun CarouselCardPreview() {
    NewAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            CarouselCard(
                news = (1..10).map {
                    previewNew
                },
                onAction = {},
                modifier = Modifier.fillMaxHeight(0.3f)
            )
        }
    }
}