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

import com.kubecloud.spring.jcr.jackrabbit.ocm.DigesterOcmMapperFactory;

/**
 * Classe representant le parseur de l'element digester-mapper Jackrabbit OCM 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 12 févr. 2016 - 10:00:05
 */
public class JackrabbitOcmDigesterMapperFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	
	/**
	 * XML mappings attribute
	 */
	static final String MAPPINGS = "mappings";

	/**
	 * XML validate attribute
	 */
	static final String VALIDATE = "validate";
	
	/**
	 * Class mappings attribute
	 */
	static final String CF_MAPPINGS = "mappings";

	/**
	 * Class validate attribute
	 */
	static final String CF_VALIDATE = "validate";
	
	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		
		// On retourne la fabrique de mapper digester Jackrabbit OCM
		return DigesterOcmMapperFactory.class;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element elem, BeanDefinitionBuilder builder) {
		
		// Obtention de la valeur de l'attribut mappings
		String mappings = elem.getAttribute(MAPPINGS) == null ? "" : elem.getAttribute(MAPPINGS).trim();
		
		// Si le mappings est vide
		if (mappings.isEmpty()) {
			
			// Si la liste des mappings est vide
			throw new NullPointerException(String.format("La balise '%1$s' doit specifier une valeur pour l'attribut '%2$s'", JackrabbitNamespaceHandler.OCM_DIGESTER_MAPPER_ELEMENT_NAME, MAPPINGS));
			
		} else {

			// Ajout de la valeur comme propriete de reference la fabrique
			builder.addPropertyReference(CF_MAPPINGS, mappings);
		}

		// Obtention de la valeur de l'attribut validate
		String validate = elem.getAttribute(VALIDATE) == null ? "" : elem.getAttribute(VALIDATE).trim();
		
		// Si le mappings est non vide
		if (!mappings.isEmpty()) {

			// Ajout de la valeur comme propriete de reference la fabrique
			builder.addPropertyValue(CF_VALIDATE, validate);
		}
	}
}