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

import com.kubecloud.spring.jcr.jackrabbit.ocm.AnnotationOcmMapperFactory;

/**
 * Classe representant le parseur de l'element annotation-mapper Jackrabbit OCM 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 12 févr. 2016 - 10:00:05
 */
public class JackrabbitOcmAnnotationMapperFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	
	/**
	 * XML packages attribute
	 */
	static final String PACKAGES = "packages";

	/**
	 * XML packages-list attribute
	 */
	static final String PACKAGES_LIST = "packages-list";
	
	/**
	 * Class packages attribute
	 */
	static final String CF_PACKAGES = "packages";

	/**
	 * Class packages-list attribute
	 */
	static final String CF_PACKAGES_LIST = "packagesList";
	
	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		
		// On retourne la fabrique de mapper annotation Jackrabbit OCM
		return AnnotationOcmMapperFactory.class;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element elem, BeanDefinitionBuilder builder) {
		
		// Obtention de la valeur de l'attribut packages
		String packages = elem.getAttribute(PACKAGES) == null ? "" : elem.getAttribute(PACKAGES).trim();
		
		// Obtention de la valeur de l'attribut packages-list
		String packagesList = elem.getAttribute(PACKAGES_LIST) == null ? "" : elem.getAttribute(PACKAGES_LIST).trim();
		
		// Si le packages est vide
		if (packages.isEmpty()) {
			
			// Si la liste des packages est vide
			if(packagesList.isEmpty()) throw new NullPointerException(String.format("La balise '%1$s' doit specifier une valeur pour l'attribut '%2$s'", JackrabbitNamespaceHandler.OCM_ANNOTATION_MAPPER_ELEMENT_NAME, PACKAGES));
			
		} else {

			// Ajout de la valeur comme propriete de reference la fabrique
			builder.addPropertyValue(CF_PACKAGES, packages);
		}
		
		// Si la liste des packages est non vide
		if(!packagesList.isEmpty()) {

			// Ajout de la valeur comme propriete de reference la fabrique
			builder.addPropertyReference(CF_PACKAGES_LIST, packagesList);
		}
	}
}