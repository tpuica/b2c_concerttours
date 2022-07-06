package de.stripedgiraffe.concerttours.facades.converters;

import de.hybris.platform.converters.impl.AbstractConverter;
import de.stripedgiraffe.concerttours.core.model.BandModel;
import de.stripedgiraffe.concerttours.core.model.TourModel;
import de.stripedgiraffe.concerttours.facades.data.BandData;
import de.stripedgiraffe.concerttours.facades.data.TourData;

import java.util.stream.Collectors;


public class BandConverter extends AbstractConverter<BandModel, BandData> {

    @Override
    public void populate(BandModel bandModel, BandData bandData) {
        bandData.setCode(bandModel.getCode());
        bandData.setName(bandModel.getName());
        bandData.setAlbumsSold(bandModel.getAlbumSales());
        bandData.setGenres(bandModel.getTypes().stream().map(t -> t.getCode()).collect(Collectors.toList()));
    }
}
