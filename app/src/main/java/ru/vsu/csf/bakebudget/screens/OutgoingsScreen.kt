package ru.vsu.csf.bakebudget.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.vsu.csf.bakebudget.R
import ru.vsu.csf.bakebudget.components.Outgoing
import ru.vsu.csf.bakebudget.components.OutgoingAdd
import ru.vsu.csf.bakebudget.models.OutgoingModel
import ru.vsu.csf.bakebudget.models.MenuItemModel
import ru.vsu.csf.bakebudget.ui.theme.Back2
import ru.vsu.csf.bakebudget.ui.theme.PrimaryBack
import ru.vsu.csf.bakebudget.ui.theme.SideBack

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OutgoingsScreen(navController: NavHostController,
                    outgoings: MutableList<OutgoingModel>,
                    isLogged: MutableState<Boolean>
) {
    val mContext = LocalContext.current
    val item = listOf(MenuItemModel(R.drawable.outgoings, "Издержки"))
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember {
        mutableStateOf(item[0])
    }
    val name = remember {
        mutableStateOf("")
    }
    val value = remember {
        mutableStateOf("")
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideMenu(
                navController = navController,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                isLogged = isLogged
            )
        },
        content = {
            Scaffold(bottomBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .background(SideBack)
                        .padding(start = 8.dp, end = 8.dp),
                    shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
                ) {
                    Box(
                        modifier = Modifier.background(PrimaryBack),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            OutgoingAdd(name, value)
                            TextButton(
                                onClick = {
                                    if (name.value.isEmpty() || value.value.isEmpty() || value.value.toIntOrNull() == null ) {
                                        mToast(context = mContext)
                                    } else {
                                        outgoings.add(
                                            OutgoingModel(
                                                name.value,
                                                value.value.toInt()
                                            )
                                        )
                                        name.value = "q"
                                        value.value = "1"
                                    }
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.button_add),
                                    contentDescription = "add"
                                )
                            }
                        }
                    }
                }
            }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(SideBack)
                        .padding(bottom = 10.dp)
                ) {
                    Column {
                        Header(scope = scope, drawerState = drawerState)
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .background(SideBack)
                                .padding(top = 20.dp)
                        ) {
                            itemsIndexed(outgoings) { num, cost ->
                                if (num % 2 == 0) {
                                    Outgoing(cost, SideBack, outgoings)
                                } else {
                                    Outgoing(cost, Back2, outgoings)
                                }
                            }
                        }
                    }
                }
            }
        })
}

@Composable
private fun Header(scope: CoroutineScope, drawerState: DrawerState) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .background(SideBack)
            .padding(start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp),
    ) {
        Column(modifier = Modifier.background(PrimaryBack)) {
            Row {
                Box(
                    modifier = Modifier
                        .background(PrimaryBack)
                ) {
                    TextButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.menu_open),
                            contentDescription = "menu",
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryBack)
                        .padding(top = 8.dp, end = 60.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(text = "ИЗДЕРЖКИ", fontSize = 24.sp, color = Color.White)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(40.dp)
                    .background(PrimaryBack)
                    .padding(start = 16.dp, top = 6.dp, bottom = 6.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "НАЗВАНИЕ", color = Color.White, fontSize = 12.sp)
                Text(text = "СТОИМОСТЬ", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

private fun mToast(context: Context) {
    Toast.makeText(
        context,
        "Некорректные данные",
        Toast.LENGTH_LONG
    ).show()
}