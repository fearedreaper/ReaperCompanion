package com.example.reapercompanion.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.database.MapDatabase
import com.example.reapercompanion.design.ReaperBadge
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.design.ReaperHeader
import com.example.reapercompanion.design.ReaperInfoPanel
import com.example.reapercompanion.design.ReaperListCard
import java.util.Locale

@Composable
fun MatchCoachMapScreen(
    selectedKiller: String,
    onBackClick: () -> Unit,
    onMapSelected: (String) -> Unit
) {
    val maps = remember {
        MapDatabase.getAllNames()
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
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.match_coach_step_2),
                        modifier = Modifier.weight(1f),
                        color = ReaperColors.CyanGlow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )

                    ReaperBadge(
                        text = if (selectedKiller.isBlank()) {
                            stringResource(R.string.match_coach_killer_not_set)
                        } else {
                            localizedKillerName(selectedKiller)
                        },
                        accentColor = Color(0xFFFF6B6B)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.match_coach_choose_map),
                    color = ReaperColors.PrimaryText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.match_coach_map_body),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = stringResource(R.string.match_coach_selected_killer),
                    title = if (selectedKiller.isBlank()) {
                        stringResource(R.string.match_coach_no_killer_selected)
                    } else {
                        localizedKillerName(selectedKiller)
                    },
                    body = stringResource(R.string.match_coach_continue_summary),
                    accentColor = ReaperColors.CyanGlow,
                    badge = stringResource(
                        R.string.match_coach_map_count,
                        maps.size
                    )
                )
            }

            items(
                items = maps,
                key = { map ->
                    map
                }
            ) { map ->
                val mapData = remember(map) {
                    MapDatabase.get(map)
                }

                ReaperListCard(
                    title = localizedMapName(map),
                    description = localizedMapSummary(
                        englishSummary = mapData?.summary
                            ?: "Map-specific strategy and matchup planning"
                    ),
                    onClick = {
                        // Preserve canonical English map name for the engine.
                        onMapSelected(map)
                    },
                    accentColor = ReaperColors.CyanGlow,
                    trailingText = "›"
                )
            }

            item {
                Text(
                    text = stringResource(R.string.match_coach_tap_map),
                    modifier = Modifier
                        .fillMaxWidth()
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

private fun localizedKillerName(
    killer: String
): String {
    if (Locale.getDefault().language != "es") {
        return killer
    }

    return spanishKillerNames[ killer ] ?: killer
}

private fun localizedMapName(
    map: String
): String {
    if (Locale.getDefault().language != "es") {
        return map
    }

    return spanishMapNames[map] ?: map
}

private fun localizedMapSummary(
    englishSummary: String
): String {
    if (Locale.getDefault().language != "es") {
        return englishSummary
    }

    return spanishMapSummaries[englishSummary]
        ?: "Estrategia específica del mapa y planificación del enfrentamiento"
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
    "The Blight" to "El Deterioro",
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

private val spanishMapNames = mapOf(
    "Autohaven Wreckers" to "Desguace de Autohaven",
    "Coldwind Farm" to "Granja Coldwind",
    "MacMillan Estate" to "Finca MacMillan",
    "Crotus Prenn Asylum" to "Asilo Crotus Prenn",
    "Haddonfield" to "Haddonfield",
    "Backwater Swamp" to "Pantano de Aguas Estancadas",
    "Léry's Memorial Institute" to "Instituto Memorial Léry",
    "Red Forest" to "Bosque Rojo",
    "Springwood" to "Springwood",
    "Gideon Meat Plant" to "Planta Cárnica Gideon",
    "Yamaoka Estate" to "Finca Yamaoka",
    "Ormond" to "Ormond",
    "Hawkins National Laboratory" to "Laboratorio Nacional Hawkins",
    "Grave of Glenvale" to "Tumba de Glenvale",
    "Silent Hill" to "Silent Hill",
    "Raccoon City" to "Raccoon City",
    "Forsaken Boneyard" to "Osario Abandonado",
    "Withered Isle" to "Isla Marchita",
    "Dvarka Deepwood" to "Bosque Profundo de Dvarka"
)

private val spanishMapSummaries = mapOf(
    "Map-specific strategy and matchup planning" to
            "Estrategia específica del mapa y planificación del enfrentamiento"
)