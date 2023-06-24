package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.service.user.UserRequest;
import space.sedov.server.service.response.MessageService;
import space.sedov.server.service.response.ResponseService;
import space.sedov.server.service.user.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @GetMapping("/")
    public ResponseService getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    @PostMapping("/find/account")
    @ResponseBody
    public ResponseService findUserByEmail(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseService(HttpStatus.BAD_REQUEST, bindingResult);
        }
        return userDetailsService.findUserByEmail(userRequest);
    }

    @PostMapping("/signin")
    @ResponseBody
    public ResponseService signin(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseService(HttpStatus.BAD_REQUEST, bindingResult);
        }
        return userDetailsService.signin(userRequest);
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseService signup(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseService(HttpStatus.BAD_REQUEST, bindingResult);
        }
        return userDetailsService.signup(userRequest);
    }

    @GetMapping("/signout")
    @ResponseBody
    public ResponseService signout() {
        return userDetailsService.signout();
    }

    @PostMapping("/change/data")
    @ResponseBody
    public ResponseService changePersonalData(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseService(HttpStatus.BAD_REQUEST, bindingResult);
        }
        return userDetailsService.changePersonalData(userRequest);
    }

    @PostMapping("/change/password")
    @ResponseBody
    public ResponseService changePassword(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseService(HttpStatus.BAD_REQUEST, bindingResult);
        }
        return userDetailsService.changePassword(userRequest);
    }

    @PostMapping("/change/email")
    @ResponseBody
    public ResponseService changeEmail(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseService(HttpStatus.BAD_REQUEST, bindingResult);
        }
        return userDetailsService.changeEmail(userRequest);
    }

    @GetMapping(value = "/email/confirmation/{token}")
    public ResponseService confirmRegistration(@PathVariable("token") String token) {
        System.out.println(token);
        return userDetailsService.confirmationEmail(token);
    }

    @PostMapping("/signin/email")
    @ResponseBody
    public ResponseService sendSigninEmail(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseService(HttpStatus.BAD_REQUEST, bindingResult);
        }
        return userDetailsService.sendSigninEmail(userRequest);
    }
}
