package net.leadware.spring.jcr.test;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.leadware.spring.jcr.JcrTemplate;

/**
 * Classe de test du templace JCR 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 7 févr. 2016 - 17:19:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/jcr-template-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JcrTemplateTest {

	/**
	 * Template JCR
	 */
	@Autowired
	@Qualifier("jcrTemplate")
	private JcrTemplate jcrTemplate;
	
	/**
	 * Methode permettant de tester l'ajout d'un noeud a partir de son chemin absolu
	 * @throws Exception Exception potentielle
	 */
	@Test
	public void addNodeWithAbsolutePathTest() throws Exception {
		
		// Chemin du noeud
		String path = "/selfpaas/datasources";
		
		// Verification de non existence
		assertFalse(jcrTemplate.nodeExists(path));
		
		// Creation
		jcrTemplate.addNode(path);
		
		// Verification d'existence
		assertTrue(jcrTemplate.nodeExists(path));
		
		// Suppression du noeud
		jcrTemplate.remove(path);
		
		// Verification de non existence
		assertFalse(jcrTemplate.nodeExists(path));
	}
}