/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.promotions.service;

public interface ConcerttourspromotionsService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
