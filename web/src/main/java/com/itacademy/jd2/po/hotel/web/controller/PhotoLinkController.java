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

import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.service.IPhotoLinkService;
import com.itacademy.jd2.po.hotel.web.converter.PhotoLinkFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.PhotoLinkToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.PhotoLinkDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/photolink")
public class PhotoLinkController extends AbstractController<PhotoLinkDTO, PhotoLinkFilter> {

    @Autowired
    private IPhotoLinkService photoLinkService;
    @Autowired
    private PhotoLinkFromDTOConverter fromDTOConverter;
    @Autowired
    private PhotoLinkToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<PhotoLinkDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final PhotoLinkFilter filter = new PhotoLinkFilter();
        applySortAndOrder2Filter(listDTO, filter);

        filter.setFetchRoom(true);
        filter.setFetchUserAccount(true);
        final List<IPhotoLink> entities = photoLinkService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(photoLinkService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("photolink.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        final PhotoLinkDTO dto = new PhotoLinkDTO();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("photolink.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final PhotoLinkDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadAllCommonItems(hashMap);
            return new ModelAndView("photolink.edit", hashMap);
        } else {
            final IPhotoLink entity = fromDTOConverter.apply(formModel);
            try {
                photoLinkService.save(entity);
            } catch (PersistenceException e) {
                loadAllCommonItems(hashMap);
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("photolink.edit", hashMap);
            }
            return "redirect:/photolink";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        photoLinkService.delete(id);
        return "redirect:/photolink";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IPhotoLink dbModel = photoLinkService.getFullInfo(id);
        final PhotoLinkDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadAllCommonItems(hashMap);
        return new ModelAndView("photolink.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final PhotoLinkDTO dto = toDTOConverter.apply(photoLinkService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("photolink.edit", hashMap);
    }

    private void loadAllCommonItems(final Map<String, Object> hashMap) {
        loadCommonFormRooms(hashMap);
        loadCommonFormUserAccounts(hashMap);
    }
}
