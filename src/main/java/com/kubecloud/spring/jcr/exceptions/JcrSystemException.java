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

import org.springframework.dao.UncategorizedDataAccessException;

/**
 * Exception JCR Sysème
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 6 févr. 2016 - 13:11:44
 */
public class JcrSystemException extends UncategorizedDataAccessException {
	
	/**
	 * ID Genere par eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur avec initialisation des parametres
	 * @param message	message
	 * @param cause	Exception cause
	 */
    public JcrSystemException(String message, Throwable cause) {
    	
    	// Appel parent
        super(message, cause);
    }
    
    /**
     * Constructeur avec initialisation des parametres
     * @param cause	Exception cause
     */
    public JcrSystemException(Throwable cause) {
    	
    	// Appel parent
        super("Exception survenue lors de l'operation", cause);
    }
}
