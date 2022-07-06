/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.core.search.solrfacetsearch.provider.impl;

import de.stripedgiraffe.concerttours.core.model.ConcertModel;
import de.stripedgiraffe.concerttours.core.model.ConcertTicketModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ConcertDateValueProvider extends BasePropertyFieldValueProvider<ConcertModel>
{

	private final static DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	ConcertModel getModel(Object model) {
		Object finalModel = model;
		if (model instanceof ConcertTicketModel)
		{
			finalModel = ((ConcertTicketModel) finalModel).getBaseProduct();
		}

		if (finalModel instanceof ConcertModel)
		{
			return (ConcertModel) finalModel;
		}
		else
		{
			return null;
		}
	}

	@Override
	String getFieldValue(ConcertModel model) {
		return (model != null) ? DATE_FORMATTER.format(model.getDate()) : null;
	}

}
