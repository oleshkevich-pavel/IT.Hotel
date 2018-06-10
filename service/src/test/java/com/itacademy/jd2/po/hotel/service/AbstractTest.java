package com.itacademy.jd2.po.hotel.service;

import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.RoomType;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public abstract class AbstractTest {

    private static final Random RANDOM = new Random();
    @Autowired
    private IBookedMaintenanceService bookedMaintenanceService;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IGuestStatusService guestStatusService;
    @Autowired
    private IMaintenanceService maintenanceService;
    @Autowired
    private IPhotoLinkService photoLinkService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IBookingStatusService bookingStatusService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IGuestService guestService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IUnstructuredObjectService unstructuredObjectService;

    protected IComment saveNewComment() {
        final IComment entity = getCommentService().createEntity();
        entity.setUserAccount(saveNewUserAccount());
        entity.setRoom(saveNewRoom());
        entity.setComment("comment-" + getRandomInt());

        getCommentService().save(entity);
        return entity;
    }

    protected IPhotoLink saveNewPhotoLink() {
        final IPhotoLink entity = getPhotoLinkService().createEntity();
        entity.setUserAccount(saveNewUserAccount());
        entity.setRoom(saveNewRoom());
        entity.setLink("link-" + getRandomInt());

        getPhotoLinkService().save(entity);
        return entity;
    }

    protected IPost saveNewPost() {
        final IPost entity = getPostService().createEntity();
        entity.setName("name-" + getRandomInt());
        entity.setDescription("description-" + getRandomInt());
        // entity.setSupervisor(saveSupervisor());
        entity.setSupervisor(null);

        getPostService().save(entity);
        return entity;
    }

    protected ITask saveNewTask() {
        final ITask entity = getTaskService().createEntity();
        entity.setToDo("toDo-" + getRandomInt());
        entity.setDescription("description-" + getRandomInt());

        final TaskPriority[] priority = TaskPriority.values();
        int randomIndex = Math.max(0, RANDOM.nextInt(priority.length) - 1);
        entity.setPriority(priority[randomIndex]);
        entity.setExecutionTime(getRandomDate());
        entity.setAnswerable(saveNewUserAccount());
        entity.setExecuted(getRandomBoolean());
        entity.setCreator(saveNewUserAccount());

        getTaskService().save(entity);
        return entity;
    }

    protected IEmployee saveNewEmployee(final IUserAccount userAccount) {
        final IEmployee entity = getEmployeeService().createEntity();
        entity.setHiring(getRandomDate());
        entity.setLayoff(getRandomDate());
        entity.setPost(saveNewPost());
        entity.setUserAccount(userAccount);

        getEmployeeService().save(userAccount, entity);
        return entity;
    }

    protected IEmployee saveNewEmployee() {
        final IEmployee entity = getEmployeeService().createEntity();
        entity.setHiring(getRandomDate());
        entity.setLayoff(getRandomDate());
        entity.setPost(saveNewPost());

        IUserAccount userAccount = getNewUserAccount();
        entity.setUserAccount(userAccount);

        getEmployeeService().save(userAccount, entity);
        return entity;
    }

    protected IGuest saveNewGuest(final IUserAccount userAccount) throws PersistenceException {
        final IGuest entity = getGuestService().createEntity();
        entity.setVerifyKey("verifyKey-" + getRandomInt());
        entity.setVerified(getRandomBoolean());
        entity.setGuestStatus(saveNewGuestStatus());
        entity.setCredit(getRandomDouble());
        entity.setUserAccount(userAccount);

        getGuestService().save(userAccount, entity);
        return entity;
    }

    protected IGuest saveNewGuest() throws PersistenceException {
        final IGuest entity = getGuestService().createEntity();
        entity.setVerifyKey("verifyKey-" + getRandomInt());
        entity.setVerified(getRandomBoolean());
        entity.setGuestStatus(saveNewGuestStatus());
        entity.setCredit(getRandomDouble());

        IUserAccount userAccount = getNewUserAccount();
        entity.setUserAccount(userAccount);

        getGuestService().save(userAccount, entity);
        return entity;
    }

    protected IUserAccount getNewUserAccount() {
        final IUserAccount entity = getUserAccountService().createEntity();
        entity.setEmail("email-" + getRandomInt());
        entity.setPassword("password-" + getRandomInt());

        final Role[] role = Role.values();
        int randomIndex = Math.max(0, RANDOM.nextInt(role.length) - 1);
        entity.setRole(role[randomIndex]);
        entity.setFirstName("firstName-" + getRandomInt());
        entity.setLastName("lastName-" + getRandomInt());
        entity.setBirthday(getRandomDate());
        entity.setAddress("address-" + getRandomInt());
        entity.setPhone("phone-" + getRandomInt());
        return entity;
    }

    protected IUserAccount saveNewUserAccount() {
        final IUserAccount entity = getNewUserAccount();
        getUserAccountService().save(entity);
        return entity;
    }

    protected IGuestStatus saveNewGuestStatus() {
        final IGuestStatus entity = getGuestStatusService().createEntity();
        entity.setName("name-" + getRandomInt());
        entity.setDiscount(getRandomInt());

        getGuestStatusService().save(entity);
        return entity;
    }

    protected IBooking saveNewBooking() {
        final IBooking entity = getBookingService().createEntity();
        entity.setRoom(saveNewRoom());
        entity.setUserAccount(saveNewUserAccount());
        entity.setCheckIn(getRandomDate());
        entity.setCheckOut(getRandomDate());
        entity.setRoomPrice(getRandomDouble());
        entity.setBookingStatus(saveNewBookingStatus());

        getBookingService().save(entity);
        return entity;
    }

    protected IMaintenance saveNewMaintenance() {
        final IMaintenance entity = getMaintenanceService().createEntity();
        entity.setName("maintenance-" + getRandomInt());
        entity.setActualPrice(getRandomDouble());
        entity.setPhotoLink("photoLink-" + getRandomInt());
        entity.setAvailable(getRandomBoolean());

        getMaintenanceService().save(entity);
        return entity;
    }

    protected IBookedMaintenance saveNewBookedMaintenance() {
        final IBookedMaintenance entity = getBookedMaintenanceService().createEntity();
        entity.setUserAccount(saveNewUserAccount());
        entity.setMaintenance(saveNewMaintenance());
        entity.setTime(getRandomDate());
        entity.setPrice(getRandomDouble());

        getBookedMaintenanceService().save(entity);
        return entity;
    }

    protected IBookingStatus saveNewBookingStatus() {
        final IBookingStatus entity = getBookingStatusService().createEntity();
        entity.setName("bookingStatus-" + getRandomInt());
        entity.setColor(
                String.format("rgb(%s, %s, %s)", RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255)));

        getBookingStatusService().save(entity);
        return entity;
    }

    protected IRoom saveNewRoom() {
        final IRoom entity = getRoomService().createEntity();
        entity.setNumber(getRandomInt());
        entity.setFloor(getRandomInt());

        final RoomType[] roomTypes = RoomType.values();
        int randomIndex = Math.max(0, RANDOM.nextInt(roomTypes.length) - 1);
        entity.setType(roomTypes[randomIndex]);

        final Accomodation[] accomodation = Accomodation.values();
        randomIndex = Math.max(0, RANDOM.nextInt(accomodation.length) - 1);
        entity.setAccomodation(accomodation[randomIndex]);

        final ViewType[] viewType = ViewType.values();
        randomIndex = Math.max(0, RANDOM.nextInt(viewType.length) - 1);
        entity.setView(viewType[randomIndex]);

        entity.setActualPrice(getRandomDouble());
        entity.setDescription("description" + getRandomInt());
        entity.setDirty(getRandomBoolean());
        entity.setBroken(getRandomBoolean());

        getRoomService().save(entity);
        return entity;
    }

    protected IMessage saveNewMessage() throws MessagingException {
        final IMessage entity = getMessageService().createEntity();
        entity.setName("name-" + getRandomInt());
        entity.setPhone("phone-" + getRandomInt());
        entity.setEmail("email-" + getRandomInt());
        entity.setMessage("message-" + getRandomInt());

        getMessageService().save(entity);
        return entity;
    }

    protected IUnstructuredObject saveNewUnstructuredObject() {
        final IUnstructuredObject entity = getUnstructuredObjectService().createEntity();
        entity.setName("name-" + getRandomInt());
        entity.setContent(getRandomByteArray());

        entity.setUserAccount(saveNewUserAccount());

        getUnstructuredObjectService().save(entity);
        return entity;
    }

    public IBookedMaintenanceService getBookedMaintenanceService() {
        return bookedMaintenanceService;
    }

    public IBookingService getBookingService() {
        return bookingService;
    }

    public ICommentService getCommentService() {
        return commentService;
    }

    public IGuestStatusService getGuestStatusService() {
        return guestStatusService;
    }

    public IMaintenanceService getMaintenanceService() {
        return maintenanceService;
    }

    public IPhotoLinkService getPhotoLinkService() {
        return photoLinkService;
    }

    public IRoomService getRoomService() {
        return roomService;
    }

    public IBookingStatusService getBookingStatusService() {
        return bookingStatusService;
    }

    public IPostService getPostService() {
        return postService;
    }

    public IMessageService getMessageService() {
        return messageService;
    }

    public ITaskService getTaskService() {
        return taskService;
    }

    public IGuestService getGuestService() {
        return guestService;
    }

    public IEmployeeService getEmployeeService() {
        return employeeService;
    }

    public IUserAccountService getUserAccountService() {
        return userAccountService;
    }

    public IUnstructuredObjectService getUnstructuredObjectService() {
        return unstructuredObjectService;
    }

    protected Integer getRandomInt() {
        return RANDOM.nextInt(99999);
    }

    protected int getRandomObjectsCount() {
        return RANDOM.nextInt(9) + 1;
    }

    protected Boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }

    protected Double getRandomDouble() {
        final double min = 0;
        final double max = 100.00;
        final double diff = max - min;
        final double randomValue = min + RANDOM.nextDouble() * diff;
        final double res = Math.floor(randomValue * 100);
        return res / 100;
    }

    protected Date getRandomDate() {
        final long rand = RANDOM.nextInt(17600);
        final long dateMillis = Math.abs(System.currentTimeMillis() - rand * 24 * 60 * 60 * 1000);
        final Date date = new Date(dateMillis);
        return date;
    }

    private byte[] getRandomByteArray() {
        int count = getRandomObjectsCount();
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            strBuilder.append(getRandomInt());
        }
        byte[] byteArray = strBuilder.toString().getBytes();
        return byteArray;
    }
}
