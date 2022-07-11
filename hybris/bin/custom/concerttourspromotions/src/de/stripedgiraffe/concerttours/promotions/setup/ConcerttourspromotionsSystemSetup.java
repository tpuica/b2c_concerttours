/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.promotions.setup;

import static de.stripedgiraffe.concerttours.promotions.constants.ConcerttourspromotionsConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import de.stripedgiraffe.concerttours.promotions.constants.ConcerttourspromotionsConstants;
import de.stripedgiraffe.concerttours.promotions.service.ConcerttourspromotionsService;


@SystemSetup(extension = ConcerttourspromotionsConstants.EXTENSIONNAME)
public class ConcerttourspromotionsSystemSetup
{
	private final ConcerttourspromotionsService concerttourspromotionsService;

	public ConcerttourspromotionsSystemSetup(final ConcerttourspromotionsService concerttourspromotionsService)
	{
		this.concerttourspromotionsService = concerttourspromotionsService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		concerttourspromotionsService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return ConcerttourspromotionsSystemSetup.class.getResourceAsStream("/concerttourspromotions/sap-hybris-platform.png");
	}
}
