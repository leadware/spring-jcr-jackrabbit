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

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.MergeException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.PathNotFoundException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.InvalidQueryException;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;

import net.leadware.spring.jcr.exceptions.JcrSystemException;

/**
 * Classe d'aide a la gestion des exception JCR
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 6 févr. 2016 - 12:44:42
 */
public class JcrExceptionUtils {

	/**
	 * Methode permettant de convertir les exception JCR en exception Data
	 * @param exception	Exception JCR
	 * @return	Exception Data
	 */
	public static DataAccessException translateException(Exception exception) {
		
		// Si l'exception est de type acces refuse
		if (exception instanceof AccessDeniedException) {
			
			// On leve une exception d'echec d'obtention de donnees
			return new DataRetrievalFailureException("selfpaas.tools.jcr.accessdenied", exception);
		}
		
		// Si l'exception est de type violation de contraintes
		if (exception instanceof ConstraintViolationException) {
			
			// On leve une exception de violation de contrainte
			return new DataIntegrityViolationException("selfpaas.tools.jcr.constraint.violation", exception);
		}
		
		// Si l'exception est de type Etat invalide
		if (exception instanceof InvalidItemStateException) {
			
			// On leve une exception d'accees concurent
			return new ConcurrencyFailureException("selfpaas.tools.jcr.invalid.state", exception);
		}
		
		// Si l'exception est de type requete invalide
		if (exception instanceof InvalidQueryException) {
			
			// On leve une exception d'echec d'obtention de donnees
			return new DataRetrievalFailureException("selfpaas.tools.jcr.invalid.query", exception);
		}
		
		// Si l'exception est de type donnees non serialisable
		if (exception instanceof InvalidSerializedDataException) {
			
			// On leve une exception d'echec d'obtention de donnees
			return new DataRetrievalFailureException("selfpaas.tools.jcr.invalid.serializable.data", exception);
		}
		
		// Si l'exception est de type Element existant
		if (exception instanceof ItemExistsException) {
			
			// On leve une exception de violation de contrainte
			return new DataIntegrityViolationException("selfpaas.tools.jcr.item.already.exists", exception);
		}
		
		// Si l'exception est de type element inexistant
		if (exception instanceof ItemNotFoundException) {
			
			// On leve une exception d'echec d'obtention de donnees
			return new DataRetrievalFailureException("selfpaas.tools.jcr.item.not.found", exception);
		}
		
		// Si l'exception est de type echec login
		if (exception instanceof LoginException) {
			
			// On leve une exception d'echec d'acces aux donnees
			return new DataAccessResourceFailureException("selfpaas.tools.jcr.login.invalid.data", exception);
		}
		
		// Si l'exception est de type lock
		if (exception instanceof LockException) {
			
			// On leve une exception d'acces concurent
			return new ConcurrencyFailureException("selfpaas.tools.jcr.locked.data", exception);
		}
		
		// Si l'exception est de type fusion
		if (exception instanceof MergeException) {
			
			// On leve une exception de violation de contraintes
			return new DataIntegrityViolationException("selfpaas.tools.jcr.merge.failed", exception);
		}
		
		// Si l'exception est de type workspace inexistant
		if (exception instanceof NoSuchWorkspaceException) {
			
			// On leve une exception d'acces aux données
			return new DataAccessResourceFailureException("selfpaas.tools.jcr.workspace.not.found", exception);
		}
		
		// Si l'exception est de type chemin introuvable
		if (exception instanceof PathNotFoundException) {
			
			// On leve une exception d'acces aux donnees
			return new DataRetrievalFailureException("selfpaas.tools.jcr.item.not.found", exception);
		}
		
		// Si l'exception est de type contraintes referentielles
		if (exception instanceof ReferentialIntegrityException) {
			
			// On leve une exception de violation de contraintes
			return new DataIntegrityViolationException("selfpaas.tools.jcr.referential.constraint.violation", exception);
		}
		
		// On retourne une exception generique
		return new JcrSystemException(exception);
	}
}
