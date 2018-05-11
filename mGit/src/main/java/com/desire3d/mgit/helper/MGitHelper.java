package com.desire3d.mgit.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.desire3d.mgit.CloneRepository;
import com.desire3d.mgit.Commit;
import com.desire3d.mgit.IndexFiles;
import com.desire3d.mgit.PullRequest;
import com.desire3d.mgit.RepositoryStatus;
import com.desire3d.mgit.exceptions.BaseMGitAPIException;
import com.desire3d.mgit.exceptions.CloneFailureException;
import com.desire3d.mgit.exceptions.CommitFailureException;
import com.desire3d.mgit.exceptions.LocalRepositoryException;
import com.desire3d.mgit.exceptions.PullFailureException;

/**
 * @author Mahesh Pardeshi
 *
 */
public abstract class MGitHelper {
	
	private static final Logger LOGGER = Logger.getLogger(MGitHelper.class);
	
	private static final String GIT_DEFAULT_DIRECTORY = ".git"; 
	
	/**
	 * method to clone repository with authentication with {@link UsernamePasswordCredentialsProvider}
	 * 
	 * @param remoteURL remote url of repository eg. https://github.com/meta-magic/mgittestrepo.git
	 * @param localDir directory in which git repository is configured  
	 * @return {@link Git}
	 * @throws CloneFailureException 
	 * */
	public static Git cloneRepository(final String remoteURL, final File localDir) throws CloneFailureException {
		CloneRepository repo = new CloneRepository();
		Git clonedGit;
		try {
			clonedGit = repo.cloneRepository(remoteURL, localDir);
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new CloneFailureException("Git remote repository cloning failed due to cause - "+ e.getMessage(), e);
		}
		return clonedGit;
	}
	
	/**
	 * method to clone repository with authentication with {@link UsernamePasswordCredentialsProvider}
	 * 
	 * @param remoteURL remote url of repository eg. https://github.com/meta-magic/mgittestrepo.git
	 * @param localDir directory in which git repository is configured  
	 * @return {@link Git}
	 * @throws CloneFailureException 
	 * */
	public static Git cloneRepository(final String remoteURL, final String username, final String password, final File localDir) throws CloneFailureException {
		CloneRepository repo = new CloneRepository();
		Git clonedGit;
		try {
			clonedGit = repo.cloneRepository(remoteURL, username, password, localDir);
		} catch (GitAPIException e) {
			e.printStackTrace();
			throw new CloneFailureException("Git remote repository cloning failed due to cause - "+ e.getMessage(), e);
		}
		return clonedGit;
	}
	
	/** 
	 * method to read and open existing local repository and return {@link Repository} object
	 * 
	 * @param localDir directory in which git repository is configured
	 * @throws LocalRepositoryException 
	 * */
	public static Repository openRepository(final String localDir) throws LocalRepositoryException {
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		Repository repository;
		try {
			repository = repositoryBuilder.setGitDir(new File(appendGitDefaultDir(localDir)))
										  .readEnvironment() 
										  .findGitDir()
										  .setMustExist(true)
										  .build();
		} catch (IOException e) {
			e.printStackTrace();
			throw new LocalRepositoryException("Git local repository open failed due to cause - " + e.getMessage(), e);
		}
		return repository;
	}
	
	/**
	 * Initial commit to the repository includes cloning remote repository and first commit of project
	 * 
	 * @param remoteURL of github repository
	 * @param username of github user
	 * @param password of github user account 
	 * @param commitMessage 
	 * @param localDir directory in which git repository is configured 
	 * @throws LocalRepositoryException, BaseMGitAPIException 
	 * 
	 * */
	public static void initialCommit(final String remoteURL, final String username, final String password, final String commitMessage, final String localDir) throws LocalRepositoryException, BaseMGitAPIException  {
		File tempFolder = null;
		try {
			tempFolder = Files.createTempDirectory("TempCloningDir").toFile();
			cloneRepository(remoteURL, username, password, tempFolder);

			Files.move(
					   Paths.get(appendGitDefaultDir(tempFolder.getAbsolutePath())), 
					   Paths.get(appendGitDefaultDir(localDir)), 
					   StandardCopyOption.REPLACE_EXISTING
			);
			LOGGER.info("Cloned repository configured to project at " + localDir);
			commitAllAndPush(username, password, commitMessage, localDir);
		} catch (Throwable e) {
			e.printStackTrace();
			if (e instanceof BaseMGitAPIException) {
				throw (BaseMGitAPIException) e;
			}
			throw new LocalRepositoryException(e.getMessage(), e);
		}
	}
	
	/**
	 * Call to index all changes, commit locally and push to remote repository 
	 * 
	 * @param username of github user
	 * @param password of github user account 
	 * @param commitMessage 
	 * @param localDir directory in which git repository is configured
	 * @throws BaseMGitAPIException 
	 * */
	public static void commitAllAndPush(final String username, final String password, final String commitMessage, final String localDir) throws CommitFailureException, BaseMGitAPIException {
		try {
			IndexFiles indexFiles = new IndexFiles();
			Git git = indexFiles.addAllFilesToIndex(localDir);
			Set<String> changes = new RepositoryStatus().getFilesAddedToIndex(git);
			for (String change : changes) {
				LOGGER.info(change);
			}
			Commit commit = new Commit();
			Iterable<PushResult> pushresult = commit.commitAllAndPush(git, commitMessage, username, password);	
			for (final PushResult result : pushresult) {
				for (final RemoteRefUpdate upd : result.getRemoteUpdates()) {
					LOGGER.info(upd.getStatus());
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			if (e instanceof BaseMGitAPIException) {
				throw (BaseMGitAPIException) e;
			}
			throw new CommitFailureException("Commit all & Push failed due to cause - " + e.getMessage(), e);
		}
	}
	
	/**
	 * Call to index selected files, commit locally and push to remote repository 
	 * 
	 * @param username of github user
	 * @param password of github user account 
	 * @param commitMessage 
	 * @param localDir directory in which git repository is configured 
	 * @param filePaths selected files relative path
	 * @throws CommitFailureException 
	 * */
	public static void commitSelectedAndPush(final String username, final String password, final String commitMessage, final String localDir, final Set<String> filePaths) throws CommitFailureException, BaseMGitAPIException {
		try {
			IndexFiles indexFiles = new IndexFiles();
			Git git = indexFiles.addAllFilesToIndex(localDir, filePaths);
			Set<String> changes = new RepositoryStatus().getFilesAddedToIndex(git);
			for (String change : changes) {
				LOGGER.info(change);
			}
			Commit commit = new Commit();
			Iterable<PushResult> pushresult = commit.commitAllAndPush(git, commitMessage, username, password);	
			for (final PushResult result : pushresult) {
				for (final RemoteRefUpdate upd : result.getRemoteUpdates()) {
					LOGGER.info(upd.getStatus());
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			if (e instanceof BaseMGitAPIException) {
				throw (BaseMGitAPIException) e;
			}
			throw new CommitFailureException("Commit all & Push failed due to cause - " + e.getMessage(), e);
		}
	}
	
	/**
	 * pull changes from remote repository to local repository 
	 * 
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * @param localDir directory in which git repository is configured
	 * @throws PullFailureException, BaseMGitAPIException 
	 * 
	 * */
	public static PullResult pull(final String username, final String password, final String localDir) throws PullFailureException, BaseMGitAPIException {
		PullRequest pullRequest = new PullRequest();
		try {
			return pullRequest.pull(localDir, username, password);
		} catch (Throwable e) {
			e.printStackTrace();
			if (e instanceof BaseMGitAPIException) {
				throw (BaseMGitAPIException) e;
			}
			throw new PullFailureException("PullRequest request failed due to cause - " + e.getMessage(), e);
		}
	}
	
	/**
	 * Get uncommitted changes
	 * 
	 * @param localDir directory in which git repository is configured
	 * @return uncommitted changes
	 * @throws LocalRepositoryException 
	 * */
	public static Map<String, Set<String>> getUncommittedChanges(final String localDir) throws LocalRepositoryException {
		try {
			return new RepositoryStatus().getUncommittedChanges(localDir);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new LocalRepositoryException("Local repository tracking uncommitted changes failed due to cause - " + e.getMessage(), e);
		}
	}
	
	/**
	 * Append the default git directory path to local dir 
	 * 
	 * @param localDir directory in which git repository is configured 
	 * @return appended absolute path of .git directory
	 * */
	private static String appendGitDefaultDir(final String localDir) {
		StringBuilder sb = new StringBuilder(localDir);
		sb.append("/");
		sb.append(GIT_DEFAULT_DIRECTORY);
		return sb.toString();
	}
	
}