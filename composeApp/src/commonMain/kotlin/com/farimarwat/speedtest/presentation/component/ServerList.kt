package com.farimarwat.speedtest.presentation.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.farimarwat.speedtest.domain.model.STServer
import org.jetbrains.compose.resources.painterResource
import speedtest.composeapp.generated.resources.Res
import speedtest.composeapp.generated.resources.server

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerList(
    list:List<STServer>,
   onDismiss:()->Unit={},
   onItemSelected:(STServer)->Unit={}
){
    val sheetState = rememberModalBottomSheetState()
    val icon = painterResource(Res.drawable.server)


    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
           onDismiss()
        }
    ){
        LazyColumn {
            items(
                items = list,
                key = {it.url.toString()}
            ){item ->
                ServerItem(
                    server = item,
                    onClick = {
                        onItemSelected(item)
                    },
                    icon = icon
                )
            }
        }
    }
}