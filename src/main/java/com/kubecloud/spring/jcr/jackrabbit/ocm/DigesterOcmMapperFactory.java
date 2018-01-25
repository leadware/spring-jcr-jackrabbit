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

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.digester.DigesterMapperImpl;
import org.springframework.core.io.Resource;

/**
 * Classe representant une fabrique de Mapper OCM basee sur les annotations
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 12 févr. 2016 - 09:44:16
 */
public class DigesterOcmMapperFactory extends OcmMapperFactory {
	
	/**
	 * Ensemble des Fichiers de mapping OCM
	 */
	private Set<Resource> mappings = null;
	
	/**
	 * Tableau de fichier de mappings OCM
	 */
	private InputStream[] mappingsTable = null;
	
	/**
	 * Etat de validation des fichiers XML
	 */
	private boolean validate = false;
	
	/**
	 * Constructeur par defaut
	 */
	public DigesterOcmMapperFactory() {}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param mappings Ensemble des fichiers de mapping OCM
	 */
	public DigesterOcmMapperFactory(Set<Resource> mappings) {
		
		// Positionnement de la liste de mappings
		this.mappings = mappings;
	}
	
	/**
	 * Methode permettant d'obtenir la valeur du champ "mappings"
	 * @return valeur du champ "mappings"
	 */
	public Set<Resource> getMappings() {
	
		// On retourne le champ "mappings"
		return mappings;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "mappings"
	 * @param mappings Nouvelle valeur du champ "mappings"
	 */
	public void setMappings(Set<Resource> mappings) {
	
		// Mise a jour du champ "this.mappings"
		this.mappings = mappings;
	}
	
	/**
	 * Methode permettant d'obtenir la valeur du champ "validate"
	 * @return valeur du champ "validate"
	 */
	public boolean isValidate() {
	
		// On retourne le champ "validate"
		return validate;
	}

	/**
	 * Methode permettant de modifier la valeur du champ "validate"
	 * @param validate Nouvelle valeur du champ "validate"
	 */
	public void setValidate(boolean validate) {
	
		// Mise a jour du champ "this.validate"
		this.validate = validate;
	}

	/* (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmMapperFactory#prepareMapper()
	 */
	@Override
	protected void prepareMapper() throws Exception {
		
		// Si la liste est nulle
		if(mappings == null) mappings = new HashSet<Resource>();
		
		// Instanciation du tableau
		mappingsTable = new InputStream[mappings.size()];
		
		// Index
		int index = 0;
		
		// Parcours des ressources
		for (Resource resource : mappings) mappingsTable[index++] = resource.getInputStream();
	}

	/* (non-Javadoc)
	 * @see com.kubecloud.spring.jcr.jackrabbit.ocm.OcmMapperFactory#createMapper()
	 */
	@Override
	protected Mapper createMapper() throws Exception {
		
		// Creation du mapper
		return new DigesterMapperImpl(mappingsTable, validate);
	}
}
