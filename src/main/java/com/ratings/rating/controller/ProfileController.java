package com.ratings.rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratings.rating.entity.Profiles;
import com.ratings.rating.model.Profile;
import com.ratings.rating.service.ProfilesService;

@Controller
public class ProfileController {
    @Autowired
    private ProfilesService ProfilesService;

    @GetMapping("/")
    public String showForm() {
        return "profile";
    }

    @GetMapping("/getProfiles")
    public String getUser(@RequestParam("username") String username, Model model) {
        Profiles profiles = ProfilesService.getUserByUsername(username);
        String url = "https://codeforces.com/api/contest.list";
        RestTemplate rs = new RestTemplate();
        ObjectMapper ob = new ObjectMapper();
        Profile prf = new Profile();
        try{
            ResponseEntity<String> response = rs.getForEntity(url,String.class);
            JsonNode root = ob.readTree(response.getBody()).get("result");
            for(JsonNode i:root){
                if(("BEFORE").equalsIgnoreCase(i.get("phase").asText())){
                    String te = i.get("name").asText()+"|"+i.get("id").asText();
                    prf.cont.add(te);
                }
                else{
                    break;
                }
                
            }
            // System.out.println(root.asText());

        } catch(Exception e){
            e.printStackTrace();
        }
        model.addAttribute("profiles", profiles);
        model.addAttribute("inf",prf);
        return "profile";
    }
}
