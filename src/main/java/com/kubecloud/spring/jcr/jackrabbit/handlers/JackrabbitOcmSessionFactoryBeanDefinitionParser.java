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
import org.w3c.dom.Element;

import com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser;
import com.kubecloud.spring.jcr.jackrabbit.ocm.JackrabbitOcmSessionFactory;

/**
 * Classe representant le parseur de l'element repository Jackrabbit OCM
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 5 févr. 2016 - 01:41:40
 */
public class JackrabbitOcmSessionFactoryBeanDefinitionParser extends JcrSessionFactoryBeanDefinitionParser {
	
	/**
	 * XML mapper attribute
	 */
	static final String MAPPER = "mapper";
	
	/**
	 * Class mapper attribute
	 */
	static final String CF_MAPPER = "mapper";
	
	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		
		// On retourne la fabrique de session Jackrabbit OCM
		return JackrabbitOcmSessionFactory.class;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element elem, BeanDefinitionBuilder builder) {
		
		// Appel parent
		super.doParse(elem, builder);
		
		// Obtention de la valeur de l'attribut mapper
		String mapper = elem.getAttribute(MAPPER) == null ? "" : elem.getAttribute(MAPPER).trim();
		
		// Si le mapper est vide
		if (mapper.isEmpty()) {
			
			// Si la liste des packages est vide
			throw new NullPointerException(String.format("La balise '%1$s' doit specifier une valeur pour l'attribut '%2$s'", JackrabbitNamespaceHandler.SESSION_ELEMENT_NAME, MAPPER));
			
		} else {

			// Ajout de la valeur comme propriete de reference la fabrique
			builder.addPropertyReference(CF_MAPPER, mapper);
		}
	}
}