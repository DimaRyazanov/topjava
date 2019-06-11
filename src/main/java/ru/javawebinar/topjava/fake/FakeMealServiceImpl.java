package ru.javawebinar.topjava.fake;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeMealServiceImpl implements MealService {
    private MealRepository mealRepository = new FakeMealRepository();

    @Override
    public List<MealTo> getAllMealsTo() {
        Map<LocalDate, Integer> summingCaloriesPerDate = mealRepository.getAllMeals().stream().collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return mealRepository.getAllMeals().stream()
                    .map(x -> new MealTo(x.getId(), x.getDateTime(), x.getDescription(), x.getCalories(),
                            summingCaloriesPerDate.get(x.getDate()) > 2000))
                    .collect(Collectors.toList());
    }

    @Override
    public void addMeal(Meal meal) {
        mealRepository.addMeal(meal);
    }

    @Override
    public Meal getMealById(int id) {
        return mealRepository.getMealById(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealRepository.updateMeal(meal);
    }

    @Override
    public void delete(int id) {
        mealRepository.delete(id);
    }
}
