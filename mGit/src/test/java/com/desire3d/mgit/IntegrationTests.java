package com.desire3d.mgit;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;

import com.desire3d.mgit.exceptions.LocalRepositoryException;
import com.desire3d.mgit.helper.MGitHelper;

/**
 * 
 * @author Mahesh Pardeshi
 */
public class IntegrationTests {

	private final static String LOCAL_DIR1 = "/home/mahesh/desire3d/projects/4a9c471-aa3b-4479-b70d-e24293856fusr/918f5ef8-42bd-44ee-bc74-820799da04fe/trytwo";
	private final static String USER_NAME = "gitusername";
	private final static String PASSWORD = "gitpassword";
	private final static Logger LOGGER = Logger.getLogger(IntegrationTests.class);
	
//	@Test
	public void testOne() {
		String path = "/test.ts";
		String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
		System.out.println(fileName);
	}
	
//	@Test
	public void cloningTest() {
		String remoteURL = "https://github.com/mahesh-pardeshi17/mgittest2.git";
		String localDir = "/home/mahesh/apps/gitexamples/try_temp/";
		try {
			MGitHelper.cloneRepository(remoteURL, USER_NAME, PASSWORD, new File(localDir));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void firstCommit() {
		String remoteURL = "https://github.com/mahesh-pardeshi17/jGitTryOne.git";
		String projectLocation = "/home/mahesh/desire3d/projects/4a9c471-aa3b-4479-b70d-e24293856fusr/918f5ef8-42bd-44ee-bc74-820799da04fe/trytwo";
		try {
			MGitHelper.initialCommit(remoteURL, USER_NAME, PASSWORD, "Initial Commit",  projectLocation);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void commitAllAndPush() {
		String localDir = "/home/mahesh/desire3d/projects/4a9c471-aa3b-4479-b70d-e24293856fusr/918f5ef8-42bd-44ee-bc74-820799da04fe/trytwo";
		try {
			MGitHelper.commitAllAndPush(USER_NAME, PASSWORD, " Commiting all changes", localDir);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void commitAndPush_selectedFilesTest() {
		String localDir = "/home/mahesh/desire3d/projects/4a9c471-aa3b-4479-b70d-e24293856fusr/918f5ef8-42bd-44ee-bc74-820799da04fe/trytwo";
		try {
			final Set<String> files = new HashSet<String>();
//			files.add("src/index.html");
//			files.add("src/checkfo/checkinfo/tslint.json");
 			MGitHelper.commitSelectedAndPush(USER_NAME, PASSWORD, " Checking with setOnly", localDir, files);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void getPath() throws IOException, LocalRepositoryException {
		File file = new File("/home/mahesh/desire3d/projects/4a9c471-aa3b-4479-b70d-e24293856fusr/918f5ef8-42bd-44ee-bc74-820799da04fe/trytwo/src/checkfo/checkinfo/tslint.json");
		Repository repository = MGitHelper.openRepository(LOCAL_DIR1);
	    String workTreePath = repository.getWorkTree().getCanonicalPath();
	    String pagePath = file.getCanonicalPath();

	    assert pagePath.startsWith(workTreePath);

	    pagePath = pagePath.substring(workTreePath.length());
	    
	    // git stores paths unix-style
	    pagePath = pagePath.replace(File.separatorChar, '/');
	    
	    // Skip starting '/'
	    if (pagePath.startsWith("/"))
	        pagePath = pagePath.substring(1);

	    LOGGER.info(pagePath);
	  }
	
//	@Test
	public void pullTest() {
		try {
			PullRequest pullRequest = new PullRequest();
			PullResult pullResult = pullRequest.pull(LOCAL_DIR1);
			LOGGER.info("PullRequest response : " + pullResult);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void getUncommittedChanges() {
		RepositoryStatus status = new RepositoryStatus();
		try {
			Map<String, Set<String>> uncommitedChanges = status.getUncommittedChanges(LOCAL_DIR1);
			LOGGER.info("Uncommitted changes : "+ uncommitedChanges);
		} catch (NoWorkTreeException | GitAPIException | LocalRepositoryException e) {
			e.printStackTrace();
		}
	}
}