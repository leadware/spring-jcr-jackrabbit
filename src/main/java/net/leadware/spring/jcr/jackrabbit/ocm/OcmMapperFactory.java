package net.leadware.spring.jcr.jackrabbit.ocm;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Classe representant une fabrique de mapper OCM
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 12 févr. 2016 - 09:25:12
 */
public abstract class OcmMapperFactory implements DisposableBean, FactoryBean<Mapper>,
		InitializingBean {

	/**
	 * Loggeur
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Mappeur OCM
	 */
	private Mapper mapper;
	
	/*(non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public Mapper getObject() throws Exception {
		
		// On retourne le mappeur
		return mapper;
	}
	
	/*(non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		
		// On retourne la classe du mapper
		return Mapper.class;
	}
	
	/*(non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		
		// On retourne true
		return true;
	}
	
	/*(non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
		// Preparation du mappeur
		prepareMapper();
		
		// Creation du mappeur
		mapper = createMapper();
	}
	
	/*(non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {}
	
	/**
	 * Methode permettant de preparer la configuration du mappeur OCM
	 * @throws Exception Exception Potentielle
	 */
	protected abstract void prepareMapper() throws Exception;
	
	/**
	 * Methode permettant de creer le mappeur OCM
	 * @return	Mappeur OCM
	 * @throws Exception Exception Potentielle
	 */
	protected abstract Mapper createMapper() throws Exception;
}