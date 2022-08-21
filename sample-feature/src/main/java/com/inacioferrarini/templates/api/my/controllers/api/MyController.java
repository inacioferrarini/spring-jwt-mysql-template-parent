package com.inacioferrarini.templates.api.my.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    public String getPrincipalName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDTO) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping({"/public"})
    public String publicEndpoint() {
        return "Public Endpoint Response";
    }

    @GetMapping({"/protected"})
    public String protectedEndpoint() {
        return "Protected Endpoint Response";
    }

}
