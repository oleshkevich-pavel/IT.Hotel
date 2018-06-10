package com.itacademy.jd2.po.hotel.web.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.TaskFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.service.ITaskService;
import com.itacademy.jd2.po.hotel.web.converter.RoomToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.TaskToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;
import com.itacademy.jd2.po.hotel.web.dto.TaskDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.search.TaskSearchDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/todaytask")
public class TodayTaskController extends AbstractController<TaskDTO, TaskFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = TaskController.class.getSimpleName() + "_SEACH_DTO";

    @Autowired
    private ITaskService taskService;
    @Autowired
    private TaskToDTOConverter taskToDTOConverter;
    @Autowired
    private RoomToDTOConverter roomToDTOConverter;
    @Autowired
    private IRoomService roomService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req, @ModelAttribute(SEARCH_FORM_MODEL) TaskSearchDTO searchDTO,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDTO = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }

        final ListDTO<TaskDTO> listDTO = getTodayTasks(req, searchDTO, pageNumber, sortColumn);
        final ListDTO<RoomDTO> dirtyRoomsListDTO = getDirtyRooms();
        final ListDTO<RoomDTO> brokenRoomsListDTO = getBrokenRooms();

        final HashMap<String, Object> models = new HashMap<>();
        loadCommonFormEmployeeAccounts(models);
        models.put("dirtyRooms", dirtyRoomsListDTO);
        models.put("brokenRooms", brokenRoomsListDTO);
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDTO);
        return new ModelAndView("todaytask.list", models);
    }

    @RequestMapping(value = "/{id}/done", method = RequestMethod.GET)
    public Object done(@PathVariable(name = "id", required = true) final Integer id) {
        final ITask entity = taskService.getFullInfo(id);
        if (entity != null) {
            entity.setExecuted(true);
            taskService.save(entity);
        }
        return "redirect:/todaytask";
    }

    @RequestMapping(value = "/{roomId}/clean", method = RequestMethod.GET)
    public Object clean(@PathVariable(name = "roomId", required = true) final Integer roomId) {
        final IRoom entity = roomService.getFullInfo(roomId);
        if (entity != null) {
            entity.setDirty(false);
            roomService.save(entity);
        }
        return "redirect:/todaytask";
    }

    @RequestMapping(value = "/{roomId}/repair", method = RequestMethod.GET)
    public Object repair(@PathVariable(name = "roomId", required = true) final Integer roomId) {
        final IRoom entity = roomService.getFullInfo(roomId);
        if (entity != null) {
            entity.setBroken(false);
            roomService.save(entity);
        }
        return "redirect:/todaytask";
    }

    private TaskSearchDTO getSearchDTO(final HttpServletRequest req) {
        TaskSearchDTO searchDTO = (TaskSearchDTO) req.getSession().getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new TaskSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

    private TaskFilter applyTaskFilter(final TaskSearchDTO searchDTO) {
        final TaskFilter filter = new TaskFilter();

        if (searchDTO.getExecutionDate() == null) {
            Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            searchDTO.setExecutionDate(calendar.getTime());
        }
        filter.setExecutionDate(searchDTO.getExecutionDate());

        if (searchDTO.getExecuted() != null) {
            filter.setExecuted(searchDTO.getExecuted());
        }

        if (searchDTO.getAnswerableId() != null) {
            filter.setAnswerableId(searchDTO.getAnswerableId());
        }

        if (searchDTO.getExecuted() != null && searchDTO.getExecuted()) {
            filter.setExecuted(Boolean.FALSE);
        } else {
            filter.setExecuted(null);
        }

        filter.setFetchAnswerable(true);
        filter.setFetchCreator(true);

        if (!AuthHelper.isUserAnonymous() && "[ROLE_EMPLOYEE]".equals(AuthHelper.getLoggedUserRole())) {
            filter.setAnswerableId(AuthHelper.getLoggedUserId());
        }
        return filter;
    }

    private ListDTO<TaskDTO> getTodayTasks(final HttpServletRequest req, final TaskSearchDTO searchDTO,
            final Integer pageNumber, final String sortColumn) {
        final ListDTO<TaskDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final TaskFilter filter = applyTaskFilter(searchDTO);

        listDTO.setTotalCount(taskService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        final List<ITask> entities = taskService.find(filter);
        listDTO.setList(entities.stream().map(taskToDTOConverter).collect(Collectors.toList()));
        return listDTO;
    }

    private ListDTO<RoomDTO> getDirtyRooms() {
        final RoomFilter filter = new RoomFilter();
        filter.setSortColumn("number");
        filter.setSortOrder(true);
        filter.setDirty(true);
        final ListDTO<RoomDTO> listDTO = new ListDTO<RoomDTO>();

        final List<IRoom> entities = roomService.find(filter);
        listDTO.setList(entities.stream().map(roomToDTOConverter).collect(Collectors.toList()));
        return listDTO;
    }

    private ListDTO<RoomDTO> getBrokenRooms() {
        final RoomFilter filter = new RoomFilter();
        filter.setSortColumn("number");
        filter.setSortOrder(true);
        filter.setBroken(true);
        final ListDTO<RoomDTO> listDTO = new ListDTO<RoomDTO>();

        final List<IRoom> entities = roomService.find(filter);
        listDTO.setList(entities.stream().map(roomToDTOConverter).collect(Collectors.toList()));
        return listDTO;
    }
}
