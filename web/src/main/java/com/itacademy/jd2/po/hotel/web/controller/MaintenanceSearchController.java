package com.itacademy.jd2.po.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.MaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IMaintenanceService;
import com.itacademy.jd2.po.hotel.web.converter.MaintenanceToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.BookedMaintenanceDTO;
import com.itacademy.jd2.po.hotel.web.dto.MaintenanceDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.Slider;
import com.itacademy.jd2.po.hotel.web.dto.search.MaintenanceSearchDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/maintenancesearch")
public class MaintenanceSearchController extends AbstractController<MaintenanceDTO, MaintenanceFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = MaintenanceSearchController.class.getSimpleName() + "_SEACH_DTO";

    @Autowired
    private IGuestService guestService;
    @Autowired
    private IMaintenanceService maintenanceService;
    @Autowired
    private MaintenanceToDTOConverter maintenanceToDTOConverter;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @Valid @ModelAttribute(SEARCH_FORM_MODEL) MaintenanceSearchDTO searchDTO,
            @ModelAttribute("formModel") BookedMaintenanceDTO bookedMaintenanceDTO,
            @RequestParam(name = "page", required = false) final Integer pageNumber) {

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDTO = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }

        final ListDTO<MaintenanceDTO> listMaintenanceDTO = getListDTO(req);
        listMaintenanceDTO.setPage(pageNumber);
        listMaintenanceDTO.setSort("name:asc");

        final MaintenanceFilter filter = applyMaintenanceFilter(searchDTO);

        listMaintenanceDTO.setTotalCount(maintenanceService.getCount(filter));
        applySortAndOrder2Filter(listMaintenanceDTO, filter);

        final List<IMaintenance> entities = maintenanceService.find(filter);

        Integer discount = 0;
        if (!AuthHelper.isUserAnonymous()) {
            discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
        }
        double d = discount;
        entities.stream().forEach(s -> s.setActualPrice(s.getActualPrice() * (1 - d / 100)));

        listMaintenanceDTO.setList(entities.stream().map(maintenanceToDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        loadCommonFormAvailableMaintenances(models);
        models.put(ListDTO.SESSION_ATTR_NAME, listMaintenanceDTO);
        models.put(SEARCH_FORM_MODEL, searchDTO);

        models.put(Slider.SESSION_SLIDER_NAME, getSlider(searchDTO));
        return new ModelAndView("maintenancesearch.list", models);
    }

    private MaintenanceFilter applyMaintenanceFilter(final MaintenanceSearchDTO searchDTO) {
        final MaintenanceFilter filter = new MaintenanceFilter();

        filter.setAvailable(Boolean.TRUE);
        if (searchDTO.getId() != null) {
            filter.setName(maintenanceService.getFullInfo(searchDTO.getId()).getName());
        }

        Double priceMin = searchDTO.getPriceMin();
        if (priceMin != null) {
            priceMin = getFilterPrice(priceMin);
            filter.setPriceMin(priceMin);
        }
        Double priceMax = searchDTO.getPriceMax();
        if (priceMax != null) {
            priceMax = getFilterPrice(priceMax);
            filter.setPriceMax(priceMax);
        }
        return filter;
    }

    private Double getFilterPrice(Double price) {
        if (!AuthHelper.isUserAnonymous()) {
            double discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
            if (discount == 100) {
                price = 0.00;
            } else {
                price = price * 100 / (100 - discount);
            }
        }
        return price;
    }

    private Slider getSlider(final MaintenanceSearchDTO searchDTO) {
        Double max = maintenanceService.getMaxPrice();
        if (!AuthHelper.isUserAnonymous()) {
            double discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
            max = Math.ceil(max * (1 - discount / 100));
        }
        if (max == null) {
            max = 100.00;
        }

        Double valueMin = searchDTO.getPriceMin();
        if (valueMin == null) {
            valueMin = 0.00;
        }
        Double valueMax = searchDTO.getPriceMax();
        if (valueMax == null) {
            valueMax = max;
        }
        return new Slider(valueMin, valueMax, max);
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
