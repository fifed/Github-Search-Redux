package com.fifed.ui.extension

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.findLastVisibleItem() = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1