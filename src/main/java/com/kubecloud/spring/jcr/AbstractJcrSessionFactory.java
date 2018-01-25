package com.kubecloud.spring.jcr;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Credentials;
import javax.jcr.NamespaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeTypeManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.xml.NodeTypeReader;
import org.apache.jackrabbit.spi.QNodeTypeDefinition;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * Classe de base des fabriques de session JCR 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 7 févr. 2016 - 16:06:51
 */
public abstract class AbstractJcrSessionFactory implements DisposableBean,
		InitializingBean {
	
	/**
	 * Loggeur
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Depot cible
	 */
	protected Repository repository;
	
	/**
	 * Nom du workspace
	 */
	protected String workspaceName;
	
	/**
	 * Credentials de connexion
	 */
	protected Credentials credentials;
	
	/**
	 * Fichiers de definition des types de noeuds
	 */
	protected Set<Resource> nodeTypeDefinitions;
	
	/**
	 * cache des sessions creee
	 */
	private Set<Session> sessionsCache = new HashSet<Session>();
	
	/**
	 * Constructeur par defaut
	 */
	public AbstractJcrSessionFactory() {}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param repository	Depot
	 * @param workspaceName	Nom du Workspace
	 * @param credentials	Credentials
	 */
	public AbstractJcrSessionFactory(Repository repository, String workspaceName,
			Credentials credentials) {
		
		// Positionnement du depot
		this.repository = repository;
		
		// Positionnement du nom du workspace
		this.workspaceName = workspaceName;
		
		// Positionnement des credentials
		this.credentials = credentials;
	}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param repository	Depot
	 * @param workspaceName	Nom du Workspace
	 * @param credentials	Credentials
	 * @param nodeTypeDefinitions Fichiers de definition des types de noeuds
	 */
	public AbstractJcrSessionFactory(Repository repository, String workspaceName,
			Credentials credentials, Set<Resource> nodeTypeDefinitions) {
		
		// Positionnement du depot
		this.repository = repository;
		
		// Positionnement du nom du workspace
		this.workspaceName = workspaceName;
		
		// Positionnement des credentials
		this.credentials = credentials;
		
		// Positionnement des Fichiers de definition des types de noeuds
		this.nodeTypeDefinitions = nodeTypeDefinitions;
	}
	
	/**
	 * Methode permettant d'obtenir la valeur du champ "repository"
	 * @return valeur du champ "repository"
	 */
	public Repository getRepository() {
	
		// On retourne le champ "repository"
		return repository;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "repository"
	 * @param repository Nouvelle valeur du champ "repository"
	 */
	public void setRepository(Repository repository) {
	
		// Mise a jour du champ "this.repository"
		this.repository = repository;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "workspaceName"
	 * @return valeur du champ "workspaceName"
	 */
	public String getWorkspaceName() {
	
		// On retourne le champ "workspaceName"
		return workspaceName;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "workspaceName"
	 * @param workspaceName Nouvelle valeur du champ "workspaceName"
	 */
	public void setWorkspaceName(String workspaceName) {
	
		// Mise a jour du champ "this.workspaceName"
		this.workspaceName = workspaceName;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "credentials"
	 * @return valeur du champ "credentials"
	 */
	public Credentials getCredentials() {
	
		// On retourne le champ "credentials"
		return credentials;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "credentials"
	 * @param credentials Nouvelle valeur du champ "credentials"
	 */
	public void setCredentials(Credentials credentials) {
	
		// Mise a jour du champ "this.credentials"
		this.credentials = credentials;
	}
		
	/**
	 * Methode permettant d'obtenir la valeur du champ "nodeTypeDefinitions"
	 * @return valeur du champ "nodeTypeDefinitions"
	 */
	public Set<Resource> getNodeTypeDefinitions() {
	
		// On retourne le champ "nodeTypeDefinitions"
		return nodeTypeDefinitions;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "nodeTypeDefinitions"
	 * @param nodeTypeDefinitions Nouvelle valeur du champ "nodeTypeDefinitions"
	 */
	public void setNodeTypeDefinitions(Set<Resource> nodeTypeDefinitions) {
	
		// Mise a jour du champ "this.nodeTypeDefinitions"
		this.nodeTypeDefinitions = nodeTypeDefinitions;
	}

	/**
	 * Methode permettant d'ajouter des listeners a la session JCR
	 * @param session	Session JCR
	 * @return	JCR Session
	 */
	private Session addListeners(Session session) {
		
		// On retourne la session
		return session;
	}

	/**
	 * Methode permettant d'ajouter des type de noeuds a la session JCR
	 * @param session	Session JCR
	 * @return	JCR Session
	 * @throws RepositoryException Exception potentielle
	 */
	private Session addNodeTypes(Session session)  throws RepositoryException {
		
		// Si la liste des fichiers de definition est nulle
		if(nodeTypeDefinitions == null || nodeTypeDefinitions.isEmpty()) return session;
		
		// Espace de travail
		Workspace workspace = session.getWorkspace();
		
		// Gestionnaire des types de noeuds
		NodeTypeManager nodeTypeManager = workspace.getNodeTypeManager();
		
		// Registre des types de noeuds
		NodeTypeRegistry nodeTypeRegistry = ((NodeTypeManagerImpl) nodeTypeManager).getNodeTypeRegistry();
		
		// Parcours de la liste
		for (Resource resource : nodeTypeDefinitions) {
			
			try {
				
				// Tableau des definitions de noeud
				QNodeTypeDefinition[] nodeTypeDefinitions = NodeTypeReader.read(resource.getInputStream());
				
				// Parcours du tableau des definitions
				for (QNodeTypeDefinition nodeTypeDefinition : nodeTypeDefinitions) {
					
					try {
						
						// Recherche du noeud dans le registre
		                nodeTypeRegistry.getNodeTypeDef(nodeTypeDefinition.getName());
		                
		            } catch (NoSuchNodeTypeException e) {
		                
		            		// Enregistrement du type
		            		nodeTypeRegistry.registerNodeType(nodeTypeDefinition);
		            }
				}
				
			} catch (InvalidNodeTypeDefException | IOException e) {
				
				// On relance l'exception
				throw new RepositoryException("Erreur survenue lors de l'importation du");
			}
		}
		
		// On retourne la session
		return session;
	}
	
	/**
	 * Methode permettant d'enregistrer les espaces de noms 
	 * @param session	Session JCR
	 * @return	Session JCR
	 * @throws RepositoryException Exception potentielle
	 */
	protected Session registerNamespace(Session session) throws RepositoryException {
		
		// Obtention des espaces de noms
		Map<String, String> namespaces = getNamespaces();
		
		// Si la map est vide
		if(namespaces == null || namespaces.isEmpty()) return session;
		
		// Parcours
		for (Entry<String, String> entry : namespaces.entrySet()) {
			
			try {
				
				// Enregistrement
				session.getWorkspace().getNamespaceRegistry().registerNamespace(entry.getKey(), entry.getValue());
				
			} catch (NamespaceException e) {}
		}
		
		// On retourne la session
		return session;
	}
	
	/**
	 * Methode permettant d'obtenir les espaces de noms
	 * @return	Map d'espaces de noms
	 */
	protected Map<String, String> getNamespaces() {
		
		// Map des espaces de noms
		Map<String, String> namespaces = new HashMap<String, String>();
		
		// Ajout de l'espace spring-jcr
		namespaces.put("spring-jcr", "http://spring.developpers.kube-cloud.com/schema/jcr");
		
		// Ajout de l'espace spring-jackrabbit
		namespaces.put("spring-jackrabbit", "http://spring.developpers.kube-cloud.com/schema/jcr/jackrabbit");
		
		
		// On retourne la map
		return namespaces;
	}
	
	/**
	 * Methode permettant d'obtenir une session
	 * @return	Session construite
	 * @throws RepositoryException	Exception potentielle
	 */
	public Session getSession() throws RepositoryException {
		
		// Creation de la session par authentification
		Session session = addListeners(addNodeTypes(registerNamespace(repository.login(credentials, workspaceName))));
		
		// Ajout dans le cache des sessions
		sessionsCache.add(session);
		
		// On retourne la session
		return session;
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
		
		// Si le cache de sessions est vide
		if(sessionsCache.isEmpty()) return;
		
		// Parcours du cache
		for (Session session : sessionsCache) {

			try {
				
				// Deconnexion de la session
				session.logout();
				
			} catch (Exception e) {

				// Si les log sont actives
				if (log.isDebugEnabled()) {
					
					// Un log
					log.debug(String.format("Erreur survenue lors de la dexonnexion de la session JCR : %1$s", e.getMessage()), e);
				}
			}
		}
		
		// On vide le cache
		sessionsCache.clear();
	}
}
