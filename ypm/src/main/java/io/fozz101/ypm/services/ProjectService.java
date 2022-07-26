package io.fozz101.ypm.services;

import io.fozz101.ypm.domain.Project;
import io.fozz101.ypm.exceptions.ProjectIdException;
import io.fozz101.ypm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project Id "+ project.getProjectIdentifier().toUpperCase()+" already exists !");
        }
    }
    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());
        if (project==null){
            throw new ProjectIdException("Project Id "+ projectId.toUpperCase()+" doesn't exist !");
        }
        return project;
    }
    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }
    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());
        if (project==null){
            throw new ProjectIdException("Cannot delete project with ID: "+ projectId.toUpperCase()+". This project Doesn't exist !");
        }
        projectRepository.delete(project);
    }
}

