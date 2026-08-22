package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.request.ForgotPasswordRequestDTO;
import com.emranhss.GarmentsManagementSystem.dto.request.LoginRequestDTO;
import com.emranhss.GarmentsManagementSystem.dto.request.ResetPasswordRequestDTO;
import com.emranhss.GarmentsManagementSystem.dto.response.LoginResponseDTO;
import com.emranhss.GarmentsManagementSystem.entity.User;
import com.emranhss.GarmentsManagementSystem.repository.UserRepository;
import com.emranhss.GarmentsManagementSystem.security.JwtUtil;
import com.emranhss.GarmentsManagementSystem.util.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * Spring Security authentication manager.
     * Responsible for validating username/password.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Repository used to retrieve user information.
     */
    private final UserRepository userRepository;




    /**
     * Utility class for generating and validating JWT tokens.
     */
    private final JwtUtil jwtUtil;

    private final EmailService emailService;



    private final PasswordEncoder encoder;

    /**
     * Authenticates a user and returns login information
     * along with a JWT token.
     *
     * @param dto Login request containing email and password
     * @return LoginResponseDTO containing token and user details
     */

    public LoginResponseDTO login(LoginRequestDTO dto){
        // =====================================================
        // STEP 1: Authenticate user credentials
        //
        // Spring Security checks:
        // - User exists
        // - Password matches
        // - Account status (if configured)
        //
        // If authentication fails,
        // AuthenticationException is thrown.
        // =====================================================


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
        }
        catch (Exception e) {

            System.out.println("Exception Class = " + e.getClass().getName());
            System.out.println("Exception Message = " + e.getMessage());

            e.printStackTrace();

            throw e;
        }

        // =====================================================
        // STEP 2: Load user from database
        //
        // Since authentication succeeded,
        // retrieve the full user entity.
        // =====================================================
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // =====================================================
        // STEP 3: Generate JWT token
        //
        // Token contains:
        // - User email
        // - User role
        //
        // Example payload:
        // {
        //   "sub": "admin@gmail.com",
        //   "role": "ADMIN",
        //   "iat": ...
        //   "exp": ...
        // }
        // =====================================================
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        // =====================================================
        // STEP 4: Create response DTO
        //
        // This data is returned to frontend after login.
        // =====================================================

        LoginResponseDTO response = new LoginResponseDTO();

        response.setToken(token);
        // Token prefix used in API calls
        response.setTokenType("Bearer");

        // User basic information
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        // User role
        response.setRole(user.getRole().name());




        // =====================================================
        // STEP 6: Return login response
        //
        // Frontend receives:
        // - JWT Token
        // - User Information
        // - Role
        // - Hub Information (for agents)
        // =====================================================
        return response;



    }



    // ── Forgot password — send reset link ────────────────────────
    public void forgotPassword(ForgotPasswordRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException(
                        "No account found with email: " + dto.getEmail()));

        String token = jwtUtil.generateResetToken(user.getEmail());

        try {
            emailService.sendPasswordResetEmail(user.getEmail(), user.getName(), token);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset email: " + e.getMessage());
        }
    }

    // ── Reset password using token ────────────────────────────────
    public void resetPassword(ResetPasswordRequestDTO dto) {

        if (!jwtUtil.isValidForPurpose(dto.getToken(), "PASSWORD_RESET")) {
            throw new RuntimeException("Invalid or expired reset link");
        }

        if (dto.getNewPassword() == null || dto.getNewPassword().length() < 4) {
            throw new RuntimeException("Password must be at least 4 characters");
        }

        String email = jwtUtil.extractEmail(dto.getToken());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }





}
