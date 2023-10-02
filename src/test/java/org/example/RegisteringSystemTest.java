package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RegisteringSystemTest {
    RegisteringSystem registeringSystem;
    @BeforeEach
    void setUp() {
            registeringSystem = new RegisteringSystem();
    }

    @Nested
    class InitiateSystemTests{

        @Test
        void invalidOption() throws NotValidMailException {

            InputStream in = new ByteArrayInputStream("f\n".getBytes());
            System.setIn(in);

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            registeringSystem.initiateSystem();
            String expectedOutput = "You can only enter l or r, please try again later.";

            assertTrue(outContent.toString().contains(expectedOutput));

        }
        //Testing that register method is called
        @Test
        void validRegister() throws NotValidMailException {
            RegisteringSystem registeringSystemMock = Mockito.spy(registeringSystem);
            Mockito.doNothing().when(registeringSystemMock).register();
            InputStream in = new ByteArrayInputStream("r\n".getBytes());
            System.setIn(in);
            registeringSystemMock.initiateSystem();
            Mockito.verify(registeringSystemMock, Mockito.times(1)).register();
            Mockito.verify(registeringSystemMock, Mockito.never()).logIn();

        }
        //Testing that login method is called
        @Test
        void validLogin() throws NotValidMailException {
            RegisteringSystem registeringSystemMock = Mockito.spy(registeringSystem);
            Mockito.doNothing().when(registeringSystemMock).logIn();
            InputStream in = new ByteArrayInputStream("l\n".getBytes());
            System.setIn(in);
            registeringSystemMock.initiateSystem();
            Mockito.verify(registeringSystemMock, Mockito.times(1)).logIn();
            Mockito.verify(registeringSystemMock, Mockito.never()).register();

        }
    }

    @Nested
    class RegisterTests {
        @Test
        void invalidMail() throws NotValidMailException {
            InputStream in = new ByteArrayInputStream("f\n".getBytes());
            System.setIn(in);
            assertThrows(NotValidMailException.class, () -> registeringSystem.register());
        }

        @Test
        void foundUser() throws NotValidMailException {
            InputStream in = new ByteArrayInputStream("saif@gmail.com\nf\n".getBytes());
            System.setIn(in);

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            registeringSystem.register();
            assertTrue(outContent.toString().contains("user already exists"));
        }

        @Test
        void correctRegister() throws NotValidMailException {
            InputStream in = new ByteArrayInputStream("saifmohamed@gmail.com\nSaif Mohamed\n156\n".getBytes());
            System.setIn(in);

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            registeringSystem.register();
            assertTrue(outContent.toString().contains("You have registered successfully."));
        }
    }

    @Nested
    class LoginTests {
        @Test
        void userDoesntExist() throws NotValidMailException {
            RegisteringSystem registeringSystemMock = Mockito.spy(registeringSystem);
            Mockito.doNothing().when(registeringSystemMock).tryAgain();
            InputStream in = new ByteArrayInputStream("mohamedahmed@gmail.com\n".getBytes());
            System.setIn(in);
            registeringSystemMock.logIn();
            Mockito.verify(registeringSystemMock, Mockito.times(1)).tryAgain();

        }

        @Test
        void userExistsWrongPassword() throws NotValidMailException {
            RegisteringSystem registeringSystemMock = Mockito.spy(registeringSystem);
            Mockito.doNothing().when(registeringSystemMock).tryAgain();
            InputStream in = new ByteArrayInputStream("saif@gmail.com\n1234\n".getBytes());
            System.setIn(in);
            registeringSystemMock.logIn();
            Mockito.verify(registeringSystemMock, Mockito.times(1)).tryAgain();

        }

        @Test
        void userDeposits() throws NotValidMailException {
            InputStream in = new ByteArrayInputStream("saif@gmail.com\n12345678\nd\n0\n1000\n".getBytes());
            System.setIn(in);
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            registeringSystem.logIn();
            assertTrue(outContent.toString().contains("Your new balance on this account is"));
        }

        @Test
        void userWithdrawsInvalidAmount() throws NotValidMailException {
            RegisteringSystem registeringSystemMock = Mockito.spy(registeringSystem);
            Mockito.doNothing().when(registeringSystemMock).tryAgain();
            InputStream in = new ByteArrayInputStream("saif@gmail.com\n12345678\nw\n0\n15550\n".getBytes());
            System.setIn(in);
            registeringSystemMock.logIn();
            Mockito.verify(registeringSystemMock, Mockito.times(1)).tryAgain();

        }

        @Test
        void userWithdrawsValidAmount() throws NotValidMailException {

            InputStream in = new ByteArrayInputStream("saif@gmail.com\n12345678\nw\n0\n13000\n".getBytes());
            System.setIn(in);
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            registeringSystem.logIn();
            assertTrue(outContent.toString().contains("Your new balance on this account is"));

        }

        @Test
        void userOpensNewAccount() throws NotValidMailException {

            InputStream in = new ByteArrayInputStream("saif@gmail.com\n12345678\no\nc\n".getBytes());
            System.setIn(in);
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            registeringSystem.logIn();
            assertTrue(outContent.toString().contains("accountList="));

        }
    }

}