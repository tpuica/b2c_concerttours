package de.stripedgiraffe.concerttours.promotions.converters.populator;

import de.hybris.platform.commerceservices.stock.CommerceStockService;
import de.hybris.platform.commerceservices.stock.strategies.WarehouseSelectionStrategy;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ruleengineservices.converters.populator.OrderEntryRaoPopulator;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.RAOConsumptionSupport;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import org.springframework.beans.factory.annotation.Required;
import de.hybris.platform.warehousing.atp.services.impl.WarehousingCommerceStockService;

import java.util.Collection;

public class CustomOrderEntryRaoPopulator extends OrderEntryRaoPopulator {

    private CommerceStockService commerceStockService;

    private BaseStoreService baseStoreService;

    private StockService stockService;

    private WarehouseSelectionStrategy warehouseSelectionStrategy;


    @Override
    public void populate(AbstractOrderEntryModel source, OrderEntryRAO target) {
        super.populate(source, target);

        final BaseStoreModel baseStore = getBaseStoreService().getCurrentBaseStore();

        // availability
        Long availableQuantity = commerceStockService.getStockLevelForProductAndBaseStore(source.getProduct(), baseStore);
        Collection<StockLevelModel> stockLevels = stockService.getStockLevels(source.getProduct(), warehouseSelectionStrategy.getWarehousesForBaseStore(baseStore));
        // availability at regular price
        int availableQuantityRegularPrice = stockLevels.stream().mapToInt(sl -> sl.getAvailableRegularPrice()).sum();

        target.setQualifyForEarlyBirdPromotion(availableQuantity.intValue() >= availableQuantityRegularPrice + source.getQuantity());
        target.setAvailability(availableQuantity.intValue());
        target.setAvailabilityRegularPrice(availableQuantityRegularPrice);
    }

    protected BaseStoreService getBaseStoreService()
    {
        return baseStoreService;
    }

    @Required
    public void setBaseStoreService(final BaseStoreService baseStoreService)
    {
        this.baseStoreService = baseStoreService;
    }

    protected CommerceStockService getCommerceStockService()
   {
        return commerceStockService;
    }

    @Required
    public void setCommerceStockService(final CommerceStockService commerceStockService)
    {
        this.commerceStockService = commerceStockService;
    }

    protected StockService getStockService()
    {
        return stockService;
    }

    @Required
    public void setStockService(final StockService stockService)
    {
        this.stockService = stockService;
    }

    protected WarehouseSelectionStrategy getWarehouseSelectionStrategy()
    {
        return warehouseSelectionStrategy;
    }

    @Required
    public void setWarehouseSelectionStrategy(final WarehouseSelectionStrategy warehouseSelectionStrategy)
    {
        this.warehouseSelectionStrategy = warehouseSelectionStrategy;
    }

}
