/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 8 Jul 2022, 09:40:22                        ---
 * ----------------------------------------------------------------
 */
package de.stripedgiraffe.concerttours.promotions.jalo;

import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.extension.ExtensionManager;
import java.util.HashMap;
import java.util.Map;
import de.stripedgiraffe.concerttours.promotions.constants.ConcerttourspromotionsConstants;

/**
 * Generated class for type <code>ConcerttourspromotionsManager</code>.
 */
@SuppressWarnings({"unused","cast"})
@SLDSafe
public class ConcerttourspromotionsManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	public static final ConcerttourspromotionsManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (ConcerttourspromotionsManager) em.getExtension(ConcerttourspromotionsConstants.EXTENSIONNAME);
	}
	
	@Override
	public String getName()
	{
		return ConcerttourspromotionsConstants.EXTENSIONNAME;
	}
	
}
