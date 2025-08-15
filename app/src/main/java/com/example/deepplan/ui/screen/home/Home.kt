package com.example.deepplan.ui.screen.home

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deepplan.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import com.example.deepplan.ui.theme.DeepPlanTheme

//@Preview(showBackground = false)
@Composable
fun Home() {
    var textBox by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 12.dp, start = 4.dp, end = 4.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.leading_icon),
                contentDescription = "My Local Image",
//                modifier = Modifier.size(120.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.trailing_elements),
                contentDescription = "My Local Image",
//                modifier = Modifier.size(120.dp)
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(bottom = 40.dp, start = 23.dp, top = 6.dp)
        ) {
            Text(
                text = "How's it going, Handsome Kevin?",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column (
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 16.dp)
                .background(MaterialTheme.colorScheme.onPrimary),
        ) {
            Column (
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "SMART Recycling",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ElevatedCard (
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 18.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "Project progress",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.surfaceTint,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(27.dp)
                        )

                        LinearProgressIndicator(
                            progress = { 0.1f },
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "${(0.1f * 100).toInt()}%",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(27.dp))

                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                )

                Spacer(modifier = Modifier.height(22.dp))

//                MyTooltipExample()

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 18.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "Tip",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Consider giving a well detailed instructions on what the project is about.",
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "I understand",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Right,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(49.dp)
                )

                Text(
                    text = "Summary",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                ExpandableBox("General Information", "Project Name: SMART")

                ExpandableBox("Technical Scope", "Project Name: SMART")

                ExpandableBox("External Context", "Project Name: SMART")

                ExpandableBox("Internal Factors", "Project Name: SMART")

                ExpandableBox("Prediction Results", "Project Name: SMART")

                Spacer(modifier = Modifier.height(75.dp))


                    Column {
                        Text(
                            text = "Gantt Chart",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Image(
                            painter = painterResource(R.drawable.image_1),
                            contentDescription = "image 1"
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Describe to us how do you want the planning to be laid out:"
                        )

                        Spacer(modifier = Modifier.height(11.dp))

                        TextField(
                            value = textBox,
                            onValueChange = { textBox = it }
                        )
                    }


                Spacer(modifier = Modifier.height(26.dp))

                Card (
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column (
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 18.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "To-do list",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        Text(
                            text = "A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made."
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        CheckList()
                    }
                }

                Spacer(modifier = Modifier.height(55.dp))

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFD9D9D9))
                            .padding(vertical = 11.dp, horizontal = 45.dp)
                            .clickable {  }
                    ) {
                        Text(
                            text = "Edit Information"
                        )
                    }

                    Spacer(modifier = Modifier.height(9.dp))

                    Text(
                        text = "Warning: This will redo the prediction once and will overwrite current information, but you can still rollback to this version",
                        style = TextStyle(
                            fontSize = 13.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableBox(text: String, desc: String) {
    var expanded by remember { mutableStateOf(false) }

    // Animate arrow rotation
    val rotation by animateFloatAsState(if (expanded) 90f else 0f, label = "arrowRotation")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded } // Toggle dropdown
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.play_arrow_filled),
                contentDescription = "Arrow",
                modifier = Modifier.rotate(rotation)
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                text = text,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Animated dropdown content
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 37.dp, top = 8.dp)) {
                Text(
                    text = desc,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyTooltipExample() {
//    TooltipBox(
//        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
//        tooltip = {
//            RichTooltip {
//                Text("Test 1")
//            }
//        },
//        state = rememberTooltipState()
//    ) {
//        Text("Test 2")
//    }
//}


@Composable
fun CheckList() {
    val items = listOf(
        "Project Kick-off & Final Permits",
        "Site Survey and Staking",
        "Site Clearing and Grubbing"
    )

    val checkedStates = remember { mutableStateListOf(false, false, false) }

    Column (
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        items.forEachIndexed { index, item ->
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                )

                Checkbox(
                    checked = checkedStates[index],
                    onCheckedChange = { checkedStates[index] = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = Color.White
                    )
                )
            }

            if(index != items.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(42.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 28.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Add",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {  }
            )

            Spacer(modifier = Modifier.width(47.dp))

            Text(
                text = "Edit",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {  }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = false) // remove the forced white background
@Composable
fun HomePreview() {
    DeepPlanTheme { // wrap in your app theme
        Home()
    }
}
