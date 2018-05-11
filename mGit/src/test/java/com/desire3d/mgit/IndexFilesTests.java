package com.desire3d.mgit;

/**
 * @author Mahesh Pardeshi
 *
 */
public class IndexFilesTests {
		
	private final static String LOCAL_DIR1 = "/home/mahesh/apps/gitexamples/testapp6/.git";
	
//	@Test
	public void indexAllFilesTest() {
		IndexFiles indexFiles = new IndexFiles();
		try {
			indexFiles.addAllFilesToIndex(LOCAL_DIR1);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}