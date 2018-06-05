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

import com.itacademy.jd2.po.hotel.dao.api.filter.CommentFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.service.ICommentService;
import com.itacademy.jd2.po.hotel.web.converter.CommentFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.CommentToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.CommentDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/comment")
public class CommentController extends AbstractController<CommentDTO, CommentFilter> {

    @Autowired
    private ICommentService commentService;
    @Autowired
    private CommentFromDTOConverter fromDTOConverter;
    @Autowired
    private CommentToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<CommentDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final CommentFilter filter = new CommentFilter();
        listDTO.setTotalCount(commentService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        filter.setFetchRoom(true);
        filter.setFetchUserAccount(true);
        final List<IComment> entities = commentService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("comment.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {
        final Map<String, Object> hashMap = new HashMap<>();
        final CommentDTO dto = new CommentDTO();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("comment.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final CommentDTO formModel, final BindingResult result) {
        if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            loadAllCommonItems(hashMap);
            return new ModelAndView("comment.edit", hashMap);
        } else {
            final IComment entity = fromDTOConverter.apply(formModel);
            commentService.save(entity);
            return "redirect:/comment";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        commentService.delete(id);
        return "redirect:/comment";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IComment dbModel = commentService.getFullInfo(id);
        final CommentDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadAllCommonItems(hashMap);
        return new ModelAndView("comment.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final CommentDTO dto = toDTOConverter.apply(commentService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("comment.edit", hashMap);
    }

    private void loadAllCommonItems(final Map<String, Object> hashMap) {
        loadCommonFormRooms(hashMap);
        loadCommonFormUserAccounts(hashMap);
    }
}
