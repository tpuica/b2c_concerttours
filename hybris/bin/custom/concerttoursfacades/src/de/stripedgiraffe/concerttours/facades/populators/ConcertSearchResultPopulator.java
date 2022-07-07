package de.stripedgiraffe.concerttours.facades.populators;

import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantOptionsProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.stripedgiraffe.concerttours.facades.data.BandData;
import de.stripedgiraffe.concerttours.facades.data.TourData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class ConcertSearchResultPopulator extends SearchResultVariantOptionsProductPopulator {

    @Override
    public void populate(final SearchResultValueData source, final ProductData target) {
        super.populate(source, target);

        target.setVenue(this.getValue(source, "venue"));
        target.setConcertDate(this.getValue(source, "concertDate"));

        // build Tour+Band with names only
        BandData bandData = new BandData();
        bandData.setName(this. getValue(source, "bandName"));

        TourData tourData = new TourData();
        tourData.setName(this.getValue(source, "tourName"));
        tourData.setBand(bandData);

        target.setTour(tourData);

        List<Map<String, Object>> allSourceValues = new ArrayList();
        allSourceValues.add(source.getValues());
        source.getVariants().stream().forEach(variant -> allSourceValues.add(variant.getValues()));

        OptionalDouble minPriceOptional = allSourceValues.stream()
                .filter(i -> i != null && i.get("inStockFlag") != null && (Boolean) i.get("inStockFlag"))
                .mapToDouble(i -> (double) i.get("priceValue")).min();
        if (minPriceOptional.isPresent()) {
            final PriceData priceData = getPriceDataFactory().create(PriceDataType.BUY, BigDecimal.valueOf(minPriceOptional.getAsDouble()),
                    getCommonI18NService().getCurrentCurrency());
            target.setStartingPrice(priceData);
        }
    }

}
