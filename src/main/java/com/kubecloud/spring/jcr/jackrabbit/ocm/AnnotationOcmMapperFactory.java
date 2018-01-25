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

import java.util.ArrayList;
import java.util.List;

import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;

import com.kubecloud.spring.jcr.jackrabbit.ocm.scanners.NodeEntityScanner;

/**
 * Classe representant une fabrique de Mapper OCM basee sur les annotations
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 12 févr. 2016 - 09:44:16
 */
@SuppressWarnings("rawtypes")
public class AnnotationOcmMapperFactory extends OcmMapperFactory {

	/**
	 * Liste des package separes par une virgule
	 */
	private String packages = null;
	
	/**
	 * Liste des packagesList des entites OCM
	 */
	private List<String> packagesList = null;
	
	/**
	 * Liste des entites OCM
	 */
	private List<Class> ocmEntities = null; 
	
	/**
	 * Constructeur par defaut
	 */
	public AnnotationOcmMapperFactory() {}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param packagesList Liste des packagesList a scanner
	 */
	public AnnotationOcmMapperFactory(List<String> packagesList) {
		
		// Positionnement de la liste de packagesList
		this.packagesList = packagesList;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "packages"
	 * @return valeur du champ "packages"
	 */
	public String getPackages() {
	
		// On retourne le champ "packages"
		return packages;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "packages"
	 * @param packages Nouvelle valeur du champ "packages"
	 */
	public void setPackages(String packages) {
	
		// Mise a jour du champ "this.packages"
		this.packages = packages;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "packagesList"
	 * @return valeur du champ "packagesList"
	 */
	public List<String> getPackagesList() {
	
		// On retourne le champ "packagesList"
		return packagesList;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "packagesList"
	 * @param packages Nouvelle valeur du champ "packagesList"
	 */
	public void setPackagesList(List<String> packages) {
	
		// Mise a jour du champ "this.packages"
		this.packagesList = packages;
	}

	/**
	 * Methode permettant d'obtenir la valeur du champ "ocmEntities"
	 * @return valeur du champ "ocmEntities"
	 */
	public List<Class> getOcmEntities() {
	
		// On retourne le champ "ocmEntities"
		return ocmEntities;
	}
	
	/* (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmMapperFactory#prepareMapper()
	 */
	@Override
	protected void prepareMapper() throws Exception {
		
		// Construction de la liste des packages
		buildPackagesList();
		
		// Scanner de noeud OCM
		NodeEntityScanner scanner = new NodeEntityScanner(packagesList);

		// Obtention des entites OCM
		ocmEntities = scanner.scan();
	}

	/* (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmMapperFactory#createMapper()
	 */
	@Override
	protected Mapper createMapper() throws Exception {
		
		// Creation du mapper
		return new AnnotationMapperImpl(ocmEntities);
	}

	/**
	 * Methode permettant de construire la liste des packages
	 */
	private void buildPackagesList() {
		
		// Si la liste est vide
		if(packages == null || packages.trim().isEmpty()) return;
		
		// Si la liste des packages est nulle
		if(packagesList == null) packagesList = new ArrayList<String>();
		
		// On splitte
		String[] packagesArray = packages.split(",");
		
		// Parcours
		for (String packageEntry : packagesArray) {
			
			// Ajout dans la liste
			packagesList.add(packageEntry.trim());
		}
	}
}
