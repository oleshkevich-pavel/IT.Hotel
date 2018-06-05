package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;
import com.itacademy.jd2.po.hotel.web.dto.TaskDTO;

@Component
public class TaskToDTOConverter implements Function<ITask, TaskDTO> {

    @Override
    public TaskDTO apply(final ITask entity) {
        final TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(entity.getId());
        taskDTO.setToDo(entity.getToDo());
        taskDTO.setDescription(entity.getDescription());
        taskDTO.setExecutionDate(entity.getExecutionTime());
        taskDTO.setExecutionTime(entity.getExecutionTime());

        final IUserAccount answerable = entity.getAnswerable();
        if (answerable != null) {
            taskDTO.setAnswerableId(answerable.getId());
            taskDTO.setAnswerableEmail(answerable.getEmail());
        }

        TaskPriority priority = entity.getPriority();
        if (priority != null) {
            taskDTO.setPriority(priority.name());
        }

        final IUserAccount creator = entity.getCreator();
        if (creator != null) {
            taskDTO.setCreatorId(creator.getId());
            taskDTO.setCreatorEmail(creator.getEmail());
        }
        taskDTO.setExecuted(entity.getExecuted());
        taskDTO.setVersion(entity.getVersion());
        taskDTO.setCreated(entity.getCreated());
        taskDTO.setUpdated(entity.getUpdated());
        return taskDTO;
    }

}
