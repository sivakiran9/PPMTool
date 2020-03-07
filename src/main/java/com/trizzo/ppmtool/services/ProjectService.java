package com.trizzo.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trizzo.ppmtool.domain.Backlog;
import com.trizzo.ppmtool.domain.Project;
import com.trizzo.ppmtool.domain.User;
import com.trizzo.ppmtool.exceptions.ProjectIdException;
import com.trizzo.ppmtool.exceptions.ProjectNotFoundException;
import com.trizzo.ppmtool.repositories.BacklogRepository;
import com.trizzo.ppmtool.repositories.ProjectRepository;
import com.trizzo.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository; 
	
	@Autowired
	private BacklogRepository backlogRepository; 
	
	@Autowired
	private UserRepository userRepository; 
	
	public Project saveOrUpdateProject(Project project, String username) {
		
		if(project.getId() != 0L) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account."); 
			
			} else if(existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' cannot be updated because it does not exist.");
			}
		}
		
		try {
			
			User user = userRepository.findByUsername(username);
			
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId() == 0L) {
				Backlog backlog = new Backlog(); 
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if(project.getId() != 0L) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
		} 
		catch (Exception e) 
		{
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
		}
	}
	
	public Project findProjectByIdentifier(String projectId, String username) 
	{
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if(project == null)
		{
			throw new ProjectIdException("Project ID '" + projectId + "' does not exist.");
		}
		
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account."); 
		}
		
		return project;
	}
	
	public Iterable<Project> findAllProjects(String username)
	{
		return projectRepository.findAllByProjectLeader(username); 
	}
	
	public void deleteProjectByIdentifier(String projectId, String username)
	{
		projectRepository.delete(findProjectByIdentifier(projectId, username));
	}

}

