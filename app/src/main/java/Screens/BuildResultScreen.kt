package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.database.PerkDatabase
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.BuildRecommendation
import com.example.reapercompanion.models.FavoriteBuild
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.storage.FavoritesStorage
import java.util.Locale

@Composable
fun BuildResultScreen(
    selectedGoal: String,
    onBackClick: () -> Unit,
    onGenerateAgainClick: () -> Unit
) {
    val context = LocalContext.current
    val build = getBuildForGoal(selectedGoal)
    val localizedBuild = localizeBuildForDisplay(build)

    val favoriteBuild = FavoriteBuild(
        name = build.name,
        goal = build.goal,
        score = build.score,
        difficulty = localizedBuild.difficulty,
        perks = build.perks.map { it.name }
    )

    var isSaved by remember(selectedGoal) {
        mutableStateOf(
            FavoritesStorage.isFavorite(
                context = context,
                build = favoriteBuild
            )
        )
    }

    var selectedPerk by remember {
        mutableStateOf<Perk?>(null)
    }

    selectedPerk?.let { perk ->
        PerkDetailsDialog(
            perk = perk,
            onDismiss = {
                selectedPerk = null
            }
        )
    }

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = stringResource(R.string.survivor_result_your_build),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.CyanGlow,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = localizedBuild.name,
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = localizedBuild.goal,
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                ScorePanel(
                    score = build.score,
                    difficulty = localizedBuild.difficulty
                )
            }

            item {
                SectionHeading(stringResource(R.string.survivor_result_recommended_perks))
            }

            item {
                PerkGrid(
                    perks = build.perks,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                StrengthPanel(
                    strengths = localizedBuild.strengths
                )
            }

            item {
                AlternativePerksPanel(
                    perks = build.alternatives,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                ExplanationPanel(
                    explanation = localizedBuild.explanation
                )
            }

            item {
                Button(
                    onClick = {
                        if (isSaved) {
                            FavoritesStorage.deleteFavorite(
                                context = context,
                                build = favoriteBuild
                            )
                            isSaved = false
                        } else {
                            FavoritesStorage.saveFavorite(
                                context = context,
                                build = favoriteBuild
                            )
                            isSaved = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSaved) {
                            Color(0xFF167A62)
                        } else {
                            ReaperColors.CyanGlow
                        },
                        contentColor = if (isSaved) {
                            Color.White
                        } else {
                            Color(0xFF001014)
                        }
                    )
                ) {
                    Text(
                        text = if (isSaved) {
                            stringResource(R.string.survivor_result_saved)
                        } else {
                            stringResource(R.string.survivor_result_save_build)
                        },
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp
                    )
                }
            }

            item {
                Button(
                    onClick = onGenerateAgainClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ReaperColors.CyanDark,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.survivor_result_generate_another),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(17.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    )
                ) {
                    Text(
                        text = stringResource(R.string.survivor_result_back_goals),
                        color = ReaperColors.CyanGlow,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ScorePanel(
    score: Int,
    difficulty: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = ReaperColors.BorderActive
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF07343D),
                            ReaperColors.CardAvailable,
                            ReaperColors.CardBackground
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.survivor_result_score),
                        color = ReaperColors.CyanGlow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = score.toString(),
                        color = ReaperColors.PrimaryText,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = stringResource(R.string.survivor_result_out_of_100),
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(R.string.survivor_result_difficulty),
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = difficulty,
                        color = ReaperColors.PrimaryText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "★★★★☆",
                        color = ReaperColors.CyanGlow,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun PerkGrid(
    perks: List<Perk>,
    onPerkClick: (Perk) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        perks.chunked(2).forEach { perkRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                perkRow.forEach { perk ->
                    PerkCard(
                        perk = perk,
                        onClick = {
                            onPerkClick(perk)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (perkRow.size == 1) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun PerkCard(
    perk: Perk,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .pressScale()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
        ),
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = localizedPerkName(perk.name),
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.PrimaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            PerkCategoryBadge(
                category = perk.category
            )

            Text(
                text = stringResource(R.string.survivor_result_tap_details),
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.CyanGlow,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun StrengthPanel(
    strengths: List<String>
) {
    InformationPanel(
        title = stringResource(R.string.survivor_result_strengths),
        lines = strengths.map { strength ->
            "✓  $strength"
        }
    )
}

@Composable
private fun AlternativePerksPanel(
    perks: List<Perk>,
    onPerkClick: (Perk) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = stringResource(R.string.survivor_result_alternative_perks),
                color = ReaperColors.CyanGlow,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            perks.forEach { perk ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable {
                            onPerkClick(perk)
                        },
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderInactive
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0C1216)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            text = localizedPerkName(perk.name),
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "${perk.owner} • ${stringResource(R.string.survivor_result_tap_details_sentence)}",
                            color = ReaperColors.SecondaryText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InformationPanel(
    title: String,
    lines: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = title,
                color = ReaperColors.CyanGlow,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            lines.forEach { line ->
                Text(
                    text = line,
                    color = ReaperColors.PrimaryText,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun ExplanationPanel(
    explanation: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderActive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = stringResource(R.string.survivor_result_why_build_works),
                color = ReaperColors.CyanGlow,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = explanation,
                color = ReaperColors.SecondaryText,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun SectionHeading(
    text: String
) {
    Text(
        text = text,
        color = ReaperColors.PrimaryText,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
}

private fun localizedPerkName(
    englishName: String
): String {
    if (Locale.getDefault().language != "es") {
        return englishName
    }

    return spanishPerkNames[englishName] ?: englishName
}

private val spanishPerkNames = mapOf(
    "Windows of Opportunity" to "Oportunidades",
    "Lithe" to "Agilidad",
    "Resilience" to "Resiliencia",
    "Adrenaline" to "Adrenalina",
    "Finesse" to "Finura",
    "Balanced Landing" to "Caída Equilibrada",
    "Quick & Quiet" to "Velocidad Silenciosa",
    "Five Moves Ahead" to "Cinco Movimientos por Delante",

    "Deja Vu" to "Déjà Vu",
    "Prove Thyself" to "Demuestra lo que Vales",
    "Built to Last" to "Fabricado para Durar",
    "Overzealous" to "Exceso de Entusiasmo",
    "Stake Out" to "Bajo Vigilancia",
    "Hyperfocus" to "Hiperconcentración",

    "We'll Make It" to "Lo Conseguiremos",
    "Botany Knowledge" to "Conocimientos de Botánica",
    "Empathy" to "Empatía",
    "Kindred" to "Afinidad",
    "Desperate Measures" to "Medidas Desesperadas",
    "Aftercare" to "Postratamiento",
    "Babysitter" to "Canguro",
    "Leader" to "Líder",

    "Lightweight" to "De Pies Ligeros",
    "Distortion" to "Distorsión",
    "Iron Will" to "Voluntad de Hierro",
    "Dance With Me" to "Baila Conmigo",
    "Lucky Break" to "Golpe de Suerte",
    "Calm Spirit" to "Espíritu Tranquilo",
    "Urban Evasion" to "Evasión Urbana",

    "Head On" to "De Frente",
    "Flashbang" to "Granada Aturdidora",
    "Deception" to "Engaño",
    "Blast Mine" to "Mina Explosiva",
    "Diversion" to "Diversión",
    "Power Struggle" to "Lucha de Poder",
    "Chemical Trap" to "Trampa Química",

    "Off the Record" to "Extraoficial",
    "Decisive Strike" to "Golpe Decisivo",
    "Unbreakable" to "Inquebrantable",
    "Deliverance" to "Liberación",
    "Dead Hard" to "Fajador",

    "Bond" to "Vínculo"
)

private fun localizeBuildForDisplay(
    build: BuildRecommendation
): BuildRecommendation {
    if (Locale.getDefault().language != "es") {
        return build
    }

    return build.copy(
        name = spanishBuildText[build.name] ?: build.name,
        goal = spanishBuildText[build.goal] ?: build.goal,
        difficulty = spanishBuildText[build.difficulty] ?: build.difficulty,
        strengths = build.strengths.map { strength ->
            spanishBuildText[strength] ?: strength
        },
        explanation =
            spanishBuildText[build.explanation]
                ?: build.explanation
    )
}

private val spanishBuildText = mapOf(
    "LOOP BETTER" to "MEJORAR EN PERSECUCIONES",
    "RUSH GENERATORS" to "ACELERAR GENERADORES",
    "SUPPORT THE TEAM" to "APOYAR AL EQUIPO",
    "STEALTH" to "SIGILO",
    "TROLL THE KILLER" to "TROLLEAR AL ASESINO",
    "SURVIVE LONGER" to "SOBREVIVIR MÁS TIEMPO",
    "SURPRISE ME" to "SORPRÉNDEME",

    "Loop Monster" to "Monstruo de persecución",
    "Generator Specialist" to "Especialista en generadores",
    "Guardian Angel" to "Ángel guardián",
    "Vanishing Act" to "Acto de desaparición",
    "Chaos Gremlin" to "Gremlin del caos",
    "Second Chance" to "Segunda oportunidad",
    "Reaper's Choice" to "Elección de Reaper",

    "Easy" to "Fácil",
    "Medium" to "Media",
    "Hard" to "Difícil",

    "Strong chase pathing" to "Rutas fuertes durante la persecución",
    "Creates distance after vaults" to "Crea distancia después de saltos",
    "Useful in solo queue" to "Útil en Solo Queue",
    "Powerful endgame recovery" to "Recuperación potente en el final de partida",

    "Fast objective progress" to "Progreso rápido de objetivos",
    "Generator information" to "Información de generadores",
    "Beginner friendly" to "Apto para principiantes",
    "Strong coordinated repairs" to "Reparaciones coordinadas fuertes",

    "Fast healing" to "Curación rápida",
    "Strong rescue value" to "Gran valor en rescates",
    "Team information" to "Información del equipo",

    "Quiet movement" to "Movimiento silencioso",
    "Reduced tracking information" to "Menos información de rastreo",
    "Strong line-of-sight breaks" to "Fuertes rupturas de línea de visión",
    "Useful against aura reading" to "Útil contra lectura de auras",

    "Funny coordinated plays" to "Jugadas coordinadas divertidas",
    "Locker mind games" to "Mind games con taquillas",
    "Surprise saves" to "Rescates sorpresa",
    "Best with friends" to "Mejor con amigos",

    "Protection after unhook" to "Protección después del desenganche",
    "Reliable chase information" to "Información fiable de persecución",
    "Strong escape potential" to "Gran potencial de escape",
    "Good for solo queue" to "Bueno para Solo Queue",

    "Balanced utility" to "Utilidad equilibrada",
    "Good information" to "Buena información",
    "Reliable mobility" to "Movilidad fiable",
    "Team support" to "Apoyo al equipo",

    "This build helps you identify nearby resources, create distance after a fast vault, gain value while injured, and receive a powerful boost when the final generator is completed." to
            "Esta configuración te ayuda a identificar recursos cercanos, crear distancia tras un salto rápido, obtener valor mientras estás herido y recibir un potente impulso cuando se completa el último generador.",

    "This build focuses on locating important generators, improving repair efficiency, and maintaining pressure on objectives throughout the match." to
            "Esta configuración se centra en localizar generadores importantes, mejorar la eficiencia de reparación y mantener presión sobre los objetivos durante toda la partida.",

    "This support build helps you locate injured teammates, heal efficiently, make safer rescues, and provide useful information to the entire team." to
            "Esta configuración de apoyo te ayuda a localizar compañeros heridos, curar con eficiencia, realizar rescates más seguros y proporcionar información útil a todo el equipo.",

    "This stealth build reduces the information you leave behind and gives you tools for quietly breaking line of sight and disappearing during a chase." to
            "Esta configuración de sigilo reduce la información que dejas atrás y te da herramientas para romper silenciosamente la línea de visión y desaparecer durante una persecución.",

    "This build is designed for locker tricks, surprise stuns, fake movements, and funny saves. It works best with friends who can coordinate around the chaos." to
            "Esta configuración está diseñada para trucos con taquillas, aturdimientos sorpresa, movimientos falsos y rescates divertidos. Funciona mejor con amigos que puedan coordinarse alrededor del caos.",

    "This build gives you information, distance, protection after being unhooked, and a strong opportunity to escape during the final stage of the match." to
            "Esta configuración te proporciona información, distancia, protección después de ser desenganchado y una gran oportunidad de escapar durante la fase final de la partida.",

    "This balanced build provides information, chase mobility, objective guidance, and faster healing after rescues." to
            "Esta configuración equilibrada proporciona información, movilidad en persecución, orientación de objetivos y curación más rápida después de los rescates."
)

private fun getBuildForGoal(
    goal: String
): BuildRecommendation {
    return when (goal) {
        "LOOP BETTER" -> BuildRecommendation(
            name = "Loop Monster",
            goal = goal,
            score = 94,
            difficulty = "Medium",
            perks = listOf(
                PerkDatabase.windowsOfOpportunity,
                PerkDatabase.lithe,
                PerkDatabase.resilience,
                PerkDatabase.adrenaline
            ),
            alternatives = listOf(
                PerkDatabase.finesse,
                PerkDatabase.balancedLanding,
                PerkDatabase.quickAndQuiet,
                PerkDatabase.fiveMovesAhead
            ),
            strengths = listOf(
                "Strong chase pathing",
                "Creates distance after vaults",
                "Useful in solo queue",
                "Powerful endgame recovery"
            ),
            explanation =
                "This build helps you identify nearby resources, create distance after a fast vault, gain value while injured, and receive a powerful boost when the final generator is completed."
        )

        "RUSH GENERATORS" -> BuildRecommendation(
            name = "Generator Specialist",
            goal = goal,
            score = 91,
            difficulty = "Easy",
            perks = listOf(
                PerkDatabase.dejaVu,
                PerkDatabase.proveThyself,
                PerkDatabase.resilience,
                PerkDatabase.adrenaline
            ),
            alternatives = listOf(
                PerkDatabase.builtToLast,
                PerkDatabase.overzealous,
                PerkDatabase.stakeOut,
                PerkDatabase.hyperfocus
            ),
            strengths = listOf(
                "Fast objective progress",
                "Generator information",
                "Beginner friendly",
                "Strong coordinated repairs"
            ),
            explanation =
                "This build focuses on locating important generators, improving repair efficiency, and maintaining pressure on objectives throughout the match."
        )

        "SUPPORT THE TEAM" -> BuildRecommendation(
            name = "Guardian Angel",
            goal = goal,
            score = 90,
            difficulty = "Easy",
            perks = listOf(
                PerkDatabase.wellMakeIt,
                PerkDatabase.botanyKnowledge,
                PerkDatabase.empathy,
                PerkDatabase.kindred
            ),
            alternatives = listOf(
                PerkDatabase.desperateMeasures,
                PerkDatabase.aftercare,
                PerkDatabase.babysitter,
                PerkDatabase.leader
            ),
            strengths = listOf(
                "Fast healing",
                "Strong rescue value",
                "Team information",
                "Beginner friendly"
            ),
            explanation =
                "This support build helps you locate injured teammates, heal efficiently, make safer rescues, and provide useful information to the entire team."
        )

        "STEALTH" -> BuildRecommendation(
            name = "Vanishing Act",
            goal = goal,
            score = 87,
            difficulty = "Medium",
            perks = listOf(
                PerkDatabase.quickAndQuiet,
                PerkDatabase.lightweight,
                PerkDatabase.distortion,
                PerkDatabase.ironWill
            ),
            alternatives = listOf(
                PerkDatabase.danceWithMe,
                PerkDatabase.luckyBreak,
                PerkDatabase.calmSpirit,
                PerkDatabase.urbanEvasion
            ),
            strengths = listOf(
                "Quiet movement",
                "Reduced tracking information",
                "Strong line-of-sight breaks",
                "Useful against aura reading"
            ),
            explanation =
                "This stealth build reduces the information you leave behind and gives you tools for quietly breaking line of sight and disappearing during a chase."
        )

        "TROLL THE KILLER" -> BuildRecommendation(
            name = "Chaos Gremlin",
            goal = goal,
            score = 88,
            difficulty = "Hard",
            perks = listOf(
                PerkDatabase.headOn,
                PerkDatabase.quickAndQuiet,
                PerkDatabase.flashbang,
                PerkDatabase.deception
            ),
            alternatives = listOf(
                PerkDatabase.blastMine,
                PerkDatabase.diversion,
                PerkDatabase.powerStruggle,
                PerkDatabase.chemicalTrap
            ),
            strengths = listOf(
                "Funny coordinated plays",
                "Locker mind games",
                "Surprise saves",
                "Best with friends"
            ),
            explanation =
                "This build is designed for locker tricks, surprise stuns, fake movements, and funny saves. It works best with friends who can coordinate around the chaos."
        )

        "SURVIVE LONGER" -> BuildRecommendation(
            name = "Second Chance",
            goal = goal,
            score = 92,
            difficulty = "Easy",
            perks = listOf(
                PerkDatabase.offTheRecord,
                PerkDatabase.windowsOfOpportunity,
                PerkDatabase.lithe,
                PerkDatabase.adrenaline
            ),
            alternatives = listOf(
                PerkDatabase.decisiveStrike,
                PerkDatabase.unbreakable,
                PerkDatabase.deliverance,
                PerkDatabase.deadHard
            ),
            strengths = listOf(
                "Protection after unhook",
                "Reliable chase information",
                "Strong escape potential",
                "Good for solo queue"
            ),
            explanation =
                "This build gives you information, distance, protection after being unhooked, and a strong opportunity to escape during the final stage of the match."
        )

        else -> BuildRecommendation(
            name = "Reaper's Choice",
            goal = goal,
            score = 89,
            difficulty = "Medium",
            perks = listOf(
                PerkDatabase.kindred,
                PerkDatabase.lithe,
                PerkDatabase.dejaVu,
                PerkDatabase.wellMakeIt
            ),
            alternatives = listOf(
                PerkDatabase.resilience,
                PerkDatabase.distortion,
                PerkDatabase.bond,
                PerkDatabase.adrenaline
            ),
            strengths = listOf(
                "Balanced utility",
                "Good information",
                "Reliable mobility",
                "Team support"
            ),
            explanation =
                "This balanced build provides information, chase mobility, objective guidance, and faster healing after rescues."
        )
    }
}