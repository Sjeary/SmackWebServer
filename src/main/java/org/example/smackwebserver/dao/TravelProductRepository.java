package org.example.smackwebserver.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelProductRepository extends JpaRepository<TravelProduct,Long> {

    @Query("SELECT tp FROM TravelProduct tp WHERE " +
            "(:userId IS NULL OR tp.userId = :userId) AND " +
            "(:productType IS NULL OR tp.productType = :productType) AND " +
            "(:theme IS NULL OR tp.theme = :theme) AND " +
            "(:departureLocation IS NULL OR tp.departureLocation = :departureLocation) AND " +
            "(:destination IS NULL OR tp.destination = :destination)")
    Page<TravelProduct> searchTravelProducts(@Param("userId") Integer userId,
                                             @Param("productType") String productType,
                                             @Param("theme") String theme,
                                             @Param("departureLocation") String departureLocation,
                                             @Param("destination") String destination,
                                             Pageable pageable);

    @Query("SELECT tp FROM TravelProduct tp " +
            "WHERE tp.title LIKE %:keyword% " +
            "   OR tp.departureLocation LIKE %:keyword% " +
            "   OR tp.destination LIKE %:keyword% " +
            "   OR tp.features LIKE %:keyword% " +
            "   OR tp.theme LIKE %:keyword% " +
            "ORDER BY tp.id DESC")
    Page<TravelProduct> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);


}
