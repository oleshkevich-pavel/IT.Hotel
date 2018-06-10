package com.itacademy.jd2.po.hotel.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.itacademy.jd2.po.hotel.service.IAbstractService;

public abstract class AbstractServiceImpl<T, DAO, ID, FILTER> implements IAbstractService<T, DAO, ID, FILTER> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceImpl.class);

    private Class<? extends T> t;
    @Autowired
    private DAO dao;

    @Override
    public T createEntity() {
        T entity = null;
        try {
            Method method = dao.getClass().getDeclaredMethod("createEntity");
            entity = (T) method.invoke(dao);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void save(final T entity) {
        final Date modifiedOn = new Date();
        Field fieldId;
        try {
            fieldId = t.getField("id");
            fieldId.setAccessible(true);
            Field fieldCreated = t.getField("created");
            fieldCreated.setAccessible(true);
            Field fieldUpdated = t.getField("updated");
            fieldUpdated.setAccessible(true);
            Integer id = (Integer) fieldId.get(entity);
            fieldUpdated.set(entity, (Date) modifiedOn);
            if (id == null) {
                fieldCreated.set(entity, (Date) modifiedOn);
                LOGGER.info("new saved entity: {}", entity);

                Method method = dao.getClass().getDeclaredMethod("insert");
                method.setAccessible(true);
                method.invoke(dao, entity);
            } else {
                LOGGER.info("updated entity: {}", entity);
                Method method = dao.getClass().getDeclaredMethod("update");
                method.setAccessible(true);
                method.invoke(dao, entity);
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public T get(final ID id) {
        T entity = null;
        try {
            Method method = dao.getClass().getDeclaredMethod("get");
            method.setAccessible(true);
            entity = (T) method.invoke(dao, id);
            LOGGER.debug("entityById[{}]: {}", id, entity);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void delete(final ID id) {
        try {
            LOGGER.info("delete {} entity: {}", dao.getClass().getSimpleName(), id);
            Method method = dao.getClass().getDeclaredMethod("delete");
            method.invoke(dao, id);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try {
            LOGGER.info("delete all {} entities", dao.getClass().getSimpleName());
            Method method = dao.getClass().getDeclaredMethod("deleteAll");
            method.invoke(dao);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll() {
        List<T> all = null;
        try {
            Method method = dao.getClass().getDeclaredMethod("selectAll");
            all = (List<T>) method.invoke(dao);
            LOGGER.debug("total count {} entities in DB: {}", dao.getClass().getSimpleName(), all.size());
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return all;
    }

    @Override
    public List<T> find(final FILTER filter) {
        List<T> all = null;
        try {
            Method[] methods = dao.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                System.out.println(methods[i].getName());
            }
            Method method = dao.getClass().getDeclaredMethod("find");
            all = (List<T>) method.invoke(dao, filter);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return all;
    }

    @Override
    public long getCount(final FILTER filter) {
        Method method;
        long count = 0;
        try {
            method = dao.getClass().getDeclaredMethod("getCount");
            count = (long) method.invoke(dao, filter);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public T getFullInfo(final ID id) {
        T entity = null;
        try {
            Method method = dao.getClass().getDeclaredMethod("getFullInfo");
            entity = (T) method.invoke(dao, id);
            LOGGER.debug("entityById[{}]: {}", id, entity);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public List<T> getAllFullInfo() {
        List<T> all = null;
        try {
            Method method = dao.getClass().getDeclaredMethod("getAllFullInfo");
            all = (List<T>) method.invoke(dao);
            LOGGER.debug("total count {} entities in DB: {}", dao.getClass().getSimpleName(), all.size());
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return all;
    }

/*    @Override
    public Double getMaxPrice() {
        Double maxPrice = 0.00;
        try {
            Method method = dao.getClass().getDeclaredMethod("getMaxPrice");
            maxPrice = (Double) method.invoke(dao);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return maxPrice;
    }*/
}
