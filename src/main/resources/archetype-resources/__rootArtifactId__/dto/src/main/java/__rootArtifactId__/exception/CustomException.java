#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.${rootArtifactId}.exception;

/**
 * Excepcion generica de la aplicacion ${artifactId}S
 *
 * @author ingapl
 */
public class CustomException extends Exception {

    private static final long serialVersionUID = 1496864227890149614L;

    private String codigo;
    private String mensaje;
    private Throwable wrappedException;

    public CustomException() {

    }

    public CustomException(String codigo, String mensaje, Throwable wrappedException) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.wrappedException = wrappedException;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Throwable getWrappedException() {
        return wrappedException;
    }

    public void setWrappedException(Throwable wrappedException) {
        this.wrappedException = wrappedException;
    }

    @Override
    public String toString() {
        String exceptionMessage = "NULL";
        if (wrappedException != null) {
            exceptionMessage = wrappedException.toString();
            if (exceptionMessage.contains("alf_ticket")) {
                exceptionMessage = wrappedException.getClass().getName();
            }
        }
        return "CustomException{" + "codigo=" + codigo + ", mensaje=" + mensaje + ", wrappedException=" + exceptionMessage + '}';
    }

}
