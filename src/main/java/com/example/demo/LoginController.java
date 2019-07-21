package com.example.demo;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Filip Bednárik
 * @since 18.7.2019
 */
@Controller
public class LoginController {

  @GetMapping("/securedPage")
  @ResponseBody
  public String securedPage(Principal principal) {
    System.out.println(principal);
    return "You are authenticated " + principal.getName();
  }



}
