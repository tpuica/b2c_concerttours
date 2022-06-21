package de.stripedgiraffe.concerttours.core.constraints;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import de.stripedgiraffe.concerttours.core.model.BandModel;


public class UniqueBandNameValidator implements ConstraintValidator<UniqueBandName, BandModel>
{

	static protected Logger LOG = Logger.getLogger(UniqueBandNameValidator.class);

	// TODO move to DAO
	private static final String FIND_UNIQUE_CASE_INSENSITIVE_TRIM_BAND_NAME_QUERY = "select {PK} from {Band} where trim(lower({name}))=?name";

	private FlexibleSearchService flexibleSearchService;

	@Override
	public void initialize(final UniqueBandName constraintAnnotation)
	{
		LOG.info("INIT");
		this.flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext().getBean("flexibleSearchService");
		LOG.info("flexibleSearchService: " + flexibleSearchService);
	}

	@Override
	public boolean isValid(final BandModel value, final ConstraintValidatorContext context)
	{
		boolean valid = (value != null);

		if (valid)
		{
			final String bandName = StringUtils.defaultString(value.getName()).toLowerCase().trim();

			// TODO move to service
			final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(FIND_UNIQUE_CASE_INSENSITIVE_TRIM_BAND_NAME_QUERY);
			fQuery.addQueryParameter("name", bandName);

			final SearchResult<BandModel> searchRslt = flexibleSearchService.search(fQuery);

			if (searchRslt.getTotalCount() > 1
					|| (searchRslt.getTotalCount() == 1 && !searchRslt.getResult().get(0).getPk().equals(value.getPk())))
			{
				valid = false;
			}
		}

		LOG.info("isValid: " + valid);

		return valid;
	}

}