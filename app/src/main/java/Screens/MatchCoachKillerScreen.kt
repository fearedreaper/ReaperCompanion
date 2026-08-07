package com.example.reapercompanion.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.database.MatchCoachEngine
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperListCard
import java.util.Locale

@Composable
fun MatchCoachKillerScreen(
    onBackClick: () -> Unit,
    onKillerSelected: (String) -> Unit
) {
    val killers = remember {
        MatchCoachEngine.getSupportedKillers()
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
                Spacer(modifier = Modifier.height(16.dp))

                ReaperHeader(
                    title = stringResource(R.string.match_coach_title),
                    onBackClick = onBackClick,
                    accentColor = Color(0xFFFF6B6B)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.match_coach_step_1),
                    color = Color(0xFFFF6B6B),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.match_coach_choose_killer),
                    color = ReaperColors.PrimaryText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.match_coach_choose_killer_body),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = stringResource(R.string.match_coach_title),
                    title = stringResource(R.string.match_coach_know_threat),
                    body = stringResource(R.string.match_coach_know_threat_body),
                    accentColor = Color(0xFFFF6B6B),
                    badge = stringResource(
                        R.string.match_coach_killer_count,
                        killers.size
                    )
                )
            }

            items(
                items = killers,
                key = { killer ->
                    killer
                }
            ) { killer ->
                ReaperListCard(
                    title = localizedKillerDisplayName(killer),
                    description = killerSubtitle(killer),
                    onClick = {
                        // Preserve the canonical English Killer name for MatchCoachEngine.
                        onKillerSelected(killer)
                    },
                    accentColor = Color(0xFFFF6B6B),
                    trailingText = "›"
                )
            }

            item {
                Text(
                    text = stringResource(R.string.match_coach_tap_killer),
                    modifier = Modifier
                        .padding(
                            top = 6.dp,
                            bottom = 24.dp
                        ),
                    color = ReaperColors.SecondaryText,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun killerSubtitle(
    killer: String
): String {
    val normalized = killer.lowercase()

    return when {
        normalized.contains("nurse") ->
            stringResource(R.string.match_coach_killer_subtitle_blink)

        normalized.contains("blight") ||
                normalized.contains("hillbilly") ||
                normalized.contains("oni") ||
                normalized.contains("mastermind") ||
                normalized.contains("houndmaster") ||
                normalized.contains("slasher") ->
            stringResource(R.string.match_coach_killer_subtitle_speed)

        normalized.contains("huntress") ||
                normalized.contains("deathslinger") ||
                normalized.contains("trickster") ||
                normalized.contains("artist") ||
                normalized.contains("executioner") ||
                normalized.contains("nemesis") ||
                normalized.contains("unknown") ||
                normalized.contains("animatronic") ||
                normalized.contains("first") ->
            stringResource(R.string.match_coach_killer_subtitle_ranged)

        normalized.contains("spirit") ||
                normalized.contains("wraith") ||
                normalized.contains("ghost face") ||
                normalized.contains("shape") ||
                normalized.contains("pig") ||
                normalized.contains("onryo") ||
                normalized.contains("good guy") ||
                normalized.contains("dark lord") ||
                normalized.contains("ghoul") ->
            stringResource(R.string.match_coach_killer_subtitle_stealth)

        normalized.contains("trapper") ||
                normalized.contains("hag") ||
                normalized.contains("knight") ||
                normalized.contains("skull merchant") ||
                normalized.contains("singularity") ||
                normalized.contains("xenomorph") ->
            stringResource(R.string.match_coach_killer_subtitle_control)

        normalized.contains("doctor") ||
                normalized.contains("clown") ||
                normalized.contains("legion") ||
                normalized.contains("plague") ||
                normalized.contains("cenobite") ||
                normalized.contains("dredge") ||
                normalized.contains("twins") ||
                normalized.contains("nightmare") ||
                normalized.contains("demogorgon") ||
                normalized.contains("lich") ||
                normalized.contains("krasue") ->
            stringResource(R.string.match_coach_killer_subtitle_disruption)

        else ->
            stringResource(R.string.match_coach_killer_subtitle_default)
    }
}

private fun localizedKillerDisplayName(
    killer: String
): String {
    if (Locale.getDefault().language != "es") {
        return killer
    }

    return spanishKillerNames[killer] ?: killer
}

private val spanishKillerNames = mapOf(
    "The Trapper" to "El Trampero",
    "The Wraith" to "El Espectro",
    "The Hillbilly" to "El Pueblerino",
    "The Nurse" to "La Enfermera",
    "The Shape" to "La Forma",
    "The Hag" to "La Bruja",
    "The Doctor" to "El Doctor",
    "The Huntress" to "La Cazadora",
    "The Cannibal" to "El Caníbal",
    "The Nightmare" to "La Pesadilla",
    "The Pig" to "La Cerda",
    "The Clown" to "El Payaso",
    "The Spirit" to "El Espíritu",
    "The Legion" to "La Legión",
    "The Plague" to "La Plaga",
    "The Ghost Face" to "El Ghost Face",
    "The Demogorgon" to "El Demogorgon",
    "The Oni" to "El Oni",
    "The Deathslinger" to "El Arponero",
    "The Executioner" to "El Verdugo",
    "The Blight" to "La Plaga",
    "The Twins" to "Los Gemelos",
    "The Trickster" to "El Traicionero",
    "The Nemesis" to "El Némesis",
    "The Cenobite" to "El Cenobita",
    "The Artist" to "La Artista",
    "The Onryo" to "La Onryō",
    "The Dredge" to "La Draga",
    "The Mastermind" to "El Cerebro",
    "The Knight" to "El Caballero",
    "The Skull Merchant" to "La Comerciante de Calaveras",
    "The Singularity" to "La Singularidad",
    "The Xenomorph" to "El Xenomorfo",
    "The Good Guy" to "El Chico Bueno",
    "The Unknown" to "Lo Desconocido",
    "The Lich" to "El Liche",
    "The Dark Lord" to "El Señor Oscuro",
    "The Houndmaster" to "La Maestra de Sabuesos"
)