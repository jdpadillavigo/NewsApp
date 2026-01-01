package com.jdpadillavigo.newsapp.news.presentation.new_detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import androidx.core.net.toUri
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.jdpadillavigo.newsapp.news.domain.New
import com.jdpadillavigo.newsapp.news.domain.Source
import com.jdpadillavigo.newsapp.news.presentation.home.ScreenGradient
import com.jdpadillavigo.newsapp.news.presentation.home.components.LoadingScreen
import com.jdpadillavigo.newsapp.news.presentation.models.toNewUi
import com.jdpadillavigo.newsapp.news.presentation.models.toTimeAgo
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListState
import com.jdpadillavigo.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewDetailScreen(
    state: NewListState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if(state.isLoading) {
        LoadingScreen(modifier = modifier)
    } else if(state.selectedNew != null) {
        val new = state.selectedNew
        val context = LocalContext.current
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        val iconColors = IconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        )

        val backgroundGradient: Brush
        if(new.urlToImage != "") {
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

        val textShadow = TextStyle(
            shadow = Shadow(
                color = MaterialTheme.colorScheme.onSurface,
                offset = IntOffset(3, 3).toOffset(),
                blurRadius = 10f
            )
        )

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .align(Alignment.Center)
            ) {
                item {
                    Box (
                        modifier = Modifier
                            .height(screenHeight * 0.5f)
                            .background(MaterialTheme.colorScheme.onSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(new.urlToImage)
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
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier.align(Alignment.TopStart),
                                colors = iconColors
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = "Go back"
                                )
                            }
                            Row(
                                modifier = Modifier.align(Alignment.TopEnd),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                IconButton(
                                    onClick = {},
                                    colors = iconColors
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.BookmarkBorder,
                                        contentDescription = "Save news"
                                    )
                                }
                                IconButton(
                                    onClick = {},
                                    colors = iconColors
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MoreHoriz,
                                        contentDescription = "Options"
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.align(Alignment.BottomStart),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = new.title,
                                    color = MaterialTheme.colorScheme.surface,
                                    fontSize = 24.sp,
                                    lineHeight = 30.sp,
                                    fontWeight = FontWeight.Black,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = textShadow
                                )
                                Text(
                                    text = new.publishedAt.toTimeAgo(),
                                    color = MaterialTheme.colorScheme.surface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    style = textShadow
                                )
                            }
                        }
                    }
                }
                item {
                    Column (
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onSurface)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 30.dp,
                                    topEnd = 30.dp
                                )
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = new.source.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = new.content,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, new.url.toUri())
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 50.dp)
                        ) {
                            Text(
                                text = "Ver noticia completa",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
            ScreenGradient(modifier = Modifier.align(Alignment.Center))
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
private fun NewsDetailScreenPreview() {
    NewsAppTheme {
        NewDetailScreen(
            state = NewListState(
                selectedNew = previewNew.toNewUi()
            ),
            onBack = {}
        )
    }
}

internal val previewSource = Source(
    id = "the-verge",
    name = "The Verge The VergeThe VergeThe VergeThe VergeThe VergeThe VergeThe VergeThe Verge"
)

internal val previewNew = New(
    source = previewSource,
    author = "David Pierce",
    title = "Alexander wears modified helmet in road races",
    description = "Hi, friends! Welcome to Installer No. 110, your guide to the best and Verge-iest stuff in the world. (If you're new here, welcome, happy holidays, and also you can read all the old editions at the Installer homepage.) This week, I've been reading about mall S…",
    url = "https://www.theverge.com/tech/848567/best-tech-movies-gadgets-2025-installer",
    urlToImage = "https://platform.theverge.com/wp-content/uploads/sites/2/2025/12/Installer-110.png?quality=90&strip=all&crop=0%2C10.711631919237%2C100%2C78.576736161526&w=1200",
    publishedAt = "2025-12-20T18:56:13Z",
    content = "As a tech department, we're usually pretty good at spotting As a tech department, we're usually pretty good at spotting As a tech department, we're usually pretty good at spotting As a tech department, we're usually pretty good at spotting tech that's out of the ordinary. During <ul><li></li><li></li><li></li></ul>\r\t\tIn this \t weeks Installer: All the things we loved to watch, read, play, build, and goof around with this year.\r\nIn this weeks Installer: All the Podcast: Play in new window | Download | Embed\\r\\nSubscribe: Apple Podcasts | Email | RSS\\r\\nJoining me today is 7SEES, here to discuss his research regarding numerous technological developments and the … [+7152 chars]"
)