package ru.yandex.practicum.filmorate.controllers.implcontrollers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.implservice.ReviewServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@Slf4j
public class ReviewImplController {
    private final ReviewServiceImpl reviewService;

    public ReviewImplController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Optional<Review> save(@RequestBody @Valid Review review) {
        review.setUseful(0L);
        log.info("Получен POST-запрос /reviews с телом: {}", review);
        Optional<Review> createdReview = reviewService.save(review);
        log.info("Отправлен ответ на POST-запрос /reviews с телом: {}", createdReview);
        return createdReview;
    }

    @PutMapping
    public Optional<Review> update(@RequestBody @Valid Review review) {
        log.info("Получен PUT-запрос /reviews с телом: {}", review);
        Optional<Review> updatedReview = reviewService.update(review);
        log.info("Отправлен ответ на PUT-запрос /reviews с телом: {}", updatedReview);
        return updatedReview;
    }

    @DeleteMapping("/{reviewId}")
    public void delete(@PathVariable("reviewId") @Min(0) Long reviewId) {
        log.info("Получен DELETE-запрос /reviews/{}", reviewId);
        reviewService.delete(reviewId);
        log.info("Отправлен ответ на DELETE-запрос /reviews/{}", reviewId);
    }

//    @GetMapping
//    public List<Review> findAll() {
//        log.info("Получен GET-запрос /reviews");
//        List<Review> foundedReviews = reviewService.findAll();
//        log.info("Отправлен ответ на GET-запрос /reviews с телом: {}", foundedReviews);
//        return foundedReviews;
//    }

    @GetMapping("/{reviewId}")
    public Optional<Review> findById(@PathVariable("reviewId") @Min(0) Long reviewId) {
        log.info("Получен GET-запрос /reviews/{}", reviewId);
        Optional<Review> foundedReview = reviewService.findById(reviewId);
        log.info("Отправлен ответ на GET-запрос /reviews/{} с телом: {}", reviewId, foundedReview);
        return foundedReview;
    }

    @GetMapping
    public List<Review> getReviewByFilmId(@RequestParam(value = "filmId", required = false) Long filmId,
                                          @RequestParam(value = "count", defaultValue = "10") int count) {
        List<Review> filmsList;
        if (filmId != null) {
            if (count > 0) {
                // Получение отзывов по идентификатору фильма с ограничением по количеству
                log.info("Получен GET-запрос /reviews?filmId={}&count={}", filmId, count);
                filmsList = reviewService.getReviewsByFilmIdLimited(filmId, count);
                log.info("Отправлен ответ на GET-запрос /reviews?filmId={}&count={} c телом {}", filmId, count, filmsList);
            } else {
                // Получение всех отзывов для определенного фильма (без ограничения по количеству)
                log.info("Получен GET-запрос /reviews?filmId={}", filmId);
                filmsList = reviewService.getReviewsByFilmId(filmId);
                log.info("Отправлен ответ на GET-запрос /reviews?filmId={} c телом {}", filmId, filmsList);
            }
        } else {
            if (count > 0) {
                // Получение определенного количества отзывов (с ограничением по количеству)
                log.info("Получен GET-запрос /reviews?count={}", count);
                filmsList = reviewService.getLimitedReviews(count);
                log.info("Отправлен ответ на GET-запрос /reviews?count={} c телом {}", count, filmsList);
            } else {
                // Получение всех отзывов (без фильтров)
                log.info("Получен GET-запрос /reviews");
                filmsList = reviewService.getAllReviews();
                log.info("Отправлен ответ на GET-запрос /reviews c телом {}", filmsList);
            }
        }
        return filmsList;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeReview(@PathVariable("id") @Min(0) Long reviewId,
                              @PathVariable("userId") @Min(0) Long userId) {
        log.info("Получен PUT-запрос /reviews/{}/like/{}", reviewId, userId);
        reviewService.addLikeReview(reviewId, userId);
        log.info("Отправлен ответ на PUT-запрос /reviews/{}/like/{}", reviewId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeReview(@PathVariable("id") @Min(0) Long reviewId,
                                 @PathVariable("userId") @Min(0) Long userId) {
        log.info("Получен DELETE-запрос /reviews/{}/like/{}", reviewId, userId);
        reviewService.deleteLikeReview(reviewId, userId);
        log.info("Отправлен ответ на DELETE-запрос /reviews/{}/like/{}", reviewId, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislikeReview(@PathVariable("id") @Min(0) Long reviewId,
                                 @PathVariable("userId") @Min(0) Long userId) {
        log.info("Получен PUT-запрос /reviews/{}/dislike/{}", reviewId, userId);
        reviewService.addDislikeReview(reviewId, userId);
        log.info("Отправлен ответ на PUT-запрос /reviews/{}/dislike/{}", reviewId, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void deleteDislikeReview(@PathVariable("id") @Min(0) Long reviewId,
                                    @PathVariable("userId") @Min(0) Long userId) {
        log.info("Получен DELETE-запрос /reviews/{}/dislike/{}", reviewId, userId);
        reviewService.deleteDislikeReview(reviewId, userId);
        log.info("Отправлен ответ на DELETE-запрос /reviews/{}/dislike/{}", reviewId, userId);
    }
}
