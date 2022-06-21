package de.stripedgiraffe.concerttours.core.facades.impl;

import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Required;

import de.stripedgiraffe.concerttours.core.data.BandData;
import de.stripedgiraffe.concerttours.core.data.TourSummaryData;
import de.stripedgiraffe.concerttours.core.enums.MusicType;
import de.stripedgiraffe.concerttours.core.facades.BandFacade;
import de.stripedgiraffe.concerttours.core.model.BandModel;
import de.stripedgiraffe.concerttours.core.service.BandService;

// TODO move to facades project
public class DefaultBandFacade implements BandFacade
{

	public static final String BAND_LIST_FORMAT = "band.list.format.name";
	private static final String BAND_DETAIL_FORMAT = "band.detail.format.name";

	private BandService bandService;
	private MediaService mediaService;
	private ConfigurationService configService;

	@Override
	public List<BandData> getBands()
	{
		//final MediaFormatModel format = mediaService.getFormat(BAND_LIST_FORMAT);
		final String mediaFormatName = configService.getConfiguration().getString(BAND_LIST_FORMAT);
		System.out.println("mediaFormatName:" + mediaFormatName);
		final MediaFormatModel format = mediaService.getFormat(mediaFormatName);

		final List<BandModel> bandModels = bandService.getBands();
		final List<BandData> bandFacadeData = new ArrayList<>();

		for (final BandModel sm : bandModels)
		{
			final BandData sfd = new BandData();
			sfd.setId(sm.getCode());
			sfd.setName(sm.getName());
			sfd.setDescription(sm.getHistory());
			sfd.setAlbumsSold(sm.getAlbumSales());
			final String bandUrl = getImageURL(sm, format);
			//System.out.println("getbands: band " + sm.getName() + ", URL - " + bandUrl);
			sfd.setImageURL(bandUrl);
			bandFacadeData.add(sfd);
		}
		return bandFacadeData;
	}

	@Override
	public BandData getBand(final String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("Band name cannot be null");
		}
		final BandModel band = bandService.getBandForCode(name);
		if (band == null)
		{
			return null;
		}

		// Create a list of genres
		final List<String> genres = new ArrayList<>();
		if (band.getTypes() != null)
		{
			for (final MusicType musicType : band.getTypes())
			{
				genres.add(musicType.getCode());
			}
		}
		// Create a list of TourSummaryData from the matches
		final List<TourSummaryData> tourHistory = new ArrayList<>();
		if (band.getTours() != null)
		{
			for (final ProductModel tour : band.getTours())
			{
				final TourSummaryData summary = new TourSummaryData();
				summary.setId(tour.getCode());
				summary.setTourName(tour.getName(Locale.ENGLISH));
				// making the big assumption that all variants are concerts and ignore product catalogs
				summary.setNumberOfConcerts(Integer.toString(tour.getVariants().size()));
				tourHistory.add(summary);
			}
		}
		// Now we can create the BandData transfer object
		final MediaFormatModel format = mediaService.getFormat(configService.getConfiguration().getString(BAND_DETAIL_FORMAT));
		final BandData bandData = new BandData();
		bandData.setId(band.getCode());
		bandData.setName(band.getName());
		bandData.setAlbumsSold(band.getAlbumSales());
		final String bandUrl = getImageURL(band, format);
		//System.out.println("getband: band " + band.getName() + ", URL - " + bandUrl);
		bandData.setImageURL(bandUrl);
		bandData.setDescription(band.getHistory());
		bandData.setGenres(genres);
		bandData.setTours(tourHistory);
		return bandData;
	}

	protected String getImageURL(final BandModel sm, final MediaFormatModel format)
	{
		final MediaContainerModel container = sm.getImage();
		if (container != null)
		{
			return mediaService.getMediaByFormat(container, format).getDownloadURL();
		}
		return null;
	}

	@Required
	public void setBandService(final BandService bandService)
	{
		this.bandService = bandService;
    }

	 @Required
	 public void setMediaService(final MediaService mediaService)
	 {
		 this.mediaService = mediaService;
	 }

	 @Required
	 public void setConfigurationService(final ConfigurationService configService)
	 {
		 this.configService = configService;
	 }

}