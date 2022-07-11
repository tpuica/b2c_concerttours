/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.stripedgiraffe.concerttours.promotions.controller;

import static de.stripedgiraffe.concerttours.promotions.constants.ConcerttourspromotionsConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.stripedgiraffe.concerttours.promotions.service.ConcerttourspromotionsService;


@Controller
public class ConcerttourspromotionsHelloController
{
	@Autowired
	private ConcerttourspromotionsService concerttourspromotionsService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", concerttourspromotionsService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
