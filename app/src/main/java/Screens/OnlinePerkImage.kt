package com.example.reapercompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.reapercompanion.design.ReaperColors
import com.example.reapercompanion.models.Perk
import com.example.reapercompanion.models.PerkRole

@Composable
fun OnlinePerkImage(
    perk: Perk,
    modifier: Modifier = Modifier
) {
    val initials = perk.name
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { word ->
            word.firstOrNull()?.uppercase() ?: ""
        }

    val iconName = when (perk.id) {

        // Survivor perks
        "windows_of_opportunity" -> "windowsOfOpportunity"
        "lithe" -> "lithe"
        "resilience" -> "resilience"
        "adrenaline" -> "adrenaline"
        "deja_vu" -> "dejaVu"
        "prove_thyself" -> "proveThyself"
        "well_make_it" -> "wellMakeIt"
        "botany_knowledge" -> "botanyKnowledge"
        "empathy" -> "empathy"
        "kindred" -> "kindred"
        "quick_and_quiet" -> "quickAndQuiet"
        "lightweight" -> "lightweight"
        "distortion" -> "distortion"
        "iron_will" -> "ironWill"
        "head_on" -> "headOn"
        "flashbang" -> "flashbang"
        "deception" -> "deception"
        "off_the_record" -> "offTheRecord"
        "finesse" -> "finesse"
        "balanced_landing" -> "balancedLanding"

        // Special filename handled inside perkImageUrl().
        "five_moves_ahead" -> "FiveMovesAhead"

        "built_to_last" -> "builtToLast"
        "overzealous" -> "overzealous"
        "stake_out" -> "stakeOut"
        "hyperfocus" -> "hyperfocus"
        "desperate_measures" -> "desperateMeasures"
        "aftercare" -> "aftercare"
        "babysitter" -> "babysitter"
        "leader" -> "leader"
        "dance_with_me" -> "danceWithMe"
        "lucky_break" -> "luckyBreak"
        "calm_spirit" -> "calmSpirit"
        "urban_evasion" -> "urbanEvasion"
        "blast_mine" -> "blastMine"
        "diversion" -> "diversion"
        "power_struggle" -> "powerStruggle"
        "chemical_trap" -> "chemicalTrap"
        "decisive_strike" -> "decisiveStrike"
        "unbreakable" -> "unbreakable"
        "deliverance" -> "deliverance"
        "dead_hard" -> "deadHard"
        "bond" -> "bond"

        // Killer perks
        "scourge_hook_pain_resonance" ->
            "scourgeHookPainResonance"

        "pop_goes_the_weasel" ->
            "popGoesTheWeasel"

        "corrupt_intervention" ->
            "corruptIntervention"

        "deadlock" ->
            "deadlock"

        "bamboozle" ->
            "bamboozle"

        "enduring" ->
            "enduring"

        "spirit_fury" ->
            "spiritFury"

        "brutal_strength" ->
            "brutalStrength"

        "nowhere_to_hide" ->
            "nowhereToHide"

        "lethal_pursuer" ->
            "lethalPursuer"

        "barbecue_and_chilli" ->
            "barbecueAndChilli"

        "a_nurses_calling" ->
            "aNursesCalling"

        "tinkerer" ->
            "tinkerer"

        "trail_of_torment" ->
            "trailOfTorment"

        "dark_devotion" ->
            "darkDevotion"

        "hex_ruin" ->
            "hexRuin"

        "hex_undying" ->
            "hexUndying"

        "hex_devour_hope" ->
            "hexDevourHope"

        "no_way_out" ->
            "noWayOut"

        "remember_me" ->
            "rememberMe"

        "blood_warden" ->
            "bloodWarden"

        else -> null
    }

    val imageUrl = when {
        iconName != null -> perkImageUrl(iconName)
        !perk.imageUrl.isNullOrBlank() -> perk.imageUrl
        else -> null
    }

    val outerShape = RoundedCornerShape(18.dp)
    val innerShape = RoundedCornerShape(14.dp)

    val glowColor = when (perk.role) {
        PerkRole.KILLER -> Color(0xFFFF5A5A)
        PerkRole.SURVIVOR -> ReaperColors.CyanGlow
    }

    var imageFailed by remember(perk.id, imageUrl) {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .clip(outerShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = 0.48f),
                        Color(0xFF10252C),
                        Color(0xFF060A0D)
                    )
                )
            )
            .border(
                width = 1.5.dp,
                color = glowColor.copy(alpha = 0.62f),
                shape = outerShape
            )
            .padding(7.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(innerShape)
                .background(Color(0xFF080D10)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl.isNullOrBlank() || imageFailed) {
                Text(
                    text = initials,
                    color = glowColor,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = perk.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    contentScale = ContentScale.Fit,
                    onLoading = {
                        imageFailed = false
                    },
                    onSuccess = {
                        imageFailed = false
                    },
                    onError = {
                        imageFailed = true
                    }
                )
            }
        }
    }
}

private fun perkImageUrl(
    iconName: String
): String {
    return when (iconName) {
        "FiveMovesAhead" ->
            "https://deadbydaylight.wiki.gg/wiki/" +
                    "Special:Redirect/file/IconsPerks%20FiveMovesAhead.png"

        else ->
            "https://deadbydaylight.wiki.gg/wiki/" +
                    "Special:Redirect/file/IconPerks_${iconName}.png"
    }
}