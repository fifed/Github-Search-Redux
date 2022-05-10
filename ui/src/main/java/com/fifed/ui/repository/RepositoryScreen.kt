package com.fifed.ui.repository

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fifed.githubrepositories.ui.theme.Purple700
import com.fifed.presentation.R
import com.fifed.presentation.connector.RepositoryScreenConnector
import com.fifed.presentation.ui_props.RepositoryUIProps
import com.fifed.ui.image.RemoteImage
import com.fifed.ui.search.Error
import org.koin.androidx.compose.get

@Composable
fun RepositoryScreen(navController: NavHostController, arguments: Bundle, connector: RepositoryScreenConnector = get()) {
    val ownerName = RepositoryScreenStarter.getOwnerName(arguments)
    val repositoryName = RepositoryScreenStarter.getRepositoryName(arguments)
    val id = RepositoryScreenStarter.getRepositoryId(arguments)
    val props = connector.uiFlow.collectAsState(initial = RepositoryUIProps()).value
    LaunchedEffect(Unit) {
        connector.load(id, ownerName, repositoryName)
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = 8.dp
    ) {
        if (props.showProgress) {
            Progress()
        }
        if (props.showError) {
            Error {
                connector.load(id, ownerName, repositoryName)
            }
        }
        if (props.showContent) {
            Content(props)
        }
    }
}

@Composable
private fun Progress() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .offset(y = 100.dp)
        )
    }
}

@Composable
private fun Content(props: RepositoryUIProps) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopImage(props.ownerAvatar)
        Spacer(modifier = Modifier.height(16.dp))
        Name(props.name)
        Spacer(modifier = Modifier.height(16.dp))
        Owner(props.ownerName)
        if (props.showLanguage) {
            Spacer(modifier = Modifier.height(16.dp))
            Language(props.language)
        }
        if (props.showLicence) {
            Spacer(modifier = Modifier.height(16.dp))
            License(name = props.licenceName)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TitleValueBlock(title = "${stringResource(id = R.string.forks)} : ", value = props.forks)
        Spacer(modifier = Modifier.height(16.dp))
        TitleValueBlock(title = "${stringResource(id = R.string.visibility)} : ", value = props.type)
        Spacer(modifier = Modifier.height(16.dp))
        TitleValueBlock(title = "${stringResource(id = R.string.default_branch)} : ", value = props.defaultBranch)
        Spacer(modifier = Modifier.height(16.dp))
        TitleValueBlock(title = "${stringResource(id = R.string.issues)} : ", value = props.issues)
        Spacer(modifier = Modifier.height(16.dp))
        Url(props.url)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun Language(language: String) {
    Text(
        text = language,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = Purple700,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun Owner(ownerName: String) {
    Text(
        text = ownerName,
        fontSize = 18.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Name(name: String) {
    Text(
        text = name, fontSize = 22.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
private fun TopImage(imageUrl: String) {
    RemoteImage(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(5.dp)),
        url = imageUrl,
        loading = {
            CircularProgressIndicator()
        }
    )
}

@Composable
private fun TitleValueBlock(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Url(url: String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = url,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            modifier = Modifier.clickable {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        )
    }
}

@Composable
private fun License(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${stringResource(id = R.string.license)} : ",
            fontSize = 18.sp,
        )

        Text(
            text = name,
            color = Color.Blue,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}