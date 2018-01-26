package net.leadware.spring.jcr.handlers;

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

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import net.leadware.spring.jcr.JcrSessionFactory;

/**
 * Classe representant le parseur de l'element repository JCR 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 5 févr. 2016 - 01:18:27
 */
public class JcrSessionFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	
	/**
	 * Repository REF attribute
	 */
	static final String REPOSITORY = "repository";
	
	/**
	 * workspace-name attribute
	 */
	static final String WORKSPACE_NAME = "workspace-name";
	
	/**
	 * credentials REF attribute
	 */
	static final String CREDENTIALS = "credentials";
	
	/**
	 * nodeTypeDefinitions REF attribute
	 */
	static final String NODE_TYPE_DEFINITIONS_REF = "node-type-definitions";
	
	
	/**
	 * Class Repository attribute
	 */
	static final String CF_REPOSITORY = "repository";
	
	/**
	 * Class workspace-name attribute
	 */
	static final String CF_WORKSPACE_NAME = "workspaceName";
	
	/**
	 * Class credentials attribute
	 */
	static final String CF_CREDENTIALS = "credentials";

	/**
	 * nodeTypeDefinitions REF attribute
	 */
	static final String CF_NODE_TYPE_DEFINITIONS_REF = "nodeTypeDefinitions";
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		
		// On retourne la fabrique de session JCR
		return JcrSessionFactory.class;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element elem, BeanDefinitionBuilder builder) {
		
		// Obtention de la valeur de l'attribut repository
		String repository = elem.getAttribute(REPOSITORY) == null ? "" : elem.getAttribute(REPOSITORY).trim();
		
		// Si le repository est non vide
		if (!repository.isEmpty()) {
			
			// Ajout de la valeur comme propriete de reference la fabrique
			builder.addPropertyReference(CF_REPOSITORY, repository);
			
		} else {
			
			// On leve une exception
			throw new NullPointerException(String.format("La balise '%1$s' doit specifier une valeur pour l'attribut '%2$s'", JcrNamespaceHandler.SESSION_ELEMENT_NAME, REPOSITORY));
		}
		
		// Obtention de la valeur de l'attribut credentials
		String credentials = elem.getAttribute(CREDENTIALS) == null ? "" : elem.getAttribute(CREDENTIALS).trim();
		
		// Si le credentials est non vide
		if (!credentials.isEmpty()) {
			
			// Ajout de la valeur comme propriete de reference la fabrique
			builder.addPropertyReference(CF_CREDENTIALS, credentials);
			
		} else {
			
			// On leve une exception
			throw new NullPointerException(String.format("La balise '%1$s' doit specifier une valeur pour l'attribut '%2$s'", JcrNamespaceHandler.SESSION_ELEMENT_NAME, CREDENTIALS));
		}

		// Obtention de la valeur de l'attribut workspace-name
		String workspaceName = elem.getAttribute(WORKSPACE_NAME) == null ? "" : elem.getAttribute(WORKSPACE_NAME).trim();
		
		// Si le workspaceName est non vide
		if (!workspaceName.isEmpty()) {
			
			// Ajout de la valeur comme propriete a la fabrique
			builder.addPropertyValue(CF_WORKSPACE_NAME, workspaceName);
			
		}
		
		// Obtention de la valeur de l'attribut node-type-definitions
		String nodeTypedefinitions = elem.getAttribute(NODE_TYPE_DEFINITIONS_REF) == null ? "" : elem.getAttribute(NODE_TYPE_DEFINITIONS_REF).trim();
		
		// Si le nodeTypedefinitions est non vide
		if (!nodeTypedefinitions.isEmpty()) {
			
			// Ajout de la valeur comme propriete a la fabrique
			builder.addPropertyReference(CF_NODE_TYPE_DEFINITIONS_REF, nodeTypedefinitions);
			
		}
	}
}
