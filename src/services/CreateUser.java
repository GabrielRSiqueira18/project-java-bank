package services;

import entities.User;
import repositories.UserRepository;
import repositories.interfaces.InterfaceUsers;

public class CreateUser {
	InterfaceUsers userRepository;

	public CreateUser(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User execute(
		String name,
		String email,
		String password
	) {
		User userExist = userRepository.getUserByEmail(email); 

		if(userExist != null) {
			return null;
		} 

		User user = new User(name, email, password);

		return user;
	}
}