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

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Interface des actions JCR (utilisee par le template JCR pour executer une action sur la session JCR)
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 5 févr. 2016 - 11:03:10
 */
public interface JcrAction {
	
	/**
	 * Methode permettant d'executer une action sur la session JCR
	 * @param session	session JCR
	 * @return	Objet resultant
	 * @throws IOException Exception Potentielle
	 * @throws RepositoryException Exception potentielle
	 */
	public Object execute(Session session) throws IOException, RepositoryException;
}