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

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Interface des fabriques de session Jackrabbit OCM 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 7 févr. 2016 - 11:13:21
 */
public interface OcmSessionFactory extends InitializingBean, DisposableBean {
	
	/**
	 * Methode permettant d'obtenir une session OCM
	 * @return	Session OCM creee
	 * @throws RepositoryException Exception Potentielle
	 */
	public ObjectContentManager getOCM() throws RepositoryException;
}