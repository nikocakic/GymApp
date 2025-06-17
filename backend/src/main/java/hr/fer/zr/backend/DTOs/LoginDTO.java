package hr.fer.zr.backend.DTOs;


import hr.fer.zr.backend.domain.UsersEntity;

public class LoginDTO {

        private String email;
        private String password;

        public LoginDTO() {
        }

        public LoginDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static LoginDTO toDTO(UsersEntity account) {
            return new LoginDTO(account.getUsername(), account.getPassword());
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

    }

