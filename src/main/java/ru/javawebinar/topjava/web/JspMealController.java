package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    @Autowired
    private final MealService mealService;

    public JspMealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public String meals(Model model){
        model.addAttribute("meals", MealsUtil.getWithExcess(
                mealService.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("meal",
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping(value = "/delete/id={id}")
    public String delete(@PathVariable int id){
        mealService.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping(value = "/update/id={id}")
    public String update(@PathVariable int id, Model model){
        model.addAttribute("meal", mealService.get(id, SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        List<Meal> mealsFilteredByDays = mealService.getBetweenDates(startDate, endDate, SecurityUtil.authUserId());
        model.addAttribute("meals", MealsUtil.getFilteredWithExcess(mealsFilteredByDays,
                SecurityUtil.authUserCaloriesPerDay(),
                startTime,
                endTime));

        return "meals";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request){
        LocalDateTime dateTime = parseLocalDateTime(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (request.getParameter("id").isEmpty()){
            mealService.create(new Meal(dateTime, description, calories), SecurityUtil.authUserId());
        }else{
            int id = Integer.parseInt(request.getParameter("id"));
            mealService.update(new Meal(id, dateTime, description, calories), SecurityUtil.authUserId());
        }

        return "redirect:/meals";
    }
}
