package com.horionDev.climbingapp.android.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.horionDev.climbingapp.android.R
import androidx.compose.ui.unit.sp
import com.horionDev.climbingapp.android.ui.theme.AppColor


@Composable
fun GlobalPopup(modifier: Modifier, state: GlobalPopupState, onExit: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColor.ClearGrey)
    ) {
        Column(
            modifier
                .padding(horizontal = 34.dp)
                .shadow(4.dp, RoundedCornerShape(15))
                .background(
                    color = Color.White, shape = RoundedCornerShape(15)
                )
                .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.Center)
        ) {

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = when (state) {
                        GlobalPopupState.NETWORK_MISSING -> stringResource(id = R.string.popup_missing_network_title)
                        GlobalPopupState.GPS_MISSING -> stringResource(id = R.string.popup_missing_gps_title)
                        GlobalPopupState.HID -> ""
                        GlobalPopupState.CUSTOM -> stringResource(id = R.string.popup_missing_gps_title)
                    },
                    fontWeight = FontWeight.W700, fontFamily = FontFamily(Font(R.font.circularstdmedium)),
                    fontSize = 22.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onExit() }) {
                    Icon(imageVector = Icons.Sharp.Close, contentDescription = "")
                }
            }


            Text(
                text = when (state) {
                    GlobalPopupState.NETWORK_MISSING -> stringResource(id = R.string.popup_missing_network_text)
                    GlobalPopupState.GPS_MISSING -> stringResource(id = R.string.popup_missing_gps_text)
                    GlobalPopupState.HID -> ""
                    GlobalPopupState.CUSTOM -> stringResource(id = R.string.popup_missing_gps_title)

                },
                modifier = Modifier.padding(top = 5.dp, bottom = 20.dp),
                fontWeight = FontWeight(450), fontFamily = FontFamily(Font(R.font.circularstdmedium)),
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1
            )
        }
    }
}

enum class GlobalPopupState(var title: String? = null, var content: String? = null) {
    HID, NETWORK_MISSING, GPS_MISSING, CUSTOM("title", "content");
}
