package de.stripedgiraffe.concerttours.core.facades;

import java.util.List;

import de.stripedgiraffe.concerttours.core.data.BandData;

// TODO move to facades project
public interface BandFacade
{
	BandData getBand(String name);

	List<BandData> getBands();
}