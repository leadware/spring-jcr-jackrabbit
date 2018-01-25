package com.kubecloud.spring.jcr.jackrabbit;

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

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

import com.kubecloud.spring.jcr.JcrRepositoryFactory;

/**
 * Classe representant la fabrique de depot Jackrabbit
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 4 févr. 2016 - 22:57:45
 */
public class JackrabbitRepositoryFactory extends JcrRepositoryFactory {
	
	/**
	 * Chemin par defaut du fichier de configuration du depot 
	 */
	private static final String DEFAULT_CONF_FILE = "repository.xml";
	
	/**
	 * Chemin par defaut du repertoire de travail 
	 */
	private static final String DEFAULT_REP_DIR = ".";
	
	/**
	 * Repertoire de travail
	 */
	private Resource homeDir;
	
	/**
	 * Configuration du depot
	 */
	private RepositoryConfig repositoryConfig;
	
	
	/**
	 * Methode permettant d'obtenir la valeur du champ "homeDir"
	 * @return valeur du champ "homeDir"
	 */
	public Resource getHomeDir() {
	
		// On retourne le champ "homeDir"
		return homeDir;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "homeDir"
	 * @param homeDir Nouvelle valeur du champ "homeDir"
	 */
	public void setHomeDir(Resource homeDir) {
	
		// Mise a jour du champ "this.homeDir"
		this.homeDir = homeDir;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "repositoryConfig"
	 * @return valeur du champ "repositoryConfig"
	 */
	public RepositoryConfig getRepositoryConfig() {
	
		// On retourne le champ "repositoryConfig"
		return repositoryConfig;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "repositoryConfig"
	 * @param repositoryConfig Nouvelle valeur du champ "repositoryConfig"
	 */
	public void setRepositoryConfig(RepositoryConfig repositoryConfig) {
	
		// Mise a jour du champ "this.repositoryConfig"
		this.repositoryConfig = repositoryConfig;
	}

	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.JcrRepositoryFactory#createRepository()
	 */
	@Override
	protected Repository createRepository() throws Exception {
		
		// return JackRabbit repository.
		return RepositoryImpl.create(repositoryConfig);
	}

	/*
	 * (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.JcrRepositoryFactory#resolveConfigurationResource()
	 */
	@Override
	protected void resolveConfigurationResource() throws Exception {
		
		// Si la configuration est deja construite
		if (repositoryConfig != null) {
			
			// On sort
			return;
		}
		
		// Si la ressource pointant sur le fichier de configuration du depot est nulle
		if (this.configuration == null) {
			
			// Si les log sont actives
			if (log.isDebugEnabled()) {
				
				// Un log
				log.debug("Aucun fichier de configuration n'a ete specifie : le fichier par defaut sera utilise - " + DEFAULT_CONF_FILE);
			}
			
			// Positionnement du fichier de configuration par defaut
			this.configuration = new ClassPathResource(DEFAULT_CONF_FILE);
		}
		
		// Si le repertoire de travail est null
		if (homeDir == null) {
			
			// Si les log sont actives
			if (log.isDebugEnabled()) {
				
				// Un log
				log.debug("Aucun repertoire de travail n'a ete specifie : le repertoire par defaut sera utilise - " + DEFAULT_REP_DIR);
			}
			
			// Positionnement du repertoire de travail par defaut
			this.homeDir = new FileSystemResource(DEFAULT_REP_DIR);
		}
		
		// Si les log sont actives
		if (log.isDebugEnabled()) {
			
			// Un Log
			log.debug("Construction de la configuration de depot : homeDir = " + homeDir + ", configuration = " + configuration);
		}
		
		// Si la ressource existe
		if(configuration.exists()) {

			// Creation de la configuration du depot
			this.repositoryConfig = RepositoryConfig.create(
					new InputSource(configuration.getInputStream()),
					homeDir.getFile().getAbsolutePath());
		} else {

			// Installation de la configuration du depot
			this.repositoryConfig = RepositoryConfig.install(homeDir.getFile());
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see org.springmodules.jcr.RepositoryFactoryBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		
		// Si le depot est de type Jackrabbit
		if (repository instanceof JackrabbitRepository) {
			
			// On caste et on arrete
			((JackrabbitRepository) repository).shutdown();
		}
	}
}
