package com.cc.security.user;

import com.cc.mail.ColorConfluenceMailSender;
import com.cc.security.user.token.ConfirmationToken;
import com.cc.security.user.token.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@CrossOrigin(origins = {"http://localhost:4200", "https://azoriussergh4.github.io"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ColorConfluenceMailSender mailSender;

    @Autowired
    private UserComponent userComponent;

    /**
     * Creates the user disabled and send a confirmation email to enable it
     * @param data the user data
     * @return the response of the operation
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterForm data) {
        User existingUser = userRepository.findByEmailOrUsername(data.getEmail(),data.getUsername());

        if(existingUser != null){
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        } else {

            //User creation
            User user = new User(data);

            userRepository.save(user);

            //Confirmation token creation
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            //Email send
            mailSender.sendValidationAccount(user.getEmail(), confirmationToken.getToken());
        }

        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    /**
     * Verifies the token to confirm the account creation
     * @param tk the token
     * @return the response of the oepration
     */
    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmAccount(@RequestParam String tk) {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(tk);

        if (tk != null) {
            User user = userRepository.findByEmail(confirmationToken.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            confirmationTokenRepository.delete(confirmationToken);
            return new ResponseEntity<>("", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Changes the password given a logged user
     * @param passwordForm the old and new password
     * @return the response of the operation
     */
    @RequestMapping(value="/change-password", method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<User> changePassword(@RequestBody ChangePasswordForm passwordForm, HttpSession session) {
        if(!userComponent.isLoggedUser()){
            session.invalidate();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(!encoder.matches(passwordForm.getOldPassword(), userComponent.getLoggedUser().getPassword())){
                session.invalidate();
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }else if (!this.checkPasswordPattern(passwordForm.getNewPassword())) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("error-type", "password-pattern");
                return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
            } else if (passwordForm.isSamePssword()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("error-type", "same-password");
                return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
            } else {
                userComponent.getLoggedUser().setPassword(encoder.encode(passwordForm.getNewPassword()));
                userRepository.save(userComponent.getLoggedUser());
                session.invalidate();
                return new ResponseEntity<>(userComponent.getLoggedUser(), HttpStatus.OK);
            }
        }
    }

    /**
     * Creates a token to validate password recover and sends an email with a link to reset it
     * @param email the email to sent the links
     * @return the response of the operation
     */
    @GetMapping( "/recover-password")
    public ResponseEntity<String> recoverPassword(@RequestParam String email){
        User user = userRepository.findByEmail(email);
        if(user != null) {
            //Confirmation token creation
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            //Email send
            mailSender.sendRecoverPassword(user.getEmail(), confirmationToken.getToken());
        }
        return new ResponseEntity<>("", HttpStatus.CREATED);

    }

    /**
     * Changes the password based on a token sent before
     * @param tk the token associated to the user
     * @param passwordForm the old and new password
     * @return the response of the operation
     */
    @RequestMapping(value="/confirm-recover-password", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<User> confirmRecoverPassword(@RequestParam String tk, @RequestBody ChangePasswordForm passwordForm) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(tk);

        if (tk != null) {
            User user = userRepository.findByEmail(confirmationToken.getUser().getEmail());
            if (!this.checkPasswordPattern(passwordForm.getNewPassword())) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("error-type", "password-pattern");
                return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
            } else if (passwordForm.isSamePssword()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("error-type", "same-password");
                return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
            }
            user.setPassword(new BCryptPasswordEncoder().encode(passwordForm.getNewPassword()));
            userRepository.save(user);
            confirmationTokenRepository.delete(confirmationToken);
            return new ResponseEntity<>(HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * The login method
     * @return the user or an error response
     */
    @RequestMapping("/login")
    public ResponseEntity<User>login(){
        if(!userComponent.isLoggedUser()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else{
            User loggedUser=userComponent.getLoggedUser();
            return new ResponseEntity<>(loggedUser,HttpStatus.OK);
        }
    }

    /**
     * The logout method
     * @param session the current session
     * @return true if the logout was successfully, an error response in other case
     */
    @RequestMapping(value = "/logout")
    public ResponseEntity<Boolean>logout(HttpSession session){
        if(!userComponent.isLoggedUser()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else{
            session.invalidate();
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
    }

    /**
     * Checks if the password matches the password criteria
     * @param password the password to check
     * @return true if the passsword matches, false in other case
     */
    private boolean checkPasswordPattern(String password){
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(pattern, password);
    }
}
