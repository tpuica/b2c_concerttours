/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.core.search.solrfacetsearch.provider.impl;

import de.stripedgiraffe.concerttours.core.model.BandModel;
import de.stripedgiraffe.concerttours.core.model.ConcertModel;
import de.stripedgiraffe.concerttours.core.model.ConcertTicketModel;
import de.stripedgiraffe.concerttours.core.model.TourModel;


public class BandValueProvider extends BasePropertyFieldValueProvider<BandModel>
{

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
