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

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;


/**
 * Classe d'aide a l'utilisation de OCM 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 11 févr. 2016 - 20:55:13
 */
public class OcmUtils {
	
	/**
	 * Methode permettant d'obtenir le type JCR de la classe de l'entite OCM 
	 * @param entityClass	Classe de l'entite OCM
	 * @return	Type JCR
	 */
	public static String getJcrType(Class<?> entityClass) {
		
		// Si la classe est nulle
		if(entityClass == null) return null;
		
		// Recuperation de l'annotation @Node
		Node node = entityClass.getAnnotation(Node.class);
		
		// Si l'annotation est nulle
		if(node == null) return null;
		
		// On retourne le type JCR
		return node.jcrType().trim();
	}
}
