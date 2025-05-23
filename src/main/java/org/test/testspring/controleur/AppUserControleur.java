package org.test.testspring.controleur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.test.testspring.entity.AppUser;
import org.test.testspring.entity.AppRole;
import org.test.testspring.entity.Authenficat;
import org.test.testspring.security.JwtService;
import org.test.testspring.service.AccountSerice;
import org.test.testspring.service.AccountServiceImpl;
import org.test.testspring.service.UserDetailImpl;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RestController
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
    public List<AppUser> users(){

        return accountSerice.listUser();
    }
    @PostMapping(path = "/saveRoletoUser")
    public void saveRoleTouser(@RequestBody Roleuserform roleuserform){

         accountSerice.addRoleToUser(roleuserform.getUsername(),roleuserform.getRolename());
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
}
@Data
class Roleuserform{
    public String username;
    public String rolename;
}

