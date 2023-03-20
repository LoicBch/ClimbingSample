package com.example.camperpro.android.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.camperpro.android.R
import com.example.camperpro.android.ui.theme.AppColor
import com.example.camperpro.android.ui.theme.Dimensions

@Composable
fun LoadingModal(modifier: Modifier) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColor.ClearGrey)
    ) {
        Column(
            modifier
                .padding(horizontal = 34.dp)
                .shadow(4.dp, RoundedCornerShape(Dimensions.radiusTextField))
                .background(
                    color = Color.White, shape = RoundedCornerShape(Dimensions.radiusTextField)
                )
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .width(IntrinsicSize.Max)
        ) {

            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.loading),
                    fontWeight = FontWeight(450),
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                CircularProgressIndicator(modifier = Modifier.padding(start = 15.dp))
            }
        }
    }
}