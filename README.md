
# archetype-webservice-client-alfresco
Arquetipo para la generación de un servicio web SOAP (CXF) cliente del gestor documental Alfresco

## Compilación del Arquetipo

    $ mvn clean install

Además de generar el .jar del arquetipo, creará el fichero archetype-catalog.xml en `~/.m2`, donde se listará el arquetipo disponible.

## Uso del Arquetipo
Teniendo el arquetipo disponible en el repositorio, para poder utilizarlo los pasos a seguir serían:
#### 1 - Incluír arquetipo en el catálogo local
En caso de que no exista, debe copiarse el fichero 
  

    $ config/archetype-catalog.xml 

en 

    $ (mvn 2) /home/[usuario]/.m2
    $ (mvn 3) /home/[usuario]/.m2/repository

Si el fichero ya existe, simplemente debería añadirse el arquetipo al listado existente.
	
#### 2 - Incluir autenticación para la descarga del arquetipo
Para autenticarse y descargar el arquetipo (en caso de tenerlo alojado en un repositorio), maven utiliza el id de repositorio [archetypeArtifactId]-repo: 
http://maven.apache.org/archetype/maven-archetype-plugin/faq.html#authentication

Por tanto, en el fichero `/home/[usuario]/.m2` debe editarse el fichero `settings.xml` para incluir este id de repositorio webservice-repo, 
de forma similar al siguiente ejemplo:

    <servers>
            <server>
    	<id>webservice-client-alfresco-repo</id>
    	<username>usuario</username>
    	<password>contrasena</password>
            </server>
    ...



#### 3 - Generar la estructura del proyecto

    $ mvn archetype:generate

Listará los arquetipos disponibles, al final de la lista aparecerán los arquetipos locales. Debe seleccionarse:

    - com.ingapl.archetype:webservice (Ingapl Archetype - Servicio Web)

Se indican los parametros solicitados (groupId, artifactId, version) y la estructura se generará automáticamente. 

#### 3 - Dar de alta en jenkins y sonar
El proyecto viene preparado para ser dado de alta en ambas aplicaciones. 
El fichero de configuración `sonar.properties` se genera en `[aplicacion]/target/sonar/sonar.properties`.

En jenkins debe configurarse el SonarQube Scanner con dicha ruta: 

`Path to project properties = target\sonar\sonar.properties`

También debe configurarse el project key resultante en 

`Acciones para ejecutar después - Quality Gates.` 

De esta forma si sonar detecta errores graves, jenkins lo notificará a los correos configurados.
