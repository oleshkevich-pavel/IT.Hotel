package com.itacademy.jd2.po.hotel.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.service.IBookingService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.web.converter.BookingToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.RoomToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.BookingDTO;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.search.BookingSearchDTO;

@Controller
@RequestMapping(value = "/chess")

public class ChessController extends AbstractController<RoomDTO, RoomFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = ChessController.class.getSimpleName() + "_SEACH_DTO";

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private RoomToDTOConverter roomToDTOConverter;

    @Autowired
    private BookingToDTOConverter bookingToDTOConverter;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) BookingSearchDTO searchDTO,
            @RequestParam(name = "week", required = false) Integer countWeek) {

        final ListDTO<RoomDTO> listRoomDTO = new ListDTO<RoomDTO>();
        final List<IRoom> roomEntities = roomService.getAllFullInfo();
        listRoomDTO.setList(roomEntities.stream().map(roomToDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listRoomDTO);

        Integer period = 31;
        List<Date> month = getMonth(req, searchDTO, countWeek, models, period);
        models.put("calendar", month);

        Map<RoomDTO, List<BookingDTO>> chess = new TreeMap<RoomDTO, List<BookingDTO>>();
        for (int i = 0; i < listRoomDTO.getList().size(); i++) {
            Integer roomNumberId = listRoomDTO.getList().get(i).getId();
            Date startDay = month.get(0);
            Date endDay = month.get(period - 1);
            final BookingFilter bookingFilter = getFilter(startDay, endDay, roomNumberId);
            final ListDTO<BookingDTO> listBookingDTO = getListBookingDTO(bookingFilter);
            chess.put(listRoomDTO.getList().get(i), buildChess(listBookingDTO.getList(), month));
        }

        models.put("chess", chess);
        return new ModelAndView("chess", models);
    }

    private List<Date> getMonth(final HttpServletRequest req, BookingSearchDTO searchDTO, Integer countWeek,
            final HashMap<String, Object> models, final Integer period) {
        Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        if (req.getMethod().equalsIgnoreCase("get")) {
            searchDTO = getSearchDTO(req);
            if (searchDTO.getCheckIn() == null) {
                searchDTO.setCheckIn(calendar.getTime());
            }
            if (countWeek == null) {
                countWeek = 0;
            } else if (countWeek == 0) {
                searchDTO.setCheckIn(calendar.getTime());
            }
            calendar.setTime(searchDTO.getCheckIn());
            calendar.add(Calendar.DAY_OF_YEAR, countWeek * 7);
            searchDTO.setCheckIn(calendar.getTime());
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
            calendar.setTime(searchDTO.getCheckIn());
        }

        calendar.add(Calendar.DAY_OF_YEAR, -15);
        models.put(SEARCH_FORM_MODEL, searchDTO);

        final List<Date> month = new ArrayList<Date>();
        for (int i = 0; i < period; i++) {
            month.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return month;
    }

    private ListDTO<BookingDTO> getListBookingDTO(final BookingFilter bookingFilter) {
        final ListDTO<BookingDTO> listBookingDTO = new ListDTO<BookingDTO>();
        // final ListDTO<BookingDTO> listBookingDTO = getListDTO(req);

        // listBookingDTO.setTotalCount(bookingService.getCount(bookingFilter));
        bookingFilter.setFetchRoom(true);
        bookingFilter.setFetchUserAccount(true);
        bookingFilter.setFetchBookingStatus(true);
        final List<IBooking> bookingEntities = bookingService.find(bookingFilter);
        listBookingDTO.setList(bookingEntities.stream().map(bookingToDTOConverter).collect(Collectors.toList()));
        // applySortAndOrder2Filter(listBookingDTO, bookingFilter);
        return listBookingDTO;
    }

    private BookingFilter getFilter(final Date startDay, final Date checkOut, final Integer roomNumberId) {
        final BookingFilter bookingFilter = new BookingFilter();
        bookingFilter.setRoomNumberId(roomNumberId);
        bookingFilter.setCheckIn(startDay);
        bookingFilter.setCheckOut(checkOut);
        return bookingFilter;
    }

    private BookingSearchDTO getSearchDTO(final HttpServletRequest req) {
        BookingSearchDTO searchDTO = (BookingSearchDTO) req.getSession().getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new BookingSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

    private List<BookingDTO> buildChess(final List<BookingDTO> listBookingDTO, final List<Date> month) {
        List<BookingDTO> reservation = new LinkedList<BookingDTO>();
        Collections.sort(listBookingDTO);
        int i = 0;
        int k = 0;
        int countBooking = listBookingDTO.size();
        if (countBooking != 0) {
            BookingDTO firstBookingDTO = listBookingDTO.get(i);
            Date checkIn = firstBookingDTO.getCheckIn();
            Date checkOut = firstBookingDTO.getCheckOut();
            Date date = month.get(i);
            if (checkIn.before(date)) {
                int daysBetween = 0;
                if (checkOut.before(month.get(month.size() - 1))) {
                    daysBetween = daysBetween(checkOut, date) + 1;
                } else {
                    daysBetween = month.size();
                }
                firstBookingDTO.setColspan(daysBetween);
                reservation.add(firstBookingDTO);
                i = i + daysBetween;
                k++;
            }
            while (i < month.size()) {
                date = month.get(i);
                if (k < countBooking) {
                    BookingDTO booking = listBookingDTO.get(k);
                    checkIn = booking.getCheckIn();
                    Integer period = booking.getPeriod();
                    if (date.equals(checkIn)) {
                        reservation.add(booking);
                        i = i + period;
                        k++;
                    } else {
                        reservation.add(null);
                        i++;
                    }
                } else {
                    reservation.add(null);
                    i++;
                }
            }
        } else {
            for (int j = 0; j < month.size(); j++) {
                reservation.add(null);
            }
        }
        return reservation;
    }

    public int daysBetween(final Date d1, final Date d2) {
        return (int) (Math.abs((d2.getTime() - d1.getTime())) / (1000 * 60 * 60 * 24));
    }
}
