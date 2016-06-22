/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIMessages;

import za.co.sindi.jsf.bootstrap3.renderer.Bootstrap3MessagesRenderer;

/**
 * @author Bienfait Sindi
 * @since 07 April 2014
 *
 */
@FacesComponent(Bootstrap3Messages.COMPONENT_TYPE)
public class Bootstrap3Messages extends UIMessages {

	public static final String COMPONENT_TYPE = "za.co.sindi.faces.bootstrap3.Bootstrap3Messages";
	
	/**
	 * 
	 */
	public Bootstrap3Messages() {
		// TODO Auto-generated constructor stub
		setRendererType(Bootstrap3MessagesRenderer.RENDERER_TYPE);
	}
	
	private enum PropertyKeys {
		dismissible,
		escape,
		iconLibrary,
		showIcon,
	}
	
	/**
	 * @return the showIcon
	 */
	public boolean isShowIcon() {
		return (Boolean) getStateHelper().eval(PropertyKeys.showIcon, false);
	}

	/**
	 * @param showIcon the showIcon to set
	 */
	public void setShowIcon(boolean showIcon) {
		getStateHelper().put(PropertyKeys.showIcon, showIcon);
	}
	
	/**
	 * @return the dismissible
	 */
	public boolean isDismissible() {
		return (Boolean) getStateHelper().eval(PropertyKeys.dismissible, false);
	}

	/**
	 * @param dismissable the dismissible to set
	 */
	public void setDismissible(boolean dismissible) {
		getStateHelper().put(PropertyKeys.dismissible, dismissible);
	}

	/**
	 * Returns whether the message detail and summary should be HTML-escaped. Default is <code>true</code>.
	 * @return Whether the message detail and summary should be HTML-escaped.
	 */
	public boolean isEscape() {
		return (Boolean) getStateHelper().eval(PropertyKeys.escape, true);
	}

	/**
	 * Sets whether the message detail and summary should be HTML-escaped.
	 * @param escape Whether the message detail and summary should be HTML-escaped.
	 */
	public void setEscape(boolean escape) {
		getStateHelper().put(PropertyKeys.escape, escape);
	}

	/**
	 * @return the iconLibrary
	 */
	public String getIconLibrary() {
		return (String) getStateHelper().eval(PropertyKeys.iconLibrary, "bs");
	}

	/**
	 * @param iconLibrary the iconLibrary to set
	 */
	public void setIconLibrary(String iconLibrary) {
		getStateHelper().put(PropertyKeys.iconLibrary, iconLibrary);
	}
}
