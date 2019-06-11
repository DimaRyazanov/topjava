package ru.javawebinar.topjava.fake;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAllMeals();

    void addMeal(Meal meal);

    Meal getMealById(int id);

    void updateMeal(Meal meal);

    void delete(int id);
}
