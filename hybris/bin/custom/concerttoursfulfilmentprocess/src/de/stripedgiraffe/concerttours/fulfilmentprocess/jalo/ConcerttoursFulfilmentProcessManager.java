/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import de.stripedgiraffe.concerttours.fulfilmentprocess.constants.ConcerttoursFulfilmentProcessConstants;

public class ConcerttoursFulfilmentProcessManager extends GeneratedConcerttoursFulfilmentProcessManager
{
	public static final ConcerttoursFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (ConcerttoursFulfilmentProcessManager) em.getExtension(ConcerttoursFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
