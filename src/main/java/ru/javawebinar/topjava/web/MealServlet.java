package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.fake.FakeMealServiceImpl;
import ru.javawebinar.topjava.fake.MealService;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealService mealService = new FakeMealServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> allMealsTo = mealService.getAllMealsTo();
        request.setAttribute("listMeal", allMealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
