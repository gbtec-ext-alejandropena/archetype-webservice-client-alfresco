 #set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package $

{package}.${rootArtifactId}.dao.impl.alfresco;

import $
{package}.${rootArtifactId}.idao.IDao;
import $
{package}.${rootArtifactId}.dao.impl.alfresco.jersey.JerseyServiceClientBase;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MultivaluedMap;
import com.sun.jersey.multipart.FormDataMultiPart;
import javax.ws.rs.core.MediaType;
import $

{package







}.${rootArtifactId}.exception.CustomException;

/**
 *
 * @author ingapl
 */
public class Dao implements IDao {

    private JerseyServiceClientBase jerseyService;

    private Object ejemplo() throws CustomException {
        WebResource wr = jerseyService.getWebResourceBase().path("/${rootArtifactId}/metodo");
        MultivaluedMap<String, String> defaultParams = jerseyService.getDefaultQueryParams();

        FormDataMultiPart form = new FormDataMultiPart(); //Rellenar formulario con los parametros necesarios

        //En lugar de object class se utilizaran clases jaxb propias para formatear las respuestas, las clases se generaran en el modulo compartido alfresco-services-model para
        // que puedan ser consumidas tanto en alfresco como en el DAO. Se debe indicar en JerseyClientConfig.java un reader de jaxb asociado a dichas clases
        return wr.queryParams(defaultParams).type(MediaType.MULTIPART_FORM_DATA).post(Object.class, form);
    }

//    /**
//     * Ejemplo de instanciacion de un cliente incluyendo configuracion para funcionar con HTTPS
//     *
//     * @return
//     * @throws CVEWSSException
//     * @throws IOException
//     */
//    public ClienteWss getClienteWss() throws IOException {
//        if (clienteWss == null) {
//            JaxWsProxyFactoryBean factory = generarJaxWsProxyFactoryBean();
//            clienteWss = factory.create(ClienteWss.class);
//
//            //Configuracion necesaria para que funcione con ssl
//            //Soluciona problema de java.security.cert.CertPathValidatorException: Certificate chaining error
//            Client clientClienteWss = ClientProxy.getClient(clienteWss);
//            HTTPConduit httpConduit = (HTTPConduit) clientClienteWss.getConduit();
//            TLSClientParameters tlsParams = new TLSClientParameters();
//            tlsParams.setUseHttpsURLConnectionDefaultSslSocketFactory(true);
//            httpConduit.setTlsClientParameters(tlsParams);
//        }
//        return clienteWss;
//    }
    
    public void setJerseyService(JerseyServiceClientBase jerseyService) {
        this.jerseyService = jerseyService;
    }

}
