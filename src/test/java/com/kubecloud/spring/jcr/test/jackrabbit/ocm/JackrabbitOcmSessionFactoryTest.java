package com.kubecloud.spring.jcr.test.jackrabbit.ocm;

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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kubecloud.spring.jcr.jackrabbit.ocm.OcmSessionFactory;
import com.kubecloud.spring.jcr.test.jackrabbit.ocm.entities.PressRelease;

/**
 * Classe de test de la fabrique de session Jackrabbit OCM 
 * @author <a href="mailto:jetune@kubecloud.com">Jean-Jacques ETUNE NGI (Kube Cloud Enterprise Architect)</a>
 * @since 7 févr. 2016 - 11:10:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/jackrabbit-ocm-session-factory-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JackrabbitOcmSessionFactoryTest {
	
	/**
	 * Fabrique de session JCR
	 */
	@Autowired
	@Qualifier("ocmSessionFactory")
	private OcmSessionFactory ocmSessionFactory;
	
	/**
	 * Methode permettant de tester la creation d'une Presse
	 * @throws Exception Exception potentielle
	 */
	@Test
	public void createPressReleaseTest() throws Exception {
		
		// Obtention d'un OCM
		ObjectContentManager ocmSession = ocmSessionFactory.getOCM();
		
		// Instanciation de la presse a creer
		PressRelease pressRelease = new PressRelease("/newtutorial", 
				"This is the first tutorial on OCM", 
				new Date(), 
				"Helper for Jackrabbit OCM");
		
		// Verification de non existence
		assertNull(ocmSession.getObject(pressRelease.getClass(), pressRelease.getPath()));
		
		// Insertion
		ocmSession.insert(pressRelease);
		
		// Enregistrement des actions
		ocmSession.save();
		
		// Verification d'existence
		assertNotNull(ocmSession.getObject(pressRelease.getClass(), pressRelease.getPath()));
		
		// Suppression
		ocmSession.remove(pressRelease);
		
		// Enregistrement
		ocmSession.save();
		
		// Verification de non existence
		assertNull(ocmSession.getObject(pressRelease.getClass(), pressRelease.getPath()));
	}
}
