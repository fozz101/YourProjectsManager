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

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;

            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority()==null||projectTask.getPriority()==0){
                projectTask.setPriority(3);
            }
            if(projectTask.getStatus()==""||projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);



    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        projectService.findProjectByIdentifier(id,username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
    public ProjectTask findPTbyProjectSequence(String backlog_id,String pt_id, String username){
        projectService.findProjectByIdentifier(backlog_id,username);

        ProjectTask projectTask = projectTaskRepository.findProjectTaskByProjectSequence(pt_id);
        if (projectTask==null){
            throw new ProjectNotFoundException("Project Task with ID: "+pt_id+" doesn't exist !");
        }
        if (!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task "+pt_id+" does not exist in the project: "+backlog_id);
        }

        return projectTask;
    }
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTbyProjectSequence(backlog_id,pt_id,username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTbyProjectSequence(backlog_id,pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
