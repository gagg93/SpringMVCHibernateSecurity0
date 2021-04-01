package com.websystique.springmvc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.mapping.Set;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="USER")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Column(name="ADMIN", nullable=false)
	private Boolean admin;

	@NotEmpty
	@Column(name="USERNAME", nullable=false)
	private String username;

	@NotEmpty
	@Column(name="PASSWORD", nullable=false)
	private String password;
		
	@NotEmpty
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;

	@NotEmpty
	@Column(name="LAST_NAME", nullable=false)
	private String lastName;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DI_NASCITA", nullable=false)
	private Date dataDiNascita;

	public List<Prenotazione> getPrenotazioneSet() {
		return prenotazioneSet;
	}

	public void setPrenotazioneSet(List<Prenotazione> prenotazioneSet) {
		this.prenotazioneSet = prenotazioneSet;
	}

	@Column
	@OneToMany(mappedBy="user",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Prenotazione> prenotazioneSet;


	public User() {
	}

	public User(String username, String password, String firstName, String lastName, Date dataDiNascita,Boolean admin) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dataDiNascita = dataDiNascita;
		this.admin=admin;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}

	/*
	 * DO-NOT-INCLUDE passwords in toString function.
	 * It is done here just for convenience purpose.
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}


	
}
