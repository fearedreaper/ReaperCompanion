package com.example.reapercompanion.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.database.KillerPerkDatabase
import com.example.reapercompanion.database.PerkDatabase
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperCard
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperListCard
import com.example.reapercompanion.design.ReaperPrimaryButton
import com.example.reapercompanion.design.ReaperSecondaryButton
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole

private data class MetaBuildDefinition(
    val id: String,
    val title: String,
    val description: String,
    val role: PerkRole,
    val accentColor: Color,
    val difficulty: String,
    val score: Int,
    val perkIds: List<String>,
    val whyItWorks: String,
    val bestFor: List<String>,
    val watchOutFor: List<String>
)

@Composable
fun MetaBuildsScreen(
    onBackClick: () -> Unit
) {
    var selectedBuild by remember {
        mutableStateOf<MetaBuildDefinition?>(null)
    }

    var openedPerk by remember {
        mutableStateOf<Perk?>(null)
    }

    openedPerk?.let { perk ->
        PerkDetailsDialog(
            perk = perk,
            onDismiss = {
                openedPerk = null
            }
        )
    }

    if (selectedBuild == null) {
        MetaBuildLibrary(
            onBackClick = onBackClick,
            onBuildClick = { build ->
                selectedBuild = build
            }
        )
    } else {
        MetaBuildDetails(
            build = selectedBuild!!,
            onBackClick = {
                selectedBuild = null
            },
            onPerkClick = { perk ->
                openedPerk = perk
            },
            onChooseAnotherClick = {
                selectedBuild = null
            }
        )
    }
}

@Composable
private fun MetaBuildLibrary(
    onBackClick: () -> Unit,
    onBuildClick: (MetaBuildDefinition) -> Unit
) {
    AppBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                ReaperHeader(
                    title = "META BUILDS",
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Curated loadouts built around clear roles and reliable synergy.",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    lineHeight = 21.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "REAPER CURATED",
                    title = "Proven Build Archetypes",
                    body =
                        "Choose a playstyle to see the full loadout, why it works, and what can counter it.",
                    badge = "V1 META"
                )
            }

            items(metaBuilds.size) { index ->
                val build = metaBuilds[index]

                ReaperListCard(
                    title = build.title,
                    description = build.description,
                    onClick = {
                        onBuildClick(build)
                    },
                    accentColor = build.accentColor,
                    trailingText = build.score.toString()
                )
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK TO DEAD BY DAYLIGHT",
                    onClick = onBackClick
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun MetaBuildDetails(
    build: MetaBuildDefinition,
    onBackClick: () -> Unit,
    onPerkClick: (Perk) -> Unit,
    onChooseAnotherClick: () -> Unit
) {
    val perks = remember(build.id) {
        build.perkIds.mapNotNull { perkId ->
            allPerksForRole(build.role).firstOrNull { perk ->
                perk.id == perkId
            }
        }
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
                    title = build.title,
                    onBackClick = onBackClick,
                    accentColor = build.accentColor
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = "META LOADOUT",
                    title = build.title,
                    body = build.description,
                    accentColor = build.accentColor,
                    badge = if (build.role == PerkRole.SURVIVOR) {
                        "SURVIVOR"
                    } else {
                        "KILLER"
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetaStatCard(
                        label = "REAPER SCORE",
                        value = build.score.toString(),
                        accentColor = build.accentColor,
                        modifier = Modifier.weight(1f)
                    )

                    MetaStatCard(
                        label = "DIFFICULTY",
                        value = build.difficulty,
                        accentColor = build.accentColor,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                ReaperCard(
                    accentColor = build.accentColor,
                    contentPadding = 16.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "BUILD STRENGTH",
                            color = ReaperColors.SecondaryText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = "${build.score}%",
                            color = build.accentColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { build.score / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = build.accentColor,
                        trackColor = ReaperColors.BorderInactive
                    )
                }
            }

            item {
                Text(
                    text = "LOADOUT",
                    color = ReaperColors.PrimaryText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            item {
                MetaPerkGrid(
                    perks = perks,
                    accentColor = build.accentColor,
                    onPerkClick = onPerkClick
                )
            }

            item {
                ReaperCard(
                    accentColor = build.accentColor
                ) {
                    Text(
                        text = "REAPER ANALYSIS",
                        color = build.accentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.7.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text =
                            "This loadout earns its score through reliable synergy. " +
                                    build.whyItWorks,
                        color = ReaperColors.PrimaryText,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            item {
                MetaBulletPanel(
                    title = "BEST FOR",
                    entries = build.bestFor,
                    accentColor = build.accentColor
                )
            }

            item {
                MetaBulletPanel(
                    title = "WATCH OUT FOR",
                    entries = build.watchOutFor,
                    accentColor = Color(0xFFFF6A6A)
                )
            }

            item {
                ReaperPrimaryButton(
                    text = "CHOOSE ANOTHER BUILD",
                    onClick = onChooseAnotherClick,
                    accentColor = build.accentColor
                )
            }

            item {
                ReaperSecondaryButton(
                    text = "BACK",
                    onClick = onBackClick,
                    accentColor = build.accentColor
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun MetaPerkGrid(
    perks: List<Perk>,
    accentColor: Color,
    onPerkClick: (Perk) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        perks.chunked(2).forEach { rowPerks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowPerks.forEach { perk ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onPerkClick(perk)
                            },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ReaperColors.CardBackground
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OnlinePerkImage(
                                perk = perk,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = perk.name,
                                modifier = Modifier.fillMaxWidth(),
                                color = ReaperColors.PrimaryText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            ReaperBadge(
                                text = perk.category.name.replace("_", " "),
                                accentColor = accentColor
                            )
                        }
                    }
                }

                if (rowPerks.size == 1) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MetaBulletPanel(
    title: String,
    entries: List<String>,
    accentColor: Color
) {
    ReaperCard(
        accentColor = accentColor
    ) {
        Text(
            text = title,
            color = accentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.7.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        entries.forEach { entry ->
            Text(
                text = "• $entry",
                color = ReaperColors.PrimaryText,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
private fun MetaStatCard(
    label: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    ReaperCard(
        modifier = modifier,
        accentColor = accentColor,
        contentPadding = 16.dp
    ) {
        Text(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            color = ReaperColors.SecondaryText,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            modifier = Modifier.fillMaxWidth(),
            color = accentColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
    }
}

private fun allPerksForRole(
    role: PerkRole
): List<Perk> {
    return when (role) {
        PerkRole.SURVIVOR ->
            PerkDatabase.getSurvivorPerks()

        PerkRole.KILLER ->
            KillerPerkDatabase.allPerks
    }
}

private val metaBuilds = listOf(
    MetaBuildDefinition(
        id = "chase_specialist",
        title = "CHASE SPECIALIST",
        description = "Extend chases, route efficiently, and punish predictable pressure.",
        role = PerkRole.SURVIVOR,
        accentColor = ReaperColors.CyanGlow,
        difficulty = "MEDIUM",
        score = 94,
        perkIds = listOf(
            "windows_of_opportunity",
            "lithe",
            "resilience",
            "adrenaline"
        ),
        whyItWorks =
            "Windows of Opportunity improves routing, Lithe creates separation after a vault, Resilience increases action speed while injured, and Adrenaline rewards surviving until the final generator.",
        bestFor = listOf(
            "Players who enjoy long chases",
            "Maps with connected windows and pallets",
            "Solo queue players who need reliable information"
        ),
        watchOutFor = listOf(
            "Anti-loop Killers",
            "Exhaustion management",
            "Dead zones after resources are spent"
        )
    ),
    MetaBuildDefinition(
        id = "generator_pressure",
        title = "GENERATOR PRESSURE",
        description = "Push objectives quickly while protecting the final generator spread.",
        role = PerkRole.SURVIVOR,
        accentColor = Color(0xFF56D6A7),
        difficulty = "HARD",
        score = 91,
        perkIds = listOf(
            "deja_vu",
            "prove_thyself",
            "hyperfocus",
            "stake_out"
        ),
        whyItWorks =
            "Deja Vu prevents dangerous generator clusters, Prove Thyself improves cooperative repair efficiency, and the Stake Out plus Hyperfocus pairing rewards strong skill-check execution.",
        bestFor = listOf(
            "Objective-focused players",
            "Coordinated teammates",
            "Breaking dangerous three-generator setups"
        ),
        watchOutFor = listOf(
            "Heavy regression builds",
            "Frequent chase interruption",
            "Poor Great Skill Check consistency"
        )
    ),
    MetaBuildDefinition(
        id = "solo_queue",
        title = "SOLO QUEUE SURVIVOR",
        description = "Reliable information, safer rescues, and independent survival.",
        role = PerkRole.SURVIVOR,
        accentColor = Color(0xFF67B7FF),
        difficulty = "EASY",
        score = 93,
        perkIds = listOf(
            "kindred",
            "bond",
            "off_the_record",
            "adrenaline"
        ),
        whyItWorks =
            "Kindred and Bond replace missing communication, Off the Record helps after an unhook, and Adrenaline gives a powerful endgame reset.",
        bestFor = listOf(
            "Solo queue",
            "Players without voice communication",
            "Rescue timing and teammate awareness"
        ),
        watchOutFor = listOf(
            "Aura blocking",
            "Early elimination before endgame",
            "Overcommitting to unsafe rescues"
        )
    ),
    MetaBuildDefinition(
        id = "stealth_escape",
        title = "STEALTH ESCAPE",
        description = "Break tracking, hide your route, and disappear after contact.",
        role = PerkRole.SURVIVOR,
        accentColor = Color(0xFFB26BFF),
        difficulty = "MEDIUM",
        score = 88,
        perkIds = listOf(
            "distortion",
            "quick_and_quiet",
            "dance_with_me",
            "lucky_break"
        ),
        whyItWorks =
            "Distortion protects against aura reading, while Quick & Quiet, Dance With Me, and Lucky Break suppress the information Killers normally use to continue a chase.",
        bestFor = listOf(
            "Breaking line of sight",
            "Indoor maps",
            "Players who prefer evasion over looping"
        ),
        watchOutFor = listOf(
            "Killers with strong sound tracking",
            "Open maps",
            "Running out of Distortion value"
        )
    ),
    MetaBuildDefinition(
        id = "anti_tunnel",
        title = "ANTI-TUNNEL",
        description = "Punish repeated pressure and create extra chances after an unhook.",
        role = PerkRole.SURVIVOR,
        accentColor = Color(0xFFFF6B9D),
        difficulty = "MEDIUM",
        score = 92,
        perkIds = listOf(
            "off_the_record",
            "decisive_strike",
            "dead_hard",
            "unbreakable"
        ),
        whyItWorks =
            "Off the Record protects after an unhook, Decisive Strike punishes immediate pickup pressure, Dead Hard can extend a chase, and Unbreakable answers slugging.",
        bestFor = listOf(
            "High-pressure matches",
            "Players frequently targeted after unhook",
            "Second-chance focused play"
        ),
        watchOutFor = listOf(
            "Perk activation requirements",
            "Killers waiting out protection",
            "Using resources too early"
        )
    ),
    MetaBuildDefinition(
        id = "slowdown_control",
        title = "KILLER SLOWDOWN",
        description = "Control generator progress and force Survivors into inefficient repairs.",
        role = PerkRole.KILLER,
        accentColor = Color(0xFFFF5A5A),
        difficulty = "EASY",
        score = 95,
        perkIds = listOf(
            "scourge_hook_pain_resonance",
            "pop_goes_the_weasel",
            "corrupt_intervention",
            "deadlock"
        ),
        whyItWorks =
            "Corrupt Intervention stabilizes the opening, Pain Resonance and Pop reward hooks with regression, and Deadlock slows the next objective after a generator is completed.",
        bestFor = listOf(
            "Most Killer powers",
            "Learning macro pressure",
            "Protecting a strong generator spread"
        ),
        watchOutFor = listOf(
            "Scourge Hook placement",
            "Failing to convert pressure into hooks",
            "Overcommitting to one chase"
        )
    ),
    MetaBuildDefinition(
        id = "aura_hunter",
        title = "AURA HUNTER",
        description = "Locate targets quickly and reduce downtime between chases.",
        role = PerkRole.KILLER,
        accentColor = Color(0xFFFF8A65),
        difficulty = "MEDIUM",
        score = 90,
        perkIds = listOf(
            "lethal_pursuer",
            "barbecue_and_chilli",
            "nowhere_to_hide",
            "a_nurses_calling"
        ),
        whyItWorks =
            "Lethal Pursuer gives immediate direction, Barbecue & Chilli finds distant targets after hooks, Nowhere to Hide checks nearby generator zones, and A Nurse's Calling punishes healing.",
        bestFor = listOf(
            "Mobile Killers",
            "Aggressive chase chaining",
            "Players who dislike searching"
        ),
        watchOutFor = listOf(
            "Distortion",
            "Lockers",
            "Overreliance on aura information"
        )
    ),
    MetaBuildDefinition(
        id = "endgame_lockdown",
        title = "ENDGAME LOCKDOWN",
        description = "Turn the final generator into a dangerous second phase.",
        role = PerkRole.KILLER,
        accentColor = Color(0xFFFFC857),
        difficulty = "HARD",
        score = 89,
        perkIds = listOf(
            "no_way_out",
            "remember_me",
            "blood_warden",
            "hex_devour_hope"
        ),
        whyItWorks =
            "No Way Out and Remember Me delay escape, Blood Warden can trap Survivors after a late hook, and Devour Hope creates pressure before the endgame begins.",
        bestFor = listOf(
            "Killers with strong late-game mobility",
            "Players who enjoy comeback pressure",
            "Punishing greedy gate play"
        ),
        watchOutFor = listOf(
            "Hex removal",
            "Losing too much pressure before endgame",
            "Blood Warden timing"
        )
    )
)