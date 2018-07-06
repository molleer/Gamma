package it.chalmers.gamma.service;

import it.chalmers.gamma.db.entity.ITUser;
import it.chalmers.gamma.db.repository.ITUserRepository;
import it.chalmers.gamma.exceptions.NoCidFoundException;
import it.chalmers.gamma.exceptions.PasswordTooShortException;
import it.chalmers.gamma.requests.CreateITUserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service("userDetailsService")
public class ITUserService implements UserDetailsService{

    private final ITUserRepository itUserRepository;

    private final PasswordEncoder passwordEncoder;

    private int minPasswordLength = 8;

    private ITUserService(ITUserRepository itUserRepository) {
        this.itUserRepository = itUserRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String cid) throws UsernameNotFoundException {
        System.out.println(itUserRepository.findByCid(cid));
        return itUserRepository.findByCid(cid);
    }

    public ITUser loadUser(String cid) throws UsernameNotFoundException {
        System.out.println(itUserRepository.findByCid(cid));
        return itUserRepository.findByCid(cid);
    }

    public List<ITUser> loadAllUsers(){
        return itUserRepository.findAll();
    }


    public boolean userExists(String cid){
            if(itUserRepository.findByCid(cid) == null){
                return false;
            }
            return true;
    }

    public void createUser(CreateITUserRequest user) throws PasswordTooShortException {
        if(user.getPassword().length() < minPasswordLength){        // MOVE THIS TO VALIDATE
            throw new PasswordTooShortException();
        }
        ITUser itUser = new ITUser();
        itUser.setNick(user.getNick());
        itUser.setFirstName(user.getFirstName());
        itUser.setLastName(user.getLastName());
        String currentTime = String.valueOf(System.currentTimeMillis());
        itUser.setCid(user.getWhitelist().getCid()); //Todo get cid from activation code
        itUser.setAcceptanceYear(Year.of(2017));
        itUser.setUserAgreement(user.isUserAgreement());
        itUser.setGdpr(false);
        itUser.setAccountLocked(false);
        itUser.setEmail(itUser.getCid() + "@student.chalmers.it");
        itUser.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(itUser);
        itUserRepository.save(itUser);
    }

    public void removeCid(CreateITUserRequest createITUserRequest) throws NoCidFoundException{
        itUserRepository.delete(itUserRepository.findByCid(createITUserRequest.getWhitelist().getCid()));
    }
}
