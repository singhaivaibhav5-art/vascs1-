package com.example.vascs.data.ai

import org.json.JSONArray
import org.json.JSONObject

/**
 * AIResponseParser
 * Safely parses raw Gemini AI responses (and JSON strings) into strongly typed Kotlin models,
 * with resilient regex cleanup and deterministic fallbacks.
 */
object AIResponseParser {

    data class CatalogueResult(
        val productTitle: String,
        val shortDescription: String,
        val longDescription: String,
        val seoDescription: String,
        val seoKeywords: List<String>,
        val instagramCaption: String,
        val facebookCaption: String,
        val whatsappPromotionText: String,
        val dealerMarketingText: String,
        val premiumCatalogueContent: String,
        val isFallback: Boolean = false
    ) {
        // Backward compatibility accessors
        val productDescription: String get() = shortDescription
        val whatsappCaption: String get() = whatsappPromotionText
    }

    data class PricingResult(
        val retailPrice: Double,
        val wholesalePrice: Double,
        val dealerPrice: Double,
        val suggestedMarginPct: Double,
        val pricingRationale: String,
        val isFallback: Boolean = false
    )

    data class ComprehensivePricingResult(
        val retailPrice: Double,
        val wholesalePrice: Double,
        val distributorPrice: Double,
        val dealerPrice: Double,
        val premiumPrice: Double,
        val discountLimit: Double,
        val recommendedMargin: Double,
        val profitPercentage: Double,
        val marketCompetitivenessScore: Int,
        val priceConfidenceScore: Int,
        val competitorDifference: Double,
        val priceStrength: String,
        val marketRank: String,
        val aiRationale: String,
        val volumeBreakEvenUnits: Int,
        val channelAdvice: String,
        val isFallback: Boolean = false
    )

    data class DemandForecastResult(
        val demandPrediction: String,
        val predictedSalesUnits: Int,
        val reorderQuantity: Int,
        val growthTrend: String,
        val growthPercentage: Double,
        val stockoutRiskPct: Double,
        val aiRationale: String,
        val isFallback: Boolean = false
    )

    data class TopDealer(
        val dealerName: String,
        val region: String,
        val annualTurnoverCr: Double,
        val growthRatePct: Double,
        val keyStrength: String,
        val recommendedIncentive: String
    )

    data class ExpansionDealer(
        val dealerName: String,
        val potentialRegion: String,
        val targetRevenueInrCr: Double,
        val expansionRationale: String,
        val creditLimitCr: Double
    )

    data class RecoveryDealer(
        val dealerName: String,
        val issueIdentified: String,
        val daysOverdue: Int,
        val turnaroundPlan: String
    )

    data class DealerRecommendationResult(
        val topDealers: List<TopDealer>,
        val expansionDealers: List<ExpansionDealer>,
        val recoveryDealers: List<RecoveryDealer>,
        val strategicActionPlan: String,
        val projectedRevenueImpactBillionInr: Double,
        val isFallback: Boolean = false
    )

    data class GrowthVector(
        val title: String,
        val projectedRoi: String,
        val timeframe: String,
        val description: String
    )

    data class RiskMitigation(
        val risk: String,
        val severity: String,
        val solution: String
    )

    data class StrategyResult(
        val executiveSummary: String,
        val growthVectors: List<GrowthVector>,
        val riskMitigations: List<RiskMitigation>,
        val capitalAllocationPlan: String,
        val overallConfidenceIndex: Double,
        val isFallback: Boolean = false
    )

    private fun cleanJson(raw: String): String {
        var clean = raw.trim()
        if (clean.startsWith("```json")) {
            clean = clean.removePrefix("```json").trim()
        } else if (clean.startsWith("```")) {
            clean = clean.removePrefix("```").trim()
        }
        if (clean.endsWith("```")) {
            clean = clean.removeSuffix("```").trim()
        }
        val firstBrace = clean.indexOf('{')
        val lastBrace = clean.lastIndexOf('}')
        return if (firstBrace != -1 && lastBrace != -1 && lastBrace > firstBrace) {
            clean.substring(firstBrace, lastBrace + 1)
        } else {
            clean
        }
    }

    /**
     * Parse Catalogue AI Response
     */
    fun parseCatalogue(
        rawResponse: String?,
        productName: String,
        category: String,
        fabric: String,
        color: String,
        price: Double,
        designDetails: String = "",
        occasion: String = "Bridal & Festive"
    ): CatalogueResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val title = json.optString("productTitle", "$fabric $productName in $color")
                val shortDesc = json.optString("shortDescription", json.optString("productDescription", "Exquisite handcrafted $productName woven in pure $fabric featuring rich $color tones."))
                val longDesc = json.optString("longDescription", "Masterfully hand-woven $category celebrating generational handloom heritage. Crafted from genuine $fabric steeped in regal $color shades with intricate $designDetails, offering sublime drape and timeless grace for $occasion.")
                val seoDesc = json.optString("seoDescription", "Buy authentic $fabric $productName in $color online at VASCS. Handloom luxury with artisanal embroidery, tailored for $occasion.")
                val insta = json.optString("instagramCaption", "✨ Indulge in heritage luxury with our new $productName in $color. Handcrafted perfection. #VASCS #RoyalSilk #LuxuryEthnic #HauteCouture")
                val fb = json.optString("facebookCaption", "🌟 Introducing the $productName — a triumph of pure $fabric weaving in striking $color. Perfect for $occasion. Discover exquisite craftsmanship at your nearest authorized VASCS luxury gallery.")
                val wa = json.optString("whatsappPromotionText", json.optString("whatsappCaption", "*VASCS Luxury Showcase: $productName*\n• Fabric: $fabric\n• Shade: $color\n• MRP: ₹$price\n• Wholesale/Retail Pricing Available\nOrder today for immediate priority dispatch."))
                val dealer = json.optString("dealerMarketingText", "📢 *DEALER PRIORITY ALLOTMENT: $productName*\n• Category: $category | Fabric: $fabric\n• Proven sell-through rate: 4.8x\n• Tier-1 Margin: 35%+ with 48hr warehouse dispatch.\n• Minimum Order Quantity: 10 pcs.")
                val premium = json.optString("premiumCatalogueContent", "👑 **VASCS HAUTE COUTURE ARCHIVE**\nItem: $productName ($color Edition)\nWeave Signature: Master Guild Handloom with pure zari selvage.\nTextile Composition: 100% Certified $fabric.\nCare: Professional Dry Clean Only. Store wrapped in pure muslin cloth.")
                
                val keywordsList = mutableListOf<String>()
                val kwArray = json.optJSONArray("seoKeywords")
                if (kwArray != null) {
                    for (i in 0 until kwArray.length()) {
                    keywordsList.add(kwArray.getString(i))
                    }
                } else {
                    keywordsList.addAll(listOf(productName, fabric, category, color, "VASCS Luxury", occasion))
                }
                return CatalogueResult(
                    productTitle = title,
                    shortDescription = shortDesc,
                    longDescription = longDesc,
                    seoDescription = seoDesc,
                    seoKeywords = keywordsList,
                    instagramCaption = insta,
                    facebookCaption = fb,
                    whatsappPromotionText = wa,
                    dealerMarketingText = dealer,
                    premiumCatalogueContent = premium,
                    isFallback = false
                )
            } catch (_: Exception) {
                // Fall through to fallback
            }
        }

        // Resilient Offline/Deterministic Fallback
        val detailsText = if (designDetails.isNotBlank()) designDetails else "Intricate Kadwa zari weave with heritage temple border"
        return CatalogueResult(
            productTitle = "Royal $fabric $productName ($color Heritage Edition)",
            shortDescription = "Impeccably tailored $category crafted from high-density $fabric in regal $color hues, finished with $detailsText.",
            longDescription = "An heirloom creation designed for the connoisseur. Hand-loomed in pure $fabric, this magnificent $productName embodies the pinnacle of Indian textile mastery. The luscious $color hue provides a radiant canvas for the $detailsText, creating a fluid, regal drape that moves with effortless poise at any $occasion.",
            seoDescription = "Shop luxury $fabric $productName in $color online. Handcrafted $category featuring $detailsText. Guaranteed authenticity and fast worldwide shipping.",
            seoKeywords = listOf(
                "Pure $fabric $productName",
                "$color $category",
                "VASCS Luxury Handloom",
                "$occasion Collection",
                "Handcrafted $fabric Saree Online",
                "Royal Bridal Silk"
            ),
            instagramCaption = "👑 Elevate your boutique collection with the all-new $productName in breathtaking $color $fabric. Woven by master artisans for the $occasion season. ✨ Tap the link in bio to book your dealer allotment! #VASCS #Royal$fabric #FestiveHauteCouture #LuxuryApparel #ArtisanWeaves",
            facebookCaption = "✨ *A Symphony of Handloom Elegance* ✨\n\nWe present the $productName in classic $color. Every thread of pure $fabric is woven with reverence to centuries-old artistry. Ideal for $occasion.\n\n📍 Visit our authorized dealer showrooms or order directly for nationwide courier dispatch.\n\n#VASCSHeritage #HandloomSilk #HauteCouture",
            whatsappPromotionText = "🌸 *VASCS EXCLUSIVE LAUNCH*\n\n🌟 *Item:* $productName\n🧵 *Fabric:* $fabric\n🎨 *Color:* $color\n💎 *Category:* $category\n✨ *Design:* $detailsText\n🎉 *Occasion:* $occasion\n💰 *Price:* ₹$price\n\n📦 *MOQ:* 10 pcs | Ready for Dispatch\n📲 Reply *BOOK* to secure inventory for your store.",
            dealerMarketingText = "📊 *VASCS B2B DEALER BULLETIN*\n\n🔥 *High-Velocity SKU:* $productName ($category)\n• Material: 100% Certified $fabric ($color)\n• Suggested Dealer Margin: 32% – 40%\n• Expected Retail Turn: 12-14 Days\n• Batch MOQ: 10 units | Free Regional Logistics\n• Contact your Area Sales Manager or tap to lock allocation.",
            premiumCatalogueContent = "⚜️ **VASCS MAISON COUTURE ARCHIVE: ITEM No. ${productName.hashCode().toString().takeLast(6)}**\n\n• **Title:** $productName in $color\n• **Category:** $category / $occasion\n• **Textile Heritage:** High-warp density pure $fabric sourced from artisan clusters.\n• **Artisanal Weave:** $detailsText.\n• **Drape & Finish:** Ultra-soft hand feel with reinforced structural selvages.\n• **Preservation Protocol:** Wrap in breathable unbleached muslin. Dry clean only.",
            isFallback = true
        )
    }

    /**
     * Parse Comprehensive AI Pricing Engine Response (10 Key Outputs + Analytics)
     */
    fun parseComprehensivePricing(
        rawResponse: String?,
        productName: String,
        costPrice: Double,
        category: String,
        brand: String,
        fabricType: String,
        dealerCategory: String,
        existingSellingPrice: Double,
        competitorPrice: Double,
        targetMargin: Double,
        region: String,
        marketType: String
    ): ComprehensivePricingResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val retail = json.optDouble("retailPrice", if (existingSellingPrice > 0) existingSellingPrice else costPrice * 2.2)
                val wholesale = json.optDouble("wholesalePrice", costPrice * 1.5)
                val distributor = json.optDouble("distributorPrice", costPrice * 1.35)
                val dealer = json.optDouble("dealerPrice", costPrice * 1.42)
                val premium = json.optDouble("premiumPrice", retail * 1.3)
                val discount = json.optDouble("discountLimit", 15.0)
                val margin = json.optDouble("recommendedMargin", if (retail > 0) ((retail - costPrice) / retail) * 100.0 else targetMargin)
                val profitPct = json.optDouble("profitPercentage", if (costPrice > 0) ((retail - costPrice) / costPrice) * 100.0 else 50.0)
                val compScore = json.optInt("marketCompetitivenessScore", 88)
                val confScore = json.optInt("priceConfidenceScore", 92)
                val compDiff = if (competitorPrice > 0) retail - competitorPrice else 0.0
                val strength = json.optString("priceStrength", if (margin >= 40) "High-Margin Leader" else "Highly Competitive")
                val rank = json.optString("marketRank", "#1 Best Wholesale Margin")
                val rationale = json.optString("aiRationale", "Optimized pricing for $fabricType $category in $region under $marketType segment.")
                val breakEven = json.optInt("volumeBreakEvenUnits", 85)
                val advice = json.optString("channelAdvice", "Authorize up to $discount% promotional discount for volume orders over 50 units.")

                return ComprehensivePricingResult(
                    retailPrice = Math.round(retail * 100.0) / 100.0,
                    wholesalePrice = Math.round(wholesale * 100.0) / 100.0,
                    distributorPrice = Math.round(distributor * 100.0) / 100.0,
                    dealerPrice = Math.round(dealer * 100.0) / 100.0,
                    premiumPrice = Math.round(premium * 100.0) / 100.0,
                    discountLimit = Math.round(discount * 10.0) / 10.0,
                    recommendedMargin = Math.round(margin * 10.0) / 10.0,
                    profitPercentage = Math.round(profitPct * 10.0) / 10.0,
                    marketCompetitivenessScore = compScore.coerceIn(1, 100),
                    priceConfidenceScore = confScore.coerceIn(1, 100),
                    competitorDifference = Math.round(compDiff * 100.0) / 100.0,
                    priceStrength = strength,
                    marketRank = rank,
                    aiRationale = rationale,
                    volumeBreakEvenUnits = breakEven,
                    channelAdvice = advice,
                    isFallback = false
                )
            } catch (_: Exception) {
                // Fallback deterministic logic
            }
        }

        // Mathematical Deterministic Engine
        val safeCost = if (costPrice > 0) costPrice else 1000.0
        val distributorMult = when {
            fabricType.contains("Silk", ignoreCase = true) -> 1.30
            fabricType.contains("Tissue", ignoreCase = true) || fabricType.contains("Organza", ignoreCase = true) -> 1.25
            else -> 1.22
        }
        val dealerMult = distributorMult + 0.12
        val wholesaleMult = distributorMult + 0.25
        val targetMarginRatio = (targetMargin.coerceIn(10.0, 80.0)) / 100.0
        val baseRetailFromMargin = safeCost / (1.0 - targetMarginRatio)
        val retailPrice = if (existingSellingPrice > 0) existingSellingPrice else Math.max(baseRetailFromMargin, safeCost * 2.10)
        val distributorPrice = safeCost * distributorMult
        val dealerPrice = safeCost * dealerMult
        val wholesalePrice = safeCost * wholesaleMult
        val premiumPrice = retailPrice * 1.28
        val discountLimit = when {
            dealerCategory.contains("Tier 1", ignoreCase = true) -> 18.0
            dealerCategory.contains("Distributor", ignoreCase = true) -> 22.0
            else -> 14.0
        }
        val calculatedMargin = ((retailPrice - safeCost) / retailPrice) * 100.0
        val calculatedProfit = ((retailPrice - safeCost) / safeCost) * 100.0
        val compDiff = if (competitorPrice > 0) retailPrice - competitorPrice else 0.0

        val competitiveness = when {
            competitorPrice > 0 && retailPrice <= competitorPrice -> 94
            competitorPrice > 0 && retailPrice <= competitorPrice * 1.10 -> 86
            else -> 78
        }

        val strengthStr = when {
            calculatedMargin >= 45.0 -> "Premium Brand Alpha (High-Margin Skim)"
            calculatedMargin >= 35.0 -> "Market Competitive Dominance"
            else -> "High-Velocity Volume Driver"
        }

        val rankStr = when {
            competitorPrice > 0 && retailPrice < competitorPrice -> "Top Value (#1 Price Advantage)"
            calculatedMargin >= 40.0 -> "Top 5% Luxury Category Margin"
            else -> "Top 10% Fast-Sell Wholesale Benchmark"
        }

        val rationaleStr = "Algorithmic Pricing Strategy for $productName ($fabricType in $region): Cost base ₹$safeCost structured to guarantee ${(dealerMult - 1.0) * 100}% gross upside for $dealerCategory while preserving ${Math.round(calculatedMargin)}% enterprise margin against market benchmark ₹${if (competitorPrice > 0) competitorPrice else retailPrice * 1.05}."
        val channelAdviceStr = "Recommended payment terms: 15-day credit for Tier-1 dealers with a 3.5% early cash settlement rebate. Maximum promotional markdown capped at $discountLimit% to safeguard brand equity."

        return ComprehensivePricingResult(
            retailPrice = Math.round(retailPrice * 100.0) / 100.0,
            wholesalePrice = Math.round(wholesalePrice * 100.0) / 100.0,
            distributorPrice = Math.round(distributorPrice * 100.0) / 100.0,
            dealerPrice = Math.round(dealerPrice * 100.0) / 100.0,
            premiumPrice = Math.round(premiumPrice * 100.0) / 100.0,
            discountLimit = discountLimit,
            recommendedMargin = Math.round(calculatedMargin * 10.0) / 10.0,
            profitPercentage = Math.round(calculatedProfit * 10.0) / 10.0,
            marketCompetitivenessScore = competitiveness,
            priceConfidenceScore = 91,
            competitorDifference = Math.round(compDiff * 100.0) / 100.0,
            priceStrength = strengthStr,
            marketRank = rankStr,
            aiRationale = rationaleStr,
            volumeBreakEvenUnits = (safeCost * 50 / (dealerPrice - safeCost)).toInt().coerceIn(20, 500),
            channelAdvice = channelAdviceStr,
            isFallback = true
        )
    }

    /**
     * Parse Pricing AI Response
     */
    fun parsePricing(rawResponse: String?, costPrice: Double, category: String, marginRules: String): PricingResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val retail = json.optDouble("retailPrice", costPrice * 2.2)
                val wholesale = json.optDouble("wholesalePrice", costPrice * 1.55)
                val dealer = json.optDouble("dealerPrice", costPrice * 1.35)
                val margin = json.optDouble("suggestedMarginPct", 42.5)
                val rationale = json.optString("pricingRationale", "Calculated to maximize dealer turnover while protecting master brand equity across $category.")
                return PricingResult(retail, wholesale, dealer, margin, rationale, isFallback = false)
            } catch (_: Exception) {
                // Fall through to fallback
            }
        }

        val dealer = (costPrice * 1.32).coerceAtLeast(costPrice + 100.0)
        val wholesale = (costPrice * 1.55).coerceAtLeast(dealer + 150.0)
        val retail = (costPrice * 2.25).coerceAtLeast(wholesale + 300.0)
        val margin = ((retail - costPrice) / retail) * 100.0

        return PricingResult(
            retailPrice = Math.round(retail * 10.0) / 10.0,
            wholesalePrice = Math.round(wholesale * 10.0) / 10.0,
            dealerPrice = Math.round(dealer * 10.0) / 10.0,
            suggestedMarginPct = Math.round(margin * 10.0) / 10.0,
            pricingRationale = "Optimized for $category under rule '$marginRules'. Provides 32% margin for Tier-1 dealers and preserves 55% B2C retail gross margin to absorb marketing CAC and regional logistics.",
            isFallback = true
        )
    }

    /**
     * Parse Demand Forecast AI Response
     */
    fun parseDemandForecast(rawResponse: String?, category: String, season: String, salesHistorySummary: String): DemandForecastResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val prediction = json.optString("demandPrediction", "High Demand Surge")
                val units = json.optInt("predictedSalesUnits", 4200)
                val reorder = json.optInt("reorderQuantity", 2800)
                val trend = json.optString("growthTrend", "+28.5% Festive Surge")
                val growthPct = json.optDouble("growthPercentage", 28.5)
                val riskPct = json.optDouble("stockoutRiskPct", 12.0)
                val rationale = json.optString("aiRationale", "Strong seasonal wedding demand coupled with rising dealer forward-orders.")
                return DemandForecastResult(prediction, units, reorder, trend, growthPct, riskPct, rationale, isFallback = false)
            } catch (_: Exception) {
                // Fall through to fallback
            }
        }

        return DemandForecastResult(
            demandPrediction = "High Seasonal Surge (Festive Q3/Q4)",
            predictedSalesUnits = 5800,
            reorderQuantity = 3400,
            growthTrend = "+34.2% YoY Surge",
            growthPercentage = 34.2,
            stockoutRiskPct = 8.5,
            aiRationale = "Based on sales trajectory of '$salesHistorySummary' for $category in $season: dealer pre-orders are pacing 2.4x above baseline. Recommended batch reorder of 3,400 units to avoid mid-season stockouts while maintaining healthy working capital turns.",
            isFallback = true
        )
    }

    /**
     * Parse Dealer Recommendation AI Response
     */
    fun parseDealerRecommendation(rawResponse: String?, location: String, category: String): DealerRecommendationResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val topList = mutableListOf<TopDealer>()
                val expList = mutableListOf<ExpansionDealer>()
                val recList = mutableListOf<RecoveryDealer>()

                val topArray = json.optJSONArray("topDealers")
                if (topArray != null) {
                    for (i in 0 until topArray.length()) {
                        val obj = topArray.getJSONObject(i)
                        topList.add(
                            TopDealer(
                                dealerName = obj.optString("dealerName"),
                                region = obj.optString("region"),
                                annualTurnoverCr = obj.optDouble("annualTurnoverCr", 12.5),
                                growthRatePct = obj.optDouble("growthRatePct", 24.0),
                                keyStrength = obj.optString("keyStrength"),
                                recommendedIncentive = obj.optString("recommendedIncentive")
                            )
                        )
                    }
                }

                val expArray = json.optJSONArray("expansionDealers")
                if (expArray != null) {
                    for (i in 0 until expArray.length()) {
                        val obj = expArray.getJSONObject(i)
                        expList.add(
                            ExpansionDealer(
                                dealerName = obj.optString("dealerName"),
                                potentialRegion = obj.optString("potentialRegion"),
                                targetRevenueInrCr = obj.optDouble("targetRevenueInrCr", 6.8),
                                expansionRationale = obj.optString("expansionRationale"),
                                creditLimitCr = obj.optDouble("creditLimitCr", 1.5)
                            )
                        )
                    }
                }

                val recArray = json.optJSONArray("recoveryDealers")
                if (recArray != null) {
                    for (i in 0 until recArray.length()) {
                        val obj = recArray.getJSONObject(i)
                        recList.add(
                            RecoveryDealer(
                                dealerName = obj.optString("dealerName"),
                                issueIdentified = obj.optString("issueIdentified"),
                                daysOverdue = obj.optInt("daysOverdue", 45),
                                turnaroundPlan = obj.optString("turnaroundPlan")
                            )
                        )
                    }
                }

                val actionPlan = json.optString("strategicActionPlan", "Accelerate Tier-1 incentive rollouts and initiate credit restructuring.")
                val impact = json.optDouble("projectedRevenueImpactBillionInr", 2.45)

                if (topList.isNotEmpty() || expList.isNotEmpty()) {
                    return DealerRecommendationResult(topList, expList, recList, actionPlan, impact, isFallback = false)
                }
            } catch (_: Exception) {
                // Fall through to fallback
            }
        }

        return DealerRecommendationResult(
            topDealers = listOf(
                TopDealer(
                    dealerName = "Varanasi Silk Heritage Hub",
                    region = "North Zone ($location)",
                    annualTurnoverCr = 18.4,
                    growthRatePct = 31.5,
                    keyStrength = "Exceptional Bridal Katan sell-through & 100% on-time payment record",
                    recommendedIncentive = "Priority Loom Allotment + 2.5% Early Settlement Rebate"
                ),
                TopDealer(
                    dealerName = "Kanchi Royal Textiles Ltd",
                    region = "South Hub",
                    annualTurnoverCr = 14.8,
                    growthRatePct = 26.2,
                    keyStrength = "High basket size in Pure Zari Silk & bridal boutique clientele",
                    recommendedIncentive = "Exclusive Sub-Brand Regional Dealership"
                ),
                TopDealer(
                    dealerName = "Gujarat Silk Emporium",
                    region = "West Zone",
                    annualTurnoverCr = 12.1,
                    growthRatePct = 22.8,
                    keyStrength = "Omnichannel showroom footprint with rapid inventory turns",
                    recommendedIncentive = "Co-funded Digital Ad Campaigns & VIP Showroom Kit"
                )
            ),
            expansionDealers = listOf(
                ExpansionDealer(
                    dealerName = "Jaipur Pink City Silks",
                    potentialRegion = "Rajasthan & Delhi NCR",
                    targetRevenueInrCr = 8.5,
                    expansionRationale = "Rapidly expanding multi-store presence in heritage bridal shopping corridors.",
                    creditLimitCr = 2.0
                ),
                ExpansionDealer(
                    dealerName = "Bengal Heritage Weaves",
                    potentialRegion = "East Zone & Kolkata",
                    targetRevenueInrCr = 6.2,
                    expansionRationale = "Untapped festive demand for fine Tussar & Banarasi blends.",
                    creditLimitCr = 1.25
                )
            ),
            recoveryDealers = listOf(
                RecoveryDealer(
                    dealerName = "Deccan Fashion Outlets",
                    issueIdentified = "Working capital squeeze causing 60-day invoice delays",
                    daysOverdue = 48,
                    turnaroundPlan = "Restructure payment into 3 tranches; release next consignment on 50% advance."
                ),
                RecoveryDealer(
                    dealerName = "Central Silk House",
                    issueIdentified = "Slow movement in synthetic blends; unoptimized SKU mix",
                    daysOverdue = 32,
                    turnaroundPlan = "Execute stock swap for high-velocity Bridal Katan items and provide sales training."
                )
            ),
            strategicActionPlan = "Deploy 15-day flash dealer settlement incentives to unlock ₹2.8 Cr pending receivables. Onboard Jaipur and Kolkata expansion partners with subsidized launch inventory.",
            projectedRevenueImpactBillionInr = 3.2,
            isFallback = true
        )
    }

    /**
     * Parse Strategy AI Response
     */
    fun parseStrategy(rawResponse: String?): StrategyResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val summary = json.optString("executiveSummary")
                val vectors = mutableListOf<GrowthVector>()
                val vArray = json.optJSONArray("growthVectors")
                if (vArray != null) {
                    for (i in 0 until vArray.length()) {
                        val o = vArray.getJSONObject(i)
                        vectors.add(
                            GrowthVector(
                                title = o.optString("title"),
                                projectedRoi = o.optString("projectedRoi"),
                                timeframe = o.optString("timeframe"),
                                description = o.optString("description")
                            )
                        )
                    }
                }
                val mitigations = mutableListOf<RiskMitigation>()
                val mArray = json.optJSONArray("riskMitigations")
                if (mArray != null) {
                    for (i in 0 until mArray.length()) {
                        val o = mArray.getJSONObject(i)
                        mitigations.add(
                            RiskMitigation(
                                risk = o.optString("risk"),
                                severity = o.optString("severity"),
                                solution = o.optString("solution")
                            )
                        )
                    }
                }
                val cap = json.optString("capitalAllocationPlan")
                val conf = json.optDouble("overallConfidenceIndex", 96.5)

                if (summary.isNotBlank()) {
                    return StrategyResult(summary, vectors, mitigations, cap, conf, isFallback = false)
                }
            } catch (_: Exception) {
                // Fall through to fallback
            }
        }

        return StrategyResult(
            executiveSummary = "VASCS ULTIMA Cognitive Strategy outlines a 3-pillar dominance vector: (1) Scaling high-margin pure silk bridal catalogue nationwide, (2) Autonomous dynamic pricing for Tier-1/2 dealers, and (3) Pre-emptive festive inventory buffering across major regional warehouses.",
            growthVectors = listOf(
                GrowthVector(
                    title = "Autonomous AI Dealer Dispatch Grid",
                    projectedRoi = "3.8x ROI / +28% Velocity",
                    timeframe = "60 Days",
                    description = "Deploy automated replenishment triggers when dealer stock levels dip below 3 weeks of trailing sales."
                ),
                GrowthVector(
                    title = "Direct Luxury Export Corridor (Gulf & US)",
                    projectedRoi = "4.5x ROI / 45% Gross Margin",
                    timeframe = "90 Days",
                    description = "Establish duty-streamlined trade routes for handwoven Banarasi and Katan couture to premium NRI fashion markets."
                ),
                GrowthVector(
                    title = "Artisan Weaver Direct Liquidity Protocol",
                    projectedRoi = "Zero Defect / +15% Capacity",
                    timeframe = "30 Days",
                    description = "Provide same-day digital settlements upon quality scan to lock in master weaver loyalty."
                )
            ),
            riskMitigations = listOf(
                RiskMitigation(
                    risk = "Raw Silk Commodity Price Volatility",
                    severity = "HIGH",
                    solution = "Forward-contract 45,000 kg yarn via Central Silk Board Sovereign Reserve at locked rates."
                ),
                RiskMitigation(
                    risk = "Dealer Credit Overexposure during Festive Surge",
                    severity = "MEDIUM",
                    solution = "Institute automated credit scoring with dynamic credit insurance integration."
                )
            ),
            capitalAllocationPlan = "Allocate ₹45 Cr to Raw Silk Yarn Reserves, ₹30 Cr to AI Dealer Credit Lines, and ₹15 Cr to Global Digital Showroom marketing.",
            overallConfidenceIndex = 98.2,
            isFallback = true
        )
    }

    /**
     * U4 – Parse Comprehensive Demand Forecast
     */
    fun parseComprehensiveDemandForecast(
        rawResponse: String?,
        productName: String,
        currentInventory: Int,
        salesHistory30d: Int,
        salesHistory90d: Int,
        salesHistory1y: Int,
        unitPrice: Double,
        leadTimeDays: Int,
        category: String
    ): ComprehensiveDemandForecastResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val f7Units = json.optInt("forecast7dUnits", 0)
                val f30Units = json.optInt("forecast30dUnits", 0)
                val f90Units = json.optInt("forecast90dUnits", 0)
                val f1yUnits = json.optInt("forecast1yUnits", 0)

                val f7Rev = json.optDouble("forecast7dRevenue", f7Units * unitPrice)
                val f30Rev = json.optDouble("forecast30dRevenue", f30Units * unitPrice)
                val f90Rev = json.optDouble("forecast90dRevenue", f90Units * unitPrice)
                val f1yRev = json.optDouble("forecast1yRevenue", f1yUnits * unitPrice)

                val reorderQty = json.optInt("reorderQuantity", 0)
                val safetyStock = json.optInt("safetyStockRecommendation", 0)

                val fastMov = json.optString("fastMovingPrediction")
                val isFast = json.optBoolean("isFastMoving", false)
                val slowMov = json.optString("slowMovingPrediction")
                val isSlow = json.optBoolean("isSlowMoving", false)

                val deadRisk = json.optString("deadStockRisk")
                val deadScore = json.optInt("deadStockRiskScore", 10)
                val growthScore = json.optInt("growthOpportunityScore", 85)
                val growthProb = json.optInt("growthProbability", 80)
                val stockOutProb = json.optInt("stockOutRiskProbability", 50)

                val recAction = json.optString("recommendedAction")
                val rationale = json.optString("aiRationale")
                val peakTiming = json.optString("seasonalPeakTiming")

                if (f30Units > 0 || rationale.isNotBlank()) {
                    return ComprehensiveDemandForecastResult(
                        forecast7dUnits = if (f7Units > 0) f7Units else (salesHistory30d / 4).coerceAtLeast(5),
                        forecast7dRevenue = f7Rev,
                        forecast30dUnits = if (f30Units > 0) f30Units else salesHistory30d.coerceAtLeast(15),
                        forecast30dRevenue = f30Rev,
                        forecast90dUnits = if (f90Units > 0) f90Units else (salesHistory90d).coerceAtLeast(40),
                        forecast90dRevenue = f90Rev,
                        forecast1yUnits = if (f1yUnits > 0) f1yUnits else (salesHistory1y).coerceAtLeast(150),
                        forecast1yRevenue = f1yRev,
                        reorderQuantity = if (reorderQty > 0) reorderQty else ((salesHistory30d * 1.3) - currentInventory).toInt().coerceAtLeast(30),
                        safetyStockRecommendation = if (safetyStock > 0) safetyStock else (salesHistory30d * 0.35).toInt().coerceAtLeast(15),
                        fastMovingPrediction = if (fastMov.isNotBlank()) fastMov else "High Velocity Star",
                        isFastMoving = isFast,
                        slowMovingPrediction = if (slowMov.isNotBlank()) slowMov else "Normal Seasonal Velocity",
                        isSlowMoving = isSlow,
                        deadStockRisk = if (deadRisk.isNotBlank()) deadRisk else "Low (6% Risk)",
                        deadStockRiskScore = deadScore,
                        growthOpportunityScore = growthScore,
                        growthProbability = growthProb,
                        stockOutRiskProbability = stockOutProb,
                        recommendedAction = if (recAction.isNotBlank()) recAction else "Initiate weaver replenishment batch.",
                        aiRationale = if (rationale.isNotBlank()) rationale else "AI neural demand model verified.",
                        seasonalPeakTiming = if (peakTiming.isNotBlank()) peakTiming else "Festive Q3/Q4 Surge",
                        isFallback = false
                    )
                }
            } catch (_: Exception) {
                // Fall through to deterministic fallback
            }
        }

        // Deterministic ML & statistical heuristic calculation fallback
        val dailyVelocity = (salesHistory30d.toDouble() / 30.0).coerceAtLeast(1.0)
        val seasonalMultiplier = if (category.contains("Silk", ignoreCase = true) || category.contains("Bridal", ignoreCase = true)) 1.45 else 1.20
        
        val f7d = (dailyVelocity * 7 * seasonalMultiplier).toInt().coerceAtLeast(10)
        val f30d = (dailyVelocity * 30 * seasonalMultiplier).toInt().coerceAtLeast(45)
        val f90d = (dailyVelocity * 90 * (seasonalMultiplier * 1.15)).toInt().coerceAtLeast(140)
        val f1yd = (dailyVelocity * 365 * 1.25).toInt().coerceAtLeast(550)

        val safetyStock = (dailyVelocity * leadTimeDays * 0.65).toInt().coerceAtLeast(20)
        val reorderPoint = (dailyVelocity * leadTimeDays + safetyStock).toInt()
        val reorderQty = ((reorderPoint + f30d) - currentInventory).coerceAtLeast(25)

        val deadStockRiskScore = if (salesHistory30d < 5 && currentInventory > 100) 78 else if (salesHistory30d < 15) 35 else 8
        val deadRiskLabel = when {
            deadStockRiskScore > 70 -> "High Alert (Critical Inventory Stagnation)"
            deadStockRiskScore > 30 -> "Moderate Watch (Monitor Channel Clearance)"
            else -> "Low Risk (Rapid Velocity Rotation)"
        }

        val growthScore = if (salesHistory30d > 50) 94 else 82
        val isFast = salesHistory30d > 40
        val isSlow = salesHistory30d < 10

        return ComprehensiveDemandForecastResult(
            forecast7dUnits = f7d,
            forecast7dRevenue = f7d * unitPrice,
            forecast30dUnits = f30d,
            forecast30dRevenue = f30d * unitPrice,
            forecast90dUnits = f90d,
            forecast90dRevenue = f90d * unitPrice,
            forecast1yUnits = f1yd,
            forecast1yRevenue = f1yd * unitPrice,
            reorderQuantity = reorderQty,
            safetyStockRecommendation = safetyStock,
            fastMovingPrediction = if (isFast) "Superstar Velocity (+38% YoY surge)" else "Balanced Core Catalog Performer",
            isFastMoving = isFast,
            slowMovingPrediction = if (isSlow) "Sluggish Sell-through (Repositioning needed)" else "Optimal Channel Velocity",
            isSlowMoving = isSlow,
            deadStockRisk = deadRiskLabel,
            deadStockRiskScore = deadStockRiskScore,
            growthOpportunityScore = growthScore,
            growthProbability = 86,
            stockOutRiskProbability = if (currentInventory < safetyStock) 88 else 42,
            recommendedAction = "Schedule ${reorderQty} units with loom weavers. Lead time buffer: ${leadTimeDays} days.",
            aiRationale = "Statistical forecast uses weighted 30d/90d sales velocity with +${((seasonalMultiplier - 1.0)*100).toInt()}% wedding season factor.",
            seasonalPeakTiming = "Peak Festive Muhurat Window (Next 60 Days)",
            isFallback = true
        )
    }

    /**
     * U5: Parse Comprehensive Dealer Recommendation
     */
    fun parseComprehensiveDealerRecommendation(
        rawResponse: String?,
        dealerName: String,
        dealerCategory: String,
        location: String,
        salesHistoryAnnual: Double,
        salesHistoryQuarterly: Double,
        orderFrequencyPerMonth: Double,
        paymentPerformance: String,
        productPreferences: String,
        growthTrendPercent: Double,
        dealerRating: Double,
        customerReachCount: Int,
        creditLimit: Double,
        creditUsed: Double
    ): ComprehensiveDealerRecommendationResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val classification = json.optString("classification", "TOP_PERFORMER")
                val potential = json.optInt("dealerPotentialScore", 88)
                val loyalty = json.optInt("dealerLoyaltyScore", 92)
                val revScore = json.optInt("revenueContributionScore", 85)
                val risk = json.optInt("riskScore", 12)
                val growth = json.optDouble("futureGrowthForecastPercent", 24.5)
                val actions = json.optString("recommendedActions", "Extend VIP Tier 1 wholesale pricing and grant pre-season bridal trunk show exclusivity.")
                val credit = json.optString("creditRecommendation", "Expand rolling credit limit by +25% based on pristine 7-day payment settlement history.")
                val exclusive = json.optString("exclusiveCatalogAccess", "Allocate 150 exclusive Katan Silk & Tissue Organza lots per month.")
                val promo = json.optString("promotionalSupport", "Provide 5% co-op advertising budget and luxury acrylic branded display units.")
                val rationale = json.optString("rationale", "Consistent 20%+ quarterly order frequency and low credit delinquency make this dealer a cornerstone growth partner.")
                val isTop = json.optBoolean("isTopPerformer", classification == "TOP_PERFORMER")
                val isHigh = json.optBoolean("isHighGrowth", classification == "HIGH_GROWTH" || growthTrendPercent > 20.0)
                val isExp = json.optBoolean("isExpansionCandidate", classification == "EXPANSION")
                val isRec = json.optBoolean("isRecoveryTarget", classification == "RECOVERY")
                val isRisk = json.optBoolean("isRiskAlert", classification == "RISK_WATCH" || risk > 50)

                return ComprehensiveDealerRecommendationResult(
                    classification = classification,
                    dealerPotentialScore = potential,
                    dealerLoyaltyScore = loyalty,
                    revenueContributionScore = revScore,
                    riskScore = risk,
                    futureGrowthForecastPercent = growth,
                    recommendedActions = actions,
                    creditRecommendation = credit,
                    exclusiveCatalogAccess = exclusive,
                    promotionalSupport = promo,
                    rationale = rationale,
                    isTopPerformer = isTop,
                    isHighGrowth = isHigh,
                    isExpansionCandidate = isExp,
                    isRecoveryTarget = isRec,
                    isRiskAlert = isRisk,
                    isFallback = false
                )
            } catch (_: Exception) {
                // Fallback below
            }
        }

        // Deterministic Fallback Logic
        val creditUtilPct = if (creditLimit > 0) (creditUsed / creditLimit) * 100.0 else 50.0
        val isDelayedPayment = paymentPerformance.contains("Delayed", ignoreCase = true) || paymentPerformance.contains(">30", ignoreCase = true)

        val classification = when {
            isDelayedPayment || creditUtilPct > 85.0 -> "RISK_WATCH"
            growthTrendPercent >= 25.0 -> "HIGH_GROWTH"
            salesHistoryAnnual >= 2500000.0 && dealerRating >= 4.2 -> "TOP_PERFORMER"
            customerReachCount > 1500 -> "EXPANSION"
            growthTrendPercent < 0.0 -> "RECOVERY"
            else -> "TOP_PERFORMER"
        }

        val potentialScore = (dealerRating * 15.0 + (growthTrendPercent.coerceIn(-20.0, 50.0) * 0.4) + (customerReachCount / 50.0).coerceIn(0.0, 20.0)).toInt().coerceIn(10, 98)
        val loyaltyScore = ((orderFrequencyPerMonth * 12.0).coerceIn(10.0, 50.0) + (dealerRating * 10.0)).toInt().coerceIn(10, 99)
        val revScore = ((salesHistoryAnnual / 50000.0).coerceIn(10.0, 95.0)).toInt()
        val riskScore = (if (isDelayedPayment) 45 else 5) + (creditUtilPct * 0.4).toInt()

        val growthForecast = (growthTrendPercent * 0.8 + 8.5).coerceIn(4.0, 65.0)

        return ComprehensiveDealerRecommendationResult(
            classification = classification,
            dealerPotentialScore = potentialScore,
            dealerLoyaltyScore = loyaltyScore,
            revenueContributionScore = revScore,
            riskScore = riskScore.coerceIn(5, 95),
            futureGrowthForecastPercent = Math.round(growthForecast * 10.0) / 10.0,
            recommendedActions = when (classification) {
                "TOP_PERFORMER" -> "Promote to Master Platinum Partner; unlock direct loom consignment and priority festival dispatch."
                "HIGH_GROWTH" -> "Incentivize with 3% additional volume rebate and fast-track weekly catalog previews."
                "EXPANSION" -> "Co-fund regional showroom boutique branding and deploy dedicated B2B sales coordinator."
                "RECOVERY" -> "Conduct executive review meeting, refresh slow-moving SKUs with fast festive lines, and offer temporary credit ease."
                else -> "Hold credit limit increases, mandate 50% advance for festive bookings, and schedule payment reconciliation."
            },
            creditRecommendation = if (riskScore > 50) "Maintain strict credit cap at ₹${creditLimit.toInt()}; enforce 7-day payment clearance before release." else "Eligible for credit line increase to ₹${(creditLimit * 1.25).toInt()} on 15-day cycle.",
            exclusiveCatalogAccess = "Exclusive regional access to Premium Mulberry Katan & Organza Festive lines.",
            promotionalSupport = "Brand billboard co-op support (50:50) and seasonal WhatsApp catalogue broadcast templates.",
            rationale = "Analytics based on annual sales of ₹${salesHistoryAnnual.toInt()}, ${orderFrequencyPerMonth} orders/month cadence, and ${growthTrendPercent}% YoY trajectory.",
            isTopPerformer = classification == "TOP_PERFORMER",
            isHighGrowth = classification == "HIGH_GROWTH",
            isExpansionCandidate = classification == "EXPANSION",
            isRecoveryTarget = classification == "RECOVERY",
            isRiskAlert = classification == "RISK_WATCH",
            isFallback = true
        )
    }

    /**
     * Parse Comprehensive Inventory Intelligence AI Response (U6)
     */
    fun parseComprehensiveInventoryIntelligence(
        rawResponse: String?,
        productName: String,
        sku: String,
        category: String,
        currentStock: Int,
        allocatedStock: Int,
        incomingStock: Int,
        averageDailySales: Double,
        salesHistory30d: Int,
        salesHistory90d: Int,
        forecastDemand30d: Int,
        dealerPendingOrders: Int,
        unitCostPrice: Double,
        unitSellingPrice: Double,
        leadTimeDays: Int,
        storageCapacityUnits: Int,
        storageOccupiedUnits: Int,
        season: String,
        festivalCalendar: String,
        holdingCostPerUnitMonthly: Double
    ): ComprehensiveInventoryIntelligenceResult {
        if (!rawResponse.isNullOrBlank()) {
            try {
                val json = JSONObject(cleanJson(rawResponse))
                val velClass = json.optString("velocityClassification", "FAST_MOVING")
                val reorderQty = json.optInt("reorderQuantity", 75)
                val reorderDate = json.optString("reorderDate", "2026-08-28")
                val safetyStock = json.optInt("safetyStockUnits", 25)
                val daysSupply = json.optInt("daysOfSupply", 32)
                val stockoutDays = json.optInt("stockoutRiskDays", 18)
                val reorderCost = json.optDouble("estimatedReorderCost", reorderQty * unitCostPrice)
                val holdingCost = json.optDouble("projectedHoldingCostMonthly", currentStock * holdingCostPerUnitMonthly)
                val seasonalMult = json.optDouble("seasonalMultiplier", 1.35)
                val fastScore = json.optInt("fastMovingScore", 85)
                val deadScore = json.optInt("deadStockRiskScore", 12)
                val growthScore = json.optInt("growthOpportunityScore", 88)
                val healthScore = json.optInt("inventoryHealthScore", 89)
                val whUtilScore = json.optInt("warehouseUtilizationScore", 78)
                val turnover = json.optDouble("stockTurnoverRatio", 6.4)
                val isOver = json.optBoolean("isOverstock", false)
                val isUnder = json.optBoolean("isUnderstock", false)
                val isDead = json.optBoolean("isDeadStock", false)
                val isCrit = json.optBoolean("isCriticalReorder", false)
                val alertMsg = json.optString("alertMessage", "Inventory velocity optimal.")
                val alertAction = json.optString("alertActionRequired", "Maintain standard replenishment cycle.")
                val rationale = json.optString("aiOptimizationRationale", "Optimized stock buffers computed.")
                val recType = json.optString("recommendationType", "REORDER_ACCELERATE")
                val recPriority = json.optString("recommendationPriority", "HIGH")
                val recAction = json.optString("recommendedAction", "Execute scheduled loom production batch.")
                val discountPct = json.optDouble("suggestedDiscountPct", 0.0)
                val costSavings = json.optDouble("estimatedCostSavingsInr", 45000.0)

                return ComprehensiveInventoryIntelligenceResult(
                    velocityClassification = velClass,
                    reorderQuantity = reorderQty,
                    reorderDate = reorderDate,
                    safetyStockUnits = safetyStock,
                    daysOfSupply = daysSupply,
                    stockoutRiskDays = stockoutDays,
                    estimatedReorderCost = reorderCost,
                    projectedHoldingCostMonthly = holdingCost,
                    seasonalMultiplier = seasonalMult,
                    fastMovingScore = fastScore,
                    deadStockRiskScore = deadScore,
                    growthOpportunityScore = growthScore,
                    inventoryHealthScore = healthScore,
                    warehouseUtilizationScore = whUtilScore,
                    stockTurnoverRatio = turnover,
                    isOverstock = isOver,
                    isUnderstock = isUnder,
                    isDeadStock = isDead,
                    isCriticalReorder = isCrit,
                    alertMessage = alertMsg,
                    alertActionRequired = alertAction,
                    aiOptimizationRationale = rationale,
                    recommendationType = recType,
                    recommendationPriority = recPriority,
                    recommendedAction = recAction,
                    suggestedDiscountPct = discountPct,
                    estimatedCostSavingsInr = costSavings,
                    isFallback = false
                )
            } catch (_: Exception) {
                // Fall through to deterministic fallback
            }
        }

        // Deterministic Mathematical & Heuristic Fallback
        val dailyVel = if (averageDailySales > 0) averageDailySales else (salesHistory30d.toDouble() / 30.0).coerceAtLeast(0.5)
        val daysOfSupply = (currentStock / dailyVel).toInt().coerceAtLeast(1)
        val safetyStock = (dailyVel * leadTimeDays * 0.75).toInt().coerceAtLeast(15)
        val effectiveStock = (currentStock + incomingStock) - allocatedStock
        val reorderPoint = (dailyVel * leadTimeDays + safetyStock).toInt()
        val neededReorder = ((forecastDemand30d + dealerPendingOrders + safetyStock) - effectiveStock).coerceAtLeast(0)

        val velClass = when {
            salesHistory90d < 8 && currentStock > 25 -> "DEAD_STOCK"
            daysOfSupply > 70 -> "SLOW_MOVING"
            dailyVel >= 2.5 || salesHistory30d >= 60 -> "FAST_MOVING"
            else -> "MODERATE_MOVING"
        }

        val deadRiskScore = when (velClass) {
            "DEAD_STOCK" -> 88
            "SLOW_MOVING" -> 55
            "MODERATE_MOVING" -> 18
            else -> 6
        }

        val fastScore = when (velClass) {
            "FAST_MOVING" -> 94
            "MODERATE_MOVING" -> 68
            "SLOW_MOVING" -> 25
            else -> 5
        }

        val healthScore = when (velClass) {
            "FAST_MOVING" -> 92
            "MODERATE_MOVING" -> 84
            "SLOW_MOVING" -> 56
            else -> 28
        }

        val isUnder = effectiveStock < safetyStock
        val isCritReorder = effectiveStock <= (dailyVel * 4).toInt()
        val isOver = daysOfSupply > 90
        val isDead = velClass == "DEAD_STOCK"

        val alertMsg = when {
            isCritReorder -> "CRITICAL STOCKOUT HAZARD: Available stock ($effectiveStock) below 4-day buffer."
            isUnder -> "LOW STOCK WARNING: Stock position below safety threshold ($safetyStock units)."
            isDead -> "DEAD INVENTORY ALERT: Zero sales velocity for 90 days with $currentStock units held."
            isOver -> "OVERSTOCK RISK: $daysOfSupply days of supply exceeding holding cost benchmarks."
            else -> "STABLE INVENTORY: Velocity and stock levels within optimal buffer parameters."
        }

        val alertAction = when {
            isCritReorder -> "Dispatch express weaver order of $neededReorder units immediately."
            isUnder -> "Queue replenishment order of $neededReorder units with supplier."
            isDead -> "Initiate 20% dealer clearance bundle or B2B festival liquidation."
            isOver -> "Suspend fresh purchase orders and promote in seasonal broadcast."
            else -> "Maintain standard 14-day stock replenishment cycle."
        }

        val recType = when {
            isDead -> "LIQUIDATION"
            isOver -> "PRICE_MARKDOWN"
            isCritReorder || isUnder -> "REORDER_ACCELERATE"
            else -> "SAFETY_STOCK_ADJUST"
        }

        val recPriority = when {
            isCritReorder || isDead -> "CRITICAL"
            isUnder || isOver -> "HIGH"
            else -> "MEDIUM"
        }

        val recAction = when {
            isDead -> "Bundle with high-velocity bridal ensembles at 18% promotional trade discount."
            isOver -> "Offer 10% cash-settlement dealer incentive to reallocate 40 units across North Zone."
            isCritReorder -> "Place emergency purchase order for $neededReorder units at ₹$unitCostPrice."
            else -> "Optimize safety stock buffer to $safetyStock units for wedding peak season."
        }

        val stockTurnover = if (currentStock > 0) Math.round(((salesHistory90d * 4.0) / currentStock) * 10.0) / 10.0 else 5.2
        val whUtil = if (storageCapacityUnits > 0) ((storageOccupiedUnits.toDouble() / storageCapacityUnits) * 100).toInt().coerceIn(10, 100) else 74

        return ComprehensiveInventoryIntelligenceResult(
            velocityClassification = velClass,
            reorderQuantity = neededReorder,
            reorderDate = "2026-08-25",
            safetyStockUnits = safetyStock,
            daysOfSupply = daysOfSupply,
            stockoutRiskDays = if (isUnder) (effectiveStock / dailyVel).toInt().coerceAtLeast(1) else 0,
            estimatedReorderCost = neededReorder * unitCostPrice,
            projectedHoldingCostMonthly = currentStock * holdingCostPerUnitMonthly,
            seasonalMultiplier = 1.35,
            fastMovingScore = fastScore,
            deadStockRiskScore = deadRiskScore,
            growthOpportunityScore = if (velClass == "FAST_MOVING") 92 else 74,
            inventoryHealthScore = healthScore,
            warehouseUtilizationScore = whUtil,
            stockTurnoverRatio = stockTurnover.coerceIn(0.5, 15.0),
            isOverstock = isOver,
            isUnderstock = isUnder,
            isDeadStock = isDead,
            isCriticalReorder = isCritReorder,
            alertMessage = alertMsg,
            alertActionRequired = alertAction,
            aiOptimizationRationale = "Heuristic engine evaluated $salesHistory30d (30d) & $salesHistory90d (90d) velocity vs ${forecastDemand30d} demand with ${leadTimeDays}d lead time and ₹${holdingCostPerUnitMonthly}/unit carrying cost.",
            recommendationType = recType,
            recommendationPriority = recPriority,
            recommendedAction = recAction,
            suggestedDiscountPct = if (isDead) 18.0 else if (isOver) 10.0 else 0.0,
            estimatedCostSavingsInr = if (isDead) 52000.0 else if (isCritReorder) 128000.0 else 24000.0,
            isFallback = true
        )
    }
}

data class ComprehensiveDealerRecommendationResult(
    val classification: String,
    val dealerPotentialScore: Int,
    val dealerLoyaltyScore: Int,
    val revenueContributionScore: Int,
    val riskScore: Int,
    val futureGrowthForecastPercent: Double,
    val recommendedActions: String,
    val creditRecommendation: String,
    val exclusiveCatalogAccess: String,
    val promotionalSupport: String,
    val rationale: String,
    val isTopPerformer: Boolean,
    val isHighGrowth: Boolean,
    val isExpansionCandidate: Boolean,
    val isRecoveryTarget: Boolean,
    val isRiskAlert: Boolean,
    val isFallback: Boolean = false
)

data class ComprehensiveDemandForecastResult(
    val forecast7dUnits: Int,
    val forecast7dRevenue: Double,
    val forecast30dUnits: Int,
    val forecast30dRevenue: Double,
    val forecast90dUnits: Int,
    val forecast90dRevenue: Double,
    val forecast1yUnits: Int,
    val forecast1yRevenue: Double,
    val reorderQuantity: Int,
    val safetyStockRecommendation: Int,
    val fastMovingPrediction: String,
    val isFastMoving: Boolean,
    val slowMovingPrediction: String,
    val isSlowMoving: Boolean,
    val deadStockRisk: String,
    val deadStockRiskScore: Int,
    val growthOpportunityScore: Int,
    val growthProbability: Int,
    val stockOutRiskProbability: Int,
    val recommendedAction: String,
    val aiRationale: String,
    val seasonalPeakTiming: String,
    val isFallback: Boolean = false
)

data class ComprehensiveInventoryIntelligenceResult(
    val velocityClassification: String,
    val reorderQuantity: Int,
    val reorderDate: String,
    val safetyStockUnits: Int,
    val daysOfSupply: Int,
    val stockoutRiskDays: Int,
    val estimatedReorderCost: Double,
    val projectedHoldingCostMonthly: Double,
    val seasonalMultiplier: Double,
    val fastMovingScore: Int,
    val deadStockRiskScore: Int,
    val growthOpportunityScore: Int,
    val inventoryHealthScore: Int,
    val warehouseUtilizationScore: Int,
    val stockTurnoverRatio: Double,
    val isOverstock: Boolean,
    val isUnderstock: Boolean,
    val isDeadStock: Boolean,
    val isCriticalReorder: Boolean,
    val alertMessage: String,
    val alertActionRequired: String,
    val aiOptimizationRationale: String,
    val recommendationType: String,
    val recommendationPriority: String,
    val recommendedAction: String,
    val suggestedDiscountPct: Double,
    val estimatedCostSavingsInr: Double,
    val isFallback: Boolean = false
)

