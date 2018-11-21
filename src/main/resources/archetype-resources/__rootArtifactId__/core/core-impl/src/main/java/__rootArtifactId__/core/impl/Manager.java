#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.${rootArtifactId}.core.impl;

import ${package}.${rootArtifactId}.icore.IManager;
import ${package}.${rootArtifactId}.idao.IDao;

/**
 *
 * @author ingapl
 */
public class Manager implements IManager {

    private IDao dao;

    
    
    public void setDao(IDao dao) {
        this.dao = dao;
    }

}
