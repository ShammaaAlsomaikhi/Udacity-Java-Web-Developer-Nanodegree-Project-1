package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.routes.Router;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addCredential(Credential credential, Authentication auth) {
        if(credential != null) {
            User user = userService.getUser(auth.getName());
            credential.setUserId(user.getUserId());
            credentialService.addCredential(credential);
            return Router.SUCCESS;
        } else {
            return Router.ERROR;
        }
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("id") int credentialId) {
        if (credentialId > 0) {
            credentialService.deleteCredential(credentialId);
            return Router.SUCCESS;
        } else {
            return Router.ERROR;
        }
    }
}
