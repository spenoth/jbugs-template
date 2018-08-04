package ro.msg.edu.jbugs.usermanagement.business.control;

import ro.msg.edu.jbugs.usermanagement.business.exception.BuisnissException;
import ro.msg.edu.jbugs.usermanagement.business.exception.ExceptionCode;
import ro.msg.edu.jbugs.usermanagement.persistance.dao.UserPersistanceManagement;
import ro.msg.edu.jbugs.usermanagement.persistance.entity.User;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.usermanagement.business.utils.Encryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementBeanTest {

    @Mock
    UserPersistanceManagement userPersistanceMock;

    @InjectMocks
    UserManagementBean userManagementMock;

    @Test
    public void geneateUsername_expectedMarini() {
        String username = userManagementMock.geneateUsername("Ion", "Marin");
        assertTrue("Expected marini but found " + username, username.equals("marini"));
    }

    @Test
    public void geneateUsername_expectedIonion() {
        String username = userManagementMock.geneateUsername("Ion", "Ion");
        assertTrue("Expected ionion but found " + username, username.equals("ionion"));
    }

    @Test
    public void geneateUsername_expectedPetric() {
        String username = userManagementMock.geneateUsername("Calin", "Petridean");
        assertTrue("Expected petric but found " + username, username.equals("petric"));
    }

    @Test
    public void geneateUsername_expectedBa0000() {
        String username = userManagementMock.geneateUsername("A", "B");
        assertTrue("Expected ba0000 but found " + username, username.equals("ba0000"));
    }

    @Test
    public void testCreateSuffix_expectedEmpty() {
        when(userPersistanceMock.getUsernamesLike(any(String.class))).thenReturn(new ArrayList<>());
        String suffix = userManagementMock.createSuffix("dorel0");
        assertEquals(suffix, "");
    }

    @Test
    public void testCreateSuffix_expected4() {
        when(userPersistanceMock.getUsernamesLike(any(String.class))).thenReturn(new ArrayList<String>() {{
            add("dorel0");
            add("dorel01");
            add("dorel02");
            add("dorel03");
        }});
        String suffix = userManagementMock.createSuffix("dorel0");
        assertEquals(suffix, "4");
    }

    @Test
    public void testCreateSuffix_expected7() {
        when(userPersistanceMock.getUsernamesLike(any(String.class))).thenReturn(new ArrayList<String>() {{
            add("dorel04");
            add("dorel06");
        }});
        String suffix = userManagementMock.createSuffix("dorel0");
        assertEquals(suffix, "7");
    }

    @Test
    public void testCreateSuffix_expected1() {
        when(userPersistanceMock.getUsernamesLike(any(String.class))).thenReturn(new ArrayList<String>() {{
            add("marini");
        }});
        String suffix = userManagementMock.createSuffix("marini");
        assertEquals("1", suffix);
    }

    @Test
    public void testLogin_WrongUsername() {
        when(userPersistanceMock.getUserByUsername(any(String.class)))
                .thenReturn(null);
        try {
            userManagementMock.login("a", "s");
            fail("Shouldn't reach this point!");
        } catch (BuisnissException e) {
            assertEquals(ExceptionCode.USERNAME_NOT_VALID, e.getExceptionCode());
        }
    }

    @Test
    public void testLogin_WrongPassword() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("salut");

        when(userPersistanceMock.getUserByUsername(any(String.class)))
                .thenReturn(user);
        try {
            userManagementMock.login("salut", "s");
            fail("Shouldn't reach this point!");
        } catch (BuisnissException e) {
            assertEquals(ExceptionCode.PASSWORD_NOT_VALID, e.getExceptionCode());
        }
    }

    @Test
    public void testLogin_Success() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("salut");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));

        when(userPersistanceMock.getUserByUsername(any(String.class)))
                .thenReturn(user);
        try {
            UserDTO userDTO = userManagementMock.login("salut", "secret");
            assertEquals(userDTO.getUsername(),user.getUsername());
        } catch (BuisnissException e) {
            fail("Shouldn't reach this point!");
        }
    }

    @Test
    public void testValidatePhoneNumber_invalid1() {

        String invalidPhone = "04235564333";
        assertFalse(userManagementMock.isValidPhoneNumber(invalidPhone));
    }

    @Test
    public void testValidatePhoneNumber_invalid2() {

        String invalidPhone = "+36324543239";
        assertFalse(userManagementMock.isValidPhoneNumber(invalidPhone));
    }

    @Test
    public void testValidatePhoneNumber_invalid3() {

        String invalidPhone = "+87ss91xxsaa";
        assertFalse(userManagementMock.isValidPhoneNumber(invalidPhone));
    }

    @Test
    public void testValidatePhoneNumber_validRo1() {
        String invalidPhone = "+40745896535";
        assertTrue(userManagementMock.isValidPhoneNumber(invalidPhone));
    }

    @Test
    public void testValidatePhoneNumber_validRo2() {
        String invalidPhone = "0745896535";
        assertTrue(userManagementMock.isValidPhoneNumber(invalidPhone));
    }

    @Test
    public void testValidatePhoneNumber_validRo3() {
        String invalidPhone = "0265234543";
        assertTrue(userManagementMock.isValidPhoneNumber(invalidPhone));
    }

    @Test
    public void testValidatePhoneNumber_validRo4() {
        String invalidPhone = "+40265234543";
        assertTrue(userManagementMock.isValidPhoneNumber(invalidPhone));
    }

//    @Test
//    public void testValidatePhoneNumber_validGe1() {
//        String invalidPhone = "+491517953677";
//        assertTrue(userManagementMock.isValidPhoneNumber(invalidPhone));
//    }
//
//    @Test
//    public void testValidatePhoneNumber_validGe2() {
//        String invalidPhone = "(06442) 3933923";
//        assertTrue(userManagementMock.isValidPhoneNumber(invalidPhone));
//    }
//
//    @Test
//    public void testValidatePhoneNumber_validGe3() {
//        String invalidPhone = "+4922145911479";
//        assertTrue(userManagementMock.isValidPhoneNumber(invalidPhone));
//    }

    @Test
    public void testCreateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Cristi");
        userDTO.setLastName("Borcea");
        userDTO.setEmail("dinamo@msggroup.com");
        userDTO.setPhoneNumber("0743211122");
        userDTO.setPassword("BereGratis");
        when(userPersistanceMock.getUserByEmail2(any(String.class))).thenReturn(Optional.empty());
        try {
            UserDTO createdUser = userManagementMock.createUser(userDTO);
            assertEquals(userDTO.getFirstName(), createdUser.getFirstName());
            assertEquals(userDTO.getLastName(), createdUser.getLastName());
            assertEquals(userDTO.getEmail(), createdUser.getEmail());
            assertEquals("borcec", createdUser.getUsername());
        } catch (BuisnissException e) {
            fail(e.getExceptionCode().toString());
        }
    }
}