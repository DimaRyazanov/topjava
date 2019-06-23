package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    List<MealTo> getAll(int userId);

    List<MealTo> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);

    Meal get(int id, int userId);

    Meal create(Meal meal);

    void delete(int id, int userId);

    void update(Meal meal, int userId);
}