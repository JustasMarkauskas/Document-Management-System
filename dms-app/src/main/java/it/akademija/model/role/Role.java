package it.akademija.model.role;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import it.akademija.model.operation.Operation;
import it.akademija.model.user.User;

@Entity
public class Role implements GrantedAuthority {

	@Id
	private String id;
	
	private String comment;

	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Collection<User> users;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinTable(name = "role_operations", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "operation_id", referencedColumnName = "id"))
	private Collection<Operation> operations;

	public Role() {
	}

	public Role(String id, String comment, Collection<User> users, Collection<Operation> operations) {
		this.id = id;
		this.comment = comment;
		this.users = users;
		this.operations = operations;
	}

	public Role(String id, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

	@Override
	public String getAuthority() {
		return id;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<GrantedAuthority> getGrantedAutoritiesFromOperations() {

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Operation operation : operations) {
			grantedAuthorities.add(new SimpleGrantedAuthority(operation.getId()));
		}
		List<GrantedAuthority> grantedAuthoritiesList = grantedAuthorities.stream().collect(Collectors.toList());
		return grantedAuthoritiesList;

	}

	

}
