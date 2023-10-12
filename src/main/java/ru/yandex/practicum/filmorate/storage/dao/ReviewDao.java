package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    Optional<Review> save(Review review);

    Optional<Review> update(Review review);

    void delete(Long id);

    Optional<Review> getReviewById(Long reviewId);

    void addLikeReview(Long reviewDao, Long userId);

    void deleteLikeReview(Long reviewId, Long userId);

    void addDislikeReview(Long reviewId, Long userId);

    void deleteDislikeReview(Long reviewId, Long userId);

    List<Review> getReviewsByFilmIdLimited(Long filmId, Integer count);

    List<Review> getReviewsByFilmId(Long filmId);

    List<Review> getLimitedReviews(Integer count);

    List<Review> getAllReviews();
}
