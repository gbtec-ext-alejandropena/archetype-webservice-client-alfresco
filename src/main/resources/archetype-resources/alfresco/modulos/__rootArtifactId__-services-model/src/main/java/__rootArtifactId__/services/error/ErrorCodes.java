#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.${rootArtifactId}.services.error;

/**
 * Codigos utilizados entre los servicios de alfresco y la aplicacion para
 * identificar la causa de los errores.
 *
 * @author ingapl
 */
public final class ErrorCodes {

    private ErrorCodes() {

    }

    public static final int NO_AUTORIZADO = 401;
    
}
