/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.renderer;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import za.co.sindi.faces.renderer.BaseRenderer;
import za.co.sindi.jsf.bootstrap3.component.Bootstrap3Message;

/**
 * Renderers {@link UIMessage} with Twitter Bootstrap 3 alert component.
 * This is a modified copy of the <code>com.sun.faces.renderkit.html_basic.MessageRenderer</code> class.
 * 
 * @author Bienfait Sindi
 * @since 07 April 2014
 *
 */
@FacesRenderer(componentFamily=UIMessage.COMPONENT_FAMILY, rendererType=Bootstrap3MessageRenderer.RENDERER_TYPE)
public class Bootstrap3MessageRenderer extends BaseRenderer {

	/** The standard renderer type. */
	public static final String RENDERER_TYPE = "za.co.sindi.faces.bootstrap3.Message";
	
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
		 
        boolean mustRender = shouldWriteIdAttribute(component);
 
        Bootstrap3Message message = (Bootstrap3Message) component;
        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);
 
        String clientId = message.getFor();
        // if no clientId was included
		if (clientId == null) {
		    // and the author explicitly only wants global messages
			if (LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.warning("'for' attribute cannot be null.");
			}
			
			return ;
		}
		
		clientId = augmentIdReference(clientId, component);
		Iterator<FacesMessage> messageIter = getMessageIter(context, clientId, component);
        assert(messageIter != null);
 
        if (!messageIter.hasNext()) {
            if (mustRender) {
                writer.startElement("span", component);
                writeIdAttributeIfNecessary(context, writer, component);
                writer.endElement("span");
            }
            return;
        }
        
        // Should we escape HTML?
    	Boolean htmlEscape = (Boolean) component.getAttributes().get("escape");
    	if (htmlEscape == null) {
    		htmlEscape = true; //The default
    	}
    	
    	FacesMessage currentMessage = messageIter.next();
    	if (currentMessage.isRendered() && !message.isRedisplay()) {
    		return ;
    	}
    	
    	currentMessage.rendered();
    
    	writer.startElement("span", component);
    	writeIdAttributeIfNecessary(context, writer, message);
    	writer.writeAttribute("class", "help-block", null);
    	
		if (message.isShowIcon()) {
			String iconLibraryName = message.getIconLibrary();
			StringBuilder sb = new StringBuilder();
			if (iconLibraryName == null || iconLibraryName.isEmpty()) {
				iconLibraryName = "bs";
			}
			if ("fa".equals(iconLibraryName))
				sb.append("fa ");
			else if ("bs".equals(iconLibraryName))
				sb.append("glyphicon ");
			else if (LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.warning("Unknown iconLibrary name '" + iconLibraryName + "'. Allowable values are 'fa' or 'bs' (without quotes).");
			}
			
			Severity severity = currentMessage.getSeverity();
        	
        	if (FacesMessage.SEVERITY_ERROR == severity) {
        		if ("fa".equals(iconLibraryName))
        			sb.append("fa-times-circle");
        		else if ("bs".equals(iconLibraryName))
        			sb.append("glyphicon-remove-sign");
        	} else if (FacesMessage.SEVERITY_FATAL == severity) {
        		if ("fa".equals(iconLibraryName))
        			sb.append("fa-times-circle");
        		else if ("bs".equals(iconLibraryName))
        			sb.append("glyphicon-question-sign");
        	} else if (FacesMessage.SEVERITY_INFO == severity) {
        		if ("fa".equals(iconLibraryName))
        			sb.append("fa-info-circle");
        		else if ("bs".equals(iconLibraryName))
        			sb.append("glyphicon-info-sign");
        	} else if (FacesMessage.SEVERITY_WARN == severity) {
        		if ("fa".equals(iconLibraryName))
        			sb.append("fa-warning");
        		else if ("bs".equals(iconLibraryName))
        			sb.append("glyphicon-exclamation-sign");
        	}
			
        	if ("fa".equals(iconLibraryName)) {
				sb.append(" fa-fw fa-lg");
				
				writer.startElement("i", component);
				writer.writeAttribute("class", sb.toString(), null);
				writer.endElement("i");
        	} else if ("bs".equals(iconLibraryName)) {
        		writer.startElement("span", component);
    			writer.writeAttribute("class", sb.toString(), null);
    			writer.writeAttribute("aria-hidden", "true", null);
    			writer.write("");
    			writer.endElement("span");
        	}
		}
   
    	if (message.isShowSummary()) {
    		String summary = currentMessage.getSummary();
    		if (summary != null && !summary.isEmpty()) {
    			writer.startElement("strong", component);
    			writeText(writer, summary, null, htmlEscape);
    			writer.endElement("strong");
    		}
    	}
    	
    	if (message.isShowDetail()) {
    		String detail = currentMessage.getDetail();
    		if (detail != null && !detail.isEmpty()) {
    			writeText(writer, " " + detail, null, htmlEscape);
    		}
    	}
    	
    	writer.endElement("span");
	}
	
	private void writeText(ResponseWriter writer, String text, String property, boolean htmlEscape) throws IOException {
		if (!htmlEscape) {
			writer.write(text);
		} else {
			writer.writeText(text, property);
		}
	}
}
