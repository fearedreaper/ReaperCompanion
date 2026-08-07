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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.MatchCoachRecommendation
import com.example.reapercompanion.models.Perk
import java.util.Locale

@Composable
fun MatchCoachResultScreen(
    recommendation: MatchCoachRecommendation,
    onBackClick: () -> Unit,
    onAnalyzeAnotherClick: () -> Unit
) {
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

    val threatColor = when (
        recommendation.threatLevel.uppercase()
    ) {
        "LOW" -> Color(0xFF56D6A7)
        "MODERATE" -> Color(0xFFFFC857)
        "HIGH" -> Color(0xFFFF8A4C)
        "EXTREME" -> Color(0xFFFF5A5A)
        else -> ReaperColors.CyanGlow
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
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = stringResource(R.string.match_coach_title),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.CyanGlow,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = localizedMatchTitle(recommendation.title),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.PrimaryText,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "${localizedMatchKillerName(recommendation.opponentName)} • ${localizedMatchMapName(recommendation.mapName)}",
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                MatchCoachOverviewPanel(
                    recommendation = recommendation,
                    threatColor = threatColor
                )
            }

            item {
                MatchCoachSummaryPanel(
                    summary = localizedMatchSummary(recommendation),
                    threatColor = threatColor
                )
            }

            item {
                MatchCoachSectionTitle(
                    text = stringResource(R.string.match_coach_result_recommended_perks)
                )
            }

            item {
                MatchCoachPerkGrid(
                    perks = recommendation.recommendedPerks,
                    onPerkClick = { perk ->
                        openedPerk = perk
                    }
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = stringResource(R.string.match_coach_result_chase_plan),
                    lines = recommendation.chaseAdvice.map(::localizedMatchAdvice),
                    accentColor = Color(0xFFFF784F),
                    bullet = "›"
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = stringResource(R.string.match_coach_result_objective_plan),
                    lines = recommendation.objectiveAdvice.map(::localizedMatchAdvice),
                    accentColor = ReaperColors.CyanGlow,
                    bullet = "✓"
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = stringResource(R.string.match_coach_result_endgame_plan),
                    lines = recommendation.endgameAdvice.map(::localizedMatchAdvice),
                    accentColor = Color(0xFFB26BFF),
                    bullet = "›"
                )
            }

            item {
                MatchCoachAdvicePanel(
                    title = stringResource(R.string.match_coach_result_watch_out),
                    lines = recommendation.warnings.map(::localizedMatchAdvice),
                    accentColor = Color(0xFFFF5A5A),
                    bullet = "!"
                )
            }

            item {
                Button(
                    onClick = onAnalyzeAnotherClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ReaperColors.CyanGlow,
                        contentColor = Color(0xFF001014)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.match_coach_result_analyze_another),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            item {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    )
                ) {
                    Text(
                        text = stringResource(R.string.match_coach_result_back_dbd),
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

private fun isSpanishMatchCoach(): Boolean =
    Locale.getDefault().language == "es"

private fun localizedMatchTitle(
    title: String
): String {
    if (!isSpanishMatchCoach()) return title
    return if (title == "Survivor Match Plan") {
        "Plan de partida de Superviviente"
    } else {
        title
    }
}

private fun localizedThreat(
    threat: String
): String {
    if (!isSpanishMatchCoach()) return threat.uppercase()
    return when (threat.uppercase()) {
        "LOW" -> "BAJA"
        "MODERATE" -> "MODERADA"
        "HIGH" -> "ALTA"
        "EXTREME" -> "EXTREMA"
        else -> threat.uppercase()
    }
}

private fun localizedDifficulty(
    difficulty: String
): String {
    if (!isSpanishMatchCoach()) return difficulty.uppercase()
    return when (difficulty.lowercase()) {
        "easy" -> "FÁCIL"
        "medium" -> "MEDIA"
        "hard" -> "DIFÍCIL"
        "expert" -> "EXPERTA"
        else -> difficulty.uppercase()
    }
}

private fun localizedMatchSummary(
    recommendation: MatchCoachRecommendation
): String {
    if (!isSpanishMatchCoach()) {
        return recommendation.summary
    }

    val killer = localizedMatchKillerName(recommendation.opponentName)
    val map = localizedMatchMapName(recommendation.mapName)
    val threat = localizedThreat(recommendation.threatLevel).lowercase()
    val difficulty = localizedDifficulty(recommendation.difficulty).lowercase()

    return "Reaper ha preparado tu plan contra $killer en $map. " +
            "La amenaza es $threat y la dificultad estimada es $difficulty. " +
            "Prioriza rutas seguras, mantén la presión sobre los generadores y adapta rescates y final de partida al poder del Asesino y a la geometría del mapa."
}

private fun localizedMatchAdvice(
    text: String
): String {
    if (!isSpanishMatchCoach()) return text
    return spanishMatchAdvice[text] ?: text
}

private fun localizedMatchPerkName(
    name: String
): String {
    if (!isSpanishMatchCoach()) return name
    return spanishMatchPerkNames[name] ?: name
}

private fun localizedMatchKillerName(
    killer: String
): String {
    if (!isSpanishMatchCoach()) return killer
    return spanishMatchKillerNames[killer] ?: killer
}

private fun localizedMatchMapName(
    map: String
): String {
    if (!isSpanishMatchCoach()) return map
    return spanishMatchMapNames[map] ?: map
}

private val spanishMatchPerkNames = mapOf(
    "Windows of Opportunity" to "Oportunidades",
    "Lithe" to "Agilidad",
    "Resilience" to "Resiliencia",
    "Adrenaline" to "Adrenalina",
    "Deja Vu" to "Déjà Vu",
    "We'll Make It" to "Lo Conseguiremos",
    "Kindred" to "Afinidad",
    "Quick & Quiet" to "Velocidad Silenciosa",
    "Lightweight" to "De Pies Ligeros",
    "Distortion" to "Distorsión",
    "Iron Will" to "Voluntad de Hierro",
    "Balanced Landing" to "Caída Equilibrada",
    "Bond" to "Vínculo"
)

private val spanishMatchKillerNames = mapOf(
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
    "The Houndmaster" to "La Maestra de Sabuesos",
    "The Ghoul" to "El Ghoul",
    "The Animatronic" to "El Animatrónico",
    "The Krasue" to "La Krasue",
    "The First" to "El Primero",
    "The Slasher" to "El Slasher",
    "Unknown Killer" to "Asesino desconocido"
)

private val spanishMatchMapNames = mapOf(
    "Coal Tower" to "Torre de Carbón",
    "Groaning Storehouse" to "Almacén Gimiente",
    "Ironworks of Misery" to "Herrería de la Miseria",
    "Shelter Woods" to "Bosque Refugio",
    "Suffocation Pit" to "Foso de Asfixia",
    "Azarov's Resting Place" to "Lugar de Descanso de Azarov",
    "Blood Lodge" to "Refugio Sangriento",
    "Gas Heaven" to "Paraíso de Gas",
    "Wreckers' Yard" to "Desguace",
    "Wretched Shop" to "Taller Miserable",
    "Fractured Cowshed" to "Establo Fracturado",
    "Rancid Abattoir" to "Matadero Rancio",
    "Rotten Fields" to "Campos Podridos",
    "The Thompson House" to "Casa Thompson",
    "Torment Creek" to "Arroyo del Tormento",
    "Disturbed Ward" to "Pabellón Perturbado",
    "Father Campbell's Chapel" to "Capilla del Padre Campbell",
    "Lampkin Lane" to "Calle Lampkin",
    "The Pale Rose" to "La Rosa Pálida",
    "Grim Pantry" to "Despensa Sombría",
    "Treatment Theatre" to "Teatro de Tratamiento",
    "Mother's Dwelling" to "Morada de la Madre",
    "The Temple of Purgation" to "Templo de la Purgación",
    "Badham Preschool" to "Preescolar Badham",
    "The Game" to "El Juego",
    "Family Residence" to "Residencia Familiar",
    "Sanctum of Wrath" to "Santuario de la Ira",
    "Mount Ormond Resort" to "Complejo del Monte Ormond",
    "Ormond Lake Mine" to "Mina del Lago Ormond",
    "The Underground Complex" to "Complejo Subterráneo",
    "Dead Dawg Saloon" to "Salón Dead Dawg",
    "Midwich Elementary School" to "Escuela Primaria Midwich",
    "Raccoon City Police Station" to "Comisaría de Raccoon City",
    "Raccoon City Police Station East Wing" to "Comisaría de Raccoon City - Ala Este",
    "Raccoon City Police Station West Wing" to "Comisaría de Raccoon City - Ala Oeste",
    "Eyrie of Crows" to "Nido de Cuervos",
    "Dead Sands" to "Arenas Muertas",
    "Garden of Joy" to "Jardín de la Alegría",
    "Greenville Square" to "Plaza Greenville",
    "Freddy Fazbear's Pizza" to "Freddy Fazbear's Pizza",
    "Fallen Refuge" to "Refugio Caído",
    "The Shattered Square" to "La Plaza Destrozada",
    "Forgotten Ruins" to "Ruinas Olvidadas",
    "Toba Landing" to "Desembarco Toba",
    "Nostromo Wreckage" to "Restos del Nostromo",
    "Trickster's Delusion" to "Delirio del Traicionero",
    "Unknown Realm" to "Reino desconocido"
)

private val spanishMatchAdvice = mapOf(
    "Break line of sight before she begins charging a blink." to "Rompe la línea de visión antes de que empiece a cargar un parpadeo.",
    "Change direction after disappearing behind walls." to "Cambia de dirección después de desaparecer detrás de paredes.",
    "Avoid straight routes through open areas." to "Evita rutas rectas por zonas abiertas.",
    "Use unpredictable pathing rather than relying only on pallets." to "Usa rutas impredecibles en vez de depender solo de los pallets.",
    "Spread out so one blink chain cannot pressure several Survivors." to "Sepárense para que una cadena de parpadeos no pueda presionar a varios Supervivientes.",
    "Prioritize generators near strong sightline blockers." to "Prioriza generadores cerca de obstáculos que bloqueen bien la visión.",
    "Open gates carefully because she can cross the map quickly." to "Abre las puertas con cuidado porque puede cruzar el mapa rápidamente.",
    "Do not group tightly around a hooked teammate." to "No se agrupen demasiado alrededor de un compañero enganchado.",
    "Traditional looping is less reliable against blink mobility." to "El looping tradicional es menos fiable contra la movilidad de parpadeo.",
    "Predictable healing locations are easy to revisit." to "Los lugares de curación predecibles son fáciles de volver a presionar.",

    "Keep solid cover between yourself and the Killer." to "Mantén cobertura sólida entre tú y el Asesino.",
    "Do not vault predictably while the ranged attack is readied." to "No saltes de forma predecible mientras el ataque a distancia esté preparado.",
    "Change timing at pallets instead of dropping instantly." to "Cambia el timing en los pallets en vez de soltarlos de inmediato.",
    "Avoid long, exposed sightlines." to "Evita líneas de visión largas y expuestas.",
    "Favor generators with nearby walls and tall cover." to "Prioriza generadores con paredes y cobertura alta cerca.",
    "Move early when the terror cue approaches an exposed objective." to "Muévete pronto cuando la señal de terror se acerque a un objetivo expuesto.",
    "Use cover while opening gates." to "Usa cobertura mientras abres las puertas.",
    "Approach rescues from protected angles." to "Acércate a los rescates desde ángulos protegidos.",
    "Windows and pallets can become easy ranged-hit opportunities." to "Ventanas y pallets pueden convertirse en oportunidades fáciles para ataques a distancia.",
    "Open spaces heavily favor the Killer." to "Los espacios abiertos favorecen mucho al Asesino.",

    "Use tight corners to interrupt the power path." to "Usa esquinas cerradas para interrumpir la trayectoria del poder.",
    "Avoid long straight lanes." to "Evita pasillos largos y rectos.",
    "Rotate toward cluttered tiles with several obstacles." to "Rota hacia zonas cargadas de obstáculos.",
    "Change direction before the Killer reaches full speed." to "Cambia de dirección antes de que el Asesino alcance su velocidad máxima.",
    "Repair away from open travel lanes." to "Repara lejos de rutas abiertas de desplazamiento.",
    "Split pressure because the Killer rotates quickly." to "Divide la presión porque el Asesino rota rápidamente.",
    "Expect fast gate rotations." to "Espera rotaciones rápidas entre las puertas.",
    "Leave unsafe rescues early rather than feeding a snowball." to "Abandona pronto los rescates inseguros en vez de alimentar un efecto bola de nieve.",
    "Isolated Survivors can be reached very quickly." to "Los Supervivientes aislados pueden ser alcanzados muy rápido.",
    "Open areas reduce your reaction time." to "Las zonas abiertas reducen tu tiempo de reacción.",

    "Position near safety before the Killer fully commits." to "Colócate cerca de una zona segura antes de que el Asesino se comprometa.",
    "Use multiple escape routes instead of one obvious lane." to "Usa varias rutas de escape en vez de una sola ruta obvia.",
    "Check behind you frequently and listen for subtle audio cues." to "Mira detrás de ti con frecuencia y escucha señales de audio sutiles.",
    "Repair from positions with strong visibility." to "Repara desde posiciones con buena visibilidad.",
    "Avoid generators surrounded by dead zones." to "Evita generadores rodeados de zonas muertas.",
    "Check for hidden gate pressure before committing." to "Comprueba si hay presión oculta en las puertas antes de comprometerte.",
    "Do not heal in exposed areas." to "No cures en zonas expuestas.",
    "The first hit may arrive with little warning." to "El primer golpe puede llegar con muy poco aviso.",
    "Poor pre-positioning is difficult to recover from." to "Es difícil recuperarse de un mal posicionamiento previo.",

    "Watch narrow paths, pallets, windows, and stairs." to "Vigila caminos estrechos, pallets, ventanas y escaleras.",
    "Leave a loop when the Killer begins controlling both exits." to "Abandona un loop cuando el Asesino empiece a controlar ambas salidas.",
    "Remember unsafe zones and rotate elsewhere." to "Recuerda las zonas inseguras y rota hacia otro lugar.",
    "Pressure generators away from fortified areas." to "Presiona generadores lejos de zonas fortificadas.",
    "Disable or counter map objects only when it is safe." to "Desactiva o contrarresta objetos del mapa solo cuando sea seguro.",
    "Check exit routes before committing." to "Comprueba las rutas de salida antes de comprometerte.",
    "Approach basement rescues carefully." to "Acércate con cuidado a los rescates del sótano.",
    "Previously safe routes may become controlled later." to "Rutas que antes eran seguras pueden quedar controladas después.",
    "Basement and narrow loops can become severe traps." to "El sótano y los loops estrechos pueden convertirse en trampas graves.",

    "Identify which part of the power is limiting your route." to "Identifica qué parte del poder está limitando tu ruta.",
    "Leave compromised loops before every option is removed." to "Abandona los loops comprometidos antes de que desaparezcan todas las opciones.",
    "Do not repeat the same counterplay in every chase." to "No repitas el mismo counterplay en cada persecución.",
    "Assign one teammate to interact with the Killer's secondary objective." to "Asigna a un compañero para interactuar con el objetivo secundario del Asesino.",
    "Keep generator pressure spread across the map." to "Mantén la presión de generadores repartida por el mapa.",
    "Resolve lingering power effects before a rescue." to "Resuelve los efectos persistentes del poder antes de un rescate.",
    "Confirm teammate positions before committing." to "Confirma la posición de tus compañeros antes de comprometerte.",
    "Ignoring the secondary objective can create team-wide pressure." to "Ignorar el objetivo secundario puede generar presión sobre todo el equipo.",
    "Grouping often increases the power's value." to "Agruparse suele aumentar el valor del poder.",

    "Identify your next safe tile before the chase reaches you." to "Identifica tu próxima zona segura antes de que la persecución te alcance.",
    "Avoid dropping strong pallets without forcing commitment." to "Evita gastar pallets fuertes sin obligar al Asesino a comprometerse.",
    "Break line of sight whenever possible." to "Rompe la línea de visión siempre que sea posible.",
    "Spread the team across multiple generators." to "Reparte al equipo entre varios generadores.",
    "Avoid creating an easy three-generator cluster." to "Evita crear un three-gen fácil de defender.",
    "Confirm teammate locations before a late rescue." to "Confirma la ubicación de tus compañeros antes de un rescate tardío.",
    "Leave when another rescue would only give the Killer more downs." to "Vete cuando otro rescate solo vaya a darle más derribos al Asesino.",
    "Do not repeat the same looping pattern." to "No repitas el mismo patrón de looping.",
    "Avoid actions in locations with only one escape route." to "Evita acciones en lugares con una sola ruta de escape.",

    "Plan the next structure before crossing open ground." to "Planea la siguiente estructura antes de cruzar terreno abierto.",
    "Use solid cover to break line of sight." to "Usa cobertura sólida para romper la línea de visión.",
    "Avoid long straight routes against ranged or mobility Killers." to "Evita rutas largas y rectas contra Asesinos de distancia o movilidad.",
    "Complete exposed generators while the Killer is occupied." to "Completa generadores expuestos mientras el Asesino esté ocupado.",
    "Prevent a dangerous final three-generator cluster." to "Evita un three-gen peligroso al final.",
    "Locate both exit gates before the final generator completes." to "Localiza ambas puertas de salida antes de completar el último generador.",
    "Open sightlines reduce reaction time." to "Las líneas de visión abiertas reducen el tiempo de reacción.",
    "Outer areas can become severe dead zones." to "Las zonas exteriores pueden convertirse en zonas muertas severas.",

    "Use rooms and corners to break line of sight." to "Usa habitaciones y esquinas para romper la línea de visión.",
    "Memorize stairs, corridors, and major landmarks." to "Memoriza escaleras, pasillos y puntos de referencia importantes.",
    "Avoid long hallways without a nearby side route." to "Evita pasillos largos sin una ruta lateral cercana.",
    "Track generators by landmark rooms." to "Ubica los generadores usando habitaciones de referencia.",
    "Split the team across separate sections." to "Divide al equipo entre secciones separadas.",
    "Begin moving toward an exit before the final generator completes." to "Empieza a moverte hacia una salida antes de completar el último generador.",
    "Navigation mistakes cost critical time." to "Los errores de navegación cuestan tiempo crítico.",
    "Stealth Killers gain value from blocked visibility." to "Los Asesinos de sigilo obtienen valor cuando la visibilidad está bloqueada.",

    "Connect major structures instead of repeating one loop." to "Conecta estructuras importantes en vez de repetir un solo loop.",
    "Leave a building early when both exits become controlled." to "Abandona un edificio pronto cuando ambas salidas queden controladas.",
    "Preserve strong windows and pallets for later chases." to "Conserva ventanas y pallets fuertes para persecuciones posteriores.",
    "Complete difficult building generators early." to "Completa pronto los generadores difíciles dentro de edificios.",
    "Spread generator progress between major structures." to "Reparte el progreso de generadores entre las estructuras principales.",
    "Use buildings to conceal gate and rescue approaches." to "Usa edificios para ocultar aproximaciones a puertas y rescates.",
    "Buildings can become traps after key resources are spent." to "Los edificios pueden convertirse en trampas después de gastar recursos clave.",
    "Routes between structures may be exposed." to "Las rutas entre estructuras pueden quedar expuestas.",

    "Use elevation changes to interrupt pursuit." to "Usa cambios de altura para interrumpir la persecución.",
    "Learn the safest stairs, drops, and floor connections." to "Aprende las escaleras, caídas y conexiones entre pisos más seguras.",
    "Avoid becoming trapped on the wrong level." to "Evita quedar atrapado en el nivel equivocado.",
    "Track which floor teammates occupy." to "Controla en qué piso están tus compañeros.",
    "Complete difficult central generators early." to "Completa pronto los generadores centrales difíciles.",
    "Leave lower or upper sections before endgame pressure closes in." to "Abandona las zonas superiores o inferiores antes de que cierre la presión de final de partida.",
    "Wrong-level rotations waste valuable time." to "Las rotaciones al nivel equivocado desperdician tiempo valioso.",
    "Narrow stair routes can be controlled easily." to "Las rutas de escaleras estrechas se controlan con facilidad.",

    "Identify the next safe tile before leaving the current one." to "Identifica la siguiente zona segura antes de abandonar la actual.",
    "Preserve the strongest central resources." to "Conserva los recursos centrales más fuertes.",
    "Spread generator pressure across the map." to "Reparte la presión de generadores por el mapa.",
    "Track the final three generators before endgame." to "Controla los tres generadores finales antes del endgame.",
    "Confirm gate locations before the final generator completes." to "Confirma la ubicación de las puertas antes de completar el último generador.",
    "Repeated routes become predictable." to "Las rutas repetidas se vuelven predecibles.",
    "Careless pallet use creates dead zones." to "El uso descuidado de pallets crea zonas muertas.",

    "Repair separate generators so one chase does not interrupt the team." to "Repara generadores separados para que una persecución no interrumpa a todo el equipo.",
    "Heal before the final rescue when time allows." to "Cura antes del rescate final cuando haya tiempo.",
    "Avoid giving the Killer unnecessary late-game trades." to "Evita regalar al Asesino intercambios innecesarios al final de la partida.",

    "Move through classrooms instead of staying in long hallways." to "Muévete por las aulas en vez de permanecer en pasillos largos.",
    "Change floors after breaking line of sight when stairs are nearby." to "Cambia de piso después de romper la línea de visión cuando haya escaleras cerca.",
    "Avoid waiting at pallets because blink pressure ignores traditional loop safety." to "Evita esperar en pallets porque la presión de parpadeo ignora la seguridad tradicional del loop.",
    "Double back only after the Nurse loses visual information." to "Haz un cambio de dirección solo después de que la Enfermera pierda información visual.",
    "Prioritize generators inside rooms with several exits." to "Prioriza generadores dentro de habitaciones con varias salidas.",
    "Avoid repairing in long hallways with no nearby cover." to "Evita reparar en pasillos largos sin cobertura cercana.",
    "Track which floor the Nurse is pressuring before committing to a rescue." to "Controla qué piso está presionando la Enfermera antes de comprometerte con un rescate.",
    "Begin moving toward an exit gate before the final generator completes." to "Empieza a moverte hacia una puerta antes de que se complete el último generador.",
    "Long hallways strongly favor blink prediction." to "Los pasillos largos favorecen mucho la predicción de parpadeos.",
    "Poor staircase knowledge can trap Survivors on the wrong floor." to "Un mal conocimiento de las escaleras puede atrapar a los Supervivientes en el piso equivocado.",

    "Route toward the main building before the chase begins." to "Rota hacia el edificio principal antes de que empiece la persecución.",
    "Use elevation changes to disrupt blink timing." to "Usa cambios de altura para alterar el timing del parpadeo.",
    "Cross open snowfields only when the Nurse is committed elsewhere." to "Cruza los campos de nieve abiertos solo cuando la Enfermera esté comprometida en otro lugar.",
    "Break line of sight with exterior walls before changing direction." to "Rompe la línea de visión con paredes exteriores antes de cambiar de dirección.",
    "Spread generators across both sides of the map." to "Reparte los generadores entre ambos lados del mapa.",
    "Avoid clustering the team around the main building." to "Evita agrupar al equipo alrededor del edificio principal.",
    "Repair exposed generators only when the Nurse is far away." to "Repara generadores expuestos solo cuando la Enfermera esté lejos.",
    "Use the wide map to separate gate pressure." to "Usa el tamaño del mapa para separar la presión sobre las puertas.",
    "Do not heal directly beside an exit switch." to "No cures directamente junto a un interruptor de salida.",
    "Open snowfields provide very little protection." to "Los campos de nieve abiertos ofrecen muy poca protección.",
    "Long-distance blink rotations can punish isolated Survivors." to "Los parpadeos de larga distancia pueden castigar a Supervivientes aislados.",

    "Route toward tall cover before the Huntress begins winding up." to "Rota hacia cobertura alta antes de que la Cazadora empiece a preparar el hacha.",
    "Avoid predictable vaults in open structures." to "Evita saltos predecibles en estructuras abiertas.",
    "Use the main building to deny long throwing lanes." to "Usa el edificio principal para negar líneas largas de lanzamiento.",
    "Change direction after moving behind solid cover." to "Cambia de dirección después de pasar detrás de cobertura sólida.",
    "Prioritize generators near the main building and tall obstacles." to "Prioriza generadores cerca del edificio principal y obstáculos altos.",
    "Avoid repairing exposed edge generators without an escape plan." to "Evita reparar generadores expuestos del borde sin un plan de escape.",
    "Spread out so one hatchet angle cannot pressure multiple Survivors." to "Sepárense para que un solo ángulo de hacha no pueda presionar a varios Supervivientes.",
    "Use nearby cover while opening gates." to "Usa cobertura cercana mientras abres las puertas.",
    "Approach rescues from angles that block direct hatchet paths." to "Acércate a los rescates desde ángulos que bloqueen trayectorias directas de hacha.",
    "Open snowfields heavily favor ranged attacks." to "Los campos de nieve abiertos favorecen mucho los ataques a distancia.",
    "Standing still at gate switches creates easy hatchet opportunities." to "Quedarse quieto en los interruptores de salida crea oportunidades fáciles para el hacha."
)

@Composable
private fun MatchCoachOverviewPanel(
    recommendation: MatchCoachRecommendation,
    threatColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = threatColor.copy(alpha = 0.75f)
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
                            threatColor.copy(alpha = 0.19f),
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
                ReaperScoreGauge(
                    score = recommendation.score,
                    size = 140.dp
                )

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(R.string.match_coach_result_threat_level),
                        color = ReaperColors.SecondaryText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.3.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = localizedThreat(recommendation.threatLevel),
                        color = threatColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(17.dp))

                    Text(
                        text = stringResource(R.string.match_coach_result_difficulty),
                        color = ReaperColors.SecondaryText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.1.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = localizedDifficulty(recommendation.difficulty),
                        color = ReaperColors.PrimaryText,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchCoachSummaryPanel(
    summary: String,
    threatColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.2.dp,
            color = threatColor.copy(alpha = 0.55f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Text(
                text = stringResource(R.string.match_coach_result_assessment),
                color = threatColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp
            )

            Spacer(modifier = Modifier.height(11.dp))

            Text(
                text = summary,
                color = ReaperColors.PrimaryText,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun MatchCoachPerkGrid(
    perks: List<Perk>,
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
                    MatchCoachPerkCard(
                        perk = perk,
                        onClick = {
                            onPerkClick(perk)
                        },
                        modifier = Modifier.weight(1f)
                    )
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
private fun MatchCoachPerkCard(
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
            color = ReaperColors.CyanGlow.copy(alpha = 0.55f)
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

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = localizedMatchPerkName(perk.name),
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.PrimaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(7.dp))

            PerkCategoryBadge(
                category = perk.category
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.match_coach_result_tap_details),
                modifier = Modifier.fillMaxWidth(),
                color = ReaperColors.CyanGlow,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.9.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MatchCoachAdvicePanel(
    title: String,
    lines: List<String>,
    accentColor: Color,
    bullet: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.52f)
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
                color = accentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (lines.isEmpty()) {
                Text(
                    text = stringResource(R.string.match_coach_result_no_advice),
                    color = ReaperColors.SecondaryText,
                    fontSize = 14.sp
                )
            } else {
                lines.forEach { line ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {
                        Text(
                            text = bullet,
                            color = accentColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(end = 10.dp)
                        )

                        Text(
                            text = line,
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            lineHeight = 21.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchCoachSectionTitle(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        color = ReaperColors.PrimaryText,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
}