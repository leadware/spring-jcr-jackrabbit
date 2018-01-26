package net.leadware.spring.jcr;

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

import javax.jcr.Credentials;
import javax.jcr.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe representant la fabrique de session JCR
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 5 févr. 2016 - 00:39:36
 */
public class JcrSessionFactory extends AbstractJcrSessionFactory implements SessionFactory {
	
	/**
	 * Loggeur
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Constructeur par defaut
	 */
	public JcrSessionFactory() {}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param repository	Depot
	 * @param workspaceName	Nom du Workspace
	 * @param credentials	Credentials
	 */
	public JcrSessionFactory(Repository repository, String workspaceName,
			Credentials credentials) {
		
		// Appel parent
		super(repository, workspaceName, credentials);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {}
}