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

import com.itacademy.jd2.po.hotel.dao.api.filter.BookedMaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.service.IBookedMaintenanceService;
import com.itacademy.jd2.po.hotel.web.converter.BookedMaintenanceFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.BookedMaintenanceFromGBMDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.BookedMaintenanceToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.BookedMaintenanceDTO;
import com.itacademy.jd2.po.hotel.web.dto.GuestBookedMaintenanceDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/bookedmaintenance")
public class BookedMaintenanceController extends AbstractController<BookedMaintenanceDTO, BookedMaintenanceFilter> {

    @Autowired
    private IBookedMaintenanceService bookedMaintenanceService;
    @Autowired
    private BookedMaintenanceFromDTOConverter fromDTOConverter;
    @Autowired
    private BookedMaintenanceToDTOConverter toDTOConverter;
    @Autowired
    private BookedMaintenanceFromGBMDTOConverter bookedMaintenanceFromGBMDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<BookedMaintenanceDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final BookedMaintenanceFilter filter = new BookedMaintenanceFilter();

        applySortAndOrder2Filter(listDTO, filter);

        filter.setFetchUserAccount(true);
        filter.setFetchMaintenance(true);
        final List<IBookedMaintenance> entities = bookedMaintenanceService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(bookedMaintenanceService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("bookedmaintenance.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        final BookedMaintenanceDTO dto = new BookedMaintenanceDTO();
        hashMap.put("formModel", dto);
        loadAllCommonForms(hashMap);
        return new ModelAndView("bookedmaintenance.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final BookedMaintenanceDTO formModel,
            final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadAllCommonForms(hashMap);
            return new ModelAndView("bookedmaintenance.edit", hashMap);
        } else {
            final IBookedMaintenance entity = fromDTOConverter.apply(formModel);
            bookedMaintenanceService.save(entity);
            return "redirect:/bookedmaintenance";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        bookedMaintenanceService.delete(id);
        return "redirect:/bookedmaintenance";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IBookedMaintenance dbModel = bookedMaintenanceService.getFullInfo(id);
        final BookedMaintenanceDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadAllCommonForms(hashMap);
        return new ModelAndView("bookedmaintenance.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final BookedMaintenanceDTO dto = toDTOConverter.apply(bookedMaintenanceService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadAllCommonForms(hashMap);
        return new ModelAndView("bookedmaintenance.edit", hashMap);
    }

    @RequestMapping(value = "/mymaintenance", method = RequestMethod.GET)
    public ModelAndView myBookedMaintenance(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<BookedMaintenanceDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final BookedMaintenanceFilter filter = new BookedMaintenanceFilter();
        filter.setFetchUserAccount(true);
        filter.setFetchMaintenance(true);
        filter.setUserAccountId(AuthHelper.getLoggedUserId());
        listDTO.setTotalCount(bookedMaintenanceService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        final List<IBookedMaintenance> entities = bookedMaintenanceService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("bookedmaintenance.mymaintenance", models);
    }

    @RequestMapping(value = "/newmaintenance/{id}", method = RequestMethod.GET)
    public ModelAndView showForm(@PathVariable(name = "id", required = true) final Integer id) {
        final Map<String, Object> hashMap = new HashMap<>();
        GuestBookedMaintenanceDTO guestBookedMaintenance = new GuestBookedMaintenanceDTO();
        guestBookedMaintenance.setMaintenanceId(id);
        loadCommonFormAvailableMaintenances(hashMap);
        hashMap.put("formModel", guestBookedMaintenance);

        return new ModelAndView("newmaintenance.edit", hashMap);
    }

    @RequestMapping(value = "/newmaintenance/add", method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final GuestBookedMaintenanceDTO formModel,
            final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadCommonFormAvailableMaintenances(hashMap);
            return new ModelAndView("newmaintenance.edit", hashMap);
        } else {
            final IBookedMaintenance entity = bookedMaintenanceFromGBMDTOConverter.apply(formModel);
            bookedMaintenanceService.save(entity);
            return "redirect:/bookedmaintenance/mymaintenance";
        }
    }

    private void loadAllCommonForms(final Map<String, Object> hashMap) {
        loadCommonFormGuestAccounts(hashMap);
        loadCommonFormAvailableMaintenances(hashMap);
    }
}
