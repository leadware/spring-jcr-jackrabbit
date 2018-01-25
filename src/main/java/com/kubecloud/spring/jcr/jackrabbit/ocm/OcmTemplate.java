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

import org.apache.jackrabbit.ocm.query.Query;
import org.springframework.dao.DataAccessException;

/**
 * Interfaces des operations OCM 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 8 févr. 2016 - 12:36:25
 */
public interface OcmTemplate<T> {
	
	/**
	 * Methode permettant d'executer une action OCM 
	 * @param action	Action a executer
	 * @param autoSave Enregistrement automatique des modifications
	 * @return	resultat de l'execution
	 */
	public Object execute(OcmAction<T> action, boolean autoSave) throws DataAccessException;
	
	/**
	 * Methode permettant d'executer une action OCM 
	 * @param action	Action a executer
	 * @return	resultat de l'execution
	 */
	public Object execute(OcmAction<T> action) throws DataAccessException;
	
	/**
	 * Methode permettant d'inserer l'entite dans le depot 
	 * @param entity	Entite a inserer
	 * @return	Entite inseree
	 * @exception DataAccessException Exception potentielle
	 */
	public T insert(T entity) throws DataAccessException;
	
	/**
	 * Methode permettant de mettre a jour une entite du depot
	 * @param entity	Entite a mettre a jour
	 * @return	Entite mis e a jour
	 * @exception DataAccessException Exception potentielle
	 */
	public T update(T entity) throws DataAccessException;
	
	/**
	 * Methode permettant de supprimer une entite du depot
	 * @param entity	Entite a mettre a jour
	 * @exception DataAccessException Exception potentielle
	 */
	public void remove(T entity) throws DataAccessException;
	
	/**
	 * Methode permettant de rechercher une entite a partir de son chemin
	 * @param path	Chemin de l'entite
	 * @return	Entite rouvee
	 * @throws DataAccessException Exception potentielle
	 */
	public T findByPath(String path) throws DataAccessException;
	
	/**
	 * Methode permettant de filtrer des entites du depot
	 * @param query	Requete de filtre (OCM)
	 * @return	Collection des entites
	 * @throws DataAccessException	Exception potentielle
	 */
	public Collection<T> filter(Query query) throws DataAccessException;
}