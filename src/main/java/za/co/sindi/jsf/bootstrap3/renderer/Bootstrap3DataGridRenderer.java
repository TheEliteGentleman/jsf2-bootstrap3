/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.renderer;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import za.co.sindi.faces.renderer.BaseRenderer;

/**
 * @author Bienfait Sindi
 * @since 16 July 2015
 *
 */
@FacesRenderer(componentFamily=UIData.COMPONENT_FAMILY, rendererType=Bootstrap3DataGridRenderer.RENDERER_TYPE)
public class Bootstrap3DataGridRenderer extends BaseRenderer {

	public static final String RENDERER_TYPE = "za.co.sindi.faces.bootstrap3.DataGrid";

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeBegin(context, component);
		
		if (!shouldEncode(component)) {
			return ;
		}
		
		String layout = (String)component.getAttributes().get("layout");
		if (!"table".equals(layout) && !"grid".equals(layout)) {
			throw new FacesException(layout + " is not a valid value for DataGrid layout. Possible values are 'table' and 'grid' (without quotes).");
		}
		
		// Set up the variables we will need
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement("grid".equals(layout) ? "div" : "table", component);
        writeIdAttributeIfNecessary(context, writer, component);
        String styleClass = (String) component.getAttributes().get("styleClass");
        if (styleClass != null && "grid".equals(layout)) {
        	writer.writeAttribute("class", styleClass, "styleClass");
        } else if ("table".equals(layout)) {
    		writer.writeAttribute("class", "table" + (styleClass == null || styleClass.isEmpty() ? "" : " " + styleClass), null);
    	}
        String style = (String) component.getAttributes().get("style");
        if (style != null) {
            writer.writeAttribute("style", style, "style");
        }
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
			return ;
		}
		
		// Set up the variables we will need
		String layout = (String)component.getAttributes().get("layout");
		UIData data = (UIData)component;
		int columns = component.getAttributes().get("columns") != null ? (Integer)component.getAttributes().get("columns") : 4;
		int rowIndex = data.getFirst();
		int rows = data.getRows();
		int itemsToRender = rows != 0 ? rows : data.getRowCount();
		int numberOfRowsToRender = (itemsToRender + columns - 1) / columns;
        final ResponseWriter writer = context.getResponseWriter();
        if ("table".equals(layout)) {
        	writer.startElement("tbody", component);
        }
        
        for(int i = 0; i < numberOfRowsToRender; i++) {
        	data.setRowIndex(rowIndex);
        	if (!data.isRowAvailable()) {
        		return ;
        	}
        	
        	writer.startElement("grid".equals(layout) ? "div" : "tr", component);
        	if ("grid".equals(layout)) {
        		writer.writeAttribute("class", "row", null);
        	}
            
            for(int j = 0; j < columns; j++) {
            	data.setRowIndex(rowIndex);
            	if (!data.isRowAvailable()) {
            		break;
            	}
            	
            	writer.startElement("grid".equals(layout) ? "div" : "td", component);
                String columnClasses = (String) component.getAttributes().get("columnClasses");
                if (columnClasses != null) {
                	String[] columnClassez = columnClasses.split(",");
                    writer.writeAttribute("class", columnClassez.length == 1 ? columnClassez[0] : columnClassez[j], null);
                }
                
                super.encodeChildren(context, data);
            	rowIndex++;
                
                writer.endElement("grid".equals(layout) ? "div" : "td");
            }
            
            writer.endElement("grid".equals(layout) ? "div" : "tr");
        }
        
        //Clean data
        data.setRowIndex(-1);
        
        if ("table".equals(layout)) {
        	writer.endElement("tbody");
        }
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeBegin(context, component);
		
		if (!shouldEncode(component)) {
			return ;
		}
		
		String layout = (String)component.getAttributes().get("layout");
		// Set up the variables we will need
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement("grid".equals(layout) ? "div" : "table");
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}
}
