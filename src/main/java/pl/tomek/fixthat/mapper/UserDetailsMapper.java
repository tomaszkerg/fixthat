package pl.tomek.fixthat.mapper;

import pl.tomek.fixthat.dto.user.UserDetailsDto;
import pl.tomek.fixthat.entity.user.UserDetails;

public class UserDetailsMapper {


    public static UserDetails toEntity(UserDetailsDto userDetailsDto){
        UserDetails userDetails = new UserDetails();
        userDetails.setNewsLetter(userDetailsDto.isNewsLetter());
        userDetails.setAddress(userDetailsDto.getAddress());
        userDetails.setCity(userDetailsDto.getCity());
        userDetails.setFirstName(userDetailsDto.getFirstName());
        userDetails.setLastName(userDetailsDto.getLastName());
        userDetails.setId(userDetailsDto.getId());
        userDetails.setPesel(userDetailsDto.getPesel());
        return userDetails;
    }

    public static UserDetailsDto toDto(UserDetails userDetails){
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setNewsLetter(userDetails.isNewsLetter());
        userDetailsDto.setAddress(userDetails.getAddress());
        userDetailsDto.setCity(userDetails.getCity());
        userDetailsDto.setFirstName(userDetails.getFirstName());
        userDetailsDto.setLastName(userDetails.getLastName());
        userDetailsDto.setId(userDetails.getId());
        userDetailsDto.setPesel(userDetails.getPesel());
        return userDetailsDto;
    }
}
