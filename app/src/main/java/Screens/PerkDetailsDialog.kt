package com.example.reapercompanion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole
import java.util.Locale

@Composable
fun PerkDetailsDialog(
    perk: Perk,
    onDismiss: () -> Unit
) {
    val isSpanish = Locale.getDefault().language == "es"
    val displayName =
        if (isSpanish) {
            spanishPerkNames[perk.name] ?: perk.name
        } else {
            perk.name
        }

    val displayDescription =
        if (isSpanish) {
            spanishPerkDescriptions[perk.id] ?: perk.description
        } else {
            perk.description
        }

    val displayRole =
        if (isSpanish) {
            when (perk.role) {
                PerkRole.SURVIVOR -> "Superviviente"
                PerkRole.KILLER -> "Asesino"
            }
        } else {
            perk.role.name
                .lowercase()
                .replaceFirstChar { it.uppercase() }
        }

    val displayAvailability =
        if (isSpanish) {
            if (perk.isBaseGame) {
                "Juego base"
            } else {
                "Perk de personaje o DLC"
            }
        } else {
            if (perk.isBaseGame) {
                "Base Game"
            } else {
                "Character or DLC Perk"
            }
        }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF080D10),
        shape = RoundedCornerShape(24.dp),
        title = {
            Column {
                OnlinePerkImage(
                    perk = perk,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.35f)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = displayName,
                    color = ReaperColors.PrimaryText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = perk.owner,
                    color = ReaperColors.CyanGlow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 430.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                PerkCategoryBadge(
                    category = perk.category
                )

                Spacer(modifier = Modifier.height(14.dp))

                DetailCard(
                    label = if (isSpanish) "ROL" else "ROLE",
                    value = displayRole
                )

                Spacer(modifier = Modifier.height(12.dp))

                DetailCard(
                    label = if (isSpanish) "DISPONIBILIDAD" else "AVAILABILITY",
                    value = displayAvailability
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = ReaperColors.BorderActive
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = ReaperColors.CardBackground
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = if (isSpanish) "DESCRIPCIÓN" else "DESCRIPTION",
                            color = ReaperColors.CyanGlow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = displayDescription,
                            color = ReaperColors.PrimaryText,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ReaperColors.CyanDark,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (isSpanish) "CERRAR" else "CLOSE",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
private fun DetailCard(
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = ReaperColors.BorderInactive
        ),
        colors = CardDefaults.cardColors(
            containerColor = ReaperColors.CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                color = ReaperColors.SecondaryText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = ReaperColors.PrimaryText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private val spanishPerkNames = mapOf(
    "Windows of Opportunity" to "Oportunidades",
    "Lithe" to "Agilidad",
    "Resilience" to "Resiliencia",
    "Adrenaline" to "Adrenalina",
    "Deja Vu" to "Déjà Vu",
    "Prove Thyself" to "Demuestra lo que Vales",
    "We'll Make It" to "Lo Conseguiremos",
    "Botany Knowledge" to "Conocimientos de Botánica",
    "Empathy" to "Empatía",
    "Kindred" to "Afinidad",
    "Quick & Quiet" to "Velocidad Silenciosa",
    "Lightweight" to "De Pies Ligeros",
    "Distortion" to "Distorsión",
    "Iron Will" to "Voluntad de Hierro",
    "Head On" to "De Frente",
    "Flashbang" to "Granada Aturdidora",
    "Deception" to "Engaño",
    "Off the Record" to "Extraoficial",
    "Finesse" to "Finura",
    "Balanced Landing" to "Caída Equilibrada",
    "Five Moves Ahead" to "Cinco Movimientos por Delante",
    "Built to Last" to "Fabricado para Durar",
    "Overzealous" to "Exceso de Entusiasmo",
    "Stake Out" to "Bajo Vigilancia",
    "Hyperfocus" to "Hiperconcentración",
    "Desperate Measures" to "Medidas Desesperadas",
    "Aftercare" to "Postratamiento",
    "Babysitter" to "Canguro",
    "Leader" to "Líder",
    "Dance With Me" to "Baila Conmigo",
    "Lucky Break" to "Golpe de Suerte",
    "Calm Spirit" to "Espíritu Tranquilo",
    "Urban Evasion" to "Evasión Urbana",
    "Blast Mine" to "Mina Explosiva",
    "Diversion" to "Diversión",
    "Power Struggle" to "Lucha de Poder",
    "Chemical Trap" to "Trampa Química",
    "Decisive Strike" to "Golpe Decisivo",
    "Unbreakable" to "Inquebrantable",
    "Deliverance" to "Liberación",
    "Dead Hard" to "Fajador",
    "Bond" to "Vínculo"
)

private val spanishPerkDescriptions = mapOf(
    "windows_of_opportunity" to "Revela pallets, paredes rompibles y ubicaciones de salto cercanas.",
    "lithe" to "Obtén una ráfaga de velocidad después de realizar un salto rápido.",
    "resilience" to "Realiza varias acciones más rápido mientras estás herido.",
    "adrenaline" to "Recupera salud y gana velocidad cuando se activan las puertas de salida.",
    "deja_vu" to "Revela generadores situados cerca unos de otros.",
    "prove_thyself" to "Mejora la eficiencia de reparación cooperativa.",
    "well_make_it" to "Mejora la velocidad de curación después de desenganchar de forma segura a un Superviviente.",
    "botany_knowledge" to "Mejora la eficiencia de curación.",
    "empathy" to "Revela a Supervivientes heridos o moribundos dentro del alcance.",
    "kindred" to "Proporciona información de auras mientras un Superviviente está enganchado.",
    "quick_and_quiet" to "Suprime el ruido de acciones rápidas después de su tiempo de reutilización.",
    "lightweight" to "Hace que las marcas de arañazos sean más difíciles de seguir para el Asesino.",
    "distortion" to "Ayuda a ocultar tu aura del Asesino.",
    "iron_will" to "Reduce los gemidos de dolor mientras estás herido.",
    "head_on" to "Permite aturdir al Asesino al salir rápidamente de una taquilla.",
    "flashbang" to "Crea una granada cegadora después de completar progreso de generador.",
    "deception" to "Finge entrar en una taquilla para confundir al Asesino.",
    "off_the_record" to "Proporciona protección y sigilo después de ser desenganchado.",
    "finesse" to "Mejora el rendimiento de los saltos bajo ciertas condiciones.",
    "balanced_landing" to "Reduce el tambaleo y otorga velocidad después de una caída.",
    "five_moves_ahead" to "Favorece una planificación anticipada de rutas durante una persecución.",
    "built_to_last" to "Restaura cargas del objeto después de esconderte en una taquilla.",
    "overzealous" to "Mejora la velocidad de reparación después de limpiar o bendecir un Tótem.",
    "stake_out" to "Convierte pruebas de habilidad en un progreso de reparación más fuerte.",
    "hyperfocus" to "Recompensa pruebas de habilidad excelentes consecutivas.",
    "desperate_measures" to "Mejora la velocidad de curación y desenganche cuando hay compañeros heridos.",
    "aftercare" to "Crea conexiones de lectura de aura con compañeros a los que has ayudado.",
    "babysitter" to "Ayuda a proteger a Supervivientes después de ser desenganchados.",
    "leader" to "Mejora varias acciones realizadas por compañeros cercanos.",
    "dance_with_me" to "Suprime marcas de arañazos después de ciertas acciones rápidas.",
    "lucky_break" to "Suprime temporalmente la sangre y las marcas de arañazos después de sufrir daño.",
    "calm_spirit" to "Suprime los gritos y reduce las reacciones de la fauna.",
    "urban_evasion" to "Aumenta la velocidad de movimiento mientras estás agachado.",
    "blast_mine" to "Coloca una trampa en un generador que ciega o aturde al Asesino.",
    "diversion" to "Lanza una piedra para crear una notificación falsa.",
    "power_struggle" to "Permite soltar un pallet mientras te transportan bajo ciertas condiciones.",
    "chemical_trap" to "Coloca una trampa en un pallet derribado para ralentizar al Asesino cuando lo rompa.",
    "decisive_strike" to "Ofrece una oportunidad de escapar del Asesino después de ser desenganchado.",
    "unbreakable" to "Permite recuperarte del estado agonizante una vez por prueba.",
    "deliverance" to "Permite un auto-desenganche garantizado después de realizar un rescate seguro.",
    "dead_hard" to "Proporciona una breve protección durante una persecución.",
    "bond" to "Revela a los compañeros cercanos."
)