package com.dogwifhat.app.ui.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dogwifhat.app.ui.theme.AppColor

@Composable
fun BasedAlertDialog(
  title: String,
  description: String,
  onConfirmClick: () -> Unit,
  onDismissClick: (() -> Unit)? = null,
  onDismissRequest: () -> Unit = {},
) {
  AlertDialog(
    onDismissRequest = onDismissRequest,
    title = {
      Text(
        text = title,
        color = Color.White,
      )
    },
    text = {
      Text(
        text = description,
        color = Color.White,
      )
    },
    containerColor = Color(0xFF14161B),
    confirmButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = AppColor.ButtonPrimary,
          contentColor = AppColor.ButtonPrimaryText,
        ),
        onClick = onConfirmClick,
      ) {
        Text("Ok")
      }
    },
    dismissButton = onDismissClick?.let {
      {
        Button(
          colors = ButtonDefaults.buttonColors(
            containerColor = AppColor.ButtonPrimary,
            contentColor = AppColor.ButtonPrimaryText,
          ),
          onClick = onDismissClick,
        ) {
          Text("Cancel")
        }
      }
    },
  )
}

@Composable
@Preview
fun BasedAlertDialogPreview() {
  BasedAlertDialog(
    title = "Hello World",
    description = "Lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
    onConfirmClick = {},
    onDismissClick = {},
  )
}
