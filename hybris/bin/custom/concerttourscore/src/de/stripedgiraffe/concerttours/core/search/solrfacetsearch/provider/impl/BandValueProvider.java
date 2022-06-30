/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.stripedgiraffe.concerttours.core.model.BandModel;
import de.stripedgiraffe.concerttours.core.model.ConcertModel;
import de.stripedgiraffe.concerttours.core.model.ConcertTicketModel;
import de.stripedgiraffe.concerttours.core.model.TourModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class BandValueProvider extends BasePropertyFieldValueProvider<BandModel>
{
	private FieldNameProvider fieldNameProvider;

	@Override
	BandModel getModel(Object model) {
		Object finalModel = model;
		if (model instanceof ConcertTicketModel)
		{
			finalModel = ((ConcertTicketModel) finalModel).getBaseProduct();
		}

		if (finalModel instanceof ConcertModel)
		{
			finalModel = ((ConcertModel) finalModel).getTour();
		}

		if (finalModel instanceof TourModel)
		{
			return ((TourModel) finalModel).getBand();
		}
		else
		{
			return null;
		}
	}

	@Override
	String getFieldValue(BandModel model) {
		return (model != null) ? model.getName() : null;
	}

}
