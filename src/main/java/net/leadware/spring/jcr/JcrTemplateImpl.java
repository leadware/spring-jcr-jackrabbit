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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

/**
 * Implementation de base des operations JCR
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 6 févr. 2016 - 11:52:04
 */
public class JcrTemplateImpl implements JcrTemplate, InitializingBean, DisposableBean {
	
	/**
	 * Un Logger
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Fabrique de session JCR
	 */
	protected SessionFactory sessionFactory;
	
	/**
	 * Constructeur par defaut
	 */
	public JcrTemplateImpl() {}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param sessionFactory	Fabrique de sessions
	 */
	public JcrTemplateImpl(SessionFactory sessionFactory) {
		
		// Positionnement de la fabrique de sessions
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Methode permettant d'obtenir la valeur du champ "sessionFactory"
	 * @return valeur du champ "sessionFactory"
	 */
	public SessionFactory getSessionFactory() {
	
		// On retourne le champ "sessionFactory"
		return sessionFactory;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "sessionFactory"
	 * @param sessionFactory Nouvelle valeur du champ "sessionFactory"
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
	
		// Mise a jour du champ "this.sessionFactory"
		this.sessionFactory = sessionFactory;
	}
	
	/*(non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#execute(net.leadware.spring.jcr.JcrAction, boolean)
	 */
	@Override
	public Object execute(JcrAction action, boolean autoSave) throws DataAccessException {
		
		// Session JCR
		Session session = null;
		
		try {

			// Obtention d'une session
			session = sessionFactory.getSession();
			
			// Execution de l'action
			Object result = action.execute(session);
			
			// Enregistrement
			if(autoSave) session.save();
			
			// Excution de l'action
			return result;
			
		} catch (Exception e) {
			
			// On relance l'exception
			throw JcrExceptionUtils.translateException(e);
			
		} finally {
			
			try {

				// Si la session est non nulle
				if(session != null) session.logout();
				
			} catch (Exception e2) {}
		}
	}
	
	/*(non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#execute(net.leadware.spring.jcr.JcrAction)
	 */
	@Override
	public Object execute(JcrAction action) throws DataAccessException {
		
		// On retourne le resultat de l'execution
		return execute(action, true);
	}
	
	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#addNode(javax.jcr.Node, java.lang.String)
	 */
	@Override
	public Node addNode(final Node parent, final String nodePath) throws DataAccessException {
		
		// On retourne le noeud
		return (Node) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// On retourne le noeud ajouté
				return JcrUtils.getOrCreateNodeByPath(parent, nodePath, null, session);
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#addNode(java.lang.String)
	 */
	@Override
	public Node addNode(final String nodePath) throws DataAccessException {
		
		// On retourne le resultat de l'execution de l'action
		return (Node) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// On retourne le noeud creee
				return JcrUtils.getOrCreateNodeByPath(nodePath, null, session);
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#itemExists(java.lang.String)
	 */
	@Override
	public boolean itemExists(final String itemPath) {
		
		// On retourne le resultat de l'execution de l'action
		return (boolean) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// On retourne le resultat de la recherche
				return session.itemExists(itemPath);
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#nodeExists(java.lang.String)
	 */
	@Override
	public boolean nodeExists(final String nodePath) {
		
		// On retourne le resultat de l'execution de l'action
		return (boolean) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// On retourne le resultat de la recherche
				return JcrUtils.nodeExist(nodePath, session);
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#getItem(java.lang.String)
	 */
	@Override
	public Item getItem(final String path) {
		
		// On retourne le resultat de l'execution de l'action
		return (Item) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// On retourne le resultat de la recherche
				return JcrUtils.getItem(path, session);
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#getNode(java.lang.String)
	 */
	@Override
	public Node getNode(final String path) throws DataAccessException {
		
		// On retourne le resultat de l'execution de l'action
		return (Node) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// On retourne le resultat de la recherche
				return JcrUtils.getNode(path, session);
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#executeSQL2Query(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Node> executeSQL2Query(final String query)
			throws DataAccessException {
		
		// On retourne le resultat de l'execution de l'action
		return (List<Node>) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// Gestionnaire de requete
				QueryManager queryManager = session.getWorkspace().getQueryManager();
				
				// Construction de la requete
				Query qry = queryManager.createQuery(query, Query.JCR_SQL2);
				
				// Execution de la requete
				QueryResult result = qry.execute();
				
				// Obtention de l
				NodeIterator iterator = result.getNodes();
				
				// Liste des Noeuds
				List<Node> nodes = new ArrayList<Node>();
				
				while (iterator.hasNext()) nodes.add(iterator.nextNode());
				
				// On retourne la liste des noeuds
				return nodes;
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#copy(javax.jcr.Node, javax.jcr.Node)
	 */
	@Override
	public void copy(final Node sourceNode, final Node destinationNode)
			throws DataAccessException {
		
		// Execution de l'action
		execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// Copie du noeud
				session.getWorkspace().copy(sourceNode.getPath(), destinationNode.getPath());
				
				// On retourne null
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#move(java.lang.String, java.lang.String)
	 */
	@Override
	public void move(final String sourcePath, final String destinationPath)
			throws DataAccessException {
		
		// Execution de l'action
		execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// Deplacement du noeud
				session.move(sourcePath, destinationPath);
				
				// On retourne null
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#move(javax.jcr.Node, javax.jcr.Node)
	 */
	@Override
	public void move(final Node sourceNode, final Node destinationNode)
			throws DataAccessException {
		
		// Execution de l'action
		execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// Deplacement du noeud
				session.move(sourceNode.getPath(), destinationNode.getPath());
				
				// On retourne null
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#dump(javax.jcr.Node)
	 */
	@Override
	public String dump(final Node node) throws DataAccessException {
		
		// On retourne le resultat de l'execution de l'action
		return (String) execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// On retourne le resultat de la recherche
				return JcrUtils.dump(node);
			}
		});
	}
	
	/*(non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#remove(javax.jcr.Node)
	 */
	@Override
	public void remove(final Node node) throws DataAccessException {
		
		try {
			
			// Execution de l'action
			remove(node.getPath());
			
		} catch (RepositoryException e) {
			
			// On relance l'exception traduite
			throw JcrExceptionUtils.translateException(e);
		}
	}
	
	/*(non-Javadoc)
	 * @see net.leadware.spring.jcr.JcrTemplate#remove(java.lang.String)
	 */
	@Override
	public void remove(final String path) throws DataAccessException {
		
		// Execution de l'action
		execute(new JcrAction() {
			
			/*
			 * (non-Javadoc)
			 * @see net.leadware.spring.jcr.JcrAction#execute(javax.jcr.Session)
			 */
			@Override
			public Object execute(Session session) throws IOException,
					RepositoryException {
				
				// Suppression du noeud
				session.removeItem(path);
				
				// On retourne null
				return null;
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {}
}
