package com.example.reapercompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.reapercompanion.livecontent.LiveContentScreen
import com.example.reapercompanion.models.BuildRecommendation
import com.example.reapercompanion.database.MatchCoachEngine
import com.example.reapercompanion.models.BuildStyle
import com.example.reapercompanion.models.MatchCoachRecommendation
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.screens.BuildAroundPerkScreen
import com.example.reapercompanion.screens.BuildAroundResultScreen
import com.example.reapercompanion.screens.BuildComparisonScreen
import com.example.reapercompanion.screens.BuildResultScreen
import com.example.reapercompanion.screens.BuildStylePickerScreen
import com.example.reapercompanion.screens.DeadByDaylightScreen
import com.example.reapercompanion.screens.FavoritesScreen
import com.example.reapercompanion.screens.HomeScreen
import com.example.reapercompanion.screens.KillerBuildResultScreen
import com.example.reapercompanion.screens.KillerScreen
import com.example.reapercompanion.screens.MatchCoachResultScreen
import com.example.reapercompanion.screens.MatchCoachKillerScreen
import com.example.reapercompanion.screens.MatchCoachMapScreen
import com.example.reapercompanion.screens.MatchCoachSummaryScreen
import com.example.reapercompanion.screens.MetaBuildsScreen
import com.example.reapercompanion.screens.RandomBuildScreen
import com.example.reapercompanion.screens.SplashScreen
import com.example.reapercompanion.screens.SurvivorScreen
import com.example.reapercompanion.ui.theme.ReaperCompanionTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReaperCompanionTheme {

                var showSplash by remember {
                    mutableStateOf(true)
                }

                var currentPage by remember {
                    mutableStateOf("home")
                }

                var selectedGoal by remember {
                    mutableStateOf("")
                }

                var selectedKillerGoal by remember {
                    mutableStateOf("")
                }

                var selectedBuildAroundPerk by remember {
                    mutableStateOf<Perk?>(null)
                }

                var selectedBuildStyle by remember {
                    mutableStateOf(BuildStyle.BALANCED)
                }

                var comparisonBuildA by remember {
                    mutableStateOf<BuildRecommendation?>(null)
                }

                var comparisonBuildB by remember {
                    mutableStateOf<BuildRecommendation?>(null)
                }

                var comparisonStyleA by remember {
                    mutableStateOf<BuildStyle?>(null)
                }

                var comparisonStyleB by remember {
                    mutableStateOf<BuildStyle?>(null)
                }

                var matchCoachRecommendation by remember {
                    mutableStateOf<MatchCoachRecommendation?>(null)
                }

                var selectedMatchCoachKiller by remember {
                    mutableStateOf("")
                }

                var selectedMatchCoachMap by remember {
                    mutableStateOf("")
                }

                LaunchedEffect(Unit) {
                    delay(2000)
                    showSplash = false
                }

                BackHandler(enabled = currentPage != "home") {
                    currentPage = when (currentPage) {
                        "results" -> "survivor"
                        "killerResults" -> "killer"
                        "buildComparison" -> "buildAroundResults"
                        "buildAroundResults" -> "buildStylePicker"
                        "buildStylePicker" -> "buildAround"
                        "buildAround" -> "deadByDaylight"
                        "matchCoachResults" -> "matchCoachSummary"
                        "matchCoachSummary" -> "matchCoachMap"
                        "matchCoachMap" -> "matchCoachKiller"
                        "matchCoachKiller" -> "deadByDaylight"
                        "randomBuild" -> "deadByDaylight"
                        "metaBuilds" -> "deadByDaylight"
                        "survivor" -> "deadByDaylight"
                        "killer" -> "deadByDaylight"
                        "favorites" -> "deadByDaylight"
                        "reaperLive" -> "home"
                        "deadByDaylight" -> "home"
                        else -> "home"
                    }
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    when (currentPage) {

                        "home" -> HomeScreen(
                            onDeadByDaylightClick = {
                                currentPage = "deadByDaylight"
                            },
                            onReaperLiveClick = {
                                currentPage = "reaperLive"
                            }
                        )

                        "reaperLive" -> LiveContentScreen(
                            onBackClick = {
                                currentPage = "home"
                            }
                        )

                        "deadByDaylight" -> DeadByDaylightScreen(
                            onBackClick = {
                                currentPage = "home"
                            },
                            onSurvivorClick = {
                                currentPage = "survivor"
                            },
                            onKillerClick = {
                                currentPage = "killer"
                            },
                            onBuildAroundClick = {
                                comparisonBuildA = null
                                comparisonBuildB = null
                                comparisonStyleA = null
                                comparisonStyleB = null
                                selectedBuildAroundPerk = null

                                currentPage = "buildAround"
                            },
                            onMatchCoachClick = {
                                matchCoachRecommendation = null
                                selectedMatchCoachKiller = ""
                                selectedMatchCoachMap = ""
                                currentPage = "matchCoachKiller"
                            },
                            onRandomBuildClick = {
                                currentPage = "randomBuild"
                            },
                            onMetaBuildsClick = {
                                currentPage = "metaBuilds"
                            },
                            onFavoritesClick = {
                                currentPage = "favorites"
                            }
                        )

                        "survivor" -> SurvivorScreen(
                            onBackClick = {
                                currentPage = "deadByDaylight"
                            },
                            onGoalClick = { goal ->
                                selectedGoal = goal
                                currentPage = "results"
                            }
                        )

                        "killer" -> KillerScreen(
                            onBackClick = {
                                currentPage = "deadByDaylight"
                            },
                            onGoalClick = { goal ->
                                selectedKillerGoal = goal
                                currentPage = "killerResults"
                            }
                        )

                        "buildAround" -> BuildAroundPerkScreen(
                            onBackClick = {
                                currentPage = "deadByDaylight"
                            },
                            onPerkClick = { perk ->
                                selectedBuildAroundPerk = perk

                                comparisonBuildA = null
                                comparisonBuildB = null
                                comparisonStyleA = null
                                comparisonStyleB = null

                                currentPage = "buildStylePicker"
                            }
                        )

                        "buildStylePicker" -> {
                            val selectedPerk = selectedBuildAroundPerk

                            if (selectedPerk != null) {
                                BuildStylePickerScreen(
                                    selectedPerk = selectedPerk,
                                    onBackClick = {
                                        currentPage = "buildAround"
                                    },
                                    onStyleClick = { style ->
                                        selectedBuildStyle = style
                                        currentPage = "buildAroundResults"
                                    }
                                )
                            } else {
                                currentPage = "buildAround"
                            }
                        }

                        "buildAroundResults" -> {
                            val selectedPerk = selectedBuildAroundPerk

                            if (selectedPerk != null) {
                                BuildAroundResultScreen(
                                    selectedPerk = selectedPerk,
                                    selectedStyle = selectedBuildStyle,
                                    comparisonBuildExists =
                                        comparisonBuildA != null,
                                    onCompareClick = { generatedBuild ->
                                        if (comparisonBuildA == null) {
                                            comparisonBuildA = generatedBuild
                                            comparisonStyleA =
                                                selectedBuildStyle

                                            currentPage =
                                                "buildStylePicker"
                                        } else {
                                            comparisonBuildB =
                                                generatedBuild

                                            comparisonStyleB =
                                                selectedBuildStyle

                                            currentPage =
                                                "buildComparison"
                                        }
                                    },
                                    onBackClick = {
                                        currentPage =
                                            "deadByDaylight"
                                    },
                                    onChooseAnotherClick = {
                                        comparisonBuildA = null
                                        comparisonBuildB = null
                                        comparisonStyleA = null
                                        comparisonStyleB = null

                                        currentPage = "buildAround"
                                    }
                                )
                            } else {
                                currentPage = "buildAround"
                            }
                        }

                        "buildComparison" -> {
                            val firstBuild = comparisonBuildA
                            val secondBuild = comparisonBuildB

                            if (
                                firstBuild != null &&
                                secondBuild != null
                            ) {
                                BuildComparisonScreen(
                                    firstBuild = firstBuild,
                                    secondBuild = secondBuild,
                                    onBackClick = {
                                        currentPage =
                                            "buildAroundResults"
                                    },
                                    onChooseFirstClick = {
                                        selectedBuildStyle =
                                            comparisonStyleA
                                                ?: BuildStyle.BALANCED

                                        currentPage =
                                            "buildAroundResults"
                                    },
                                    onChooseSecondClick = {
                                        selectedBuildStyle =
                                            comparisonStyleB
                                                ?: BuildStyle.BALANCED

                                        currentPage =
                                            "buildAroundResults"
                                    }
                                )
                            } else {
                                currentPage = "buildAroundResults"
                            }
                        }

                        "matchCoachKiller" -> MatchCoachKillerScreen(
                            onBackClick = {
                                currentPage = "deadByDaylight"
                            },
                            onKillerSelected = { killer ->
                                selectedMatchCoachKiller = killer
                                selectedMatchCoachMap = ""
                                currentPage = "matchCoachMap"
                            }
                        )

                        "matchCoachMap" -> MatchCoachMapScreen(
                            selectedKiller = selectedMatchCoachKiller,
                            onBackClick = {
                                currentPage = "matchCoachKiller"
                            },
                            onMapSelected = { map ->
                                selectedMatchCoachMap = map
                                currentPage = "matchCoachSummary"
                            }
                        )

                        "matchCoachSummary" -> MatchCoachSummaryScreen(
                            selectedKiller = selectedMatchCoachKiller,
                            selectedMap = selectedMatchCoachMap,
                            onBackClick = {
                                currentPage = "matchCoachMap"
                            },
                            onAnalyzeClick = { opponentName, mapName ->
                                matchCoachRecommendation =
                                    MatchCoachEngine.generateRecommendation(
                                        opponentName = opponentName,
                                        mapName = mapName
                                    )

                                currentPage = "matchCoachResults"
                            }
                        )

                        "matchCoachResults" -> {
                            val recommendation = matchCoachRecommendation

                            if (recommendation != null) {
                                MatchCoachResultScreen(
                                    recommendation = recommendation,
                                    onBackClick = {
                                        currentPage = "deadByDaylight"
                                    },
                                    onAnalyzeAnotherClick = {
                                        matchCoachRecommendation = null
                                        selectedMatchCoachKiller = ""
                                        selectedMatchCoachMap = ""
                                        currentPage = "matchCoachKiller"
                                    }
                                )
                            } else {
                                currentPage = "matchCoachKiller"
                            }
                        }

                        "results" -> BuildResultScreen(
                            selectedGoal = selectedGoal,
                            onBackClick = {
                                currentPage = "survivor"
                            },
                            onGenerateAgainClick = {
                                currentPage = "survivor"
                            }
                        )

                        "killerResults" -> KillerBuildResultScreen(
                            selectedGoal = selectedKillerGoal,
                            onBackClick = {
                                currentPage = "killer"
                            },
                            onGenerateAgainClick = {
                                currentPage = "killer"
                            }
                        )

                        "randomBuild" -> RandomBuildScreen(
                            onBackClick = {
                                currentPage = "deadByDaylight"
                            }
                        )

                        "metaBuilds" -> MetaBuildsScreen(
                            onBackClick = {
                                currentPage = "deadByDaylight"
                            }
                        )

                        "favorites" -> FavoritesScreen(
                            onBackClick = {
                                currentPage = "deadByDaylight"
                            }
                        )
                    }
                }
            }
        }
    }
}