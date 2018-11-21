#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 *  
 *  
 */
package ${package}.${rootArtifactId}.dao.impl.alfresco.jersey;

import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Configuración global del cliente de servicios REST Jersey.
 *
 *
 */
public class JerseyClientConfig extends DefaultClientConfig {

    public JerseyClientConfig() {
        //Example: getClasses().add(OIDResponseReader.class);        
    }
}
