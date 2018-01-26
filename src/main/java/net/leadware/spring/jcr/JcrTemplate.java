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

import java.util.List;

import javax.jcr.Item;
import javax.jcr.Node;

import org.springframework.dao.DataAccessException;

/**
 * Interfaces des operations JCR
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 5 févr. 2016 - 23:02:40
 */
public interface JcrTemplate {

	/**
	 * Methode permettant d'executer une action JCR 
	 * @param action	Action a executer
	 * @param autoSave Enregistrement automatique des modifications sur la session
	 * @return	Resultat de l'execution de l'action
	 * @exception DataAccessException Exception Potentielle
	 */
	public Object execute(JcrAction action, boolean autoSave) throws DataAccessException;
	
	/**
	 * Methode permettant d'executer une action JCR 
	 * @param action	Action a executer
	 * @return	Resultat de l'execution de l'action
	 * @exception DataAccessException Exception Potentielle
	 */
	public Object execute(JcrAction action) throws DataAccessException;
	
	/**
	 * Methode permettant d'ajouter un noeud a partir de la racine
	 * @param parent Parent Node 
	 * @param nodePath	Chemin du noeud
	 * @return	Noeud ajoute
	 * @throws DataAccessException	Exception potentielle
	 */
	public Node addNode(Node parent, String nodePath) throws DataAccessException;
	
	/**
	 * Methode permettant d'ajouter un noeud a partir de la racine 
	 * @param nodePath	Chemin du noeud
	 * @return	Noeud ajoute
	 * @throws DataAccessException	Exception potentielle
	 */
	public Node addNode(String nodePath) throws DataAccessException;

	/**
	 * Methode permettant de verifier qu'un element existe pour ce chemin
	 * @param itemPath	Chemin de l'element
	 * @return	Etat d'existence
	 */
	public boolean itemExists(String itemPath);
	
	/**
	 * Methode permettant de verifier qu'un noeud existe pour ce chemin
	 * @param nodePath	Chemin du noeud
	 * @return	Etat d'existence
	 */
	public boolean nodeExists(String nodePath);
	
	/**
	 * Methode permettant d'obtenir l'element situe a cette adresse 
	 * @param path	Adresse de l'element recherche
	 * @return	Element recherche
	 */
	public Item getItem(String path);
	
	/**
	 * Methode permettant de charger un noeud a partir de son chemin absolu
	 * @param path	Chemin absolu du noeud
	 * @return	Noeud recherche
	 * @throws DataAccessException Exception potentielle
	 */
	public Node getNode(String path) throws DataAccessException;
	
	/**
	 * Methode permettant d'executer une requete JCR-SQL2
	 * @param query	Requete JCR-SQL2
	 * @return	Liste des noeuds
	 * @throws DataAccessException	Exception potentielle
	 */
	public List<Node> executeSQL2Query(String query) throws DataAccessException;

	/**
	 * Methode permettant copier un noeud ainsi que son arborescence complete 
	 * @param sourceNode	Noeud source
	 * @param destinationNode	Noeud cible
	 * @exception DataAccessException Exception Potentielle
	 */
	public void copy(Node sourceNode, Node destinationNode) throws DataAccessException;

	/**
	 * Methode permettant deplacer un noeud ainsi que son arborescence complete 
	 * @param sourcePath	Chemin source du noeud
	 * @param destinationPath	Chemin cible du noeud
	 * @exception DataAccessException Exception Potentielle
	 */
	public void move(String sourcePath, String destinationPath) throws DataAccessException;
	
	/**
	 * Methode permettant deplacer un noeud ainsi que son arborescence complete 
	 * @param sourceNode		Noeud source
	 * @param destinationNode	Noeud cible
	 */
	public void move(Node sourceNode, Node destinationNode) throws DataAccessException;

	/**
	 * Methode permettant d'obtenir le dump du noeud 
	 * @param node	Noeud source
	 * @return	Chaine representant le dump
	 * @exception DataAccessException Exception Potentielle
	 */
	public String dump(Node node) throws DataAccessException;
	
	/**
	 * Methode permettant de suppression d'un noeud
	 * @param node	Noeud a supprimer
	 * @throws DataAccessException	Exception potentielle
	 * @exception DataAccessException Exception Potentielle
	 */
	public void remove(Node node) throws DataAccessException;

	/**
	 * Methode permettant de suppression d'un noeud
	 * @param path	Chemin du Noeud a supprimer
	 * @throws DataAccessException	Exception potentielle
	 * @exception DataAccessException Exception Potentielle
	 */
	public void remove(String path) throws DataAccessException;
}
