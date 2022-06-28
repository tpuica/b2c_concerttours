package de.stripedgiraffe.concerttours.core.facades.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import de.stripedgiraffe.concerttours.core.attributehandlers.ConcertDaysUntilAttributeHandler;
import de.stripedgiraffe.concerttours.core.data.ConcertSummaryData;
import de.stripedgiraffe.concerttours.core.data.TourData;
import de.stripedgiraffe.concerttours.core.enums.ConcertType;
import de.stripedgiraffe.concerttours.core.facades.TourFacade;
import de.stripedgiraffe.concerttours.core.model.ConcertModel;

// TODO move to facades project
public class DefaultTourFacade implements TourFacade
{
	private ProductService productService;

	private ConcertDaysUntilAttributeHandler concertDaysUntilAttributeHandler;

	@Override
	public TourData getTourDetails(final String tourId)
	{
		return null;
	}

	@Required
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
    }

	 /**
	  * @param concertDaysUntilAttributeHandler
	  *           the concertDaysUntilAttributeHandler to set
	  */
	 public void setConcertDaysUntilAttributeHandler(final ConcertDaysUntilAttributeHandler concertDaysUntilAttributeHandler)
	 {
		 this.concertDaysUntilAttributeHandler = concertDaysUntilAttributeHandler;
	 }
}