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
import de.stripedgiraffe.concerttours.core.model.*;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ConcertVenueValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider
{
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final ConcertModel concertModel = getConcertModel(model);
		if (concertModel == null)
		{
			return Collections.emptyList();
		}

		final String concertVenue = concertModel.getVenue();

		if (concertVenue != null && concertVenue.trim().length() > 0)
		{
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			fieldValues.addAll(createFieldValue(concertVenue, indexedProperty));
			return fieldValues;
		}
		else
		{
			return Collections.emptyList();
		}
	}

	protected List<FieldValue> createFieldValue(final String venue, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, venue));
		}
		return fieldValues;
	}

	protected ConcertModel getConcertModel(final Object model)
	{
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

	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

}
