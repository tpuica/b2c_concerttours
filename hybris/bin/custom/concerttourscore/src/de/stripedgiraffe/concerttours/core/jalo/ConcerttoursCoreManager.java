/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import de.stripedgiraffe.concerttours.core.constants.ConcerttoursCoreConstants;
import de.stripedgiraffe.concerttours.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class ConcerttoursCoreManager extends GeneratedConcerttoursCoreManager
{
	public static final ConcerttoursCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (ConcerttoursCoreManager) em.getExtension(ConcerttoursCoreConstants.EXTENSIONNAME);
	}
}
