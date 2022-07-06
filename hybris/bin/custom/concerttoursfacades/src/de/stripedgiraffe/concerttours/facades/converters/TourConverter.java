package de.stripedgiraffe.concerttours.facades.converters;

import de.hybris.platform.converters.impl.AbstractConverter;
import de.stripedgiraffe.concerttours.core.model.BandModel;
import de.stripedgiraffe.concerttours.core.model.TourModel;
import de.stripedgiraffe.concerttours.facades.data.TourData;
import org.springframework.beans.factory.annotation.Required;


public class TourConverter extends AbstractConverter<TourModel, TourData> {

    private BandConverter bandConverter;

    @Override
    public void populate(TourModel tourModel, TourData tourData) {
        tourData.setCode(tourModel.getCode());
        tourData.setName(tourModel.getName());
        tourData.setHashtag(tourModel.getHashtag());

        final BandModel bandModel = tourModel.getBand();
        if (bandModel != null) {
            tourData.setBand(bandConverter.convert(bandModel));
        }
    }

    @Required
    public void setBandConverter(BandConverter bandConverter) {
        this.bandConverter = bandConverter;
    }

}
