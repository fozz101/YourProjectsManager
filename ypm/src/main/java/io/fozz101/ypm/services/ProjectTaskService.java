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
    public ProjectTask findPTbyProjectSequence(String backlog_id,String pt_id){
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog==null){
            throw new ProjectNotFoundException("Project with ID: "+backlog_id+" doesn't exist !");
        }
        ProjectTask projectTask = projectTaskRepository.findProjectTaskByProjectSequence(pt_id);
        if (projectTask==null){
            throw new ProjectNotFoundException("Project Task with ID: "+pt_id+" doesn't exist !");
        }
        if (!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task "+pt_id+" does not exist in the project: "+backlog_id);
        }

        return projectTask;
    }
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask = findPTbyProjectSequence(backlog_id,pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTbyProjectSequence(backlog_id,pt_id);
        projectTaskRepository.delete(projectTask);
    }
}
