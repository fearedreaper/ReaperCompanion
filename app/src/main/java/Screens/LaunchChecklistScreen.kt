package com.example.reapercompanion.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperCard
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperDivider
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperSecondaryButton

private data class LaunchTask(
    val id: String,
    val title: String,
    val description: String,
    val completedByDefault: Boolean
)

@Composable
fun LaunchChecklistScreen(
    onBackClick: () -> Unit
) {
    val sections = remember {
        listOf(
            "CORE FEATURES" to listOf(
                LaunchTask(
                    id = "survivor",
                    title = "Survivor Builds",
                    description = "Generate Survivor builds by goal.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "killer",
                    title = "Killer Builds",
                    description = "Generate Killer builds by strategy.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "build_around",
                    title = "Build Around a Perk",
                    description = "Create builds around a selected perk.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "random",
                    title = "Random Build",
                    description = "Generate surprise Survivor or Killer builds.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "meta",
                    title = "Meta Builds",
                    description = "Show curated competitive loadouts.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "favorites",
                    title = "Favorites",
                    description = "Save and review selected builds.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "match_coach",
                    title = "Reaper Match Coach",
                    description = "Three-step Killer and map coaching flow.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "maps",
                    title = "Full Map Roster",
                    description = "All supported maps available in Match Coach.",
                    completedByDefault = true
                )
            ),
            "LIVE CONTENT" to listOf(
                LaunchTask(
                    id = "remote_json",
                    title = "Remote JSON",
                    description = "GitHub-hosted content file is created.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "remote_service",
                    title = "Remote Fetch Service",
                    description = "App can download and parse live content.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "live_screen",
                    title = "Live Content Screen",
                    description = "Announcements, rewards, and featured builds UI.",
                    completedByDefault = true
                ),
                LaunchTask(
                    id = "live_navigation",
                    title = "Live Content Navigation",
                    description = "Add a working dashboard route to the live screen.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "internet_permission",
                    title = "Internet Permission",
                    description = "Add INTERNET permission to AndroidManifest.xml.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "remote_test",
                    title = "Remote Update Test",
                    description = "Change GitHub JSON and verify the app refreshes.",
                    completedByDefault = false
                )
            ),
            "POLISH AND TESTING" to listOf(
                LaunchTask(
                    id = "dashboard",
                    title = "Dashboard 2.0",
                    description = "Organize Reaper Features and Tools sections.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "settings",
                    title = "Settings Navigation",
                    description = "Connect the Settings screen.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "full_test",
                    title = "Full App Test",
                    description = "Test every screen, button, and back action.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "warnings",
                    title = "Critical Warning Cleanup",
                    description = "Resolve warnings that affect stability or release.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "phone_build",
                    title = "Signed Phone Build",
                    description = "Create and install a signed release build.",
                    completedByDefault = false
                )
            ),
            "GOOGLE PLAY" to listOf(
                LaunchTask(
                    id = "privacy",
                    title = "Privacy Policy",
                    description = "Publish a valid privacy policy page.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "store_listing",
                    title = "Store Listing",
                    description = "Write title, short description, and full description.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "screenshots",
                    title = "Store Graphics",
                    description = "Create screenshots, icon, and feature graphic.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "data_safety",
                    title = "Data Safety",
                    description = "Complete the Play Console Data Safety form.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "content_rating",
                    title = "Content Rating",
                    description = "Complete the Play Console questionnaire.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "closed_test",
                    title = "Closed Testing",
                    description = "Complete required testing before production.",
                    completedByDefault = false
                ),
                LaunchTask(
                    id = "production",
                    title = "Production Release",
                    description = "Submit Reaper Companion to Google Play.",
                    completedByDefault = false
                )
            )
        )
    }

    val checkedStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            sections
                .flatMap { it.second }
                .forEach { task ->
                    this[task.id] = task.completedByDefault
                }
        }
    }

    val allTasks = sections.flatMap { it.second }
    val completedCount = allTasks.count { task ->
        checkedStates[task.id] == true
    }

    val progressPercent = if (allTasks.isEmpty()) {
        0
    } else {
        (completedCount * 100) / allTasks.size
    }

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                ReaperHeader(
                    title = "LAUNCH CHECKLIST",
                    onBackClick = onBackClick
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER COMPANION",
                    title = "$progressPercent% Launch Ready",
                    body =
                        "$completedCount of ${allTasks.size} launch tasks are marked complete.",
                    badge = "LAUNCH MODE"
                )
            }

            sections.forEach { (sectionTitle, tasks) ->
                item {
                    SectionHeader(
                        title = sectionTitle,
                        completed = tasks.count { task ->
                            checkedStates[task.id] == true
                        },
                        total = tasks.size
                    )
                }

                tasks.forEach { task ->
                    item {
                        LaunchTaskCard(
                            task = task,
                            checked = checkedStates[task.id] == true,
                            onCheckedChange = { checked ->
                                checkedStates[task.id] = checked
                            }
                        )
                    }
                }

                item {
                    ReaperDivider()
                }
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK",
                    onClick = onBackClick
                )
            }

            item {
                Text(
                    text = "SHIP THE APP. IMPROVE IT AFTER REAL PLAYERS USE IT.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                    color = Color(0xFF526268),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    completed: Int,
    total: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = ReaperColors.PrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        ReaperBadge(
            text = "$completed/$total",
            accentColor = if (completed == total) {
                Color(0xFF56D6A7)
            } else {
                ReaperColors.CyanGlow
            }
        )
    }
}

@Composable
private fun LaunchTaskCard(
    task: LaunchTask,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ReaperCard(
        accentColor = if (checked) {
            Color(0xFF56D6A7)
        } else {
            ReaperColors.BorderInactive
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF56D6A7),
                    uncheckedColor = ReaperColors.SecondaryText,
                    checkmarkColor = Color(0xFF001014)
                )
            )

            Spacer(modifier = Modifier.padding(horizontal = 6.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    color = ReaperColors.PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.description,
                    color = ReaperColors.SecondaryText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}