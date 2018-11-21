#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.dto;
/**
 * DTO asociado a los datos LOPD
 *
 * @author ingapl
 */
public class DatosLopdDTO {

    private String login;
    private String idAplicacion;
    private String versionAplicacion;
    private String ip;

    public DatosLopdDTO() {

    }

    public DatosLopdDTO(String login, String idAplicacion, String versionAplicacion, String ip) {
        this.login = login;
        this.idAplicacion = idAplicacion;
        this.versionAplicacion = versionAplicacion;
        this.ip = ip;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(String idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public String getVersionAplicacion() {
        return versionAplicacion;
    }

    public void setVersionAplicacion(String versionAplicacion) {
        this.versionAplicacion = versionAplicacion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "DatosLopdDTO{" + "login=" + login + ", idAplicacion=" + idAplicacion + ", versionAplicacion=" + versionAplicacion + ", ip=" + ip + '}';
    }

}
