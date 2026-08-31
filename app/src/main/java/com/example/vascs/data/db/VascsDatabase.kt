package com.example.vascs.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vascs.data.model.AiCatalogueImageEntity
import com.example.vascs.data.model.AiImageArchiveEntity
import com.example.vascs.data.model.MediaCommandCenterEntity
import com.example.vascs.data.model.DealerEntity
import com.example.vascs.data.model.DealerProductEntity
import com.example.vascs.data.model.WhatsAppCampaignEntity
import com.example.vascs.data.model.DealerCatalogueEntity
import com.example.vascs.data.model.DealerUserEntity
import com.example.vascs.data.model.DealerOrderEntity
import com.example.vascs.data.model.SocialAnalyticsEntity
import com.example.vascs.data.model.OrderMasterEntity
import com.example.vascs.data.model.OrderItemEntity
import com.example.vascs.data.model.PackingSlipEntity
import com.example.vascs.data.model.DispatchEntity
import com.example.vascs.data.model.DeliveryEntity
import com.example.vascs.data.model.OrderTrackingEntity
import com.example.vascs.data.model.CatalogueGenerationJobEntity
import com.example.vascs.data.model.ExportQueueEntity
import com.example.vascs.data.model.MediaLibraryEntity
import com.example.vascs.data.model.ProductBatchEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.model.ProductImageEntity

import com.example.vascs.data.model.CustomerLeadEntity
import com.example.vascs.data.model.QuotationEntity
import com.example.vascs.data.model.FollowupEntity
import com.example.vascs.data.model.WhatsappTemplateEntity
import com.example.vascs.data.model.BroadcastCampaignEntity
import com.example.vascs.data.model.SalesOrderEntity
import com.example.vascs.data.model.SalesOrderItemEntity
import com.example.vascs.data.model.DispatchRecordEntity
import com.example.vascs.data.model.TrackingRecordEntity
import com.example.vascs.data.model.InvoiceRecordEntity
import com.example.vascs.data.model.PaymentRecordEntity
import com.example.vascs.data.model.DealerOutstandingEntity
import com.example.vascs.data.model.RawMaterialEntity
import com.example.vascs.data.model.FabricStockEntity
import com.example.vascs.data.model.ProductionOrderEntity
import com.example.vascs.data.model.ProductionBatchEntity
import com.example.vascs.data.model.DyeingRecordEntity
import com.example.vascs.data.model.EmbroideryRecordEntity
import com.example.vascs.data.model.QualityCheckEntity
import com.example.vascs.data.model.FinishedGoodsEntity
import com.example.vascs.data.model.WorkerEntity
import com.example.vascs.data.model.AccountLedgerEntity
import com.example.vascs.data.model.CashBookEntity
import com.example.vascs.data.model.BankBookEntity
import com.example.vascs.data.model.PurchaseRegisterEntity
import com.example.vascs.data.model.ExpenseRegisterEntity
import com.example.vascs.data.model.AccountsReceivableEntity
import com.example.vascs.data.model.AccountsPayableEntity
import com.example.vascs.data.model.GstReportEntity
import com.example.vascs.data.model.ProfitLossReportEntity
import com.example.vascs.data.model.BalanceSheetReportEntity
import com.example.vascs.data.model.CompanyEntity
import com.example.vascs.data.model.SubscriptionEntity
import com.example.vascs.data.model.BillingRecordEntity
import com.example.vascs.data.model.CustomerPortalEntity
import com.example.vascs.data.model.DealerPortalEntity
import com.example.vascs.data.model.ApiKeyEntity
import com.example.vascs.data.model.SupportTicketEntity
import com.example.vascs.data.model.WhiteLabelConfigEntity
import com.example.vascs.data.model.CountryEntity
import com.example.vascs.data.model.CurrencyEntity
import com.example.vascs.data.model.CurrencyRateEntity
import com.example.vascs.data.model.TaxRuleEntity
import com.example.vascs.data.model.MarketplaceProductEntity
import com.example.vascs.data.model.TradeLeadEntity
import com.example.vascs.data.model.ExportDocumentEntity
import com.example.vascs.data.model.ImportDocumentEntity
import com.example.vascs.data.model.GlobalShipmentEntity
import com.example.vascs.data.model.GlobalWarehouseEntity
import com.example.vascs.data.model.CustomerEntity
import com.example.vascs.data.model.VendorEntity
import com.example.vascs.data.model.DeliveryPartnerEntity
import com.example.vascs.data.model.ChatMessageEntity
import com.example.vascs.data.model.NotificationEntity
import com.example.vascs.data.model.RewardPointEntity
import com.example.vascs.data.model.AiEmployeeEntity
import com.example.vascs.data.model.AiTaskEntity
import com.example.vascs.data.model.AiForecastEntity
import com.example.vascs.data.model.AiRecommendationEntity
import com.example.vascs.data.model.AiDecisionEntity
import com.example.vascs.data.model.AiAutomationRuleEntity
import com.example.vascs.data.model.AiActivityLogEntity
import com.example.vascs.data.model.AiAgentEntity
import com.example.vascs.data.model.BusinessTwinModelEntity
import com.example.vascs.data.model.PredictionEntity
import com.example.vascs.data.model.AutonomousDecisionEntity
import com.example.vascs.data.model.OptimizationLogEntity
import com.example.vascs.data.model.RiskAlertEntity
import com.example.vascs.data.model.MarketIntelligenceEntity
import com.example.vascs.data.model.ExecutionLogEntity
import com.example.vascs.data.model.ManufacturerEntity
import com.example.vascs.data.model.SupplierEntity
import com.example.vascs.data.model.ReputationScoreEntity
import com.example.vascs.data.model.BusinessConnectionEntity
import com.example.vascs.data.model.FashionTrendEntity
import com.example.vascs.data.model.GlobalIntelligenceEntity

import com.example.vascs.data.db.SalesOrderDao
import com.example.vascs.data.db.SalesOrderItemDao
import com.example.vascs.data.db.DispatchRecordDao
import com.example.vascs.data.db.TrackingRecordDao
import com.example.vascs.data.db.InvoiceRecordDao
import com.example.vascs.data.db.PaymentRecordDao
import com.example.vascs.data.db.DealerOutstandingDao
import com.example.vascs.data.db.RawMaterialDao
import com.example.vascs.data.db.FabricStockDao
import com.example.vascs.data.db.ProductionOrderDao
import com.example.vascs.data.db.ProductionBatchDao
import com.example.vascs.data.db.DyeingRecordDao
import com.example.vascs.data.db.EmbroideryRecordDao
import com.example.vascs.data.db.QualityCheckDao
import com.example.vascs.data.db.FinishedGoodsDao
import com.example.vascs.data.db.WorkerDao
import com.example.vascs.data.db.AccountLedgerDao
import com.example.vascs.data.db.CashBookDao
import com.example.vascs.data.db.BankBookDao
import com.example.vascs.data.db.PurchaseRegisterDao
import com.example.vascs.data.db.ExpenseRegisterDao
import com.example.vascs.data.db.AccountsReceivableDao
import com.example.vascs.data.db.AccountsPayableDao
import com.example.vascs.data.db.GstReportDao
import com.example.vascs.data.db.ProfitLossReportDao
import com.example.vascs.data.db.BalanceSheetReportDao
import com.example.vascs.data.db.CompanyDao
import com.example.vascs.data.db.SubscriptionDao
import com.example.vascs.data.db.BillingRecordDao
import com.example.vascs.data.db.CustomerPortalDao
import com.example.vascs.data.db.DealerPortalDao
import com.example.vascs.data.db.ApiKeyDao
import com.example.vascs.data.db.SupportTicketDao
import com.example.vascs.data.db.WhiteLabelConfigDao
import com.example.vascs.data.db.CountryDao
import com.example.vascs.data.db.CurrencyDao
import com.example.vascs.data.db.CurrencyRateDao
import com.example.vascs.data.db.TaxRuleDao
import com.example.vascs.data.db.MarketplaceProductDao
import com.example.vascs.data.db.TradeLeadDao
import com.example.vascs.data.db.ExportDocumentDao
import com.example.vascs.data.db.ImportDocumentDao
import com.example.vascs.data.db.GlobalShipmentDao
import com.example.vascs.data.db.GlobalWarehouseDao
import com.example.vascs.data.db.CustomerDao
import com.example.vascs.data.db.VendorDao
import com.example.vascs.data.db.DeliveryPartnerDao
import com.example.vascs.data.db.ChatMessageDao
import com.example.vascs.data.db.NotificationDao
import com.example.vascs.data.db.RewardPointDao
import com.example.vascs.data.db.AiEmployeeDao
import com.example.vascs.data.db.AiTaskDao
import com.example.vascs.data.db.AiForecastDao
import com.example.vascs.data.db.AiRecommendationDao
import com.example.vascs.data.db.AiDecisionDao
import com.example.vascs.data.db.AiAutomationRuleDao
import com.example.vascs.data.db.AiActivityLogDao
import com.example.vascs.data.db.AiAgentDao
import com.example.vascs.data.db.BusinessTwinModelDao
import com.example.vascs.data.db.PredictionDao
import com.example.vascs.data.db.AutonomousDecisionDao
import com.example.vascs.data.db.OptimizationLogDao
import com.example.vascs.data.db.RiskAlertDao
import com.example.vascs.data.db.MarketIntelligenceDao
import com.example.vascs.data.db.ExecutionLogDao
import com.example.vascs.data.db.ManufacturerDao
import com.example.vascs.data.db.SupplierDao
import com.example.vascs.data.db.ReputationScoreDao
import com.example.vascs.data.db.BusinessConnectionDao
import com.example.vascs.data.db.FashionTrendDao
import com.example.vascs.data.db.GlobalIntelligenceDao

import com.example.vascs.data.db.OmegaCoreDao
import com.example.vascs.data.db.GlobalTradeDataDao
import com.example.vascs.data.db.CompetitorIntelligenceDao
import com.example.vascs.data.db.CapitalManagementDao
import com.example.vascs.data.db.SupplyChainAiDao
import com.example.vascs.data.db.OmegaTwinDao
import com.example.vascs.data.db.RevenueEngineDao
import com.example.vascs.data.db.OmegaHealthDao

import com.example.vascs.data.db.IndustryMasterDao
import com.example.vascs.data.db.CountryMasterDao
import com.example.vascs.data.db.GlobalEconomyDao
import com.example.vascs.data.db.ResearchReportDao
import com.example.vascs.data.db.MarketOpportunityDao
import com.example.vascs.data.db.ExpansionBlueprintDao
import com.example.vascs.data.db.InfinityAnalyticsDao
import com.example.vascs.data.db.UniversalMarketplaceDao

import com.example.vascs.data.db.CosmosNodeDao
import com.example.vascs.data.db.PlanetaryTradeRouteDao
import com.example.vascs.data.db.SovereignReserveDao
import com.example.vascs.data.db.AutonomousGovernanceLogDao
import com.example.vascs.data.db.SelfEvolvingModelDao
import com.example.vascs.data.db.CosmicMarketIndexDao
import com.example.vascs.data.db.PlanetarySimulationDao
import com.example.vascs.data.db.CosmosTelemetryDao
import com.example.vascs.data.db.CosmosCoreDao
import com.example.vascs.data.db.TradeNetworksDao
import com.example.vascs.data.db.GlobalRiskDao
import com.example.vascs.data.db.EconomicTwinsDao
import com.example.vascs.data.db.MarketCosmosDao
import com.example.vascs.data.db.SupplyGridDao
import com.example.vascs.data.db.CosmosHealthDao
import com.example.vascs.data.db.CosmosAnalyticsDao
import com.example.vascs.data.db.NexusCoreDao
import com.example.vascs.data.db.EnterpriseNetworkDao
import com.example.vascs.data.db.KnowledgeWebDao
import com.example.vascs.data.db.PartnershipNetworkDao
import com.example.vascs.data.db.OpportunityExchangeDao
import com.example.vascs.data.db.DecisionExchangeDao
import com.example.vascs.data.db.NexusHealthDao
import com.example.vascs.data.db.NexusAnalyticsDao

import com.example.vascs.data.model.OmegaCoreEntity
import com.example.vascs.data.model.GlobalTradeDataEntity
import com.example.vascs.data.model.CompetitorIntelligenceEntity
import com.example.vascs.data.model.CapitalManagementEntity
import com.example.vascs.data.model.SupplyChainAiEntity
import com.example.vascs.data.model.OmegaTwinEntity
import com.example.vascs.data.model.RevenueEngineEntity
import com.example.vascs.data.model.OmegaHealthEntity

import com.example.vascs.data.model.IndustryMasterEntity
import com.example.vascs.data.model.CountryMasterEntity
import com.example.vascs.data.model.GlobalEconomyEntity
import com.example.vascs.data.model.ResearchReportEntity
import com.example.vascs.data.model.MarketOpportunityEntity
import com.example.vascs.data.model.ExpansionBlueprintEntity
import com.example.vascs.data.model.InfinityAnalyticsEntity
import com.example.vascs.data.model.UniversalMarketplaceEntity

import com.example.vascs.data.model.CosmosNodeEntity
import com.example.vascs.data.model.PlanetaryTradeRouteEntity
import com.example.vascs.data.model.SovereignReserveEntity
import com.example.vascs.data.model.AutonomousGovernanceLogEntity
import com.example.vascs.data.model.SelfEvolvingModelEntity
import com.example.vascs.data.model.CosmicMarketIndexEntity
import com.example.vascs.data.model.PlanetarySimulationEntity
import com.example.vascs.data.model.CosmosTelemetryEntity
import com.example.vascs.data.model.CosmosCoreEntity
import com.example.vascs.data.model.TradeNetworksEntity
import com.example.vascs.data.model.GlobalRiskEntity
import com.example.vascs.data.model.EconomicTwinsEntity
import com.example.vascs.data.model.MarketCosmosEntity
import com.example.vascs.data.model.SupplyGridEntity
import com.example.vascs.data.model.CosmosHealthEntity
import com.example.vascs.data.model.CosmosAnalyticsEntity
import com.example.vascs.data.model.NexusCoreEntity
import com.example.vascs.data.model.EnterpriseNetworkEntity
import com.example.vascs.data.model.KnowledgeWebEntity
import com.example.vascs.data.model.PartnershipNetworkEntity
import com.example.vascs.data.model.OpportunityExchangeEntity
import com.example.vascs.data.model.DecisionExchangeEntity
import com.example.vascs.data.model.NexusHealthEntity
import com.example.vascs.data.model.NexusAnalyticsEntity

import com.example.vascs.data.local.FutureEngineDao
import com.example.vascs.data.local.SimulationNetworkDao
import com.example.vascs.data.local.EvolutionEngineDao
import com.example.vascs.data.local.OpportunityQuantumDao
import com.example.vascs.data.local.MarketQuantumDao
import com.example.vascs.data.local.DecisionMatrixDao
import com.example.vascs.data.local.RiskQuantumDao
import com.example.vascs.data.local.QuantumHealthDao

import com.example.vascs.data.model.FutureEngineEntity
import com.example.vascs.data.model.SimulationNetworkEntity
import com.example.vascs.data.model.EvolutionEngineEntity
import com.example.vascs.data.model.OpportunityQuantumEntity
import com.example.vascs.data.model.MarketQuantumEntity
import com.example.vascs.data.model.DecisionMatrixEntity
import com.example.vascs.data.model.RiskQuantumEntity
import com.example.vascs.data.model.QuantumHealthEntity

import com.example.vascs.data.model.AscensionCoreEntity
import com.example.vascs.data.model.EconomicCivilizationEntity
import com.example.vascs.data.model.ResourceIntelligenceEntity
import com.example.vascs.data.model.TradeUniverseEntity
import com.example.vascs.data.model.ProsperityEngineEntity
import com.example.vascs.data.model.InnovationUniverseEntity
import com.example.vascs.data.model.DecisionUniverseEntity
import com.example.vascs.data.model.AscensionHealthEntity

import com.example.vascs.data.dao.AscensionCoreDao
import com.example.vascs.data.dao.EconomicCivilizationDao
import com.example.vascs.data.dao.ResourceIntelligenceDao
import com.example.vascs.data.dao.TradeUniverseDao
import com.example.vascs.data.dao.ProsperityEngineDao
import com.example.vascs.data.dao.InnovationUniverseDao
import com.example.vascs.data.dao.DecisionUniverseDao
import com.example.vascs.data.dao.AscensionHealthDao

import com.example.vascs.data.model.OmniverseCoreEntity
import com.example.vascs.data.model.EconomyNetworkEntity
import com.example.vascs.data.model.MarketMatrixEntity
import com.example.vascs.data.model.TradeGridEntity
import com.example.vascs.data.model.KnowledgeFabricEntity
import com.example.vascs.data.model.IndustryMatrixEntity
import com.example.vascs.data.model.OpportunityUniverseEntity
import com.example.vascs.data.model.OmniverseHealthEntity
import com.example.vascs.data.model.OmniverseRiskEntity
import com.example.vascs.data.model.OmniverseInnovationEntity

import com.example.vascs.data.model.EternityCoreEntity
import com.example.vascs.data.model.WealthUniverseEntity
import com.example.vascs.data.model.DemandUniverseEntity
import com.example.vascs.data.model.CapitalUniverseEntity
import com.example.vascs.data.model.TradeInfinityEntity
import com.example.vascs.data.model.KnowledgeEternityEntity
import com.example.vascs.data.model.RiskShieldEntity
import com.example.vascs.data.model.EternityHealthEntity
import com.example.vascs.data.model.EternityInnovationEntity

import com.example.vascs.data.model.TranscendenceCoreEntity
import com.example.vascs.data.model.RealityCommerceEntity
import com.example.vascs.data.model.EnterpriseCreatorEntity
import com.example.vascs.data.model.TranscendenceOpportunityEntity
import com.example.vascs.data.model.DemandNetworkEntity
import com.example.vascs.data.model.CapitalCivilizationEntity
import com.example.vascs.data.model.DecisionCosmosEntity
import com.example.vascs.data.model.KnowledgeOceanEntity
import com.example.vascs.data.model.TranscendenceEvolutionEntity
import com.example.vascs.data.model.TranscendenceRealityTwinEntity
import com.example.vascs.data.model.TranscendenceInnovationEntity
import com.example.vascs.data.model.TranscendenceRiskEntity
import com.example.vascs.data.model.TranscendenceHealthEntity
import com.example.vascs.data.model.TranscendenceExpansionEntity

import com.example.vascs.data.dao.OmniverseCoreDao
import com.example.vascs.data.dao.EconomyNetworkDao
import com.example.vascs.data.dao.MarketMatrixDao
import com.example.vascs.data.dao.TradeGridDao
import com.example.vascs.data.dao.KnowledgeFabricDao
import com.example.vascs.data.dao.IndustryMatrixDao
import com.example.vascs.data.dao.OpportunityUniverseDao
import com.example.vascs.data.dao.OmniverseHealthDao
import com.example.vascs.data.dao.OmniverseRiskDao
import com.example.vascs.data.dao.OmniverseInnovationDao

import com.example.vascs.data.dao.EternityCoreDao
import com.example.vascs.data.dao.WealthUniverseDao
import com.example.vascs.data.dao.DemandUniverseDao
import com.example.vascs.data.dao.CapitalUniverseDao
import com.example.vascs.data.dao.TradeInfinityDao
import com.example.vascs.data.dao.KnowledgeEternityDao
import com.example.vascs.data.dao.RiskShieldDao
import com.example.vascs.data.dao.EternityHealthDao
import com.example.vascs.data.dao.EternityInnovationDao

import com.example.vascs.data.dao.TranscendenceCoreDao
import com.example.vascs.data.dao.RealityCommerceDao
import com.example.vascs.data.dao.EnterpriseCreatorDao
import com.example.vascs.data.dao.TranscendenceOpportunityDao
import com.example.vascs.data.dao.DemandNetworkDao
import com.example.vascs.data.dao.CapitalCivilizationDao
import com.example.vascs.data.dao.DecisionCosmosDao
import com.example.vascs.data.dao.KnowledgeOceanDao
import com.example.vascs.data.dao.TranscendenceEvolutionDao
import com.example.vascs.data.dao.TranscendenceRealityTwinDao
import com.example.vascs.data.dao.TranscendenceInnovationDao
import com.example.vascs.data.dao.TranscendenceRiskDao
import com.example.vascs.data.dao.TranscendenceHealthDao
import com.example.vascs.data.dao.TranscendenceExpansionDao

import com.example.vascs.data.model.SupremacyCoreEntity
import com.example.vascs.data.model.CivilizationGovernanceEntity
import com.example.vascs.data.model.EconomicCommandEntity
import com.example.vascs.data.model.SupremeOpportunityEntity
import com.example.vascs.data.model.ExpansionNetworkEntity
import com.example.vascs.data.model.CapitalMatrixEntity
import com.example.vascs.data.model.TradeAuthorityEntity
import com.example.vascs.data.model.DigitalCivilizationEntity
import com.example.vascs.data.model.DecisionAuthorityEntity
import com.example.vascs.data.model.KnowledgeGridEntity
import com.example.vascs.data.model.InnovationAuthorityEntity
import com.example.vascs.data.model.RiskShieldSupremacyEntity
import com.example.vascs.data.model.HealthAuthorityEntity
import com.example.vascs.data.model.SupremacyCommandTowerEntity
import com.example.vascs.data.model.SovereigntyEngineEntity

import com.example.vascs.data.dao.SupremacyCoreDao
import com.example.vascs.data.dao.CivilizationGovernanceDao
import com.example.vascs.data.dao.EconomicCommandDao
import com.example.vascs.data.dao.SupremeOpportunityDao
import com.example.vascs.data.dao.ExpansionNetworkDao
import com.example.vascs.data.dao.CapitalMatrixDao
import com.example.vascs.data.dao.TradeAuthorityDao
import com.example.vascs.data.dao.DigitalCivilizationDao
import com.example.vascs.data.dao.DecisionAuthorityDao
import com.example.vascs.data.dao.KnowledgeGridDao
import com.example.vascs.data.dao.InnovationAuthorityDao
import com.example.vascs.data.dao.RiskShieldSupremacyDao
import com.example.vascs.data.dao.HealthAuthorityDao
import com.example.vascs.data.dao.SupremacyCommandTowerDao
import com.example.vascs.data.dao.SovereigntyEngineDao

import com.example.vascs.data.model.SingularityPrimeCoreEntity
import com.example.vascs.data.model.CivilizationEngineEntity
import com.example.vascs.data.model.WealthGeneratorEntity
import com.example.vascs.data.model.OpportunityCreatorEntity
import com.example.vascs.data.model.DemandCosmosEntity
import com.example.vascs.data.model.CapitalAuthorityEntity
import com.example.vascs.data.model.TradeSupremacyEntity
import com.example.vascs.data.model.RealityEngineEntity
import com.example.vascs.data.model.DecisionPrimeEntity
import com.example.vascs.data.model.KnowledgePrimeEntity
import com.example.vascs.data.model.InnovationFactoryEntity
import com.example.vascs.data.model.RiskShieldPrimeEntity
import com.example.vascs.data.model.HealthPrimeEntity
import com.example.vascs.data.model.PrimeCommandTowerEntity
import com.example.vascs.data.model.EvolutionAuthorityEntity

import com.example.vascs.data.dao.SingularityPrimeCoreDao
import com.example.vascs.data.dao.CivilizationEngineDao
import com.example.vascs.data.dao.WealthGeneratorDao
import com.example.vascs.data.dao.OpportunityCreatorDao
import com.example.vascs.data.dao.DemandCosmosDao
import com.example.vascs.data.dao.CapitalAuthorityDao
import com.example.vascs.data.dao.TradeSupremacyDao
import com.example.vascs.data.dao.RealityEngineDao
import com.example.vascs.data.dao.DecisionPrimeDao
import com.example.vascs.data.dao.KnowledgePrimeDao
import com.example.vascs.data.dao.InnovationFactoryDao
import com.example.vascs.data.dao.RiskShieldPrimeDao
import com.example.vascs.data.dao.HealthPrimeDao
import com.example.vascs.data.dao.PrimeCommandTowerDao
import com.example.vascs.data.dao.EvolutionAuthorityDao

import com.example.vascs.data.model.AbsoluteCoreEntity
import com.example.vascs.data.model.EconomicOSEntity
import com.example.vascs.data.model.WealthMatrixEntity
import com.example.vascs.data.model.OpportunityGridEntity
import com.example.vascs.data.model.DemandMatrixEntity
import com.example.vascs.data.model.CapitalSupremacyEntity
import com.example.vascs.data.model.TradeNetworkEntity
import com.example.vascs.data.model.RealityMatrixEntity
import com.example.vascs.data.model.DecisionEngineEntity
import com.example.vascs.data.model.KnowledgeMatrixEntity
import com.example.vascs.data.model.InnovationEngineEntity
import com.example.vascs.data.model.ProtectionSystemEntity
import com.example.vascs.data.model.AbsoluteHealthEngineEntity
import com.example.vascs.data.model.AbsoluteCommandTowerEntity
import com.example.vascs.data.model.UnityEngineEntity

import com.example.vascs.data.dao.AbsoluteCoreDao
import com.example.vascs.data.dao.EconomicOSDao
import com.example.vascs.data.dao.WealthMatrixDao
import com.example.vascs.data.dao.OpportunityGridDao
import com.example.vascs.data.dao.DemandMatrixDao
import com.example.vascs.data.dao.CapitalSupremacyDao
import com.example.vascs.data.dao.TradeNetworkDao
import com.example.vascs.data.dao.RealityMatrixDao
import com.example.vascs.data.dao.DecisionEngineDao
import com.example.vascs.data.dao.KnowledgeMatrixDao
import com.example.vascs.data.dao.InnovationEngineDao
import com.example.vascs.data.dao.ProtectionSystemDao
import com.example.vascs.data.dao.HealthEngineDao
import com.example.vascs.data.dao.AbsoluteCommandTowerDao
import com.example.vascs.data.dao.UnityEngineDao

import com.example.vascs.data.model.UltimaCoreEntity
import com.example.vascs.data.model.CommerceCivilizationEntity
import com.example.vascs.data.model.UltimaWealthUniverseEntity
import com.example.vascs.data.model.FutureOpportunityEntity
import com.example.vascs.data.model.UltimaDemandUniverseEntity
import com.example.vascs.data.model.UltimaCapitalAuthorityEntity
import com.example.vascs.data.model.TradeCivilizationEntity
import com.example.vascs.data.model.UltimaRealityGridEntity
import com.example.vascs.data.model.UltimaDecisionAuthorityEntity
import com.example.vascs.data.model.KnowledgeCivilizationEntity
import com.example.vascs.data.model.InnovationCivilizationEntity
import com.example.vascs.data.model.ProtectionGridEntity
import com.example.vascs.data.model.HealthCivilizationEntity
import com.example.vascs.data.model.UltimaTowerEntity
import com.example.vascs.data.model.UniversalHarmonyEngineEntity

import com.example.vascs.data.local.UltimaCoreDao
import com.example.vascs.data.local.CommerceCivilizationDao
import com.example.vascs.data.local.UltimaWealthUniverseDao
import com.example.vascs.data.local.FutureOpportunityDao
import com.example.vascs.data.local.UltimaDemandUniverseDao
import com.example.vascs.data.local.UltimaCapitalAuthorityDao
import com.example.vascs.data.local.TradeCivilizationDao
import com.example.vascs.data.local.UltimaRealityGridDao
import com.example.vascs.data.local.UltimaDecisionAuthorityDao
import com.example.vascs.data.local.KnowledgeCivilizationDao
import com.example.vascs.data.local.InnovationCivilizationDao
import com.example.vascs.data.local.ProtectionGridDao
import com.example.vascs.data.local.HealthCivilizationDao
import com.example.vascs.data.local.UltimaTowerDao
import com.example.vascs.data.local.UniversalHarmonyEngineDao
import com.example.vascs.data.model.AIPromptEntity
import com.example.vascs.data.model.AIConversationEntity
import com.example.vascs.data.model.AISuggestionEntity
import com.example.vascs.data.dao.AIPromptDao
import com.example.vascs.data.dao.AIConversationDao
import com.example.vascs.data.dao.AISuggestionDao
import com.example.vascs.data.dao.AICatalogueDao
import com.example.vascs.data.model.AICatalogueRequestEntity
import com.example.vascs.data.model.AICatalogueResultEntity
import com.example.vascs.data.model.AICatalogueTemplateEntity
import com.example.vascs.data.dao.AIPricingDao
import com.example.vascs.data.model.AIPricingRequestEntity
import com.example.vascs.data.model.AIPricingResultEntity
import com.example.vascs.data.model.AIPricingHistoryEntity
import com.example.vascs.data.model.AIPricingRuleEntity
import com.example.vascs.data.dao.AIDemandDao
import com.example.vascs.data.model.AIDemandRequestEntity
import com.example.vascs.data.model.AIDemandForecastEntity
import com.example.vascs.data.model.AIDemandHistoryEntity
import com.example.vascs.data.model.AIDemandModelEntity
import com.example.vascs.data.dao.AIDealerDao
import com.example.vascs.data.model.AIDealerRequestEntity
import com.example.vascs.data.model.AIDealerRecommendationEntity
import com.example.vascs.data.model.AIDealerScoreEntity
import com.example.vascs.data.model.AIDealerGrowthForecastEntity
import com.example.vascs.data.dao.AIInventoryDao
import com.example.vascs.data.model.AIInventoryRequestEntity
import com.example.vascs.data.model.AIInventoryForecastEntity
import com.example.vascs.data.model.AIInventoryAlertEntity
import com.example.vascs.data.model.AIInventoryHealthEntity
import com.example.vascs.data.model.AIInventoryRecommendationEntity
import com.example.vascs.data.dao.AIFinanceDao
import com.example.vascs.data.model.AIFinanceRequestEntity
import com.example.vascs.data.model.AIFinanceReportEntity
import com.example.vascs.data.model.AICashFlowForecastEntity
import com.example.vascs.data.model.AIFinancialHealthEntity
import com.example.vascs.data.model.AIFinanceRecommendationEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductBatchEntity::class,
        ProductImageEntity::class,
        CatalogueGenerationJobEntity::class,
        MediaLibraryEntity::class,
        ExportQueueEntity::class,
        AiCatalogueImageEntity::class,
        AiImageArchiveEntity::class,
        MediaCommandCenterEntity::class,
        DealerEntity::class,
        DealerProductEntity::class,
        WhatsAppCampaignEntity::class,
        DealerCatalogueEntity::class,
        DealerUserEntity::class,
        DealerOrderEntity::class,
        SocialAnalyticsEntity::class,
        OrderMasterEntity::class,
        OrderItemEntity::class,
        PackingSlipEntity::class,
        DispatchEntity::class,
        DeliveryEntity::class,
        OrderTrackingEntity::class,
        CustomerLeadEntity::class,
        QuotationEntity::class,
        FollowupEntity::class,
        WhatsappTemplateEntity::class,
        BroadcastCampaignEntity::class,
        SalesOrderEntity::class,
        SalesOrderItemEntity::class,
        DispatchRecordEntity::class,
        TrackingRecordEntity::class,
        InvoiceRecordEntity::class,
        PaymentRecordEntity::class,
        DealerOutstandingEntity::class,
        RawMaterialEntity::class,
        FabricStockEntity::class,
        ProductionOrderEntity::class,
        ProductionBatchEntity::class,
        DyeingRecordEntity::class,
        EmbroideryRecordEntity::class,
        QualityCheckEntity::class,
        FinishedGoodsEntity::class,
        WorkerEntity::class,
        AccountLedgerEntity::class,
        CashBookEntity::class,
        BankBookEntity::class,
        PurchaseRegisterEntity::class,
        ExpenseRegisterEntity::class,
        AccountsReceivableEntity::class,
        AccountsPayableEntity::class,
        GstReportEntity::class,
        ProfitLossReportEntity::class,
        BalanceSheetReportEntity::class,
        CompanyEntity::class,
        SubscriptionEntity::class,
        BillingRecordEntity::class,
        CustomerPortalEntity::class,
        DealerPortalEntity::class,
        ApiKeyEntity::class,
        SupportTicketEntity::class,
        WhiteLabelConfigEntity::class,
        CountryEntity::class,
        CurrencyEntity::class,
        CurrencyRateEntity::class,
        TaxRuleEntity::class,
        MarketplaceProductEntity::class,
        TradeLeadEntity::class,
        ExportDocumentEntity::class,
        ImportDocumentEntity::class,
        GlobalShipmentEntity::class,
        GlobalWarehouseEntity::class,
        CustomerEntity::class,
        VendorEntity::class,
        DeliveryPartnerEntity::class,
        ChatMessageEntity::class,
        NotificationEntity::class,
        RewardPointEntity::class,
        AiEmployeeEntity::class,
        AiTaskEntity::class,
        AiForecastEntity::class,
        AiRecommendationEntity::class,
        AiDecisionEntity::class,
        AiAutomationRuleEntity::class,
        AiActivityLogEntity::class,
        AiAgentEntity::class,
        BusinessTwinModelEntity::class,
        PredictionEntity::class,
        AutonomousDecisionEntity::class,
        OptimizationLogEntity::class,
        RiskAlertEntity::class,
        MarketIntelligenceEntity::class,
        ExecutionLogEntity::class,
        ManufacturerEntity::class,
        SupplierEntity::class,
        ReputationScoreEntity::class,
        BusinessConnectionEntity::class,
        FashionTrendEntity::class,
        GlobalIntelligenceEntity::class,
        OmegaCoreEntity::class,
        GlobalTradeDataEntity::class,
        CompetitorIntelligenceEntity::class,
        CapitalManagementEntity::class,
        SupplyChainAiEntity::class,
        OmegaTwinEntity::class,
        RevenueEngineEntity::class,
        OmegaHealthEntity::class,
        IndustryMasterEntity::class,
        CountryMasterEntity::class,
        GlobalEconomyEntity::class,
        ResearchReportEntity::class,
        MarketOpportunityEntity::class,
        ExpansionBlueprintEntity::class,
        InfinityAnalyticsEntity::class,
        UniversalMarketplaceEntity::class,
        CosmosNodeEntity::class,
        PlanetaryTradeRouteEntity::class,
        SovereignReserveEntity::class,
        AutonomousGovernanceLogEntity::class,
        SelfEvolvingModelEntity::class,
        CosmicMarketIndexEntity::class,
        PlanetarySimulationEntity::class,
        CosmosTelemetryEntity::class,
        CosmosCoreEntity::class,
        TradeNetworksEntity::class,
        GlobalRiskEntity::class,
        EconomicTwinsEntity::class,
        MarketCosmosEntity::class,
        SupplyGridEntity::class,
        CosmosHealthEntity::class,
        CosmosAnalyticsEntity::class,
        NexusCoreEntity::class,
        EnterpriseNetworkEntity::class,
        KnowledgeWebEntity::class,
        PartnershipNetworkEntity::class,
        OpportunityExchangeEntity::class,
        DecisionExchangeEntity::class,
        NexusHealthEntity::class,
        NexusAnalyticsEntity::class,
        FutureEngineEntity::class,
        SimulationNetworkEntity::class,
        EvolutionEngineEntity::class,
        OpportunityQuantumEntity::class,
        MarketQuantumEntity::class,
        DecisionMatrixEntity::class,
        RiskQuantumEntity::class,
        QuantumHealthEntity::class,
        AscensionCoreEntity::class,
        EconomicCivilizationEntity::class,
        ResourceIntelligenceEntity::class,
        TradeUniverseEntity::class,
        ProsperityEngineEntity::class,
        InnovationUniverseEntity::class,
        DecisionUniverseEntity::class,
        AscensionHealthEntity::class,
        OmniverseCoreEntity::class,
        EconomyNetworkEntity::class,
        MarketMatrixEntity::class,
        TradeGridEntity::class,
        KnowledgeFabricEntity::class,
        IndustryMatrixEntity::class,
        OpportunityUniverseEntity::class,
        OmniverseHealthEntity::class,
        OmniverseRiskEntity::class,
        OmniverseInnovationEntity::class,
        EternityCoreEntity::class,
        WealthUniverseEntity::class,
        DemandUniverseEntity::class,
        CapitalUniverseEntity::class,
        TradeInfinityEntity::class,
        KnowledgeEternityEntity::class,
        RiskShieldEntity::class,
        EternityHealthEntity::class,
        EternityInnovationEntity::class,
        TranscendenceCoreEntity::class,
        RealityCommerceEntity::class,
        EnterpriseCreatorEntity::class,
        TranscendenceOpportunityEntity::class,
        DemandNetworkEntity::class,
        CapitalCivilizationEntity::class,
        DecisionCosmosEntity::class,
        KnowledgeOceanEntity::class,
        TranscendenceEvolutionEntity::class,
        TranscendenceRealityTwinEntity::class,
        TranscendenceInnovationEntity::class,
        TranscendenceRiskEntity::class,
        TranscendenceHealthEntity::class,
        TranscendenceExpansionEntity::class,
        SupremacyCoreEntity::class,
        CivilizationGovernanceEntity::class,
        EconomicCommandEntity::class,
        SupremeOpportunityEntity::class,
        ExpansionNetworkEntity::class,
        CapitalMatrixEntity::class,
        TradeAuthorityEntity::class,
        DigitalCivilizationEntity::class,
        DecisionAuthorityEntity::class,
        KnowledgeGridEntity::class,
        InnovationAuthorityEntity::class,
        RiskShieldSupremacyEntity::class,
        HealthAuthorityEntity::class,
        SupremacyCommandTowerEntity::class,
        SovereigntyEngineEntity::class,
        SingularityPrimeCoreEntity::class,
        CivilizationEngineEntity::class,
        WealthGeneratorEntity::class,
        OpportunityCreatorEntity::class,
        DemandCosmosEntity::class,
        CapitalAuthorityEntity::class,
        TradeSupremacyEntity::class,
        RealityEngineEntity::class,
        DecisionPrimeEntity::class,
        KnowledgePrimeEntity::class,
        InnovationFactoryEntity::class,
        RiskShieldPrimeEntity::class,
        HealthPrimeEntity::class,
        PrimeCommandTowerEntity::class,
        EvolutionAuthorityEntity::class,
        AbsoluteCoreEntity::class,
        EconomicOSEntity::class,
        WealthMatrixEntity::class,
        OpportunityGridEntity::class,
        DemandMatrixEntity::class,
        CapitalSupremacyEntity::class,
        TradeNetworkEntity::class,
        RealityMatrixEntity::class,
        DecisionEngineEntity::class,
        KnowledgeMatrixEntity::class,
        InnovationEngineEntity::class,
        ProtectionSystemEntity::class,
        AbsoluteHealthEngineEntity::class,
        AbsoluteCommandTowerEntity::class,
        UnityEngineEntity::class,
        UltimaCoreEntity::class,
        CommerceCivilizationEntity::class,
        UltimaWealthUniverseEntity::class,
        FutureOpportunityEntity::class,
        UltimaDemandUniverseEntity::class,
        UltimaCapitalAuthorityEntity::class,
        TradeCivilizationEntity::class,
        UltimaRealityGridEntity::class,
        UltimaDecisionAuthorityEntity::class,
        KnowledgeCivilizationEntity::class,
        InnovationCivilizationEntity::class,
        ProtectionGridEntity::class,
        HealthCivilizationEntity::class,
        UltimaTowerEntity::class,
        UniversalHarmonyEngineEntity::class,
        AIPromptEntity::class,
        AIConversationEntity::class,
        AISuggestionEntity::class,
        AICatalogueRequestEntity::class,
        AICatalogueResultEntity::class,
        AICatalogueTemplateEntity::class,
        AIPricingRequestEntity::class,
        AIPricingResultEntity::class,
        AIPricingHistoryEntity::class,
        AIPricingRuleEntity::class,
        AIDemandRequestEntity::class,
        AIDemandForecastEntity::class,
        AIDemandHistoryEntity::class,
        AIDemandModelEntity::class,
        AIDealerRequestEntity::class,
        AIDealerRecommendationEntity::class,
        AIDealerScoreEntity::class,
        AIDealerGrowthForecastEntity::class,
        AIInventoryRequestEntity::class,
        AIInventoryForecastEntity::class,
        AIInventoryAlertEntity::class,
        AIInventoryHealthEntity::class,
        AIInventoryRecommendationEntity::class,
        AIFinanceRequestEntity::class,
        AIFinanceReportEntity::class,
        AICashFlowForecastEntity::class,
        AIFinancialHealthEntity::class,
        AIFinanceRecommendationEntity::class
    ],
    version = 44,
    exportSchema = false
)
abstract class VascsDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun productBatchDao(): ProductBatchDao
    abstract fun productImageDao(): ProductImageDao
    abstract fun catalogueGenerationJobDao(): CatalogueGenerationJobDao
    abstract fun mediaLibraryDao(): MediaLibraryDao
    abstract fun exportQueueDao(): ExportQueueDao
    abstract fun aiCatalogueImageDao(): AiCatalogueImageDao
    abstract fun aiImageArchiveDao(): AiImageArchiveDao
    abstract fun mediaCommandCenterDao(): MediaCommandCenterDao
    abstract fun dealerDao(): DealerDao
    abstract fun dealerProductDao(): DealerProductDao
    abstract fun dealerOrderDao(): DealerOrderDao
    abstract fun whatsAppCampaignDao(): WhatsAppCampaignDao
    abstract fun dealerCatalogueDao(): DealerCatalogueDao
    abstract fun socialAnalyticsDao(): SocialAnalyticsDao
    abstract fun orderMasterDao(): OrderMasterDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun packingSlipDao(): PackingSlipDao
    abstract fun dispatchDao(): DispatchDao
    abstract fun deliveryDao(): DeliveryDao
    abstract fun orderTrackingDao(): OrderTrackingDao
    abstract fun customerLeadDao(): CustomerLeadDao
    abstract fun quotationDao(): QuotationDao
    abstract fun followupDao(): FollowupDao
    abstract fun whatsappTemplateDao(): WhatsappTemplateDao
    abstract fun broadcastCampaignDao(): BroadcastCampaignDao
    abstract fun salesOrderDao(): SalesOrderDao
    abstract fun salesOrderItemDao(): SalesOrderItemDao
    abstract fun dispatchRecordDao(): DispatchRecordDao
    abstract fun trackingRecordDao(): TrackingRecordDao
    abstract fun invoiceRecordDao(): InvoiceRecordDao
    abstract fun paymentRecordDao(): PaymentRecordDao
    abstract fun dealerOutstandingDao(): DealerOutstandingDao
    abstract fun rawMaterialDao(): RawMaterialDao
    abstract fun fabricStockDao(): FabricStockDao
    abstract fun productionOrderDao(): ProductionOrderDao
    abstract fun productionBatchDao(): ProductionBatchDao
    abstract fun dyeingRecordDao(): DyeingRecordDao
    abstract fun embroideryRecordDao(): EmbroideryRecordDao
    abstract fun qualityCheckDao(): QualityCheckDao
    abstract fun finishedGoodsDao(): FinishedGoodsDao
    abstract fun workerDao(): WorkerDao
    abstract fun accountLedgerDao(): AccountLedgerDao
    abstract fun cashBookDao(): CashBookDao
    abstract fun bankBookDao(): BankBookDao
    abstract fun purchaseRegisterDao(): PurchaseRegisterDao
    abstract fun expenseRegisterDao(): ExpenseRegisterDao
    abstract fun accountsReceivableDao(): AccountsReceivableDao
    abstract fun accountsPayableDao(): AccountsPayableDao
    abstract fun gstReportDao(): GstReportDao
    abstract fun profitLossReportDao(): ProfitLossReportDao
    abstract fun balanceSheetReportDao(): BalanceSheetReportDao
    abstract fun companyDao(): CompanyDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun billingRecordDao(): BillingRecordDao
    abstract fun customerPortalDao(): CustomerPortalDao
    abstract fun dealerPortalDao(): DealerPortalDao
    abstract fun apiKeyDao(): ApiKeyDao
    abstract fun supportTicketDao(): SupportTicketDao
    abstract fun whiteLabelConfigDao(): WhiteLabelConfigDao
    abstract fun countryDao(): CountryDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun taxRuleDao(): TaxRuleDao
    abstract fun marketplaceProductDao(): MarketplaceProductDao
    abstract fun tradeLeadDao(): TradeLeadDao
    abstract fun exportDocumentDao(): ExportDocumentDao
    abstract fun importDocumentDao(): ImportDocumentDao
    abstract fun globalShipmentDao(): GlobalShipmentDao
    abstract fun globalWarehouseDao(): GlobalWarehouseDao
    abstract fun customerDao(): CustomerDao
    abstract fun vendorDao(): VendorDao
    abstract fun deliveryPartnerDao(): DeliveryPartnerDao
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun notificationDao(): NotificationDao
    abstract fun rewardPointDao(): RewardPointDao
    abstract fun aiEmployeeDao(): AiEmployeeDao
    abstract fun aiTaskDao(): AiTaskDao
    abstract fun aiForecastDao(): AiForecastDao
    abstract fun aiRecommendationDao(): AiRecommendationDao
    abstract fun aiDecisionDao(): AiDecisionDao
    abstract fun aiAutomationRuleDao(): AiAutomationRuleDao
    abstract fun aiActivityLogDao(): AiActivityLogDao
    abstract fun aiAgentDao(): AiAgentDao
    abstract fun businessTwinModelDao(): BusinessTwinModelDao
    abstract fun predictionDao(): PredictionDao
    abstract fun autonomousDecisionDao(): AutonomousDecisionDao
    abstract fun optimizationLogDao(): OptimizationLogDao
    abstract fun riskAlertDao(): RiskAlertDao
    abstract fun marketIntelligenceDao(): MarketIntelligenceDao
    abstract fun executionLogDao(): ExecutionLogDao
    abstract fun manufacturerDao(): ManufacturerDao
    abstract fun supplierDao(): SupplierDao
    abstract fun reputationScoreDao(): ReputationScoreDao
    abstract fun businessConnectionDao(): BusinessConnectionDao
    abstract fun fashionTrendDao(): FashionTrendDao
    abstract fun globalIntelligenceDao(): GlobalIntelligenceDao
    abstract fun omegaCoreDao(): OmegaCoreDao
    abstract fun globalTradeDataDao(): GlobalTradeDataDao
    abstract fun competitorIntelligenceDao(): CompetitorIntelligenceDao
    abstract fun capitalManagementDao(): CapitalManagementDao
    abstract fun supplyChainAiDao(): SupplyChainAiDao
    abstract fun omegaTwinDao(): OmegaTwinDao
    abstract fun revenueEngineDao(): RevenueEngineDao
    abstract fun omegaHealthDao(): OmegaHealthDao
    abstract fun industryMasterDao(): IndustryMasterDao
    abstract fun countryMasterDao(): CountryMasterDao
    abstract fun globalEconomyDao(): GlobalEconomyDao
    abstract fun researchReportDao(): ResearchReportDao
    abstract fun marketOpportunityDao(): MarketOpportunityDao
    abstract fun expansionBlueprintDao(): ExpansionBlueprintDao
    abstract fun infinityAnalyticsDao(): InfinityAnalyticsDao
    abstract fun universalMarketplaceDao(): UniversalMarketplaceDao
    abstract fun cosmosNodeDao(): CosmosNodeDao
    abstract fun planetaryTradeRouteDao(): PlanetaryTradeRouteDao
    abstract fun sovereignReserveDao(): SovereignReserveDao
    abstract fun autonomousGovernanceLogDao(): AutonomousGovernanceLogDao
    abstract fun selfEvolvingModelDao(): SelfEvolvingModelDao
    abstract fun cosmicMarketIndexDao(): CosmicMarketIndexDao
    abstract fun planetarySimulationDao(): PlanetarySimulationDao
    abstract fun cosmosTelemetryDao(): CosmosTelemetryDao
    abstract fun cosmosCoreDao(): CosmosCoreDao
    abstract fun tradeNetworksDao(): TradeNetworksDao
    abstract fun globalRiskDao(): GlobalRiskDao
    abstract fun economicTwinsDao(): EconomicTwinsDao
    abstract fun marketCosmosDao(): MarketCosmosDao
    abstract fun supplyGridDao(): SupplyGridDao
    abstract fun cosmosHealthDao(): CosmosHealthDao
    abstract fun cosmosAnalyticsDao(): CosmosAnalyticsDao
    abstract fun nexusCoreDao(): NexusCoreDao
    abstract fun enterpriseNetworkDao(): EnterpriseNetworkDao
    abstract fun knowledgeWebDao(): KnowledgeWebDao
    abstract fun partnershipNetworkDao(): PartnershipNetworkDao
    abstract fun opportunityExchangeDao(): OpportunityExchangeDao
    abstract fun decisionExchangeDao(): DecisionExchangeDao
    abstract fun nexusHealthDao(): NexusHealthDao
    abstract fun nexusAnalyticsDao(): NexusAnalyticsDao
    abstract fun futureEngineDao(): FutureEngineDao
    abstract fun simulationNetworkDao(): SimulationNetworkDao
    abstract fun evolutionEngineDao(): EvolutionEngineDao
    abstract fun opportunityQuantumDao(): OpportunityQuantumDao
    abstract fun marketQuantumDao(): MarketQuantumDao
    abstract fun decisionMatrixDao(): DecisionMatrixDao
    abstract fun riskQuantumDao(): RiskQuantumDao
    abstract fun quantumHealthDao(): QuantumHealthDao
    abstract fun ascensionCoreDao(): AscensionCoreDao
    abstract fun economicCivilizationDao(): EconomicCivilizationDao
    abstract fun resourceIntelligenceDao(): ResourceIntelligenceDao
    abstract fun tradeUniverseDao(): TradeUniverseDao
    abstract fun prosperityEngineDao(): ProsperityEngineDao
    abstract fun innovationUniverseDao(): InnovationUniverseDao
    abstract fun decisionUniverseDao(): DecisionUniverseDao
    abstract fun ascensionHealthDao(): AscensionHealthDao
    abstract fun omniverseCoreDao(): OmniverseCoreDao
    abstract fun economyNetworkDao(): EconomyNetworkDao
    abstract fun marketMatrixDao(): MarketMatrixDao
    abstract fun tradeGridDao(): TradeGridDao
    abstract fun knowledgeFabricDao(): KnowledgeFabricDao
    abstract fun industryMatrixDao(): IndustryMatrixDao
    abstract fun opportunityUniverseDao(): OpportunityUniverseDao
    abstract fun omniverseHealthDao(): OmniverseHealthDao
    abstract fun omniverseRiskDao(): OmniverseRiskDao
    abstract fun omniverseInnovationDao(): OmniverseInnovationDao
    abstract fun eternityCoreDao(): EternityCoreDao
    abstract fun wealthUniverseDao(): WealthUniverseDao
    abstract fun demandUniverseDao(): DemandUniverseDao
    abstract fun capitalUniverseDao(): CapitalUniverseDao
    abstract fun tradeInfinityDao(): TradeInfinityDao
    abstract fun knowledgeEternityDao(): KnowledgeEternityDao
    abstract fun riskShieldDao(): RiskShieldDao
    abstract fun eternityHealthDao(): EternityHealthDao
    abstract fun eternityInnovationDao(): EternityInnovationDao
    abstract fun transcendenceCoreDao(): TranscendenceCoreDao
    abstract fun realityCommerceDao(): RealityCommerceDao
    abstract fun enterpriseCreatorDao(): EnterpriseCreatorDao
    abstract fun transcendenceOpportunityDao(): TranscendenceOpportunityDao
    abstract fun demandNetworkDao(): DemandNetworkDao
    abstract fun capitalCivilizationDao(): CapitalCivilizationDao
    abstract fun decisionCosmosDao(): DecisionCosmosDao
    abstract fun knowledgeOceanDao(): KnowledgeOceanDao
    abstract fun transcendenceEvolutionDao(): TranscendenceEvolutionDao
    abstract fun transcendenceRealityTwinDao(): TranscendenceRealityTwinDao
    abstract fun transcendenceInnovationDao(): TranscendenceInnovationDao
    abstract fun transcendenceRiskDao(): TranscendenceRiskDao
    abstract fun transcendenceHealthDao(): TranscendenceHealthDao
    abstract fun transcendenceExpansionDao(): TranscendenceExpansionDao
    abstract fun supremacyCoreDao(): SupremacyCoreDao
    abstract fun civilizationGovernanceDao(): CivilizationGovernanceDao
    abstract fun economicCommandDao(): EconomicCommandDao
    abstract fun supremeOpportunityDao(): SupremeOpportunityDao
    abstract fun expansionNetworkDao(): ExpansionNetworkDao
    abstract fun capitalMatrixDao(): CapitalMatrixDao
    abstract fun tradeAuthorityDao(): TradeAuthorityDao
    abstract fun digitalCivilizationDao(): DigitalCivilizationDao
    abstract fun decisionAuthorityDao(): DecisionAuthorityDao
    abstract fun knowledgeGridDao(): KnowledgeGridDao
    abstract fun innovationAuthorityDao(): InnovationAuthorityDao
    abstract fun riskShieldSupremacyDao(): RiskShieldSupremacyDao
    abstract fun healthAuthorityDao(): HealthAuthorityDao
    abstract fun supremacyCommandTowerDao(): SupremacyCommandTowerDao
    abstract fun sovereigntyEngineDao(): SovereigntyEngineDao
    abstract fun singularityPrimeCoreDao(): SingularityPrimeCoreDao
    abstract fun civilizationEngineDao(): CivilizationEngineDao
    abstract fun wealthGeneratorDao(): WealthGeneratorDao
    abstract fun opportunityCreatorDao(): OpportunityCreatorDao
    abstract fun demandCosmosDao(): DemandCosmosDao
    abstract fun capitalAuthorityDao(): CapitalAuthorityDao
    abstract fun tradeSupremacyDao(): TradeSupremacyDao
    abstract fun realityEngineDao(): RealityEngineDao
    abstract fun decisionPrimeDao(): DecisionPrimeDao
    abstract fun knowledgePrimeDao(): KnowledgePrimeDao
    abstract fun innovationFactoryDao(): InnovationFactoryDao
    abstract fun riskShieldPrimeDao(): RiskShieldPrimeDao
    abstract fun healthPrimeDao(): HealthPrimeDao
    abstract fun primeCommandTowerDao(): PrimeCommandTowerDao
    abstract fun evolutionAuthorityDao(): EvolutionAuthorityDao

    abstract fun absoluteCoreDao(): AbsoluteCoreDao
    abstract fun economicOSDao(): EconomicOSDao
    abstract fun wealthMatrixDao(): WealthMatrixDao
    abstract fun opportunityGridDao(): OpportunityGridDao
    abstract fun demandMatrixDao(): DemandMatrixDao
    abstract fun capitalSupremacyDao(): CapitalSupremacyDao
    abstract fun tradeNetworkDao(): TradeNetworkDao
    abstract fun realityMatrixDao(): RealityMatrixDao
    abstract fun decisionEngineDao(): DecisionEngineDao
    abstract fun knowledgeMatrixDao(): KnowledgeMatrixDao
    abstract fun innovationEngineDao(): InnovationEngineDao
    abstract fun protectionSystemDao(): ProtectionSystemDao
    abstract fun healthEngineDao(): HealthEngineDao
    abstract fun absoluteCommandTowerDao(): AbsoluteCommandTowerDao
    abstract fun unityEngineDao(): UnityEngineDao

    abstract fun ultimaCoreDao(): UltimaCoreDao
    abstract fun commerceCivilizationDao(): CommerceCivilizationDao
    abstract fun ultimaWealthUniverseDao(): UltimaWealthUniverseDao
    abstract fun futureOpportunityDao(): FutureOpportunityDao
    abstract fun ultimaDemandUniverseDao(): UltimaDemandUniverseDao
    abstract fun ultimaCapitalAuthorityDao(): UltimaCapitalAuthorityDao
    abstract fun tradeCivilizationDao(): TradeCivilizationDao
    abstract fun ultimaRealityGridDao(): UltimaRealityGridDao
    abstract fun ultimaDecisionAuthorityDao(): UltimaDecisionAuthorityDao
    abstract fun knowledgeCivilizationDao(): KnowledgeCivilizationDao
    abstract fun innovationCivilizationDao(): InnovationCivilizationDao
    abstract fun protectionGridDao(): ProtectionGridDao
    abstract fun healthCivilizationDao(): HealthCivilizationDao
    abstract fun ultimaTowerDao(): UltimaTowerDao
    abstract fun universalHarmonyEngineDao(): UniversalHarmonyEngineDao

    abstract fun aiPromptDao(): AIPromptDao
    abstract fun aiConversationDao(): AIConversationDao
    abstract fun aiSuggestionDao(): AISuggestionDao
    abstract fun aiCatalogueDao(): AICatalogueDao
    abstract fun aiPricingDao(): AIPricingDao
    abstract fun aiDemandDao(): AIDemandDao
    abstract fun aiDealerDao(): AIDealerDao
    abstract fun aiInventoryDao(): AIInventoryDao
    abstract fun aiFinanceDao(): AIFinanceDao

    companion object {
        @Volatile
        private var INSTANCE: VascsDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `product_images` (
                        `id` TEXT NOT NULL,
                        `productId` TEXT NOT NULL,
                        `uri` TEXT NOT NULL,
                        `localPath` TEXT,
                        `fileName` TEXT,
                        `imageType` TEXT NOT NULL,
                        `isPrimary` INTEGER NOT NULL,
                        `sortOrder` INTEGER NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `catalogue_generation_jobs` (
                        `jobId` TEXT NOT NULL,
                        `productId` TEXT NOT NULL,
                        `sourceImageId` TEXT,
                        `sourceImageUri` TEXT,
                        `style` TEXT NOT NULL,
                        `modelId` TEXT,
                        `backgroundStyle` TEXT,
                        `pose` TEXT,
                        `resolution` TEXT,
                        `prompt` TEXT,
                        `negativePrompt` TEXT,
                        `status` TEXT NOT NULL,
                        `progress` INTEGER NOT NULL,
                        `remoteJobId` TEXT,
                        `resultImageId` TEXT,
                        `resultImageUri` TEXT,
                        `errorMessage` TEXT,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`jobId`)
                    )
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `media_library` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `productId` TEXT,
                        `sku` TEXT,
                        `qrNumber` TEXT,
                        `imageUri` TEXT NOT NULL,
                        `imageSource` TEXT NOT NULL,
                        `imageType` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL,
                        `updatedDate` INTEGER NOT NULL,
                        `width` INTEGER NOT NULL,
                        `height` INTEGER NOT NULL,
                        `isPrimary` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `notes` TEXT
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_catalogue_images` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `sku` TEXT NOT NULL,
                        `qrNumber` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `imageUri` TEXT NOT NULL,
                        `imageType` TEXT NOT NULL,
                        `imageSource` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL,
                        `updatedDate` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `width` INTEGER NOT NULL,
                        `height` INTEGER NOT NULL,
                        `isPrimary` INTEGER NOT NULL,
                        `notes` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Bridge migration
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Bridge migration
            }
        }

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `export_queue` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `productId` TEXT NOT NULL,
                        `sku` TEXT,
                        `exportType` TEXT NOT NULL,
                        `targetWidth` INTEGER NOT NULL,
                        `targetHeight` INTEGER NOT NULL,
                        `sourceImageUri` TEXT NOT NULL,
                        `outputImageUri` TEXT,
                        `status` TEXT NOT NULL,
                        `progress` INTEGER NOT NULL,
                        `errorMessage` TEXT,
                        `createdDate` INTEGER NOT NULL,
                        `updatedDate` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_4_7 = object : Migration(4, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                MIGRATION_6_7.migrate(db)
            }
        }

        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_catalogue_images` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `sku` TEXT NOT NULL,
                        `qrNumber` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `imageUri` TEXT NOT NULL,
                        `imageType` TEXT NOT NULL,
                        `imageSource` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL,
                        `updatedDate` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `width` INTEGER NOT NULL,
                        `height` INTEGER NOT NULL,
                        `isPrimary` INTEGER NOT NULL,
                        `notes` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_8_9 = object : Migration(8, 9) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_image_archive` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `archiveId` TEXT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `sku` TEXT NOT NULL,
                        `qrNumber` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `versionNumber` INTEGER NOT NULL,
                        `imageUri` TEXT NOT NULL,
                        `thumbnailUri` TEXT NOT NULL,
                        `imageSource` TEXT NOT NULL,
                        `imageType` TEXT NOT NULL,
                        `prompt` TEXT NOT NULL,
                        `negativePrompt` TEXT NOT NULL,
                        `modelName` TEXT NOT NULL,
                        `providerName` TEXT NOT NULL,
                        `generationDate` INTEGER NOT NULL,
                        `generationTime` TEXT NOT NULL,
                        `createdBy` TEXT NOT NULL,
                        `width` INTEGER NOT NULL,
                        `height` INTEGER NOT NULL,
                        `fileSize` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `usageCount` INTEGER NOT NULL,
                        `shareCount` INTEGER NOT NULL,
                        `downloadCount` INTEGER NOT NULL,
                        `coverAppliedCount` INTEGER NOT NULL,
                        `isDeleted` INTEGER NOT NULL,
                        `notes` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_ai_image_archive_archiveId` ON `ai_image_archive` (`archiveId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_image_archive_productId` ON `ai_image_archive` (`productId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_image_archive_sku` ON `ai_image_archive` (`sku`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_image_archive_qrNumber` ON `ai_image_archive` (`qrNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_image_archive_isDeleted` ON `ai_image_archive` (`isDeleted`)")
            }
        }

        val MIGRATION_9_10 = object : Migration(9, 10) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `media_command_center` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `mediaId` TEXT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `productName` TEXT NOT NULL,
                        `sku` TEXT NOT NULL,
                        `qrNumber` TEXT NOT NULL,
                        `mediaType` TEXT NOT NULL,
                        `mediaSource` TEXT NOT NULL,
                        `versionNumber` INTEGER NOT NULL,
                        `imageUri` TEXT NOT NULL,
                        `thumbnailUri` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL,
                        `updatedDate` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `width` INTEGER NOT NULL,
                        `height` INTEGER NOT NULL,
                        `fileSize` INTEGER NOT NULL,
                        `isPrimary` INTEGER NOT NULL,
                        `isArchived` INTEGER NOT NULL,
                        `isDeleted` INTEGER NOT NULL,
                        `shareCount` INTEGER NOT NULL,
                        `downloadCount` INTEGER NOT NULL,
                        `viewCount` INTEGER NOT NULL,
                        `notes` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_media_command_center_mediaId` ON `media_command_center` (`mediaId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_command_center_productId` ON `media_command_center` (`productId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_command_center_sku` ON `media_command_center` (`sku`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_command_center_qrNumber` ON `media_command_center` (`qrNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_command_center_mediaType` ON `media_command_center` (`mediaType`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_command_center_isArchived` ON `media_command_center` (`isArchived`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_media_command_center_isDeleted` ON `media_command_center` (`isDeleted`)")
            }
        }

        val MIGRATION_10_11 = object : Migration(10, 11) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dealers` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `dealerId` TEXT NOT NULL,
                        `dealerName` TEXT NOT NULL,
                        `firmName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `whatsapp` TEXT NOT NULL,
                        `email` TEXT NOT NULL,
                        `city` TEXT NOT NULL,
                        `state` TEXT NOT NULL,
                        `gstNumber` TEXT NOT NULL,
                        `address` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `creditLimit` REAL NOT NULL,
                        `dealerType` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_dealers_dealerId` ON `dealers` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealers_firmName` ON `dealers` (`firmName`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealers_mobile` ON `dealers` (`mobile`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealers_city` ON `dealers` (`city`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealers_dealerType` ON `dealers` (`dealerType`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealers_status` ON `dealers` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dealer_products` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `dealerId` TEXT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `assignedDate` INTEGER NOT NULL,
                        `specialPrice` REAL NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_products_dealerId` ON `dealer_products` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_products_productId` ON `dealer_products` (`productId`)")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_dealer_products_dealerId_productId` ON `dealer_products` (`dealerId`, `productId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `whatsapp_campaigns` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `campaignId` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `campaignType` TEXT NOT NULL,
                        `targetDealerType` TEXT NOT NULL,
                        `targetDealerCount` INTEGER NOT NULL,
                        `messageTemplate` TEXT NOT NULL,
                        `productIdsJson` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL,
                        `sentCount` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_whatsapp_campaigns_campaignId` ON `whatsapp_campaigns` (`campaignId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_whatsapp_campaigns_campaignType` ON `whatsapp_campaigns` (`campaignType`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_whatsapp_campaigns_status` ON `whatsapp_campaigns` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dealer_catalogues` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `catalogueId` TEXT NOT NULL,
                        `dealerId` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `catalogueType` TEXT NOT NULL,
                        `productIdsJson` TEXT NOT NULL,
                        `fileUri` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL,
                        `downloadCount` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_dealer_catalogues_catalogueId` ON `dealer_catalogues` (`catalogueId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_catalogues_dealerId` ON `dealer_catalogues` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_catalogues_catalogueType` ON `dealer_catalogues` (`catalogueType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dealer_users` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `dealerId` TEXT NOT NULL,
                        `username` TEXT NOT NULL,
                        `passwordHash` TEXT NOT NULL,
                        `lastLoginDate` INTEGER NOT NULL,
                        `isActive` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_dealer_users_dealerId` ON `dealer_users` (`dealerId`)")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_dealer_users_username` ON `dealer_users` (`username`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dealer_orders` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` TEXT NOT NULL,
                        `dealerId` TEXT NOT NULL,
                        `dealerName` TEXT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `productName` TEXT NOT NULL,
                        `qty` INTEGER NOT NULL,
                        `rate` REAL NOT NULL,
                        `amount` REAL NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdDate` INTEGER NOT NULL,
                        `notes` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_dealer_orders_orderId` ON `dealer_orders` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_orders_dealerId` ON `dealer_orders` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_orders_productId` ON `dealer_orders` (`productId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_orders_status` ON `dealer_orders` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `social_analytics` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `eventType` TEXT NOT NULL,
                        `dealerId` TEXT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `productName` TEXT NOT NULL,
                        `channel` TEXT NOT NULL,
                        `timestamp` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_social_analytics_eventType` ON `social_analytics` (`eventType`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_social_analytics_dealerId` ON `social_analytics` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_social_analytics_productId` ON `social_analytics` (`productId`)")
            }
        }

        val MIGRATION_11_12 = object : Migration(11, 12) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Intermediary version bump
            }
        }

        val MIGRATION_12_13 = object : Migration(12, 13) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `order_master` (
                        `orderId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderNumber` TEXT NOT NULL,
                        `dealerId` TEXT NOT NULL,
                        `dealerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `whatsapp` TEXT NOT NULL,
                        `orderDate` TEXT NOT NULL,
                        `totalItems` INTEGER NOT NULL,
                        `totalQty` INTEGER NOT NULL,
                        `totalAmount` REAL NOT NULL,
                        `gstAmount` REAL NOT NULL,
                        `netAmount` REAL NOT NULL,
                        `status` TEXT NOT NULL,
                        `remarks` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL,
                        `updatedDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_master_orderNumber` ON `order_master` (`orderNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_master_dealerId` ON `order_master` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_master_status` ON `order_master` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `order_items` (
                        `orderItemId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `productId` TEXT NOT NULL,
                        `sku` TEXT NOT NULL,
                        `qrNumber` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `qty` INTEGER NOT NULL,
                        `rate` REAL NOT NULL,
                        `amount` REAL NOT NULL,
                        `gst` REAL NOT NULL,
                        `netAmount` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_orderId` ON `order_items` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_productId` ON `order_items` (`productId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_sku` ON `order_items` (`sku`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `packing_slips` (
                        `packingId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `packingNumber` TEXT NOT NULL,
                        `totalBoxes` INTEGER NOT NULL,
                        `totalItems` INTEGER NOT NULL,
                        `packedBy` TEXT NOT NULL,
                        `packedDate` TEXT NOT NULL,
                        `remarks` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_packing_slips_orderId` ON `packing_slips` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_packing_slips_packingNumber` ON `packing_slips` (`packingNumber`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dispatches` (
                        `dispatchId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `dispatchNumber` TEXT NOT NULL,
                        `transportName` TEXT NOT NULL,
                        `lrNumber` TEXT NOT NULL,
                        `vehicleNumber` TEXT NOT NULL,
                        `dispatchDate` TEXT NOT NULL,
                        `expectedDeliveryDate` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dispatches_orderId` ON `dispatches` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dispatches_dispatchNumber` ON `dispatches` (`dispatchNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dispatches_lrNumber` ON `dispatches` (`lrNumber`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `deliveries` (
                        `deliveryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `deliveredDate` TEXT NOT NULL,
                        `receivedBy` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `remarks` TEXT NOT NULL,
                        `proofImageUri` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_deliveries_orderId` ON `deliveries` (`orderId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `order_tracking` (
                        `trackingId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `message` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_tracking_orderId` ON `order_tracking` (`orderId`)")
            }
        }

        val MIGRATION_11_13 = object : Migration(11, 13) {
            override fun migrate(db: SupportSQLiteDatabase) {
                MIGRATION_12_13.migrate(db)
            }
        }

        val MIGRATION_13_14 = object : Migration(13, 14) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `customer_leads` (
                        `leadId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `customerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `whatsapp` TEXT NOT NULL,
                        `city` TEXT NOT NULL,
                        `state` TEXT NOT NULL,
                        `source` TEXT NOT NULL,
                        `interestedProduct` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `remarks` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_customer_leads_mobile` ON `customer_leads` (`mobile`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_customer_leads_status` ON `customer_leads` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `quotations` (
                        `quotationId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `quotationNo` TEXT NOT NULL,
                        `leadId` INTEGER NOT NULL,
                        `customerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `productsJson` TEXT NOT NULL,
                        `totalQty` INTEGER NOT NULL,
                        `totalAmount` REAL NOT NULL,
                        `gstAmount` REAL NOT NULL,
                        `netAmount` REAL NOT NULL,
                        `validityDate` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_quotations_quotationNo` ON `quotations` (`quotationNo`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_quotations_leadId` ON `quotations` (`leadId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `followups` (
                        `followupId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `leadId` INTEGER NOT NULL,
                        `customerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `reminderType` TEXT NOT NULL,
                        `dueDate` TEXT NOT NULL,
                        `notes` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_followups_leadId` ON `followups` (`leadId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_followups_status` ON `followups` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `whatsapp_templates` (
                        `templateId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `title` TEXT NOT NULL,
                        `templateType` TEXT NOT NULL,
                        `content` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `broadcast_campaigns` (
                        `campaignId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `campaignName` TEXT NOT NULL,
                        `targetSegment` TEXT NOT NULL,
                        `targetCount` INTEGER NOT NULL,
                        `sentCount` INTEGER NOT NULL,
                        `deliveredCount` INTEGER NOT NULL,
                        `templateUsed` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_14_15 = object : Migration(14, 15) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `packing_slips` ADD COLUMN `boxNumber` TEXT NOT NULL DEFAULT 'BOX-1'")
                db.execSQL("ALTER TABLE `packing_slips` ADD COLUMN `packingDate` TEXT NOT NULL DEFAULT ''")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `sales_orders` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderNumber` TEXT NOT NULL,
                        `dealerId` INTEGER NOT NULL,
                        `dealerName` TEXT NOT NULL,
                        `orderDate` TEXT NOT NULL,
                        `orderStatus` TEXT NOT NULL,
                        `totalQty` INTEGER NOT NULL,
                        `totalAmount` REAL NOT NULL,
                        `gstAmount` REAL NOT NULL,
                        `netAmount` REAL NOT NULL,
                        `remarks` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL,
                        `updatedDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_sales_orders_orderNumber` ON `sales_orders` (`orderNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_sales_orders_dealerId` ON `sales_orders` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_sales_orders_orderStatus` ON `sales_orders` (`orderStatus`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `sales_order_items` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `sku` TEXT NOT NULL,
                        `qrNumber` TEXT NOT NULL,
                        `productName` TEXT NOT NULL,
                        `quantity` INTEGER NOT NULL,
                        `rate` REAL NOT NULL,
                        `gstPercent` REAL NOT NULL,
                        `amount` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_sales_order_items_orderId` ON `sales_order_items` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_sales_order_items_productId` ON `sales_order_items` (`productId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_sales_order_items_sku` ON `sales_order_items` (`sku`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dispatch_records` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `dispatchNumber` TEXT NOT NULL,
                        `dispatchDate` TEXT NOT NULL,
                        `courierName` TEXT NOT NULL,
                        `vehicleNumber` TEXT NOT NULL,
                        `trackingNumber` TEXT NOT NULL,
                        `packedBy` TEXT NOT NULL,
                        `dispatchStatus` TEXT NOT NULL,
                        `remarks` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dispatch_records_orderId` ON `dispatch_records` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dispatch_records_dispatchNumber` ON `dispatch_records` (`dispatchNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dispatch_records_trackingNumber` ON `dispatch_records` (`trackingNumber`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `tracking_records` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `dispatchId` INTEGER NOT NULL,
                        `trackingNumber` TEXT NOT NULL,
                        `courierName` TEXT NOT NULL,
                        `currentStatus` TEXT NOT NULL,
                        `lastUpdated` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_tracking_records_dispatchId` ON `tracking_records` (`dispatchId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_tracking_records_trackingNumber` ON `tracking_records` (`trackingNumber`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `invoice_records` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `invoiceNumber` TEXT NOT NULL,
                        `invoiceType` TEXT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `dealerId` INTEGER NOT NULL,
                        `invoiceDate` TEXT NOT NULL,
                        `taxableAmount` REAL NOT NULL,
                        `gstAmount` REAL NOT NULL,
                        `netAmount` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_invoice_records_invoiceNumber` ON `invoice_records` (`invoiceNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_invoice_records_orderId` ON `invoice_records` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_invoice_records_dealerId` ON `invoice_records` (`dealerId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `payment_records` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `orderId` INTEGER NOT NULL,
                        `dealerId` INTEGER NOT NULL,
                        `paymentDate` TEXT NOT NULL,
                        `paymentMode` TEXT NOT NULL,
                        `receivedAmount` REAL NOT NULL,
                        `pendingAmount` REAL NOT NULL,
                        `referenceNumber` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_payment_records_dealerId` ON `payment_records` (`dealerId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_payment_records_orderId` ON `payment_records` (`orderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_payment_records_paymentDate` ON `payment_records` (`paymentDate`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dealer_outstanding` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `dealerId` INTEGER NOT NULL,
                        `dealerName` TEXT NOT NULL,
                        `totalSales` REAL NOT NULL,
                        `totalReceived` REAL NOT NULL,
                        `outstandingAmount` REAL NOT NULL,
                        `lastUpdated` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_outstanding_dealerId` ON `dealer_outstanding` (`dealerId`)")
            }
        }

        val MIGRATION_15_16 = object : Migration(15, 16) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `raw_materials` (
                        `materialId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `materialCode` TEXT NOT NULL,
                        `materialName` TEXT NOT NULL,
                        `materialCategory` TEXT NOT NULL,
                        `unit` TEXT NOT NULL,
                        `openingStock` REAL NOT NULL,
                        `currentStock` REAL NOT NULL,
                        `minimumStock` REAL NOT NULL,
                        `purchaseRate` REAL NOT NULL,
                        `supplierName` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_raw_materials_materialCode` ON `raw_materials` (`materialCode`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_raw_materials_materialCategory` ON `raw_materials` (`materialCategory`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `fabric_stock` (
                        `fabricId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `fabricCode` TEXT NOT NULL,
                        `fabricName` TEXT NOT NULL,
                        `colour` TEXT NOT NULL,
                        `gsm` INTEGER NOT NULL,
                        `width` REAL NOT NULL,
                        `meterAvailable` REAL NOT NULL,
                        `ratePerMeter` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_fabric_stock_fabricCode` ON `fabric_stock` (`fabricCode`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_fabric_stock_colour` ON `fabric_stock` (`colour`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `production_orders` (
                        `productionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `productionNumber` TEXT NOT NULL,
                        `designName` TEXT NOT NULL,
                        `productionDate` TEXT NOT NULL,
                        `targetQty` INTEGER NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_production_orders_productionNumber` ON `production_orders` (`productionNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_production_orders_status` ON `production_orders` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `production_batches` (
                        `batchId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `batchCode` TEXT NOT NULL,
                        `productionId` INTEGER NOT NULL,
                        `fabricUsed` TEXT NOT NULL,
                        `colourUsed` TEXT NOT NULL,
                        `workerName` TEXT NOT NULL,
                        `machineName` TEXT NOT NULL,
                        `batchQty` INTEGER NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_production_batches_batchCode` ON `production_batches` (`batchCode`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_production_batches_productionId` ON `production_batches` (`productionId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_production_batches_status` ON `production_batches` (`status`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dyeing_records` (
                        `dyeingId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `batchId` INTEGER NOT NULL,
                        `colourName` TEXT NOT NULL,
                        `shadeCode` TEXT NOT NULL,
                        `dyeDate` TEXT NOT NULL,
                        `operator` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dyeing_records_batchId` ON `dyeing_records` (`batchId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `embroidery_records` (
                        `embroideryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `batchId` INTEGER NOT NULL,
                        `designCode` TEXT NOT NULL,
                        `machineUsed` TEXT NOT NULL,
                        `operator` TEXT NOT NULL,
                        `workStatus` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_embroidery_records_batchId` ON `embroidery_records` (`batchId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `quality_checks` (
                        `qcId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `batchId` INTEGER NOT NULL,
                        `checkedBy` TEXT NOT NULL,
                        `checkedDate` TEXT NOT NULL,
                        `passQty` INTEGER NOT NULL,
                        `rejectQty` INTEGER NOT NULL,
                        `remarks` TEXT NOT NULL,
                        `qcResult` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_quality_checks_batchId` ON `quality_checks` (`batchId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `finished_goods` (
                        `finishedId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `batchId` INTEGER NOT NULL,
                        `productId` TEXT NOT NULL,
                        `sku` TEXT NOT NULL,
                        `qrNumber` TEXT NOT NULL,
                        `finishedQty` INTEGER NOT NULL,
                        `availableQty` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_finished_goods_batchId` ON `finished_goods` (`batchId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_finished_goods_sku` ON `finished_goods` (`sku`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_finished_goods_qrNumber` ON `finished_goods` (`qrNumber`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `workers` (
                        `workerId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `workerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `department` TEXT NOT NULL,
                        `salaryType` TEXT NOT NULL,
                        `ratePerPiece` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_workers_department` ON `workers` (`department`)")
            }
        }

        val MIGRATION_16_17 = object : Migration(16, 17) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `chart_of_accounts` (
                        `ledgerId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `ledgerCode` TEXT NOT NULL,
                        `ledgerName` TEXT NOT NULL,
                        `ledgerGroup` TEXT NOT NULL,
                        `openingBalance` REAL NOT NULL,
                        `currentBalance` REAL NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_chart_of_accounts_ledgerCode` ON `chart_of_accounts` (`ledgerCode`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_chart_of_accounts_ledgerGroup` ON `chart_of_accounts` (`ledgerGroup`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `cash_book` (
                        `cashTxnId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `txnDate` TEXT NOT NULL,
                        `voucherNumber` TEXT NOT NULL,
                        `particulars` TEXT NOT NULL,
                        `debitAmount` REAL NOT NULL,
                        `creditAmount` REAL NOT NULL,
                        `balance` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_cash_book_voucherNumber` ON `cash_book` (`voucherNumber`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `bank_book` (
                        `bankTxnId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `bankName` TEXT NOT NULL,
                        `txnDate` TEXT NOT NULL,
                        `chequeNumber` TEXT NOT NULL,
                        `utrNumber` TEXT NOT NULL,
                        `debitAmount` REAL NOT NULL,
                        `creditAmount` REAL NOT NULL,
                        `balance` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_bank_book_bankName` ON `bank_book` (`bankName`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `purchase_register` (
                        `purchaseId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `purchaseNumber` TEXT NOT NULL,
                        `purchaseType` TEXT NOT NULL,
                        `supplierName` TEXT NOT NULL,
                        `invoiceNumber` TEXT NOT NULL,
                        `invoiceDate` TEXT NOT NULL,
                        `taxableAmount` REAL NOT NULL,
                        `gstAmount` REAL NOT NULL,
                        `netAmount` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_purchase_register_purchaseNumber` ON `purchase_register` (`purchaseNumber`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_purchase_register_supplierName` ON `purchase_register` (`supplierName`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `expense_register` (
                        `expenseId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `expenseDate` TEXT NOT NULL,
                        `expenseCategory` TEXT NOT NULL,
                        `amount` REAL NOT NULL,
                        `remarks` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_expense_register_expenseCategory` ON `expense_register` (`expenseCategory`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `receivables` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `dealerId` INTEGER NOT NULL,
                        `dealerName` TEXT NOT NULL,
                        `invoiceAmount` REAL NOT NULL,
                        `receivedAmount` REAL NOT NULL,
                        `pendingAmount` REAL NOT NULL,
                        `dueDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_receivables_dealerId` ON `receivables` (`dealerId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `payables` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `supplierId` INTEGER NOT NULL,
                        `supplierName` TEXT NOT NULL,
                        `billAmount` REAL NOT NULL,
                        `paidAmount` REAL NOT NULL,
                        `pendingAmount` REAL NOT NULL,
                        `dueDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_payables_supplierId` ON `payables` (`supplierId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `gst_reports` (
                        `reportId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `reportPeriod` TEXT NOT NULL,
                        `cgst` REAL NOT NULL,
                        `sgst` REAL NOT NULL,
                        `igst` REAL NOT NULL,
                        `inputTax` REAL NOT NULL,
                        `outputTax` REAL NOT NULL,
                        `netTaxPayable` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_gst_reports_reportPeriod` ON `gst_reports` (`reportPeriod`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `profit_loss_reports` (
                        `reportId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `period` TEXT NOT NULL,
                        `sales` REAL NOT NULL,
                        `purchase` REAL NOT NULL,
                        `grossProfit` REAL NOT NULL,
                        `expenses` REAL NOT NULL,
                        `netProfit` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_profit_loss_reports_period` ON `profit_loss_reports` (`period`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `balance_sheet_reports` (
                        `reportId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `financialYear` TEXT NOT NULL,
                        `assets` REAL NOT NULL,
                        `liabilities` REAL NOT NULL,
                        `capital` REAL NOT NULL,
                        `currentAssets` REAL NOT NULL,
                        `currentLiabilities` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_balance_sheet_reports_financialYear` ON `balance_sheet_reports` (`financialYear`)")
            }
        }

        val MIGRATION_17_18 = object : Migration(17, 18) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `companies` (
                        `companyId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyCode` TEXT NOT NULL,
                        `companyName` TEXT NOT NULL,
                        `ownerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `email` TEXT NOT NULL,
                        `gstin` TEXT NOT NULL,
                        `address` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_companies_companyCode` ON `companies` (`companyCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `subscriptions` (
                        `subscriptionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyId` INTEGER NOT NULL,
                        `planName` TEXT NOT NULL,
                        `maxUsers` INTEGER NOT NULL,
                        `maxProducts` INTEGER NOT NULL,
                        `maxBranches` INTEGER NOT NULL,
                        `startDate` TEXT NOT NULL,
                        `endDate` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_subscriptions_companyId` ON `subscriptions` (`companyId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `billing_records` (
                        `billingId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyId` INTEGER NOT NULL,
                        `invoiceNumber` TEXT NOT NULL,
                        `amount` REAL NOT NULL,
                        `paymentStatus` TEXT NOT NULL,
                        `billingDate` TEXT NOT NULL,
                        `dueDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_billing_records_companyId` ON `billing_records` (`companyId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `customer_portals` (
                        `portalId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyId` INTEGER NOT NULL,
                        `customerName` TEXT NOT NULL,
                        `email` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_customer_portals_companyId` ON `customer_portals` (`companyId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `dealer_portals` (
                        `portalId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyId` INTEGER NOT NULL,
                        `dealerCode` TEXT NOT NULL,
                        `dealerName` TEXT NOT NULL,
                        `outstandingBalance` REAL NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_dealer_portals_companyId` ON `dealer_portals` (`companyId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `api_keys` (
                        `keyId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyId` INTEGER NOT NULL,
                        `apiKey` TEXT NOT NULL,
                        `clientName` TEXT NOT NULL,
                        `rateLimit` INTEGER NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_api_keys_companyId` ON `api_keys` (`companyId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `support_tickets` (
                        `ticketId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyId` INTEGER NOT NULL,
                        `subject` TEXT NOT NULL,
                        `category` TEXT NOT NULL,
                        `priority` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_support_tickets_companyId` ON `support_tickets` (`companyId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `white_label_configs` (
                        `configId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyId` INTEGER NOT NULL,
                        `brandName` TEXT NOT NULL,
                        `logoUrl` TEXT NOT NULL,
                        `primaryColorHex` TEXT NOT NULL,
                        `secondaryColorHex` TEXT NOT NULL,
                        `customDomain` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_white_label_configs_companyId` ON `white_label_configs` (`companyId`)")
            }
        }

        val MIGRATION_18_19 = object : Migration(18, 19) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `countries` (
                        `countryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `countryCode` TEXT NOT NULL,
                        `countryName` TEXT NOT NULL,
                        `currencyCode` TEXT NOT NULL,
                        `taxSystem` TEXT NOT NULL,
                        `timezone` TEXT NOT NULL,
                        `language` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_countries_countryCode` ON `countries` (`countryCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `currencies` (
                        `currencyId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `currencyCode` TEXT NOT NULL,
                        `currencyName` TEXT NOT NULL,
                        `symbol` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_currencies_currencyCode` ON `currencies` (`currencyCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `currency_rates` (
                        `rateId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `currencyCode` TEXT NOT NULL,
                        `exchangeRate` REAL NOT NULL,
                        `effectiveDate` TEXT NOT NULL,
                        `source` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_currency_rates_currencyCode` ON `currency_rates` (`currencyCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `tax_rules` (
                        `ruleId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `countryCode` TEXT NOT NULL,
                        `taxType` TEXT NOT NULL,
                        `taxRate` REAL NOT NULL,
                        `description` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_tax_rules_countryCode` ON `tax_rules` (`countryCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `marketplace_products` (
                        `marketProductId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `productId` INTEGER NOT NULL,
                        `productName` TEXT NOT NULL,
                        `countryCode` TEXT NOT NULL,
                        `localPrice` REAL NOT NULL,
                        `currencyCode` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_marketplace_products_countryCode` ON `marketplace_products` (`countryCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `trade_leads` (
                        `leadId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `supplierName` TEXT NOT NULL,
                        `buyerName` TEXT NOT NULL,
                        `country` TEXT NOT NULL,
                        `productCategory` TEXT NOT NULL,
                        `quantity` INTEGER NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_trade_leads_country` ON `trade_leads` (`country`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `export_documents` (
                        `documentId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `invoiceNumber` TEXT NOT NULL,
                        `countryCode` TEXT NOT NULL,
                        `documentType` TEXT NOT NULL,
                        `issuedDate` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_export_documents_countryCode` ON `export_documents` (`countryCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `import_documents` (
                        `documentId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `poNumber` TEXT NOT NULL,
                        `countryCode` TEXT NOT NULL,
                        `vendorName` TEXT NOT NULL,
                        `importDuty` REAL NOT NULL,
                        `totalCost` REAL NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_import_documents_countryCode` ON `import_documents` (`countryCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `global_shipments` (
                        `shipmentId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `trackingNumber` TEXT NOT NULL,
                        `courierPartner` TEXT NOT NULL,
                        `originCountry` TEXT NOT NULL,
                        `destinationCountry` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `estimatedDelivery` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_global_shipments_trackingNumber` ON `global_shipments` (`trackingNumber`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `global_warehouses` (
                        `warehouseId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `warehouseName` TEXT NOT NULL,
                        `countryCode` TEXT NOT NULL,
                        `city` TEXT NOT NULL,
                        `capacity` INTEGER NOT NULL,
                        `currentStock` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_global_warehouses_countryCode` ON `global_warehouses` (`countryCode`)")
            }
        }

        val MIGRATION_19_20 = object : Migration(19, 20) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `customers` (
                        `customerId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `customerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `email` TEXT NOT NULL,
                        `address` TEXT NOT NULL,
                        `city` TEXT NOT NULL,
                        `loyaltyPoints` INTEGER NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_customers_mobile` ON `customers` (`mobile`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `vendors` (
                        `vendorId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `vendorCode` TEXT NOT NULL,
                        `vendorName` TEXT NOT NULL,
                        `contactPerson` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `email` TEXT NOT NULL,
                        `materialType` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `performanceScore` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_vendors_vendorCode` ON `vendors` (`vendorCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `delivery_partners` (
                        `partnerId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `partnerCode` TEXT NOT NULL,
                        `partnerName` TEXT NOT NULL,
                        `mobile` TEXT NOT NULL,
                        `vehicleNumber` TEXT NOT NULL,
                        `activeDeliveriesCount` INTEGER NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_delivery_partners_partnerCode` ON `delivery_partners` (`partnerCode`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `chat_messages` (
                        `messageId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `senderId` TEXT NOT NULL,
                        `receiverId` TEXT NOT NULL,
                        `senderRole` TEXT NOT NULL,
                        `messageText` TEXT NOT NULL,
                        `attachmentUrl` TEXT NOT NULL,
                        `timestamp` TEXT NOT NULL,
                        `isRead` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_chat_messages_senderId` ON `chat_messages` (`senderId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_chat_messages_receiverId` ON `chat_messages` (`receiverId`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `notifications` (
                        `notificationId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `targetUser` TEXT NOT NULL,
                        `channel` TEXT NOT NULL,
                        `alertType` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `message` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL,
                        `isRead` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_notifications_targetUser` ON `notifications` (`targetUser`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `reward_points` (
                        `rewardId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `userCode` TEXT NOT NULL,
                        `userType` TEXT NOT NULL,
                        `pointsEarned` INTEGER NOT NULL,
                        `activityDescription` TEXT NOT NULL,
                        `dateEarned` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_reward_points_userCode` ON `reward_points` (`userCode`)")
            }
        }

        val MIGRATION_20_21 = object : Migration(20, 21) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_employees` (
                        `employeeId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `role` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `healthScore` INTEGER NOT NULL,
                        `lastAction` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_ai_employees_role` ON `ai_employees` (`role`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_tasks` (
                        `taskId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `assignedRole` TEXT NOT NULL,
                        `taskType` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `description` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_tasks_assignedRole` ON `ai_tasks` (`assignedRole`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_forecasts` (
                        `forecastId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `forecastType` TEXT NOT NULL,
                        `period` TEXT NOT NULL,
                        `predictedValue` TEXT NOT NULL,
                        `confidenceScore` REAL NOT NULL,
                        `generatedDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_forecasts_forecastType` ON `ai_forecasts` (`forecastType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_recommendations` (
                        `recommendationId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `category` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `impactScore` INTEGER NOT NULL,
                        `actionText` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_recommendations_category` ON `ai_recommendations` (`category`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_decisions` (
                        `decisionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `boardTopic` TEXT NOT NULL,
                        `consensusDecision` TEXT NOT NULL,
                        `actionPlan` TEXT NOT NULL,
                        `riskLevel` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_decisions_boardTopic` ON `ai_decisions` (`boardTopic`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_automation_rules` (
                        `ruleId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `triggerCondition` TEXT NOT NULL,
                        `actionCommand` TEXT NOT NULL,
                        `isActive` INTEGER NOT NULL,
                        `executionCount` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_activity_logs` (
                        `logId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `employeeRole` TEXT NOT NULL,
                        `actionName` TEXT NOT NULL,
                        `details` TEXT NOT NULL,
                        `timestamp` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_activity_logs_employeeRole` ON `ai_activity_logs` (`employeeRole`)")
            }
        }

        val MIGRATION_21_22 = object : Migration(21, 22) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_agents` (
                        `agentId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `agentType` TEXT NOT NULL,
                        `agentName` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `tasksCompleted` INTEGER NOT NULL,
                        `performanceScore` REAL NOT NULL,
                        `executionMode` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_ai_agents_agentType` ON `ai_agents` (`agentType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `business_twin_models` (
                        `twinId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `scenarioName` TEXT NOT NULL,
                        `expectedSalesInr` REAL NOT NULL,
                        `expectedProfitInr` REAL NOT NULL,
                        `riskLevel` TEXT NOT NULL,
                        `growthMultiplier` REAL NOT NULL,
                        `simulatedDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_business_twin_models_scenarioName` ON `business_twin_models` (`scenarioName`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `predictions` (
                        `predictionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `targetDomain` TEXT NOT NULL,
                        `periodHorizon` TEXT NOT NULL,
                        `predictionValue` TEXT NOT NULL,
                        `confidence` REAL NOT NULL,
                        `stockActionSuggestion` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_predictions_targetDomain` ON `predictions` (`targetDomain`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `autonomous_decisions` (
                        `decisionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `decisionCategory` TEXT NOT NULL,
                        `decisionTitle` TEXT NOT NULL,
                        `recommendationText` TEXT NOT NULL,
                        `approvalMode` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_autonomous_decisions_decisionCategory` ON `autonomous_decisions` (`decisionCategory`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `optimization_logs` (
                        `logId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `optimizationArea` TEXT NOT NULL,
                        `originalState` TEXT NOT NULL,
                        `optimizedState` TEXT NOT NULL,
                        `gainPercentage` REAL NOT NULL,
                        `timestamp` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_optimization_logs_optimizationArea` ON `optimization_logs` (`optimizationArea`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `risk_alerts` (
                        `riskId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `riskCategory` TEXT NOT NULL,
                        `severityLevel` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `description` TEXT NOT NULL,
                        `mitigationPlan` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_risk_alerts_riskCategory` ON `risk_alerts` (`riskCategory`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `market_intelligence` (
                        `intelligenceId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `trendType` TEXT NOT NULL,
                        `trendTitle` TEXT NOT NULL,
                        `impactAssessment` TEXT NOT NULL,
                        `recommendedAction` TEXT NOT NULL,
                        `capturedDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_market_intelligence_trendType` ON `market_intelligence` (`trendType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `execution_logs` (
                        `logId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `targetChannel` TEXT NOT NULL,
                        `executionMode` TEXT NOT NULL,
                        `executionSummary` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `timestamp` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_execution_logs_targetChannel` ON `execution_logs` (`targetChannel`)")
            }
        }

        val MIGRATION_22_23 = object : Migration(22, 23) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `manufacturers` (
                        `manufacturerId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `companyName` TEXT NOT NULL,
                        `location` TEXT NOT NULL,
                        `productionUnitsCount` INTEGER NOT NULL,
                        `monthlyCapacityPcs` INTEGER NOT NULL,
                        `mainCategories` TEXT NOT NULL,
                        `factoryRating` REAL NOT NULL,
                        `globalVisibilityStatus` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_manufacturers_companyName` ON `manufacturers` (`companyName`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `suppliers` (
                        `supplierId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `supplierName` TEXT NOT NULL,
                        `supplierType` TEXT NOT NULL,
                        `location` TEXT NOT NULL,
                        `costIndex` TEXT NOT NULL,
                        `qualityRating` REAL NOT NULL,
                        `aiRecommendationScore` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_suppliers_supplierType` ON `suppliers` (`supplierType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `marketplace_products` (
                        `productId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `title` TEXT NOT NULL,
                        `marketplaceType` TEXT NOT NULL,
                        `wholesalePriceInr` REAL NOT NULL,
                        `minOrderQuantity` INTEGER NOT NULL,
                        `sellerName` TEXT NOT NULL,
                        `category` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_marketplace_products_marketplaceType` ON `marketplace_products` (`marketplaceType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `trade_leads` (
                        `leadId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `leadType` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `requirementDetails` TEXT NOT NULL,
                        `quantityRequired` INTEGER NOT NULL,
                        `targetPriceInr` REAL NOT NULL,
                        `aiLeadScore` INTEGER NOT NULL,
                        `postedDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_trade_leads_leadType` ON `trade_leads` (`leadType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `reputation_scores` (
                        `scoreId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `entityType` TEXT NOT NULL,
                        `entityName` TEXT NOT NULL,
                        `overallScore` REAL NOT NULL,
                        `orderFulfillmentRate` REAL NOT NULL,
                        `paymentTimelinessRate` REAL NOT NULL,
                        `reviewRating` REAL NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_reputation_scores_entityType` ON `reputation_scores` (`entityType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `business_connections` (
                        `connectionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `partyA` TEXT NOT NULL,
                        `partyB` TEXT NOT NULL,
                        `connectionType` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `proposalText` TEXT NOT NULL,
                        `createdDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_business_connections_connectionType` ON `business_connections` (`connectionType`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `fashion_trends` (
                        `trendId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `trendCategory` TEXT NOT NULL,
                        `trendName` TEXT NOT NULL,
                        `trajectory` TEXT NOT NULL,
                        `projectedGrowthPct` REAL NOT NULL,
                        `primaryRegion` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_fashion_trends_trendCategory` ON `fashion_trends` (`trendCategory`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `global_intelligence` (
                        `intelligenceId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `regionCountry` TEXT NOT NULL,
                        `marketPotentialScore` INTEGER NOT NULL,
                        `recommendedCategory` TEXT NOT NULL,
                        `exportOpportunityInr` REAL NOT NULL,
                        `tariffRiskLevel` TEXT NOT NULL,
                        `capturedDate` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_global_intelligence_regionCountry` ON `global_intelligence` (`regionCountry`)")
            }
        }

        fun getDatabase(context: Context): VascsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VascsDatabase::class.java,
                    "vascs_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7, MIGRATION_4_7, MIGRATION_7_8, MIGRATION_8_9, MIGRATION_9_10, MIGRATION_10_11, MIGRATION_11_12, MIGRATION_12_13, MIGRATION_11_13, MIGRATION_13_14, MIGRATION_14_15, MIGRATION_15_16, MIGRATION_16_17, MIGRATION_17_18, MIGRATION_18_19, MIGRATION_19_20, MIGRATION_20_21, MIGRATION_21_22, MIGRATION_22_23)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
