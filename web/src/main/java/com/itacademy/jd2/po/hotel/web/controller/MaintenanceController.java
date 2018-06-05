package com.itacademy.jd2.po.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.itacademy.jd2.po.hotel.dao.api.filter.MaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.service.IMaintenanceService;
import com.itacademy.jd2.po.hotel.web.converter.MaintenanceFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.MaintenanceToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.MaintenanceDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.search.MaintenanceSearchDTO;

@Controller
@RequestMapping(value = "/maintenance")
public class MaintenanceController extends AbstractController<MaintenanceDTO, MaintenanceFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = MaintenanceController.class.getSimpleName() + "_SEACH_DTO";

    @Autowired
    private IMaintenanceService maintenanceService;

    @Autowired
    private MaintenanceFromDTOConverter fromDTOConverter;

    @Autowired
    private MaintenanceToDTOConverter toDTOConverter;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) MaintenanceSearchDTO searchDTO,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<MaintenanceDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDTO = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }

        final MaintenanceFilter filter = new MaintenanceFilter();
        if (searchDTO.getName() != null) {
            filter.setName(searchDTO.getName());
        }

        if (searchDTO.getAvailable() != null && searchDTO.getAvailable()) {
            filter.setAvailable(Boolean.TRUE);
        } else {
            filter.setAvailable(null);
        }

        listDTO.setTotalCount(maintenanceService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        final List<IMaintenance> entities = maintenanceService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDTO);
        return new ModelAndView("maintenance.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", new MaintenanceDTO());

        return new ModelAndView("maintenance.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final MaintenanceDTO dto = toDTOConverter.apply(maintenanceService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);

        return new ModelAndView("maintenance.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    // @valid стоит для валидации
    public String save(@Valid @ModelAttribute("formModel") final MaintenanceDTO formModel, final BindingResult result) {
        if (result.hasErrors()) {
            return "maintenance.edit";
        } else {
            final IMaintenance entity = fromDTOConverter.apply(formModel);
            maintenanceService.save(entity);
            return "redirect:/maintenance";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        maintenanceService.delete(id);
        return "redirect:/maintenance";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IMaintenance dbModel = maintenanceService.getFullInfo(id);
        final MaintenanceDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);

        return new ModelAndView("maintenance.edit", hashMap);
    }

    private MaintenanceSearchDTO getSearchDTO(final HttpServletRequest req) {
        MaintenanceSearchDTO searchDTO = (MaintenanceSearchDTO) req.getSession().getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new MaintenanceSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }
}
