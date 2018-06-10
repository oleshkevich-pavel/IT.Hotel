package com.itacademy.jd2.po.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
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

import com.itacademy.jd2.po.hotel.dao.api.filter.MessageFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.service.IMessageService;
import com.itacademy.jd2.po.hotel.web.converter.MessageFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.MessageToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.MessageDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.search.MessageSearchDTO;

@Controller
@RequestMapping(value = "/message")
public class MessageController extends AbstractController<MessageDTO, MessageFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = MessageController.class.getSimpleName() + "_SEACH_DTO";
    private static final Object MESSAGING_ERROR = "Message was saved but not sended.";

    @Autowired
    private IMessageService messageService;
    @Autowired
    private MessageFromDTOConverter fromDTOConverter;
    @Autowired
    private MessageToDTOConverter toDTOConverter;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) MessageSearchDTO searchDTO,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<MessageDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final MessageFilter filter = new MessageFilter();

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDTO = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }

        if (searchDTO.getName() != null) {
            filter.setName(searchDTO.getName());
        }

        if (searchDTO.getPhone() != null) {
            filter.setPhone(searchDTO.getPhone());
        }

        if (searchDTO.getEmail() != null) {
            filter.setEmail(searchDTO.getEmail());
        }

        listDTO.setTotalCount(messageService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        final List<IMessage> entities = messageService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        loadCommonFormEmails(models);
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDTO);
        return new ModelAndView("message.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {
        final Map<String, Object> hashMap = new HashMap<>();
        final IMessage newMessage = messageService.createEntity();
        final MessageDTO dto = toDTOConverter.apply(newMessage);
        hashMap.put("formModel", dto);
        return new ModelAndView("message.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final MessageDTO formModel, final BindingResult result) {
        if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            return new ModelAndView("message.edit", hashMap);
        } else {
            final IMessage entity = fromDTOConverter.apply(formModel);
            try {
                messageService.save(entity);
            } catch (MessagingException e) {
                final Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("formModel", formModel);
                hashMap.put("error", MESSAGING_ERROR);
                return new ModelAndView("message.list", hashMap);
            }
            return "redirect:/message";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        messageService.delete(id);
        return "redirect:/message";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IMessage dbModel = messageService.get(id);
        final MessageDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        return new ModelAndView("message.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final MessageDTO dto = toDTOConverter.apply(messageService.get(id));
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        return new ModelAndView("message.edit", hashMap);
    }

    private MessageSearchDTO getSearchDTO(final HttpServletRequest req) {
        MessageSearchDTO searchDTO = (MessageSearchDTO) req.getSession().getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new MessageSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

    private void loadCommonFormEmails(final Map<String, Object> hashMap) {
        final List<IMessage> entities = messageService.getAllFullInfo();
        final Set<String> emails = new TreeSet<String>();
        for (final IMessage message : entities) {
            emails.add(message.getEmail());
        }
        hashMap.put("emailChoises", emails);
    }
}
