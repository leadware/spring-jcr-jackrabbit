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
import static org.junit.Assert.assertNotNull;

import javax.jcr.Node;
import javax.jcr.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.leadware.spring.jcr.SessionFactory;

/**
 * Classe de test de la fabrique de session JCR
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 5 févr. 2016 - 17:20:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/jackrabbit-session-factory-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JcrSessionfactoryTest {
	
	/**
	 * Fabrique de session JCR
	 */
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * Methode permettant de tester la creation d'un noeud
	 * @throws Exception Exception potentielle
	 */
	@Test
	public void createNodeTest() throws Exception {
		
		// Nom du noeud
		String nodeName = "myNodeTest";
		
		// Obtention de la session
		Session session = sessionFactory.getSession();
		
		// Obtention du noeud root
		Node rootNode = session.getRootNode();
		
		// Creation du noeud
		rootNode.addNode(nodeName);
		
		// Enregistrement
		session.save();
		
		// Recherche du noeud
		Node node = session.getNode("/" + nodeName);
		
		// Verification d'existence
		assertNotNull(node);
		
		// Suppression du noeud
		node.remove();
		
		// Enregistrement
		session.save();
		
		// Verification de non existence
		assertFalse(session.nodeExists("/" + nodeName));
		
		// Fermeture de la session
		session.logout();
	}
}
