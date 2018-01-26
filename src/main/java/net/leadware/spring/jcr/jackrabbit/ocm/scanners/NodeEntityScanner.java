package net.leadware.spring.jcr.jackrabbit.ocm.scanners;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * Classe representant un scanner des entites OCM
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 4 févr. 2016 - 21:44:47
 */
public class NodeEntityScanner {

	/**
	 * Resolveur de ressources par pattern 
	 */
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	/**
	 * Fabrique des lecteurs de metadonnees 
	 */
    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
    
	/**
	 * Packages de base
	 */
	private List<String> basePackages;
	
	/**
	 * Constructeur par defaut
	 */
	public NodeEntityScanner() {}
	
	/**
	 * Costructeur avec initialisation des parametres
	 * @param basePackages	Package de base du scan
	 */
	public NodeEntityScanner(List<String> basePackages) {
		
		// Positionnement des packages de base du scan
		this.basePackages = basePackages;
	}
	
	/**
	 * Methode permettant de scanner les packages de base
	 * @return	La liste des entites node
	 * @throws IOException Exception potentielle
	 * @throws ClassNotFoundException Exception Potentielle
	 */
	@SuppressWarnings("rawtypes")
	public List<Class> scan() throws IOException, ClassNotFoundException {
		
		// Si la liste des packages est vide
		if (basePackages == null || basePackages.isEmpty()) return null;
		
		// Ensemble des classes
		Set<Class<?>> nodeEntities = new HashSet<Class<?>>();

		// Parcours des packages
		for (String basePackage : basePackages) {
			
			// Pattern du chemin de recherche
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + 
									   ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) +
									   "/" + 
									   "**/*.class";
			
			// Obtention de toutes les ressources de ce chemin de recherche
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			
			// Si la liste est vide
			if(resources == null || resources.length == 0) return null;
			
			// Parcours
			for (Resource resource : resources) {

				// Lecteur de metadonnees sur la ressources
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				
				// Si la ressource est eligible
				if(isCandidate(resource, metadataReader)) nodeEntities.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
			}
		}
		
		// On retourne la liste des classes
		return new ArrayList<Class>(nodeEntities);
	}
	
	/**
	 * Methode permettant de verifier qu'une ressource est eligible pour le scanner
	 * @param resource	Ressource en cours
	 * @param metadataReader Lecteur de metadonnees sur la ressources
	 * @return	Etat d'eligibilite
	 */
	private boolean isCandidate(Resource resource, MetadataReader metadataReader) {
		
		try {

			// Si la ressource est en lecture
			if(resource.isReadable()) {
				
				// Chargement de la classe
				Class<?> cls = Class.forName(metadataReader.getClassMetadata().getClassName());
				
				// Si la classe porte l'annotation @Node
				if(cls.getAnnotation(Node.class) != null) return true;
			}

			// On retourne false
			return false;
			
		} catch (Exception e) {
			
			// On retourne false
			return false;
		}
	}
}
