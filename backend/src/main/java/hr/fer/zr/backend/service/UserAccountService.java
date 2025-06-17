package hr.fer.zr.backend.service;

import hr.fer.zr.backend.domain.*;
import hr.fer.zr.backend.rest.UserAccountRole;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface UserAccountService extends UserDetailsService {
	List<UsersEntity> listAll();
//
////	List<DataGroup> listDataGroups(UserAccount account);
////
//	Optional<UserAccount> findByUsername(String username);
//
//
//	UserAccount getUserAccountByIdUserAccount(Long idUserAccount);
//
//	UserAccountDTO login(LoginDTO loginDTO);
//	void confirmRegistration(String token);
//
////	String getInformationAboutParticipant(Long idUserAccount);
//
////	public boolean getCertificate(Long idUserAccount, File d);
//
//	UserAccount getUserAccountByUsername(String username);
//
//	void sendMailToMainAdmin(UserAccount mainAdmin, String type);
//
//
 List<UsersEntity> listAllDisabled();
	Optional<UsersEntity> findUserByEmail(String email);

	UsersEntity saveUser(String firstName, String lastName, String email, Date birthdate, UserAccountRole role, String username, String password, boolean enabled);

	TrainerEntity saveTrainer(UsersEntity usersEntity);

	GymAttendantsEntity saveGymA (UsersEntity usersEntity);

	Optional<UsersEntity> findUserByUsername(String username);

	void validateEmail(String email);

	void createAdmin();
	UsersEntity deleteUser(String username);

	UsersEntity fetch(String username);
	void confirmUser(UsersEntity existingUser);

	UsersEntity getUserById(Long id);

	UsersEntity updateUser(UsersEntity user);

	void deleteTrainer(Long userId);

	List<UsersEntity> listAllTrainers ();
}
