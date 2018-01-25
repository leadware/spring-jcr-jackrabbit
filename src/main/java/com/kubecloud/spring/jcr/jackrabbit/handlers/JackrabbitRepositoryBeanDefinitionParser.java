package com.kubecloud.spring.jcr.jackrabbit.handlers;

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

import com.kubecloud.spring.jcr.jackrabbit.JackrabbitRepositoryFactory;

/**
 * Classe representant le parseur de l'element repository
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 4 févr. 2016 - 22:38:15
 */
public class JackrabbitRepositoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	
	/**
	 * configuration REF attribute
	 */
	static final String CONFIGURATION_REF = "configuration-ref";
	
	/**
	 * configuration file attribute
	 */
	static final String CONFIGURATION_FILE = "configuration-file";
	
	/**
	 * Home attribute
	 */
	static final String HOME = "home";

	/**
	 * Class configuration attribute
	 */
	static final String CF_CONFIGURATION_REF = "repositoryConfig";
	
	/**
	 * Class configuration file attribute
	 */
	static final String CF_CONFIGURATION_FILE = "configuration";

	/**
	 * Class Home attribute
	 */
	static final String CF_HOME = "homeDir";

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		
		// On retourne la fabrique de repository
		return JackrabbitRepositoryFactory.class;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element elem, BeanDefinitionBuilder builder) {
		
		// Obtention de la valeur de l'attribut configuration
		String configuration = elem.getAttribute(CONFIGURATION_FILE) == null ? "" : elem.getAttribute(CONFIGURATION_FILE).trim();

		// Obtention de l'attribut referentiel repositoryConfig
		String configurationRef = elem.getAttribute(CONFIGURATION_REF) == null ? "" : elem.getAttribute(CONFIGURATION_REF).trim();
		
		// Si la configuration est non vide
		if (!configuration.isEmpty()) {
			
			// Ajout de la configuration comme propriete de la fabrique
			builder.addPropertyValue(CF_CONFIGURATION_FILE, configuration);
			
		} else if(configurationRef.isEmpty()) {
			
			// On leve une exception
			throw new NullPointerException(String.format("La balise '%1$s' doit specifier une valeur pour l'attribut '%2$s'", JackrabbitNamespaceHandler.REPOSITORY_ELEMENT_NAME, CONFIGURATION_FILE));
		}
		
		// Obtention de la valeur de l'attribut home
		String homeDir = elem.getAttribute(HOME) == null ? "" : elem.getAttribute(HOME).trim();
		
		// Si le home est non vide
		if (!homeDir.isEmpty()) {
			
			// Ajout du home comme propriete de la fabrique
			builder.addPropertyValue(CF_HOME, homeDir);
		}
		
		// Si sa valeur est non vide
		if (!configurationRef.isEmpty()) {
			
			// Ajout de la valeur comme propriete de reference a la fabrique
			builder.addPropertyReference(CF_CONFIGURATION_REF, configurationRef);
		}
	}
}