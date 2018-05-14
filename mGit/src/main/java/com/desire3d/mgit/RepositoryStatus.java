package com.desire3d.mgit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;

import com.desire3d.mgit.enums.RepositoryStatusEnum;
import com.desire3d.mgit.exceptions.LocalRepositoryException;
import com.desire3d.mgit.helper.MGitHelper;

/**
 * Class is used to get the status of repository 
 * 
 * 
 * @author Mahesh Pardeshi
 *
 */
public class RepositoryStatus {
	
	private static final Logger LOGGER = Logger.getLogger(MGitHelper.class);
	
	/**
	 * Get files added to the index
	 * 
	 * @param git
	 * @return staged changes
	 * */
	public Set<String> getFilesAddedToIndex(final Git git) throws NoWorkTreeException, GitAPIException {
		Status status = git.status().call();
		Set<String> added = status.getAdded();
		LOGGER.info("Indexed files");
		for (String change : added) {
			LOGGER.info("Added "+ change);
		}
		Set<String> changed = status.getChanged();
		for (String change : changed) {
			LOGGER.info("Changed "+ change);
		}
		Set<String> modified = status.getModified();
		for (String change : modified) {
			LOGGER.info("Modified "+ change);
		}
		Set<String> removed = status.getRemoved();
		for (String change : removed) {
			LOGGER.info("Removed "+ change);
		}
		return added;
	}
	
	/**
	 * Get files added to the index
	 * 
	 * @param localDir directory in which git repository is configured
	 * @return staged changes
	 * @throws IOException 
	 * @throws LocalRepositoryException 
	 * */
	public Set<String> getFilesAddedToIndex(final String localDir) throws NoWorkTreeException, GitAPIException, IOException, LocalRepositoryException {
		Repository repository = MGitHelper.openRepository(localDir);
		Git git = new Git(repository);
		Status status = git.status().call();
		Set<String> added = status.getAdded();
		git.close();
		return added;
	}
	
	/**
	 * Get uncommitted changes
	 * 
	 * @param git
	 * @return uncommitted changes
	 * */
	public Map<String, Set<String>> getUncommittedChanges(final Git git) throws NoWorkTreeException, GitAPIException {
		Status status = git.status().call();
		return getUncommittedChanges(status);
	}	
	
	/**
	 * Get uncommitted changes
	 * 
	 * @param localDir directory in which git repository is configured
	 * @return uncommitted changes
	 * */
	public Map<String, Set<String>> getUncommittedChanges(final String localDir) throws NoWorkTreeException, GitAPIException, LocalRepositoryException {
		Repository repository = MGitHelper.openRepository(localDir);
		Git git = new Git(repository);
		Status status = git.status().call();
		Map<String, Set<String>> uncommittedChanges = getUncommittedChanges(status);
		git.close();
		return uncommittedChanges;
	}
	
	private Map<String, Set<String>> getUncommittedChanges(final Status status) throws NoWorkTreeException, GitAPIException {
		final Map<String, Set<String>> uncommittedChanges = new HashMap<>(6);
		Set<String> added = status.getAdded();
		Set<String> changed = status.getChanged();
		Set<String> removed = status.getRemoved();
		Set<String> missing = status.getMissing();
		Set<String> modified = status.getModified();
		Set<String> conflicting = status.getConflicting();
		
		if(!added.isEmpty()) 		{ uncommittedChanges.put(RepositoryStatusEnum.ADDED.toString(), added); 			}
		if(!changed.isEmpty()) 		{ uncommittedChanges.put(RepositoryStatusEnum.CHANGED.toString(), changed);			}
		if(!removed.isEmpty()) 		{ uncommittedChanges.put(RepositoryStatusEnum.REMOVED.toString(), removed); 		}
		if(!missing.isEmpty()) 		{ uncommittedChanges.put(RepositoryStatusEnum.MISSING.toString(), missing); 		}
		if(!modified.isEmpty())		{ uncommittedChanges.put(RepositoryStatusEnum.MODIFIED.toString(), modified); 		}
		if(!conflicting.isEmpty()) 	{ uncommittedChanges.put(RepositoryStatusEnum.CONFLICTING.toString(), conflicting); }
		
		return uncommittedChanges;
	}
}