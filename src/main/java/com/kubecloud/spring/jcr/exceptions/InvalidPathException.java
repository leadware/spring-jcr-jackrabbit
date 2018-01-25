package com.kubecloud.spring.jcr.exceptions;

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

import org.springframework.dao.DataAccessException;

/**
 * Classe representant une exception de chemin invalide
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 8 févr. 2016 - 10:12:56
 */
public class InvalidPathException extends DataAccessException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur avec initialisation de parametres
	 * @param message Message	
	 */
	public InvalidPathException(String message) {
		
		// Appel parent
		super(message);
	}
	
	/**
	 * Constructeur avec initialisatio des parametres
	 * @param message	Message
	 * @param cause	Cause
	 */
	public InvalidPathException(String message, Throwable cause) {
		
		// Appel parent
		super(message, cause);
	}
}
