package com.example.reapercompanion.itemcoach

import java.util.Locale

object ItemCoachEngine {

    fun generate(
        goalId: String
    ): ItemCoachRecommendationSet {
        val result = when (goalId) {

            "blind_killer" -> ItemCoachRecommendationSet(
                goalId = goalId,
                goalTitle = "Blind the Killer",
                recommendations = listOf(
                    RankedRecommendation(
                        tier = RecommendationTier.REAPER_CHOICE,
                        title = "Maximum Blind Pressure",
                        item = "Utility Flashlight",
                        addOns = listOf(
                            "Odd Bulb",
                            "Intense Halogen"
                        ),
                        perks = listOf(
                            "Champion of Light",
                            "Background Player",
                            "Residual Manifest",
                            "Flashbang"
                        ),
                        whyThisWorks =
                            "The Utility Flashlight gives you strong blind duration and plenty of beam time. Odd Bulb and Intense Halogen push blind duration even further, while the perk package helps you reach pickup saves and create extra blind opportunities.",
                        executionTip =
                            "For a pickup save, stay hidden until the Killer commits to the pickup animation. Move into position during the animation, then start the blind late enough that it finishes as the Killer regains control."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.STRONG_ALTERNATIVE,
                        title = "Accurate Save Setup",
                        item = "Sport Flashlight",
                        addOns = listOf(
                            "High-End Sapphire Lens",
                            "Intense Halogen"
                        ),
                        perks = listOf(
                            "Champion of Light",
                            "Background Player",
                            "Bond",
                            "Flashbang"
                        ),
                        whyThisWorks =
                            "The Sport Flashlight is easier to aim and depletes more slowly. The Sapphire Lens improves reach and blind duration, making this a more forgiving setup when positioning is not perfect.",
                        executionTip =
                            "Use Bond to track the teammate being chased and rotate early. The safest flashlight save is the one where you are already behind cover near the pickup instead of sprinting in after the Killer lifts them."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.BUDGET,
                        title = "Practice Setup",
                        item = "Flashlight",
                        addOns = listOf(
                            "Focus Lens",
                            "Long Life Battery"
                        ),
                        perks = listOf(
                            "Kindred",
                            "Sprint Burst",
                            "Windows of Opportunity",
                            "Flashbang"
                        ),
                        whyThisWorks =
                            "This uses easier-to-find equipment while still giving useful reach and extra battery life. It is a good setup for practicing timing without burning your best flashlight inventory.",
                        executionTip =
                            "Practice centering the beam on the Killer's face before trying hero saves. Good timing with a basic flashlight beats bad timing with an expensive one."
                    )
                ),
                nextUnlock = listOf(
                    "Utility Flashlight",
                    "Odd Bulb",
                    "Champion of Light"
                )
            )

            "heal_faster" -> ItemCoachRecommendationSet(
                goalId = goalId,
                goalTitle = "Heal Faster",
                recommendations = listOf(
                    RankedRecommendation(
                        tier = RecommendationTier.REAPER_CHOICE,
                        title = "Fast Team Recovery",
                        item = "Ranger Med-Kit",
                        addOns = listOf(
                            "Abdominal Dressing",
                            "Medical Scissors"
                        ),
                        perks = listOf(
                            "We'll Make It",
                            "Botany Knowledge",
                            "Desperate Measures",
                            "Empathy"
                        ),
                        whyThisWorks =
                            "The Ranger Med-Kit is built for fast altruistic healing. Abdominal Dressing and Medical Scissors add more healing speed, while the perks help you find injured teammates and recover the team quickly after hooks.",
                        executionTip =
                            "Do not run across the map before healing. First break the Killer's line of sight, move behind nearby solid cover, then heal immediately if the chase has clearly moved away."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.STRONG_ALTERNATIVE,
                        title = "Reliable Rescue Healer",
                        item = "Emergency Med-Kit",
                        addOns = listOf(
                            "Medical Scissors",
                            "Butterfly Tape"
                        ),
                        perks = listOf(
                            "We'll Make It",
                            "Kindred",
                            "Empathy",
                            "Botany Knowledge"
                        ),
                        whyThisWorks =
                            "This setup gives excellent healing speed with easier-to-find add-ons and strong information for safe rescues.",
                        executionTip =
                            "After an unhook, read the Killer first. If the Killer commits to another chase, heal under the hook or behind the nearest safe cover instead of automatically running to a distant corner."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.BUDGET,
                        title = "Low-Cost Healing",
                        item = "Camping Aid Kit",
                        addOns = listOf(
                            "Butterfly Tape",
                            "Bandages"
                        ),
                        perks = listOf(
                            "We'll Make It",
                            "Kindred",
                            "Bond",
                            "Deja Vu"
                        ),
                        whyThisWorks =
                            "The Camping Aid Kit and common add-ons give useful healing value without spending rare inventory. The perks provide rescue information and keep the build useful when healing is not needed.",
                        executionTip =
                            "Save Med-Kit charges for moments when healing speed matters. If a teammate is completely safe, a normal heal preserves the item for a dangerous recovery later."
                    )
                ),
                nextUnlock = listOf(
                    "Ranger Med-Kit",
                    "Abdominal Dressing",
                    "We'll Make It"
                )
            )

            "rush_generators" -> ItemCoachRecommendationSet(
                goalId = goalId,
                goalTitle = "Rush Generators",
                recommendations = listOf(
                    RankedRecommendation(
                        tier = RecommendationTier.REAPER_CHOICE,
                        title = "Maximum Generator Value",
                        item = "Commodious Toolbox",
                        addOns = listOf(
                            "Brand New Part",
                            "Wire Spool"
                        ),
                        perks = listOf(
                            "Built to Last",
                            "Streetwise",
                            "Deja Vu",
                            "Hyperfocus"
                        ),
                        whyThisWorks =
                            "The Commodious Toolbox carries a large charge pool, Wire Spool extends it, and Brand New Part can permanently cut a generator's remaining repair requirement. The perks help stretch item value and keep you focused on important generators.",
                        executionTip =
                            "Use Brand New Part on a generator you are committed to finishing, especially a dangerous central generator. Do not waste it on a safe edge generator that the team can finish later."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.STRONG_ALTERNATIVE,
                        title = "Fast Burst Repair",
                        item = "Engineer's Toolbox",
                        addOns = listOf(
                            "Brand New Part",
                            "Socket Swivels"
                        ),
                        perks = listOf(
                            "Deja Vu",
                            "Built to Last",
                            "Streetwise",
                            "Stake Out"
                        ),
                        whyThisWorks =
                            "The Engineer's Toolbox burns through fewer charges very quickly, making it excellent when you need a short burst to finish a contested generator before the Killer returns.",
                        executionTip =
                            "Do not dump the toolbox into the first generator you touch. Hold the fast charges for a generator that is nearly complete or one the Killer is actively trying to defend."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.BUDGET,
                        title = "Everyday Repair Kit",
                        item = "Toolbox",
                        addOns = listOf(
                            "Scraps",
                            "Socket Swivels"
                        ),
                        perks = listOf(
                            "Deja Vu",
                            "Prove Thyself",
                            "Built to Last",
                            "Kindred"
                        ),
                        whyThisWorks =
                            "A basic Toolbox with extra charges and repair speed is easy to replace and still gives meaningful generator pressure.",
                        executionTip =
                            "Use Deja Vu to identify the three generators that could become a late-game three-gen. Breaking that cluster early is often more valuable than simply repairing the closest generator."
                    )
                ),
                nextUnlock = listOf(
                    "Commodious Toolbox",
                    "Brand New Part",
                    "Built to Last"
                )
            )

            "sabotage_hooks" -> ItemCoachRecommendationSet(
                goalId = goalId,
                goalTitle = "Sabotage Hooks",
                recommendations = listOf(
                    RankedRecommendation(
                        tier = RecommendationTier.REAPER_CHOICE,
                        title = "Hook Denial Specialist",
                        item = "Alex's Toolbox",
                        addOns = listOf(
                            "Hacksaw",
                            "Grip Wrench"
                        ),
                        perks = listOf(
                            "Saboteur",
                            "Breakout",
                            "Background Player",
                            "Empathy"
                        ),
                        whyThisWorks =
                            "Alex's Toolbox has exceptional sabotage speed. Hacksaw pushes the action faster, while Grip Wrench keeps the sabotaged hook unavailable longer. The perks help you identify the play, reach the carry path, and create extra wiggle pressure.",
                        executionTip =
                            "Do not sabotage before the Killer chooses a direction. Wait for the pickup, read the hook they are actually walking toward, then sprint ahead and start the sabotage late enough that the hook breaks just before they reach it."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.STRONG_ALTERNATIVE,
                        title = "Flexible Sabotage",
                        item = "Commodious Toolbox",
                        addOns = listOf(
                            "Hacksaw",
                            "Grip Wrench"
                        ),
                        perks = listOf(
                            "Saboteur",
                            "Breakout",
                            "Sprint Burst",
                            "Bond"
                        ),
                        whyThisWorks =
                            "The Commodious Toolbox gives more charges than Alex's Toolbox while still having strong sabotage speed, making it better for players who want multiple attempts.",
                        executionTip =
                            "Approach from the side of the hook opposite the Killer's path. That forces the Killer to either hit you and lose distance or abandon the hook."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.BUDGET,
                        title = "Basic Hook Rescue",
                        item = "Toolbox",
                        addOns = listOf(
                            "Cutting Wire",
                            "Protective Gloves"
                        ),
                        perks = listOf(
                            "Saboteur",
                            "Breakout",
                            "Bond",
                            "Sprint Burst"
                        ),
                        whyThisWorks =
                            "This is an inexpensive way to practice hook-denial timing. Protective Gloves also keep the completed sabotage quieter.",
                        executionTip =
                            "If the Killer is already close enough to hit you and still reach the hook, cancel the play. A failed sabotage plus a free hit usually gives the Killer more value than the rescue attempt was worth."
                    )
                ),
                nextUnlock = listOf(
                    "Alex's Toolbox",
                    "Hacksaw",
                    "Background Player"
                )
            )

            "support_teammates" -> ItemCoachRecommendationSet(
                goalId = goalId,
                goalTitle = "Support Teammates",
                recommendations = listOf(
                    RankedRecommendation(
                        tier = RecommendationTier.REAPER_CHOICE,
                        title = "Team Rescue Support",
                        item = "Ranger Med-Kit",
                        addOns = listOf(
                            "Gel Dressings",
                            "Medical Scissors"
                        ),
                        perks = listOf(
                            "Kindred",
                            "We'll Make It",
                            "Bond",
                            "Reassurance"
                        ),
                        whyThisWorks =
                            "This setup gives you information, fast post-rescue healing, and enough Med-Kit capacity to help multiple teammates while still contributing to generators.",
                        executionTip =
                            "Before leaving a generator for a rescue, check whether another teammate is already closer. Good support means making the needed play, not sending three Survivors to the same hook."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.STRONG_ALTERNATIVE,
                        title = "Information Support",
                        item = "Emergency Med-Kit",
                        addOns = listOf(
                            "Gel Dressings",
                            "Butterfly Tape"
                        ),
                        perks = listOf(
                            "Kindred",
                            "Empathy",
                            "We'll Make It",
                            "Deja Vu"
                        ),
                        whyThisWorks =
                            "This keeps healing strong while giving you clear information about who needs help and where your generator pressure should go next.",
                        executionTip =
                            "Use the HUD and aura information before moving. If the injured Survivor is already safe and another teammate is rescuing, stay on your generator instead of creating unnecessary downtime."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.BUDGET,
                        title = "Solo Queue Helper",
                        item = "First Aid Kit",
                        addOns = listOf(
                            "Bandages",
                            "Butterfly Tape"
                        ),
                        perks = listOf(
                            "Kindred",
                            "Bond",
                            "We'll Make It",
                            "Deja Vu"
                        ),
                        whyThisWorks =
                            "Everything in this setup is aimed at making better team decisions without requiring rare equipment.",
                        executionTip =
                            "When Kindred shows the Killer leaving the hook, move early. Arriving just after the Killer leaves is safer and faster than waiting until the hooked Survivor is close to the next stage."
                    )
                ),
                nextUnlock = listOf(
                    "Kindred",
                    "We'll Make It",
                    "Ranger Med-Kit"
                )
            )

            "stealth" -> ItemCoachRecommendationSet(
                goalId = goalId,
                goalTitle = "Play Stealthy",
                recommendations = listOf(
                    RankedRecommendation(
                        tier = RecommendationTier.REAPER_CHOICE,
                        title = "Disappear in the Fog",
                        item = "Vigo's Fog Vial",
                        addOns = listOf(
                            "Oily Sap",
                            "Mushroom Formula"
                        ),
                        perks = listOf(
                            "Distortion",
                            "Lightweight",
                            "Quick & Quiet",
                            "Iron Will"
                        ),
                        whyThisWorks =
                            "Vigo's Fog Vial can suppress Scratch Marks and Auras inside its cloud while also obscuring sound and visibility. Oily Sap extends the cloud and Mushroom Formula makes it larger, giving you more room to break tracking.",
                        executionTip =
                            "Do not throw the Fog Vial while running in a straight line with the Killer watching you. Break line of sight first, release the cloud at a junction or obstacle, then change direction inside it so the Killer has to guess which exit you took."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.STRONG_ALTERNATIVE,
                        title = "Fast Disengage",
                        item = "Artisan's Fog Vial",
                        addOns = listOf(
                            "Reactive Compound",
                            "Oily Sap"
                        ),
                        perks = listOf(
                            "Distortion",
                            "Quick & Quiet",
                            "Dance With Me",
                            "Lightweight"
                        ),
                        whyThisWorks =
                            "The Artisan's Fog Vial expands quickly and lasts long enough to create a strong pathing mix-up. The perk package helps hide your trail after vaults and locker plays.",
                        executionTip =
                            "Use the cloud to cover a decision point, not an empty field. A window, pallet, doorway, or split path gives you multiple believable escape routes."
                    ),
                    RankedRecommendation(
                        tier = RecommendationTier.BUDGET,
                        title = "Entry-Level Stealth",
                        item = "Apprentice's Fog Vial",
                        addOns = listOf(
                            "Volcanic Stone",
                            "Reactive Compound"
                        ),
                        perks = listOf(
                            "Lightweight",
                            "Quick & Quiet",
                            "Iron Will",
                            "Kindred"
                        ),
                        whyThisWorks =
                            "The common Fog Vial still suppresses key tracking information and gives newer players a practical way to learn line-of-sight breaks.",
                        executionTip =
                            "After losing line of sight, stop giving the Killer free information. Avoid immediately fast-vaulting or sprinting through open ground unless the move creates real distance."
                    )
                ),
                nextUnlock = listOf(
                    "Vigo's Fog Vial",
                    "Oily Sap",
                    "Distortion"
                )
            )

            else -> ItemCoachRecommendationSet(
                goalId = goalId,
                goalTitle = ItemCoachGoals.findById(goalId)?.title ?: "Coming Soon",
                recommendations = emptyList(),
                nextUnlock = emptyList()
            )
        }

        return if (Locale.getDefault().language == "es") {
            localizeSpanish(result)
        } else {
            result
        }
    }

    private fun localizeSpanish(
        set: ItemCoachRecommendationSet
    ): ItemCoachRecommendationSet {
        return set.copy(
            goalTitle = es(set.goalTitle),
            nextUnlock = set.nextUnlock.map(::es),
            recommendations = set.recommendations.map { recommendation ->
                recommendation.copy(
                    title = es(recommendation.title),
                    item = es(recommendation.item),
                    addOns = recommendation.addOns.map(::es),
                    perks = recommendation.perks.map(::es),
                    whyThisWorks = es(recommendation.whyThisWorks),
                    executionTip = es(recommendation.executionTip)
                )
            }
        )
    }

    private fun es(text: String): String {
        return spanish[text] ?: text
    }

    private val spanish = mapOf(
        "Blind the Killer" to "Cegar al Asesino",
        "Maximum Blind Pressure" to "Máxima presión de cegamiento",
        "Accurate Save Setup" to "Configuración de rescate preciso",
        "Practice Setup" to "Configuración de práctica",

        "Heal Faster" to "Curar más rápido",
        "Fast Team Recovery" to "Recuperación rápida del equipo",
        "Reliable Rescue Healer" to "Sanador fiable de rescates",
        "Low-Cost Healing" to "Curación de bajo costo",

        "Rush Generators" to "Acelerar generadores",
        "Maximum Generator Value" to "Máximo valor de generador",
        "Fast Burst Repair" to "Reparación explosiva rápida",
        "Everyday Repair Kit" to "Kit de reparación diario",

        "Sabotage Hooks" to "Sabotear ganchos",
        "Hook Denial Specialist" to "Especialista en negar ganchos",
        "Flexible Sabotage" to "Sabotaje flexible",
        "Basic Hook Rescue" to "Rescate básico de gancho",

        "Support Teammates" to "Apoyar a los compañeros",
        "Team Rescue Support" to "Apoyo de rescate del equipo",
        "Information Support" to "Apoyo de información",
        "Solo Queue Helper" to "Ayuda para Solo Queue",

        "Play Stealthy" to "Jugar con sigilo",
        "Disappear in the Fog" to "Desaparece en la niebla",
        "Fast Disengage" to "Desenganche rápido",
        "Entry-Level Stealth" to "Sigilo para principiantes",
        "Coming Soon" to "Próximamente",

        // Official Spanish Dead by Daylight display names
        "Utility Flashlight" to "Linterna Multiusos",
        "Sport Flashlight" to "Linterna Deportiva",
        "Flashlight" to "Linterna",
        "Odd Bulb" to "Bombilla Rara",
        "Intense Halogen" to "Halógeno Potente",
        "High-End Sapphire Lens" to "Lente de Zafiro de Calidad Superior",
        "Focus Lens" to "Lente de Enfoque",
        "Long Life Battery" to "Pila de Larga Duración",

        "Ranger Med-Kit" to "Botiquín de Guardabosques",
        "Emergency Med-Kit" to "Botiquín de Emergencia",
        "Camping Aid Kit" to "Botiquín de Primeros Auxilios de Acampada",
        "First Aid Kit" to "Botiquín de Primeros Auxilios",
        "Abdominal Dressing" to "Vendaje Abdominal",
        "Medical Scissors" to "Tijeras Médicas",
        "Butterfly Tape" to "Cinta Mariposa",
        "Bandages" to "Vendas",
        "Gel Dressings" to "Apósitos de Gel",

        "Commodious Toolbox" to "Caja de Herramientas Espaciosa",
        "Engineer's Toolbox" to "Caja de Herramientas de Ingeniería",
        "Alex's Toolbox" to "Caja de Herramientas de Alex",
        "Toolbox" to "Caja de Herramientas",
        "Brand New Part" to "Pieza Nueva",
        "Wire Spool" to "Bobina de Alambre",
        "Socket Swivels" to "Articulaciones de Llave",
        "Scraps" to "Restos",
        "Hacksaw" to "Sierra para Metales",
        "Grip Wrench" to "Llave de Agarre",
        "Cutting Wire" to "Alambre de Corte",
        "Protective Gloves" to "Guantes Protectores",

        "Vigo's Fog Vial" to "Vial de Niebla de Vigo",
        "Artisan's Fog Vial" to "Vial de Niebla de Artesano",
        "Apprentice's Fog Vial" to "Vial de Niebla de Aprendiz",
        "Oily Sap" to "Savia Aceitosa",
        "Mushroom Formula" to "Fórmula de Hongos",
        "Reactive Compound" to "Componente Reactivo",
        "Volcanic Stone" to "Piedra Volcánica",

        "Bond" to "Vínculo",
        "Botany Knowledge" to "Conocimientos de Botánica",
        "Desperate Measures" to "Medidas Desesperadas",
        "Empathy" to "Empatía",
        "We'll Make It" to "Lo Conseguiremos",
        "Deja Vu" to "Déjà Vu",
        "Prove Thyself" to "Demuestra lo que Vales",
        "Sprint Burst" to "Esprint",
        "Windows of Opportunity" to "Oportunidades",
        "Streetwise" to "Con Calle",
        "Hyperfocus" to "Hiperconcentración",
        "Stake Out" to "Bajo Vigilancia",
        "Breakout" to "Fuga",
        "Reassurance" to "Reafirmación",
        "Residual Manifest" to "Manifestación Residual",
        "Distortion" to "Distorsión",
        "Lightweight" to "De Pies Ligeros",
        "Quick & Quiet" to "Velocidad Silenciosa",
        "Champion of Light" to "Campeón de la Luz",
        "Background Player" to "Jugador de Fondo",
        "Flashbang" to "Granada Aturdidora",
        "Saboteur" to "Saboteador",
        "Iron Will" to "Voluntad de Hierro",
        "Dance With Me" to "Baila Conmigo",

        "The Utility Flashlight gives you strong blind duration and plenty of beam time. Odd Bulb and Intense Halogen push blind duration even further, while the perk package helps you reach pickup saves and create extra blind opportunities." to
                "La Utility Flashlight ofrece una gran duración de cegamiento y bastante tiempo de haz. Odd Bulb e Intense Halogen aumentan aún más la duración, mientras que los perks te ayudan a llegar a rescates durante recogidas y crear más oportunidades de cegamiento.",
        "For a pickup save, stay hidden until the Killer commits to the pickup animation. Move into position during the animation, then start the blind late enough that it finishes as the Killer regains control." to
                "Para un rescate durante una recogida, mantente oculto hasta que el Asesino se comprometa con la animación. Colócate mientras recoge al Superviviente y empieza el cegamiento lo bastante tarde para que termine justo cuando recupere el control.",
        "The Sport Flashlight is easier to aim and depletes more slowly. The Sapphire Lens improves reach and blind duration, making this a more forgiving setup when positioning is not perfect." to
                "La Sport Flashlight es más fácil de apuntar y se consume más lentamente. High-End Sapphire Lens mejora el alcance y la duración del cegamiento, haciendo esta configuración más tolerante cuando tu posición no es perfecta.",
        "Use Bond to track the teammate being chased and rotate early. The safest flashlight save is the one where you are already behind cover near the pickup instead of sprinting in after the Killer lifts them." to
                "Usa Bond para seguir al compañero perseguido y muévete con anticipación. El rescate con linterna más seguro es aquel en el que ya estás detrás de cobertura cerca de la recogida, en vez de correr hacia allí después de que el Asesino lo levante.",
        "This uses easier-to-find equipment while still giving useful reach and extra battery life. It is a good setup for practicing timing without burning your best flashlight inventory." to
                "Usa equipo más fácil de conseguir y aun así ofrece buen alcance y batería adicional. Es una buena configuración para practicar el timing sin gastar tus mejores linternas.",
        "Practice centering the beam on the Killer's face before trying hero saves. Good timing with a basic flashlight beats bad timing with an expensive one." to
                "Practica centrando el haz en la cara del Asesino antes de intentar rescates heroicos. Un buen timing con una linterna básica supera un mal timing con una cara.",

        "The Ranger Med-Kit is built for fast altruistic healing. Abdominal Dressing and Medical Scissors add more healing speed, while the perks help you find injured teammates and recover the team quickly after hooks." to
                "Ranger Med-Kit está pensado para curaciones altruistas rápidas. Abdominal Dressing y Medical Scissors aumentan la velocidad de curación, mientras que los perks ayudan a encontrar compañeros heridos y recuperar al equipo rápidamente después de los ganchos.",
        "Do not run across the map before healing. First break the Killer's line of sight, move behind nearby solid cover, then heal immediately if the chase has clearly moved away." to
                "No cruces todo el mapa antes de curar. Primero rompe la línea de visión del Asesino, ponte detrás de una cobertura sólida cercana y cura de inmediato si la persecución claramente se ha alejado.",
        "This setup gives excellent healing speed with easier-to-find add-ons and strong information for safe rescues." to
                "Esta configuración ofrece una excelente velocidad de curación con complementos más fáciles de conseguir y buena información para realizar rescates seguros.",
        "After an unhook, read the Killer first. If the Killer commits to another chase, heal under the hook or behind the nearest safe cover instead of automatically running to a distant corner." to
                "Después de un desenganche, observa primero al Asesino. Si se compromete con otra persecución, cura bajo el gancho o detrás de la cobertura segura más cercana en vez de correr automáticamente a una esquina lejana.",
        "The Camping Aid Kit and common add-ons give useful healing value without spending rare inventory. The perks provide rescue information and keep the build useful when healing is not needed." to
                "Camping Aid Kit y los complementos comunes ofrecen buena curación sin gastar inventario raro. Los perks aportan información para rescates y mantienen útil la configuración cuando no hace falta curar.",
        "Save Med-Kit charges for moments when healing speed matters. If a teammate is completely safe, a normal heal preserves the item for a dangerous recovery later." to
                "Guarda las cargas del botiquín para cuando la velocidad de curación realmente importe. Si un compañero está completamente seguro, una curación normal conserva el objeto para una recuperación peligrosa más adelante.",

        "The Commodious Toolbox carries a large charge pool, Wire Spool extends it, and Brand New Part can permanently cut a generator's remaining repair requirement. The perks help stretch item value and keep you focused on important generators." to
                "Commodious Toolbox tiene muchas cargas, Wire Spool las amplía y Brand New Part puede reducir permanentemente la reparación restante de un generador. Los perks ayudan a aprovechar el objeto y mantenerte centrado en los generadores importantes.",
        "Use Brand New Part on a generator you are committed to finishing, especially a dangerous central generator. Do not waste it on a safe edge generator that the team can finish later." to
                "Usa Brand New Part en un generador que estés decidido a terminar, especialmente uno central y peligroso. No lo desperdicies en un generador seguro del borde que el equipo pueda completar después.",
        "The Engineer's Toolbox burns through fewer charges very quickly, making it excellent when you need a short burst to finish a contested generator before the Killer returns." to
                "Engineer's Toolbox consume sus cargas muy rápido y es excelente cuando necesitas un impulso corto para terminar un generador disputado antes de que vuelva el Asesino.",
        "Do not dump the toolbox into the first generator you touch. Hold the fast charges for a generator that is nearly complete or one the Killer is actively trying to defend." to
                "No gastes la caja de herramientas en el primer generador que toques. Guarda las cargas rápidas para uno casi terminado o que el Asesino esté defendiendo activamente.",
        "A basic Toolbox with extra charges and repair speed is easy to replace and still gives meaningful generator pressure." to
                "Una Toolbox básica con cargas adicionales y velocidad de reparación es fácil de reemplazar y aun así aporta una presión significativa sobre los generadores.",
        "Use Deja Vu to identify the three generators that could become a late-game three-gen. Breaking that cluster early is often more valuable than simply repairing the closest generator." to
                "Usa Deja Vu para identificar los tres generadores que podrían formar un three-gen al final de la partida. Romper ese grupo pronto suele valer más que simplemente reparar el generador más cercano.",

        "Alex's Toolbox has exceptional sabotage speed. Hacksaw pushes the action faster, while Grip Wrench keeps the sabotaged hook unavailable longer. The perks help you identify the play, reach the carry path, and create extra wiggle pressure." to
                "Alex's Toolbox tiene una velocidad de sabotaje excepcional. Hacksaw acelera la acción y Grip Wrench mantiene el gancho saboteado fuera de servicio durante más tiempo. Los perks ayudan a identificar la jugada, llegar a la ruta de transporte y generar más presión de forcejeo.",
        "Do not sabotage before the Killer chooses a direction. Wait for the pickup, read the hook they are actually walking toward, then sprint ahead and start the sabotage late enough that the hook breaks just before they reach it." to
                "No sabotees antes de que el Asesino elija una dirección. Espera a la recogida, identifica el gancho hacia el que realmente camina y corre por delante para iniciar el sabotaje lo bastante tarde como para romperlo justo antes de que llegue.",
        "The Commodious Toolbox gives more charges than Alex's Toolbox while still having strong sabotage speed, making it better for players who want multiple attempts." to
                "Commodious Toolbox ofrece más cargas que Alex's Toolbox y mantiene una buena velocidad de sabotaje, por lo que funciona mejor para jugadores que quieren varios intentos.",
        "Approach from the side of the hook opposite the Killer's path. That forces the Killer to either hit you and lose distance or abandon the hook." to
                "Acércate por el lado del gancho opuesto a la ruta del Asesino. Eso lo obliga a golpearte y perder distancia o abandonar el gancho.",
        "This is an inexpensive way to practice hook-denial timing. Protective Gloves also keep the completed sabotage quieter." to
                "Es una forma económica de practicar el timing para negar ganchos. Protective Gloves también hacen más silencioso el sabotaje completado.",
        "If the Killer is already close enough to hit you and still reach the hook, cancel the play. A failed sabotage plus a free hit usually gives the Killer more value than the rescue attempt was worth." to
                "Si el Asesino ya está lo bastante cerca para golpearte y aun así llegar al gancho, cancela la jugada. Un sabotaje fallido más un golpe gratis suele darle más valor al Asesino del que valía el intento de rescate.",

        "This setup gives you information, fast post-rescue healing, and enough Med-Kit capacity to help multiple teammates while still contributing to generators." to
                "Esta configuración ofrece información, curación rápida después de rescates y suficiente capacidad de botiquín para ayudar a varios compañeros mientras sigues contribuyendo a los generadores.",
        "Before leaving a generator for a rescue, check whether another teammate is already closer. Good support means making the needed play, not sending three Survivors to the same hook." to
                "Antes de dejar un generador para rescatar, comprueba si otro compañero ya está más cerca. Dar buen apoyo significa hacer la jugada necesaria, no mandar a tres Supervivientes al mismo gancho.",
        "This keeps healing strong while giving you clear information about who needs help and where your generator pressure should go next." to
                "Mantiene una curación fuerte mientras te da información clara sobre quién necesita ayuda y dónde debes aplicar después la presión de generadores.",
        "Use the HUD and aura information before moving. If the injured Survivor is already safe and another teammate is rescuing, stay on your generator instead of creating unnecessary downtime." to
                "Usa el HUD y la información de auras antes de moverte. Si el Superviviente herido ya está seguro y otro compañero está rescatando, quédate en tu generador en vez de crear tiempo muerto innecesario.",
        "Everything in this setup is aimed at making better team decisions without requiring rare equipment." to
                "Todo en esta configuración está orientado a tomar mejores decisiones de equipo sin necesitar equipamiento raro.",
        "When Kindred shows the Killer leaving the hook, move early. Arriving just after the Killer leaves is safer and faster than waiting until the hooked Survivor is close to the next stage." to
                "Cuando Kindred muestre que el Asesino se aleja del gancho, muévete pronto. Llegar justo después de que se vaya es más seguro y rápido que esperar hasta que el Superviviente enganchado esté cerca de la siguiente fase.",

        "Vigo's Fog Vial can suppress Scratch Marks and Auras inside its cloud while also obscuring sound and visibility. Oily Sap extends the cloud and Mushroom Formula makes it larger, giving you more room to break tracking." to
                "Vigo's Fog Vial puede ocultar marcas de arañazos y auras dentro de su nube, además de dificultar el sonido y la visibilidad. Oily Sap prolonga la nube y Mushroom Formula la hace más grande, dándote más espacio para romper el rastreo.",
        "Do not throw the Fog Vial while running in a straight line with the Killer watching you. Break line of sight first, release the cloud at a junction or obstacle, then change direction inside it so the Killer has to guess which exit you took." to
                "No lances Fog Vial mientras corres en línea recta con el Asesino mirándote. Rompe primero la línea de visión, libera la nube en una intersección u obstáculo y cambia de dirección dentro para obligarlo a adivinar por dónde saliste.",
        "The Artisan's Fog Vial expands quickly and lasts long enough to create a strong pathing mix-up. The perk package helps hide your trail after vaults and locker plays." to
                "Artisan's Fog Vial se expande rápidamente y dura lo suficiente para crear una fuerte confusión de rutas. El conjunto de perks ayuda a ocultar tu rastro después de saltos y jugadas con taquillas.",
        "Use the cloud to cover a decision point, not an empty field. A window, pallet, doorway, or split path gives you multiple believable escape routes." to
                "Usa la nube para cubrir un punto de decisión, no un campo vacío. Una ventana, pallet, puerta o camino dividido te ofrece varias rutas de escape creíbles.",
        "The common Fog Vial still suppresses key tracking information and gives newer players a practical way to learn line-of-sight breaks." to
                "El Fog Vial común sigue ocultando información clave de rastreo y ofrece a los jugadores nuevos una forma práctica de aprender a romper la línea de visión.",
        "After losing line of sight, stop giving the Killer free information. Avoid immediately fast-vaulting or sprinting through open ground unless the move creates real distance." to
                "Después de romper la línea de visión, deja de regalar información al Asesino. Evita hacer inmediatamente un salto rápido o correr por terreno abierto salvo que el movimiento genere distancia real."
    )
}