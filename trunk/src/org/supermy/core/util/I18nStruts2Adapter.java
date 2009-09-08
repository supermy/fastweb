package org.supermy.core.util;

import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.jsp.TagUtils;
import org.displaytag.Messages;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.ValueStack;


/**
 * Resolve i18n resources in Struts 2
 * 
 * @author <a:href="mailto:snowwolf@wabunoh-tech.com">mailto:snowwolf@wabunoh-tech.com</>
 * @version $Revision: 1.1 $
 * @since Jan 4, 2008 2:37:29 PM
 */
public class I18nStruts2Adapter implements LocaleResolver, I18nResourceProvider {

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(I18nStruts2Adapter.class);

    /**
     * @see LocaleResolver#resolveLocale(HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request) {

        Locale result = null;
        ValueStack stack = ActionContext.getContext().getValueStack();

        Iterator iterator = stack.getRoot().iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();

            if (o instanceof LocaleProvider) {
                LocaleProvider lp = (LocaleProvider) o;
                result = lp.getLocale();

                break;
            }
        }

        if (result == null) {
            log.debug("Missing LocalProvider actions, init locale to default");
            result = Locale.getDefault();
        }

        return result;
    }

    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, javax.servlet.jsp.PageContext)
     */
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext) {

        // if resourceKey isn't defined either, use defaultValue
        String key = (resourceKey != null) ? resourceKey : defaultValue;

        String message = null;
        ValueStack stack = TagUtils.getStack(pageContext);
        Iterator iterator = stack.getRoot().iterator();

        while (iterator.hasNext()) {
            Object o = iterator.next();

            if (o instanceof TextProvider) {
                TextProvider tp = (TextProvider) o;
                message = tp.getText(key, null, (String) null);

                break;
            }
        }

        // if user explicitely added a titleKey we guess this is an error
        if (message == null && resourceKey != null) {
            log.debug(Messages.getString("Localization.missingkey", resourceKey)); //$NON-NLS-1$
            message = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }

        return message;
    }

}