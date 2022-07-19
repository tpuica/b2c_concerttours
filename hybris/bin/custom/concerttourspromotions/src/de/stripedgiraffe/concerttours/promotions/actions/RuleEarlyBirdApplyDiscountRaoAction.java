package de.stripedgiraffe.concerttours.promotions.actions;

import de.hybris.platform.ruleengineservices.calculation.RuleEngineCalculationService;
import de.hybris.platform.ruleengineservices.rao.*;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.AbstractRuleExecutableSupport;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Deprecated
// NOT USED, development purpose only
// extends RuleOrderEntryPercentageDiscountRAOAction instead
public class RuleEarlyBirdApplyDiscountRaoAction extends AbstractRuleExecutableSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEarlyBirdApplyDiscountRaoAction.class);

    private RuleEngineCalculationService ruleEngineCalculationService;

    public boolean performActionInternal(RuleActionContext context) {
        boolean isPerformed = false;
        Optional<BigDecimal> amount = this.extractAmountForCurrency(context, context.getParameter("value"));
        LOGGER.info("Discount parameter - value: {}, class: {}", amount, amount.getClass());

        if (amount.isPresent()) {
            Set<OrderEntryRAO> orderEntries = context.getValues(OrderEntryRAO.class);
            if (CollectionUtils.isNotEmpty(orderEntries)) {
                List<OrderEntryRAO> filteredOrderEntries = orderEntries.stream()
                        //.filter(o -> o.getAvailability() >= o.getAvailabilityRegularPrice() + this.getConsumptionSupport().getConsumableQuantity(o))
                        .filter(o -> o.isQualifyForEarlyBirdPromotion())
                        .collect(Collectors.toList());
                OrderEntryRAO orderEntryRAO;
                for(Iterator iOrderEntry = filteredOrderEntries.iterator(); iOrderEntry.hasNext(); isPerformed |= this.processOrderEntry(context, orderEntryRAO, (BigDecimal)amount.get())) {
                    orderEntryRAO = (OrderEntryRAO)iOrderEntry.next();
                }
            }
        }

        return isPerformed;
    }

    protected boolean processOrderEntry(RuleActionContext context, OrderEntryRAO orderEntryRao, BigDecimal value) {
        boolean isPerformed = false;
        int consumableQuantity = this.getConsumptionSupport().getConsumableQuantity(orderEntryRao);
        LOGGER.info("Processing order entry RAO {}", orderEntryRao);
        LOGGER.info("Availability (quantity): {}", orderEntryRao.getAvailability());
        LOGGER.info("Availability regular price (quantity): {}", orderEntryRao.getAvailabilityRegularPrice());
        LOGGER.info("Will continue: {} (consumableQuantity: {})", consumableQuantity > 0, consumableQuantity);
        if (consumableQuantity > 0) {
                LOGGER.info("Total price before applying discount: {}", orderEntryRao.getTotalPrice());
                DiscountRAO discount = this.getRuleEngineCalculationService().addOrderEntryLevelDiscount(orderEntryRao, false, value);
                LOGGER.info("Total price after applying discount: {}", orderEntryRao.getTotalPrice());
                this.setRAOMetaData(context, new AbstractRuleActionRAO[]{discount});
                this.getConsumptionSupport().consumeOrderEntry(orderEntryRao, consumableQuantity, discount);
                RuleEngineResultRAO result = (RuleEngineResultRAO) context.getValue(RuleEngineResultRAO.class);
                result.getActions().add(discount);
                context.scheduleForUpdate(new Object[]{orderEntryRao, orderEntryRao.getOrder(), result});
                context.insertFacts(new Object[]{discount});
                context.insertFacts(discount.getConsumedEntries());
                isPerformed = true;
        }

        return isPerformed;
    }

}