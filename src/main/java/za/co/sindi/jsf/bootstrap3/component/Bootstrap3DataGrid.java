/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIData;

import za.co.sindi.jsf.bootstrap3.renderer.Bootstrap3DataGridRenderer;

/**
 * @author Bienfait Sindi
 * @since 16 July 2015
 *
 */
@FacesComponent(Bootstrap3DataGrid.COMPONENT_TYPE)
public class Bootstrap3DataGrid extends UIData {

	public static final String COMPONENT_TYPE = "za.co.sindi.faces.bootstrap3.Bootstrap3DataGrid";
	
	private enum PropertyKeys {
		columns,
		columnClasses,
		layout,
//		rowClass,
		style,
		styleClass
	}
	
	/**
	 * 
	 */
	public Bootstrap3DataGrid() {
		super();
		// TODO Auto-generated constructor stub
		setRendererType(Bootstrap3DataGridRenderer.RENDERER_TYPE);
	}

	/**
	 * @return the columns
	 */
	public int getColumns() {
		return (Integer) getStateHelper().eval(PropertyKeys.columns, 4);
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(int columns) {
		getStateHelper().put(PropertyKeys.columns, columns);
	}

	/**
	 * @return the columnClasses
	 */
	public String getColumnClasses() {
		return (String) getStateHelper().eval(PropertyKeys.columnClasses);
	}

	/**
	 * @param columnClasses the columnClasses to set
	 */
	public void setColumnClasses(String columnClasses) {
		getStateHelper().put(PropertyKeys.columnClasses, columnClasses);
	}
	
	/**
	 * @return the layout
	 */
	public String getLayout() {
		return (String) getStateHelper().eval(PropertyKeys.layout, "table");
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(String layout) {
		getStateHelper().put(PropertyKeys.layout, layout);
	}

//	/**
//	 * @return the rowClass
//	 */
//	public String getRowClass() {
//		return (String) getStateHelper().eval(PropertyKeys.rowClass);
//	}
//
//	/**
//	 * @param rowClass the rowClass to set
//	 */
//	public void setRowClass(String rowClass) {
//		getStateHelper().put(PropertyKeys.rowClass, rowClass);
//	}

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
