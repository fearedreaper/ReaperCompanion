package com.example.reapercompanion.itemcoach

object ItemCoachEngine {

    fun generate(
        goalId: String
    ): ItemCoachRecommendationSet {
        return when (goalId) {

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
    }
}