/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIMessage;

import za.co.sindi.jsf.bootstrap3.renderer.Bootstrap3MessageRenderer;

/**
 * @author Bienfait Sindi
 * @since 10 April 2014
 *
 */
@FacesComponent(Bootstrap3Message.COMPONENT_TYPE)
public class Bootstrap3Message extends UIMessage {

	public static final String COMPONENT_TYPE = "za.co.sindi.faces.bootstrap3.Bootstrap3Message";
	
	/**
	 * 
	 */
	public Bootstrap3Message() {
		// TODO Auto-generated constructor stub
		setRendererType(Bootstrap3MessageRenderer.RENDERER_TYPE);
	}
	
	private enum PropertyKeys {
		showIcon,
		errorClass,
        fatalClass,
        infoClass,
        warnClass,
		escape,
		iconLibrary
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
	 * @return the errorClass
	 */
	public String getErrorClass() {
		return (String) getStateHelper().eval(PropertyKeys.errorClass);
	}

	/**
	 * @param errorClass the errorClass to set
	 */
	public void setErrorClass(String errorClass) {
		getStateHelper().put(PropertyKeys.errorClass, errorClass);
	}

	/**
	 * @return the fatalClass
	 */
	public String getFatalClass() {
		return (String) getStateHelper().eval(PropertyKeys.fatalClass);
	}

	/**
	 * @param fatalClass the fatalClass to set
	 */
	public void setFatalClass(String fatalClass) {
		getStateHelper().put(PropertyKeys.fatalClass, fatalClass);
	}

	/**
	 * @return the infoClass
	 */
	public String getInfoClass() {
		return (String) getStateHelper().eval(PropertyKeys.infoClass);
	}

	/**
	 * @param infoClass the infoClass to set
	 */
	public void setInfoClass(String infoClass) {
		getStateHelper().put(PropertyKeys.infoClass, infoClass);
	}

	/**
	 * @return the warnClass
	 */
	public String getWarnClass() {
		return (String) getStateHelper().eval(PropertyKeys.warnClass);
	}

	/**
	 * @param warnClass the warnClass to set
	 */
	public void setWarnClass(String warnClass) {
		getStateHelper().put(PropertyKeys.warnClass, warnClass);
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
