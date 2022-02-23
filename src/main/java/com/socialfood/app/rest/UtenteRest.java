package com.socialfood.app.rest;

import com.socialfood.app.model.Post;
import com.socialfood.app.model.Utente;
import com.socialfood.app.repository.UtenteCrudRepository;
import com.socialfood.app.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = { "/rest/utente" }, produces = { "application/json" }, consumes = { "application/json" })
@CrossOrigin(origins = { "*" })
public class UtenteRest {
    @Autowired
    UtenteService utenteService;

    @PostMapping(path = {"/registrazione"})
    public ResponseEntity<Utente> registrazione(@RequestBody Utente nuovoUtente) {
        try {
            Utente utente = utenteService.findByUsername(nuovoUtente.getUsername());

            if (utente == null) {
                utente = utenteService.creaUtente(nuovoUtente);
                return new ResponseEntity<>(utente, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = {"/login"})
    public ResponseEntity<String> login(@RequestBody Utente u) {
        try {
            String login = utenteService.doLogin(u);

            switch (login) {
                case "OK":
                    return new ResponseEntity<>(HttpStatus.OK); //login effettuato

                case "usernameError":
                    return new ResponseEntity<>("usernameError",HttpStatus.BAD_REQUEST); //username non esiste

                case "passwordError":
                    return new ResponseEntity<>("passwordError",HttpStatus.BAD_REQUEST); //password errata

                default:
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //errore generico
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //errore generico
        }
    }

}
