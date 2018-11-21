#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.dao.impl.alfresco.interceptor;

import ${package}.${rootArtifactId}.dto.DatosLopdDTO;
import ${package}.${rootArtifactId}.dao.impl.alfresco.jersey.JerseyServiceClientBase;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import ${package}.${rootArtifactId}.exception.CustomException;
import ${package}.${rootArtifactId}.services.error.ErrorCodes;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.util.Date;

/**
 * Interceptor encargado de la gestion de log LOPD, renovacion de tickets de
 * acceso e identificacion de las excepciones recibidas desde el repositorio
 *
 * @author ingapl
 */
public class AlfrescoDaoInterceptor implements MethodInterceptor {

    private JerseyServiceClientBase jerseyService;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return procesarPeticion(methodInvocation, true);
    }

    /**
     * Procesa la peticion interceptada. Mediante el flag renovar se indica si
     * se quiere intentar la renovacion del ticket en caso de estar caducado
     *
     * @param methodInvocation
     * @param renovar
     * @return
     * @throws Throwable
     */
    private Object procesarPeticion(MethodInvocation methodInvocation, boolean renovar) throws Throwable {
        //Date fini = new Date();
        Object object = null;
        ErrorResponse errorResponse = new ErrorResponse();

        try {
            object = methodInvocation.proceed();
        } catch (CustomException ex) {
            errorResponse = identificarCodigoError(Integer.parseInt(ex.getCodigo()), ex);
            throw errorResponse.exception;
        } catch (UniformInterfaceException ex) {
            if (renovar && ex.getResponse().getStatus() == ErrorCodes.NO_AUTORIZADO) {
                renovarTicket(errorResponse);
                object = procesarPeticion(methodInvocation, false);
            } else {
                errorResponse = identificarCodigoError(ex.getResponse().getStatus(), ex);
                throw errorResponse.exception;
            }
        } finally {
            //Revisar metodo generarInfoLogLopd antes de utilizar escribirLogLopd
            //escribirLogLopd(fini, methodInvocation, errorResponse);
        }
        return object;
    }

    /**
     * Renueva el ticket de conexion a alfresco
     *
     * @param methodInvocation
     * @param errorResponse
     * @return
     * @throws Throwable
     */
    private Object renovarTicket(ErrorResponse errorResponse) throws CustomException {
        Object object = null;
        try {
            jerseyService.startConnection();
        } catch (Exception e) {
            errorResponse.codeError = "ADI000";
            errorResponse.exception = new CustomException("ADI000", "Problema renovando el ticket y relanzando la peticion", e);
        }
        return object;
    }

    /**
     * Identifica un codigo de error, devolviendo el ErrorResponse asociado.
     * Deberían controlarse todas las situaciones de error para que entre por el
     * default en el menor numero de ocasiones posibles.
     *
     * @param errorCode
     * @param ex
     * @return
     */
    private ErrorResponse identificarCodigoError(int errorCode, Throwable ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (errorCode) {
            default:
                errorResponse.codeError = "ADI001";
                errorResponse.exception = new CustomException(errorResponse.codeError, "Se ha producido un error interno en el repositorio, debe consultarse con un administrador de alfresco", ex);
                break;
            case ErrorCodes.NO_AUTORIZADO:
                errorResponse.codeError = "ADI002";
                errorResponse.exception = new CustomException(errorResponse.codeError, "No ha sido posible autenticarse contra el repositorio de alfresco", ex);
                break;
        }
        return errorResponse;
    }

    /**
     * Genera el log de LOPD, y lanza la excepcion de errorResponse en caso
     * exista un error.
     *
     * @param fini
     * @param methodInvocation
     * @param codError
     * @throws CustomException
     */
    private void escribirLogLopd(Date fini, MethodInvocation methodInvocation, ErrorResponse errorResponse) throws CustomException {
        String name = methodInvocation.getMethod().getName();
        InfoLogLopd infoLogLopd = generarInfoLogLopd(name, methodInvocation);

        if (errorResponse.codeError == null) {
            //LOG LOPD ERROR
        } else {
            //LOG LOPD
        }
    }

    /**
     * Genera el objeto InfoLogLopd
     *
     * @param name
     * @param methodInvocation
     */
    private InfoLogLopd generarInfoLogLopd(String name, MethodInvocation methodInvocation) throws CustomException {
        InfoLogLopd infoLogLopd = new InfoLogLopd();
        //Generar la informacion necesaria de LOPD en base al metodo
        return infoLogLopd;
    }

    /**
     * Clase interna para empaquetar toda la info de LOPD necesaria para el log
     */
    private static class InfoLogLopd {



    }

    /**
     * Clase interna para agrupar la info de error generada
     */
    private static class ErrorResponse {

        private CustomException exception;
        private String codeError;
    }

    public void setJerseyService(JerseyServiceClientBase jerseyService) {
        this.jerseyService = jerseyService;
    }

}
