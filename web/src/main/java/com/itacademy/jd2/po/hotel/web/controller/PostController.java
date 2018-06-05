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

import com.itacademy.jd2.po.hotel.dao.api.filter.PostFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.service.IPostService;
import com.itacademy.jd2.po.hotel.web.converter.PostFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.PostToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.PostDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/post")
public class PostController extends AbstractController<PostDTO, PostFilter> {

    @Autowired
    private IPostService postService;
    @Autowired
    private PostFromDTOConverter fromDTOConverter;
    @Autowired
    private PostToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<PostDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final PostFilter filter = new PostFilter();
        applySortAndOrder2Filter(listDTO, filter);

        // filter.setFetchPost(true); ??
        final List<IPost> entities = postService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(postService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("post.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        final PostDTO dto = new PostDTO();
        hashMap.put("formModel", dto);
        loadCommonFormPosts(hashMap);
        return new ModelAndView("post.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final PostDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadCommonFormPosts(hashMap);
            return new ModelAndView("post.edit", hashMap);
        } else {
            final IPost entity = fromDTOConverter.apply(formModel);
            try {
                postService.save(entity);
            } catch (PersistenceException e) {
                loadCommonFormPosts(hashMap);
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("post.edit", hashMap);
            }
            return "redirect:/post";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        postService.delete(id);
        return "redirect:/post";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IPost dbModel = postService.getFullInfo(id);
        final PostDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadCommonFormPosts(hashMap);
        return new ModelAndView("post.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final PostDTO dto = toDTOConverter.apply(postService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadCommonFormPosts(hashMap);
        return new ModelAndView("post.edit", hashMap);
    }
}
