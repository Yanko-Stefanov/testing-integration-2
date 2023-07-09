package jwt.token.demo.controller;

import jwt.token.demo.model.JwtRequest;
import jwt.token.demo.model.JwtResponse;
import jwt.token.demo.model.UserModel;
import jwt.token.demo.service.CustomUserDetailService;
import jwt.token.demo.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class JwtController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;

    public JwtController(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserModel userModel) {
        UserModel user = customUserDetailService.register(userModel);
        ResponseEntity<UserModel> response = new ResponseEntity<>(user, HttpStatus.CREATED);
        return response;
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) {

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
        authenticationManager.authenticate(upat);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public UserModel getCurrentUser(Principal principal) {
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(principal.getName());
        return (UserModel) userDetails;
    }
}
