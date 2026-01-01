package com.jdpadillavigo.newsapp.news.presentation.new_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.jdpadillavigo.newsapp.news.presentation.models.NewUi
import com.jdpadillavigo.newsapp.news.presentation.models.SourceUi
import com.jdpadillavigo.newsapp.news.presentation.models.toFormattedPublishedAt
import com.jdpadillavigo.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewListItem(
    newUi: NewUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(95.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SubcomposeAsyncImage (
            model = ImageRequest.Builder(LocalContext.current)
                .data(newUi.urlToImage)
                .crossfade(true)
                .scale(Scale.FILL)
                .build(),
            contentDescription = "News image",
            contentScale = ContentScale.Crop,
            loading = {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Loading news image",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            error = {
                Icon(
                    imageVector = Icons.Default.ImageNotSupported,
                    contentDescription = "No news image available",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(15.dp))
        )
        Column {
            Text(
                text = newUi.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                if(newUi.author != "") {
                    TextInfo(
                        modifier = Modifier.fillMaxWidth(0.35f),
                        text = newUi.author
                    )
                    if(newUi.publishedAt != "") {
                        TextInfo(text = "  •  ")
                    }
                }
                TextInfo(text = newUi.publishedAt.toFormattedPublishedAt())
            }
        }
    }
}

@Composable
private fun TextInfo(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 16.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun NewListItemPreview() {
    NewsAppTheme {
        NewListItem(
            newUi = previewNew,
            onClick = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        )
    }
}

internal val previewSource = SourceUi(
    id = "the-verge",
    name = "CNN Indonesia"
)

internal val previewNew = NewUi(
    source = previewSource,
    author = "David Pierce",
    title = "Alexander wears modified helmet in road races",
    description = "Hi, friends! Welcome to Installer No. 110, your guide to the best and Verge-iest stuff in the world. (If you're new here, welcome, happy holidays, and also you can read all the old editions at the Installer homepage.) This week, I've been reading about mall S…",
    url = "https://www.theverge.com/tech/848567/best-tech-movies-gadgets-2025-installer",
    urlToImage = "https://platform.theverge.com/wp-content/uploads/sites/2/2025/12/Installer-110.png?quality=90&strip=all&crop=0%2C10.711631919237%2C100%2C78.576736161526&w=1200",
    publishedAt = "2025-12-20T18:56:13Z",
    content = "As a tech department, we're usually pretty good at spotting tech that's out of the ordinary. During <ul><li></li><li></li><li></li></ul>\r\nIn this weeks Installer: All the things we loved to watch, read, play, build, and goof around with this year.\r\nIn this weeks Installer: All the things we loved t… [+15418 chars]"
)