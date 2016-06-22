/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.renderer;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import za.co.sindi.common.utils.PreConditions;
import za.co.sindi.common.utils.Strings;
import za.co.sindi.faces.renderer.BaseRenderer;
import za.co.sindi.faces.utils.FacesUtils;
import za.co.sindi.jsf.bootstrap3.component.AbstractDataScroller;
import za.co.sindi.jsf.bootstrap3.event.DataScrollerEvent;

/**
 * @author Bienfait Sindi
 * @since 24 November 2015
 *
 */
@FacesRenderer(componentFamily=AbstractDataScroller.COMPONENT_FAMILY, rendererType=Bootstrap3DataScrollerRenderer.RENDERER_TYPE)
public class Bootstrap3DataScrollerRenderer extends BaseRenderer {

	public static final String RENDERER_TYPE = "za.co.sindi.faces.bootstrap3.DataScroller";
	
	protected static final String PAGE_NAVIGATION = "index_".intern();

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void decode(FacesContext context, UIComponent component) {
		// TODO Auto-generated method stub
		super.decode(context, component);
		
		if (!shouldDecode(component)) {
			return ;
		}
		
		AbstractDataScroller dataScroller = (AbstractDataScroller) component;
		Map<String, String> requestParameters = FacesUtils.getRequestParameterMap(context);
		String clientId = component.getClientId(context);
		if (requestParameters.containsKey(clientId)) {
			int currentPageIndex = dataScroller.getPageIndex();
			
			String parameter = requestParameters.get(clientId);
			if (parameter.startsWith(PAGE_NAVIGATION)) {
				//User selected a page.
				component.queueEvent(new DataScrollerEvent(component, currentPageIndex, Integer.parseInt(parameter.substring(PAGE_NAVIGATION.length(), parameter.length()))));
			} else {
				//User selected facets...
				component.queueEvent(new DataScrollerEvent(component, currentPageIndex, getPageIndexForFacet(dataScroller, parameter)));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
//		super.encodeBegin(context, component);
		validateParameters(context, component, AbstractDataScroller.class);
		
		if (!shouldEncode(component)) {
			return ;
		}
		
		AbstractDataScroller dataScroller = (AbstractDataScroller) component;
		setVariables(context, dataScroller);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
//		super.encodeChildren(context, component);
		validateParameters(context, component, AbstractDataScroller.class);
		
		if (component.getChildCount() > 0) {
			String clientIdPrefix = component.getId() + "_" + PAGE_NAVIGATION;
			for (UIComponent childComponent : component.getChildren()) {
				String childId = childComponent.getId();
				if (childId != null && !childId.startsWith(clientIdPrefix)) {
					encodeRecursive(context, childComponent);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
//		super.encodeEnd(context, component);
		validateParameters(context, component, AbstractDataScroller.class);
		
		if (!shouldEncode(component)) {
			return ;
		}
		
		//Check if there is an availabe UIData linked to this component
		UIData uiData = getUIData(context, component);
		if (uiData == null || !UIData.class.isInstance(uiData)) {
			throw new FacesException("UIComponent " + component.getClientId(context) + " is not a child of UIData component or the \"for\" attribute name is not linked to a UIData component.");
		}
		
		AbstractDataScroller dataScroller = (AbstractDataScroller) component;
		boolean paginator = dataScroller.isPaginator();
		if (paginator) {
			renderPaginator(context, dataScroller);
		} else {
			renderPager(context, dataScroller);
		}
		
		//Finally
		removeVariables(context, dataScroller);
	}

	protected UIData getUIData(FacesContext context, UIComponent component) {
		UIComponent uiData = null;
		String clientId = null;
		if (component instanceof AbstractDataScroller) {
			clientId = ((AbstractDataScroller)component).getFor();
		}
		
		if (Strings.isNullOrEmpty(clientId)) {
			clientId = (String) component.getAttributes().get("for");
		}
		
		if (!Strings.isNullOrEmpty(clientId)) {
			uiData = getForComponent(context, clientId, component);
		} else {
			uiData = component.getParent(); //Are we inside an UIData
		}
		
		if (uiData != null && UIData.class.isInstance(uiData)) {
			return (UIData) uiData;
		}
		
		return null;
	}
	
	protected int getPageIndexForFacet(AbstractDataScroller dataScroller, final String facetName) {
		PreConditions.checkArgument(dataScroller != null, "No data scroller was specified.");
		PreConditions.checkArgument(!Strings.isNullOrEmpty(facetName), "No facet name was specified.");
		
		int newPageIndex = 1;
		int pageCount = dataScroller.getPageCount();
		if (pageCount < 0) {
			pageCount = 1;
		}
		int fastStep = dataScroller.getFastStep();
		if (fastStep < 0) {
			fastStep = 1;
		}
		
		if (AbstractDataScroller.FAST_FORWARD_FACET_NAME.equals(facetName)) {
			newPageIndex = pageCount + fastStep;
		} else if (AbstractDataScroller.FAST_REWIND_FACET_NAME.equals(facetName)) {
			newPageIndex = pageCount - fastStep;
		} else if (AbstractDataScroller.FIRST_FACET_NAME.equals(facetName)) {
			newPageIndex = 1;
		} else if (AbstractDataScroller.LAST_FACET_NAME.equals(facetName)) {
			newPageIndex = pageCount;
		} else if (AbstractDataScroller.NEXT_FACET_NAME.equals(facetName)) {
			newPageIndex = dataScroller.getPageIndex() + 1;
		} else if (AbstractDataScroller.PREVIOUS_FACET_NAME.equals(facetName)) {
			newPageIndex = dataScroller.getPageIndex() - 1;
		}
		
		if (newPageIndex >= 1 && newPageIndex <= pageCount) {
			return newPageIndex;
		}
		
		return 0;
	}
	
	protected void setVariables(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		Map<String, Object> requestMap = FacesUtils.getRequestMap(context);
		
		//pageCount & pageCountVar
		String pageCountVar = dataScroller.getPageCountVar();
		if (!Strings.isNullOrEmpty(pageCountVar)) {
			Integer pageCount = dataScroller.getPageCount();
			requestMap.put(pageCountVar, pageCount);
		}
		
		//pageIndex & pageIndexVar
		String pageIndexVar = dataScroller.getPageIndexVar();
		if (!Strings.isNullOrEmpty(pageIndexVar)) {
			Integer pageIndex = dataScroller.getPageIndex();
			requestMap.put(pageIndexVar, pageIndex);
		}
	}
	
	protected void removeVariables(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		Map<String, Object> requestMap = FacesUtils.getRequestMap(context);
		
		//pageCount & pageCountVar
		String pageCountVar = dataScroller.getPageCountVar();
		if (!Strings.isNullOrEmpty(pageCountVar)) {
			requestMap.remove(pageCountVar);
		}
		
		//pageIndex & pageIndexVar
		String pageIndexVar = dataScroller.getPageIndexVar();
		if (!Strings.isNullOrEmpty(pageIndexVar)) {
			requestMap.remove(pageIndexVar);
		}
	}
	
	protected HtmlCommandLink createLink(FacesContext context, AbstractDataScroller dataScroller, int pageIndex, boolean active) {
		Application application = context.getApplication();
		String id = PAGE_NAVIGATION + pageIndex;
		String componentId = dataScroller.getId() + "_" + id;
		HtmlCommandLink link = (HtmlCommandLink) dataScroller.findComponent(componentId);
		if (link == null) {
			link = new HtmlCommandLink();
			link.setId(componentId);
			link.setTransient(true);
			UIParameter parameter = (UIParameter) application.createComponent(UIParameter.COMPONENT_TYPE);
			parameter.setId(componentId + "_param");
			parameter.setTransient(true);
			parameter.setName(dataScroller.getClientId(context));
			parameter.setValue(id);
			HtmlOutputText text = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
			text.setId(componentId + "_text");
			text.setTransient(true);
			text.setValue(String.valueOf(pageIndex));
			link.getChildren().add(text);
			dataScroller.getChildren().add(link);
		} else {
			UIOutput text = (UIOutput) link.findComponent(componentId + "_text");
			if (text != null) {
				text.setValue(String.valueOf(pageIndex));
			}
		}
		
		//We need to remove and re-create the link, in case we move to another page.
		HtmlPanelGroup panelGroup = (HtmlPanelGroup) link.findComponent(componentId + "_panel");
		if (panelGroup != null) {
			link.getChildren().remove(panelGroup);
		}
		
		if (active) {
			panelGroup = (HtmlPanelGroup) application.createComponent(HtmlPanelGroup.COMPONENT_TYPE);
			panelGroup.setId(componentId + "_panel");
			panelGroup.setLayout(""); //Forces it to be a span
			panelGroup.setTransient(true);
			
			HtmlOutputText spanText = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
			spanText.setId(componentId + "_paneltext");
			spanText.setTransient(true);
			spanText.setValue("(current)");
			panelGroup.getChildren().add(spanText);
			link.getChildren().add(panelGroup);
		}
		
		return link;
	}
	
	protected HtmlCommandLink createLink(FacesContext context, AbstractDataScroller dataScroller, String facetName) {
		String componentId = dataScroller.getId() + "_" + facetName;
		HtmlCommandLink link = (HtmlCommandLink) dataScroller.findComponent(componentId);
		if (link == null) {
			link = new HtmlCommandLink();
			link.setId(componentId);
			link.setTransient(true);
			UIParameter parameter = (UIParameter) context.getApplication().createComponent(UIParameter.COMPONENT_TYPE);
			parameter.setId(componentId + "_param");
			parameter.setTransient(true);
			parameter.setName(dataScroller.getClientId(context));
			parameter.setValue(facetName);
			link.getChildren().add(parameter);
			dataScroller.getChildren().add(link);
		}
		
		return link;
	}
	
	protected void renderPaginatorFacet(FacesContext context, AbstractDataScroller dataScroller, UIComponent facetComponent, String facetName, String ariaLabel, boolean active, boolean disabled) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		HtmlCommandLink link = createLink(context, dataScroller, facetName);
		if (disabled) {
			link.setDisabled(true); //For safety sake....
		}
		
		writer.startElement("li", dataScroller);
		if (disabled) {
			writer.writeAttribute("class", "disabled", null);
		} else if (active) {
			writer.writeAttribute("class", "active", null);
		}
		
//		writer.startElement("span", dataScroller);
//		writer.writeAttribute("aria-hidden", "true", null);
		
		if (!Strings.isNullOrEmpty(ariaLabel)) {
			link.getPassThroughAttributes(true).put("aria-label", ariaLabel);
		}
		link.encodeBegin(context);
		writer.write("<span aria-hidden=\"true\">");
		facetComponent.encodeBegin(context);
		if (!facetComponent.getRendersChildren()) {
			facetComponent.encodeChildren(context);
		}
		facetComponent.encodeEnd(context);
		writer.write("</span>");
		link.encodeEnd(context);
//		writer.endElement("span");
		writer.endElement("li");
	}
	
	protected void renderPaginator(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		
		boolean startActive = (dataScroller.getPageIndex() > 0 && dataScroller.getPageIndex() != 1);
		boolean endActive = (dataScroller.getPageIndex() != dataScroller.getPageCount());
		
		renderStartPaginator(context, dataScroller);
		
		//First
		renderFirstPaginator(context, dataScroller, startActive, !startActive);
		
		//Fast Rewind
		renderFastRewindPaginator(context, dataScroller, startActive, !startActive);
		
		//Previous
		renderPreviousPaginator(context, dataScroller, startActive, !startActive);
		
		//Render Pagination
		renderPagination(context, dataScroller);
		
		//Next
		renderNextPaginator(context, dataScroller, endActive, !endActive);
		
		//Fast Forward
		renderFastForwardPaginator(context, dataScroller, endActive, !endActive);
		
		//Fast Forward
		renderLastPaginator(context, dataScroller, endActive, !endActive);
		
		renderEndPaginator(context, dataScroller);
	}
	
	protected void renderStartPaginator(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		
		writer.startElement("nav", dataScroller);
		writer.startElement("ul", dataScroller);
		String styleClass = (String) dataScroller.getAttributes().get("styleClass");
        writer.writeAttribute("class", "pagination" + (Strings.isNullOrEmpty(styleClass) ?  "" : " " + styleClass) , null);
        String style = (String) dataScroller.getAttributes().get("style");
        if (!Strings.isNullOrEmpty(style)) {
        	writer.writeAttribute("style", style, "style");
        }
	}
	
	protected void renderEndPaginator(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("ul");
		writer.endElement("nav");
	}
	
	protected void renderFirstPaginator(FacesContext context, AbstractDataScroller dataScroller, boolean active, boolean disabled) throws IOException {
		UIComponent fastRewindFacetComponent = getFacet(dataScroller, AbstractDataScroller.FIRST_FACET_NAME);
		if (fastRewindFacetComponent != null) {
			renderPaginatorFacet(context, dataScroller, fastRewindFacetComponent, AbstractDataScroller.FIRST_FACET_NAME, "First", active, disabled);
		}
	}
	
	protected void renderFastRewindPaginator(FacesContext context, AbstractDataScroller dataScroller, boolean active, boolean disabled) throws IOException {
		UIComponent fastRewindFacetComponent = getFacet(dataScroller, AbstractDataScroller.FAST_REWIND_FACET_NAME);
		if (fastRewindFacetComponent != null) {
			renderPaginatorFacet(context, dataScroller, fastRewindFacetComponent, AbstractDataScroller.FAST_REWIND_FACET_NAME, "Fast Rewind", active, disabled);
		}
	}
	
	protected void renderPreviousPaginator(FacesContext context, AbstractDataScroller dataScroller, boolean active, boolean disabled) throws IOException {
		UIComponent previousFacetComponent = getFacet(dataScroller, AbstractDataScroller.PREVIOUS_FACET_NAME);
		if (previousFacetComponent != null) {
			renderPaginatorFacet(context, dataScroller, previousFacetComponent, AbstractDataScroller.PREVIOUS_FACET_NAME, "Previous", active, disabled);
		}
	}
	
	protected void renderNextPaginator(FacesContext context, AbstractDataScroller dataScroller, boolean active, boolean disabled) throws IOException {
		UIComponent nextFacetComponent = getFacet(dataScroller, AbstractDataScroller.NEXT_FACET_NAME);
		if (nextFacetComponent != null) {
			renderPaginatorFacet(context, dataScroller, nextFacetComponent, AbstractDataScroller.NEXT_FACET_NAME, "Next", active, disabled);
		}
	}
	
	protected void renderFastForwardPaginator(FacesContext context, AbstractDataScroller dataScroller, boolean active, boolean disabled) throws IOException {
		UIComponent fastForwardFacetComponent = getFacet(dataScroller, AbstractDataScroller.FAST_FORWARD_FACET_NAME);
		if (fastForwardFacetComponent != null) {
			renderPaginatorFacet(context, dataScroller, fastForwardFacetComponent, AbstractDataScroller.FAST_FORWARD_FACET_NAME, "Fast Forward", active, disabled);
		}
	}
	
	protected void renderLastPaginator(FacesContext context, AbstractDataScroller dataScroller, boolean active, boolean disabled) throws IOException {
		UIComponent fastForwardFacetComponent = getFacet(dataScroller, AbstractDataScroller.LAST_FACET_NAME);
		if (fastForwardFacetComponent != null) {
			renderPaginatorFacet(context, dataScroller, fastForwardFacetComponent, AbstractDataScroller.LAST_FACET_NAME, "Fast Forward", active, disabled);
		}
	}
	
	protected void renderPagination(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		int pageCount = dataScroller.getPageCount();
		if (pageCount < 1) {
//		if (pageCount <= 1) {
			return ;
		}
		
		int maxPages = dataScroller.getPaginatorMaxPages();
		if (maxPages <= 1) {
			maxPages = 2;
		}
		
		int pageIndex = dataScroller.getPageIndex();
		int delta = maxPages / 2;
		
		int pages;
		int start; 
		if (pageCount > maxPages && pageIndex > delta) {
			pages = maxPages;
			start = pageIndex - pages / 2 - 1;
			if (start + pages > pageCount) {
				start = pageCount - pages;
			}
		} else {
			pages = pageCount < maxPages ? pageCount : maxPages;
			start = 0;
		}
		
		ResponseWriter writer = context.getResponseWriter();
		for (int i = start, size = start + pages; i < size; i++) {
			int index = i + 1;
			boolean active = index == pageIndex;
			
			writer.startElement("li", dataScroller);
			if (active) {
				writer.writeAttribute("class", "active", null);
			}
			
			HtmlCommandLink link = createLink(context, dataScroller, index, active);
			link.encodeAll(context);
			
			//End
			writer.endElement("li");
		}
	}
	
	protected void renderPager(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		
		boolean startActive = (dataScroller.getPageIndex() != 1);
		boolean endActive = (dataScroller.getPageIndex() != dataScroller.getPageCount());
		
		renderStartPager(context, dataScroller);
		
		//First
		renderFirstPagerButton(context, dataScroller, !startActive);
		
		//Fast Rewind
		renderFastRewindPagerButton(context, dataScroller, !startActive);
		
		//Previous
		renderPreviousPagerButton(context, dataScroller, !startActive);
		
		//Next
		renderNextPagerButton(context, dataScroller, !endActive);
		
		//Fast Forward
		renderFastForwardPagerButton(context, dataScroller, !endActive);
		
		//Last
		renderLastPagerButton(context, dataScroller, !endActive);
		
		renderEndPager(context, dataScroller);
		
	}
	
	protected void renderStartPager(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		
		writer.startElement("nav", dataScroller);
		writer.startElement("ul", dataScroller);
		String styleClass = (String) dataScroller.getAttributes().get("styleClass");
        writer.writeAttribute("class", "pager" + (Strings.isNullOrEmpty(styleClass) ?  "" : " " + styleClass) , null);
        String style = (String) dataScroller.getAttributes().get("style");
        if (!Strings.isNullOrEmpty(style)) {
        	writer.writeAttribute("style", style, "style");
        }
	}
	
	protected void renderEndPager(FacesContext context, AbstractDataScroller dataScroller) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("ul");
		writer.endElement("nav");
	}
	
	protected void renderFirstPagerButton(FacesContext context, AbstractDataScroller dataScroller, boolean disabled) throws IOException {
		UIComponent previousFacetComponent = getFacet(dataScroller, AbstractDataScroller.FIRST_FACET_NAME);
		if (previousFacetComponent != null) {
			renderPagerFacet(context, dataScroller, previousFacetComponent, AbstractDataScroller.FIRST_FACET_NAME, "previous", disabled);
		}
	}
	
	protected void renderFastRewindPagerButton(FacesContext context, AbstractDataScroller dataScroller, boolean disabled) throws IOException {
		UIComponent previousFacetComponent = getFacet(dataScroller, AbstractDataScroller.FAST_REWIND_FACET_NAME);
		if (previousFacetComponent != null) {
			renderPagerFacet(context, dataScroller, previousFacetComponent, AbstractDataScroller.FAST_REWIND_FACET_NAME, "previous", disabled);
		}
	}
	
	protected void renderPreviousPagerButton(FacesContext context, AbstractDataScroller dataScroller, boolean disabled) throws IOException {
		UIComponent previousFacetComponent = getFacet(dataScroller, AbstractDataScroller.PREVIOUS_FACET_NAME);
		if (previousFacetComponent != null) {
			renderPagerFacet(context, dataScroller, previousFacetComponent, AbstractDataScroller.PREVIOUS_FACET_NAME, "previous", disabled);
		}
	}
	
	protected void renderNextPagerButton(FacesContext context, AbstractDataScroller dataScroller, boolean disabled) throws IOException {
		UIComponent nextFacetComponent = getFacet(dataScroller, AbstractDataScroller.NEXT_FACET_NAME);
		if (nextFacetComponent != null) {
			renderPagerFacet(context, dataScroller, nextFacetComponent, AbstractDataScroller.NEXT_FACET_NAME, "next", disabled);
		}
	}
	
	protected void renderFastForwardPagerButton(FacesContext context, AbstractDataScroller dataScroller, boolean disabled) throws IOException {
		UIComponent nextFacetComponent = getFacet(dataScroller, AbstractDataScroller.FAST_FORWARD_FACET_NAME);
		if (nextFacetComponent != null) {
			renderPagerFacet(context, dataScroller, nextFacetComponent, AbstractDataScroller.FAST_FORWARD_FACET_NAME, "next", disabled);
		}
	}
	
	protected void renderLastPagerButton(FacesContext context, AbstractDataScroller dataScroller, boolean disabled) throws IOException {
		UIComponent nextFacetComponent = getFacet(dataScroller, AbstractDataScroller.LAST_FACET_NAME);
		if (nextFacetComponent != null) {
			renderPagerFacet(context, dataScroller, nextFacetComponent, AbstractDataScroller.NEXT_FACET_NAME, "next", disabled);
		}
	}
	
	protected void renderPagerFacet(FacesContext context, AbstractDataScroller dataScroller, UIComponent facetComponent, String facetName, String styleClass, boolean disabled) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		HtmlCommandLink link = createLink(context, dataScroller, facetName);
		if (disabled) {
			link.setDisabled(true); //For safety sake....
		}
		
		writer.startElement("li", dataScroller);
		StringBuffer styleClassSB = new StringBuffer();
		styleClassSB.append(styleClass);
		if (disabled) {
			styleClassSB.append(" ");
			styleClassSB.append("disabled");
		}
		
		writer.writeAttribute("class", styleClassSB.toString(), null);
		
		link.encodeBegin(context);
		facetComponent.encodeBegin(context);
		if (!facetComponent.getRendersChildren()) {
			facetComponent.encodeChildren(context);
		}
		writer.startElement("span", link);
		writer.writeAttribute("aria-hidden", "true", null);
		facetComponent.encodeEnd(context);
		writer.endElement("span");
		link.encodeEnd(context);
		writer.endElement("li");
	}
}
