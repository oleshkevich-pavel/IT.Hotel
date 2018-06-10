package com.itacademy.jd2.po.hotel.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.itacademy.jd2.po.hotel.dao.api.filter.AbstractFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.service.IBookingStatusService;
import com.itacademy.jd2.po.hotel.service.IGuestStatusService;
import com.itacademy.jd2.po.hotel.service.IMaintenanceService;
import com.itacademy.jd2.po.hotel.service.IPostService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.SortDTO;

public abstract class AbstractController<DTO, FILTER extends AbstractFilter> {

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IMaintenanceService maintenanceService;
    @Autowired
    private IBookingStatusService bookingStatusService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IGuestStatusService guestStatusService;

    protected ListDTO<DTO> getListDTO(final HttpServletRequest req) {
        final String sessionModelName = getClass().getSimpleName() + "_LIST_MODEL";

        ListDTO<DTO> listDTO = (ListDTO<DTO>) req.getSession().getAttribute(sessionModelName);
        if (listDTO == null) {
            listDTO = new ListDTO<DTO>();

            req.getSession().setAttribute(sessionModelName, listDTO);
        }
        return listDTO;
    }

    protected void applySortAndOrder2Filter(final ListDTO<DTO> listDTO, final FILTER filter) {
        filter.setLimit(listDTO.getItemsPerPage());

        final int offset = listDTO.getItemsPerPage() * (listDTO.getPage() - 1);
        filter.setOffset(listDTO.getTotalCount() < offset ? 0 : offset);

        final SortDTO sortModel = listDTO.getSort();
        if (sortModel != null) {
            filter.setSortColumn(sortModel.getColumn());
            filter.setSortOrder(sortModel.isAscending());
        }
    }

    protected void loadCommonFormRooms(final Map<String, Object> hashMap) {
        final Map<Integer, Integer> roomsMap = roomService.getAllFullInfo().stream()
                .collect(Collectors.toMap(IRoom::getId, IRoom::getNumber));
        // сортировка по номеру комнаты
        final Map<Integer, Integer> sortedRoomsMap = roomsMap.entrySet().stream().sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        hashMap.put("roomChoices", sortedRoomsMap);
    }

    protected void loadCommonFormUserAccounts(final Map<String, Object> hashMap) {
        final Map<Integer, String> userAccountsMap = userAccountService.getAllFullInfo().stream()
                .collect(Collectors.toMap(IUserAccount::getId, IUserAccount::getEmail));
        hashMap.put("userAccountChoices", getSortedMapByValue(userAccountsMap));
    }

    protected void loadCommonFormGuestAccounts(final Map<String, Object> hashMap) {
        final List<IUserAccount> userAccountsList = userAccountService.getAllFullInfo();
        final Map<Integer, String> guestAccountsMap = new HashMap<Integer, String>();
        for (final IUserAccount userAccount : userAccountsList) {
            if (userAccount.getRole().name() == "ROLE_GUEST") {
                guestAccountsMap.put(userAccount.getId(), userAccount.getEmail());
            }
        }
        hashMap.put("guestAccountChoices", getSortedMapByValue(guestAccountsMap));
    }

    protected void loadCommonFormGuestAndEmployeesAccounts(final Map<String, Object> hashMap) {
        final List<IUserAccount> userAccountsList = userAccountService.getAllFullInfo();
        final Map<Integer, String> guestAccountsMap = new HashMap<Integer, String>();
        for (final IUserAccount userAccount : userAccountsList) {
            if ((userAccount.getRole().name() == "ROLE_GUEST") || (userAccount.getRole().name() == "ROLE_EMPLOYEE")) {
                guestAccountsMap.put(userAccount.getId(), userAccount.getEmail());
            }
        }
        hashMap.put("guestAndEmployeeAccountChoices", getSortedMapByValue(guestAccountsMap));
    }

    protected void loadCommonFormEmployeeAccounts(final Map<String, Object> hashMap) {
        final List<IUserAccount> userAccountsList = userAccountService.getAllFullInfo();
        final Map<Integer, String> guestAccountsMap = new HashMap<Integer, String>();
        for (final IUserAccount userAccount : userAccountsList) {
            if (userAccount.getRole().name() != "ROLE_GUEST") {
                guestAccountsMap.put(userAccount.getId(), userAccount.getEmail());
            }
        }
        hashMap.put("employeeChoices", getSortedMapByValue(guestAccountsMap));
    }

    protected void loadCommonFormMaintenances(final Map<String, Object> hashMap) {
        final Map<Integer, String> maintenancesMap = maintenanceService.getAllFullInfo().stream()
                .collect(Collectors.toMap(IMaintenance::getId, IMaintenance::getName));
        hashMap.put("maintenanceChoices", getSortedMapByValue(maintenancesMap));
    }

    protected void loadCommonFormAvailableMaintenances(Map<String, Object> models) {
        final Map<Integer, String> maintenancesMap = maintenanceService.getAllFullInfo().stream()
                .filter(c -> c.isAvailable() == true)
                .collect(Collectors.toMap(IMaintenance::getId, IMaintenance::getName));
        // TODO убрать одинаковые строки
        models.put("maintenanceChoices", getSortedMapByValue(maintenancesMap));
    }

    protected void loadCommonFormBookingStatuses(final Map<String, Object> hashMap) {
        final Map<Integer, String> bookingStatusesMap = bookingStatusService.getAllFullInfo().stream()
                .collect(Collectors.toMap(IBookingStatus::getId, IBookingStatus::getName));
        hashMap.put("bookingStatusChoices", getSortedMapByValue(bookingStatusesMap));
    }

    protected void loadCommonFormPosts(final Map<String, Object> hashMap) {
        final Map<Integer, String> postsMap = postService.getAllFullInfo().stream()
                .collect(Collectors.toMap(IPost::getId, IPost::getName));
        hashMap.put("postChoices", getSortedMapByValue(postsMap));
    }

    protected void loadCommonFormGuestStatuses(final Map<String, Object> hashMap) {
        final Map<Integer, String> guestStatusesMap = guestStatusService.getAllFullInfo().stream()
                .collect(Collectors.toMap(IGuestStatus::getId, IGuestStatus::getName));
        hashMap.put("guestStatusChoices", getSortedMapByValue(guestStatusesMap));
    }

    protected void loadComboboxesEmployeesRoles(final Map<String, Object> hashMap) {
        final List<Role> rolesList = Arrays.asList(Role.values());

        final Map<String, String> rolesMap = new HashMap<>();
        for (final Role role : rolesList) {
            if (role.name() != "ROLE_GUEST") {
                rolesMap.put(role.name(), role.name());
            }
        }
        hashMap.put("employeeRoleChoices", rolesMap);
    }

    private Map<Integer, String> getSortedMapByValue(final Map<Integer, String> map) {
        final Map<Integer, String> sortedMap = map.entrySet().stream().sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedMap;
    }
}
