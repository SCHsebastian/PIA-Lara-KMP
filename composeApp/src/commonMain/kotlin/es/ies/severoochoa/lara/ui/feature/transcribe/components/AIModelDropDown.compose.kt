package es.ies.severoochoa.lara.ui.feature.transcribe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ies.severoochoa.lara.domain.TranscriptionModel

@Composable
fun TranscriptionModelDropDown(
    modifier: Modifier = Modifier,
    selectedModel: TranscriptionModel,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onItemClick: () -> Unit,
    onItemSelect: (TranscriptionModel) -> Unit,
    items: List<TranscriptionModel>
) {
    val color = MaterialTheme.colors.onSurface
    Box(modifier = modifier){
       DropdownMenu(
           expanded = isOpen,
           onDismissRequest = onDismiss,
       ) {
           items.forEach { item ->
               DropdownMenuItem(
                   modifier = Modifier,
                   onClick = {
                       onItemSelect(item)
                   },
               ){
                   Text(
                       textAlign = TextAlign.Center,
                       text = item.name
                   )
               }
           }
       }
        Row (
            modifier = Modifier
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(12.dp)
                ).clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.surface)
                .clickable(onClick = onItemClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = selectedModel.name,
            )
            Icon(
                imageVector = if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = selectedModel.name,
                tint = color,
                modifier = Modifier.size(30.dp)
            )
        }
    }

}