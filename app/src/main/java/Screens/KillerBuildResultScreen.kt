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
import com.example.reapercompanion.database.KillerBuildDatabase
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.FavoriteBuild
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.storage.FavoritesStorage
import java.util.Locale

@Composable
fun KillerBuildResultScreen(
    selectedGoal: String,
    onBackClick: () -> Unit,
    onGenerateAgainClick: () -> Unit
) {
    val context = LocalContext.current
    val build = KillerBuildDatabase.getBuild(selectedGoal)
    val localizedBuild = localizeKillerBuildForDisplay(build)

    val favoriteBuild = FavoriteBuild(
        name = build.name,
        goal = localizedBuild.goal,
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
            }

            item {
                KillerPortraitHeader(
                    goal = localizedBuild.goal
                )
            }

            item {
                Text(
                    text = stringResource(R.string.killer_result_your_build),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFF6B6B),
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
                KillerScorePanel(
                    score = build.score,
                    difficulty = localizedBuild.difficulty
                )
            }

            item {
                KillerSectionHeading(
                    text = stringResource(R.string.killer_result_recommended_perks)
                )
            }

            item {
                KillerPerkGrid(
                    perks = build.perks,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                KillerInformationPanel(
                    title = stringResource(R.string.killer_result_strengths),
                    lines = localizedBuild.strengths.map { strength ->
                        "✓  $strength"
                    }
                )
            }

            item {
                KillerAlternativePerksPanel(
                    perks = build.alternatives,
                    onPerkClick = { perk ->
                        selectedPerk = perk
                    }
                )
            }

            item {
                KillerExplanationPanel(
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
                            Color(0xFFE24A4A)
                        },
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isSaved) {
                            stringResource(R.string.killer_result_saved)
                        } else {
                            stringResource(R.string.killer_result_save_build)
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
                        containerColor = Color(0xFF7A2027),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.killer_result_generate_another),
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
                        color = Color(0x99E24A4A)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.killer_result_back_goals),
                        color = Color(0xFFFF6B6B),
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

private fun localizedKillerPerkName(
    englishName: String
): String {
    if (Locale.getDefault().language != "es") {
        return englishName
    }

    return spanishKillerPerkNames[englishName] ?: englishName
}

private fun localizeKillerBuildForDisplay(
    build: com.example.reapercompanion.models.KillerBuildRecommendation
): com.example.reapercompanion.models.KillerBuildRecommendation {
    if (Locale.getDefault().language != "es") {
        return build
    }

    return build.copy(
        name = spanishKillerBuildText[build.name] ?: build.name,
        goal = spanishKillerBuildText[build.goal] ?: build.goal,
        difficulty = spanishKillerBuildText[build.difficulty] ?: build.difficulty,
        strengths = build.strengths.map { strength ->
            spanishKillerBuildText[strength] ?: strength
        },
        explanation =
            spanishKillerBuildText[build.explanation]
                ?: build.explanation
    )
}

private val spanishKillerPerkNames = mapOf(
    "Scourge Hook: Pain Resonance" to "Gancho Flagelante: Resonancia del Dolor",
    "Pop Goes the Weasel" to "Pop Goes the Weasel",
    "Corrupt Intervention" to "Intervención Corrupta",
    "Deadlock" to "Punto Muerto",
    "Bamboozle" to "Engaño",
    "Enduring" to "Resistencia",
    "Spirit Fury" to "Furia Espiritual",
    "Brutal Strength" to "Fuerza Brutal",
    "Nowhere to Hide" to "Ningún Lugar Donde Esconderse",
    "Lethal Pursuer" to "Perseguidor Letal",
    "Barbecue & Chilli" to "Barbacoa y Chile",
    "A Nurse's Calling" to "Vocación de Enfermera",
    "Tinkerer" to "Manitas",
    "Trail of Torment" to "Rastro de Tormento",
    "Dark Devotion" to "Devoción Oscura",
    "Hex: Ruin" to "Maleficio: Ruina",
    "Hex: Undying" to "Maleficio: Inmortal",
    "Hex: Devour Hope" to "Maleficio: Devorar Esperanza",
    "No Way Out" to "Sin Salida",
    "Remember Me" to "Recuérdame",
    "Blood Warden" to "Guardián de Sangre"
)

private val spanishKillerBuildText = mapOf(
    "GEN REGRESSION" to "REGRESIÓN DE GENERADORES",
    "ANTI-LOOP" to "ANTI-LOOP",
    "AURA READING" to "LECTURA DE AURAS",
    "STEALTH" to "SIGILO",
    "HEX BUILD" to "CONFIGURACIÓN DE MALEFICIOS",
    "ENDGAME" to "FINAL DE PARTIDA",
    "BEGINNER" to "PRINCIPIANTE",
    "MEME BUILD" to "CONFIGURACIÓN MEME",

    "Generator Destroyer" to "Destructor de generadores",
    "Loop Breaker" to "Rompe-loops",
    "Relentless Hunter" to "Cazador implacable",
    "Silent Terror" to "Terror silencioso",
    "Totem Nightmare" to "Pesadilla de tótems",
    "No Escape" to "Sin escape",
    "Reliable Hunter" to "Cazador fiable",
    "Trial of Confusion" to "Prueba de confusión",

    "Easy" to "Fácil",
    "Medium" to "Media",
    "Hard" to "Difícil",

    "Excellent generator slowdown" to "Excelente ralentización de generadores",
    "Strong early-game control" to "Gran control al inicio de la partida",
    "Reliable pressure" to "Presión fiable",
    "Works on nearly every Killer" to "Funciona con casi cualquier Asesino",

    "Ends chases quickly" to "Termina las persecuciones rápidamente",
    "Destroys pallets efficiently" to "Destruye pallets con eficiencia",
    "Punishes greedy looping" to "Castiga los loops codiciosos",
    "Maintains chase momentum" to "Mantiene el ritmo de la persecución",

    "Constant Survivor tracking" to "Rastreo constante de Supervivientes",
    "Excellent map awareness" to "Excelente control de información del mapa",
    "Strong snowball potential" to "Gran potencial de efecto bola de nieve",
    "Beginner friendly" to "Apto para principiantes",

    "Creates surprise attacks" to "Crea ataques sorpresa",
    "Hides the Terror Radius" to "Oculta el radio de terror",
    "Provides generator information" to "Proporciona información de generadores",
    "Strong on mobile Killers" to "Fuerte con Asesinos móviles",

    "Powerful passive slowdown" to "Potente ralentización pasiva",
    "Dangerous late-game potential" to "Potencial peligroso al final de la partida",
    "Forces Survivors to hunt Totems" to "Obliga a los Supervivientes a buscar Tótems",
    "Creates unpredictable matches" to "Crea partidas impredecibles",

    "Strong exit-gate control" to "Gran control de las puertas de salida",
    "Punishes premature gate opening" to "Castiga abrir las puertas demasiado pronto",
    "Creates late-game comeback potential" to "Crea potencial de remontada al final",
    "Delays Survivor escapes" to "Retrasa las escapadas de los Supervivientes",

    "Simple effects" to "Efectos simples",
    "Useful on many Killers" to "Útil con muchos Asesinos",
    "Improves chase consistency" to "Mejora la consistencia en persecuciones",
    "Provides clear information" to "Proporciona información clara",

    "Unpredictable Terror Radius" to "Radio de terror impredecible",
    "Surprise attacks" to "Ataques sorpresa",
    "Chaotic endgame moments" to "Momentos caóticos al final de la partida",
    "Entertaining high-risk plays" to "Jugadas entretenidas de alto riesgo",

    "This build controls generator progress from the opening moments of the trial. Corrupt Intervention slows the early game, while Pain Resonance, Pop Goes the Weasel, and Deadlock punish generator progress throughout the match." to
            "Esta configuración controla el progreso de los generadores desde los primeros momentos de la prueba. Intervención Corrupta ralentiza el inicio, mientras que Resonancia del Dolor, Pop Goes the Weasel y Punto Muerto castigan el progreso de los generadores durante toda la partida.",

    "This build reduces the value Survivors gain from pallets and windows. Bamboozle blocks strong vaults, while Enduring and Spirit Fury punish pallet stuns and Brutal Strength clears resources faster." to
            "Esta configuración reduce el valor que los Supervivientes obtienen de pallets y ventanas. Engaño bloquea saltos fuertes, mientras Resistencia y Furia Espiritual castigan los aturdimientos con pallet y Fuerza Brutal elimina recursos más rápido.",

    "This build provides information during every stage of the trial. Lethal Pursuer starts the first chase quickly, while the other perks reveal Survivors after hooks, generator kicks, and healing actions." to
            "Esta configuración proporciona información durante cada fase de la prueba. Perseguidor Letal inicia rápidamente la primera persecución, mientras los otros perks revelan Supervivientes tras ganchos, patadas a generadores y acciones de curación.",

    "This build repeatedly hides your Terror Radius and creates unpredictable approaches. Tinkerer and Trail of Torment grant stealth, Dark Devotion causes confusion, and Nowhere to Hide exposes nearby targets." to
            "Esta configuración oculta repetidamente tu radio de terror y crea aproximaciones impredecibles. Manitas y Rastro de Tormento otorgan sigilo, Devoción Oscura genera confusión y Ningún Lugar Donde Esconderse revela objetivos cercanos.",

    "Ruin pressures unattended generators while Undying helps protect your Hex setup. Devour Hope can become extremely dangerous if Survivors fail to cleanse it, and No Way Out provides additional endgame control." to
            "Ruina presiona los generadores desatendidos mientras Inmortal ayuda a proteger tu configuración de maleficios. Devorar Esperanza puede volverse extremadamente peligroso si los Supervivientes no lo limpian, y Sin Salida aporta control adicional al final de la partida.",

    "This build becomes strongest after the generators are completed. No Way Out and Remember Me delay the gates, while Blood Warden can trap Survivors inside the trial and turn a losing match into a comeback." to
            "Esta configuración se vuelve más fuerte después de completar los generadores. Sin Salida y Recuérdame retrasan las puertas, mientras Guardián de Sangre puede atrapar a los Supervivientes dentro de la prueba y convertir una partida perdida en una remontada.",

    "This beginner-friendly build uses straightforward perks that provide value without complicated conditions. It improves pallet interactions, generator awareness, and Survivor tracking." to
            "Esta configuración para principiantes usa perks sencillos que aportan valor sin condiciones complicadas. Mejora las interacciones con pallets, la información de generadores y el rastreo de Supervivientes.",

    "This chaotic build focuses on confusing Survivors with stealth and unexpected late-game pressure. It is less consistent than a competitive build, but it can produce memorable matches." to
            "Esta configuración caótica se centra en confundir a los Supervivientes con sigilo y presión inesperada al final de la partida. Es menos consistente que una configuración competitiva, pero puede producir partidas memorables."
)

@Composable
private fun KillerScorePanel(
    score: Int,
    difficulty: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = Color(0x99E24A4A)
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
                            Color(0xFF47171C),
                            Color(0xFF251316),
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
                        text = stringResource(R.string.killer_result_score),
                        color = Color(0xFFFF6B6B),
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
                        text = stringResource(R.string.killer_result_out_of_100),
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(R.string.killer_result_difficulty),
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
                        color = Color(0xFFFF6B6B),
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun KillerPerkGrid(
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
                    KillerPerkCard(
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
private fun KillerPerkCard(
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
            color = Color(0x99E24A4A)
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
                text = localizedKillerPerkName(perk.name),
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.killer_result_tap_details),
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFF6B6B),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun KillerAlternativePerksPanel(
    perks: List<Perk>,
    onPerkClick: (Perk) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x99E24A4A)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = stringResource(R.string.killer_result_alternative_perks),
                color = Color(0xFFFF6B6B),
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
                        color = Color(0x66E24A4A)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF171012)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            text = localizedKillerPerkName(perk.name),
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "${perk.owner} • ${stringResource(R.string.killer_result_tap_details_sentence)}",
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
private fun KillerInformationPanel(
    title: String,
    lines: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x99E24A4A)
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
                color = Color(0xFFFF6B6B),
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
private fun KillerExplanationPanel(
    explanation: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0x99E24A4A)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = stringResource(R.string.killer_result_why_build_works),
                color = Color(0xFFFF6B6B),
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
private fun KillerSectionHeading(
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