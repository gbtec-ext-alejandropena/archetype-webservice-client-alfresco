#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
DOCUMENTACIÓN MAVEN
===================

	Este proyecto emplea maven. Se puede consultar documentación al respecto en :
		http://wiki.alfresco.com/wiki/Managing_Alfresco_Lifecyle_with_Maven
		http://maven.alfresco.com/nexus/content/repositories/alfresco-docs/maven-alfresco-lifecycle/maven-alfresco-archetypes/maven-alfresco-extension-archetype/index.html
		./README-m2.txt

  IMPORTANTE : la compilación desde la raíz incluye en el alfresco.war los amps recién compilados
  incluidos en la carpeta modules. Pero hay que tener cuidado con compilar desde el directorio /alfresco-war
  ya que también genera un alfresco.war, pero en este caso los amps se los descarga ya compilados del repositorio de
  maven. Por esta razón SE RECOMIENDA GENERAR EL ALFRESCO.WAR DESDE EL DIRECTORIO RAÍZ, NO DESDE ÉSTE.
       
