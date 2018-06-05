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

import com.itacademy.jd2.po.hotel.dao.api.filter.GuestFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.web.converter.GuestFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.GuestToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.GuestDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/guest")
public class GuestController extends AbstractController<GuestDTO, GuestFilter> {

    @Autowired
    private IGuestService guestService;
    @Autowired
    private GuestFromDTOConverter fromDTOConverter;
    @Autowired
    private GuestToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<GuestDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final GuestFilter filter = new GuestFilter();
        applySortAndOrder2Filter(listDTO, filter);

        filter.setFetchGuestStatus(true);
        filter.setFetchUserAccount(true);
        final List<IGuest> entities = guestService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(guestService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("guest.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {
        final Map<String, Object> hashMap = new HashMap<>();
        final GuestDTO dto = new GuestDTO();
        hashMap.put("formModel", dto);
        loadCommonFormGuestStatuses(hashMap);
        // loadComboboxesRoles(hashMap);
        return new ModelAndView("guest.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final GuestDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadCommonFormGuestStatuses(hashMap);
            // loadComboboxesRoles(hashMap);
            return new ModelAndView("guest.edit", hashMap);
        } else {
            final IGuest entity = fromDTOConverter.apply(formModel);
            final IUserAccount userAccount = entity.getUserAccount();
            try {
                guestService.save(userAccount, entity);
            } catch (PersistenceException e) {
                loadCommonFormGuestStatuses(hashMap);
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("guest.edit", hashMap);
            }
            return "redirect:/guest";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        guestService.delete(id);
        return "redirect:/guest";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IGuest dbModel = guestService.getFullInfo(id);
        final GuestDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadCommonFormGuestStatuses(hashMap);
        // loadComboboxesRoles(hashMap);
        return new ModelAndView("guest.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final GuestDTO dto = toDTOConverter.apply(guestService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadCommonFormGuestStatuses(hashMap);
        // loadComboboxesRoles(hashMap);
        return new ModelAndView("guest.edit", hashMap);
    }

    @RequestMapping(value = "/myprofile", method = RequestMethod.GET)
    public ModelAndView viewMyProfile() {
        Integer id = AuthHelper.getLoggedUserId();
        final IGuest dbModel = guestService.getFullInfo(id);
        final GuestDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        return new ModelAndView("guest.myprofile", hashMap);
    }

    @RequestMapping(value = "/myprofile", method = RequestMethod.POST)
    public Object saveMyProfile(@Valid @ModelAttribute("formModel") final GuestDTO formModel,
            final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            return new ModelAndView("guest.myprofile", hashMap);
        } else {
            final IGuest entity = fromDTOConverter.apply(formModel);
            final IUserAccount userAccount = entity.getUserAccount();
            guestService.save(userAccount, entity);
            return "redirect:/";
        }
    }

    /*
     * private void loadComboboxesRoles(final Map<String, Object> hashMap) {
     * final List<Role> rolesList = Arrays.asList(Role.values()); final
     * Map<String, String> rolesMap =
     * rolesList.stream().collect(Collectors.toMap(Role::name, Role::name));
     * hashMap.put("roleChoices", rolesMap); }
     */
}
