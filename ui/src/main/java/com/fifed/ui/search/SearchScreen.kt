package com.fifed.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.fifed.presentation.R
import com.fifed.presentation.connector.SearchScreenConnector
import com.fifed.presentation.ui_props.SearchItemUIProps
import com.fifed.presentation.ui_props.SearchUiProps
import com.fifed.ui.extension.findLastVisibleItem
import com.fifed.ui.image.RemoteImage
import com.fifed.ui.repository.RepositoryScreenStarter
import org.koin.androidx.compose.get

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavHostController, connector: SearchScreenConnector = get()) {
    val props = connector.uiStateFlow.collectAsState(initial = SearchUiProps()).value
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        SearchInput(props.searchText) { inputText ->
            connector.onInputChanged(inputText)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (props.showCentralProgress) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .offset(y = 100.dp)
                )
            }

            if (props.showDefaultPlaceHolder) {
                DefaultPlaceHolder()
            }

            if (props.showCentralError) {
                Error(connector::onRetry)
            }

            if (props.showNoResults) {
                Text(
                    text = "${stringResource(R.string.no_repository_found)} \"${props.latestQuery}\"",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .offset(y = 100.dp)
                )
            }
            if (props.items.isNotEmpty()) {
                RepositoriesList(items = props.items,
                                 showBottomProgress = props.showBottomProgress,
                                 showBottomError = props.showBottomError,
                                 onScroll = { lastVisiblePosition ->
                                     keyboardController?.hide()
                                     connector.handleScroll(lastVisiblePosition)
                                 }, onItemClick = { repo ->
                        navController.navigate(RepositoryScreenStarter(repo.id, repo.ownerName, repo.name).getRoute())
                    }, onRetry = { connector.onRetry() })
            }
        }
    }
}

@Composable
fun Error(retry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.error_happened),
            fontSize = 20.sp,
            color = Color.Red,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { retry() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun SearchInput(
    searchText: String,
    onInputChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp), elevation = 8.dp
    ) {
        OutlinedTextField(
            value = searchText,
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(all = 8.dp),
            label = { Text(text = stringResource(id = R.string.search_github_repositories)) },
            onValueChange = { text ->
                onInputChanged(text)
            })
    }
}

@Composable
private fun RepositoriesList(
    items: List<SearchItemUIProps>,
    showBottomProgress: Boolean,
    showBottomError: Boolean,
    onScroll: (Int) -> Unit,
    onItemClick: (SearchItemUIProps) -> Unit,
    onRetry: () -> Unit
) {
    val listState = rememberLazyListState()
    if (listState.isScrollInProgress) {
        onScroll(listState.findLastVisibleItem())
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(items = items) { item ->
            RepositoryItem(item, onItemClick)
        }
        if (showBottomProgress) {
            item("BottomProgress") {
                ProgressItem()
            }
        }
        if (showBottomError) {
            item("BottomError") {
                ErrorItem(onRetry)
            }
        }
    }
}

@Composable
private fun ProgressItem() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorItem(onRetry: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.error_happened),
            color = Color.Red,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Button(
            onClick = { onRetry() }, modifier = Modifier
                .weight(0.5f)
                .padding(4.dp)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun DefaultPlaceHolder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = null
        )
    }
}

@Composable
private fun RepositoryItem(uiProps: SearchItemUIProps, onItemClick: (SearchItemUIProps) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(uiProps) },
        elevation = 16.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        ) {
            val (image, ownerTitle, ownerName, repoName, type) = createRefs()
            Text(
                text = "${stringResource(R.string.owner)} :",
                modifier = Modifier
                    .constrainAs(ownerTitle) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                color = Color.DarkGray,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier
                    .constrainAs(ownerName) {
                        top.linkTo(parent.top)
                        start.linkTo(ownerTitle.end, margin = 32.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.preferredWrapContent
                    },
                text = uiProps.ownerName,
                color = Color.Black,
                fontSize = 20.sp,
            )
            RemoteImage(
                modifier = Modifier
                    .height(100.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(image) {
                        top.linkTo(ownerName.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                    },
                url = uiProps.ownerAvatar,
                maxSize = 100.dp,
                loading = {
                    CircularProgressIndicator()
                }
            )

            Text(
                modifier = Modifier
                    .constrainAs(repoName) {
                        top.linkTo(ownerName.bottom)
                        start.linkTo(image.end, margin = 16.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(type.top)
                        width = Dimension.preferredWrapContent
                    },
                text = uiProps.name,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                modifier = Modifier
                    .constrainAs(type) {
                        top.linkTo(repoName.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                text = uiProps.type,
                textAlign = TextAlign.Center,
                color = Color(uiProps.typeColor),
                fontSize = 18.sp,
                maxLines = 1
            )
        }
    }
}