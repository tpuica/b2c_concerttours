package de.stripedgiraffe.concerttours.core.facades;

import de.stripedgiraffe.concerttours.core.data.TourData;

// TODO move to facades project
public interface TourFacade
{
	TourData getTourDetails(final String tourId);
}