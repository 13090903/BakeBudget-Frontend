package ru.vsu.csf.bakebudget.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import ru.vsu.csf.bakebudget.models.IngredientModel
import ru.vsu.csf.bakebudget.models.OrderModel
import ru.vsu.csf.bakebudget.screens.sortByState
import ru.vsu.csf.bakebudget.ui.theme.SideBack
import ru.vsu.csf.bakebudget.ui.theme.TextPrimary

@Composable
fun Order(order: OrderModel,
          orders: MutableList<OrderModel>,
          orders0: MutableList<OrderModel>,
          orders1: MutableList<OrderModel>,
          orders2: MutableList<OrderModel>,
          orders3: MutableList<OrderModel>) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val selectedValue = remember { mutableIntStateOf(order.status) }
    when {
        openAlertDialog.value -> {
            AlertDialogOrder(
                onDismissRequest = {
                    openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                },
                dialogTitle = order.product.name,
                dialogText = "Можете изменить состояние заказа",
                order,
                selectedValue,
                orders,
                orders0,
                orders1,
                orders2,
                orders3
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SideBack)
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .clickable(onClick = { openAlertDialog.value = true }),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = order.product.iconId), contentDescription = order.product.name
        )
        Column {
            Text(modifier = Modifier.padding(start = 8.dp, bottom = 6.dp), text = order.product.name, color = TextPrimary, fontSize = 16.sp)
            Text(modifier = Modifier.padding(start = 8.dp), text = order.finalPrice.toString() + " р.", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(modifier = Modifier.padding(start = 10.dp), text = "за " + order.weight.toString() + " г.", fontSize = 12.sp, color = TextPrimary)
        }
    }
}

@Composable
fun AlertDialogOrder(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    order: OrderModel,
    selectedValue: MutableState<Int>,
    orders: MutableList<OrderModel>,
    orders0: MutableList<OrderModel>,
    orders1: MutableList<OrderModel>,
    orders2: MutableList<OrderModel>,
    orders3: MutableList<OrderModel>
) {
    androidx.compose.material3.AlertDialog(
        containerColor = SideBack,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier.fillMaxWidth(0.9f),
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column {
                Text(text = dialogText)
                OrderStateRadio(selectedValue = selectedValue)
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    order.status = selectedValue.value
                    sortByState(orders, orders0, orders1, orders2, orders3)
                    onConfirmation()
                }
            ) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Отмена")
            }
        }
    )
}