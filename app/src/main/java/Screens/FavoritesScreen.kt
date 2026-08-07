package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.FavoriteBuild
import com.example.reapercompanion.storage.FavoritesStorage
import java.util.Locale

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var favorites by remember {
        mutableStateOf(
            FavoritesStorage.loadFavorites(context)
        )
    }

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
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.clickable(
                            onClick = onBackClick
                        ),
                        shape = CircleShape,
                        color = Color(0x1600E5FF),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0x5500E5FF)
                        )
                    ) {
                        Text(
                            text = "‹",
                            color = ReaperColors.CyanGlow,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 6.dp
                            )
                        )
                    }

                    Text(
                        text = stringResource(
                            R.string.favorites_title
                        ),
                        modifier = Modifier.weight(1f),
                        color = ReaperColors.PrimaryText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.padding(
                            horizontal = 23.dp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(
                        R.string.favorites_subtitle
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (favorites.isEmpty()) {
                item {
                    EmptyFavoritesCard()
                }
            } else {
                items(favorites) { build ->
                    FavoriteBuildCard(
                        build = build,
                        onDeleteClick = {
                            FavoritesStorage.deleteFavorite(
                                context = context,
                                build = build
                            )

                            favorites =
                                FavoritesStorage.loadFavorites(
                                    context
                                )
                        }
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
private fun EmptyFavoritesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderInactive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "☆",
                color = ReaperColors.CyanGlow,
                fontSize = 52.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(
                    R.string.favorites_empty_title
                ),
                color = ReaperColors.PrimaryText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    R.string.favorites_empty_body
                ),
                color = ReaperColors.SecondaryText,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FavoriteBuildCard(
    build: FavoriteBuild,
    onDeleteClick: () -> Unit
) {
    val displayBuild = localizeFavoriteBuild(build)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = ReaperColors.BorderActive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardAvailable
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = displayBuild.name,
                color = ReaperColors.PrimaryText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = displayBuild.goal,
                color = ReaperColors.CyanGlow,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(
                            R.string.favorites_reaper_score
                        ),
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${displayBuild.score} / 100",
                        color = ReaperColors.CyanGlow,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(
                            R.string.favorites_difficulty
                        ),
                        color = ReaperColors.SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = displayBuild.difficulty,
                        color = ReaperColors.PrimaryText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            displayBuild.perks.forEach { perk ->
                Text(
                    text = "•  $perk",
                    color = ReaperColors.PrimaryText,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(
                        vertical = 3.dp
                    )
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onDeleteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A1F26),
                    contentColor = Color(0xFFFF9AA8)
                )
            ) {
                Text(
                    text = stringResource(
                        R.string.favorites_delete_build
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun localizeFavoriteBuild(
    build: FavoriteBuild
): FavoriteBuild {
    if (Locale.getDefault().language != "es") {
        return build
    }

    return build.copy(
        name =
            spanishFavoriteText[build.name]
                ?: build.name,
        goal =
            spanishFavoriteText[build.goal]
                ?: build.goal,
        difficulty =
            spanishFavoriteText[build.difficulty]
                ?: build.difficulty,
        perks = build.perks.map { perk ->
            spanishFavoritePerkNames[perk]
                ?: perk
        }
    )
}

private val spanishFavoriteText = mapOf(

    // Survivor goals

    "LOOP BETTER" to
            "MEJORAR EN PERSECUCIONES",

    "RUSH GENERATORS" to
            "ACELERAR GENERADORES",

    "SUPPORT THE TEAM" to
            "APOYAR AL EQUIPO",

    "STEALTH" to
            "SIGILO",

    "TROLL THE KILLER" to
            "TROLLEAR AL ASESINO",

    "SURVIVE LONGER" to
            "SOBREVIVIR MÁS TIEMPO",

    "SURPRISE ME" to
            "SORPRÉNDEME",

    // Survivor build names

    "Loop Monster" to
            "Monstruo de persecución",

    "Generator Specialist" to
            "Especialista en generadores",

    "Guardian Angel" to
            "Ángel guardián",

    "Vanishing Act" to
            "Acto de desaparición",

    "Chaos Gremlin" to
            "Gremlin del caos",

    "Second Chance" to
            "Segunda oportunidad",

    "Reaper's Choice" to
            "Elección de Reaper",

    // Killer goals

    "GEN REGRESSION" to
            "REGRESIÓN DE GENERADORES",

    "ANTI-LOOP" to
            "ANTI-LOOP",

    "AURA READING" to
            "LECTURA DE AURAS",

    "HEX BUILD" to
            "BUILD DE MALEFICIOS",

    "ENDGAME" to
            "FINAL DE PARTIDA",

    "BEGINNER" to
            "PRINCIPIANTE",

    "MEME BUILD" to
            "BUILD MEME",

    // Killer build names

    "Generator Destroyer" to
            "Destructor de generadores",

    "Loop Breaker" to
            "Rompe-loops",

    "Relentless Hunter" to
            "Cazador implacable",

    "Silent Terror" to
            "Terror silencioso",

    "Totem Nightmare" to
            "Pesadilla de tótems",

    "No Escape" to
            "Sin escapatoria",

    "Reliable Hunter" to
            "Cazador fiable",

    "Trial of Confusion" to
            "Prueba de confusión",

    // Difficulty

    "Easy" to "Fácil",
    "Medium" to "Media",
    "Hard" to "Difícil",
    "Expert" to "Experta"
)

private val spanishFavoritePerkNames = mapOf(

    // Survivor perks

    "Windows of Opportunity" to
            "Oportunidades",

    "Lithe" to
            "Agilidad",

    "Resilience" to
            "Resiliencia",

    "Adrenaline" to
            "Adrenalina",

    "Finesse" to
            "Finura",

    "Balanced Landing" to
            "Caída equilibrada",

    "Quick & Quiet" to
            "Velocidad silenciosa",

    "Five Moves Ahead" to
            "Cinco movimientos por delante",

    "Déjà Vu" to
            "Déjà Vu",

    "Deja Vu" to
            "Déjà Vu",

    "Prove Thyself" to
            "Demuestra lo que vales",

    "Built to Last" to
            "Hecho para durar",

    "Overzealous" to
            "Exceso de celo",

    "Stake Out" to
            "Vigilancia",

    "Hyperfocus" to
            "Hiperconcentración",

    "We'll Make It" to
            "Lo conseguiremos",

    "Botany Knowledge" to
            "Conocimientos de botánica",

    "Empathy" to
            "Empatía",

    "Kindred" to
            "Afinidad",

    "Desperate Measures" to
            "Medidas desesperadas",

    "Aftercare" to
            "Cuidados posteriores",

    "Babysitter" to
            "Niñera",

    "Leader" to
            "Líder",

    "Lightweight" to
            "De pies ligeros",

    "Distortion" to
            "Distorsión",

    "Iron Will" to
            "Voluntad de hierro",

    "Dance With Me" to
            "Baila conmigo",

    "Lucky Break" to
            "Golpe de suerte",

    "Calm Spirit" to
            "Espíritu calmado",

    "Urban Evasion" to
            "Evasión urbana",

    "Head On" to
            "De frente",

    "Flashbang" to
            "Granada cegadora",

    "Deception" to
            "Engaño",

    "Blast Mine" to
            "Mina explosiva",

    "Diversion" to
            "Distracción",

    "Power Struggle" to
            "Lucha de poder",

    "Chemical Trap" to
            "Trampa química",

    "Off the Record" to
            "Extraoficialmente",

    "Decisive Strike" to
            "Golpe decisivo",

    "Unbreakable" to
            "Inquebrantable",

    "Bond" to
            "Vínculo",

    // Killer perks

    "Scourge Hook: Pain Resonance" to
            "Gancho Flagelante: Resonancia del dolor",

    "Pop Goes the Weasel" to
            "Pop Goes the Weasel",

    "Corrupt Intervention" to
            "Intervención corrupta",

    "Deadlock" to
            "Bloqueo",

    "Bamboozle" to
            "Engaño",

    "Enduring" to
            "Resistencia",

    "Spirit Fury" to
            "Furia espiritual",

    "Brutal Strength" to
            "Fuerza brutal",

    "Nowhere to Hide" to
            "Ningún lugar donde esconderse",

    "Lethal Pursuer" to
            "Perseguidor letal",

    "Barbecue & Chilli" to
            "Barbacoa y chile",

    "A Nurse's Calling" to
            "La llamada de una enfermera",

    "Tinkerer" to
            "Manitas",

    "Trail of Torment" to
            "Rastro de tormento",

    "Dark Devotion" to
            "Devoción oscura",

    "Hex: Ruin" to
            "Maleficio: Ruina",

    "Hex: Undying" to
            "Maleficio: Inmortal",

    "Hex: Devour Hope" to
            "Maleficio: Devorar esperanza",

    "No Way Out" to
            "Sin salida",

    "Remember Me" to
            "Recuérdame",

    "Blood Warden" to
            "Guardián de sangre"
)