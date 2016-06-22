/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.component;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.ActionSource2;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;

import za.co.sindi.common.utils.Strings;
import za.co.sindi.jsf.bootstrap3.renderer.Bootstrap3DataScrollerRenderer;

/**
 * @author Bienfait Sindi
 * @since 24 November 2015
 *
 */
public abstract class AbstractDataScroller extends UICommand implements ActionSource2 {
	
	protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	/**
     * <p>The standard component type for this component.</p>
     */
    public static final String COMPONENT_TYPE = "za.co.sindi.faces.bootstrap3.DataScroller";


//    /**
//     * <p>The standard component family for this component.</p>
//     */
//    public static final String COMPONENT_FAMILY = "za.co.sindi.faces.bootstrap3.DataScroller";
    
    public static final String FIRST_FACET_NAME = "first";
    public static final String LAST_FACET_NAME = "last";
    public static final String NEXT_FACET_NAME = "next";
    public static final String PREVIOUS_FACET_NAME = "previous";
    public static final String FAST_FORWARD_FACET_NAME = "fastforward";
    public static final String FAST_REWIND_FACET_NAME = "fastrewind";
    
    private enum PropertyKeys {
    	fastStep,
		forValue("for"),
//		pageCount,
		pageCountVar,
//		pageIndex,
		pageIndexVar,
		paginator,
		paginatorMaxPages,
		style,
		styleClass
        ;
		
		private final String text;

		/**
		 * 
		 */
		private PropertyKeys() {
			// TODO Auto-generated constructor stub
			this(null);
		}

		/**
		 * @param text
		 */
		private PropertyKeys(final String text) {
			this.text = text;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return (!Strings.isNullOrEmpty(text) ? text : super.toString());
		}
    }

	/**
	 * 
	 */
	public AbstractDataScroller() {
		super();
		// TODO Auto-generated constructor stub
    	setRendererType(Bootstrap3DataScrollerRenderer.RENDERER_TYPE);
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return COMPONENT_FAMILY;
	}

	/**
	 * @return the fastStep
	 */
	public int getFastStep() {
		return (int) getStateHelper().eval(PropertyKeys.fastStep, Integer.MIN_VALUE);
	}

	/**
	 * @param fastStep the fastStep to set
	 */
	public void setFastStep(int fastStep) {
		getStateHelper().put(PropertyKeys.fastStep, fastStep);
	}

	/**
	 * @return the for
	 */
	public String getFor() {
		return (String) getStateHelper().eval(PropertyKeys.forValue);
	}

	/**
	 * @param forValue the for to set
	 */
	public void setFor(String forValue) {
		getStateHelper().put(PropertyKeys.forValue, forValue);
	}
	
	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
//		return (int) getStateHelper().eval(PropertyKeys.pageIndex, Integer.MIN_VALUE);
		UIData uiData = getUIData();
		int rows = uiData.getRows();
		if (rows == 0) {
			throw new FacesException("No \"rows\" attribute was set for component " + uiData.getClientId(getFacesContext()) + " (UIData component, possibly a DataTable?).");
		}
		
		int pageIndex = 0;
		int first = uiData.getFirst();
		if (rows > 0) {
			pageIndex = first / rows + 1;
		} else {
			LOGGER.log(Level.WARNING, "This should NEVER happen but " + uiData.getClientId(getFacesContext()) + " has rows value set at " + rows);
		}
		
		if (first % rows > 0) {
			pageIndex++;
		}
		
		return pageIndex;
	}

//	/**
//	 * @param pageIndex the pageIndex to set
//	 */
//	public void setPageIndex(int pageIndex) {
//		getStateHelper().put(PropertyKeys.pageIndex, pageIndex);
//	}

	/**
	 * @return the pageIndexVar
	 */
	public String getPageIndexVar() {
		return (String) getStateHelper().eval(PropertyKeys.pageIndexVar);
	}

	/**
	 * @param pageIndexVar the pageIndexVar to set
	 */
	public void setPageIndexVar(String pageIndexVar) {
		getStateHelper().put(PropertyKeys.pageIndexVar, pageIndexVar);
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
//		return (int) getStateHelper().eval(PropertyKeys.pageCount, Integer.MIN_VALUE);
		
		UIData uiData = getUIData();
		int rows = uiData.getRows();
		int pageCount = 1;
		if (rows > 0) {
			int rowCount = uiData.getRowCount();
			pageCount = rowCount / rows;
			if (rowCount % rows > 0) {
				pageCount++;
			}
		}
		
		return pageCount;
	}

//	/**
//	 * @param pageCount the pageCount to set
//	 */
//	public void setPageCount(int pageCount) {
//		getStateHelper().put(PropertyKeys.pageCount, pageCount);
//	}

	/**
	 * @return the pageCountVar
	 */
	public String getPageCountVar() {
		return (String) getStateHelper().eval(PropertyKeys.pageCountVar);
	}

	/**
	 * @param pageCountVar the pageCountVar to set
	 */
	public void setPageCountVar(String pageCountVar) {
		getStateHelper().put(PropertyKeys.pageCountVar, pageCountVar);
	}

	/**
	 * @return the paginator
	 */
	public boolean isPaginator() {
		return (boolean) getStateHelper().eval(PropertyKeys.paginator, false);
	}
	
	/**
	 * @param paginator the paginator to set
	 */
	public void setPaginator(boolean paginator) {
		getStateHelper().put(PropertyKeys.paginator, paginator);
	}
	
	/**
	 * @return the paginatorMaxPages
	 */
	public int getPaginatorMaxPages() {
		return (int) getStateHelper().eval(PropertyKeys.paginatorMaxPages, Integer.MIN_VALUE);
	}

	/**
	 * @param paginatorMaxPages the paginatorMaxPages to set
	 */
	public void setPaginatorMaxPages(int paginatorMaxPages) {
		getStateHelper().put(PropertyKeys.paginatorMaxPages, paginatorMaxPages);
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
	
	protected UIData getUIData() {
		UIComponent uiData = null;
		String clientId = getFor();
		if (!Strings.isNullOrEmpty(clientId)) {
			uiData = findComponent(clientId);
		} else {
			uiData = getParent(); //Are we inside an UIData
		}
		
		if (uiData == null) {
			if (Strings.isNullOrEmpty(clientId)) {
				throw new IllegalArgumentException("Could not find UIData component. Please add referenced to attribute " + getClass().getSimpleName() + "@for");
			} else {
				throw new IllegalArgumentException("Could not find UIData component by referenced attribute " + getClass().getSimpleName() + "@for=\"" + clientId  + "\".");
			}
		} else if (!(uiData instanceof UIData)) {
			if (Strings.isNullOrEmpty(clientId)) {
				throw new IllegalArgumentException("DataScroller component (id=\"" + getClientId(getFacesContext()) + "\") is not nested inside a " + UIData.class.getName() + " component");
			} else {
				throw new IllegalArgumentException("DataScroller component (id=\"" + getClientId(getFacesContext()) + "\") attribute @for=\"" + clientId  + "\" does not reference to component of type " + UIData.class.getName() + ", but found type " + uiData.getClass().getName());
			}
		}
		
		return (UIData) uiData;
	}
}
