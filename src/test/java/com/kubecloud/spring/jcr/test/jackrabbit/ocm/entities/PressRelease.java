package com.kubecloud.spring.jcr.test.jackrabbit.ocm.entities;

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

import java.util.Date;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;

/**
 * Classe representant une presse
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 7 févr. 2016 - 11:09:22
 */
@Node(jcrType = "spring-jcr:base")
public class PressRelease {
	
	/**
	 * Chemin de la presse (Identifiant)
	 */
	@Field(path = true) 
	private String path;
	
	/**
	 * Titre de la presse
	 */
	@Field 
	private String title;
	
	/**
	 * Date de publication de la presse
	 */
	@Field
	private Date publishedDate;
	
	/**
	 * Contenu de la presse
	 */
	@Field 
	private String content;
	
	/**
	 * Constructeur par defaut
	 */
	public PressRelease() {}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param path	Chemin de la presse (Identifiant)
	 * @param title	Titre de la presse
	 * @param publishedDate	Date de publication de la presse
	 * @param content	Contenu de la presse
	 */
	public PressRelease(String path, String title, Date publishedDate, String content) {
		
		// Positionnement du Chemin de la presse (Identifiant)
		this.path = path;
		
		// Positionnement du Titre de la presse
		this.title = title;
		
		// Positionnement de la Date de publication de la presse
		this.publishedDate = publishedDate;
		
		// Positionnement du Contenu de la presse
		this.content = content;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "path"
	 * @return valeur du champ "path"
	 */
	public String getPath() {
	
		// On retourne le champ "path"
		return path;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "path"
	 * @param path Nouvelle valeur du champ "path"
	 */
	public void setPath(String path) {
	
		// Mise a jour du champ "this.path"
		this.path = path;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "title"
	 * @return valeur du champ "title"
	 */
	public String getTitle() {
	
		// On retourne le champ "title"
		return title;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "title"
	 * @param title Nouvelle valeur du champ "title"
	 */
	public void setTitle(String title) {
	
		// Mise a jour du champ "this.title"
		this.title = title;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "publishedDate"
	 * @return valeur du champ "publishedDate"
	 */
	public Date getPublishedDate() {
	
		// On retourne le champ "publishedDate"
		return publishedDate;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "publishedDate"
	 * @param publishedDate Nouvelle valeur du champ "publishedDate"
	 */
	public void setPublishedDate(Date publishedDate) {
	
		// Mise a jour du champ "this.publishedDate"
		this.publishedDate = publishedDate;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "content"
	 * @return valeur du champ "content"
	 */
	public String getContent() {
	
		// On retourne le champ "content"
		return content;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "content"
	 * @param content Nouvelle valeur du champ "content"
	 */
	public void setContent(String content) {
	
		// Mise a jour du champ "this.content"
		this.content = content;
	}
}