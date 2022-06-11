package com.code.blog.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name="users",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"}),
		@UniqueConstraint(columnNames = {"email"})})

public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;
	private String username;
	private String password;
	
	@ManyToMany(fetch=FetchType.EAGER,
			cascade = CascadeType.ALL)//fetch type EAGER because of We want to reterive entity along with  roles also. otherwise you LAZY lazy used for on demand but in this case entity along with reterive roles also
					  //CascadeType.ALL:->means if User save someting then roles and have to save parent to child in this case child is roles
					  //suppose User entity delete operation did then roles and have to delete operation
	@JoinTable(name = "user_roles",
	joinColumns = @JoinColumn(
			name="user_id",referencedColumnName = "id"),
	//user_id as foreign key belong to User id
	inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
						//role_id as foreing key belong to Role id
	private Set<Role> roles;

}
