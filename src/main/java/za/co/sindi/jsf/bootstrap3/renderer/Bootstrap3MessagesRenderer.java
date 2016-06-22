/**
 * 
 */
package za.co.sindi.jsf.bootstrap3.renderer;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import za.co.sindi.faces.renderer.BaseRenderer;
import za.co.sindi.jsf.bootstrap3.component.Bootstrap3Messages;

/**
 * Renders {@link UIMessages} with Twitter Bootstrap 3 alert component.
 * 
 * @author Bienfait Sindi
 * @since 07 April 2014
 *
 */
@FacesRenderer(componentFamily=UIMessages.COMPONENT_FAMILY, rendererType=Bootstrap3MessagesRenderer.RENDERER_TYPE)
public class Bootstrap3MessagesRenderer extends BaseRenderer {

	/** The standard renderer type. */
	public static final String RENDERER_TYPE = "za.co.sindi.faces.bootstrap3.Messages";
	
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
 
        Bootstrap3Messages messages = (Bootstrap3Messages) component;
        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);
 
        String clientId = messages.getFor();
        // if no clientId was included
		if (clientId == null) {
		    // and the author explicitly only wants global messages
			if (messages.isGlobalOnly()) {
				// make it so only global messages get displayed.
				clientId = "";
		    }
		}
		
		Iterator<FacesMessage> messageIter = getMessageIter(context, clientId, component);
        assert(messageIter != null);
 
        if (!messageIter.hasNext()) {
            if (mustRender) {
                if ("javax_faces_developmentstage_messages".equals(component.getId())) {
                    return;
                }
                writer.startElement("div", component);
                writeIdAttributeIfNecessary(context, writer, component);
                writer.endElement("div");
            }
            return;
        }
        
        // style is rendered as a passthru attribute
//        RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
        
        // Should we escape HTML?
    	Boolean htmlEscape = (Boolean) component.getAttributes().get("escape");
    	if (htmlEscape == null) {
    		htmlEscape = true; //The default
    	}
    	
        while (messageIter.hasNext()) {
        	FacesMessage currentMessage = messageIter.next();
        	if (currentMessage.isRendered() && !messages.isRedisplay()) {
        		continue ;
        	}
        	
        	currentMessage.rendered();
        
        	String styleClass = null;
        	Severity severity = currentMessage.getSeverity();
        	
        	if (FacesMessage.SEVERITY_ERROR == severity) {
        		styleClass = (String) component.getAttributes().get("errorClass");
        		if (styleClass == null || styleClass.isEmpty()) {
        			styleClass = "alert-danger";
        		}
        	} else if (FacesMessage.SEVERITY_FATAL == severity) {
        		styleClass = (String) component.getAttributes().get("fatalClass");
        		if (styleClass == null || styleClass.isEmpty()) {
        			styleClass = "alert-danger";
        		}
        	} else if (FacesMessage.SEVERITY_INFO == severity) {
        		styleClass = (String) component.getAttributes().get("infoClass");
        		if (styleClass == null || styleClass.isEmpty()) {
        			styleClass = "alert-info";
        		}
        	} else if (FacesMessage.SEVERITY_WARN == severity) {
        		styleClass = (String) component.getAttributes().get("warnClass");
        		if (styleClass == null || styleClass.isEmpty()) {
        			styleClass = "alert-warning";
        		}
        	}
        	
        	if (styleClass == null) {
        		styleClass = "";
        	}
        	
        	writer.startElement("div", component);
        	writer.writeAttribute("class", "alert " + styleClass + (messages.isDismissible() ? " alert-dismissible" : ""), null);
        	writer.writeAttribute("role", "alert", null);
       
        	if (messages.isDismissible()) {
	        	writer.startElement("button", component);
	        	writer.writeAttribute("type", "button", null);
	        	writer.writeAttribute("class", "close", null);
	        	writer.writeAttribute("data-dismiss", "alert", null);
//	        	writer.writeAttribute("aria-hidden", "true", null);
//	        	writer.write("&times");
//	        	writer.endElement("button");
	        	
	        	//For Bootstrap 3.2.0 and above
	        	writer.writeAttribute("aria-label", "Close", null);
	        	writer.startElement("span", component);
	        	writer.writeAttribute("aria-hidden", "true", null);
	        	writer.write("&times");
	        	writer.endElement("span");
	        	writer.endElement("button");
        	}
        	
        	if (messages.isShowIcon()) {
        		String iconLibraryName = messages.getIconLibrary();
    			StringBuilder sb = new StringBuilder();
    			if (iconLibraryName == null || iconLibraryName.isEmpty()) {
    				iconLibraryName = "bs";
    			}
    			if ("bs".equals(iconLibraryName)) {
	    			sb.append("fa ");
	    			
	            	if (FacesMessage.SEVERITY_ERROR == severity) {
	            		sb.append("fa-times-circle");
	            	} else if (FacesMessage.SEVERITY_FATAL == severity) {
	            		sb.append("fa-times-circle");
	            	} else if (FacesMessage.SEVERITY_INFO == severity) {
	            		sb.append("fa-info-circle");
	            	} else if (FacesMessage.SEVERITY_WARN == severity) {
	            		sb.append("fa-warning");
	            	}
	    			
	    			sb.append(" fa-fw fa-lg");
	    			
	    			writer.startElement("i", component);
	    			writer.writeAttribute("class", sb.toString(), null);
	    			writer.endElement("i");
    			} else if ("bs".equals(iconLibraryName)) {
	    			sb.append("glyphicon ");
	    			
//	    			String severityName = "";
	            	if (FacesMessage.SEVERITY_ERROR == severity) {
//	            		severityName = "Error";
	            		sb.append("glyphicon-remove-sign");
	            	} else if (FacesMessage.SEVERITY_FATAL == severity) {
//	            		severityName = "Fatal";
	            		sb.append("glyphicon-question-sign");
	            	} else if (FacesMessage.SEVERITY_INFO == severity) {
//	            		severityName = "Info";
	            		sb.append("glyphicon-info-sign");
	            	} else if (FacesMessage.SEVERITY_WARN == severity) {
//	            		severityName = "Warning";
	            		sb.append("glyphicon-exclamation-sign");
	            	}
	    			
	    			writer.startElement("span", component);
	    			writer.writeAttribute("class", sb.toString(), null);
	    			writer.writeAttribute("aria-hidden", "true", null);
	    			writer.write("");
	    			writer.endElement("span");
    			}
    			
    			String severityName = "";
            	if (FacesMessage.SEVERITY_ERROR == severity) {
            		severityName = "Error";
            	} else if (FacesMessage.SEVERITY_FATAL == severity) {
            		severityName = "Fatal";
            	} else if (FacesMessage.SEVERITY_INFO == severity) {
            		severityName = "Info";
            	} else if (FacesMessage.SEVERITY_WARN == severity) {
            		severityName = "Warning";
            	}
    			
    			writer.startElement("span", component);
    			writer.writeAttribute("class", "sr-only", null);
    			writer.write(severityName + ":");
    			writer.endElement("span");
    		}
        	
        	if (messages.isShowSummary()) {
        		String summary = currentMessage.getSummary();
        		if (summary != null && !summary.isEmpty()) {
        			writer.startElement("strong", component);
        			writeText(writer, summary, null, htmlEscape);
        			writer.endElement("strong");
        		}
        	}
        	
        	if (messages.isShowDetail()) {
        		String detail = currentMessage.getDetail();
        		if (detail != null && !detail.isEmpty()) {
        			writeText(writer, " " + detail, null, htmlEscape);
        		}
        	}
        	
        	writer.endElement("div");
        }
	}
	
	private void writeText(ResponseWriter writer, String text, String property, boolean htmlEscape) throws IOException {
		if (!htmlEscape) {
			writer.write(text);
		} else {
			writer.writeText(text, property);
		}
	}
}
