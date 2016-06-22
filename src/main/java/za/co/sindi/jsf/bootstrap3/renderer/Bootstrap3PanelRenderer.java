/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import za.co.sindi.common.utils.Strings;
import za.co.sindi.faces.renderer.BaseRenderer;

/**
 * @author Bienfait Sindi
 * @since 19 July 2015
 *
 */
@FacesRenderer(componentFamily=UIPanel.COMPONENT_FAMILY, rendererType=Bootstrap3PanelRenderer.RENDERER_TYPE)
public class Bootstrap3PanelRenderer extends BaseRenderer {

	public static final String RENDERER_TYPE = "za.co.sindi.faces.bootstrap3.Panel";

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeBegin(context, component);
		
		if (!shouldEncode(component)) {
			return;
		}
		
		final ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", component);
        writeIdAttributeIfNecessary(context, writer, component);
        String styleClass = (String) component.getAttributes().get("styleClass");
        writer.writeAttribute("class", "panel" + (Strings.isNullOrEmpty(styleClass) ?  "" : " " + styleClass) , null);
//        if (styleClass != null) {
//            writer.writeAttribute("class", styleClass, "styleClass");
//        }
        String style = (String) component.getAttributes().get("style");
        if (style != null) {
            writer.writeAttribute("style", style, "style");
        }
        writer.write("\n\t");
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		if (null == context || null == component) {
		    throw new NullPointerException();
		}
		
		if (!shouldEncodeChildren(component)) {
			return;
		}
		
		final ResponseWriter writer = context.getResponseWriter();
		renderBody(context, component, writer);
//		for (Iterator<UIComponent> kids = getChildren(component); kids.hasNext();) {
//			 
//			UIComponent child = kids.next();
//			if (!child.isRendered()) {
//				 continue;
//			}
//			encodeRecursive(context, child);
//		}
		super.encodeChildren(context, component);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(context, component);
		
		if (!shouldEncode(component)) {
			return;
		}
		
		final ResponseWriter writer = context.getResponseWriter();
		renderFooter(context, component, writer);
		writer.write("\n");
		writer.endElement("div");
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}
	
	protected void renderHeader(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
		// TODO Auto-generated method stub
        UIComponent header = getFacet(component, "header");
        String headerClass = (String) component.getAttributes().get("headerClass");
        if (header != null) {
            writer.startElement("div", component);
            writer.writeAttribute("class", "panel-heading" + (Strings.isNullOrEmpty(headerClass) ?  "" : " " + headerClass), null);
            writer.writeText("\n", component, null);
            encodeRecursive(context, header);
            writer.endElement("div");
            writer.writeText("\n", component, null);
        }
	}
	
	protected void renderBody(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
		// TODO Auto-generated method stub
        UIComponent body = getFacet(component, "body");
        String bodyClass = (String) component.getAttributes().get("bodyClass");
        if (body != null) {
            writer.startElement("div", component);
            writer.writeAttribute("class", "panel-body" + (Strings.isNullOrEmpty(bodyClass) ?  "" : " " + bodyClass), null);
            writer.writeText("\n", component, null);
            encodeRecursive(context, body);
            writer.endElement("div");
            writer.writeText("\n", component, null);
        }
	}
	
	protected void renderFooter(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
		// TODO Auto-generated method stub
        UIComponent footer = getFacet(component, "footer");
        String footerClass = (String) component.getAttributes().get("footerClass");
        if (footer != null) {
            writer.startElement("div", component);
            writer.writeAttribute("class", "panel-footer" + (Strings.isNullOrEmpty(footerClass) ?  "" : " " + footerClass), null);
            writer.writeText("\n", component, null);
            encodeRecursive(context, footer);
            writer.endElement("div");
            writer.writeText("\n", component, null);
        }
	}
}
