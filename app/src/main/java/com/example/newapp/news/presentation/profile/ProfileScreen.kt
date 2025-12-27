package com.example.newapp.news.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.ui.theme.NewAppTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val iconColors = IconButtonColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.tertiary,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContentColor = MaterialTheme.colorScheme.tertiary
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
//            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row {
            IconButton(
                onClick = {},
                colors = iconColors
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                IconButton(
                    onClick = {},
                    colors = iconColors
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = ""
                    )
                }
                IconButton(
                    onClick = {},
                    colors = iconColors
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = ""
                    )
                }
            }
        }
        Text(
            text = "Profile Screen",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
    }
}

@PreviewLightDark
@Composable
fun ProfileScreenPreview() {
    NewAppTheme {
        ProfileScreen()
    }
}