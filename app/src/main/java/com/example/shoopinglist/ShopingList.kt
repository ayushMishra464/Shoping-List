package com.example.shoopinglist

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp

data class shopingList(val id:Int,
                       var name:String,
                       var quantity:Int,
                       var isEditing : Boolean = false
)

@Composable
fun ShopingList(){
    var sItem by remember { mutableStateOf(listOf<shopingList>()) }
    var alert by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQ by remember { mutableStateOf("") }
//    fun onEditclick(){
//        alert = true
//        itemName = sItem.elementAt(sItem.size).name
//        itemQ=sItem.elementAt(sItem.size-1).quantity.toString()
//    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = { alert = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            ) {
               Text(text = "Add Item")
        }
      LazyColumn(
          modifier = Modifier
              .fillMaxSize()
              .padding(16.dp))
      {
       items(sItem){
         item ->
            if (item.isEditing){

               ItemEdit(item = item, onEditComplete = {
                   editedName, editedQt ->
                   sItem = sItem.map { it.copy(isEditing = false)}
                       val editedItem = sItem.find { it.id == item.id }
                       editedItem?.let {
                           it.name = editedName
                           it.quantity = editedQt
                       }
               } )
            }
           else
               ShoopinglistItem(item = item,
                   onEditclick = {
                   sItem = sItem.map { it.copy(isEditing = it.id == item.id) } },
                   onDeleteclick = {
                   sItem = sItem - item
               }
               )
       }
      }
    }
    if (alert){
    AlertDialog(onDismissRequest = { alert = false },
        title = { Text(text = "Add Shopping item")},
        confirmButton = { Row (
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(8.dp),
                 horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                if (itemName.isNotEmpty() && itemQ.isNotEmpty()){
                    val item1 = shopingList(
                        id = sItem.size +1,
                        name = itemName,
                        quantity = itemQ.toInt()
                    )
                    sItem += item1
                    alert = false
                    itemName = ""
                    itemQ = ""

            }
            }
                ) {
                Text(text = "Save")
            }
            Button(onClick = { alert = false }) {
                Text(text = "Cancel")
            }

        }},
        text = {
            Column()
             {
  
                OutlinedTextField(value = itemName, onValueChange = { itemName = it } )
                OutlinedTextField(value = itemQ, onValueChange = {itemQ = it} )
        }
        }
        )
    }

}

@Composable
fun ItemEdit(
    item: shopingList, onEditComplete: (String , Int ) -> Unit
){
    var editedName by  remember { mutableStateOf(item.name) }
    var editedQt by  remember { mutableStateOf(item.quantity.toString()) }
    var statusEditing by remember { mutableStateOf(item.isEditing) }

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        Column {
            BasicTextField(value = editedName,
                onValueChange = { editedName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            BasicTextField(value = editedQt,
                onValueChange = { editedQt = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }
        Button(onClick = {
            statusEditing = false
            // if edited quantitiy is null it will take that as 1
            onEditComplete(editedName,editedQt.toIntOrNull() ?: 1)
        }) {
            Text(text = "Save")
        }
    }
}


@Composable
fun ShoopinglistItem (
         item : shopingList,
         onEditclick : () -> Unit,
         onDeleteclick : () -> Unit,
){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(
            border = BorderStroke(2.dp, Color.Blue),
            shape = RoundedCornerShape(20),
        ),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = item.name, modifier = Modifier
            .padding(8.dp)
            .align(Alignment.Top))
        Text(text = "Qut:${item.quantity}", modifier = Modifier
            .padding(8.dp)
            .align(Alignment.CenterVertically))
        Row (
            
        ) {
            IconButton(onClick = onEditclick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription ="" )
            }
            IconButton(onClick = onDeleteclick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription ="" )
            }

        }

    }
}


