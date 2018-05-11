package com.desire3d.mgit;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.desire3d.mgit.exceptions.LocalRepositoryException;
import com.desire3d.mgit.helper.MGitHelper;

/**
 * Class to commit staged changes to git repository 
 * 
 * @author Mahesh Pardeshi
 */
public class Commit {
		
	private final static Logger LOGGER = Logger.getLogger(Commit.class);
	
	/**
	 * method to commit the staged changes 
	 * 
	 * @param git
	 * @param commitMessage  
	 * */
	public RevCommit commitAll(final Git git, final String commitMessage) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException {
		RevCommit result = git.commit()
							  .setMessage("Commit all changes including additions")
							  .call();
		LOGGER.info("Changes commited successfully, Commit ID : " + result.getId().getName());
		return result;
	}
	
	/**
	 * method to stage the changes and commit them 
	 * 
	 * @param localDir directory in which git repository is configured  
	 * @param commitMessage
	 * @throws LocalRepositoryException 
	 * */
	public RevCommit commitAll(final String localDir, final String commitMessage) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException, IllegalArgumentException, IOException, LocalRepositoryException {
		IndexFiles indexFiles = new IndexFiles();
		Git git = indexFiles.addAllFilesToIndex(localDir);
		
		RevCommit result = git.commit()
							.setMessage("Commit all changes including additions")
							.call();
		LOGGER.info("Changes commited successfully, Commit ID : " + result.getId().getName());
		return result;
	}
	
	/**
	 * method to commit the staged changes and push to remote repository 
	 * 
	 * @param git
	 * @param commitMessage
	 * */
	public Iterable<PushResult> commitAllAndPush(final Git git, final String commitMessage, final String username, final String password) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException {
		RevCommit result = git.commit()
		   .setMessage(commitMessage)
		   .call();
		LOGGER.info("Changes commited successfully, Commit ID : " + result.getId().getName());
		return push(git, username, password);
	}
	
	/**
	 * method to stage the changes and commit them to remote repository
	 * 
	 * @param localDir directory in which git repository is configured  
	 * @param commitMessage
	 * @throws LocalRepositoryException 
	 * */
	public Iterable<PushResult> commitAllAndPush(final String localDir, final String commitMessage, final String username, final String password) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException, IllegalArgumentException, IOException, LocalRepositoryException {
		IndexFiles indexFiles = new IndexFiles();
		Git git = indexFiles.addAllFilesToIndex(localDir);
		
		RevCommit result = git.commit()
		   .setMessage(commitMessage)
		   .call();
		
		LOGGER.info("Changes commited successfully, Commit ID : " + result.getId().getName());
		return push(git, username, password);
	}
	
	/**
	 * method to stage the selected files and commit them to remote repository
	 * 
	 * @param localDir directory in which git repository is configured  
	 * @param commitMessage
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * @param filePaths - selected file paths 
	 * @throws LocalRepositoryException 
	 * */
	public Iterable<PushResult> commitSelectedAndPush(final String localDir, final String commitMessage, final String username, final String password, final Set<String> filePaths) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException, IllegalArgumentException, IOException, LocalRepositoryException {
		Git git = new Git(MGitHelper.openRepository(localDir));
		
		CommitCommand commitCommand = git.commit();
		for(final String filePath : filePaths) {
			commitCommand.setOnly(filePath);
		}
		RevCommit result = commitCommand.setMessage(commitMessage)
										.call();
		LOGGER.info("Changes commited successfully, Commit ID : " + result.getId().getName());
		return push(git, username, password);
	}
	
	/**
	 * push the commit to remote repository 
	 * 
	 * @param git
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * 
	 * @throws GitAPIException 
	 * @throws TransportException 
	 * @throws InvalidRemoteException 
	 * */
	public Iterable<PushResult> push(final Git git, final String username, final String password) throws InvalidRemoteException, TransportException, GitAPIException {
		return git.push()
				  .setPushAll()
//				  .setRemote(remote)
				  .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
				  .call();
	}
	
	/**
	 * push the commit to remote repository 
	 * 
	 * @param localDir directory in which git repository is configured
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * @throws LocalRepositoryException 
	 * 
	 * */
	public Iterable<PushResult> push(final String localDir, final String username, final String password) throws InvalidRemoteException, TransportException, GitAPIException, IOException, LocalRepositoryException {
		Git git = new Git(MGitHelper.openRepository(localDir));
		Iterable<PushResult> result = git.push()
			.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
			.setPushAll()
			.call();
		git.close();
		return result;
	}
}