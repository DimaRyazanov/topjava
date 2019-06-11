package ru.javawebinar.topjava.fake;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAllMeals();
}
