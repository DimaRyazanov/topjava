package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(mealService.getAll(SecurityUtil.authUserId()), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);

    }

    @Test
    void testCreateWithLocation() throws Exception  {
        Meal expected = new Meal(LocalDateTime.now(), "Крутое описание", 100);
        ResultActions action = mockMvc.perform(post(REST_URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JsonUtil.writeValue(expected)))
                                    .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = mealService.get(MEAL1_ID, SecurityUtil.authUserId());
        updated.setDescription("updated description");
        updated.setCalories(3000);

        mockMvc.perform(put(REST_URL + MEAL1_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isNoContent());

        assertMatch(mealService.get(MEAL1_ID, SecurityUtil.authUserId()), updated);

    }

    @Test
    void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=2015-05-30&startTime=13:00:00&endDate=2015-05-30&endTime=23:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(List.of(MEAL3, MEAL2), MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void testGetBetweenNullDateTimes() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=2015-05-31&startTime=&endDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(List.of(MEAL6, MEAL5, MEAL4), MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void testGetBetweenAllNullDateTimes() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=&startTime=&endDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }
}