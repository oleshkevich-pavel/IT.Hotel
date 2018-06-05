package com.itacademy.jd2.po.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.itacademy.jd2.po.hotel.dao.api.filter.EmployeeFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IEmployeeService;
import com.itacademy.jd2.po.hotel.web.converter.EmployeeFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.EmployeeToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.EmployeeDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController extends AbstractController<EmployeeDTO, EmployeeFilter> {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private EmployeeFromDTOConverter fromDTOConverter;
    @Autowired
    private EmployeeToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<EmployeeDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final EmployeeFilter filter = new EmployeeFilter();
        applySortAndOrder2Filter(listDTO, filter);

        filter.setFetchPost(true);
        filter.setFetchUserAccount(true);
        final List<IEmployee> entities = employeeService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(employeeService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("employee.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        final EmployeeDTO dto = new EmployeeDTO();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("employee.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final EmployeeDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadAllCommonItems(hashMap);
            return new ModelAndView("employee.edit", hashMap);
        } else {
            final IEmployee entity = fromDTOConverter.apply(formModel);
            final IUserAccount userAccount = entity.getUserAccount();
            try {
                employeeService.save(userAccount, entity);
            } catch (PersistenceException e) {
                loadAllCommonItems(hashMap);
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("employee.edit", hashMap);
            }
            return "redirect:/employee";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IEmployee dbModel = employeeService.getFullInfo(id);
        final EmployeeDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadAllCommonItems(hashMap);
        return new ModelAndView("employee.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        // возможно надо просто get и везде дальше
        final EmployeeDTO dto = toDTOConverter.apply(employeeService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("employee.edit", hashMap);
    }

    private void loadAllCommonItems(final Map<String, Object> hashMap) {
        loadCommonFormPosts(hashMap);
        loadComboboxesEmployeesRoles(hashMap);
    }
}
