#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
ABSTRACT
--------
	
	Este es el código fuente del modulo de alfresco perteneciente al proyecto para ${artifactId}	
	
CONTENIDO
---------

	1. ESTRUCTURA DEL PROYECTO
	2. INSTRUCCIONES DE CONFIGURACIÓN
		2.1 CONFIGURACIÓN DEL ALFRESCO.WAR
			2.1.a Extender la configuración de Spring
			2.1.b Sobreescribir la configuración de Spring
		2.2 CONFIGURACIÓN DEL LOG		
		2.3 CONFIGURACIÓN DEL WEBCIENT
			2.3.a Configuración de idioma directamente en el war
			2.3.b Añadir idiomas adicionales mediante amps
	3. INSTRUCCIONES DE COMPILACIÓN	
	
1. ESTRUCTURA DEL PROYECTO
---------------------------

	El proyecto está concebido como una agregación de módulos independientes. Esto siginifica
	que cada uno de los módulos evoluciona de manera independiente y es en proceso de compilación
	cuando se realiza la integración de todos ellos. 
	(ver http://maven.apache.org/guides/introduction/introduction-to-the-pom.html${symbol_pound}Project_Aggregation)
	
	Los módulos del proyecto están estructurados del siguiente modo :

		|-- alfresco-war
		`-- modulos        
			|-- ${parentArtifactId}-model
			|-- ${parentArtifactId}-services
			`-- ${parentArtifactId}-services-model
		    
	----------------    
	
	./alfresco-war 		
		En este directorio del proyecto se realizan las customizaciones génericas de Alfresco (ej: la página de login). 
		Tras la compilación todos los módulos de la carpeta ./modules/... se integraran en el presente war; con lo que
		el war generado en ./alfresco-war/target/alfresco.war tendrá además de las customizaciones génericas los 
		módulos del proyecto incluidos en él.
	
	./modulos 
		Incluye cada uno de los módulos que se desarrollan en este proyecto para integrar con Alfresco

	./${parentArtifactId}-model
		Modulo AMP que incluye el modelo de alfresco utilizado por ${parentArtifactId}

	./${parentArtifactId}-services
		Modulo AMP que incluye los servicios REST utilizados por el servicio web de ${parentArtifactId}

	./${parentArtifactId}-services-model
		Modulo JAR que agrupa las clases necesarias para una comunicación entre la aplicación y servicios web. Por lo general deberá importarse tanto en el modulo de servicios como en el dao de implementación de acceso a alfresco.
	

2. INSTRUCCIONES DE CONFIGURACIÓN
---------------------------------

	2.1 CONFIGURACIÓN DEL ALFRESCO.WAR			
		
		2.1.a Extender la configuración de Spring
		------------------------------------------
		Para extender la configuración de Spring con nuestros propios beans es suficiente con definirlos en 
		src/main/resources/alfresco/extension/XXX-context.xml
		
		
		2.1.b Sobreescribir la configuración de Spring
		-----------------------------------------------
		Es posible sobreescribir los beans de alfresco, aunque es una práctica que debe hacerse con cuidado. Para ello basta con
		redefinir el bean - poniendole el mismo id que tiene el original de Alfresco - en el archivo 
		src/main/resources/alfresco/extension/custom-repository-context.xml
				
	
	2.2	CONFIGURACIÓN DEL LOG
	
		El log general se configura en src/main/resources/log4.properties. En este archivo se definen los
		log4j.appender y demás opciones de configuración del log; además de los logs de las librerias que
		vienen con alfresco 'out of the box'.
		Cada uno de los módulos es responsable de la gestión de los logs de sus clases, que se definen en
		modules/modulo_XXXX/src/main/config/log4.properties. 
		
	
	2.3 CONFIGURACIÓN DEL WEBCLIENT
	
		La configuración general del webclient es gestionada por src/main/resources/web-client-config-custom.xml
	
		2.3.a Configuración de idioma directamente en el war
		----------------------------------------------------
		Alfresco viene en inglés por defecto. Para añadir otros idiomas se deben añadir a la sección languajes
		del src/main/resources/alfresco/extension/web-client-config-custom.xml el idioma correspondiente :
		
			<config evaluator="string-compare" condition="Languages" replace="true" >
                            <languages>
                                <language locale="es_ES">Español</language>
                                <language locale="en_US">English</language>
                            </languages>
                        </config>
    		
                También se deben incluir los XXX_es_ES.properties correspondientes en src/main/resources/alfresco/messages. 
                Dichos properties deben tener el mismo basename que los que trae alfresco añadiendoles como sufijo los códigos de
                locale. Por ejemplo, alfresco trae un content-model.properties y un content-model_en_US.properties; debemos proporcionar
                el archivo de traducción content-model_es_ES.properties

	2.4 INCLUSION DE NUEVOS MÓDULOS

		Para la inclusión de nuevos módulos deberá editarse el pom.xml del proyecto "modules" (trunk/alfresco/modules/pom.xml) 
		e incluir en el listado de módulos la localización del nuevo módulo. Por ejemplo, si el nuevo módulo se crea bajo la carpeta "nuevo-modulo" a la misma altura que xunta-model:

    		    	<modules>
        			<module>old-model</module>
        			<module>nuevo-modulo</module>
    			</modules>

		Las dependencias relacionadas con alfresco son gestionadas desde el propio pom de "modules" mediante un
		dependencyManagement, de igual forma que los plugins necesarios para la compilación.
    	

3. INSTRUCCIONES DE COMPILACIÓN
-------------------------------

	El proyecto está pensado para que se pueda compilar todo con una única instrucción.

		${symbol_dollar} mvn clean install
		
	Esta instrucción lleva a cabo las siguientes operaciones :
	
		1. Genera los amp correspondientes para cada uno de los modulos en la carpeta
		   ./modules. 
		
		2. Genera el alfresco.war (con todas las extensiones) que será finalmente 
		   distribuido, para lo cual :
		
				2.1. Se descarga del repositorio de maven el war de alfresco "limpio". 
				2.2. Integra en el alfresco.war (descargado en el punto 2.1) las customizaciones genéricas 
				 	 de alfresco contenidas en la carpeta ./alfresco-war
				2.3. Integra en el alfresco.war (resultado del punto 2.2) los modulos desarrollados 
				 	 ( los amps generados en el punto 1)
		
	Como resultado de la compilación se obtiene :
		
		1.  En las respectivas carpetas ./modules/${symbol_dollar}{nombre_del_módulo}/target, el amp 
			de cada uno de los módulos listos para ser instalados por separado con la 
			herramienta alfresco-mmt.jar
		2.  En ./alfresco-war/target/alfresco.war el war de alfresco con todas las customizaciones y
			módulos fruto del presente proyecto.

