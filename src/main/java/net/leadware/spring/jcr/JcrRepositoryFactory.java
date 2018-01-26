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

import javax.jcr.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * Classe representant une fabrique de depot JCR
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 4 févr. 2016 - 22:41:29
 */
public abstract class JcrRepositoryFactory implements InitializingBean, DisposableBean, FactoryBean<Repository> {
	
	/**
	 * Loggeur
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Ressource contenant la configuration du depot JCR
	 */
	protected Resource configuration;

	/**
	 * Depot JCR
	 */
	protected Repository repository;

	/**
	 * Methode permettant de constrire la configuration du depot
	 * @throws Exception	Exception potentielle
	 */
	protected abstract void resolveConfigurationResource() throws Exception;

	/**
	 * Methode permettant de creer un depot JCR
	 * @return	Depot
	 * @throws Exception	Exception potentielle
	 */
	protected abstract Repository createRepository() throws Exception;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		
		// Construction de la configuration
		resolveConfigurationResource();
		
		// Creation du depot
		repository = createRepository();
	}

	/**
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public Repository getObject() throws Exception {
		
		// On retourne le depot
		return this.repository;
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class<?> getObjectType() {
		
		// On retourne la classe du depot
		return Repository.class;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		
		// On retourne true
		return true;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "configuration"
	 * @return valeur du champ "configuration"
	 */
	public Resource getConfiguration() {
	
		// On retourne le champ "configuration"
		return configuration;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "configuration"
	 * @param configuration Nouvelle valeur du champ "configuration"
	 */
	public void setConfiguration(Resource configuration) {
	
		// Mise a jour du champ "this.configuration"
		this.configuration = configuration;
	}
}
