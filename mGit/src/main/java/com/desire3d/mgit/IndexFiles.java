package com.desire3d.mgit;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Repository;

import com.desire3d.mgit.exceptions.LocalRepositoryException;
import com.desire3d.mgit.helper.MGitHelper;

/**
 * Add files to staging
 * @author Mahesh Pardeshi
 *
 */
public class IndexFiles {
	
	private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(IndexFiles.class);
	
	/**
	 * Adding newly added files to staging 
	 * 
	 * @param localDir directory in which git repository is configured
	 * @throws LocalRepositoryException 
	 * */
	public Git addAllNewFilesToIndex(final String localDir) throws IllegalArgumentException, IOException, NoFilepatternException, GitAPIException, LocalRepositoryException {
		Repository repository = MGitHelper.openRepository(localDir);
		Git git = new Git(repository);
		
		git.add()
		   .addFilepattern(".")
		   .call();
		
		LOGGER.info("Files added to staging");
		return git;
	}
	
	/**
	 * Adding files to staging including deleted and updated file changes 
	 * 
	 * @param localDir directory in which git repository is configured
	 * @throws LocalRepositoryException 
	 * */
	public Git addAllFilesToIndex(final String localDir) throws IllegalArgumentException, IOException, NoFilepatternException, GitAPIException, LocalRepositoryException {
		Repository repository = MGitHelper.openRepository(localDir);
		Git git = new Git(repository);
		
		git.add()
		   .addFilepattern(".")
		   .call();
		
		/* adding updated files to staging */
		git.add()
		   .setUpdate(true)
		   .addFilepattern(".")
		   .call();
		LOGGER.info("Files added to staging");
		return git;
	}
	
	/**
	 * Adding a given file to staging  
	 * 
	 * @param localDir directory in which git repository is configured
	 * @param filePath Add a path to a file whose content should be added. 
	 * @throws LocalRepositoryException 
	 * */
	public Git addAllFilesToIndex(final String localDir, final String filePath) throws IllegalArgumentException, IOException, NoFilepatternException, GitAPIException, LocalRepositoryException {
		Repository repository = MGitHelper.openRepository(localDir);
		Git git = new Git(repository);
		
		git.add()
		   .addFilepattern(filePath)
		   .call();
		
		/* adding updated files to staging */
		git.add()
		   .setUpdate(true)
		   .addFilepattern(filePath)
		   .call();
		LOGGER.info("Files added to staging");
		return git;
	}
	
	/**
	 * Adding a given file to staging  
	 * 
	 * @param localDir directory in which git repository is configured
	 * @param filePath Add a path to a file whose content should be added. 
	 * @throws LocalRepositoryException 
	 * */
	public Git addAllFilesToIndex(final String localDir, final Set<String> filePaths) throws IllegalArgumentException, IOException, NoFilepatternException, GitAPIException, LocalRepositoryException {
		Repository repository = MGitHelper.openRepository(localDir);
		Git git = new Git(repository);
		AddCommand addCommand = new AddCommand(git.getRepository());
		for (final String filepath : filePaths) {
			addCommand.addFilepattern(filepath);
//			addCommand.addFilepattern(filepath).setUpdate(true);
		}
		addCommand.call();
		LOGGER.info("Files added to staging ");
		return git;
	}
}