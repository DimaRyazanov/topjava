package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public List<MealTo> getAll(int userId) {
        List<MealTo> mealWithExcess = MealsUtil.getWithExcess(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return mealWithExcess != null ? mealWithExcess : Collections.emptyList();
    }

    @Override
    public List<MealTo> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<MealTo> mealWithExcess = MealsUtil.getWithExcess(repository.getFiltered(userId, startDate, startTime, endDate, endTime), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        return mealWithExcess != null ? mealWithExcess : Collections.emptyList();
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException{
        Meal oldMeal = repository.get(meal.getId(), userId);
        checkNotFoundWithId(oldMeal, meal.getId());
        repository.save(meal);
    }
}