package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.getAll());

            System.out.println(mealRestController.getFiltered(LocalDate.of(2015, Month.MAY, 29),
                    null,
                    LocalDate.of(2015, Month.MAY, 30),
                    null));

            System.out.println(mealRestController.getFiltered(LocalDate.of(2015, Month.MAY, 29),
                    LocalTime.of(10, 0),
                    LocalDate.of(2015, Month.MAY, 30),
                    LocalTime.of(15, 0)));
            try {
                System.out.println(mealRestController.get(2));
            }catch (NotFoundException e){
                System.out.println(e);
            }

            try {
                System.out.println(mealRestController.create(new Meal(LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Завтрак 2 пользователя", 555)));
                mealRestController.get(7).setUserId(2);
                System.out.println(mealRestController.get(7));
            }catch (NotFoundException e){
                System.out.println(e);
            }

            try {
                mealRestController.update(new Meal(LocalDateTime.of(2019, Month.MAY, 30, 12, 0), "Завтрак 1 пользователя", 666), 2);
            }catch (NotFoundException e){
                System.out.println(e);
            }

            try {
                mealRestController.update(new Meal(LocalDateTime.of(2019, Month.MAY, 30, 12, 0), "Завтрак 2 пользователя", 666), 7);
            }catch (NotFoundException e){
                System.out.println(e);
            }

            System.out.println(mealRestController.create(new Meal(LocalDateTime.of(2019, Month.MAY, 11, 12, 0), "Завтрак 1 пользователя", 666)));
            System.out.println(mealRestController.getAll());
        }
    }
}
