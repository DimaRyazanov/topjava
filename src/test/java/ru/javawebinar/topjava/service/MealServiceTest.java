package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(USER_BREAKFAST_ONE.getId(), USER_ID);

        assertMatch(meal, USER_BREAKFAST_ONE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound(){
        mealService.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFountWithAnotherUser(){
        mealService.get(USER_BREAKFAST_ONE.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        mealService.delete(ADMIN_LUNCH.getId(), ADMIN_ID);

        assertMatch(mealService.getAll(ADMIN_ID), ADMIN_EVENING);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound(){
        mealService.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundWithAnotherUser(){
        mealService.delete(ADMIN_LUNCH.getId(), USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> mealsBetweenDates = mealService.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID);

        assertMatch(mealsBetweenDates, USER_EVENING_ONE, USER_DINNER_ONE, USER_BREAKFAST_ONE);
    }

    @Test
    public void getBetweenDatesWithNullDates(){
        List<Meal> meals = mealService.getBetweenDates(null, null, USER_ID);

        assertMatch(meals, MealTestData.getReferenceUserMeals());
    }

    @Test
    public void getBetweenDatesEmptyMeals(){
        List<Meal> meals = mealService.getBetweenDates(LocalDate.of(2019, Month.MAY, 30), null, USER_ID);

        assertMatch(meals, Collections.EMPTY_LIST);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealsBetweenDateTimes = mealService.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), USER_ID);

        assertMatch(mealsBetweenDateTimes, USER_EVENING_ONE, USER_DINNER_ONE);
    }

    @Test
    public void getBetweenDateTimesNullDateTimes(){
        List<Meal> meals = mealService.getBetweenDateTimes(null, null, USER_ID);

        assertMatch(meals, MealTestData.getReferenceUserMeals());
    }

    @Test
    public void getBetweenDateTimesEmptyMeals(){
        List<Meal> meals = mealService.getBetweenDateTimes(LocalDateTime.of(2019, Month.MAY, 30, 13, 0), null, ADMIN_ID);

        assertMatch(meals, Collections.EMPTY_LIST);
    }

    @Test
    public void getAll() {
        List<Meal> mealsUser = mealService.getAll(USER_ID);

        assertMatch(mealsUser, MealTestData.getReferenceUserMeals());
    }

    @Test
    public void getAllAdmin() {
        List<Meal> mealsUser = mealService.getAll(ADMIN_ID);

        assertMatch(mealsUser, MealTestData.getReferenceAdminMeals());
    }

    @Test
    public void getAllUnknownUser() {
        List<Meal> mealsUser = mealService.getAll(999999);

        assertMatch(mealsUser, Collections.EMPTY_LIST);
    }

    @Test
    public void update() {
        Meal updatedMeal = new Meal(USER_BREAKFAST_ONE);
        updatedMeal.setDescription("Завтрак update");
        updatedMeal.setCalories(200);

        mealService.update(updatedMeal, USER_ID);
        assertMatch(mealService.get(USER_BREAKFAST_ONE.getId(), USER_ID), updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFoundWithAnotherUser(){
        Meal updatedMeal = new Meal(USER_BREAKFAST_ONE);
        updatedMeal.setDescription("Завтрак update");
        updatedMeal.setCalories(200);

        mealService.update(updatedMeal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 11, 0), "Завтрак 2", 200);
        Meal createdMeal = mealService.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());

        assertMatch(mealService.getAll(USER_ID),
                USER_EVENING_TWO, USER_DINNER_TWO, USER_BREAKFAST_TWO, USER_EVENING_ONE, USER_DINNER_ONE, newMeal, USER_BREAKFAST_ONE);
        assertMatch(mealService.getAll(ADMIN_ID), MealTestData.getReferenceAdminMeals());
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDatetimeCreate(){
        mealService.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак 2", 500), USER_ID);
    }


}