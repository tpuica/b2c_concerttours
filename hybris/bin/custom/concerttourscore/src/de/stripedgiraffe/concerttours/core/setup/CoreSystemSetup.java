/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.core.setup;

import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.commerceservices.dataimport.impl.CoreDataImportService;
import de.hybris.platform.commerceservices.dataimport.impl.SampleDataImportService;
import de.hybris.platform.commerceservices.setup.data.ImportData;
import de.hybris.platform.commerceservices.setup.events.CoreDataImportedEvent;
import de.hybris.platform.commerceservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import de.stripedgiraffe.concerttours.core.constants.ConcerttoursCoreConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


/**
 * This class provides hooks into the system's initialization and update processes.
 */
@SystemSetup(extension = ConcerttoursCoreConstants.EXTENSIONNAME)
public class CoreSystemSetup extends AbstractSystemSetup
{
	public static final String IMPORT_ACCESS_RIGHTS = "accessRights";

	//public static final String ELECTRONICS = "electronics";
	public static final String APPAREL = "apparel";
	public static final String APPAREL_UK = "apparel-uk";
	public static final String APPAREL_DE = "apparel-de";

	private static final String IMPORT_CORE_DATA = "importCoreData";
	private static final String IMPORT_SAMPLE_DATA = "importSampleData";
	private static final String ACTIVATE_SOLR_CRON_JOBS = "activateSolrCronJobs";

	private CoreDataImportService coreDataImportService;
	private SampleDataImportService sampleDataImportService;

	/**
	 * This method will be called by system creator during initialization and system update. Be sure that this method can
	 * be called repeatedly.
	 *
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.ESSENTIAL, process = Process.ALL)
	public void createEssentialData(final SystemSetupContext context)
	{
		importImpexFile(context, "/concerttourscore/import/common/essential-data.impex");
		importImpexFile(context, "/concerttourscore/import/common/countries.impex");
		importImpexFile(context, "/concerttourscore/import/common/delivery-modes.impex");

		importImpexFile(context, "/concerttourscore/import/common/themes.impex");
		importImpexFile(context, "/concerttourscore/import/common/user-groups.impex");
		importImpexFile(context, "/concerttourscore/import/common/cronjobs.impex");
	}

	/**
	 * Generates the Dropdown and Multi-select boxes for the project data import
	 */
	@Override
	@SystemSetupParameterMethod
	public List<SystemSetupParameter> getInitializationOptions()
	{
		final List<SystemSetupParameter> params = new ArrayList<>();

		params.add(createBooleanSystemSetupParameter(IMPORT_ACCESS_RIGHTS, "Import Users & Groups", true));
		// params.add(createBooleanSystemSetupParameter(IMPORT_CORE_DATA, "Import Core Data", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_SAMPLE_DATA, "Import Sample Data", true));
		//params.add(createBooleanSystemSetupParameter(ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", true));

		return params;
	}

	/**
	 * This method will be called during the system initialization.
	 *
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.PROJECT, process = Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		final boolean importAccessRights = getBooleanSystemSetupParameter(context, IMPORT_ACCESS_RIGHTS);

		final List<String> extensionNames = getExtensionNames();

		processCockpit(context, importAccessRights, extensionNames, "cmsbackoffice",
				"/concerttourscore/import/cockpits/cmscockpit/cmscockpit-users.impex",
				"/concerttourscore/import/cockpits/cmscockpit/cmscockpit-access-rights.impex");

		processCockpit(context, importAccessRights, extensionNames, "productcockpit",
				"/concerttourscore/import/cockpits/productcockpit/productcockpit-users.impex",
				"/concerttourscore/import/cockpits/productcockpit/productcockpit-access-rights.impex",
				"/concerttourscore/import/cockpits/productcockpit/productcockpit-constraints.impex");

		processCockpit(context, importAccessRights, extensionNames, "customersupportbackoffice",
				"/concerttourscore/import/cockpits/cscockpit/cscockpit-users.impex",
				"/concerttourscore/import/cockpits/cscockpit/cscockpit-access-rights.impex");

		final List<ImportData> importData = new ArrayList<ImportData>();

		//final ImportData electronicsImportData = new ImportData();
		//electronicsImportData.setProductCatalogName(ELECTRONICS);
		//electronicsImportData.setContentCatalogNames(Arrays.asList(ELECTRONICS));
		//electronicsImportData.setStoreNames(Arrays.asList(ELECTRONICS));
		//importData.add(electronicsImportData);

		final ImportData apparelImportData = new ImportData();
		apparelImportData.setProductCatalogName(APPAREL);
		apparelImportData.setContentCatalogNames(Arrays.asList(APPAREL_UK, APPAREL_DE));
		apparelImportData.setStoreNames(Arrays.asList(APPAREL_UK, APPAREL_DE));
		importData.add(apparelImportData);

		// getCoreDataImportService().execute(this, context, importData);
		// getEventService().publishEvent(new CoreDataImportedEvent(context, importData));

		getSampleDataImportService().execute(this, context, importData);
		getEventService().publishEvent(new SampleDataImportedEvent(context, importData));
	}

	protected void processCockpit(final SystemSetupContext context, final boolean importAccessRights,
			final List<String> extensionNames, final String cockpit, final String... files)
	{
		if (importAccessRights && extensionNames.contains(cockpit))
		{
			for (final String file : files)
			{
				importImpexFile(context, file);
			}
		}
	}

	public CoreDataImportService getCoreDataImportService()
	{
		return coreDataImportService;
	}

	@Required
	public void setCoreDataImportService(final CoreDataImportService coreDataImportService)
	{
		this.coreDataImportService = coreDataImportService;
	}

	public SampleDataImportService getSampleDataImportService()
	{
		return sampleDataImportService;
	}

	@Required
	public void setSampleDataImportService(final SampleDataImportService sampleDataImportService)
	{
		this.sampleDataImportService = sampleDataImportService;
	}

	protected List<String> getExtensionNames()
	{
		return Registry.getCurrentTenant().getTenantSpecificExtensionNames();
	}
}
