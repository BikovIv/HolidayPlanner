package holidayplanner.HolidayPlanner.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{

	private User/*Entity*/ user;
	private Set<GrantedAuthority> authorities;

	public UserPrincipal(User/*Entity*/ user, Set<RoleEntity> roles) {
		this.user = user;
		authorities = new HashSet<GrantedAuthority>();
		insertRoles(roles);
	}

	private void insertRoles(Set<RoleEntity> roles) {
		for (RoleEntity role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}

		if (authorities.isEmpty()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPass();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
