package net.leadware.spring.jcr.jackrabbit.handlers;

/*
 * #%L
 * spring-jcr Mojo
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2013 - 2018 Kube Cloud
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Classe representant un handler d'extension spring 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 4 févr. 2016 - 22:37:13
 */
public class JackrabbitNamespaceHandler extends NamespaceHandlerSupport {
	
	/**
	 * Repository Element Name
	 */
	static final String REPOSITORY_ELEMENT_NAME = "repository-factory";
	
	/**
	 * session-factory Element Name
	 */
	static final String SESSION_ELEMENT_NAME = "session-factory";
	
	/**
	 * ocm-factory Element Name
	 */
	static final String OCM_SESSION_ELEMENT_NAME = "ocm-session-factory";
	
	/**
	 * ocm-annotation-mapper Element Name
	 */
	static final String OCM_ANNOTATION_MAPPER_ELEMENT_NAME = "ocm-annotation-mapper-factory";
	
	/**
	 * ocm-digester-mapper Element Name
	 */
	static final String OCM_DIGESTER_MAPPER_ELEMENT_NAME = "ocm-digester-mapper-factory";
	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	@Override
	public void init() {
		
		// Enregistrement du parseur pour l'element repository
		registerBeanDefinitionParser(REPOSITORY_ELEMENT_NAME, new JackrabbitRepositoryBeanDefinitionParser());
		
		// Enregistrement du parseur pour l'element session-factory
		registerBeanDefinitionParser(SESSION_ELEMENT_NAME, new JackrabbitSessionFactoryBeanDefinitionParser());
		
		// Enregistrement du parseur pour l'element ocm-factory
		registerBeanDefinitionParser(OCM_SESSION_ELEMENT_NAME, new JackrabbitOcmSessionFactoryBeanDefinitionParser());
		
		// Enregistrement du parseur pour l'element ocm-annotation-mapper-factory
		registerBeanDefinitionParser(OCM_ANNOTATION_MAPPER_ELEMENT_NAME, new JackrabbitOcmAnnotationMapperFactoryBeanDefinitionParser());
		
		// Enregistrement du parseur pour l'element ocm-digester-mapper-factory
		registerBeanDefinitionParser(OCM_DIGESTER_MAPPER_ELEMENT_NAME, new JackrabbitOcmDigesterMapperFactoryBeanDefinitionParser());
	}
}