package com.sungyeh.repository;

import com.sungyeh.domain.Booking;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * DepartmentRepository
 *
 * @author sungyeh
 */
@DisplayName("訂房單元測試")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BookingRepositoryTest {

    @Resource
    private BookingRepository bookingRepository;

    @Test
    public void testCrud() {
        // Arrange
        Booking booking = new Booking();
        booking.setPeople(1);
        booking.setDateTime(LocalDateTime.now());
        booking.setCreateTime(LocalDateTime.now());
        // Act
        Booking target = bookingRepository.save(booking);
        // Assert
        Assertions.assertNotNull(target.getId());
        Assertions.assertEquals(booking.getPeople(), target.getPeople());
        Assertions.assertEquals(booking.getDateTime(), target.getDateTime());
        Assertions.assertEquals(booking.getCreateTime(), target.getCreateTime());

    }
}
