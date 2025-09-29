package org.test.testspring.controleur;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.test.testspring.dto.ErrorEntity;
import org.test.testspring.dto.UserDto;
import org.test.testspring.dto.UsermapDto;
import org.test.testspring.entity.AppUser;
import org.test.testspring.entity.AppRole;
import org.test.testspring.entity.Authenficat;
import org.test.testspring.security.JwtService;
import org.test.testspring.service.AccountSerice;
import org.test.testspring.service.AccountServiceImpl;
import org.test.testspring.service.UserDetailImpl;
import org.test.testspring.service.ValidationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RestController
@Slf4j
public class AppUserControleur {
    //private AccountSerice accountSerice;
    @Autowired
    private AccountServiceImpl accountSerice;
    @Autowired
    private UserDetailImpl userDetail;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ValidationService validationService;



    @PostMapping(path = "/inscription")
    public void saveUsers(@RequestBody AppUser appuer){
       // System.out.println(appuer);
        this.accountSerice.saveUser(appuer);
    }
    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.accountSerice.activation(activation);
    }
    @PostMapping(path = "/saveRole")
    public AppRole saveRoles(@RequestBody AppRole appRole){

        return accountSerice.saveRole(appRole);
    }
    @GetMapping(path = "/listUsers")
    public List<UserDto> users(){

        return accountSerice.listUser();
    }
    //premiere facon de gerer les erreurs
//    @GetMapping(path = "/listUsers/{id}")
//    public ResponseEntity users(@PathVariable Long id){
//        try{
//            AppUser user =accountSerice.userbyid(id);
//           return ResponseEntity.ok(user) ;
//
//        }catch(EntityNotFoundException exception){
//            String code =null;
//            return ResponseEntity.status(BAD_REQUEST).body(new ErrorEntity(code,exception.getMessage()));
//
//        }
//
//    }

    //deuxieme facon de gerer les erreurs
    @GetMapping(path = "/listUsers/{id}")
    public AppUser users(@PathVariable Long id){

            return accountSerice.userbyid(id);
    }


    @PostMapping(path = "/deconnexion")
    public void deconnexion(){
        jwtService.deconnexion();

    }
    @PostMapping(path = "/modififiermdp")
    public void modifiermdp(@RequestBody Map<String, String> activation) {
        validationService.modifiermdp(activation);
    }
    @PostMapping(path = "/refresh-token")
    public @ResponseBody Map<String, String> refreshtoken(@RequestBody Map<String, String> refreshtoken) {
        return jwtService.refreshtoken(refreshtoken);
    }

    @PostMapping(path = "/login")
    public Map<String, String> login (@RequestBody Authenficat auth){

                        final Authentication authenticate=authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(auth.username(),auth.password())
                        );
                    if(authenticate.isAuthenticated()) {
                        return jwtService.generate(auth.username());
                    }
        return null;

            }

    @PostMapping(path = "/nouveaumdp")
    public void nouveaumdp(@RequestBody Map<String, String> activation) {

        validationService.nouveaumdp(activation);
    }
    //deuxieme methode pour gerer les exceptions

    //et pour la troisieme methode
    // c'est la methode recommander  on va crer un  controlleur advice et copier la methode ci dessous

//    @ResponseStatus(BAD_REQUEST)
//    @ExceptionHandler({EntityNotFoundException.class})
//    public ErrorEntity handleException(EntityNotFoundException exception){
//
//        return new ErrorEntity(null,exception.getMessage());
//
//    }
}

@Data
class Roleuserform{
    public String username;
    public String rolename;
}

