/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.core.search.solrfacetsearch.provider.impl;

import de.stripedgiraffe.concerttours.core.model.ConcertModel;
import de.stripedgiraffe.concerttours.core.model.ConcertTicketModel;
import de.stripedgiraffe.concerttours.core.model.TourModel;


public class TourNameValueProvider extends BasePropertyFieldValueProvider<TourModel>
{

	@Override
	TourModel getModel(Object model) {
		Object finalModel = model;
		if (model instanceof ConcertTicketModel)
		{
			finalModel = ((ConcertTicketModel) finalModel).getBaseProduct();
		}

		if (finalModel instanceof ConcertModel)
		{
			return ((ConcertModel) finalModel).getTour();
		}
		else
		{
			return null;
		}
	}

	@Override
	String getFieldValue(TourModel model) {
		return (model != null) ? model.getName() : null;
	}

}
