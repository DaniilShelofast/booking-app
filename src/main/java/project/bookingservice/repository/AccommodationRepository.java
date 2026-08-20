package project.bookingservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.bookingservice.model.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
   /* // todo якщо буде помилка заміниш WHERE a.id NOT IN - WHERE NOT EXISTS
    @Query("SELECT a FROM Accommodation a WHERE a.id NOT IN(SELECT b.accommodation.id FROM Booking b WHERE b.accommodation.id = a.id AND b.status = 'PENDING') Order By a.id")
    Page<Accommodation> findAvailable(Pageable pageable);*/
}
