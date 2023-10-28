package springbootandthymeleaf.com.serviceImpl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootandthymeleaf.com.entity.User;
import springbootandthymeleaf.com.repository.UserRepository;
import springbootandthymeleaf.com.service.CustomUserService;

@Service
@Transactional
public class CustomUserServiceImpl implements CustomUserService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = userRepo.findByUserName(username)
				 .orElseThrow(()-> new UsernameNotFoundException("user not found by this userName"+username));
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),getauthorities(user));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(User user){
		
		String [] userroles = user.getRoles().stream()
				.map(role-> role.getRoleName()).toArray(String[] :: new);
		
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userroles);
		return authorities;
		
	}
	
	 // deferent way 
	
	private static Collection<? extends GrantedAuthority> getauthorities(User user){
		
		return user.getRoles().stream()
				.map(role-> new SimpleGrantedAuthority(role.getRoleName()))
				.collect(Collectors.toList());
		
	}

}
