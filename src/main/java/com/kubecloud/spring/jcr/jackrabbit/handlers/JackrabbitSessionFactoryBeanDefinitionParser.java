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

import org.w3c.dom.Element;

import com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser;
import com.kubecloud.spring.jcr.jackrabbit.JackrabbitSessionFactory;

/**
 * Classe representant le parseur de l'element session-factory Jackrabbit
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 5 févr. 2016 - 01:41:40
 */
public class JackrabbitSessionFactoryBeanDefinitionParser extends
		JcrSessionFactoryBeanDefinitionParser {
	
	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.handlers.JcrSessionFactoryBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		
		// On retourne la fabrique de session Jackrabbit
		return JackrabbitSessionFactory.class;
	}
}