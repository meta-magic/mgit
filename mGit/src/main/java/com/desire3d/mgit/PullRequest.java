package com.desire3d.mgit;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.desire3d.mgit.exceptions.LocalRepositoryException;
import com.desire3d.mgit.helper.MGitHelper;

/**
 * @author Mahesh Pardeshi
 *
 */
public class PullRequest {
	
	/**
	 * pull changes from remote repository to local repository 
	 * 
	 * @param git
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * @return 
	 * 
	 * */
	public PullResult pull(final Git git) throws WrongRepositoryStateException, InvalidConfigurationException, InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException {
		return git.pull()
				  .call();
	}
	
	/**
	 * pull changes from remote repository to local repository 
	 * 
	 * @param localDir directory in which git repository is configured
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * @return 
	 * @throws LocalRepositoryException 
	 * 
	 * */
	public PullResult pull(final String localDir) throws WrongRepositoryStateException, InvalidConfigurationException, InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException, IOException, LocalRepositoryException {
		Git git = new Git(MGitHelper.openRepository(localDir));
		PullResult result = git.pull()
				  .call();
		git.close();
		return result;
	}
	
	/**
	 * pull changes from remote repository to local repository 
	 * 
	 * @param git
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * @return 
	 * 
	 * */
	public PullResult pull(final Git git, final String username, final String password) throws WrongRepositoryStateException, InvalidConfigurationException, InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException {
		return git.pull()
				  .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
				  .call();
	}
	
	/**
	 * pull changes from remote repository to local repository 
	 * 
	 * @param localDir directory in which git repository is configured
	 * @param username - committer's github username
	 * @param password - committer's github password
	 * @throws LocalRepositoryException 
	 * @throws GitAPIException 
	 * */
	public PullResult pull(final String localDir, final String username, final String password) throws GitAPIException, LocalRepositoryException {
		Git git = new Git(MGitHelper.openRepository(localDir));
		PullResult result = git.pull()
			.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
			.call();
		git.close();
		return result;
	}
}
