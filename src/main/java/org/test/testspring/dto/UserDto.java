package org.test.testspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.test.testspring.entity.AppUser;

import java.util.function.Function;

//la facon de gerer un DTO sans MODEL MAPPER
//@Component
//public class UserDto implements Function<AppUser, UsermapDto> {
//    @Override
//    public UsermapDto apply(AppUser appUser) {
//        return new UsermapDto(appUser.getId(),appUser.getUsername());
//    }

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
//Gerer un DTO avec Model Mapper
public class UserDto {
   private Long id;
   private String username;


}
