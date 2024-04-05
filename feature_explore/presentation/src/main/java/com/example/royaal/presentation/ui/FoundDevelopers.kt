package com.example.royaal.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.royaal.commonui.DimConst
import com.example.royaal.core.common.model.uimodel.Developer

internal fun LazyListScope.foundDevelopers(
    devs: List<Developer>,
    onDevClick: (Int) -> Unit,
) {
    item {
        LazyRow (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
            contentPadding = PaddingValues(horizontal = DimConst.defaultPadding)
        ) {
            items(count = devs.size) {
                DeveloperCard(developer = devs[it]) {
                    onDevClick(devs[it].id)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeveloperCard(
    developer: Developer,
    onDevClick: () -> Unit
) {
    Card(
        onClick = onDevClick,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .height(128.dp)
            .aspectRatio(1f)
    ) {
        Box {
            AsyncImage(
                model = developer.img,
                contentDescription = developer.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = developer.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}