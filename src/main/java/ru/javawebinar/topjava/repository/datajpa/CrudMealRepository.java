package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    List<Meal> findAllByDateTimeBetweenAndUser(LocalDateTime startDateTime, LocalDateTime endDateTime, User user, Sort sort);
    List<Meal> findAllByUser(User user, Sort sort);
}
