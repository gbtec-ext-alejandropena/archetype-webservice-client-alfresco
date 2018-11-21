#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#macro( ccase $str )
#foreach( $word in $rootArtifactId.split('-') )$word.substring(0,1).toUpperCase()$word.substring(1)#end
#end
#set( $classNamePrefix = "#ccase( $rootArtifactId )" )

package ${package}.${rootArtifactId}.facade;

import ${package}.${rootArtifactId}.icore.IManager;
import javax.jws.WebService;
import org.apache.cxf.annotations.SchemaValidation;
import org.springframework.stereotype.Service;
import com.ingapl.${rootArtifactId}.wsdl.${classNamePrefix};

/**
 * Fachada de publicacion de los metodos SOAP del servicio web
 *
 * @author ingapl
 */
@SchemaValidation
@Service("${rootArtifactId}WsImpl")
@WebService(serviceName = "${rootArtifactId}_wss", endpointInterface = "com.ingapl.${rootArtifactId}.wsdl.${classNamePrefix}") //Debe referenciarse el endpoint adecuado, asi como el serviceName y @Service correspondiente
public class WsImpl implements ${classNamePrefix}{ //Debe implementar la interfaz de servicio generada por el plugin de cxf. Renombrar la clase WsImpl a un nombre apropiado

    private IManager manager;

    
    public void setManager(IManager manager) {
        this.manager = manager;
    }

}
