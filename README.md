
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


## Estructura generada
La estructura de proyecto que se generará está compuesta por dos módulos diferenciados, el que hace referencia a alfresco,
y el relacionado con el servicio web.

	.
	|-- alfresco
	|	|-- alfresco-war
	|	|-- config
	|	`-- modulos
	|		|-- ${artifactId}-model
	|		|-- ${artifactId}-services
	|		`-- ${artifactId}-services-model
	`-- ${artifactId}
		|-- dao
		|	|-- dao-interface
		|	`-- dao-impl-alfresco
		|-- core
		|	|-- core-interface
		|	`-- core-impl
		|-- facade
		`-- dto
#### Servicio Web
Referente a la parte de ${artifactId}, el servicio web se divide en 3 módulos dao, core y facade. Los dos primeros generarán un .jar con la lógica de acceso a datos y negocio respectivamente, mientras que el último generará un WAR que será el desplegable con la implementación del servicio web.

- ./${artifactId}
Proyecto que contiene el código para el desarrollo del servicio web.	En el interior de esta carpeta se encuentra el pom.xml de ${artifactId}-modules, el cual unifica los módulos dao, core y facade.

- ./dao
Proyecto con la lógica de acceso a datos (consultas a alfresco). Toda lógica asociada al repositorio debería estar aislada en esta parte del código. Está compuesto por dos módulos, la interfaz y la implementación especifica para alfresco. Si se desease cambiar el gestor documental únicamente habría que desarrollar un DAO especifico.

- ./core
Incluye la lógica de negocio del servicio web. Está compuesto por el modulo de interfaz y el de implementación.

- ./facade
Código que genera el war de la aplicación, aquí se desarrollará la parte de fachada del servicio web.
	
- ./dto
Módulo que unifica objetos para el intercambio de datos entre las diferentes capas del proyecto (Data Transfer Objects)



#### Alfresco
El proyecto está concebido como una agregación de módulos independientes. Esto significa	que cada uno de los módulos evoluciona de manera independiente y es en proceso de compilación cuando se realiza la integración de todos ellos. (ver https://maven.apache.org/guides/introduction/introduction-to-the-pom.html#Project_Aggregation)

Los módulos del proyecto están estructurados del siguiente modo :

		|-- alfresco-war
		`-- modulos        
			|-- ${parentArtifactId}-model
			|-- ${parentArtifactId}-services
			`-- ${parentArtifactId}-services-model
		    
	  
	


 - ./alfresco-war
En este directorio del proyecto se realizan las customizaciones génericas de Alfresco (ej: la página de login). Tras la compilación todos los módulos de la carpeta ./modules/... se integraran en el presente war; con lo que el war generado en ./alfresco-war/target/alfresco.war tendrá además de las customizaciones génericas los módulos del proyecto incluidos en él.

 - ./modulos
Incluye cada uno de los módulos que se desarrollan en este proyecto para integrar con Alfresco

- ./${parentArtifactId}-model
Modulo AMP que incluye el modelo de alfresco utilizado por ${parentArtifactId}

- ./${parentArtifactId}-services
Modulo AMP que incluye los servicios REST utilizados por el servicio web de ${parentArtifactId}

- ./${parentArtifactId}-services-model
Modulo JAR que agrupa las clases necesarias para una comunicación entre la aplicación y servicios web. Por lo general deberá importarse tanto en el modulo de servicios como en el dao de implementación de acceso a alfresco.
