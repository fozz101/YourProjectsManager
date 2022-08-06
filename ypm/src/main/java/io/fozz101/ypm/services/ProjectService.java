package io.fozz101.ypm.services;

import io.fozz101.ypm.domain.Backlog;
import io.fozz101.ypm.domain.Project;
import io.fozz101.ypm.domain.User;
import io.fozz101.ypm.exceptions.ProjectIdException;
import io.fozz101.ypm.exceptions.ProjectNotFoundException;
import io.fozz101.ypm.repositories.BacklogRepository;
import io.fozz101.ypm.repositories.ProjectRepository;
import io.fozz101.ypm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;
    public Project saveOrUpdateProject(Project project, String username){
        if(project.getId() !=null){
            Project existingProject = projectRepository.findProjectByProjectIdentifier(project.getProjectIdentifier());
            if (existingProject != null || (!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account !");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID: "+project.getProjectIdentifier()+" cannot be updated because it does not exist !");
            }
        }


        try{
            User user = userRepository.findUserByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());


            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if (project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project Id "+ project.getProjectIdentifier().toUpperCase()+" already exists !");
        }
    }
    public Project findProjectByIdentifier(String projectId, String username){
        Project project = projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());
        if (project==null){
            throw new ProjectIdException("Project Id "+ projectId.toUpperCase()+" doesn't exist !");
        }
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }
    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }
    public void deleteProjectByIdentifier(String projectId,String username){
        Project project = findProjectByIdentifier(projectId, username);
        projectRepository.delete(project);
    }
}

