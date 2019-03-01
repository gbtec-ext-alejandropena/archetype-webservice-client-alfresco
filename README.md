
# archetype-webservice-client-alfresco
Archetype for the generation of a SOAP web service client of an Alfresco instance using Apache CXF

## Archetype compilation

    $ mvn clean install

In addition to generating the .jar archetype, it will create the file archetype-catalog.xml in `~/.m2`, where the available archetype will be listed.

## Archetype use
Having the archetype available in the repository, in order to use it, the steps to follow would be:
#### 1 - Include archetype in the local catalog
If it does not exist, next file must be copied
  

    $ config/archetype-catalog.xml 

in maven folder:

    $ (mvn 2)/home/[usuario]/.m2
    $ (mvn 3)/home/[usuario]/.m2/repository

If the file already exists, you should simply add the archetype to the existing list.
	
#### 2 - Include authentication to download the archetype
To authenticate and download the archetype (if it is hosted in a repository), maven uses the repository id [archetypeArtifactId]-repo: 
http://maven.apache.org/archetype/maven-archetype-plugin/faq.html#authentication

Therefore, in the file `/home/[user]/.m2`, the` settings.xml` file must be edited to include this repository id webservice-repo, like the following example:

    <servers>
            <server>
    	<id>webservice-client-alfresco-repo</id>
    	<username>usuario</username>
    	<password>contrasena</password>
            </server>
    ...



#### 3 - Generate the project structure

    $ mvn archetype:generate

It will list the available archetypes, the local archetypes will appear. 
It must be selected:

    - com.ingapl.archetype:webservice (Ingapl Archetype - Servicio Web)

The requested parameters should be indicated (groupId, artifactId, version) and the structure will be generated automatically.

#### 3 - Register in jenkins and sonar
The project is ready to be registered in both applications.
The `sonar.properties` configuration file is generated in` [application]/target/sonar/sonar.properties`.

In jenkins the SonarQube Scanner must be configured with this route:

`Path to project properties = target\sonar\sonar.properties`

The resulting project key must also be configured in:

`Actions to execute after - Quality Gates.` 

This way if sonar detects serious errors, jenkins will notify you to the configured emails.


## Generated structure
The project structure that will be generated is composed of two different modules, one that refers to alfresco,
and another related to the web service.

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
		
#### Web Service
Regarding the part of $ {artifactId}, the web service is divided into 3 modules dao, core and facade. The first two will generate a .jar with the logic of access to data and business respectively, while the last one will generate a WAR that will be the drop-down with the implementation of the web service.

- ./${artifactId}

Project that contains the code for the development of the web service. Inside this folder is the pom.xml of $ {artifactId} -modules, which unifies the dao, core and facade modules.

- ./dao

Project with the logic of access to data (requests to alfresco). Any logic associated with the repository should be isolated in this part of the code. It consists of two modules, the interface and the specific implementation for alfresco. If you want to change the document manager, you only need to develop a specific DAO.

- ./core

Includes the business logic of the web service. It is composed by the interface module and the implementation module.

- ./facade

Code that generates the war of the application, here the front part of the web service will be developed.
	
- ./dto

Module that unifies objects for the exchange of data between the different layers of the project (Data Transfer Objects)



#### Alfresco
The project is conceived as an aggregation of independent modules. This means that each of the modules evolves independently and is in the process of compilation when the integration of all of them is done. (see https://maven.apache.org/guides/introduction/introduction-to-the-pom.html#Project_Aggregation)

Modules of the project are structured as follows:

		|-- alfresco-war
		`-- modulos        
			|-- ${parentArtifactId}-model
			|-- ${parentArtifactId}-services
			`-- ${parentArtifactId}-services-model
		    
	  
	


 - ./alfresco-war
 
Alfresco's generic customizations are made in this directory (eg: the login page). After the compilation, all the modules in the folder ./modules/ will be integrated into the present war. So the war generated in ./alfresco-war/target/alfresco.war will have in addition to the generic customizations the project modules included in it.

 - ./modulos
 
Includes each one of the modules developed in this project to integrate with Alfresco

- ./${parentArtifactId}-model

AMP module that includes the alfresco model used by $ {parentArtifactId}

- ./${parentArtifactId}-services

AMP module that includes the REST services consumed by the $ {parentArtifactId} web service

- ./${parentArtifactId}-services-model

JAR module that groups the necessary classes for communication between the application and web services. In general, it should be imported both in the service module and in the alfresco implementation dao
