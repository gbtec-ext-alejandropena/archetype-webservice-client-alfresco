#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
====================================================================================================
INGAPL - ${artifactId}
====================================================================================================

	Este es el código fuente del proyecto ${artifactId}	

CONTENIDO
---------

	1. ESTRUCTURA DEL PROYECTO
	2. INSTRUCCIONES DE COMPILACIÓN

1. ESTRUCTURA DEL PROYECTO
---------------------------
	El proyecto está compuesto por dos módulos diferenciados, el que hace referencia a alfresco,
	y el relacionado con el servicio web. Dentro de la parte de alfresco existe un archivo de README
	que explica con mayor detalle la estructura y configuración de esa parte del proyecto.

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


	Referente a la parte de ${artifactId}, el servicio web se divide en 3 módulos dao, core y facade. Los dos primeros generarán un .jar con la lógica de acceso a datos y negocio respectivamente, mientras que el último generará un WAR que será el desplegable con la implementación del servicio web.

	./alfresco
		Proyecto con el desarrollo asociado a alfresco. Consultar el readme incluido dentro de la carpeta alfresco
		para más info.

	./${artifactId}
		Proyecto que contiene el código para el desarrollo del servicio web.
		En el interior de esta carpeta se encuentra el pom.xml de ${artifactId}-modules, el cual unifica los módulos dao, core y facade.

	./dao
		Proyecto con la lógica de acceso a datos (consultas a alfresco). Toda lógica asociada al repositorio debería estar aislada en esta parte del código.
		Está compuesto por dos módulos, la interfaz y la implementación especifica para alfresco. Si se desease cambiar el gestor documental únicamente habría que desarrollar un DAO especifico.

	./core
		Incluye la lógica de negocio del servicio web. Está compuesto por el modulo de interfaz y el de implementación.

	./facade
		Código que genera el war de la aplicación, aquí se desarrollará la parte de fachada del servicio web.
	./dto
		Módulo que unifica objetos para el intercambio de datos entre las diferentes capas del proyecto (Data Transfer Objects)



2. TEST FRAMEWORKS
-------------------------------
	El proyecto incluye entre las librerias declaradas en el dependencyManagement central el framework para pruebas unitarias powermock (en integracion con mockito y junit).
        
        http://powermock.github.io/

        https://github.com/powermock/powermock/wiki/GettingStarted

        http://site.mockito.org/

        http://junit.org/junit4/


3. INSTRUCCIONES DE COMPILACIÓN
-------------------------------
	Generar javadoc

	${symbol_dollar} mvn clean javadoc:aggregate

	El proyecto puede compilarse de forma completa con una sola instrucción.
	En el propio trunk del proyecto se encuentra el pom.xml del proyecto ${artifactId}, donde se centraliza el proyecto. En este pom se definen las variables globales (como los numeros de versión de cada uno de los módulos, tanto de ${artifactId} como de alfresco, o la propia versión del repositorio), y además se definen las variables específicas para cada uno de los entornode de desarrollo.

	Los entornos disponibles son: DES, PRE y PRO De forma que al compilar el proyecto se debería indicar el perfil de compilación deseado, en caso no indicar ninguno tomará el valor por defecto, que es DES.
	
	${symbol_dollar} mvn clean install -PDES
	${symbol_dollar} mvn clean install -PPRE
	${symbol_dollar} mvn clean install -PPRO

	Si no se desea utilizar un perfil ya establecido, para levantar jetty contra un Alfresco propio, se debe tocar: ${artifactId}/facade/src/main/webapp/WEB-INF/applicationContext.xml

	En el pom.xml principal se pueden definir variables específicas para cada uno de estos entornos. Con estas instrucciones se compilará el proyecto al completo.

	[INFO] ------------------------------------------------------------------------
	[INFO] Reactor Summary:
	[INFO] ------------------------------------------------------------------------
	[INFO] ${artifactId} .............................................. SUCCESS [2.958s]
	[INFO] Alfresco ${artifactId} ..................................... SUCCESS [0.022s]
	[INFO] Alfresco ${artifactId} - modulos ........................... SUCCESS [0.016s]
	[INFO] Alfresco ${artifactId} - model ............................. SUCCESS [4.950s]
	[INFO] Alfresco ${artifactId} - services .......................... SUCCESS [0.499s]
	[INFO] Alfresco ${artifactId} - services - model .................. SUCCESS [1.324s]
	[INFO] Alfresco ${artifactId} - alfresco.war 4.1.5 ................ SUCCESS [1:06.991s]
	[INFO] ${artifactId} - modulos .................................... SUCCESS [0.011s]
	[INFO] ${artifactId} - core - modulos ............................. SUCCESS [0.011s]
	[INFO] ${artifactId} - core - interface ........................... SUCCESS [0.120s]
	[INFO] ${artifactId} - core - impl ................................ SUCCESS [0.151s]
	[INFO] ${artifactId} - dao - modulos .............................. SUCCESS [0.029s]
	[INFO] ${artifactId} - dao - interface ............................ SUCCESS [0.157s]
	[INFO] ${artifactId} - dao - impl alfresco ........................ SUCCESS [0.141s]
	[INFO] ${artifactId} - facade ..................................... SUCCESS [0.966s]



	También se puede compilar de forma individual cada uno de los subproyectos.

	Para facilitar el desarrollo se ha incluido el plugin de Jetty, permite levantar el servicio mediante la instrucción:

	${symbol_dollar} mvn -f ${artifactId}/facade/pom.xml jetty:run

	El servicio estará accesible en http://localhost:9090/service/${artifactId}s?wsdl



--------------------------------------------------------------