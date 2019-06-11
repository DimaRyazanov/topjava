package ru.javawebinar.topjava.fake;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealService {
      List<MealTo> getAllMealsTo();

    void addMeal(Meal meal);

    Meal getMealById(int id);

    void updateMeal(Meal meal);

    void delete(int id);
}
