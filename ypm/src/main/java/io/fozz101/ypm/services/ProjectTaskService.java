package io.fozz101.ypm.services;


import io.fozz101.ypm.domain.Backlog;
import io.fozz101.ypm.domain.Project;
import io.fozz101.ypm.domain.ProjectTask;
import io.fozz101.ypm.exceptions.ProjectNotFoundException;
import io.fozz101.ypm.repositories.BacklogRepository;
import io.fozz101.ypm.repositories.ProjectRepository;
import io.fozz101.ypm.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        try{
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;

            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }
            if(projectTask.getStatus()==""||projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        }catch (Exception e){
            throw new ProjectNotFoundException("Project not Found !");
        }


    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findProjectByProjectIdentifier(id);
        if (project ==null){
            throw new ProjectNotFoundException("Project with ID: "+id+" doesn't exist !");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
