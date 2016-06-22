/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

import za.co.sindi.jsf.bootstrap3.renderer.Bootstrap3PanelRenderer;

/**
 * @author Bienfait Sindi
 * @since 19 July 2015
 *
 */
@FacesComponent(Bootstrap3Panel.COMPONENT_TYPE)
public class Bootstrap3Panel extends UIPanel {

	public static final String COMPONENT_TYPE = "za.co.sindi.faces.bootstrap3.Bootstrap3Panel";
	
	private enum PropertyKeys {
        bodyClass,
		footerClass,
        headerClass,
        style,
        styleClass,
	}
	
	/**
	 * 
	 */
	public Bootstrap3Panel() {
		super();
		// TODO Auto-generated constructor stub
		setRendererType(Bootstrap3PanelRenderer.RENDERER_TYPE);
	}

	/**
	 * @return the bodyClass
	 */
	public String getBodyClass() {
		return (String) getStateHelper().eval(PropertyKeys.bodyClass);
	}
	
	/**
	 * @param bodyClass the bodyClass to set
	 */
	public void setBodyClass(String bodyClass) {
		getStateHelper().put(PropertyKeys.bodyClass, bodyClass);
	}
	
	/**
	 * @return the footerClass
	 */
	public String getFooterClass() {
		return (String) getStateHelper().eval(PropertyKeys.footerClass);
	}
	
	/**
	 * @param footerClass the footerClass to set
	 */
	public void setFooterClass(String footerClass) {
		getStateHelper().put(PropertyKeys.footerClass, footerClass);
	}
	
	/**
	 * @return the headerClass
	 */
	public String getHeaderClass() {
		return (String) getStateHelper().eval(PropertyKeys.headerClass);
	}
	
	/**
	 * @param headerClass the headerClass to set
	 */
	public void setHeaderClass(String headerClass) {
		getStateHelper().put(PropertyKeys.headerClass, headerClass);
	}
	
	/**
	 * @return the style
	 */
	public String getStyle() {
		return (String) getStateHelper().eval(PropertyKeys.style);
	}
	
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		getStateHelper().put(PropertyKeys.style, style);
	}
	
	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		return (String) getStateHelper().eval(PropertyKeys.styleClass);
	}
	
	/**
	 * @param styleClass the styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		getStateHelper().put(PropertyKeys.styleClass, styleClass);
	}
}
