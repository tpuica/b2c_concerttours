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
		if (tourId == null)
		{
			throw new IllegalArgumentException("Tour id cannot be null");
		}
		final ProductModel product = productService.getProductForCode(tourId);
		if (product == null)
		{
			return null;
		}
		// Create a list of ConcertSummaryData from the matches
		final List<ConcertSummaryData> concerts = new ArrayList<>();
		if (product.getVariants() != null)
		{
			for (final VariantProductModel variant : product.getVariants())
			{
				if (variant instanceof ConcertModel)
				{
					final ConcertModel concert = (ConcertModel) variant;
					final ConcertSummaryData summary = new ConcertSummaryData();
					summary.setId(concert.getCode());
					summary.setDate(concert.getDate());
					summary.setVenue(concert.getVenue());
					summary.setType(concert.getConcertType() == ConcertType.OPENAIR ? "Outdoors" : "Indoors");
					summary.setCountDown(concert.getDaysUntil());
					concerts.add(summary);
				}
			}
		}
		// Now we can create the TourData transfer object
		final TourData tourData = new TourData();
		tourData.setId(product.getCode());
		tourData.setTourName(product.getName());
		tourData.setDescription(product.getDescription());
		tourData.setConcerts(concerts);
		return tourData;
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