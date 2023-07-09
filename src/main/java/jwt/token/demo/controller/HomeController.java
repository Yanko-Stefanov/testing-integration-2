package jwt.token.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController {

    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") //compare to the Authority parameter
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String homePage() {
        return "Hello World";
    }
}
