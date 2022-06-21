/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.core.service;

public interface ConcerttoursService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
