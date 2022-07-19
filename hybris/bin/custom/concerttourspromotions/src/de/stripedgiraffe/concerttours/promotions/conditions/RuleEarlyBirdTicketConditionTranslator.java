package de.stripedgiraffe.concerttours.promotions.conditions;


import com.google.common.collect.Lists;
import de.hybris.platform.ruledefinitions.conditions.builders.RuleIrAttributeConditionBuilder;
import de.hybris.platform.ruledefinitions.conditions.builders.RuleIrAttributeRelConditionBuilder;
import de.hybris.platform.ruledefinitions.conditions.builders.RuleIrGroupConditionBuilder;
import de.hybris.platform.ruleengineservices.compiler.*;
import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionData;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionDefinitionData;

public class RuleEarlyBirdTicketConditionTranslator implements RuleConditionTranslator {
    public RuleEarlyBirdTicketConditionTranslator() {
    }

    public RuleIrCondition translate(RuleCompilerContext context, RuleConditionData condition, RuleConditionDefinitionData conditionDefinition) {
        String orderEntryRaoVariable = context.generateVariable(OrderEntryRAO.class);
        String cartRaoVariable = context.generateVariable(CartRAO.class);

        final RuleIrAttributeRelCondition irCartOrderEntryRel = RuleIrAttributeRelConditionBuilder
                .newAttributeRelationConditionFor(cartRaoVariable)
                .withAttribute("entries")
                .withOperator(RuleIrAttributeOperator.CONTAINS)
                .withTargetVariable(orderEntryRaoVariable)
                .build();

        final RuleIrAttributeCondition irOrderEntryEarlyBirdCondition =  RuleIrAttributeConditionBuilder
                .newAttributeConditionFor(orderEntryRaoVariable)
                .withAttribute("qualifyForEarlyBirdPromotion")
                .withOperator(RuleIrAttributeOperator.EQUAL)
                .withValue(true)
                .build();

        return RuleIrGroupConditionBuilder
                .newGroupConditionOf(RuleIrGroupOperator.AND)
                .withChildren(Lists.newArrayList(irCartOrderEntryRel, irOrderEntryEarlyBirdCondition))
                .build();

    }

}
