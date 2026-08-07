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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.R
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
import java.util.Locale

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
                    title = stringResource(
                        R.string.meta_builds_title
                    ),
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(
                        R.string.meta_builds_subtitle
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    color = ReaperColors.SecondaryText,
                    fontSize = 15.sp,
                    lineHeight = 21.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = stringResource(
                        R.string.meta_builds_eyebrow
                    ),
                    title = stringResource(
                        R.string.meta_builds_panel_title
                    ),
                    body = stringResource(
                        R.string.meta_builds_panel_body
                    ),
                    badge = stringResource(
                        R.string.meta_builds_badge
                    )
                )
            }

            items(metaBuilds.size) { index ->
                val build = metaBuilds[index]
                val displayBuild =
                    localizeMetaBuildForDisplay(build)

                ReaperListCard(
                    title = displayBuild.title,
                    description = displayBuild.description,
                    onClick = {
                        onBuildClick(build)
                    },
                    accentColor = build.accentColor,
                    trailingText = build.score.toString()
                )
            }

            item {
                ReaperSecondaryButton(
                    text = stringResource(
                        R.string.meta_builds_back_dbd
                    ),
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

    val displayBuild =
        localizeMetaBuildForDisplay(build)

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
                    title = displayBuild.title,
                    onBackClick = onBackClick,
                    accentColor = build.accentColor
                )
            }

            item {
                ReaperInfoPanel(
                    eyebrow = stringResource(
                        R.string.meta_builds_loadout_eyebrow
                    ),
                    title = displayBuild.title,
                    body = displayBuild.description,
                    accentColor = build.accentColor,
                    badge = if (
                        build.role == PerkRole.SURVIVOR
                    ) {
                        stringResource(
                            R.string.meta_builds_survivor
                        )
                    } else {
                        stringResource(
                            R.string.meta_builds_killer
                        )
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    MetaStatCard(
                        label = stringResource(
                            R.string.meta_builds_reaper_score
                        ),
                        value = build.score.toString(),
                        accentColor = build.accentColor,
                        modifier = Modifier.weight(1f)
                    )

                    MetaStatCard(
                        label = stringResource(
                            R.string.meta_builds_difficulty
                        ),
                        value = displayBuild.difficulty,
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
                        horizontalArrangement =
                            Arrangement.SpaceBetween,
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(
                                R.string
                                    .meta_builds_build_strength
                            ),
                            color =
                                ReaperColors.SecondaryText,
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
                        progress = {
                            build.score / 100f
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = build.accentColor,
                        trackColor =
                            ReaperColors.BorderInactive
                    )
                }
            }

            item {
                Text(
                    text = stringResource(
                        R.string.meta_builds_loadout
                    ),
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
                        text = stringResource(
                            R.string.meta_builds_reaper_analysis
                        ),
                        color = build.accentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.7.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text =
                            stringResource(
                                R.string
                                    .meta_builds_analysis_prefix
                            ) +
                                    " " +
                                    displayBuild.whyItWorks,
                        color = ReaperColors.PrimaryText,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            item {
                MetaBulletPanel(
                    title = stringResource(
                        R.string.meta_builds_best_for
                    ),
                    entries = displayBuild.bestFor,
                    accentColor = build.accentColor
                )
            }

            item {
                MetaBulletPanel(
                    title = stringResource(
                        R.string.meta_builds_watch_out_for
                    ),
                    entries = displayBuild.watchOutFor,
                    accentColor = Color(0xFFFF6A6A)
                )
            }

            item {
                ReaperPrimaryButton(
                    text = stringResource(
                        R.string.meta_builds_choose_another
                    ),
                    onClick = onChooseAnotherClick,
                    accentColor = build.accentColor
                )
            }

            item {
                ReaperSecondaryButton(
                    text = stringResource(
                        R.string.meta_builds_back
                    ),
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
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
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
                            containerColor =
                                ReaperColors.CardBackground
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {
                            OnlinePerkImage(
                                perk = perk,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(10.dp)
                            )

                            Text(
                                text =
                                    localizedMetaPerkName(
                                        perk.name
                                    ),
                                modifier =
                                    Modifier.fillMaxWidth(),
                                color =
                                    ReaperColors.PrimaryText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            ReaperBadge(
                                text =
                                    localizedMetaCategory(
                                        perk.category.name
                                            .replace(
                                                "_",
                                                " "
                                            )
                                    ),
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

private fun localizeMetaBuildForDisplay(
    build: MetaBuildDefinition
): MetaBuildDefinition {
    if (Locale.getDefault().language != "es") {
        return build
    }

    return build.copy(
        title =
            spanishMetaText[build.title]
                ?: build.title,

        description =
            spanishMetaText[build.description]
                ?: build.description,

        difficulty =
            spanishMetaText[build.difficulty]
                ?: build.difficulty,

        whyItWorks =
            spanishMetaText[build.whyItWorks]
                ?: build.whyItWorks,

        bestFor = build.bestFor.map { text ->
            spanishMetaText[text] ?: text
        },

        watchOutFor =
            build.watchOutFor.map { text ->
                spanishMetaText[text] ?: text
            }
    )
}

private fun localizedMetaPerkName(
    name: String
): String {
    if (Locale.getDefault().language != "es") {
        return name
    }

    return spanishMetaPerkNames[name] ?: name
}

private fun localizedMetaCategory(
    category: String
): String {
    if (Locale.getDefault().language != "es") {
        return category
    }

    return spanishMetaCategories[category]
        ?: category
}

private val spanishMetaCategories = mapOf(
    "CHASE" to "PERSECUCIÓN",
    "INFORMATION" to "INFORMACIÓN",
    "EXHAUSTION" to "AGOTAMIENTO",
    "GENERATOR" to "GENERADOR",
    "OBJECTIVE" to "OBJETIVO",
    "HEALING" to "CURACIÓN",
    "SUPPORT" to "APOYO",
    "STEALTH" to "SIGILO",
    "SECOND CHANCE" to "SEGUNDA OPORTUNIDAD",
    "SURVIVAL" to "SUPERVIVENCIA",
    "ENDGAME" to "FINAL DE PARTIDA",
    "REGRESSION" to "REGRESIÓN",
    "SLOWDOWN" to "RALENTIZACIÓN",
    "AURA" to "AURA",
    "TRACKING" to "RASTREO",
    "HEX" to "MALEFICIO"
)

private val spanishMetaPerkNames = mapOf(

    // Survivor

    "Windows of Opportunity" to
            "Oportunidades",

    "Lithe" to
            "Agilidad",

    "Resilience" to
            "Resiliencia",

    "Adrenaline" to
            "Adrenalina",

    "Déjà Vu" to
            "Déjà Vu",

    "Deja Vu" to
            "Déjà Vu",

    "Prove Thyself" to
            "Demuestra lo que vales",

    "Hyperfocus" to
            "Hiperconcentración",

    "Stake Out" to
            "Vigilancia",

    "Kindred" to
            "Afinidad",

    "Bond" to
            "Vínculo",

    "Off the Record" to
            "Extraoficialmente",

    "Distortion" to
            "Distorsión",

    "Quick & Quiet" to
            "Velocidad silenciosa",

    "Dance With Me" to
            "Baila conmigo",

    "Lucky Break" to
            "Golpe de suerte",

    "Decisive Strike" to
            "Golpe decisivo",

    "Dead Hard" to
            "Fajador",

    "Unbreakable" to
            "Inquebrantable",

    // Killer

    "Scourge Hook: Pain Resonance" to
            "Gancho Flagelante: Resonancia del dolor",

    "Pop Goes the Weasel" to
            "Pop Goes the Weasel",

    "Corrupt Intervention" to
            "Intervención corrupta",

    "Deadlock" to
            "Bloqueo",

    "Lethal Pursuer" to
            "Perseguidor letal",

    "Barbecue & Chilli" to
            "Barbacoa y chile",

    "Nowhere to Hide" to
            "Ningún lugar donde esconderse",

    "A Nurse's Calling" to
            "La llamada de una enfermera",

    "No Way Out" to
            "Sin salida",

    "Remember Me" to
            "Recuérdame",

    "Blood Warden" to
            "Guardián de sangre",

    "Hex: Devour Hope" to
            "Maleficio: Devorar esperanza"
)

private val spanishMetaText = mapOf(

    // Difficulty

    "EASY" to
            "FÁCIL",

    "MEDIUM" to
            "MEDIA",

    "HARD" to
            "DIFÍCIL",

    // Chase Specialist

    "CHASE SPECIALIST" to
            "ESPECIALISTA EN PERSECUCIONES",

    "Extend chases, route efficiently, and punish predictable pressure." to
            "Alarga las persecuciones, usa rutas eficientes y castiga la presión predecible.",

    "Windows of Opportunity improves routing, Lithe creates separation after a vault, Resilience increases action speed while injured, and Adrenaline rewards surviving until the final generator." to
            "Oportunidades mejora tus rutas, Agilidad crea distancia después de un salto, Resiliencia aumenta la velocidad de acción mientras estás herido y Adrenalina recompensa llegar con vida hasta el último generador.",

    "Players who enjoy long chases" to
            "Jugadores que disfrutan de persecuciones largas",

    "Maps with connected windows and pallets" to
            "Mapas con ventanas y palés bien conectados",

    "Solo queue players who need reliable information" to
            "Jugadores de Solo Queue que necesitan información fiable",

    "Anti-loop Killers" to
            "Asesinos con fuertes herramientas anti-loop",

    "Exhaustion management" to
            "Gestión del Agotamiento",

    "Dead zones after resources are spent" to
            "Zonas muertas después de gastar los recursos",

    // Generator Pressure

    "GENERATOR PRESSURE" to
            "PRESIÓN DE GENERADORES",

    "Push objectives quickly while protecting the final generator spread." to
            "Avanza los objetivos rápidamente mientras proteges una distribución segura de los últimos generadores.",

    "Deja Vu prevents dangerous generator clusters, Prove Thyself improves cooperative repair efficiency, and the Stake Out plus Hyperfocus pairing rewards strong skill-check execution." to
            "Déjà Vu evita agrupaciones peligrosas de generadores, Demuestra lo que vales mejora la eficiencia de reparación en equipo y la combinación de Vigilancia con Hiperconcentración recompensa una buena ejecución de las pruebas de habilidad.",

    "Objective-focused players" to
            "Jugadores centrados en objetivos",

    "Coordinated teammates" to
            "Compañeros coordinados",

    "Breaking dangerous three-generator setups" to
            "Romper configuraciones peligrosas de tres generadores",

    "Heavy regression builds" to
            "Builds con mucha regresión",

    "Frequent chase interruption" to
            "Interrupciones frecuentes por persecuciones",

    "Poor Great Skill Check consistency" to
            "Poca consistencia con las pruebas de habilidad excelentes",

    // Solo Queue

    "SOLO QUEUE SURVIVOR" to
            "SUPERVIVIENTE SOLO QUEUE",

    "Reliable information, safer rescues, and independent survival." to
            "Información fiable, rescates más seguros y mayor independencia para sobrevivir.",

    "Kindred and Bond replace missing communication, Off the Record helps after an unhook, and Adrenaline gives a powerful endgame reset." to
            "Afinidad y Vínculo compensan la falta de comunicación, Extraoficialmente ayuda después de un desenganche y Adrenalina ofrece una recuperación muy potente en el final de partida.",

    "Solo queue" to
            "Solo Queue",

    "Players without voice communication" to
            "Jugadores sin comunicación por voz",

    "Rescue timing and teammate awareness" to
            "Control del momento de los rescates y posición de los compañeros",

    "Aura blocking" to
            "Bloqueo de auras",

    "Early elimination before endgame" to
            "Eliminación temprana antes del final de partida",

    "Overcommitting to unsafe rescues" to
            "Comprometerse demasiado con rescates inseguros",

    // Stealth Escape

    "STEALTH ESCAPE" to
            "ESCAPE SIGILOSO",

    "Break tracking, hide your route, and disappear after contact." to
            "Rompe el rastreo, oculta tu ruta y desaparece después del contacto.",

    "Distortion protects against aura reading, while Quick & Quiet, Dance With Me, and Lucky Break suppress the information Killers normally use to continue a chase." to
            "Distorsión protege contra la lectura de auras, mientras Velocidad silenciosa, Baila conmigo y Golpe de suerte eliminan gran parte de la información que el Asesino usa normalmente para continuar una persecución.",

    "Breaking line of sight" to
            "Romper la línea de visión",

    "Indoor maps" to
            "Mapas interiores",

    "Players who prefer evasion over looping" to
            "Jugadores que prefieren evadir en lugar de mantener un loop",

    "Killers with strong sound tracking" to
            "Asesinos con buen rastreo mediante sonido",

    "Open maps" to
            "Mapas abiertos",

    "Running out of Distortion value" to
            "Quedarse sin valor de Distorsión",

    // Anti-Tunnel

    "ANTI-TUNNEL" to
            "ANTI-TÚNEL",

    "Punish repeated pressure and create extra chances after an unhook." to
            "Castiga la presión repetida y crea oportunidades adicionales después de un desenganche.",

    "Off the Record protects after an unhook, Decisive Strike punishes immediate pickup pressure, Dead Hard can extend a chase, and Unbreakable answers slugging." to
            "Extraoficialmente protege después de un desenganche, Golpe decisivo castiga la presión inmediata al recogerte, Fajador puede alargar una persecución e Inquebrantable responde al slugging.",

    "High-pressure matches" to
            "Partidas de mucha presión",

    "Players frequently targeted after unhook" to
            "Jugadores perseguidos con frecuencia después de ser desenganchados",

    "Second-chance focused play" to
            "Juego centrado en segundas oportunidades",

    "Perk activation requirements" to
            "Requisitos de activación de las ventajas",

    "Killers waiting out protection" to
            "Asesinos que esperan a que termine la protección",

    "Using resources too early" to
            "Gastar los recursos demasiado pronto",

    // Killer Slowdown

    "KILLER SLOWDOWN" to
            "RALENTIZACIÓN DEL ASESINO",

    "Control generator progress and force Survivors into inefficient repairs." to
            "Controla el progreso de los generadores y obliga a los Supervivientes a realizar reparaciones menos eficientes.",

    "Corrupt Intervention stabilizes the opening, Pain Resonance and Pop reward hooks with regression, and Deadlock slows the next objective after a generator is completed." to
            "Intervención corrupta estabiliza el inicio de la partida, Resonancia del dolor y Pop recompensan los enganches con regresión y Bloqueo ralentiza el siguiente objetivo después de completar un generador.",

    "Most Killer powers" to
            "La mayoría de poderes de Asesino",

    "Learning macro pressure" to
            "Aprender presión macro",

    "Protecting a strong generator spread" to
            "Proteger una buena distribución de generadores",

    "Scourge Hook placement" to
            "Posición de los Ganchos Flagelantes",

    "Failing to convert pressure into hooks" to
            "No convertir la presión en enganches",

    "Overcommitting to one chase" to
            "Comprometerse demasiado con una sola persecución",

    // Aura Hunter

    "AURA HUNTER" to
            "CAZADOR DE AURAS",

    "Locate targets quickly and reduce downtime between chases." to
            "Localiza objetivos rápidamente y reduce el tiempo perdido entre persecuciones.",

    "Lethal Pursuer gives immediate direction, Barbecue & Chilli finds distant targets after hooks, Nowhere to Hide checks nearby generator zones, and A Nurse's Calling punishes healing." to
            "Perseguidor letal proporciona una dirección inmediata, Barbacoa y chile localiza objetivos lejanos después de los enganches, Ningún lugar donde esconderse revisa las zonas cercanas a generadores y La llamada de una enfermera castiga las curaciones.",

    "Mobile Killers" to
            "Asesinos con buena movilidad",

    "Aggressive chase chaining" to
            "Encadenar persecuciones de forma agresiva",

    "Players who dislike searching" to
            "Jugadores a los que no les gusta perder tiempo buscando",

    "Distortion" to
            "Distorsión",

    "Lockers" to
            "Taquillas",

    "Overreliance on aura information" to
            "Depender demasiado de la información de auras",

    // Endgame Lockdown

    "ENDGAME LOCKDOWN" to
            "BLOQUEO DE FINAL DE PARTIDA",

    "Turn the final generator into a dangerous second phase." to
            "Convierte la finalización del último generador en una peligrosa segunda fase.",

    "No Way Out and Remember Me delay escape, Blood Warden can trap Survivors after a late hook, and Devour Hope creates pressure before the endgame begins." to
            "Sin salida y Recuérdame retrasan la escapatoria, Guardián de sangre puede atrapar a los Supervivientes después de un enganche tardío y Devorar esperanza genera presión antes de que comience el final de partida.",

    "Killers with strong late-game mobility" to
            "Asesinos con buena movilidad en el final de partida",

    "Players who enjoy comeback pressure" to
            "Jugadores que disfrutan remontando mediante presión",

    "Punishing greedy gate play" to
            "Castigar jugadas demasiado codiciosas en las puertas",

    "Hex removal" to
            "Destrucción del Maleficio",

    "Losing too much pressure before endgame" to
            "Perder demasiada presión antes del final de partida",

    "Blood Warden timing" to
            "Momento de activación de Guardián de sangre"
)

private val metaBuilds = listOf(
    MetaBuildDefinition(
        id = "chase_specialist",
        title = "CHASE SPECIALIST",
        description =
            "Extend chases, route efficiently, and punish predictable pressure.",
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
        description =
            "Push objectives quickly while protecting the final generator spread.",
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
        description =
            "Reliable information, safer rescues, and independent survival.",
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
        description =
            "Break tracking, hide your route, and disappear after contact.",
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
        description =
            "Punish repeated pressure and create extra chances after an unhook.",
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
        description =
            "Control generator progress and force Survivors into inefficient repairs.",
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
        description =
            "Locate targets quickly and reduce downtime between chases.",
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
        description =
            "Turn the final generator into a dangerous second phase.",
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