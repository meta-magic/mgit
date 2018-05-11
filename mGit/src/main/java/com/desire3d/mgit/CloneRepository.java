package com.desire3d.mgit;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * Class to clone remote git repository 
 * 
 * @author Mahesh Pardeshi
 *
 */
public class CloneRepository {

	private static final Logger LOGGER = Logger.getLogger(CloneRepository.class);

	/**
	 * method to clone repository without authentication (only for public repositories)
	 * 
	 * @param remoteURL remote url of repository eg. https://github.com/meta-magic/mgittestrepo.git
	 * @param localDir directory in which git repository is configured  
	 * @return {@link Git}
	 * @throws GitAPIException 
	 * @throws TransportException 
	 * @throws InvalidRemoteException 
	 * */
	public Git cloneRepository(final String remoteURL, final String localDir) throws InvalidRemoteException, TransportException, GitAPIException {
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir);
		Git git = Git.cloneRepository()
					.setURI(remoteURL)
//					.setNoCheckout(true) // property will not checkout the remote repository content
					.setDirectory(new File(localDir))
					.call();
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir + " process successfully completed");
		return git;
	}
	
	/**
	 * method to clone repository without authentication (only for public repositories)
	 * 
	 * @param remoteURL remote url of repository eg. https://github.com/meta-magic/mgittestrepo.git
	 * @param localDir directory in which git repository is configured  
	 * @return {@link Git}
	 * @throws GitAPIException 
	 * @throws TransportException 
	 * @throws InvalidRemoteException 
	 * */
	public Git cloneRepository(final String remoteURL, final File localDir) throws InvalidRemoteException, TransportException, GitAPIException {
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir);
		Git git = Git.cloneRepository()
					.setURI(remoteURL)
					.setNoCheckout(true) // property will not checkout the remote repository content
					.setDirectory(localDir)
					.call();
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir + " process successfully completed");
		return git;
	}
	
	/**
	 * method to clone repository with authentication with {@link UsernamePasswordCredentialsProvider}
	 * 
	 * @param remoteURL remote url of repository eg. https://github.com/meta-magic/mgittestrepo.git
	 * @param localDir directory in which git repository is configured  
	 * @return {@link Git}
	 * @throws GitAPIException 
	 * @throws TransportException 
	 * @throws InvalidRemoteException 
	 * */
	public Git cloneRepository(final String remoteURL, final String username, final String password, final String localDir) throws InvalidRemoteException, TransportException, GitAPIException {
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir);
		Git git = Git.cloneRepository()
					 .setURI(remoteURL)
					 .setDirectory(new File(localDir))
					 .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
//					 .setNoCheckout(true) // property will not checkout the remote repository content
					 .call();
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir + " process successfully completed");
		return git;
	}
	
	/**
	 * method to clone repository with authentication with {@link UsernamePasswordCredentialsProvider}
	 * 
	 * @param remoteURL remote url of repository eg. https://github.com/meta-magic/mgittestrepo.git
	 * @param localDir directory in which git repository is configured  
	 * @return {@link Git}
	 * @throws GitAPIException 
	 * @throws TransportException 
	 * @throws InvalidRemoteException 
	 * */
	public Git cloneRepository(final String remoteURL, final String username, final String password, final File localDir) throws InvalidRemoteException, TransportException, GitAPIException {
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir);
		Git git = Git.cloneRepository()
					 .setURI(remoteURL)
					 .setDirectory(localDir)
					 .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
//					 .setNoCheckout(true) // property will not checkout the remote repository content
					 .call();
		LOGGER.info("Cloning repository from " + remoteURL + " to " + localDir + " process successfully completed");
		return git;
	}
}