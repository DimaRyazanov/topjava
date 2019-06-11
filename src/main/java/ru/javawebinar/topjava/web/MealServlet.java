package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.fake.FakeMealServiceImpl;
import ru.javawebinar.topjava.fake.MealService;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealCounter;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealService mealService = new FakeMealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.equals("listMeals") || action.isEmpty()){
            log.debug("list of meals");
            List<MealTo> allMealsTo = mealService.getAllMealsTo();
            request.setAttribute("listMeals", allMealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }else if (action.equalsIgnoreCase("edit")){
            log.debug("edit meal");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealService.getMealById(mealId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }else if (action.equalsIgnoreCase("delete")){
            log.debug("delete meal by id");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealService.delete(mealId);
            List<MealTo> allMealsTo = mealService.getAllMealsTo();
            request.setAttribute("listMeals", allMealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }else{
            log.debug("add new meal");
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("add(edit) meal from POST request");
        String description = request.getParameter("description");
        String mealId = request.getParameter("id");
        LocalDateTime dateTime = null;
        int calories = 0;

        try {

            dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
                    .parse(request.getParameter("datetime"))
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
            calories = Integer.parseInt(request.getParameter("calories"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(mealId == null || mealId.isEmpty())
        {
            mealService.addMeal(new Meal(MealCounter.ID_GENERATOR.getAndIncrement(), dateTime, description, calories));
        }else{
            Meal meal = new Meal(Integer.parseInt(mealId), dateTime, description, calories);
            mealService.updateMeal(meal);
        }

        List<MealTo> allMealsTo = mealService.getAllMealsTo();
        request.setAttribute("listMeals", allMealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
