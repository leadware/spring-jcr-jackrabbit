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

import java.util.StringTokenizer;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import net.leadware.spring.jcr.exceptions.InvalidPathException;

/**
 * Utiltaire d'aide aux operations JCR  
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 6 févr. 2016 - 13:36:35
 */
public class JcrUtils {
	
	/**
	 * Methode permettant de verifier l'existence d'un chemin relatif
	 * @param parent	Noeud parent
	 * @param path	Chemin relatif
	 * @return	etat d'existence
	 * @throws RepositoryException Exception Potentielle
	 */
	public static boolean nodeExist(Node parent, String path) throws RepositoryException {
		
		try {
			
			// Tentative d'obtention du noeud
			parent.getNode(getRelativePath(path));
			
			// On retourne true
			return true;
			
		} catch (PathNotFoundException e) {
			
			// On retourne false
			return false;
		}
	}

	/**
	 * Methode permettant de verifier l'existence d'un chemin relatif
	 * @param path	Chemin relatif
	 * @param session Session JCR
	 * @return	etat d'existence
	 * @throws RepositoryException Exception Potentielle
	 */
	public static boolean nodeExist(String path, Session session) throws RepositoryException {
		
		// On retourne le resultat de la recherche
		return nodeExist(session.getRootNode(), path);
	}
	
	/**
	 * Methode permettant d'obtenir un element a partir de son chemin complet 
	 * @param itemPath	Chemin complet de l'element
	 * @param session	Session en cours
	 * @return	Element trouve
	 * @throws RepositoryException Exception potentielle
	 */
	public static Item getItem(String itemPath, Session session) throws RepositoryException {
		
		try {
			
			// Tentative d'obtention de l'element
			return session.getItem(getAbsolutePath(itemPath));
			
		} catch (PathNotFoundException e) {
			
			// On retourne null
			return null;
		}
	}

	/**
	 * Methode permettant d'obtenir un element a partir de son chemin complet 
	 * @param nodePath	Chemin complet du noeud
	 * @param session	Session en cours
	 * @return	Element trouve
	 * @throws RepositoryException Exception potentielle
	 */
	public static Node getNode(String nodePath, Session session) throws RepositoryException {
		
		try {
			
			// Tentative d'obtention du noeud
			return session.getNode(getAbsolutePath(nodePath));
			
		} catch (PathNotFoundException e) {
			
			// On retourne null
			return null;
		}
	}
	
	/**
	 * Methode permettant de dump d'un noeud
	 * @param node	Noeud a dumper
	 * @return	Chaine dump
	 * @throws RepositoryException Exception potentielle
	 */
	public static String dump(Node node) throws RepositoryException {
		
		// Chaine de retour a la ligne
		String endLine = "\n";
		
		String tabulation = "\t";
		
		// Constructeur de chaine
		StringBuilder builder = new StringBuilder();
		
		// Ajout du chemin
		builder.append(node.getPath() + endLine);
		
		// Iterateur de proprietes
		PropertyIterator properties = node.getProperties();
		
		// Parcours de l'iterateur
		while (properties.hasNext()) {
			
			// Obtention de la propriete
			Property property = properties.nextProperty();
			
			// Ajout d'une tabulation
			builder.append(tabulation);
			
			// Ajout
			builder.append(property.getPath() + " = ");
			
			// Si la propriete est multiple
			if (property.getDefinition().isMultiple()) {
				
				// Obtention de la liste des valeurs
				Value[] values = property.getValues();
				
				// parcours de la liste
				for (int i = 0; i < values.length; i++) {
					
					// Si ce n'est pas la premiere valeur
					if (i > 0) { 
						
						// Ajout d'un separateur
						builder.append(","); 
					}
					
					// Ajout de la valeur
					builder.append(values[i].getString());
				}
				
			} else {
				
				// Ajout de la valeur
				builder.append(property.getString());
			}
			
			// Ajout d'un retout chariot
			builder.append(endLine);
		}
		
		// Obtention de l'iterateur sur les noeuds fils
		NodeIterator nodes = node.getNodes();
		
		// Parcours des noeuds fils
		while (nodes.hasNext()) {
			
			// Obtention du noeud fils
			Node child = nodes.nextNode();

			// Ajout d'un retout chariot
			builder.append(endLine);
			
			// Ajout du dump de ce noeud
			builder.append(dump(child));
		}
		
		// On retourne le dump
		return builder.toString();
	}
	
	/**
	 * Methode permettant de creer un noeud a partir d'un chemin relatif a un parent
	 * @param parent Noeud parent
	 * @param path	Chemin du noeud a creer
	 * @param nodeType	Type du noeud a creer
	 * @param session	Session JCR courante
	 * @return Noeud creee
	 * @throws RepositoryException Exception Potentielle
	 */
	public static Node getOrCreateNodeByPath(Node parent, String path, 
			String nodeType, Session session) throws RepositoryException {
		
		// Si la chaine est nulle
		if(path == null || path.trim().isEmpty()) throw new InvalidPathException("Le chemin du noeud a creer est vide");
		
		// Suppression du slash s'il est en premiere position
		String relativePath = getRelativePath(path);
		
		// Si le noeud existe deja
		if(nodeExist(parent, relativePath)) return parent.getNode(relativePath);
		
		// On Tokenize le chemin
		StringTokenizer tokenizer = new StringTokenizer(relativePath, "/");
		
		// Noeud courant
		Node current = parent;
		
		// Parcours des token
		while (tokenizer.hasMoreElements()) {
			
			// Token en cours
			String token = tokenizer.nextToken().trim();
			
			// Si le noeud en cours ne contient pas cet element
			if(!current.hasNode(token)) {
				
				// Si le type de noeud est null
				if(nodeType == null || nodeType.trim().isEmpty()) current.addNode(token);
				else current.addNode(token, nodeType.trim());
			}
			
			// Recuperation du noeud fraichement creee
			current = current.getNode(token);
		}
		
		// On retourne le noeud courant
		return current;
		
	}

	/**
	 * Methode permettant de creer un noeud a partir d'un chemin absolu
	 * @param path	Chemin du noeud a creer
	 * @param nodeType	Type du noeud a creer
	 * @param session	Session JCR courante
	 * @return Noeud creee
	 * @throws RepositoryException Exception Potentielle
	 */
	public static Node getOrCreateNodeByPath(String path, 
			String nodeType, Session session) throws RepositoryException {
		
		// On retourne le noeud creee ou obtenu a partir du root
		return getOrCreateNodeByPath(session.getRootNode(), path, nodeType, session);
		
	}
	
	/**
	 * Methode permettant d'obtenir le chemin absolu d'un chemin 
	 * @param path	Chemin
	 * @return	Chemin absolu
	 */
	private static String getAbsolutePath(String path) {
		
		// Si le chemin est null
		if(path == null) return null;
		
		// On retourne le chemin absolu
		return path.trim().startsWith("/") ? path.trim() : "/".concat(path.trim());
	}

	/**
	 * Methode permettant d'obtenir le chemin relatif d'un chemin 
	 * @param path	Chemin
	 * @return	Chemin absolu
	 */
	private static String getRelativePath(String path) {
		
		// Si le chemin est null
		if(path == null) return null;
		
		// On retourne le chemin absolu
		return path.trim().startsWith("/") ? path.trim().substring(1) : path.trim();
	}
	
	public static void main(String[] args) {
		
		String chn = "/a/b/c/d";
		
		System.out.println("--- " + chn.substring(1));
		
	}
}
