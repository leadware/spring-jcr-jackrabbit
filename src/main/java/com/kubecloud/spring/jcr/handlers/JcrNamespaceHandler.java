package com.kubecloud.spring.jcr.handlers;

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
 * Classe representant un handler d'extension spring JCR 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 5 févr. 2016 - 01:16:24
 */
public class JcrNamespaceHandler extends NamespaceHandlerSupport {
	
	/**
	 * Session Element Name
	 */
	public static final String SESSION_ELEMENT_NAME = "session-factory";
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	@Override
	public void init() {
		
		// Enregistrement du parseur pour l'element repository
		registerBeanDefinitionParser(SESSION_ELEMENT_NAME, new JcrSessionFactoryBeanDefinitionParser());
	}
}