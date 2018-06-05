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

import com.itacademy.jd2.po.hotel.dao.api.filter.GuestStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.service.IGuestStatusService;
import com.itacademy.jd2.po.hotel.web.converter.GuestStatusFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.GuestStatusToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.GuestStatusDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/gueststatus")
public class GuestStatusController extends AbstractController<GuestStatusDTO, GuestStatusFilter> {

    @Autowired
    private IGuestStatusService guestStatusService;
    @Autowired
    private GuestStatusFromDTOConverter fromDTOConverter;

    @Autowired
    private GuestStatusToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<GuestStatusDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final GuestStatusFilter filter = new GuestStatusFilter();
        listDTO.setTotalCount(guestStatusService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        final List<IGuestStatus> entities = guestStatusService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("gueststatus.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", new GuestStatusDTO());

        return new ModelAndView("gueststatus.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final GuestStatusDTO dto = toDTOConverter.apply(guestStatusService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);

        return new ModelAndView("gueststatus.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final GuestStatusDTO formModel, final BindingResult result) {
        if (result.hasErrors()) {
            return "gueststatus.edit";
        } else {
            final IGuestStatus entity = fromDTOConverter.apply(formModel);
            try {
            guestStatusService.save(entity);
        } catch (PersistenceException  e) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("error", "PersistenceException");
            return new ModelAndView("gueststatus.edit", hashMap);
        }
            return "redirect:/gueststatus";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        guestStatusService.delete(id);
        return "redirect:/gueststatus";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IGuestStatus dbModel = guestStatusService.getFullInfo(id);
        final GuestStatusDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);

        return new ModelAndView("gueststatus.edit", hashMap);
    }
}
