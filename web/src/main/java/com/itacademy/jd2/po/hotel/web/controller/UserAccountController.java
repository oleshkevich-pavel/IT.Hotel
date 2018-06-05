package com.itacademy.jd2.po.hotel.web.controller;

import java.util.Arrays;
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

import com.itacademy.jd2.po.hotel.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.converter.UserAccountFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.UserAccountToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.UserAccountDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/useraccount")
public class UserAccountController extends AbstractController<UserAccountDTO, UserAccountFilter> {

    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private UserAccountFromDTOConverter fromDTOConverter;
    @Autowired
    private UserAccountToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<UserAccountDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final UserAccountFilter filter = new UserAccountFilter();
        applySortAndOrder2Filter(listDTO, filter);

        final List<IUserAccount> entities = userAccountService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(userAccountService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("useraccount.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        final UserAccountDTO dto = new UserAccountDTO();
        hashMap.put("formModel", dto);
        loadComboboxesRoles(hashMap);
        return new ModelAndView("useraccount.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final UserAccountDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadComboboxesRoles(hashMap);
            return new ModelAndView("useraccount.edit", hashMap);
        } else {
            final IUserAccount entity = fromDTOConverter.apply(formModel);
            try {
                userAccountService.save(entity);
            } catch (PersistenceException e) {
                hashMap.put("error", "PersistenceException");
                loadComboboxesRoles(hashMap);
                return new ModelAndView("useraccount.edit", hashMap);
            }
            return "redirect:/useraccount";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        userAccountService.delete(id);
        return "redirect:/useraccount";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IUserAccount dbModel = userAccountService.getFullInfo(id);
        final UserAccountDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadComboboxesRoles(hashMap);
        return new ModelAndView("useraccount.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final UserAccountDTO dto = toDTOConverter.apply(userAccountService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadComboboxesRoles(hashMap);
        return new ModelAndView("useraccount.edit", hashMap);
    }

    private void loadComboboxesRoles(final Map<String, Object> hashMap) {
        final List<Role> rolesList = Arrays.asList(Role.values());
        final Map<String, String> rolesMap = rolesList.stream().collect(Collectors.toMap(Role::name, Role::name));
        hashMap.put("roleChoices", rolesMap);
    }
}
