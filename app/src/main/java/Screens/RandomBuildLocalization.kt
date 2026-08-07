package com.example.reapercompanion.screens

fun localizedRandomPerkName(
    name: String,
    isSpanish: Boolean
): String {
    return if (isSpanish) {
        spanishRandomPerkNames[name] ?: name
    } else {
        name
    }
}

fun localizedRandomCategory(
    category: String,
    isSpanish: Boolean
): String {
    return if (isSpanish) {
        spanishRandomCategories[category] ?: category
    } else {
        category
    }
}

private val spanishRandomCategories = mapOf(
    "CHASE" to "PERSECUCIÓN",
    "INFORMATION" to "INFORMACIÓN",
    "GENERATOR" to "GENERADOR",
    "HEALING" to "CURACIÓN",
    "ENDGAME" to "FINAL DE PARTIDA",
    "STEALTH" to "SIGILO",
    "MEME" to "MEME",
    "SUPPORT" to "APOYO",
    "SECOND CHANCE" to "SEGUNDA OPORTUNIDAD",
    "REGRESSION" to "REGRESIÓN",
    "SLOWDOWN" to "RALENTIZACIÓN",
    "AURA" to "AURA",
    "TRACKING" to "RASTREO",
    "HEX" to "MALEFICIO",
    "ANTI LOOP" to "ANTI-LOOP"
)

private val spanishRandomPerkNames = mapOf(
    "Windows of Opportunity" to "Oportunidades",
    "Lithe" to "Agilidad",
    "Resilience" to "Resiliencia",
    "Adrenaline" to "Adrenalina",
    "Deja Vu" to "Déjà Vu",
    "Déjà Vu" to "Déjà Vu",
    "Prove Thyself" to "Demuestra lo que vales",
    "We'll Make It" to "Lo conseguiremos",
    "Botany Knowledge" to "Conocimientos de botánica",
    "Empathy" to "Empatía",
    "Kindred" to "Afinidad",
    "Quick & Quiet" to "Velocidad silenciosa",
    "Lightweight" to "De pies ligeros",
    "Distortion" to "Distorsión",
    "Iron Will" to "Voluntad de hierro",
    "Head On" to "De frente",
    "Flashbang" to "Granada cegadora",
    "Deception" to "Engaño",
    "Off the Record" to "Extraoficialmente",
    "Finesse" to "Finura",
    "Balanced Landing" to "Caída equilibrada",
    "Five Moves Ahead" to "Cinco movimientos por delante",
    "Built to Last" to "Hecho para durar",
    "Overzealous" to "Exceso de celo",
    "Stake Out" to "Vigilancia",
    "Hyperfocus" to "Hiperconcentración",
    "Desperate Measures" to "Medidas desesperadas",
    "Aftercare" to "Cuidados posteriores",
    "Babysitter" to "Niñera",
    "Leader" to "Líder",
    "Dance With Me" to "Baila conmigo",
    "Lucky Break" to "Golpe de suerte",
    "Calm Spirit" to "Espíritu calmado",
    "Urban Evasion" to "Evasión urbana",
    "Blast Mine" to "Mina explosiva",
    "Diversion" to "Distracción",
    "Power Struggle" to "Lucha de poder",
    "Chemical Trap" to "Trampa química",
    "Decisive Strike" to "Golpe decisivo",
    "Unbreakable" to "Inquebrantable",
    "Deliverance" to "Liberación",
    "Dead Hard" to "Fajador",
    "Bond" to "Vínculo",

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