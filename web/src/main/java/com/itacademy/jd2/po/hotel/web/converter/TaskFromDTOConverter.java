package com.itacademy.jd2.po.hotel.web.converter;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;
import com.itacademy.jd2.po.hotel.service.ITaskService;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.dto.TaskDTO;

@Component
public class TaskFromDTOConverter implements Function<TaskDTO, ITask> {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IUserAccountService userAccountService;

    @Override
    public ITask apply(final TaskDTO dto) {
        final ITask entity = taskService.createEntity();
        entity.setId(dto.getId());
        entity.setToDo(dto.getToDo());
        entity.setDescription(dto.getDescription());

        final Date executionDate = dto.getExecutionDate();
        // if (executionDate != null) {
        final Calendar fullDateCalendar = Calendar.getInstance();
        fullDateCalendar.setTime(executionDate);

        final Date executionTime = dto.getExecutionTime();
        if (executionTime != null) {
            final Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(executionTime);
            fullDateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            fullDateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
        }
        entity.setExecutionTime(fullDateCalendar.getTime());
        // }

        final IUserAccount answerable = userAccountService.createEntity();
        answerable.setId(dto.getAnswerableId());
        entity.setAnswerable(answerable);

        entity.setPriority(TaskPriority.valueOf(dto.getPriority()));

        final IUserAccount creator = userAccountService.createEntity();
        creator.setId(dto.getCreatorId());
        entity.setCreator(creator);
        entity.setVersion(dto.getVersion());
        entity.setExecuted(dto.getExecuted());
        return entity;
    }
}
