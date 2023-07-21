package me.j4n8.diplomskanaloga.user;

import me.j4n8.diplomskanaloga.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}