package com.kubecloud.spring.jcr.jackrabbit.ocm;

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

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.ocm.exception.ObjectContentManagerException;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.query.Query;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

import com.kubecloud.spring.jcr.JcrExceptionUtils;

/**
 * Classe representant 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 8 févr. 2016 - 13:10:32
 */
@SuppressWarnings("unchecked")
public class OcmTemplateImpl<T> implements OcmTemplate<T>, InitializingBean,
		DisposableBean {
	
	/**
	 * Un Logger
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Fabrique de session OCM
	 */
	protected OcmSessionFactory sessionFactory;
	
	/**
	 * Constructeur par defaut
	 */
	public OcmTemplateImpl() {}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param sessionFactory	Fabrique de sessions
	 */
	public OcmTemplateImpl(OcmSessionFactory sessionFactory) {
		
		// Positionnement de la fabrique de sessions
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Methode permettant d'obtenir la valeur du champ "sessionFactory"
	 * @return valeur du champ "sessionFactory"
	 */
	public OcmSessionFactory getSessionFactory() {
	
		// On retourne le champ "sessionFactory"
		return sessionFactory;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "sessionFactory"
	 * @param sessionFactory Nouvelle valeur du champ "sessionFactory"
	 */
	public void setSessionFactory(OcmSessionFactory sessionFactory) {
	
		// Mise a jour du champ "this.sessionFactory"
		this.sessionFactory = sessionFactory;
	}

	/*(non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmTemplate#execute(com.kubecloud.spring.jcr.jackrabbit.ocm.OcmAction, boolean)
	 */
	@Override
	public Object execute(OcmAction<T> action, boolean autoSave) 
			throws DataAccessException {
		
		// Session JCR
		ObjectContentManager ocm = null;

		try {

			// Obtention d'un OCM
			ocm = sessionFactory.getOCM();
			
			// Execution de l'action
			Object result = action.execute(ocm);
			
			// Enregistrement
			if(autoSave) ocm.save();
			
			// Excution de l'action
			return result;
			
		} catch (Exception e) {
			
			// On relance l'exception
			throw JcrExceptionUtils.translateException(e);
			
		} finally {
			
			try {

				// Si l'ocm est non null
				if(ocm != null) ocm.logout();
				
			} catch (Exception e2) {}
		}
	}
	
	/*(non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmTemplate#execute(com.kubecloud.spring.jcr.jackrabbit.ocm.OcmAction)
	 */
	@Override
	public Object execute(OcmAction<T> action) throws DataAccessException {
		
		// On retourne le resultat de l'execution
		return execute(action, true);
	}
	
	/*(non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmTemplate#insert(java.lang.Object)
	 */
	@Override
	public T insert(final T entity) throws DataAccessException {
		
		// On retourne le resultat de l'execution
		return (T) execute(new OcmAction<T>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmAction#execute(org.apache.jackrabbit.ocm.manager.ObjectContentManager)
			 */
			@Override
			public Object execute(ObjectContentManager ocm) throws ObjectContentManagerException {
				
				// Enregistrement
				ocm.insert(entity);
				
				// On retourne le resultat
				return entity;
			}
		});
	}
	
	/*(non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmTemplate#update(java.lang.Object)
	 */
	@Override
	public T update(final T entity) throws DataAccessException {
		
		// On retourne le resultat de l'execution
		return (T) execute(new OcmAction<T>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmAction#execute(org.apache.jackrabbit.ocm.manager.ObjectContentManager)
			 */
			@Override
			public Object execute(ObjectContentManager ocm) throws ObjectContentManagerException {
				
				// Mise a jour
				ocm.update(entity);
				
				// On retourne le resultat
				return entity;
			}
		});
	}
	
	/*(non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmTemplate#remove(java.lang.Object)
	 */
	@Override
	public void remove(final T entity) throws DataAccessException {
		
		// Execution de l'action
		execute(new OcmAction<T>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmAction#execute(org.apache.jackrabbit.ocm.manager.ObjectContentManager)
			 */
			@Override
			public Object execute(ObjectContentManager ocm) throws ObjectContentManagerException {
				
				// Suppression
				ocm.remove(entity);
				
				// On retourne le resultat
				return entity;
			}
		}); 
	}
	
	/*(non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmTemplate#findByPath(java.lang.String)
	 */
	@Override
	public T findByPath(final String path) throws DataAccessException {
		
		// On retourne le resultat de l'execution
		return (T) execute(new OcmAction<T>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmAction#execute(org.apache.jackrabbit.ocm.manager.ObjectContentManager)
			 */
			@Override
			public Object execute(ObjectContentManager ocm) throws ObjectContentManagerException {
				
				// On retourne le resultat
				return ocm.getObject(path);
			}
		});
	}
	
	/*(non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmTemplate#filter(org.apache.jackrabbit.ocm.query.Query)
	 */
	@Override
	public Collection<T> filter(final Query query) throws DataAccessException {
		
		// On retourne le resultat de l'execution
		return (Collection<T>) execute(new OcmAction<T>() {
			
			/*
			 * (non-Javadoc)
			 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmAction#execute(org.apache.jackrabbit.ocm.manager.ObjectContentManager)
			 */
			@Override
			public Object execute(ObjectContentManager ocm) throws ObjectContentManagerException {
				
				// On retourne le resultat
				return ocm.getObjects(query);
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