package com.desire3d.mgit;

import java.util.Set;

/**
 * @author Mahesh Pardeshi
 *
 */
public class RepositoryStatusTests {

	private final static String LOCAL_DIR1 = "/home/mahesh/apps/gitexamples/testapp1/.git";

//	@Test
	public void stagingChangesTest() {
		RepositoryStatus repositoryStatus = new RepositoryStatus();
		try {
			Set<String> changes = repositoryStatus.getFilesAddedToIndex(LOCAL_DIR1);
			for (String change : changes) {
				System.out.println(change);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}