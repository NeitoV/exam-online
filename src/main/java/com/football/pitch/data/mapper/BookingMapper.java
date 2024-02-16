package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.booking.BookingCreationDTO;
import com.football.pitch.data.dto.booking.BookingShowDTO;
import com.football.pitch.data.enity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PitchDetailsMapper.class, StatusMapper.class, CustomerMapper.class})
public interface BookingMapper {
    @Mapping(source = "pitchDetailsId", target = "pitchDetails.id")
    Booking toEntity(BookingCreationDTO bookingCreationDTO);

    @Mapping(source = "pitchDetails", target = "pitchDetailsShowDTO")
    @Mapping(source = "status", target = "statusDTO")
    @Mapping(source = "customer", target = "customerDTO")
    BookingShowDTO toDTOShow(Booking  booking);
}
