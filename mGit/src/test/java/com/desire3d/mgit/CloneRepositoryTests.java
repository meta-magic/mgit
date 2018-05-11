package com.desire3d.mgit;

import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Test cases for clone repositories 
 * 
 * @author Mahesh Pardeshi
 *
 */
public class CloneRepositoryTests {
	
	private final static String PUBLIC_REMOTE_URL = "https://github.com/mahesh-pardeshi17/mgittest2.git";
	private final static String PRIVATE_REMOTE_URL = "https://github.com/mahesh-pardeshi17/mgittest2.git";
	private final static String USER_NAME = "gitusername";
	private final static String PASSWORD = "gitpassword";
	private final static String LOCAL_DIR1 = "/home/mahesh/apps/gitexamples/testapp_temp/";
	private final static String LOCAL_DIR2 = "/home/mahesh/apps/gitexamples/testapp_temp/";
	
//	@Test
	public void testClonRepositoryWithoutAuth() {
		CloneRepository repo = new CloneRepository();
		try {
			repo.cloneRepository(PUBLIC_REMOTE_URL, LOCAL_DIR1);
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testClonRepositoryWithAuth() {
		CloneRepository repo = new CloneRepository();
		try {
			repo.cloneRepository(PRIVATE_REMOTE_URL, USER_NAME, PASSWORD, LOCAL_DIR2);
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}
}
