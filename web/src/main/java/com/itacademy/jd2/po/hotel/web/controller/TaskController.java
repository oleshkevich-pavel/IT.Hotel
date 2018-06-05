package com.itacademy.jd2.po.hotel.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.TaskFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;
import com.itacademy.jd2.po.hotel.service.ITaskService;
import com.itacademy.jd2.po.hotel.web.converter.TaskFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.TaskToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.TaskDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/task")
public class TaskController extends AbstractController<TaskDTO, TaskFilter> {

    @Autowired
    private ITaskService taskService;
    @Autowired
    private TaskFromDTOConverter fromDTOConverter;
    @Autowired
    private TaskToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn,
            @RequestParam(name = "error", required = false) final String error) {

        final ListDTO<TaskDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final TaskFilter filter = new TaskFilter();
        applySortAndOrder2Filter(listDTO, filter);

        filter.setFetchCreator(true);
        filter.setFetchAnswerable(true);
        final List<ITask> entities = taskService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(taskService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("task.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {
        final Map<String, Object> hashMap = new HashMap<>();
        final ITask newTask = taskService.createEntity();
        final TaskDTO dto = toDTOConverter.apply(newTask);
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("task.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final TaskDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadAllCommonItems(hashMap);
            return new ModelAndView("task.edit", hashMap);
        } else {
            final ITask entity = fromDTOConverter.apply(formModel);
            try {
                taskService.save(entity);
            } catch (OptimisticLockException e) {
                // get latest version
                final TaskDTO newFormModel = toDTOConverter.apply(taskService.getFullInfo(formModel.getId()));
                hashMap.put("error", "OptimisticLockException");
                hashMap.put("formModel", newFormModel);
                loadAllCommonItems(hashMap);
                return new ModelAndView("task.edit", hashMap);
            } catch (PersistenceException e) {
                hashMap.put("error", "PersistenceException");
                loadAllCommonItems(hashMap);
                return new ModelAndView("task.edit", hashMap);
            }
        }
        return "redirect:/task";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        taskService.delete(id);
        return "redirect:/task";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final ITask dbModel = taskService.getFullInfo(id);
        final TaskDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadAllCommonItems(hashMap);
        return new ModelAndView("task.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final TaskDTO dto = toDTOConverter.apply(taskService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("task.edit", hashMap);
    }

    private void loadAllCommonItems(final Map<String, Object> hashMap) {
        loadComboboxesPriority(hashMap);
        loadCommonFormEmployeeAccounts(hashMap);
    }

    private void loadComboboxesPriority(final Map<String, Object> hashMap) {
        final List<TaskPriority> priorityList = Arrays.asList(TaskPriority.values());
        final Map<String, String> priorityMap = priorityList.stream()
                .collect(Collectors.toMap(TaskPriority::name, TaskPriority::name));
        hashMap.put("priorityChoices", priorityMap);
    }
}
