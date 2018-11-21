 #set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 *  
 *  
 */
package $

{package}.${rootArtifactId}.dao.impl.alfresco.jersey;

import $
{package}.${rootArtifactId}.exception.CustomException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.core.MultivaluedMap;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Clase base para el cliente de servicios REST.
 *
 *
 */


public class JerseyServiceClientBase {

    private static final String PARAM_ALF_TICKET = "alf_ticket";
    private static String alfTicket;
    private Client client;
    private String alfrescoURL;
    private String alfrescoServicesURL;
    private String user;
    private String password;

    public JerseyServiceClientBase() {
    }

    /**
     * Crea una conexion con alfresco
     *
     * @throws CustomException
     */
    public void startConnection() throws CustomException {
        //Chequeamos si es posible establecer la conexión, si salta la excepción trazamos el error y ya no intentamos inicializar el cliente.
        checkConnection();

        //Eliminamos los posibles filtros ya existentes, para permitir la recarga del contexto en caliente.
        client.removeAllFilters();

        client.addFilter(new JerseyLoggingFilter());
        login(user, password);

    }

    /**
     * Comprueba la posibilidad de conectar con el repositorio
     *
     * @throws CustomException
     */
    private void checkConnection() throws CustomException {
        try {
            URL url = new URL(alfrescoURL);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();
            if (log.isTrazaEnabled()) {
                log.traza("Resultado de la verificación de conexión con la URL " + url + " : " + urlConn.getResponseCode());
            }
        } catch (IOException ex) {
            throw new CustomException("JSCB002", "Error de conexion con el repositorio", ex);
        }
    }

    /**
     * Efectúa el login y obtiene un ticket de alfrescof
     *
     * @param username
     * @param pass
     * @throws CustomException
     */
    public void login(String username, String pass) throws CustomException {
        String ticket = null;
        try {
            WebResource webResource = client.resource(alfrescoServicesURL).path("/api/login");
            MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
            queryParams.add("u", username);
            queryParams.add("pw", pass);
            queryParams.add("format", "json");
            JSONObject json = webResource.queryParams(queryParams).get(JSONObject.class);
            ticket = (String) ((JSONObject) json.get("data")).get("ticket");
        } catch (JSONException ex) {
            throw new CustomException("JSCB003", "Error en JerseyServiceClientBase: Imposible conectar con el repositorio " + alfrescoURL, ex);
        } catch (UniformInterfaceException ex) {
            throw new CustomException("JSCB004", "Error de conexion con alfresco: Imposible conectar con el repositorio " + alfrescoURL, ex);
        }
        JerseyServiceClientBase.setAlfTicket(ticket);
    }

    /**
     * Invalida el ticket de alfresco
     *
     * @param ticket
     * @return
     */
    public int logout(String ticket) {
        WebResource webResource = client.resource(alfrescoServicesURL).path("/api/login/ticket/").path(ticket);
        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add("alf_ticket", alfTicket);
        ClientResponse clientResponse = webResource.queryParams(queryParams).delete(ClientResponse.class);
        setAlfTicket(null);
        return clientResponse.getStatus();
    }

    /**
     * Proporcional la url base para todas las peticiones a trvés del servlet de
     * webscripts stándard de Alfresco
     *
     * @return
     */
    public WebResource getWebResourceBase() {
        return getClient().resource(alfrescoServicesURL);
    }

    /**
     * Proporciona el mapa de parametros get común a todas las peticiones. Todas
     * ellas incluyen el ticket de Alfresco.
     *
     * @return
     * @throws ${package}.exception.CustomException
     */
    public MultivaluedMap<String, String> getDefaultQueryParams() throws CustomException {
        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add(PARAM_ALF_TICKET, getAlfTicket());
        return queryParams;
    }

    public String getAlfTicket() throws CustomException {
        if (alfTicket == null) {
            startConnection();
        }
        return alfTicket;
    }

    public static void setAlfTicket(String alfTicket) {
        JerseyServiceClientBase.alfTicket = alfTicket;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setAlfrescoURL(String alfrescoURL) {
        this.alfrescoURL = alfrescoURL;
    }

    public String getAlfrescoURL() {
        return alfrescoURL;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setAlfrescoServicesURL(String alfrescoServicesURL) {
        this.alfrescoServicesURL = alfrescoServicesURL;
    }

    public String getAlfrescoServicesURL() {
        return alfrescoServicesURL;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
