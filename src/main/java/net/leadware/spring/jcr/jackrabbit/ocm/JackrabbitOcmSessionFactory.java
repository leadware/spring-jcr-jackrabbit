package net.leadware.spring.jcr.jackrabbit.ocm;

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

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;
import org.apache.jackrabbit.ocm.mapper.Mapper;

import net.leadware.spring.jcr.AbstractJcrSessionFactory;

/**
 * Classe representant une fabrique de session Jackrabbit OCM
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 5 févr. 2016 - 00:52:03
 */
public class JackrabbitOcmSessionFactory extends AbstractJcrSessionFactory implements OcmSessionFactory {

	/**
	 * Loggeur
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Mappeur OCM
	 */
	private Mapper mapper;
	
	/**
	 * Constructeur par defaut
	 */
	public JackrabbitOcmSessionFactory() {}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param repository	Depot
	 * @param workspaceName	Espace de travail
	 * @param credentials	Credentials de connexion
	 * @param mapper	Mapper OCM
	 */
	public JackrabbitOcmSessionFactory(Repository repository, String workspaceName,
			Credentials credentials, Mapper mapper) {
		
		// Appel Parent
		super(repository, workspaceName, credentials);
		
		// Positionnement du mapper
		this.mapper = mapper;
	}
	
	/**
	 * Methode permettant d'obtenir la valeur du champ "mapper"
	 * @return valeur du champ "mapper"
	 */
	public Mapper getMapper() {
	
		// On retourne le champ "mapper"
		return mapper;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "mapper"
	 * @param mapper Nouvelle valeur du champ "mapper"
	 */
	public void setMapper(Mapper mapper) {
	
		// Mise a jour du champ "this.mapper"
		this.mapper = mapper;
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.spring.jcr.AbstractJcrSessionFactory#getNamespaces()
	 */
	@Override
	protected Map<String, String> getNamespaces() {
		
		// Appel Parent
		Map<String, String> namespaces = super.getNamespaces() == null ? new HashMap<String, String>() : super.getNamespaces();
		
		// Ajout du namespace OCM
		namespaces.put("ocm", "http://jackrabbit.apache.org/ocm");
		
		// On retourne la MAP
		return namespaces;
	}
	
	/**
	 * Methode permettant d'ontenir un OCM
	 * @return	OCM
	 * @throws RepositoryException Exception potentielle
	 */
	public ObjectContentManager getOCM() throws RepositoryException {
		
		// Obtention de la session
		Session session = getSession();
		
		// On retourne un OCM
		return new ObjectContentManagerImpl(session, mapper);
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
	public void destroy() throws Exception {
		
		// Si la session est non nulle
		if(getOCM() != null) {
			
			try {
				
				// Deconnexion de la session
				getOCM().logout();
				
			} catch (Exception e) {

				// Si les log sont actives
				if (log.isDebugEnabled()) {
					
					// Un log
					log.debug(String.format("Erreur survenue lors de la dexonnexion de la session JCR : %1$s", e.getMessage()), e);
				}
			}
		}
	}
}