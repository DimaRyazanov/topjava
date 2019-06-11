package ru.javawebinar.topjava.fake;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealCounter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMealRepository implements MealRepository {

    private List<Meal> mealsDB = new ArrayList<>();

    public FakeMealRepository() {
        mealsDB.add(new Meal(MealCounter.ID_GENERATOR.getAndIncrement(),LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealsDB.add(new Meal(MealCounter.ID_GENERATOR.getAndIncrement(),LocalDateTime.of(2016, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealsDB.add(new Meal(MealCounter.ID_GENERATOR.getAndIncrement(),LocalDateTime.of(2016, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealsDB.add(new Meal(MealCounter.ID_GENERATOR.getAndIncrement(),LocalDateTime.of(2016, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealsDB.add(new Meal(MealCounter.ID_GENERATOR.getAndIncrement(),LocalDateTime.of(2016, Month.MAY, 31, 13, 0), "Обед", 500));
        mealsDB.add(new Meal(MealCounter.ID_GENERATOR.getAndIncrement(),LocalDateTime.of(2016, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealsDB;
    }

    @Override
    public void addMeal(Meal meal) {
        mealsDB.add(meal);
    }

    @Override
    public Meal getMealById(int id) {
        for (Meal meal :
                mealsDB) {
            if (meal.getId() == id){
                return meal;
            }
        }

        return null;
    }

    @Override
    public void updateMeal(Meal meal) {
        Meal oldMeal = getMealById(meal.getId());
        int index = mealsDB.indexOf(oldMeal);

        if(oldMeal != null){
            mealsDB.remove(oldMeal);
            mealsDB.add(index, meal);
        }
    }

    @Override
    public void delete(int id) {
        Meal removedMeal = getMealById(id);

        if (removedMeal != null)
            mealsDB.remove(removedMeal);
    }
}
