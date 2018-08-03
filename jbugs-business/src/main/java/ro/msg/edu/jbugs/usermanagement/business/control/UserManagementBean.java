package ro.msg.edu.jbugs.usermanagement.business.control;

import ro.msg.edu.jbugs.usermanagement.business.exception.BuisnissException;
import ro.msg.edu.jbugs.usermanagement.business.exception.ExceptionCode;
import ro.msg.edu.jbugs.usermanagement.persistance.dao.UserPersistanceManagement;
import ro.msg.edu.jbugs.usermanagement.persistance.entity.User;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.usermanagement.business.utils.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class UserManagementBean implements UserManagement {

    @EJB
    UserPersistanceManagement userPersistance;

    private static final int MAX_LAST_NAME_LENGTH = 5;
    private static final int MIN_USERNAME_LENGTH = 6;

    private static final Logger logger = LogManager.getLogger(UserManagementBean.class);

    @Override
    public UserDTO createUser(UserDTO userDTO) throws BuisnissException {
        logger.log(Level.INFO, "In createUser method");

        normalizeUserDTO(userDTO);
        validateUserForCreation(userDTO);

        User user = UserDTOHelper.toEntity(userDTO);
        user.setStatus(true);
        user.setUsername(generateFullUsername(userDTO.getFirstName(), userDTO.getLastName()));
        user.setPassword(Encryptor.encrypt(userDTO.getPassword()));
        userPersistance.addUser(user);
        return UserDTOHelper.fromEntity(user);
    }

    private void validateUserForCreation(UserDTO userDTO) throws BuisnissException {
        if (!isUserValidForCreation(userDTO) || !isValidEmail(userDTO.getEmail()) || !isValidPhoneNumber(userDTO.getPhoneNumber())) {
            throw new BuisnissException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }
        // Validate if email already exists.
        if (!userPersistance.getUserByEmail(userDTO.getEmail()).isEmpty()) {
            throw new BuisnissException(ExceptionCode.EMAIL_EXISTS_ALLREADY);
        }
    }

    private void normalizeUserDTO(UserDTO userDTO) {
        userDTO.setFirstName(userDTO.getFirstName().trim());
        userDTO.setLastName(userDTO.getLastName().trim());
    }

    /**
     * Creates a suffix for the username, if the username already exists. The suffix consists
     * of a number.
     *
     * @param username
     * @return
     */
    protected String createSuffix(String username) {
        List<String> usernameLike = userPersistance.getUsersWithUsernameStartingWith(username);
        Optional<Integer> max = usernameLike
                .stream()
                .map(x -> x.substring(MIN_USERNAME_LENGTH, x.length()))
                .map(x -> x.equals("") ? 0 : Integer.parseInt(x))
                .max(Comparator.naturalOrder())
                .map(x -> x + 1);
        return max.map(Object::toString).orElse("");
    }

    protected String geneateUsername(@NotNull final String firstName, @NotNull final String lastName) {
        StringBuilder username = new StringBuilder();

        if (lastName.length() > MAX_LAST_NAME_LENGTH)
            username.append(lastName.substring(0, MAX_LAST_NAME_LENGTH) + firstName.charAt(0));
        else if (lastName.length()+firstName.length() >= MIN_USERNAME_LENGTH) {
            username.append(lastName + firstName.substring(0, MIN_USERNAME_LENGTH-lastName.length()));
        } else {
            username.append(lastName + firstName);
            int usernameLenghtj = username.length();
            for (int i=0; i<MIN_USERNAME_LENGTH-usernameLenghtj; i++) {
                username.append("0");
            }
        }
        return username.toString().toLowerCase();
    }

    private String generateFullUsername(String firstName, String lastName) {
        String prefix = geneateUsername(firstName, lastName);
        String suffix = createSuffix(prefix);
        return prefix + suffix;
    }

    private boolean isUserValidForCreation(UserDTO user) {
        return user.getFirstName() != null && user.getLastName() != null &&
            user.getEmail() != null && user.getPassword() != null &&
            user.getPhoneNumber() != null;
    }

    private boolean isValidEmail(String email){
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@msggroup.com$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
            return matcher.find();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // TODO: To be continued ... /^0[\d]{3}-[\d]{3}-[\d]{3}$/
//        final Pattern VALID_GERMANY_PHONE_REGEX =
//                Pattern.compile("(^\\+49)|(^01[5-7][1-9])", Pattern.CASE_INSENSITIVE);
//        final Pattern VALID_ROMANIA_PHONE_REGEX =
//                Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$", Pattern.CASE_INSENSITIVE);
//        Matcher matcherGermany = VALID_GERMANY_PHONE_REGEX .matcher(phoneNumber);
//        Matcher matcherRomania = VALID_ROMANIA_PHONE_REGEX .matcher(phoneNumber);
//        return matcherGermany.find() || matcherRomania.find();
        return true;
    }

    @Override
    public void deactivateUser(String username) {
        User user = userPersistance.getUserForUsername(username);
        user.setStatus(false);
        userPersistance.updateUser(user);
    }

    @Override
    public void activateUser(String username) {
        User user = userPersistance.getUserForUsername(username);
        user.setStatus(true);
        userPersistance.updateUser(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> toReturn =
                userPersistance.getAllUsers().
                    stream().
                    map(UserDTOHelper::fromEntity).
                    collect(Collectors.toList());
        return toReturn;
    }

    @Override
    public UserDTO login(String username, String password) throws BuisnissException {
        User user = userPersistance.getUserForUsername(username);
        if(user == null)
            throw new BuisnissException(ExceptionCode.USERNAME_NOT_VALID);
        if(!Encryptor.encrypt(password).equals(user.getPassword())) {
            throw new BuisnissException(ExceptionCode.PASSWORD_NOT_VALID);
        }

        return UserDTOHelper.fromEntity(user);
    }
}
