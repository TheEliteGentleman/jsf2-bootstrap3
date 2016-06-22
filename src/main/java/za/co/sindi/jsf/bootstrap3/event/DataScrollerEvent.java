/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * @author Bienfait Sindi
 * @since 24 November 2015
 *
 */
public class DataScrollerEvent extends ActionEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5074685757773051700L;
	
	private int oldPageIndex;
	private int newPageIndex;
	
	/**
	 * @param component
	 * @param oldPageIndex
	 * @param newPageIndex
	 */
	public DataScrollerEvent(UIComponent component, int oldPageIndex, int newPageIndex) {
		super(component);
		this.oldPageIndex = oldPageIndex;
		this.newPageIndex = newPageIndex;
	}

	/**
	 * @return the oldPageIndex
	 */
	public int getOldPageIndex() {
		return oldPageIndex;
	}

	/**
	 * @return the newPageIndex
	 */
	public int getNewPageIndex() {
		return newPageIndex;
	}
}
